/**
 * 
 */
package loko.dao;


import java.util.Map;

import loko.entity.Mail;
import loko.value.MailsMember;

/**
 * Rozhrani pro praci s maily.
 * @author Erik Markovi�
 *
 */
public interface IFMailsDAO {

	/**
	 * 
	 * @param id
	 *            - id mailu, kter� se m� smazat
	 * @return - vrac� po�et smazan�ch ��dku nebo -1 p�i chyb�
	 */
	int deleteMail(int id);
	
	int addMail(Mail mail);
	
	/**
	 * 
	 * @param mail
	 *            - objekt ktery m� b�t nahr�n do DB
	 * @param id
	 *            - id mailu na DB
	 * @return - vrac� po�et zm�n�n�ch ��dku nebo -1 p�i chyb�
	 */
	int updateMail(Mail mail, int id);
	
	/**
	 * vytvoreni listu
	 * 
	 * @mails = vrati maily daneho clena
	 * 
	 */
	Map<Integer, MailsMember> getAllMailMembers();
	
	/**
	 * pro vracen� mailu kokretn� osob�
	 * 
	 * @param id_member
	 *            - id �lena pro kter�ho chceme vr�tit mail
	 * @return
	 */
	MailsMember getMailsMember(int id_member);
	
	/**
	 * 
	 * @param id
	 *            - mailu na DB
	 * @return vraci objekt mail
	 */
	Mail getMail(int id);
	
}
