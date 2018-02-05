package loko.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Erik Markoviè
 *
 */

@Entity
@Table(name="clen_mobil")
public class Phone {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="id_osoby")
	private int id_member;
	
	@Column(name="nazev")
	private String name;
	
	@Column(name="telefon")
	private String phone;
	
	
	public Phone() {
	}

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
