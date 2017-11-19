package loko.core;
/**
 * 
 * @author Erik Markoviè
 *
 */
public class Phone {
	private int id;
	private int id_member;
	private String name;
	private String phone;
	
	public Phone(int id_member, String name, String phone) {
		this(0, id_member, name, phone);
	}

	public Phone(int id, int id_member, String name, String phone) {
		super();
		this.id = id;
		this.id_member = id_member;
		this.name = name;
		this.phone = phone;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String toString() {
		return name + ": " + phone;
	}
}
