package com.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.api.bd.Connecteur;
import com.api.entitie.Account;
import com.api.entitie.Faction;

public class FactionDao {

	final static Logger logger = Logger.getLogger(FactionDao.class.getName());

	private final static String QUERY_FIND_ALL = "SELECT * FROM FACTION";
	private final static String QUERY_FIND_BY_ID = "SELECT * FROM FACTION WHERE ID = ?";

	public ArrayList<Faction> getAllFactions() {
		Connection connexion = null;
		Statement stmt = null;

		ArrayList<Faction> factions = new ArrayList<Faction>();
		try {
			connexion = Connecteur.getConnexion();
			stmt = connexion.createStatement();
			final ResultSet rset = stmt.executeQuery(QUERY_FIND_ALL);
			Faction faction = new Faction();
			while (rset.next()) {
				faction = mappingFaction(rset);
				factions.add(faction);
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
		return factions;
	}

	public Faction getFactionById(int id) {
		Connection con = null;
		PreparedStatement stmt = null;

		Faction faction = new Faction();
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_ID);
			stmt.setInt(1, id);

			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				faction = mappingFaction(rset);
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
		return faction;
	}

	private Faction mappingFaction(final ResultSet rset) throws SQLException {
		final int id = rset.getInt("id");
		final String name = rset.getString("name");
		final Faction faction = new Faction(id, name);
		return faction;
	}
	
	/*public Boolean insertNewFaction(Faction account) {
		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorInsert = false;
		
		logger.info("MiPA insertNewAccount");

		try {
			con = Connecteur.getConnexion();
			Calendar calendar = Calendar.getInstance();
			java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

			// the mysql insert statement
			String query = "INSERT INTO ACCOUNT (id_global, id_faction, username, password, email, created_at, updated_at, deleted_at)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?)";
			
			//VALUES ('0', '85', '1', 'pouloulou', 'michard', 'ouh@lol.fr', '20130405', '2013-10-20 20:18:01', '');

			// create the mysql insert preparedstatement
			stmt = con.prepareStatement(query);
			
			/*stmt.setString(1, account.getGlobalID());
			stmt.setInt(2, account.getId_faction());
			stmt.setString(3, account.getUsername());
			stmt.setString(4, account.getPassword()); // TODO
			stmt.setString(5, account.getEmail());
			stmt.setDate(6, startDate);
			stmt.setDate(7, null);//df.format(account.getCreatedAT()));
			stmt.setDate(8, null);//null); */
	/*
			// execute the preparedstatement
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					errorInsert = true;
					e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					errorInsert = false;
					e.printStackTrace();
				}
			}
		}
		return errorInsert;
	} */

}
