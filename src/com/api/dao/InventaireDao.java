package com.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.api.bd.Connecteur;
import com.api.entitie.Inventaire;

public class InventaireDao {
	
	private final static String QUERY_FIND_ALL = "SELECT * FROM INVENTAIRE";
	private final static String QUERY_FIND_BY_ID = "SELECT * FROM INVENTAIRE WHERE ID = ?";

	final static Logger logger = Logger.getLogger(InventaireDao.class.getName());

	public ArrayList<Inventaire> getAllInventaires() {
		Connection connexion = null;
		Statement stmt = null;

		ArrayList<Inventaire> inventaires = new ArrayList<Inventaire>();
		try {
			connexion = Connecteur.getConnexion();
			stmt = connexion.createStatement();
			final ResultSet rset = stmt.executeQuery(QUERY_FIND_ALL);
			Inventaire inventaire = new Inventaire();
			while (rset.next()) {
				inventaire = mappingInventaire(rset);
				inventaires.add(inventaire);
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
		return inventaires;
	}

	public Inventaire getInventaireById(int id) {
		Connection con = null;
		PreparedStatement stmt = null;

		Inventaire inventaire = new Inventaire();
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_ID);
			stmt.setInt(1, id);

			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				logger.debug("MiPa, une ligne trouvée");
				inventaire = mappingInventaire(rset);
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
		return inventaire;
	}
	
	private Inventaire mappingInventaire(final ResultSet rset) throws SQLException {
		final int id_objet = rset.getInt("id_objet");
		final int id_user = rset.getInt("id_user");
		final int qte = rset.getInt("qte");
		final Inventaire inventaire = new Inventaire(id_user, id_objet, qte);
		logger.debug("construction de l'objet" + inventaire.toString());
		return inventaire;
	}

}
