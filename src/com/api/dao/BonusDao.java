package com.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.api.bd.Connecteur;
import com.api.entitie.Bonus;

/**
 * Classe permettant la gestion des bonus pour les autres jeux 
 *
 */
public class BonusDao {
	final static Logger logger = Logger.getLogger(BonusDao.class.getName()); //UNIXTIME(colonne_timestamp) as valeur_datetime

	// requetes
	private final static String QUERY_SELECT_POTION_BY_UID ="SELECT OBJET.id_objet, qte, nom_recette, description, puissance_objet, type_objet FROM INVENTAIRE INNER JOIN OBJET ON INVENTAIRE.id_objet = OBJET.id_objet INNER JOIN RECETTE ON RECETTE.id_objet = OBJET.id_objet WHERE INVENTAIRE.id_global = ? AND RECETTE.type = ? AND qte > 0";
	private final static String QUERY_UPDATE_INVENTAIRE ="UPDATE INVENTAIRE SET qte = ? WHERE id_objet = ? and id_global = ? ";
	private final static String QUERY_GET_QTE_BY_UUDI_AND_ID_POTION ="SELECT qte FROM INVENTAIRE WHERE id_objet = ? and id_global = ? ";

	/**
	 * Permet de recuperer les bonus en fonction de l'uuid et du type de jeu 
	 * @param id_global
	 * @param type
	 * @return listeBonus
	 */
	public ArrayList<Bonus> getBonusByUuidAccount(String id_global, String type) {
		Connection con = null;
		PreparedStatement stmt = null;
		ArrayList<Bonus> listeBonus = new ArrayList<Bonus>();
		try {
			//connexion
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_SELECT_POTION_BY_UID);
			stmt.setString(1, id_global);
			stmt.setString(2, type);
			
			final ResultSet rset = stmt.executeQuery();
			Bonus bonus = new Bonus();
			//resultats
			while (rset.next()) {				
				bonus = mappingBonus(rset);
				listeBonus.add(bonus);
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
		return listeBonus;
	}
	
	/**
	 * Permet de gérer la consommation des bonus faites par les autres jeux. 
	 * @param qte
	 * @param idPotion
	 * @param uuid
	 * @return <code>false</code> si tout est ok et <code>true</code> en cas d'erreur
	 */
	public Boolean updateInventaireByUuid(int qte, int idPotion, String uuid) {
		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorUpdate = false;
		try {
			//connexion
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_UPDATE_INVENTAIRE);

			//preparation
			stmt.setInt(1, qte);
			stmt.setInt(2, idPotion);
			stmt.setString(3, uuid);

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
	 * Permet de verifier la quantité disponible pour une podtion et pour un UUID (verification avant suppression)
	 * @param idPotion
	 * @param uuid
	 * @return qte
	 */
	
	public int getQtePotionByUuidAndidPotion(int idPotion, String uuid) {
		Connection con = null;
		PreparedStatement stmt = null;
		int nb = 0;
		try {
			//connexion
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_GET_QTE_BY_UUDI_AND_ID_POTION);

			stmt.setInt(1, idPotion);
			stmt.setString(2, uuid);
			
			final ResultSet rset = stmt.executeQuery();
			// resultats
			while (rset.next()) {
				nb = rset.getInt("qte");
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
		return nb;
	}
	
	/**
	 * Permet de mapper un objet java et les resulats d'une requete SQL
	 * @param rset
	 * @return bonus
	 * @throws SQLException
	 */
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
