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
import com.api.entitie.Inventaire;
import com.api.entitie.Objet;

public class InventaireDao {
	
	private final static String QUERY_FIND_ALL = "SELECT * FROM INVENTAIRE";
	private final static String QUERY_FIND_BY_ID = "SELECT * FROM INVENTAIRE WHERE id_user = ?";
	private final static String QUERY_FIND_BY_ID_ACCOUNT_AND_BY_ID_OBJET = 	"SELECT * FROM INVENTAIRE WHERE id_user = ? AND id_objet = ?";
	private final static String QUERY_INSERT = "INSERT INTO INVENTAIRE (id_objet, id_user, qte) values (?, ?, ?)";
	private final static String QUERY_UPDATE = "UPDATE INVENTAIRE SET qte = ? WHERE id_user = ? AND id_objet = ?";
	
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

	public ArrayList<Inventaire> getInventaireByIdAccount(int id) {
		Connection con = null;
		PreparedStatement stmt = null;
		ArrayList<Inventaire> inventaires = new ArrayList<Inventaire>();
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_ID);
			stmt.setInt(1, id);

			final ResultSet rset = stmt.executeQuery();
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

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return inventaires;
	}
	
	private Inventaire mappingInventaire(final ResultSet rset) throws SQLException {
		final int id_objet = rset.getInt("id_objet");
		final String id_user = rset.getString("id_global");
		final int qte = rset.getInt("qte");
		final Inventaire inventaire = new Inventaire(id_user, id_objet, qte);
		return inventaire;
	}

	public Boolean insertNewInventaire(Inventaire inventaire) {
		Connection con = null;
		PreparedStatement stmt = null;
		Boolean errorInsert = false;
		try {
			con = Connecteur.getConnexion();

			ObjetDao objetDao = new ObjetDao();
			
			int dejaPresentEnbase = objetDao.testPresentInInventaireByAccounByObjet(inventaire.getId_user(), inventaire.getId_objet());
			logger.info("deja"+dejaPresentEnbase);

			if (dejaPresentEnbase == 1){
					stmt = con.prepareStatement(QUERY_UPDATE);
					int nbObjetDejaPresent = objetDao.getNbObjetByIdAccountAndByIdObjet(inventaire.getId_user(), inventaire.getId_objet());
					logger.info("nb"+nbObjetDejaPresent);

					stmt.setInt(1, nbObjetDejaPresent+inventaire.getQuantite());
					stmt.setString(2, inventaire.getId_user());
					stmt.setInt(3, inventaire.getId_objet());
					// TODO Verifier requete 
			} else {
				stmt = con.prepareStatement(QUERY_INSERT);
				stmt.setInt(1, inventaire.getId_objet());
				stmt.setString(2, inventaire.getId_user());
				stmt.setInt(3, inventaire.getQuantite());
			}
			
			stmt.execute();
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
	
	public ArrayList<Inventaire> getInventaireByIdAccountAndByIdObjet(int idAccount, int idObjet) {
		Connection con = null;
		PreparedStatement stmt = null;

		ArrayList<Inventaire> inventaires = new ArrayList<Inventaire>();
		try {
			con = Connecteur.getConnexion();
			stmt = con.prepareStatement(QUERY_FIND_BY_ID_ACCOUNT_AND_BY_ID_OBJET);
			stmt.setInt(1, idAccount);
			stmt.setInt(2, idObjet);

			Inventaire inventaire = new Inventaire();

			final ResultSet rset = stmt.executeQuery();
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

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return inventaires;
	}
	
	
}
