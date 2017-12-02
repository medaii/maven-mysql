package loko.DAO;

import java.sql.Date;

public interface IFMember {

	/*
	 * dle slozeni tabulky v DB
	 * id, kjmeno, pjmeno, datum_narozeni, poznamka, aktivni, id_odd_kategorie, zacal
	 */
	
	// id
	public int getId();
	
	public void setId(int id);
	
	//first name
	public String getFirstName();
	
	public void setFirstName(String firstName);
	
	//last name
	public String getLastName();
	
	public void setLastName(String lastName);
	
	//datum narozeni
	
	public Date getBirthDay();
	
	public void setBirtDay(Date birthDay);
	
	//poznamka
	public String getNote();
	
	public void setNote(String note) ;
	
	//aktivní
	public int getActive();

	public void setActive(int active) ;

	//id_odd_kategorie
	public int getId_odd_kategorie();

	public void setId_odd_kategorie(int id_odd_kategorie) ;

	//datum zacatku
	
		public Date getEnterDate();
		
		public void setEnterDate(Date enterDate);
		
	// to String
	@Override
	public String toString() ;
}


