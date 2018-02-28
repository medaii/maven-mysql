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
 * Iplementace pro p��stup k dat�m z tabulky clen_mail pomoci Hibernate.
 * 
 * @author Erik Markovi�
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
		// zisk�n� vybran� hibernate session provedeni smazani
		sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().get(Mail.class, id));
	}

	@Override
	public int addMail(Mail mail) {
		try {
			// zisk�n� vybran� hibernate session
			Session session = sessionFactory.getCurrentSession();

			// dotaz
			Member member = session.get(Member.class, mail.getId_member());
			member.add(mail);

			return (int) session.save(mail);
		} catch (Exception e) {
			throw new RuntimeException("Chyba p�i uklad�n� mailu do DB - " + mail, e);
		}
	}

	@Override
	public void updateMail(Mail mail) {
		// zisk�n� vybran� hibernate session a ulozeni objektu
		sessionFactory.getCurrentSession().saveOrUpdate(mail);
	}

	@Override
	public Map<Integer, MailsMember> getAllMailMembers() {
		Map<Integer, MailsMember> map = new HashMap<>();

		try {

			// zisk�n� vybran� hibernate session a proveden� dotazu
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
			throw new RuntimeException("Chyba p�i vy�ten� z DB listu Members s jejimi maili.", e);
		}
		return map;
	}

	@Override
	public MailsMember getMailsMember(int id_member) {
		MailsMember mailsMember = new MailsMember(id_member);

		try {

			// zisk�n� vybran� hibernate sessiona a proveden� dotazu, kter� vrac� member a z
			// n�j vy�ten� listu mailu
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
		// zisk�n� vybran� hibernate session a dotaz na entitu dle id
		return sessionFactory.getCurrentSession().get(Mail.class, id);
	}

}
