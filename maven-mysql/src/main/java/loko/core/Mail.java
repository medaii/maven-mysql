package loko.core;
/**
 * 
 * @author Erik Markoviè
 *
 */
public class Mail {
	private int id;
	private int id_member;
	private String name;
	private String mail;
	
	// konstruktor
	public Mail( int id_member, String name, String mail) {
		this(0, id_member, name, mail);
	}
	
	public Mail(int id, int id_member, String name, String mail) {
		super();
		this.id = id;
		this.id_member = id_member;
		this.name = name;
		this.mail = mail;
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
	public String toString() {
		return name + ": " + mail;
	}
	
}
