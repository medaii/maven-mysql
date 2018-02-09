package loko.core;

import java.sql.Date;
/**
 * 
 * @author Erik Markoviè
 * Member pøidaný o rodné èíslo, trvale bydlištì. èíslo registraèního prùkazu
 */
public class MemberFull {
	private int id;
	private String firstName;
	private String lastName;
	private Date birthDay;
	private String note;
	private int active;
	private int id_odd_kategorie;
	private Date enterDate;
	
	private String rodneCislo;
	private String trvaleBydliste;
	private String chfRegistrace;
	
	
	public MemberFull(String firstName, String lastName, Date birthDay, String note, int active, int id_odd_kategorie,
			Date enterDate, String rodneCislo, String trvaleBydliste, String chfRegistrace) {
		this(0, firstName, lastName, birthDay, note, active, id_odd_kategorie, enterDate, rodneCislo, trvaleBydliste, chfRegistrace);
	}

	public MemberFull(int id, String firstName, String lastName, Date birthDay, String note, int active,
			int id_odd_kategorie, Date enterDate, String rodneCislo, String trvaleBydliste, String chfRegistrace) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDay = birthDay;
		this.note = note;
		this.active = active;
		this.id_odd_kategorie = id_odd_kategorie;
		this.enterDate = enterDate;
		this.rodneCislo = rodneCislo;
		this.trvaleBydliste = trvaleBydliste;
		this.chfRegistrace = chfRegistrace;
	}

	public MemberFull(Member member, RodneCislo rodneCislo, CshRegNumber cshRegNumber, TrvaleBydliste trvaleBydliste) {
		this.id = member.getId();
		this.firstName = member.getFirstName();
		this.lastName = member.getLastName();
		this.birthDay = member.getBirthDay();
		this.note = member.getNote();
		this.active = member.getActive();
		this.id_odd_kategorie = member.getId_odd_kategorie();
		this.enterDate = member.getEnterDate();
		this.rodneCislo = rodneCislo.getRodne_cislo();
		this.trvaleBydliste = trvaleBydliste.getAdresa();
		this.chfRegistrace = cshRegNumber.getRegCislo();
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

	public void setBirthDay(Date birthDay) {
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

	public String getRodneCislo() {
		return rodneCislo;
	}

	public void setRodneCislo(String rodneCislo) {
		this.rodneCislo = rodneCislo;
	}

	public String getTrvaleBydliste() {
		return trvaleBydliste;
	}

	public void setTrvaleBydliste(String trvaleBydliste) {
		this.trvaleBydliste = trvaleBydliste;
	}

	public String getChfRegistrace() {
		return chfRegistrace;
	}

	public void setChfRegistrace(String chfRegistrace) {
		this.chfRegistrace = chfRegistrace;
	}
	
	public String toString() {
		return  lastName + " " + firstName + "\n";
	}
}
