package loko.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import loko.DAO.IFUser;

/*
 * typ podle tabulky user v DB
 */
@Entity
@Table(name="users")
public class User implements IFUser {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="email" , nullable = true)
	private String email;
	
	@Column(name="is_admin")
	private boolean admin;
	
	@Column(name="password")
	private String password;
	
	public User() {
		
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
		return "User [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", email=" + email + ", admin="
				+ admin + ", password=" + password + "]";
	}

/*	@Override
	public String toString() {
		return lastName + " " + firstName;
	}
*/
	
}
