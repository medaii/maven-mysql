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
	 * @param id - id mailu, kter� se m� smazat v DB
	 * 
	 */	
	void deleteMail(int id);
	
	/**
	 * P�idan� entity Mail do DB
	 * 
	 * @param mail -  Entita, kter� m� b�t vlo�ena do DB
	 * @return nove id entity Mail
	 */
	int addMail(Mail mail);
	
	/**
	 * Aktualizace zaznamu entity Mail v DB
	 * 
	 * @param mail - Entita se zm�n�n�mi hodnotami, dle n� maj� b�t zm�n�ny �daje v DB
	 * @param id  - id mailu v DB
	 *
	 */
	void updateMail(Mail mail);
	
	/**
	 * vytvoreni mapy mail� dle kl��e id member
	 * 
	 * @mails = vrati maily daneho member
	 * 
	 */
	Map<Integer, MailsMember> getAllMailMembers();
	
	/**
	 * pro vracen� mailu kokretn� osob�
	 * 
	 * @param id_member - id �lena pro kter�ho chceme vr�tit mail
	 * 
	 * @return - vrac� objekt pro Model, kde jsou zobrazeny v�echny maily vybran�ho Membra
	 */
	MailsMember getMailsMember(int id_member);
	
	/**
	 * Vr�cen� po�adovan� entity Mail z DB
	 * 
	 * @param id - mailu na DB
	 * 
	 * @return vraci objekt Mail
	 */
	Mail getMail(int id);
	
}
