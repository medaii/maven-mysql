package loko.dao;


import java.util.Map;

import loko.entity.Phone;
import loko.value.PhonesMeber;

/**
 * Rozhrani pro p��stup k dat�m z tabulky clen_telefon.
 * 
 * @author Erik Markovi�
 *
 */

public interface PhoneDAO {
	/**
	 * 
	 * @param id - id telefonu, kter� se m� smazat
	 */	
	public void deletePhone(int id) ;
	
	/**
	 * 
	 * @param phone - p�id�n� telefonu do DB
	 *
	 * @return - vrac� id nov�ho z�znamu
	 */
	public int addPhone(Phone phone);

	/**
	 * 
	 * @param phone  - objekt ktery m� b�t nahr�n do DB
	 * @param id  - id phone na DB
	 * 
	 */
	public void updatePhone(Phone phone, int id) ;

	/**
	 * vytvoreni listu
	 * 
	 * @return - phones = vrati telefony daneho clena
	 * 
	 */
	public Map<Integer, PhonesMeber> getAllPhonesMembers() ;
	
	/**
	 * pro vracen� phone kokretn� osob�
	 * 
	 * @param id_member  - id �lena pro kter�ho chceme vr�tit telefon
	 * @return - vrac� objekt pro model GUI napln�n� telefony dan�ho member
	 */
	public PhonesMeber getPhonesMember(int id_member) ;

	/**
	 * 
	 * @param id - id entity phone po�adovan� z DB
	 * @return vraci objekt phone
	 */
	public Phone getPhone(int id);
	
	
}