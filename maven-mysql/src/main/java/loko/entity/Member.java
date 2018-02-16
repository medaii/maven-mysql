package loko.entity;

import java.sql.Date;
import java.util.ArrayList;
//id, kjmeno, pjmeno, datum_narozeni, poznamka, aktivni, id_odd_kategorie, zacal
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;



//vytvoøení datového týpu
@Entity
@Table(name="clen_seznam")
public class Member {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="kjmeno")
	private String firstName;
	
	@Column(name="pjmeno")
	private String lastName;
	
	@Column(name="datum_narozeni")
	private Date birthDay;
	
	@Column(name="poznamka")
	private String note;
	
	@Column(name="aktivni")
	private int active;
	
	@Column(name="id_odd_kategorie")
	private int id_odd_kategorie;
	
	@Column(name="zacal")
	private Date enterDate;
	
	@OneToMany(mappedBy="member", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<Mail> mails;
	
	@OneToMany(mappedBy="member", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<Phone> phones ;
	
	@OneToOne(mappedBy="member", cascade= CascadeType.ALL,fetch=FetchType.EAGER)
	private RodneCislo rodneCislo;
	
	@OneToOne(mappedBy="member", cascade= CascadeType.ALL,fetch=FetchType.LAZY)
	private CshRegNumber cshRegNumber;
	
	@OneToOne(mappedBy="member", cascade= CascadeType.ALL,fetch=FetchType.LAZY)
	private TrvaleBydliste trvaleBydliste;
	
	public Member() {
	}
	
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
	public void setMember(Member member) {
		this.firstName = member.getFirstName();
		this.lastName = member.getLastName();
		this.birthDay = member.getBirthDay();
		this.note = member.getNote();
		this.active = member.getActive();
		this.id_odd_kategorie = member.getId_odd_kategorie();
		this.enterDate = member.getEnterDate();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDay() {
		
		return birthDay;
	}

	public void setBirtDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getId_odd_kategorie() {
		return id_odd_kategorie;
	}

	public void setId_odd_kategorie(int id_odd_kategorie) {
		this.id_odd_kategorie = id_odd_kategorie;
	}

	public Date getEnterDate() {
		return enterDate;
	}

	public void setEnterDate(Date enterDate) {
		this.enterDate = enterDate;
	}
	
	public RodneCislo getRodneCislo() {
		return rodneCislo;
	}
	
	public CshRegNumber getCshRegNumber() {
		return cshRegNumber;
	}

	public void setCshRegNumber(CshRegNumber cshRegNumber) {
		this.cshRegNumber = cshRegNumber;
	}

	public void setRodneCislo(RodneCislo rodneCislo) {
		this.rodneCislo = rodneCislo;
	}
	
	public TrvaleBydliste getTrvaleBydliste() {
		return trvaleBydliste;
	}

	public void setTrvaleBydliste(TrvaleBydliste trvaleBydliste) {
		this.trvaleBydliste = trvaleBydliste;
	}

	public List<Mail> getMails() {
		return mails;
	}

	public void setMails(List<Mail> mails) {
		this.mails = mails;
	}

	public void add(Mail tempMail) {
		
		if(mails!=null) {
			mails = new ArrayList<>();
		}
		mails.add(tempMail);
		tempMail.setMember(this);
	}
	
	public void add(Phone tempPhone) {
		
		if(mails!=null) {
			mails = new ArrayList<>();
		}
		phones.add(tempPhone);
		tempPhone.setMember(this);
	}
	
	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	@Override
	public String toString() {
		return  lastName + " " + firstName + "\n";
	}
	
}
