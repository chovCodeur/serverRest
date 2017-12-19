package com.api.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.api.bd.Connecteur;
import com.api.entitie.Account;

/**
 * Classe permettant la gestion des ACCOUNTS
 */
public class AccountDao {

	final static Logger logger = Logger.getLogger(AccountDao.class.getName()); 

	// requetes
	private final static String QUERY_FIND_BY_MAIL = "SELECT * FROM ACCOUNT WHERE email = ?";
	private final static String QUERY_INSERT = "INSERT INTO ACCOUNT (id_global, faction, username, password, email, created_at, updated_at, deleted_at) values (?, ?, ?, ?, ?, ?, ?, ?)";
	private final static String QUERY_UPDATE_PASSWORD_BY_ID = "UPDATE ACCOUNT SET password = ?, updated_at = ?  WHERE id = ?";
	private final static String INSERT_MOT_DE_PASSE_BY_ID = "INSERT INTO NOUVEAU_MDP (ID, FLAG) values (?, ?)";
	private final static String QUERY_UPDATE_MOT_DE_PASSE_BY_ID = "UPDATE NOUVEAU_MDP SET FLAG = ? WHERE id = ?";
	private final static String QUERY_FIND_BY_USERNAME = "SELECT * FROM ACCOUNT WHERE USERNAME = ?";
	
	/**
	 * Recherche le compte en fonction de son mail. Retourne <code>null</code>
	 * si pas de résultat
	 * 
	 * @param mail
	 * @return account
	 */
	public Account getAccountByMail(String mail) {
		Connection con = null;
		PreparedStatement stmt = null;

		Account account = new Account();
		try {
			//connexion
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_MAIL);
			stmt.setString(1, mail);

			final ResultSet rset = stmt.executeQuery();
			// resultats
			while (rset.next()) {
				account = mappingAccount(rset);
			}
			//erreurs
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (account.getId() == 0){
			return null;
		}
		return account;
	}
	
	/**
	 * Permet de modifier un mot de passe en fonction de l'id du compte
	 * @param id
	 * @param password
	 * @return <code>true</code> si erreur ou code>false</code si pas d'erreur
	 */
	public Boolean updatePasswordById(int id, String password) {
		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorUpdate = false;
		try {
			//connexion
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_UPDATE_PASSWORD_BY_ID);
			Calendar calendar = Calendar.getInstance();

			Date modifiedDate = new java.sql.Date(calendar.getTime().getTime());

			//preparation
			stmt.setString(1, password);
			stmt.setDate(2,modifiedDate);
			stmt.setInt(3, id);

			stmt.executeUpdate();
			//erreurs
		} catch (SQLException e) {
			errorUpdate = true;
			e.printStackTrace();
			
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return errorUpdate;
	}
	
	/**
	 * Permet de mettre à jour la table NOUVEAU_MDP (gestion de la génération
	 * des nouveaux mot de passe)
	 * 
	 * @param id
	 * @param flag
	 * @return <code>true</code> si tout est ok et <code>false</code> en cas
	 *         d'erreur
	 */
	public Boolean updateFlag(int id, String Flag) {
		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorUpdate = false;
		try {
			//connexion
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_UPDATE_MOT_DE_PASSE_BY_ID);
		
			//preparation
			stmt.setString(1, Flag);
			stmt.setInt(2, id);

			stmt.executeUpdate();
			//erreurs
		} catch (SQLException e) {
			errorUpdate = true;
			e.printStackTrace();
			
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return errorUpdate;
	}

	/**
	 * Permet de mapper un objet java et les resulats d'une requete SQL
	 * 
	 * @param rset
	 * @return account
	 * @throws SQLException
	 */
	private Account mappingAccount(final ResultSet rset) throws SQLException {
		final int id = rset.getInt("id");
		final String id_global = rset.getString("id_global");
		final String username = rset.getString("username");
		final String email = rset.getString("email");
		final String password = rset.getString("password");
		final String faction = rset.getString("faction");
		
		Timestamp createdAT = rset.getTimestamp("created_at");
		Timestamp updatedAT = rset.getTimestamp("updated_at");
		Timestamp deletedAT = rset.getTimestamp("deleted_at");
		
		final Account account = new Account(id, id_global, username, email, password, faction, createdAT, updatedAT, deletedAT);
		return account;
	}
	
	/**
	 * Permet d'insérer une nouvelle ligne dans la table ACCOUNT
	 * 
	 * @param account
	 * @return <code>true</code> si tout est ok et <code>false</code> en cas
	 *         d'erreur
	 */
	public Boolean insertNewAccount(Account account) {
		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorInsert = false;

		try {
			//connexion
			con = Connecteur.getConnexion();
			Calendar calendar = Calendar.getInstance();
			Date startDate = new java.sql.Date(calendar.getTime().getTime());

			//preparation
			stmt = con.prepareStatement(QUERY_INSERT);
			stmt.setString(1, account.getGlobalID());
			stmt.setString(2, account.getFaction());
			stmt.setString(3, account.getUsername());
			stmt.setString(4, account.getPassword());
			stmt.setString(5, account.getEmail());
			stmt.setDate(6, startDate);
			stmt.setDate(7, null);
			stmt.setDate(8, null);

			stmt.execute();
			
			// creation du flag dans la table NOUVEAU_MDP
			Account accountInsere = getAccountByMail(account.getEmail());
			createFlag(accountInsere.getId(), "N");
			
			//erreurs
		} catch (SQLException e) {
			e.printStackTrace();
			errorInsert = true;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return errorInsert;
	}
	
	/**
	 * Recherche le compte en fonction de son username. Retourne
	 * <code>null</code> si pas de résultat
	 * 
	 * @param username
	 * @return account
	 */
	public Account getAccountByUsername(String username) {
		Connection con = null;
		PreparedStatement stmt = null;

		Account account = new Account();
		try {
			//connexion
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_USERNAME);
			stmt.setString(1, username.toLowerCase());

			final ResultSet rset = stmt.executeQuery();
			// resultats
			while (rset.next()) {
				account = mappingAccount(rset);
			}
			//erreur
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (account.getId() == 0){
			return null;
		}
		return account;
	}
	
	/**
	 * Permet de créer une ligne dans la table NOUVEAU_MDP (gestion de la génération des nouveaux mot de passe) 
	 * @param id
	 * @param flag
	 * @return <code>true</code> si tout est ok et <code>false</code> en cas d'erreur
	 */
	public Boolean createFlag(int id, String flag) {
		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorUpdate = false;
		try {
			//connexion
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(INSERT_MOT_DE_PASSE_BY_ID);
			stmt.setInt(1, id);		
			stmt.setString(2, flag);

			//execution
			stmt.executeUpdate();
			
			//erreurs
		} catch (SQLException e) {
			errorUpdate = true;
			e.printStackTrace();
			
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return errorUpdate;
	}
}
