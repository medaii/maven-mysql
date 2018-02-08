package loko.DAO;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


import org.hibernate.Session;
import org.hibernate.SessionFactory;

import loko.DB.DBHibernateSqlExecutor;
import loko.core.Member;
import loko.core.Phone;
import loko.core.PhonesMeber;

/**
 * 
 * @author Erik Markovic
 *
 */

public class PhonesHibernateDAOImpl implements IFPhoneDAO {
	private DBHibernateSqlExecutor dbHibernateSqlExecutor ;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public PhonesHibernateDAOImpl(DBHibernateSqlExecutor dbHibernateSqlExecutor) {
		this.dbHibernateSqlExecutor = dbHibernateSqlExecutor;
	}

	/**
	 * 
	 * @param id
	 *          - id telefonu, který se má smazat
	 * @return - vrací poèet smazaných øádku nebo -1 pøi chybì
	 */
	public int deletePhone(int id) {
		Phone phone = new Phone();
		return dbHibernateSqlExecutor.deleteObject(id, phone);
	}

	/**
	 * 
	 * @param phone
	 *          - pøidání telefonu do DB
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
			LOGGER.warning("Chyba zápisu!");
			return 0;
		}
	}

	/**
	 * 
	 * @param phone
	 *          - objekt ktery má být nahrán do DB
	 * @param id
	 *          - id phone na DB
	 * @return - vrací poèet zmìnìných øádku nebo -1 pøi chybì
	 */
	public int updatePhone(Phone phone, int id) {
		System.out.println("id phone = " + phone.getId());
		phone.setId(id);
		return dbHibernateSqlExecutor.updateObject(phone);

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
																									+ "join fetch i.phones").list();
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
			LOGGER.warning("Chyba zápisu!");
		}
		return map;
	}

	/**
	 * pro vracení phone kokretní osobì
	 * 
	 * @param id_member
	 *          - id èlena pro kterého chceme vrátit telefon
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
