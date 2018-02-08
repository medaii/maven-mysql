package loko.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import loko.DB.DBHibernateSqlExecutor;
import loko.DB.DBSqlExecutor;
import loko.core.Member;
import loko.core.Phone;
import loko.core.PhonesMeber;
/**
 * 
 * @author Erik Markovic
 *
 */

public class PhonesHibernateDAOImpl implements IFPhoneDAO {
	private DBSqlExecutor sqlExecutor = null;
	private DBHibernateSqlExecutor dbHibernateSqlExecutor = new DBHibernateSqlExecutor();
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public PhonesHibernateDAOImpl(DBSqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
		
	}
	
	public PhonesHibernateDAOImpl(DBHibernateSqlExecutor dbHibernateSqlExecutor) {
		this.dbHibernateSqlExecutor = dbHibernateSqlExecutor;
	}

	/**
	 * 
	 * @param id
	 *            - id telefonu, kter� se m� smazat
	 * @return - vrac� po�et smazan�ch ��dku nebo -1 p�i chyb�
	 */
	public int deletePhone(int id) {
		Phone phone = new Phone();
		return dbHibernateSqlExecutor.deleteObject(id, phone);	
	}
	/**
	 * 
	 * @param phone - p�id�n� telefonu do DB
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
	 *            - objekt ktery m� b�t nahr�n do DB
	 * @param id
	 *            - id phone na DB
	 * @return - vrac� po�et zm�n�n�ch ��dku nebo -1 p�i chyb�
	 */
	public int updatePhone(Phone phone, int id) {	
		System.out.println("id phone = " + phone.getId());
		phone.setId(id);
		return dbHibernateSqlExecutor.updateObject(phone);
		
	}

	/**
	 * vytvoreni listu
	 * 
	 * @phones = vrati telefony daneho clena
	 * 
	 */

	public Map<Integer, PhonesMeber> getAllPhonesMembers() {
		Map<Integer, PhonesMeber> map = new HashMap<>();
		
		ArrayList<String[]> r = new ArrayList<>();// databaze vr�ti v�sledek do listu

		String dotaz = "SELECT id,id_osoby as id_member, nazev as name, telefon  as phone FROM clen_mobil ORDER BY clen_mobil.id_osoby ASC ";

		sqlExecutor.getData(dotaz, r);
		int id_meber = -1;
		PhonesMeber phones = null;
		for (String[] a : r) {
			Phone temp = convertRowToPhone(a);
			if (temp == null) {
				LOGGER.warning("Chyba pole!");
			} else {
				if (id_meber == temp.getId_member()) {
					phones.setPhones(temp);
				} else {
					if(phones !=null) {
						map.put(id_meber, phones);
					}
					id_meber = temp.getId_member();
					phones = new PhonesMeber(id_meber, temp);
				}
			}
		}
		map.put(id_meber, phones);
		
		return map;
	}
	
	/**
	 * pro vracen� phone kokretn� osob�
	 * 
	 * @param id_member
	 *            - id �lena pro kter�ho chceme vr�tit telefon
	 * @return
	 */	
	public PhonesMeber getPhonesMember(int id_member) {
		
		SessionFactory factory = dbHibernateSqlExecutor.getSessionFactory();
		PhonesMeber phonesMember = new PhonesMeber(id_member);
		
		try(Session session = factory.getCurrentSession();) {

			// Zacatek tranzakce
			session.beginTransaction();

			// dotaz
			Member member = session.get(Member.class, id_member);
			List<Phone> phones = member.getPhones(); 
			
			//List<Phone> phones2 = phones.stream().collect(toList());
			phonesMember.setPhones(phones);
			
			// commit tranzakce
			session.getTransaction().commit();
			
			// ulozeni tel. cisel do PhonesMember
			//System.out.println(phones2);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Chyba pri spojeni member s phone");
		}
		
	return phonesMember;
	}

	/**
	 * 
	 * @param id
	 *            - telefonu na DB
	 * @return vraci objekt phone
	 */
	public Phone getPhone(int id) {
		Phone phone = (Phone)dbHibernateSqlExecutor.getObject(id, Phone.class);
		return phone;
	}

	/**
	 * p�eveden� dat z DB do typu Phobe
	 * 
	 * @param temp
	 *            - data z DB
	 * @return vrac� p�etypovan� data do Phone
	 */
	private Phone convertRowToPhone(String[] temp) {
		Phone tempPhone = null;
		if (temp.length == 4 && (temp[0] != "id")) {
			int id = Integer.parseInt(temp[0]);
			int id_member = Integer.parseInt(temp[1]);
			String name = temp[2];
			String phone = temp[3];

			tempPhone = new Phone(id, id_member, name, phone);

		} else {
			LOGGER.warning("Chyba p�i p�evodu ��dky z DB do typu Phone");
			tempPhone = null;
		}

		return tempPhone;
	}
}

