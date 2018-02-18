package loko.db.executor.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;



/**
 *  Trida pro praci s DB a nebo pred�n� 
 * 
 * @author Erik Markovi�
 *
 */
public class DBSqlExecutor {
	private static volatile DBSqlExecutor instance = null;
	private static Object mutex = new Object();
	private DBConnectionSimpleManager dbConnectionSimpleManager;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private DBSqlExecutor() {
			
		String url, user, password;
		url = "jdbc:mysql://uvdb3.active24.cz:3306/lokovrsoerik?useUnicode=true&useJDBCCompliantTimezoneShift=true&serverTimezone=UTC&characterEncoding=utf8" ;
		user = "lokovrsoerik";
		password = "J4zNx2BkZM";
		
		dbConnectionSimpleManager = new DBConnectionSimpleManager(url, user, password);				
	}

	//vrac� instanci 
	public static DBSqlExecutor getInstance() {
		DBSqlExecutor result = instance;
		if (result == null) {
			synchronized (mutex) {
				result = instance;
				if(result == null) {
					instance = new DBSqlExecutor();
				}
			}
		}		
		return result;
	}
	
	/*
	 * metody pro vr�cen� dat z DB
	 */
	/**
	 *  parametru adresa listu, kde se data ulo��
	 *  sql dotaz na tabulku
	 *  a adresa arraylistu
	 */
	public void getData(String sql, ArrayList<String[]> a) {

		try(final Statement st = dbConnectionSimpleManager.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);) {
			String query =sql;
			ResultSet rs = st.executeQuery(query);
			
			// text do konzole
			LOGGER.info("�ten� z DB " + sql);
			
			ResultSetMetaData metaData = rs.getMetaData(); // metadata
			int pocetSloupcu = metaData.getColumnCount(); //pocet sloupcu

			// ukladani do arraylist kazdy radek
			while(rs.next()) {
				String[] b = new String[pocetSloupcu];
				for(int i = 0;i<pocetSloupcu;i++) {
					b[i]= rs.getString((i+1));
				}
				a.add(b);
			}
		} catch (SQLException e) {
			LOGGER.warning("Chyba p�i �ten� z DB - " + sql +  e);
			throw new RuntimeException("Chyba p�i �ten� z DB - " + sql ,e);
		}
	}
	
	/**
	 * smazan� z�znamu v DB
	 * 
	 * id nastaven� jedi�n�ho parametru
	 */
	public void deleteRow(String dotaz, int id) {
		
		try(PreparedStatement myStmt =dbConnectionSimpleManager.getConnection().prepareStatement(dotaz)) { 
			// set param
			myStmt.setInt(1, id);
			// execute SQL
			LOGGER.info("Smaz�n� ��dku z DB " + dotaz + " id: " + id);
			
		} catch (SQLException e) {
			throw new RuntimeException("Chyba p�i delete objektu id - " + id, e);
		}
	}
	
	/**
	 * vrac� po�et nalezen�ch ��dku 
	 * 
	 *@param dotaz - sql dotaz
	 *@param hodonoty - parametry do dotazu
	 *
	 *@return pocet ��dku
	 */
	public int getCountRow(String dotaz, String[] hodnoty) {
			  
			  int pocetRadku = 0;
			  // p�ipojen� k DB
			  try(PreparedStatement rs = dbConnectionSimpleManager.getConnection().prepareStatement(dotaz)) {
				  	// na�ten� parametr�
				  	for (int i = 0; i < hodnoty.length; i++) {
				  		rs.setString(i+1, hodnoty[i]);
				  	}
				  	// proveden� p�ikazu
					ResultSet r = rs.executeQuery();
					// posun na konec
					r.last();
					//na�ten� po�ad� posledn�ho ��dku
					pocetRadku = r.getRow();			
					
					LOGGER.info("Po�et ��dku " + dotaz + " po�et: " + pocetRadku);
					
					// pro tisk do terminalu
					r.beforeFirst();
					while (r.next()) {
						LOGGER.fine("pocet: " + r.getString(1));
					}
					
					return pocetRadku;  
			} catch (SQLException e) {
				LOGGER.warning("Chyba p�i maz�n� ��dku " + e);
				throw new RuntimeException("Chyba p�i maz�n� ��dku " + hodnoty,e);
			}
			    
		}
	
	/**
	 * update ��dku v DB
	 * 
	 * dotaz - p��kaz sql
	 * hodnoty - parametry sql
	 */
	
	public void setDotaz(String dotaz, String[] hodnoty) {
		  try(PreparedStatement rs = dbConnectionSimpleManager.getConnection().prepareStatement(dotaz)) {
			  //dopln�n� hodnot
			  for (int i = 0; i < hodnoty.length; i++) {
				  rs.setString(i+1, hodnoty[i]);
			}
				LOGGER.info("Nastaven� ��dku " + dotaz);
			 //proveden� p��kazu 
				rs.executeUpdate();
			  
		} catch (SQLException e) {
			throw new RuntimeException("Chyba p�i vykonavan� SQL set p��kazu.", e);
		}
		  
	}
	/**
	 * Vlo�en� nov�ho ��dku do tabulky v DB
	 * 
	 * @param dotaz -  p�ikaz p�idan�
	 * @param hodnoty - p�idavane parametry
	 * @return id - vrac� nov� id p�idan�ho ��dku
	 */
	public int insertDotaz(String dotaz, String[] hodnoty) {
		  int id = 0;
		  try(PreparedStatement rs = dbConnectionSimpleManager.getConnection().prepareStatement(dotaz, new String[] {"id"})) {
			  //dopln�n� hodnot
			  for (int i = 0; i < hodnoty.length; i++) {
				  rs.setString(i+1, hodnoty[i]);
			}
			 //proveden� p��kazu 
			 rs.executeUpdate();

			// z�sk�n� ID:
			ResultSet res = rs.getGeneratedKeys();
			if (res.next()) {
			   id = res.getInt(1);
			}
			LOGGER.info("Vlo�en� ��dku " + dotaz + " id: " + id);
			return id;
		} catch (SQLException e) {
			throw new RuntimeException("Chyba p�i uklad�n� nov�ho ��dku do tabulky v DB " + hodnoty,e);
		}
		  
	}
}
