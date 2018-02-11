package loko.dao;


import java.util.Map;

import loko.entity.Phone;
import loko.value.PhonesMeber;

/**
 * 
 * @author Erik Markoviè
 *
 */
public interface PhoneDAO {
	/**
	 * 
	 * @param id - id telefonu, který se má smazat
	 */
	public void deletePhone(int id) ;
	/**
	 * 
	 * @param phone - pøidání telefonu do DB
	 *
	 * @return - vrací id nového záznamu
	 */
	public int addPhone(Phone phone);

	/**
	 * 
	 * @param phone  - objekt ktery má být nahrán do DB
	 * @param id  - id phone na DB
	 * 
	 */
	public void updatePhone(Phone phone, int id) ;

	/**
	 * vytvoreni listu
	 * 
	 * @phones = vrati telefony daneho clena
	 * 
	 */
	public Map<Integer, PhonesMeber> getAllPhonesMembers() ;
	/**
	 * pro vracení phone kokretní osobì
	 * 
	 * @param id_member
	 *            - id èlena pro kterého chceme vrátit telefon
	 * @return
	 */
	public PhonesMeber getPhonesMember(int id_member) ;

	/**
	 * 
	 * @param id
	 *            - telefonu na DB
	 * @return vraci objekt phone
	 */
	public Phone getPhone(int id);
	
	
}
