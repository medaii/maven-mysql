package loko.dao;


import java.util.Map;

import loko.entity.Phone;
import loko.value.PhonesMeber;

/**
 * Rozhrani pro pøístup k datùm z tabulky clen_telefon.
 * 
 * @author Erik Markoviè
 *
 */

public interface PhoneDAO {
	/**
	 * Provede smazání entity Phone v DB
	 * 
	 * @param id - id telefonu, který se má smazat
	 */	
	public void deletePhone(int id) ;
	
	/**
	 * Provede pøidání entity Phone do DB
	 * 
	 * @param phone - pøidání telefonu do DB
	 *
	 * @return - vrací id nového záznamu
	 */
	public int addPhone(Phone phone);

	/**
	 * Uloží entitu Phone do DB
	 * 
	 * @param phone  - objekt ktery má být nahrán do DB
	 * @param id  - id phone na DB
	 * 
	 */
	public void updatePhone(Phone phone) ;

	/**
	 * Vytvoøení pøepravky Map<Integer, PhonesMeber>
	 * 
	 * @return - phones = vrati telefony daneho clena
	 * 
	 */
	public Map<Integer, PhonesMeber> getAllPhonesMembers() ;
	
	/**
	 * Vracení pøepravky PhonesMember, která obsahuje všechny telefoní èísla k dané entitì Member
	 * 
	 * @param id_member  - id èlena pro kterého chceme vrátit telefony
	 * @return - vrací objekt pro model GUI naplnìný telefony daného member
	 */
	public PhonesMeber getPhonesMember(int id_member) ;

	/**
	 * Vrátí požadovanou entitu z DB, dle id Phone
	 * 
	 * @param id - id entity phone požadovaný z DB
	 * @return vraci objekt phone
	 */
	public Phone getPhone(int id);
	
	
}
