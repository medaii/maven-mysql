/**
 * 
 */
package loko.dao;


import java.util.Map;

import loko.entity.Mail;
import loko.value.MailsMember;

/**
 * Rozhrani pro pøístup k datùm z tabulky clen_mail.
 * 
 * @author Erik Markoviè
 *
 */
public interface MailsDAO {

	/**
	 * Smazání øádku v tabulce clen_mail
	 * 
	 * @param id - id mailu, který se má smazat v DB
	 * 
	 */	
	void deleteMail(int id);
	
	/**
	 * 
	 * @param mail -  Entita, která má být vložena do DB
	 * @return nove id entity Mail
	 */
	int addMail(Mail mail);
	
	/**
	 * 
	 * @param mail - Entita se zmìnìnými hodnotami, dle ní mají být zmìnìny údaje v DB
	 * @param id  - id mailu v DB
	 *
	 */
	void updateMail(Mail mail, int id);
	
	/**
	 * vytvoreni mapy mailù dle klíèe id member
	 * 
	 * @mails = vrati maily daneho member
	 * 
	 */
	Map<Integer, MailsMember> getAllMailMembers();
	
	/**
	 * pro vracení mailu kokretní osobì
	 * 
	 * @param id_member - id èlena pro kterého chceme vrátit mail
	 * 
	 * @return - vrací objekt pro Model, kde jsou zobrazeny všechny maily vybraného Membra
	 */
	MailsMember getMailsMember(int id_member);
	
	/**
	 * 
	 * @param id - mailu na DB
	 * 
	 * @return vraci objekt Mail
	 */
	Mail getMail(int id);
	
}
