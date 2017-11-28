package com.api.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import com.api.bd.Connecteur;
import com.api.entitie.Account;
import com.api.utils.Utils;

public class AccountDao {

	final static Logger logger = Logger.getLogger(AccountDao.class.getName()); //UNIXTIME(colonne_timestamp) as valeur_datetime

	private final static String QUERY_FIND_ALL = "SELECT * FROM ACCOUNT";
	private final static String QUERY_FIND_BY_ID = "SELECT * FROM ACCOUNT WHERE ID = ?";
	private final static String QUERY_FIND_BY_USERNAME = "SELECT * FROM ACCOUNT WHERE USERNAME = ?";
	private final static String QUERY_FIND_BY_UID = "SELECT * FROM ACCOUNT WHERE id_global = ?";

	
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
				logger.debug("MiPa, une ligne trouvée");
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
		return account;
	}

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
				logger.debug("MiPa, une ligne trouvée");
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
		return account;
	}
	
	
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
				logger.debug("MiPa, une ligne trouvée");
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
		return account;
	}

	private Account mappingAccount(final ResultSet rset) throws SQLException {
		final int id = rset.getInt("id");
		final String id_global = rset.getString("id_global");
		final String username = rset.getString("username");
		final String email = rset.getString("email");
		final String password = rset.getString("password");
		final int id_faction = rset.getInt("id_faction");
		logger.info("MIPA AVANT LES DATES");
		
		Timestamp createdAT = rset.getTimestamp("created_at");
		Timestamp updatedAT = rset.getTimestamp("updated_at");
		Timestamp deletedAT = rset.getTimestamp("deleted_at");
		
		logger.debug(rset.getTimestamp("created_at"));
		logger.debug(rset.getTimestamp("updated_at"));
		logger.debug(rset.getTimestamp("deleted_at"));
		
		/**if (Utils.testDateNulleForTimstamp(createdAT)) {
			createdAT = null;
		}
		
		if (Utils.testDateNulleForTimstamp(updatedAT)) {
			updatedAT = null;
		}
		
		if (Utils.testDateNulleForTimstamp(deletedAT)) {
			deletedAT = null;
		} **/
		
		final Account account = new Account(id, id_global, username, email, password, id_faction, createdAT, updatedAT, deletedAT);
		
		logger.debug("construction de l'objet" + account.toString());
		return account;
	}

}
