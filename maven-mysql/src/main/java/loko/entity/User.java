package loko.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/*
 * typ podle tabulky user v DB
 */
@Entity
@Table(name="users")
public class User{
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
	
	public void setUser(User tempUser) {
		this.lastName = tempUser.getLastName();
		this.firstName = tempUser.getFirstName();
		this.email = tempUser.getEmail();
		this.admin = tempUser.isAdmin();

	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
		
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName =  lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return lastName + " " + firstName;
	}

	
}
