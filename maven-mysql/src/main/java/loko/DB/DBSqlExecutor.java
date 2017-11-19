package loko.DB;

import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//import javax.swing.JOptionPane;


public class DBSqlExecutor {
	private static DBSqlExecutor instance = null;
	private DBConnectionSimpleManager dbConnectionSimpleManager;
	private DBSqlExecutor() {
			
		String url, user, password;
		url = "jdbc:mysql://uvdb3.active24.cz:3306/lokovrsoerik?useUnicode=true&useJDBCCompliantTimezoneShift=true&serverTimezone=UTC&characterEncoding=utf8" ;
		user = "lokovrsoerik";
		password = "J4zNx2BkZM";
		
		dbConnectionSimpleManager = new DBConnectionSimpleManager(url, user, password);				
	}

	//vrac� instanci 
	public static DBSqlExecutor getInstance() {
		if (instance == null) {
			instance = new DBSqlExecutor();
		}		
		return instance;
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
			System.out.println("Read form DB - " + sql);
			
			ResultSetMetaData metaData = rs.getMetaData(); // metadata
			int pocetSloupcu = metaData.getColumnCount(); //pocet sloupcu
			// ukladani do arraylist kazdy radek
		
		/*	kod, aby se ulozilo do prvn�ho ��dku n�zvy sloupc�
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
		} catch (Exception e) {
			System.out.println("Chyba - " + e);
		}
	}
	//smazan� z�znamu v DB
	/**
	 * dotaz sql p��kaz
	 * id nastaven� jedi�n�ho parametru
	 */
	public int deleteRow(String dotaz, int id) {
		
		try(PreparedStatement myStmt =dbConnectionSimpleManager.getConnection().prepareStatement(dotaz)) { 
			// set param
			myStmt.setInt(1, id);
			// execute SQL
			return myStmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQL delete error- " + e);
			return -1;
		}
	}
	//vrac� po�et nalezen�ch ��dku 
	/**
	 * dotaz - sql dotaz
	 * hodonoty - parametry do dotazu
	 */
	public int getCountRow(String dotaz, String[] hodnoty) {
			  System.out.println("update " + dotaz); // protisk do terminalu
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
					
					System.out.println("Dotaz na po�et ��dku - " + pocetRadku);// protisk do terminalu
					
					// pro tisk do terminalu
					r.beforeFirst();
					while (r.next()) {
						System.out.println("ucet: " + r.getString(1));
					}
					
					return pocetRadku;  
			} catch (SQLException e) {
				System.out.println(e);
				return -1;
			}
			    
		}
	// update ��dku v DB
	/**
	 * dotaz - p��kaz sql
	 * hodnoty - parametry sql
	 */
	
	public int setDotaz(String dotaz, String[] hodnoty) {
		  //System.out.println("update " + dotaz);
		  try(PreparedStatement rs = dbConnectionSimpleManager.getConnection().prepareStatement(dotaz)) {
			  //dopln�n� hodnot
			  for (int i = 0; i < hodnoty.length; i++) {
				  rs.setString(i+1, hodnoty[i]);
			}
			 //proveden� p��kazu 
			return rs.executeUpdate();
			  
		} catch (SQLException e) {
			
			System.out.println(e);
			return -1;
		}
		  
	}
	/**
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
			return id;
		} catch (SQLException e) {
			
			System.out.println(e);
			return -1;
		}
		  
	}
}
