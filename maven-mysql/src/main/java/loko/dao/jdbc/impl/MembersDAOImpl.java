package loko.dao.jdbc.impl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;


import loko.dao.DAOFactory;
import loko.dao.MailsDAO;
import loko.dao.MembersDAO;
import loko.dao.PhoneDAO;
import loko.db.executor.impl.DBSqlExecutor;
import loko.entity.CshRegNumber;
import loko.entity.Mail;
import loko.entity.Member;
import loko.entity.Phone;
import loko.entity.RodneCislo;
import loko.entity.TrvaleBydliste;
import loko.value.MailsMember;
import loko.value.MemberFull;
import loko.value.PhonesMeber;

/**
 * 
 * @author Erik Markoviè
 *
 */

public class MembersDAOImpl implements MembersDAO {
	private DBSqlExecutor sqlExecutor;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	// vytvoøení konstruktoru
	public MembersDAOImpl(DBSqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}

	@Override
	public void deleteMember(int id) {
		// vytvoreni pøistupu k mailùm a telefonum
		MailsDAO mailsDao = DAOFactory.createDAO(MailsDAO.class);
		PhoneDAO phoneDAO = DAOFactory.createDAO(PhoneDAO.class);
		MailsMember mails = mailsDao.getMailsMember(id);
		PhonesMeber phones = phoneDAO.getPhonesMember(id);

		if (mails != null) {
			for (Mail mail : mails.getMails()) {
				try {
					mailsDao.deleteMail(mail.getId());
					LOGGER.info("Mail id - " + mail.getId() + " smazán.");
				} catch (Exception e) {
					String message = "CHYBA. Mail id - " + mail.getId() + " není smazán.";
					LOGGER.warning(message);
					throw new RuntimeException(message, e);
				}
			}
		}
		if (phones != null) {
			for (Phone phone : phones.getPhones()) {
				try {
					phoneDAO.deletePhone(phone.getId());
					LOGGER.info("Telefon id - " + phone.getId() + " smazán.");
				} catch (Exception e) {
					String message = "CHYBA. Telefon id - " + phone.getId() + " není smazán.";
					LOGGER.warning(message);
					throw new RuntimeException(message, e);
				}
			}
		}
		// smazání trvalého bydlištì
		String dotaz = "DELETE FROM clen_trvala_adresa " + "WHERE id_osoby = ?";
		String zprava;
		try {
			sqlExecutor.deleteRow(dotaz, id);
			zprava = "Trvale byslištì smazáno. id èlena:" + id;
		} catch (Exception e) {
			zprava = "Trvalé bydlištì nesmazáno id èlena:" + id;
			throw new RuntimeException(zprava, e);
		}
		LOGGER.info(zprava);

		// smazaní rodného èísla
		dotaz = "DELETE FROM clen_rodne_cislo " + "WHERE id_osoby = ?";
		try {
			sqlExecutor.deleteRow(dotaz, id);
			zprava = "Rodné èíslo smazáno. id èlena:" + id;
		} catch (Exception e) {
			zprava = "Rodné èíslo bydlištì nesmazáno id èlena:" + id;
			throw new RuntimeException(zprava, e);
		}
		LOGGER.warning(zprava);

		// smazaní èísla registrace
		dotaz = "DELETE FROM cshRegC " + "WHERE id_osoby = ?";
		try {
			sqlExecutor.deleteRow(dotaz, id);
			zprava = "Èíslo registrace smazáno. id èlena:" + id;
		} catch (Exception e) {
			zprava = "Èíslo registrace nesmazáno. id èlena:" + id;
			throw new RuntimeException(zprava, e);
		}
		LOGGER.warning(zprava);

		// smazaní èlena ze seznamu
		dotaz = "DELETE FROM clen_seznam " + "WHERE id = ?";
		sqlExecutor.deleteRow(dotaz, id);
	}

