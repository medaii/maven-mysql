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
	 * Provede smaz�n� entity Phone v DB
	 * 
	 * @param id - id telefonu, kter� se m� smazat
	 */	
	public void deletePhone(int id) ;
	
	/**
	 * Provede p�id�n� entity Phone do DB
	 * 
	 * @param phone - p�id�n� telefonu do DB
	 *
	 * @return - vrac� id nov�ho z�znamu
	 */
	public int addPhone(Phone phone);

	/**
	 * Ulo�� entitu Phone do DB
	 * 
	 * @param phone  - objekt ktery m� b�t nahr�n do DB
	 * @param id  - id phone na DB
	 * 
	 */
	public void updatePhone(Phone phone) ;

	/**
	 * Vytvo�en� p�epravky Map<Integer, PhonesMeber>
	 * 
	 * @return - phones = vrati telefony daneho clena
	 * 
	 */
	public Map<Integer, PhonesMeber> getAllPhonesMembers() ;
	
	/**
	 * Vracen� p�epravky PhonesMember, kter� obsahuje v�echny telefon� ��sla k dan� entit� Member
	 * 
	 * @param id_member  - id �lena pro kter�ho chceme vr�tit telefony
	 * @return - vrac� objekt pro model GUI napln�n� telefony dan�ho member
	 */
	public PhonesMeber getPhonesMember(int id_member) ;

	/**
	 * Vr�t� po�adovanou entitu z DB, dle id Phone
	 * 
	 * @param id - id entity phone po�adovan� z DB
	 * @return vraci objekt phone
	 */
	public Phone getPhone(int id);
	
	
}
