package loko.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

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
	 * vrací pøipojení k DB
	 *
	 */
	public Connection getConnection() {
		if(con == null) {
			createConnection();
		}
		else {
			//kontrolo timeout connection
			try {
				if (con.isValid(2)) {
					createConnection();
					LOGGER.info("Obnovené pøipojení");
				}
			} catch (SQLException e) {
				LOGGER.severe("Nelze provest kontrolu pøipojení DB - " + e);
				JOptionPane.showMessageDialog(null, "Error connection");
				throw new RuntimeException(e);
			}
			
		}
		return con;
	}
	/*
	 * vytvoreni pripojení k DB
	 */
	private void createConnection() {
		try {
			con = DriverManager.getConnection(url,user,password);
			LOGGER.info("Pøipojeno k DB.");
		} catch (SQLException e) {
				LOGGER.severe("Chyba pøi vytvoøení pøipojení - " + e);
				JOptionPane.showMessageDialog(null, "Error connection");
				throw new RuntimeException(e);
		}
		
	}

}
