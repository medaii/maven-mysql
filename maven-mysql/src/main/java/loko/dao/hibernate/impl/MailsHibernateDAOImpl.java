package loko.dao.hibernate.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import loko.dao.IFMailsDAO;
import loko.db.executor.impl.DBHibernateSqlExecutor;
import loko.entity.Mail;
import loko.entity.Member;
import loko.value.MailsMember;

/**
 * 
 * @author Erik Markovi�
 *
 */
public class MailsHibernateDAOImpl implements IFMailsDAO {
	private DBHibernateSqlExecutor dbHibernateSqlExecutor;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	//konstruktor
	public MailsHibernateDAOImpl(DBHibernateSqlExecutor dbHibernateSqlExecutor) {
		this.dbHibernateSqlExecutor = dbHibernateSqlExecutor;
	}

	/**
	 * 
	 * @param id
	 *          - id mailu, kter� se m� smazat
	 * @return - vrac� po�et smazan�ch ��dku nebo -1 p�i chyb�
	 */
	public int deleteMail(int id) {
		return dbHibernateSqlExecutor.deleteObject(id, new Mail());
	}

	public int addMail(Mail mail) {
		SessionFactory factory = dbHibernateSqlExecutor.getSessionFactory();

		try (Session session = factory.getCurrentSession();) {
			// start a transaction
			session.beginTransaction();
			// dotaz
			Member member = session.get(Member.class, mail.getId_member());
			member.add(mail);
			int id = (int) session.save(mail);
			// commit transaction
			session.getTransaction().commit();
			return id;

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.warning("Chyba z�pisu! " + e);
			return 0;
		}
	}

	/**
	 * 
	 * @param mail
	 *          - objekt ktery m� b�t nahr�n do DB
	 * @param id
	 *          - id mailu na DB
	 * @return - int vrac� po�et zm�n�n�ch ��dku nebo -1 p�i chyb�
	 */
	public int updateMail(Mail mail, int id) {
		mail.setId(id);
		return dbHibernateSqlExecutor.updateObject(mail);
	}

	/**
	 * vytvoreni listu
	 * 
	 * @mails = vrati maily daneho clena Map<Integer, MailsMember>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, MailsMember> getAllMailMembers() {
		Map<Integer, MailsMember> map = new HashMap<>();

		SessionFactory factory = dbHibernateSqlExecutor.getSessionFactory();
		try (Session session = factory.getCurrentSession();) {

			// start a transaction
			session.beginTransaction();

			// dotaz

			List<Member> members = session
					.createQuery(
							"select i from Member i join fetch i.cshRegNumber " + "join fetch i.rodneCislo " 
									+ "join fetch i.mails " + "join fetch i.trvaleBydliste")
					.list();
			for (Member member : members) {
				if (!member.getMails().isEmpty()) {
					MailsMember mails = new MailsMember(member.getId());
					mails.setMails(member.getMails());
					map.put(member.getId(), mails);
				}
			}

			// vykonani tranzakce
			session.getTransaction().commit();

		} catch (Exception e) {
			LOGGER.warning("Chyba z�pisu!");
		}
		return map;
	}

	/**
	 * pro vracen� mailu kokretn� osob�
	 * 
	 * @param id_member
	 *          - id �lena pro kter�ho chceme vr�tit mail
	 * @return MailsMember
	 */
	public MailsMember getMailsMember(int id_member) {
		SessionFactory factory = dbHibernateSqlExecutor.getSessionFactory();
		MailsMember mailsMember = new MailsMember(id_member);

		try (Session session = factory.getCurrentSession();) {

			// Zacatek tranzakce
			session.beginTransaction();

			// dotaz
			Member member = session.get(Member.class, id_member);
			List<Mail> mails = member.getMails();

			// List<Phone> phones2 = phones.stream().collect(toList());
			mailsMember.setMails(mails);

			// commit tranzakce
			session.getTransaction().commit();

			// ulozeni tel. cisel do PhonesMember
			// System.out.println(phones2);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Chyba pri spojeni member s mail");
		}

		return mailsMember;
	}

	/**
	 * 
	 * @param id
	 *          - mailu na DB
	 * @return vraci objekt mail
	 */
	public Mail getMail(int id) {
			return (Mail)dbHibernateSqlExecutor.getObject(id, Mail.class);
	}

}
