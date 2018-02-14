package loko.dao.hibernate.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import loko.dao.MailsDAO;
import loko.db.executor.impl.DBHibernateSqlExecutorImpl;
import loko.entity.Mail;
import loko.entity.Member;
import loko.value.MailsMember;

/**
 * Iplementace pro pøístup k datùm z tabulky clen_mail pomoci Hibernate.
 * 
 * @author Erik Markoviè
 *
 */
public class MailsHibernateDAOImpl implements MailsDAO {
	private DBHibernateSqlExecutorImpl dbHibernateSqlExecutor;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	// konstruktor
	public MailsHibernateDAOImpl(DBHibernateSqlExecutorImpl dbHibernateSqlExecutor) {
		this.dbHibernateSqlExecutor = dbHibernateSqlExecutor;
	}

	@Override
	public void deleteMail(int id) {
		dbHibernateSqlExecutor.deleteObject(id, Mail.class);
	}

	@Override
	public int addMail(Mail mail) {
		SessionFactory factory = dbHibernateSqlExecutor.getSessionFactory();

		try (Session session = factory.getCurrentSession();) {
			// start a transaction
			session.beginTransaction();

			// dotaz
			Member member = session.get(Member.class, mail.getId_member());
			member.add(mail);
			int id = (int) session.save(mail);

			// Vykonani transakce
			session.getTransaction().commit();
			return id;

		} catch (Exception e) {
			// TODO dodìlat zachycení v GUI
			throw new RuntimeException("Chyba pøi ukladání mailu do DB - " + mail, e);
		}
	}

	@Override
	public void updateMail(Mail mail, int id) {
		dbHibernateSqlExecutor.updateObject(mail);
	}

	@Override
	public Map<Integer, MailsMember> getAllMailMembers() {
		Map<Integer, MailsMember> map = new HashMap<>();

		SessionFactory factory = dbHibernateSqlExecutor.getSessionFactory();
		try (Session session = factory.getCurrentSession();) {

			// start a transaction
			session.beginTransaction();

			// dotaz

			@SuppressWarnings("unchecked")
			List<Member> list = session.createQuery("select i from Member i join fetch i.cshRegNumber "
					+ "join fetch i.rodneCislo " + "join fetch i.mails " + "join fetch i.trvaleBydliste").list();
			List<Member> members = list;
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
			LOGGER.warning("Chyba zápisu!");
		}
		return map;
	}

	@Override
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
		} catch (Exception e) {
			throw new RuntimeException("Chyba pri spojeni member s mail", e);
		}
		return mailsMember;
	}

	@Override
	public Mail getMail(int id) {
		return (Mail) dbHibernateSqlExecutor.getObject(id, Mail.class);
	}

}
