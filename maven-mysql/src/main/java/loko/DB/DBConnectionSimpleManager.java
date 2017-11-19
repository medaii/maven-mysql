package loko.DB;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class DBConnectionSimpleManager implements IFDBConectionManager{
	private Connection con;
	private String url, user, password;
	
	public DBConnectionSimpleManager(String url, String user, String password) {
		this.url= url;
		this.user = user;
		this.password = password;
	}
	/**
	 * vrací pøipojení k DB
	 */
	public Connection getConnection() {
		if(con == null) {
			createConnection();
		}
		return con;
	}
	/*
	 * vytvoreni pripojení k DB
	 */
	private void createConnection() {
		try {
			con = DriverManager.getConnection(url,user,password);
			JOptionPane.showMessageDialog(null, "Connection");
		} catch (Exception e) {
				System.out.println(e);
				JOptionPane.showMessageDialog(null, "Error connection");
				throw new RuntimeException(e);
		}
		
	}

}
