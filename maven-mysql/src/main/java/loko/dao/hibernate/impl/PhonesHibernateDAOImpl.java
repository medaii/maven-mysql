package loko.dao.hibernate.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import loko.dao.PhoneDAO;
import loko.entity.Member;
import loko.entity.Phone;
import loko.value.PhonesMeber;

/**
 * Implementace pro pøístup k datùm z tabulky clen_telefon pomoci Hibernate.
 * 
 * @author Erik Markovic
 *
 */
@Repository
public class PhonesHibernateDAOImpl implements PhoneDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public PhonesHibernateDAOImpl() {

	}

	@Override
	public void deletePhone(int id) {
		// ziskání vybrané hibernate session
		Session session = sessionFactory.getCurrentSession();

		// provedení smazání
		session.delete(session.get(Phone.class, id));
	}

	@Override
	public int addPhone(Phone phone) {

		try {
			// ziskání vybrané hibernate session
			Session session = sessionFactory.getCurrentSession();

			// dotaz
			// telefon musi byt pridan k entite member
			Member member = session.get(Member.class, phone.getId_member());
			member.add(phone);

			// vytvoøení entity
			int id = (int) session.save(phone);

			return id;

		} catch (Exception e) {
			throw new RuntimeException("Chyba pøi vytvoøení nového zaznamu Phone " + phone, e);
		}
	}

	@Override
	public void updatePhone(Phone phone) {
		// ziskání vybrané hibernate session a uložení
		sessionFactory.getCurrentSession().saveOrUpdate(phone);
	}

	@Override
	public Map<Integer, PhonesMeber> getAllPhonesMembers() {
		Map<Integer, PhonesMeber> map = new HashMap<>();

		try {
			// dotaz
			@SuppressWarnings("unchecked")
			List<Member> list = sessionFactory.getCurrentSession().createQuery("select i from Member i join fetch i.cshRegNumber "
					+ "join fetch i.rodneCislo " + "join fetch i.phones " + "join fetch i.trvaleBydliste").list();
			List<Member> members = list;
			for (Member member : members) {
				if (!member.getPhones().isEmpty()) {
					PhonesMeber phones = new PhonesMeber(member.getId());
					phones.setPhones(member.getPhones());
					map.put(member.getId(), phones);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Chyba pøi vyètení telefoního seznamu daného èlena z DB", e);
		}
		return map;
	}

	@Override
	public PhonesMeber getPhonesMember(int id_member) {
		PhonesMeber phonesMember = new PhonesMeber(id_member);
		try {
			Member member = sessionFactory.getCurrentSession().get(Member.class, id_member);
			phonesMember.setPhones(member.getPhones());
		} catch (Exception e) {
			throw new RuntimeException("Chyba pri spojeni member s phone");
		}
		return phonesMember;
	}

	@Override
	public Phone getPhone(int id) {
		return sessionFactory.getCurrentSession().get(Phone.class, id);
	}

}