	@Override
	public int addMemberFull(Member member, RodneCislo rodneCislo, TrvaleBydliste trvaleBydliste, CshRegNumber cshRegNumber) {
		String dotaz = "insert into clen_seznam"
				+ " (kjmeno, pjmeno, datum_narozeni, poznamka, aktivni, id_odd_kategorie, zacal)"
				+ " values (?, ?, ?, ?, ?, ?, ?)";

		String[] hodnoty = { member.getFirstName(), member.getLastName(), String.valueOf(member.getBirthDay()),
				member.getNote(), String.valueOf(member.getActive()), String.valueOf(member.getId_odd_kategorie()),
				String.valueOf(member.getEnterDate()) };
		int id = sqlExecutor.insertDotaz(dotaz, hodnoty);

		if (id > 0) {
			dotaz = "insert into clen_rodne_cislo" + " (id_osoby, rodne_cislo)" + " values (?, ?)";
			String[] hodnotyRC = { String.valueOf(id), rodneCislo.getRodne_cislo() };
			int idRC = sqlExecutor.insertDotaz(dotaz, hodnotyRC);
			LOGGER.info("Vytvoøeno nové rodné èíslo id:" + idRC + "k èlenovy id:" + id);
			dotaz = "insert into clen_trvala_adresa" + " (id_osoby, adresa)" + " values (?, ?)";
			String[] hodnotyTR = { String.valueOf(id), trvaleBydliste.getAdresa() };
			int idTR = sqlExecutor.insertDotaz(dotaz, hodnotyTR);
			LOGGER.info("Vytvoøeno nové trvalé bydlištì id:" + idTR + "k èlenovy id:" + id);

			dotaz = "insert into cshRegC" + " (id_osoby, regCislo)" + " values (?, ?)";
			String[] hodnotyReg = { String.valueOf(id),cshRegNumber.getRegCislo() };
			int idReg = sqlExecutor.insertDotaz(dotaz, hodnotyReg);
			LOGGER.info("Vytvoøeno nové èíslo registrace ÈSH id:" + idReg + "k èlenovy id:" + id);
		}
		else {
			throw new RuntimeException("Chyba pøi zapisu nového zaznamu Member " + member);
		}
		return id;
	}

	@Override
	public void updateMember(Member member) {
		String dotaz = "update clen_seznam" + " set kjmeno = ?, pjmeno = ?, datum_narozeni = ? , poznamka = ?, aktivni = ?"
				+ ", id_odd_kategorie = ? , zacal = ? where id = ?";
		String[] hodnoty = { member.getFirstName(), member.getLastName(), String.valueOf(member.getBirthDay()),
				member.getNote(), String.valueOf(member.getActive()), String.valueOf(member.getId_odd_kategorie()),
				String.valueOf(member.getEnterDate()), String.valueOf(member.getId()) };
		sqlExecutor.setDotaz(dotaz, hodnoty);
	}


	@Override
	public void updateMemberFull(Member tempMember, RodneCislo tempRodneCislo, TrvaleBydliste tempTrvaleBydliste,
			CshRegNumber tempCshRegNumber) {
		String dotaz = "update clen_seznam" + " set kjmeno = ?, pjmeno = ?, datum_narozeni = ? , poznamka = ?, aktivni = ?"
				+ ", id_odd_kategorie = ? , zacal = ? where id = ?";
		String[] hodnoty = { tempMember.getFirstName(), tempMember.getLastName(), String.valueOf(tempMember.getBirthDay()),
				tempMember.getNote(), String.valueOf(tempMember.getActive()), String.valueOf(tempMember.getId_odd_kategorie()),
				String.valueOf(tempMember.getEnterDate()), String.valueOf(tempMember.getId()) };
		try {
			sqlExecutor.setDotaz(dotaz, hodnoty);
			// zapis rodneho cisla
			try {
				dotaz = "update clen_rodne_cislo" + " set rodne_cislo = ?" + " where id_osoby = ?";
				String[] hodnotyRC = { tempRodneCislo.getRodne_cislo(), String.valueOf(tempMember.getId()) };
				sqlExecutor.setDotaz(dotaz, hodnotyRC);
				// zapis trvaleho bydliste
				try {
					dotaz = "update clen_trvala_adresa" + " set adresa = ?" + " where id_osoby = ?";
					String[] hodnotyTR = { tempTrvaleBydliste.getAdresa(), String.valueOf(tempMember.getId()) };
					sqlExecutor.setDotaz(dotaz, hodnotyTR);
					// zapis cisla registrace
					try {
						dotaz = "update cshRegC" + " set regCislo = ?" + " where id_osoby = ?";
						String[] hodnotyRegC = { tempCshRegNumber.getRegCislo(), String.valueOf(tempMember.getId()) };
						sqlExecutor.setDotaz(dotaz, hodnotyRegC);
					} catch (RuntimeException e) {
						throw new RuntimeException("Chyba pøi vykonavaní SQL set pøíkazu u objektu." + CshRegNumber.class.getName(),
								e);
					}
				} catch (RuntimeException e) {
					throw new RuntimeException("Chyba pøi vykonavaní SQL set pøíkazu u objektu." + TrvaleBydliste.class.getName(),
							e);
				}
			} catch (RuntimeException e) {
				throw new RuntimeException("Chyba pøi vykonavaní SQL set pøíkazu u objektu." + RodneCislo.class.getName(), e);
			}
		} catch (RuntimeException e) {
			throw new RuntimeException("Chyba pøi vykonavaní SQL set pøíkazu u objektu." + Member.class.getName(), e);
		}
	}

