package loko.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
/**
 * 
 * @author Erik Markovi�
 *
 */
public class DBConnectionSimpleManager implements IFDBConectionManager{
	private Connection con;
	private String url, user, password;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public DBConnectionSimpleManager(String url, String user, String password) {
		this.url= url;
		this.user = user;
		this.password = password;
	}
	/**
	 * vrac� p�ipojen� k DB
	 *
	 */
	public Connection getConnection() {
		if(con == null) {
			createConnection();
		}
		else {
			//kontrolo timeout connection
			try {
				if (!con.isValid(1)) {
					createConnection();
					LOGGER.info("Obnoven� p�ipojen�");
				}
			} catch (SQLException e) {
				LOGGER.severe("Nelze provest kontrolu p�ipojen� DB - " + e);
				JOptionPane.showMessageDialog(null, "Error connection");
				throw new RuntimeException(e);
			}
			
		}
		return con;
	}
	/*
	 * vytvoreni pripojen� k DB
	 */
	private void createConnection() {
		try {
			con = DriverManager.getConnection(url,user,password);
			LOGGER.warning("P�ipojeno k DB.");
		} catch (SQLException e) {
				LOGGER.severe("Chyba p�i vytvo�en� p�ipojen� - " + e);
				JOptionPane.showMessageDialog(null, "Error connection");
				throw new RuntimeException(e);
		}
		
	}

}
