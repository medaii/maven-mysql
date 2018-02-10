package loko.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * 
 * @author Erik Markoviè
 *
 */

@Entity
@Table(name = "clen_mail")
public class Mail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "id_osoby", insertable = false, updatable = false)
	private int id_member;

	@Column(name = "nazev")
	private String name;

	@Column(name = "mail")
	private String mail;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "id_osoby")
	private Member member;

	// konstruktor
	public Mail() {
	}

	public Mail( String name, String mail) {
		this.name = name;
		this.mail = mail;
	}
	
	public Mail(int id_member, String name, String mail) {
		this(0, id_member, name, mail);
	}

	public Mail(int id, int id_member, String name, String mail) {
		super();
		this.id = id;
		this.id_member = id_member;
		this.name = name;
		this.mail = mail;
	}
	
	public Mail(int id, String name, String mail, Member member) {
		this.id = id;
		this.id_member = member.getId();
		this.name = name;
		this.mail = mail;
		this.member = member;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_member() {
		return id_member;
	}

	public void setId_member(int id_member) {
		this.id_member = id_member;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	public String toString() {
		return name + ": " + mail;
	}

}