	/**
	 * Vrací list tabulky clen seznam z DB
	 * 
	 * @return
	 */
	public List<Member> getAllMember() {
		List<Member> list = new ArrayList<>();
		ArrayList<String[]> r = new ArrayList<>();// databaze vráti výsledek do listu

		String dotaz = "Select * from clen_seznam ORDER BY clen_seznam.datum_narozeni ASC";

		sqlExecutor.getData(dotaz, r);

		for (String[] a : r) {
			Member temp = convertRowToMember(a);
			if (temp == null) {
				throw new RuntimeException("Chyba pøi dotazu na databázi " + dotaz);
			} else {
				list.add(temp);
			}
		}

		return list;
	}

	@Override
	public List<Member> getAllMemberList(int kategorie) {
		List<Member> members = new ArrayList<>();
		
		ArrayList<String[]> r = new ArrayList<>();// databaze vráti výsledek do listu

		String dotaz;
		String where = "";
		
		Interval interval = getInterval(kategorie);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		where = "WHERE datum_narozeni < '" + sdf.format(interval.getDateStart()) 
					+ "' AND datum_narozeni > '" + sdf.format(interval.getDateEnd()) + "' ";
		if (kategorie != 6) {
			where += "AND aktivni= 1 ";
		}
		
		dotaz = "Select * from clen_seznam " + where +"ORDER BY clen_seznam.datum_narozeni ASC";
			
		sqlExecutor.getData(dotaz, r);

		for (String[] a : r) {
			Member member = convertRowToMember(a);
			members.add(member);			
		}
		return members;
	}

	public List<Member> searchAllMembers(String name, int kategorie) {
		List<Member> list = new ArrayList<>();
		String word = "%" + name;
		word += "%";

		String where = "";
		
		Interval interval = getInterval(kategorie);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		where = "WHERE datum_narozeni < '" + sdf.format(interval.getDateStart()) 
					+ "' AND datum_narozeni > '" + sdf.format(interval.getDateEnd()) + "' "
					+	"AND (kjmeno like '" + word + "' OR pjmeno like '" + word + "') ";
		if (kategorie != 6) {
			where += "AND aktivni= 1 ";
		}
		
		ArrayList<String[]> r = new ArrayList<>();// databaze vráti výsledek do listu
		String dotaz;
		
		dotaz = "Select * from clen_seznam " + where +"ORDER BY clen_seznam.datum_narozeni ASC";
		
		sqlExecutor.getData(dotaz, r);

		for (String[] a : r) {
			Member member = convertRowToMember(a);
			list.add(member);
		}

		return list;
	}

	/**
	 * vrací vybraného èlena
	 * 
	 * @param id
	 * @return
	 */
	public Member getMember(int id) {
		Member member = null;
		ArrayList<String[]> r = new ArrayList<>();// databaze vráti výsledek do listu
		String dotaz = "Select * from clen_seznam" + " where id = " + id;
		sqlExecutor.getData(dotaz, r);
		for (String[] a : r) {
			member = convertRowToMember(a);
		}

		return member;
	}
	
