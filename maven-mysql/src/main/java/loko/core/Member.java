package loko.core;

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

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import loko.DAO.IFMember;

//vytvoøení datového týpu
@Entity
@Table(name="clen_seznam")
public class Member implements IFMember {
	
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
	
	@OneToMany(mappedBy="member", cascade=CascadeType.ALL)
	private List<Mail> mails;
	
	@OneToMany(mappedBy="member", cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<Phone> phones ;
	
	@OneToOne(mappedBy="member", cascade= CascadeType.ALL)
	private RodneCislo rodneCislo;
	
	@OneToOne(mappedBy="member", cascade= CascadeType.ALL)
	private CshRegNumber cshRegNumber;
	
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
	public static void main(String[] args) {
		// vytvoøení instrance na hibernateFactory, který nám pøidìlí session
		System.out.println("a");
		try (SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
																							.addAnnotatedClass(Member.class)
																							.addAnnotatedClass(Mail.class)
																							.addAnnotatedClass(RodneCislo.class)
																							.addAnnotatedClass(CshRegNumber.class)
																							.addAnnotatedClass(Phone.class)
																							.buildSessionFactory();) {
			System.out.println("n");
			// create session
			Session session = factory.getCurrentSession();

			// start a transaction
			session.beginTransaction();

			// dotaz
			//Mail mail = session.get(Mail.class,1);
			
			Member member = session.get(Member.class, 1);
			List<Mail> mails = member.getMails();
			RodneCislo rodneCislo = member.getRodneCislo();
			
			List<Phone> phones = member.getPhones();
			Phone phone = session.get(Phone.class, 240);
			
			//member.add(phone);
			
			Mail mail2 = session.get(Mail.class, 201);
			
			
			//Member member = mail.getMember();
			System.out.println("sout" + member + " - " + mails + rodneCislo + " - c - "+ member.getCshRegNumber());
			System.out.println("Tel.-" + phones);
			System.out.println(mail2 + " " + mail2.getId_member());
			
			session.delete(phone);
			
			// commit transaction			
			session.getTransaction().commit();
			
			
		} catch (Exception e) {
			
		}
		
		
	}
}
