package loko.db.executor.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;

//import javax.swing.JOptionPane;

/**
 * 
 * @author Erik Markoviè
 *
 */
public class DBSqlExecutor {
	private static DBSqlExecutor instance = null;
	private DBConnectionSimpleManager dbConnectionSimpleManager;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private DBSqlExecutor() {
			
		String url, user, password;
		url = "jdbc:mysql://uvdb3.active24.cz:3306/lokovrsoerik?useUnicode=true&useJDBCCompliantTimezoneShift=true&serverTimezone=UTC&characterEncoding=utf8" ;
		user = "lokovrsoerik";
		password = "J4zNx2BkZM";
		
		dbConnectionSimpleManager = new DBConnectionSimpleManager(url, user, password);				
	}

	//vrací instanci 
	public static DBSqlExecutor getInstance() {
		if (instance == null) {
			instance = new DBSqlExecutor();
		}		
		return instance;
	}
	
	/*
	 * metody pro vrácení dat z DB
	 */
	/**
	 *  parametru adresa listu, kde se data uloží
	 *  sql dotaz na tabulku
	 *  a adresa arraylistu
	 */
	public void getData(String sql, ArrayList<String[]> a) {

		try(final Statement st = dbConnectionSimpleManager.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);) {
			String query =sql;
			ResultSet rs = st.executeQuery(query);
			
			// text do konzole
			LOGGER.info("Ètení z DB " + sql);
			
			ResultSetMetaData metaData = rs.getMetaData(); // metadata
			int pocetSloupcu = metaData.getColumnCount(); //pocet sloupcu
			// ukladani do arraylist kazdy radek
		
		/*	kod, aby se ulozilo do prvního øádku názvy sloupcù
		 * String[] c = new String[pocetSloupcu];
			
			for(int i = 0;i<pocetSloupcu;i++) {
				c[i]= metaData.getColumnName(i+1);
			}			
			a.add(c);
			*/
			
			while(rs.next()) {
				String[] b = new String[pocetSloupcu];
				for(int i = 0;i<pocetSloupcu;i++) {
					b[i]= rs.getString((i+1));
				}
				a.add(b);
			}
		} catch (SQLException e) {
			LOGGER.warning("Chyba pøi ètení z DB - " + sql +  e);
		}
	}
	//smazaní záznamu v DB
	/**
	 * dotaz sql pøíkaz
	 * id nastavení jedièného parametru
	 */
	public void deleteRow(String dotaz, int id) {
		
		try(PreparedStatement myStmt =dbConnectionSimpleManager.getConnection().prepareStatement(dotaz)) { 
			// set param
			myStmt.setInt(1, id);
			// execute SQL
			LOGGER.info("Smazání øádku z DB " + dotaz + " id: " + id);
			
		} catch (SQLException e) {
			throw new RuntimeException("Chyba pøi delete objektu id - " + id, e);
		}
	}
	//vrací poèet nalezených øádku 
	/**
	 * dotaz - sql dotaz
	 * hodonoty - parametry do dotazu
	 */
	public int getCountRow(String dotaz, String[] hodnoty) {
			  
			  int pocetRadku = 0;
			  // pøipojení k DB
			  try(PreparedStatement rs = dbConnectionSimpleManager.getConnection().prepareStatement(dotaz)) {
				  	// naètení parametrù
				  	for (int i = 0; i < hodnoty.length; i++) {
				  		rs.setString(i+1, hodnoty[i]);
				  	}
				  	// provedení pøikazu
					ResultSet r = rs.executeQuery();
					// posun na konec
					r.last();
					//naètení poøadí posledního øádku
					pocetRadku = r.getRow();			
					
					LOGGER.info("Poèet øádku " + dotaz + " poèet: " + pocetRadku);
					
					// pro tisk do terminalu
					r.beforeFirst();
					while (r.next()) {
						LOGGER.fine("pocet: " + r.getString(1));
					}
					
					return pocetRadku;  
			} catch (SQLException e) {
				LOGGER.warning("Chyba pøi mazání øádku " + e);
				return -1;
			}
			    
		}
	// update øádku v DB
	/**
	 * dotaz - pøíkaz sql
	 * hodnoty - parametry sql
	 */
	
	public int setDotaz(String dotaz, String[] hodnoty) {
		  try(PreparedStatement rs = dbConnectionSimpleManager.getConnection().prepareStatement(dotaz)) {
			  //doplnìní hodnot
			  for (int i = 0; i < hodnoty.length; i++) {
				  rs.setString(i+1, hodnoty[i]);
			}
				LOGGER.info("Nastavení øádku " + dotaz);
			 //provedení pøíkazu 
			return rs.executeUpdate();
			  
		} catch (SQLException e) {
			LOGGER.warning("Chyba pøi obnovì øádku " + e);
			return -1;
		}
		  
	}
	/**
	 * 
	 * @param dotaz -  pøikaz pøidaní
	 * @param hodnoty - pøidavane parametry
	 * @return id - vrací nové id pøidaného øádku
	 */
	public int insertDotaz(String dotaz, String[] hodnoty) {
		  int id = 0;
		  try(PreparedStatement rs = dbConnectionSimpleManager.getConnection().prepareStatement(dotaz, new String[] {"id"})) {
			  //doplnìní hodnot
			  for (int i = 0; i < hodnoty.length; i++) {
				  rs.setString(i+1, hodnoty[i]);
			}
			 //provedení pøíkazu 
			 rs.executeUpdate();

			// získání ID:
			ResultSet res = rs.getGeneratedKeys();
			if (res.next()) {
			   id = res.getInt(1);
			}
			LOGGER.info("Vložení øádku " + dotaz + " id: " + id);
			return id;
		} catch (SQLException e) {
			
			LOGGER.warning("Chyba pøi vkladaní øádku " + e);
			return -1;
		}
		  
	}
}
