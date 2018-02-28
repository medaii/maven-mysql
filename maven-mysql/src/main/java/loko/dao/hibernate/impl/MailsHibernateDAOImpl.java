package loko.dao.hibernate.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import loko.dao.MailsDAO;
import loko.entity.Mail;
import loko.entity.Member;
import loko.value.MailsMember;

/**
 * Iplementace pro pøístup k datùm z tabulky clen_mail pomoci Hibernate.
 * 
 * @author Erik Markoviè
 *
 */
@Repository
public class MailsHibernateDAOImpl implements MailsDAO {
	@Autowired
	private SessionFactory sessionFactory;

	// konstruktor
	public MailsHibernateDAOImpl() {
	
	}

	@Override
	public void deleteMail(int id) {
		// ziskání vybrané hibernate session provedeni smazani
		sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().get(Mail.class, id));
	}

	@Override
	public int addMail(Mail mail) {
		try {
			// ziskání vybrané hibernate session
			Session session = sessionFactory.getCurrentSession();

			// dotaz
			Member member = session.get(Member.class, mail.getId_member());
			member.add(mail);

			return (int) session.save(mail);
		} catch (Exception e) {
			throw new RuntimeException("Chyba pøi ukladání mailu do DB - " + mail, e);
		}
	}

	@Override
	public void updateMail(Mail mail) {
		// ziskání vybrané hibernate session a ulozeni objektu
		sessionFactory.getCurrentSession().saveOrUpdate(mail);
	}

	@Override
	public Map<Integer, MailsMember> getAllMailMembers() {
		Map<Integer, MailsMember> map = new HashMap<>();

		try {

			// ziskání vybrané hibernate session a provedení dotazu
			@SuppressWarnings("unchecked")
			List<Member> list = sessionFactory.getCurrentSession()
					.createQuery("select i from Member i join fetch i.cshRegNumber " + "join fetch i.rodneCislo "
							+ "join fetch i.mails " + "join fetch i.trvaleBydliste")
					.list();
			List<Member> members = list;
			for (Member member : members) {
				if (!member.getMails().isEmpty()) {
					MailsMember mails = new MailsMember(member.getId());
					mails.setMails(member.getMails());
					map.put(member.getId(), mails);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Chyba pøi vyètení z DB listu Members s jejimi maili.", e);
		}
		return map;
	}

	@Override
	public MailsMember getMailsMember(int id_member) {
		MailsMember mailsMember = new MailsMember(id_member);

		try {

			// ziskání vybrané hibernate sessiona a provedení dotazu, který vrací member a z
			// nìj vyètení listu mailu
			List<Mail> mails = sessionFactory.getCurrentSession().get(Member.class, id_member).getMails();

			// List<Phone> phones2 = phones.stream().collect(toList());
			mailsMember.setMails(mails);
		} catch (Exception e) {
			throw new RuntimeException("Chyba pri spojeni member s mail", e);
		}
		return mailsMember;
	}

	@Override
	public Mail getMail(int id) {
		// ziskání vybrané hibernate session a dotaz na entitu dle id
		return sessionFactory.getCurrentSession().get(Mail.class, id);
	}

}