	@Override
	public MemberFull getMemberFull(int id) {
		MemberFull memberFull = null;
		ArrayList<String[]> r = new ArrayList<>();// databaze vráti výsledek do listu
		String dotaz = "SELECT clen_seznam.id, kjmeno as firstName, pjmeno as lastName, datum_narozeni, poznamka as note,"
				+ " aktivni as active, id_odd_kategorie, zacal, clen_rodne_cislo.rodne_cislo,"
				+ " clen_trvala_adresa.adresa, cshRegC.regCislo FROM `clen_seznam`,`clen_rodne_cislo`,"
				+ "`clen_trvala_adresa`,`cshRegC` " + "WHERE clen_seznam.id = clen_rodne_cislo.id_osoby "
				+ "AND clen_seznam.id = clen_trvala_adresa.id_osoby AND clen_seznam.id = cshRegC.id_osoby "
				+ "AND clen_seznam.id = " + id;
		String[] hodnoty = {};
		int countRow = sqlExecutor.getCountRow(dotaz, hodnoty);
		// když je poèet øádku rovný 0, tak nastala chyba pøi dotazu z DB, nejspíš chybí
		// záznam o pøidaných tabulkách
		if (countRow < 1) {
			if (!opravaTabulkyMember(id)) {
				LOGGER.severe("CHYBA TABULKY v DB");
			}
		}
		sqlExecutor.getData(dotaz, r);

		for (String[] a : r) {
			memberFull = convertRowToMemberFull(a);
		}

		return memberFull;
	}

	/**
	 * Namapuje data z tabulky do entity Member
	 * 
	 * @param temp
	 * @return jednoduchy objekt member
	 */
	private Member convertRowToMember(String[] temp) {
		Member tempMember;
		try {
			if (temp.length == 8 && (temp[0] != "id")) {
				int id = Integer.parseInt(temp[0]);
				String firstName = temp[1];
				String lastName = temp[2];
				Date birthDay = Date.valueOf(temp[3]);
				String note = temp[4];
				int active = Integer.parseInt(temp[5]);
				int id_odd_kategorie = Integer.parseInt(temp[6]);
				Date enterDate = Date.valueOf(temp[7]);

				tempMember = new Member(id, firstName, lastName, birthDay, note, active, id_odd_kategorie, enterDate);
			} else {
				tempMember = null;
			}
		} catch (Exception e) {
			tempMember = null;
		}
		return tempMember;
	}

	private MemberFull convertRowToMemberFull(String[] temp) {
		MemberFull tempMember;
		try {
			// overeni, ze je spravny dotaz z sql
			if (temp.length == 11 && (temp[0] != "id")) {
				int id = Integer.parseInt(temp[0]);
				String firstName = temp[1];
				String lastName = temp[2];
				Date birthDay = Date.valueOf(temp[3]);
				String note = temp[4];
				int active = Integer.parseInt(temp[5]);
				int id_odd_kategorie = Integer.parseInt(temp[6]);
				Date enterDate = Date.valueOf(temp[7]);

				String rodneCislo = temp[8];
				String trvaleBydliste = temp[9];
				String chfRegistrace = temp[10];

				tempMember = new MemberFull(id, firstName, lastName, birthDay, note, active, id_odd_kategorie, enterDate,
						rodneCislo, trvaleBydliste, chfRegistrace);
			} else {
				tempMember = null;
			}
		} catch (Exception e) {
			tempMember = null;
		}
		return tempMember;
	}

