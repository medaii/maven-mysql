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
public interface IFMailsDAO {

	/**
	 * Smazání øádku v tabulce clen_mail
	 * 
	 * @param id - id mailu, který se má smazat
	 * @throw - vyhození vyjímky pøi nerealizování mazání øádku 
	 */	
	void deleteMail(int id);
	
	int addMail(Mail mail);
	
	/**
	 * 
	 * @param mail
	 *            - objekt ktery má být nahrán do DB
	 * @param id
	 *            - id mailu na DB
	 * @return - vrací poèet zmìnìných øádku nebo -1 pøi chybì
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
	 * pro vracení mailu kokretní osobì
	 * 
	 * @param id_member
	 *            - id èlena pro kterého chceme vrátit mail
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
