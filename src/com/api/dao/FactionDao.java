package com.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.api.bd.Connecteur;
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
				logger.debug("MiPa, une ligne trouvée");
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
		logger.debug("construction de l'objet" + faction.toString());
		return faction;
	}

}