	private Boolean opravaTabulkyMember(int id) {
		LOGGER.warning("Chyba dotazu, oprava dat v DB");
		// kontrola trvaleho bydliste
		String dotaz2 = "SELECT * FROM `clen_trvala_adresa` WHERE id_osoby=?";
		String[] hodnoty2 = { String.valueOf(id) };
		if (sqlExecutor.getCountRow(dotaz2, hodnoty2) < 1) {
			String dotaz21 = "insert into clen_trvala_adresa" + " (id_osoby, adresa)" + " values (?, ?)";
			String[] hodnoty21 = { String.valueOf(id), "" };
			sqlExecutor.insertDotaz(dotaz21, hodnoty21);
		}
		// kontrola csh registrace
		String dotaz3 = "SELECT * FROM `cshRegC` WHERE id_osoby=?";
		String[] hodnoty3 = { String.valueOf(id) };
		if (sqlExecutor.getCountRow(dotaz3, hodnoty3) < 1) {
			String dotaz31 = "insert into cshRegC" + " (id_osoby, regCislo)" + " values (?, ?)";
			String[] hodnoty31 = { String.valueOf(id), "" };
			sqlExecutor.insertDotaz(dotaz31, hodnoty31);
		}
		// kontrola rodneho cisla
		String dotaz4 = "SELECT * FROM `clen_rodne_cislo` WHERE id_osoby=?";
		String[] hodnoty4 = { String.valueOf(id) };
		if (sqlExecutor.getCountRow(dotaz4, hodnoty4) < 1) {
			String dotaz41 = "insert into clen_rodne_cislo" + " (id_osoby, rodne_cislo)" + " values (?, ?)";
			String[] hodnoty41 = { String.valueOf(id), "" };
			sqlExecutor.insertDotaz(dotaz41, hodnoty41);
		}

		return true;
	}
	
	
	private Interval getInterval(int kategorie) {
	// date pro filtraci
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, Calendar.JANUARY);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			int nowYear = cal.get(Calendar.YEAR);
			int nowMonth = cal.get(Calendar.MONTH);

			java.util.Date dateEnd = new java.util.Date();
			cal.set(Calendar.YEAR, nowYear - 1000);
			java.util.Date dateStart = cal.getTime();
			cal.set(Calendar.YEAR, nowYear + 1000);

			if (kategorie > 0) {
				// entity nastavene date z java.until proto tento zpusob
				// vytvoreni formatu

				switch (kategorie) {
				case 1:
					if (nowMonth > 6) {
						cal.set(Calendar.YEAR, nowYear - 18);
						dateEnd = cal.getTime();
					} else {
						cal.set(Calendar.YEAR, nowYear - 19);
						dateEnd = cal.getTime();
					}
					break;

				case 2:
					if (nowMonth > 6) {
						cal.set(Calendar.YEAR, nowYear - 18);
						dateStart = cal.getTime();
					} else {
						cal.set(Calendar.YEAR, nowYear - 19);
						dateStart = cal.getTime();
					}
					break;
				case 3:
					if (nowMonth > 6) {
						cal.set(Calendar.YEAR, nowYear - 18);

					} else {
						cal.set(Calendar.YEAR, nowYear - 19);
					}
					dateStart = cal.getTime();
					if (nowMonth > 6) {
						cal.set(Calendar.YEAR, nowYear - 14);
					} else {
						cal.set(Calendar.YEAR, nowYear - 15);
					}
					dateEnd = cal.getTime();

					break;
				case 4:
					if (nowMonth > 6) {
						cal.set(Calendar.YEAR, nowYear - 14);
					} else {
						cal.set(Calendar.YEAR, nowYear - 15);
					}
					dateStart = cal.getTime();
					if (nowMonth > 6) {
						cal.set(Calendar.YEAR, nowYear - 10);
					} else {
						cal.set(Calendar.YEAR, nowYear - 11);
					}
					dateEnd = cal.getTime();
					break;
				case 5:
					if (nowMonth > 6) {
						cal.set(Calendar.YEAR, nowYear - 10);
					} else {
						cal.set(Calendar.YEAR, nowYear - 11);
					}
					dateStart = cal.getTime();

					break;
				default:
					cal.set(Calendar.YEAR, nowYear - 1000);
					dateStart = cal.getTime();
					break;
				}
			}
		return new Interval(dateStart, dateEnd);
	}
	
	private class Interval {
		private java.util.Date dateStart;
		private java.util.Date dateEnd;
		
		public Interval(java.util.Date dateStart, java.util.Date dateEnd) {
			this.dateStart = dateStart;
			this.dateEnd = dateEnd;
		}

		public java.util.Date getDateStart() {
			return dateStart;
		}

		public java.util.Date getDateEnd() {
			return dateEnd;
		}
	}
}
