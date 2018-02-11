package loko.dao.jdbc.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import loko.dao.MailsDAO;
import loko.db.executor.impl.DBSqlExecutor;
import loko.entity.Mail;
import loko.value.MailsMember;

/**
 * 
 * @author Erik Markoviè
 *
 */
public class MailsDAOImpl implements MailsDAO {
	private DBSqlExecutor sqlExecutor = null;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/*public MailsDAO() {
		con = DBconn.getConn();
	}*/
	
	public MailsDAOImpl(DBSqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}

	/**
	 * 
	 * @param id
	 *            - id mailu, který se má smazat
	 * @return - vrací poèet smazaných øádku nebo -1 pøi chybì
	 */
	public void deleteMail(int id) {

		String dotaz = "DELETE FROM clen_mail " + "WHERE id = ?";
		sqlExecutor.deleteRow(dotaz, id);
	}

	public int addMail(Mail mail) {
		String dotaz = "insert into clen_mail" + " (id_osoby, nazev, mail)" + " values (?, ?, ?)";
		String[] hodnoty = { String.valueOf(mail.getId_member()), mail.getName(), mail.getMail() };

		return sqlExecutor.insertDotaz(dotaz, hodnoty);
	}

	/**
	 * 
	 * @param mail
	 *            - objekt ktery má být nahrán do DB
	 * @param id
	 *            - id mailu na DB
	 * @return - int vrací poèet zmìnìných øádku nebo -1 pøi chybì
	 */
	public int updateMail(Mail mail, int id) {

		String dotaz = "update clen_mail" + " set id_osoby = ?, nazev = ?, mail = ?" + " where id = ?";
		String[] hodnoty = { String.valueOf(mail.getId_member()), mail.getName(), mail.getMail(), String.valueOf(id) };
		int resurm = sqlExecutor.setDotaz(dotaz, hodnoty);

		return resurm;
	}

	/**
	 * vytvoreni listu
	 * 
	 * @mails = vrati maily daneho clena Map<Integer, MailsMember>
	 * 
	 */
	public Map<Integer, MailsMember> getAllMailMembers() {
		List<MailsMember> list = new ArrayList<>();
		Map<Integer, MailsMember> map = new HashMap<>();
		
		ArrayList<String[]> r = new ArrayList<>();// databaze vráti výsledek do listu

		String dotaz = "SELECT id,id_osoby as id_member, nazev as name, mail FROM clen_mail ORDER BY clen_mail.id_osoby ASC ";

		sqlExecutor.getData(dotaz, r);
		int id_meber = -1;
		MailsMember mails = null;
		for (String[] a : r) {
			Mail temp = convertRowToMail(a);
			if (temp == null) {
				LOGGER.warning("Chyba pole!");
			} else {
				if (id_meber == temp.getId_member()) {
					mails.setMails(temp);
				} else {
					list.add(mails);
					if(mails !=null) {
						map.put(id_meber, mails);
					}
					id_meber = temp.getId_member();
					mails = new MailsMember(id_meber, temp);
				}
			}
		}
		map.put(id_meber, mails);
		list.add(mails);

		return map;
	}

	/**
	 * pro vracení mailu kokretní osobì
	 * 
	 * @param id_member
	 *            - id èlena pro kterého chceme vrátit mail
	 * @return MailsMember
	 */
	public MailsMember getMailsMember(int id_member) {
		MailsMember mailsMember = new MailsMember(id_member);
		ArrayList<String[]> r = new ArrayList<>();// databaze vráti výsledek do listu

		String dotaz = "SELECT id,id_osoby as id_member, nazev as name, mail FROM clen_mail WHERE id_osoby = "
				+ id_member + " ORDER BY clen_mail.id_osoby ASC ";

		sqlExecutor.getData(dotaz, r);
		for (String[] a : r) {
			Mail temp = convertRowToMail(a);
			mailsMember.setMails(temp);
		}
		return mailsMember;

	}

	/**
	 * 
	 * @param id
	 *            - mailu na DB
	 * @return vraci objekt mail
	 */
	public Mail getMail(int id) {
		Mail mail = null;
		ArrayList<String[]> r = new ArrayList<>();// databaze vráti výsledek do listu

		String dotaz = "SELECT id,id_osoby as id_member, nazev as name, mail FROM clen_mail WHERE id = " + id
				+ " ORDER BY clen_mail.id_osoby ASC ";

		sqlExecutor.getData(dotaz, r);
		for (String[] a : r) {
			mail = convertRowToMail(a);
		}

		return mail;
	}

	/**
	 * pøevedení dat z DB do typu Mail
	 * 
	 * @param temp
	 *            - data z DB
	 * @return vrací pøetypované data do Mail
	 */
	private Mail convertRowToMail(String[] temp) {
		Mail tempMail = null;
		if (temp.length == 4 && (temp[0] != "id")) {
			int id = Integer.parseInt(temp[0]);
			int id_member = Integer.parseInt(temp[1]);
			String name = temp[2];
			String mail = temp[3];

			tempMail = new Mail(id, id_member, name, mail);

		} else {
			tempMail = null;
		}

		return tempMail;
	}

	public static void main(String[] args) {
		MailsDAOImpl mailsDAO = new MailsDAOImpl(DBSqlExecutor.getInstance());

		Map<Integer, MailsMember> map = mailsDAO.getAllMailMembers();
		System.out.println(map);
	}
}
