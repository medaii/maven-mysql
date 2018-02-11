package loko.dao.jdbc.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import loko.dao.PhoneDAO;
import loko.db.executor.impl.DBSqlExecutor;
import loko.entity.Phone;
import loko.value.PhonesMeber;
/**
 * 
 * @author Erik Markovic
 *
 */

public class PhonesDAOImpl implements PhoneDAO {
	private DBSqlExecutor sqlExecutor = null;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public PhonesDAOImpl(DBSqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}
	
	/**
	 * 
	 * @param id
	 *            - id telefonu, který se má smazat
	 * @return - vrací poèet smazaných øádku nebo -1 pøi chybì
	 */
	public void deletePhone(int id) {

		String dotaz = "DELETE FROM clen_mobil " + "WHERE id = ?";
		sqlExecutor.deleteRow(dotaz, id);
	}
	/**
	 * 
	 * @param phone - pøidání telefonu do DB
	 * @return
	 */
	public int addPhone(Phone phone) {
		String dotaz = "insert into clen_mobil" + " (id_osoby, nazev, telefon)" + " values (?, ?, ?)";
		String[] hodnoty = { String.valueOf(phone.getId_member()), phone.getName(), phone.getPhone()};

		return sqlExecutor.insertDotaz(dotaz, hodnoty);
	}

	/**
	 * 
	 * @param phone
	 *            - objekt ktery má být nahrán do DB
	 * @param id
	 *            - id phone na DB
	 * @return - vrací poèet zmìnìných øádku nebo -1 pøi chybì
	 */
	public void updatePhone(Phone phone, int id) {

		String dotaz = "update clen_mobil" + " set id_osoby = ?, nazev = ?, telefon = ?" + " where id = ?";
		String[] hodnoty = { String.valueOf(phone.getId_member()), phone.getName(), phone.getPhone(), String.valueOf(id) };
		sqlExecutor.setDotaz(dotaz, hodnoty);
	}

	/**
	 * vytvoreni listu
	 * 
	 * @phones = vrati telefony daneho clena
	 * 
	 */
	public Map<Integer, PhonesMeber> getAllPhonesMembers() {
		Map<Integer, PhonesMeber> map = new HashMap<>();
		
		ArrayList<String[]> r = new ArrayList<>();// databaze vráti výsledek do listu

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
	 * pro vracení phone kokretní osobì
	 * 
	 * @param id_member
	 *            - id èlena pro kterého chceme vrátit telefon
	 * @return
	 */
	public PhonesMeber getPhonesMember(int id_member) {
		PhonesMeber phonesMember = new PhonesMeber(id_member);
		ArrayList<String[]> r = new ArrayList<>();// databaze vráti výsledek do listu

		String dotaz = "SELECT id,id_osoby as id_member, nazev as name, telefon  as phone FROM clen_mobil WHERE id_osoby = "
				+ id_member + " ORDER BY clen_mobil.id_osoby ASC ";

		sqlExecutor.getData(dotaz, r);
		for (String[] a : r) {
			Phone temp = convertRowToPhone(a);
			phonesMember.setPhones(temp);
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
		Phone phone = null;
		ArrayList<String[]> r = new ArrayList<>();// databaze vráti výsledek do listu

		String dotaz = "SELECT id,id_osoby as id_member, nazev as name, telefon  as phone FROM clen_mobil WHERE id = " + id
				+ " ORDER BY clen_mobil.id_osoby ASC ";

		sqlExecutor.getData(dotaz, r);
		for (String[] a : r) {
			phone = convertRowToPhone(a);
		}

		return phone;
	}

	/**
	 * pøevedení dat z DB do typu Phobe
	 * 
	 * @param temp
	 *            - data z DB
	 * @return vrací pøetypované data do Phone
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
			LOGGER.warning("Chyba pøi pøevodu øádky z DB do typu Phone");
			tempPhone = null;
		}

		return tempPhone;
	}
}

