package loko.dao;

public interface IFUser {
	
	/*
	 * dle slozeni tabulky v DB
	 * id, last name, firt name, email, admin, password
	 */
	
	// id
	public int getId();
	
	public void setId(int id);
	
	//last name
	public String getLastName();
	
	public void setLastName(String lastName);
	
	//first name
	public String getFirstName();
	
	public void setFirstName(String firstName);
	
	//email
	public String getEmail();
	
	public void setEmail(String email) ;
	
	//admin
	public boolean isAdmin();

	public void setAdmin(boolean admin) ;

	//password
	public String getPassword();

	public void setPassword(String password) ;

	// to String
	@Override
	public String toString() ;
}
