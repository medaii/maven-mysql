package loko.core;

import loko.DAO.IFUser;

/*
 * typ podle tabulky user v DB
 */
public class User implements IFUser {
	private int id;
	private String lastName;
	private String firstName;
	private String email;
	private boolean admin;
	private String password;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	public User(String lastName, String firstName, String email, boolean admin, String password) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.admin = admin;
		this.password = password;
	}
	
	public User(int id, String lastName, String firstName, String email, boolean admin) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.admin = admin;
	}
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public void setId(int id) {
		this.id = id;
		
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName =  lastName;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean isAdmin() {
		return admin;
	}

	@Override
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return lastName + " " + firstName;
	}

}
