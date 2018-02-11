package loko.dao.hibernate.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


import org.hibernate.Session;
import org.hibernate.SessionFactory;

import loko.dao.PhoneDAO;
import loko.db.executor.DBHibernateSqlExecutor;
import loko.entity.Member;
import loko.entity.Phone;
import loko.value.PhonesMeber;

/**
 * 
 * @author Erik Markovic
 *
 */

public class PhonesHibernateDAOImpl implements PhoneDAO {
	private DBHibernateSqlExecutor dbHibernateSqlExecutor ;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public PhonesHibernateDAOImpl(DBHibernateSqlExecutor dbHibernateSqlExecutor) {
		this.dbHibernateSqlExecutor = dbHibernateSqlExecutor;
	}

	/**
	 * 
	 * @param id
	 *          - id telefonu, kter� se m� smazat
	 * @return - vrac� po�et smazan�ch ��dku nebo -1 p�i chyb�
	 */
	public void deletePhone(int id) {
		dbHibernateSqlExecutor.deleteObject(id, Phone.class);
	}

	/**
	 * 
	 * @param phone
	 *          - p�id�n� telefonu do DB
	 * @return
	 */
	public int addPhone(Phone phone) {
		SessionFactory factory = dbHibernateSqlExecutor.getSessionFactory();

		try (Session session = factory.getCurrentSession();) {
			// start a transaction
			session.beginTransaction();
			// dotaz
			Member member = session.get(Member.class, phone.getId_member());
			member.add(phone);
			int id = (int) session.save(phone);
			// commit transaction
			session.getTransaction().commit();
			return id;

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.warning("Chyba z�pisu!");
			return 0;
		}
	}

	/**
	 * 
	 * @param phone
	 *          - objekt ktery m� b�t nahr�n do DB
	 * @param id
	 *          - id phone na DB
	 * @return - vrac� po�et zm�n�n�ch ��dku nebo -1 p�i chyb�
	 */
	public void updatePhone(Phone phone, int id) {
		phone.setId(id);
		dbHibernateSqlExecutor.updateObject(phone);
	}

	/**
	 * vytvoreni listu
	 * 
	 * @phones = Vraci hash list klic je id clena a obsah je list telefonu
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, PhonesMeber> getAllPhonesMembers() {
		Map<Integer, PhonesMeber> map = new HashMap<>();

		SessionFactory factory = dbHibernateSqlExecutor.getSessionFactory();
		try (Session session = factory.getCurrentSession();) {
			// start a transaction
			session.beginTransaction();
			// dotaz
			
			List<Member> members = session.createQuery("select i from Member i join fetch i.cshRegNumber "
																									+ "join fetch i.rodneCislo "
																									+ "join fetch i.phones "
																									+ "join fetch i.trvaleBydliste").list();
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
			LOGGER.warning("Chyba z�pisu!");
		}
		return map;
	}

	/**
	 * pro vracen� phone kokretn� osob�
	 * 
	 * @param id_member
	 *          - id �lena pro kter�ho chceme vr�tit telefon
	 * @return
	 */
	public PhonesMeber getPhonesMember(int id_member) {

		SessionFactory factory = dbHibernateSqlExecutor.getSessionFactory();
		PhonesMeber phonesMember = new PhonesMeber(id_member);

		try (Session session = factory.getCurrentSession();) {

			// Zacatek tranzakce
			session.beginTransaction();

			// dotaz
			Member member = session.get(Member.class, id_member);
			List<Phone> phones = member.getPhones();

			// List<Phone> phones2 = phones.stream().collect(toList());
			phonesMember.setPhones(phones);

			// commit tranzakce
			session.getTransaction().commit();

			// ulozeni tel. cisel do PhonesMember
			// System.out.println(phones2);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Chyba pri spojeni member s phone");
		}

		return phonesMember;
	}

	/**
	 * 
	 * @param id
	 *          - telefonu na DB
	 * @return vraci objekt phone
	 */
	public Phone getPhone(int id) {
		Phone phone = (Phone) dbHibernateSqlExecutor.getObject(id, Phone.class);
		return phone;
	}

}
