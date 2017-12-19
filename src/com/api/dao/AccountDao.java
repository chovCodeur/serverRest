package com.api.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.log4j.Logger;
import com.api.bd.Connecteur;
import com.api.entitie.Account;
import com.api.utils.Utils;

public class AccountDao {

	final static Logger logger = Logger.getLogger(AccountDao.class.getName()); //UNIXTIME(colonne_timestamp) as valeur_datetime

	private final static String QUERY_FIND_ALL = "SELECT * FROM ACCOUNT";
	private final static String QUERY_FIND_IN_NOUVEAU_MDP = "SELECT FLAG FROM NOUVEAU_MDP INNER JOIN ACCOUNT ON ACCOUNT.id = NOUVEAU_MDP.id WHERE username = ?";

	
	private final static String QUERY_FIND_BY_MAIL = "SELECT * FROM ACCOUNT WHERE email = ?";

	private final static String QUERY_FIND_BY_ID = "SELECT * FROM ACCOUNT WHERE ID = ?";
	private final static String QUERY_INSERT = "INSERT INTO ACCOUNT (id_global, faction, username, password, email, created_at, updated_at, deleted_at) values (?, ?, ?, ?, ?, ?, ?, ?)";
	private final static String QUERY_UPDATE_PASSWORD_BY_ID = "UPDATE ACCOUNT SET password = ?, updated_at = ?  WHERE id = ?";
	private final static String INSERT_MOT_DE_PASSE_BY_ID = "INSERT INTO NOUVEAU_MDP (ID, FLAG) values (?, ?)";

	
	private final static String QUERY_UPDATE_MOT_DE_PASSE_BY_ID = "UPDATE NOUVEAU_MDP SET FLAG = ? WHERE id = ?";

	
	private final static String QUERY_FIND_BY_USERNAME = "SELECT * FROM ACCOUNT WHERE USERNAME = ?";
	private final static String QUERY_FIND_BY_UID = "SELECT * FROM ACCOUNT WHERE id_global = ?";

	/*
	public ArrayList<Account> getAllAccounts() {
		Connection connexion = null;
		Statement stmt = null;

		ArrayList<Account> accounts = new ArrayList<Account>();
		try {
			connexion = Connecteur.getConnexion();
			stmt = connexion.createStatement();
			final ResultSet rset = stmt.executeQuery(QUERY_FIND_ALL);
			Account account = new Account();
			while (rset.next()) {
				account = mappingAccount(rset);
				accounts.add(account);
			}
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
			if (connexion != null) {
				try {
					connexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return accounts;
	}

	public Account getAccountById(int id) {
		Connection con = null;
		PreparedStatement stmt = null;

		Account account = new Account();
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_ID);
			stmt.setInt(1, id);

			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				account = mappingAccount(rset);
			}
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
	*/
	
	public Account getAccountByMail(String mail) {
		Connection con = null;
		PreparedStatement stmt = null;

		Account account = new Account();
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_MAIL);
			stmt.setString(1, mail);

			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				account = mappingAccount(rset);
			}
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
	
	public Boolean updatePasswordById(int id, String password) {
		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorUpdate = false;
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_UPDATE_PASSWORD_BY_ID);
			Calendar calendar = Calendar.getInstance();

			Date modifiedDate = new java.sql.Date(calendar.getTime().getTime());

			stmt.setString(1, password);
			stmt.setDate(2,modifiedDate);
			stmt.setInt(3, id);

			stmt.executeUpdate();
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
	
	
	public Boolean updateFlag(int id, String Flag) {
		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorUpdate = false;
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_UPDATE_MOT_DE_PASSE_BY_ID);
		
			stmt.setString(1, Flag);
			stmt.setInt(2, id);

			stmt.executeUpdate();
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
	
	public Boolean insertNewAccount(Account account) {
		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorInsert = false;

		try {
			con = Connecteur.getConnexion();
			Calendar calendar = Calendar.getInstance();
			Date startDate = new java.sql.Date(calendar.getTime().getTime());

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
			
			Account accountInsere = getAccountByMail(account.getEmail());
			createFlag(accountInsere.getId(), "N");
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
	
	/*
	public Account getAccounByUid(String uid) {
		Connection con = null;
		PreparedStatement stmt = null;

		Account account = new Account();
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_UID);
			stmt.setString(1, uid);

			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				account = mappingAccount(rset);
			}
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
	
	*/
	public Account getAccountByUsername(String username) {
		Connection con = null;
		PreparedStatement stmt = null;

		Account account = new Account();
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_USERNAME);
			stmt.setString(1, username.toLowerCase());

			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				account = mappingAccount(rset);
			}
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

/*
	public Boolean motDePasseAChanger(String username){
		Connection con = null;
		PreparedStatement stmt = null;
		
		Boolean flag = false;
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_IN_NOUVEAU_MDP);
			stmt.setString(1, username);

			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				String flagRetourne = rset.getString("FLAG");
				
				flag = flagRetourne.equals("N") ? false : true;
			}
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
		
		return flag;
	}
	*/
	
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
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(INSERT_MOT_DE_PASSE_BY_ID);
			stmt.setInt(1, id);		
			stmt.setString(2, flag);

			stmt.executeUpdate();
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
