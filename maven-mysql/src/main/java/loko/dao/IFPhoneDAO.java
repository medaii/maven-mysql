package loko.dao;


import java.util.Map;

import loko.entity.Phone;
import loko.value.PhonesMeber;

/**
 * 
 * @author Erik Markovi�
 *
 */
public interface IFPhoneDAO {
	/**
	 * 
	 * @param id
	 *            - id telefonu, kter� se m� smazat
	 * @return - vrac� po�et smazan�ch ��dku nebo -1 p�i chyb�
	 */
	public int deletePhone(int id) ;
	/**
	 * 
	 * @param phone - p�id�n� telefonu do DB
	 * @return
	 */
	public int addPhone(Phone phone);

	/**
	 * 
	 * @param phone
	 *            - objekt ktery m� b�t nahr�n do DB
	 * @param id
	 *            - id phone na DB
	 * @return - vrac� po�et zm�n�n�ch ��dku nebo -1 p�i chyb�
	 */
	public int updatePhone(Phone phone, int id) ;

	/**
	 * vytvoreni listu
	 * 
	 * @phones = vrati telefony daneho clena
	 * 
	 */
	public Map<Integer, PhonesMeber> getAllPhonesMembers() ;
	/**
	 * pro vracen� phone kokretn� osob�
	 * 
	 * @param id_member
	 *            - id �lena pro kter�ho chceme vr�tit telefon
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
