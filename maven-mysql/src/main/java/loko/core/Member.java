package loko.core;

import java.sql.Date;
//id, kjmeno, pjmeno, datum_narozeni, poznamka, aktivni, id_odd_kategorie, zacal

import loko.DAO.IFMember;

//vytvoøení datového týpu

public class Member implements IFMember {
	private int id;
	private String firstName;
	private String lastName;
	private Date birthDay;
	private String note;
	private int active;
	private int id_odd_kategorie;
	private Date enterDate;
	
	
	public Member(String firstName,	String lastName, Date birthDay,	String note, int active, int id_odd_kategorie, Date enterDate) {
		this(0,firstName, lastName, birthDay, note, active, id_odd_kategorie, enterDate);
		}
	public Member(int id, String firstName,	String lastName, Date birthDay,	String note, int active, int id_odd_kategorie, Date enterDate) {
		
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDay = birthDay;
		this.note = note;
		this.active = active;
		this.id_odd_kategorie = id_odd_kategorie;
		this.enterDate = enterDate;
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
	public String getFirstName() {
		return firstName;
	}

	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public Date getBirthDay() {
		
		return birthDay;
	}

	@Override
	public void setBirtDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	@Override
	public String getNote() {
		return note;
	}

	@Override
	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public int getActive() {
		return active;
	}

	@Override
	public void setActive(int active) {
		this.active = active;
	}

	@Override
	public int getId_odd_kategorie() {
		return id_odd_kategorie;
	}

	@Override
	public void setId_odd_kategorie(int id_odd_kategorie) {
		this.id_odd_kategorie = id_odd_kategorie;
	}

	@Override
	public Date getEnterDate() {
		return enterDate;
	}

	@Override
	public void setEnterDate(Date enterDate) {
		this.enterDate = enterDate;
	}
	@Override
	public String toString() {
		return  lastName + " " + firstName + "\n";
	}
}
