package loko.value;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import loko.entity.Mail;
import loko.entity.Member;
import loko.entity.Phone;
/**
 * 
 * @author Erik Markoviè
 *
 */
public class MemberList {
	private int id;
	private String firstName;
	private String lastName;
	private Date birthDay;	

	private List<Mail> mails = new ArrayList<>();
	private List<Phone> phones = new ArrayList<>();
	

	public MemberList(int id, String firstName, String lastName, Date birthDay, List<Mail> mails, List<Phone> phones) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDay = birthDay;
		if (mails!= null) {
			this.mails = mails;
		}
		else {
			Mail mail = new Mail(id, "", "");
			this.mails.add(mail);
		}
		if (phones!= null) {
			this.phones = phones;
		}
		else {
			Phone phone = new Phone(id, "", "");
			this.phones.add(phone);
		}
		
		
		
	}
	public MemberList(Member member, List<Mail> mails, List<Phone> phones) {
		this.id = member.getId();
		this.firstName = member.getFirstName();
		this.lastName = member.getLastName();
		this.birthDay = member.getBirthDay();
		this.mails = mails;
		this.phones = phones;
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

	public List<Mail> getMails() {
		return mails;
	}

	public void setMails(List<Mail> mails) {
		this.mails = mails;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	@Override
	public String toString() {
		return  lastName + " " + firstName + " - " + mails + "\n";
	}
}
