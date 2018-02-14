package loko.dao.hibernate.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import loko.dao.PhoneDAO;
import loko.db.executor.impl.DBHibernateSqlExecutorImpl;
import loko.entity.Member;
import loko.entity.Phone;
import loko.value.PhonesMeber;

/**
 * Implementace pro pøístup k datùm z tabulky clen_telefon pomoci Hibernate.
 * 
 * @author Erik Markovic
 *
 */

public class PhonesHibernateDAOImpl implements PhoneDAO {
	private DBHibernateSqlExecutorImpl dbHibernateSqlExecutor ;

	public PhonesHibernateDAOImpl(DBHibernateSqlExecutorImpl dbHibernateSqlExecutor) {
		this.dbHibernateSqlExecutor = dbHibernateSqlExecutor;
	}

	@Override
	public void deletePhone(int id) {
		dbHibernateSqlExecutor.deleteObject(id, Phone.class);
	}

	@Override
	public int addPhone(Phone phone) {
		SessionFactory factory = dbHibernateSqlExecutor.getSessionFactory();

		try (Session session = factory.getCurrentSession();) {
			// start a transaction
			session.beginTransaction();
			// dotaz
			// telefon musi byt pridan k entite member
			Member member = session.get(Member.class, phone.getId_member());
			member.add(phone);
			int id = (int) session.save(phone);
			// commit transaction
			session.getTransaction().commit();
			return id;

		} catch (Exception e) {
			//TODO pøidat zachycení vyjímky v GUI
			throw new RuntimeException("Chyba pøi vytvoøení nového zaznamu Phone " + phone, e);
		}
	}

	@Override
	public void updatePhone(Phone phone, int id) {
		dbHibernateSqlExecutor.updateObject(phone);
	}
	
	@Override
	public Map<Integer, PhonesMeber> getAllPhonesMembers() {
		Map<Integer, PhonesMeber> map = new HashMap<>();

		SessionFactory factory = dbHibernateSqlExecutor.getSessionFactory();
		try (Session session = factory.getCurrentSession();) {
			// start a transaction
			session.beginTransaction();
			// dotaz
			
			@SuppressWarnings("unchecked")
			List<Member> list = session.createQuery("select i from Member i join fetch i.cshRegNumber "
																									+ "join fetch i.rodneCislo "
																									+ "join fetch i.phones "
																									+ "join fetch i.trvaleBydliste").list();
			List<Member> members = list;
			for (Member member : members) {
				if(!member.getPhones().isEmpty()) {
					PhonesMeber phones = new PhonesMeber(member.getId());
					phones.setPhones(member.getPhones());
					map.put(member.getId(), phones);
				}
			}
			
			// commit transaction
			session.getTransaction().commit();

		} 
		catch (Exception e) {
			throw new RuntimeException("Chyba pøi vyètení telefoního seznamu daného èlena z DB", e);
		}
		return map;
	}

	@Override
	public PhonesMeber getPhonesMember(int id_member) {

		SessionFactory factory = dbHibernateSqlExecutor.getSessionFactory();
		PhonesMeber phonesMember = new PhonesMeber(id_member);

		try (Session session = factory.getCurrentSession();) {

			// Zacatek tranzakce
			session.beginTransaction();

			// dotaz
			Member member = session.get(Member.class, id_member);
			List<Phone> phones = member.getPhones();

			phonesMember.setPhones(phones);

			// commit tranzakce
			session.getTransaction().commit();
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Chyba pri spojeni member s phone");
		}
		return phonesMember;
	}

	@Override
	public Phone getPhone(int id) {
		Phone phone = (Phone) dbHibernateSqlExecutor.getObject(id, Phone.class);
		return phone;
	}

}
