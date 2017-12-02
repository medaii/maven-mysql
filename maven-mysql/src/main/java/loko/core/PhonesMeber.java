package loko.core;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Erik Markoviè
 *
 */
public class PhonesMeber {
	private int id_meber;
	private List<Phone> phones = new ArrayList<>();	
	
	public PhonesMeber(int id_meber) {
		this.id_meber = id_meber;
	}
	public PhonesMeber(int id_meber, Phone phone) {
		this.id_meber = id_meber;
		setPhones(phone);
	}
	
	public int getId_meber() {
		return id_meber;
	}

	public void setId_meber(int id_meber) {
		this.id_meber = id_meber;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(Phone phone) {
		this.phones.add(phone);
	}
	
	public String toString() {
		String phones = "";
		for (Phone phone : this.phones) {
			phones += phone.getPhone() + ", ";
		}
		phones +="\n";
		return phones;
	}
}
