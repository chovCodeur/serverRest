package com.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.api.bd.Connecteur;
import com.api.entitie.Bonus;
import com.api.entitie.Inventaire;

public class BonusDao {
	final static Logger logger = Logger.getLogger(BonusDao.class.getName()); //UNIXTIME(colonne_timestamp) as valeur_datetime

	private final static String QUERY_SELECT_POTION_BY_UID ="SELECT OBJET.id_objet, qte, nom_recette, description, puissance_objet, type_objet FROM INVENTAIRE INNER JOIN OBJET ON INVENTAIRE.id_objet = OBJET.id_objet INNER JOIN RECETTE ON RECETTE.id_objet = OBJET.id_objet WHERE INVENTAIRE.id_global = ? AND type_objet in ('potion','amelioration')";
	private final static String QUERY_SELECT_AMELIORATION_BY_UID ="";

	public ArrayList<Bonus> getBonusByIdAccount(String id_global) {
		Connection con = null;
		PreparedStatement stmt = null;
		ArrayList<Bonus> listeBonus = new ArrayList<Bonus>();
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_SELECT_POTION_BY_UID);
			stmt.setString(1, id_global);

			final ResultSet rset = stmt.executeQuery();
			Bonus bonus = new Bonus();
			while (rset.next()) {				
				bonus = mappingBonus(rset);
				listeBonus.add(bonus);
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
		return listeBonus;
	}
	
	private Bonus mappingBonus(final ResultSet rset) throws SQLException {
		final int id_objet = rset.getInt("id_objet");
		final int qte = rset.getInt("qte");
		final String nom_recette = rset.getString("nom_recette");
		final String description_recette = rset.getString("description");
		final int puissance_objet = rset.getInt("puissance_objet");
		final String type_objet = rset.getString("type_objet");

		final Bonus bonus = new Bonus(id_objet, qte, description_recette, puissance_objet, type_objet, nom_recette);
		return bonus;
	}
	
}