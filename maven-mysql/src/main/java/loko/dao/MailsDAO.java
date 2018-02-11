/**
 * 
 */
package loko.dao;


import java.util.Map;

import loko.entity.Mail;
import loko.value.MailsMember;

/**
 * Rozhrani pro p��stup k dat�m z tabulky clen_mail.
 * 
 * @author Erik Markovi�
 *
 */
public interface MailsDAO {

	/**
	 * Smaz�n� ��dku v tabulce clen_mail
	 * 
	 * @param id - id mailu, kter� se m� smazat
	 * @throw - vyhozen� vyj�mky p�i nerealizov�n� maz�n� ��dku 
	 */	
	void deleteMail(int id);
	
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
