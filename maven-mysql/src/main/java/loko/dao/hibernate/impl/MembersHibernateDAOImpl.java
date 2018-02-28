package loko.dao.hibernate.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.management.RuntimeErrorException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import loko.dao.MembersDAO;
import loko.entity.CshRegNumber;
import loko.entity.Member;
import loko.entity.RodneCislo;
import loko.entity.TrvaleBydliste;
import loko.value.MemberFull;

/**
 * Implementace pro pøístup k datùm z tabulky clen_seznam pomoci Hibernate.
 * 
 * @author Erik Markoviè
 *
 */
@Repository
public class MembersHibernateDAOImpl implements MembersDAO {
	@Autowired
	private SessionFactory sessionFactory;

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	// vytvoøení konstruktoru
	public MembersHibernateDAOImpl() {
	}

	@Override
	public void deleteMember(int id) {
		// vyuziti vztahu mezi tabulkami a cascade typ ALL
		// smazáním radku v tabulce clen_seznam se smažou ostatní zaznamy v DB, které
		// jsou spojeny primarním klièem member

		// ziskání vybrané hibernate session
		Session session = sessionFactory.getCurrentSession();

		// dotaz
		Member member = session.get(Member.class, id);
		session.delete(member);
	}

	@Override
	public int addMemberFull(Member member, RodneCislo rodneCislo, TrvaleBydliste trvaleBydliste,
			CshRegNumber cshRegNumber) {

		// ziskání vybrané hibernate session
		Session session = sessionFactory.getCurrentSession();

		// vytvoreni member v DB
		session.save(member); // cascade nastaveny na all, ale moznost, ze nebude zadane ani jedno z
													// nasledujicich udaju

		// vytvoreni rodného èísla
		rodneCislo.setMember(member);
		session.save(rodneCislo);

		// vytvoreni registraciního èísla
		cshRegNumber.setMember(member);
		session.save(cshRegNumber);

		// vytvoreni trvalého bydlištì
		// pridani do member
		trvaleBydliste.setMember(member);
		session.save(trvaleBydliste);

		return member.getId(); // vraci nove id pro otevreni noveho okna k editaci
	}

	@Override
	public void updateMember(Member tempMember) {
		// ziskání vybrané hibernate session
		Session session = sessionFactory.getCurrentSession();

		// dotaz
		Member member = session.get(Member.class, tempMember.getId());
		member.setMember(tempMember);

		session.saveOrUpdate(member);

	}

	@Override
	public void updateMemberFull(Member tempMember, RodneCislo tempRodneCislo, TrvaleBydliste tempTrvaleBydliste,
			CshRegNumber tempCshRegNumber) {

		try {
			// ziskání vybrané hibernate session
			Session session = sessionFactory.getCurrentSession();

			// zmìna entity Member
			// session.saveOrUpdate(tempMember);

			// commit transaction
			// session.getTransaction().commit();

			// naèteni entity Member
			Member member = session.get(Member.class, tempMember.getId());
			member.setMember(tempMember);

			// nastaveni rodneho èísla
			RodneCislo rodneCislo = member.getRodneCislo();
			if (rodneCislo == null) {
				member.setRodneCislo(tempRodneCislo);
				rodneCislo = tempRodneCislo;
			} else {
				rodneCislo.setRodne_cislo(tempRodneCislo.getRodne_cislo());
			}

			// nastavení trvalého bydlištì
			TrvaleBydliste trvaleBydliste = member.getTrvaleBydliste();
			if (trvaleBydliste == null) {
				member.setTrvaleBydliste(tempTrvaleBydliste);
				trvaleBydliste = tempTrvaleBydliste;
			} else {
				trvaleBydliste.setAdresa(tempTrvaleBydliste.getAdresa());
			}

			// nastavení CSH registrace
			CshRegNumber cshRegNumber = member.getCshRegNumber();
			if (cshRegNumber == null) {
				member.setCshRegNumber(tempCshRegNumber);
				;
				cshRegNumber = tempCshRegNumber;
			} else {
				cshRegNumber.setRegCislo(tempCshRegNumber.getRegCislo());
			}

		} catch (

		RuntimeErrorException e) {
			LOGGER.warning("Chyba zápisu Rodného èísla, trvalého bydlištì a èísla registrace do DB!");
			throw new RuntimeException("Chyba zápisu Rodného èísla, trvalého bydlištì a èísla registrace do DB!"
					+ tempRodneCislo + tempTrvaleBydliste + tempCshRegNumber, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Member> getAllMember() {
		// list ktery naplnime z DB
		List<Member> list = new ArrayList<>();

		try {
			// ziskání vybrané hibernate session
			Session session = sessionFactory.getCurrentSession();
			// dotaz

			list = session.createQuery("select i from Member i join fetch i.cshRegNumber " + "join fetch i.rodneCislo "
					+ "join fetch i.phones " + "join fetch i.trvaleBydliste").list();

		} catch (Exception e) {
			throw new RuntimeException("Chyba pøi dotzu pro list entit Member", e);
		}
		return list;
	}

	// TODO - pøi kategorii 6 se mìní active na false - zmìnit impl GUI
	@Override
	public List<Member> getAllMemberList(int kategorie) {
		List<Member> members;

		// vytvoøení intervalu dle kategorie
		Interval interval = getInterval(kategorie);

		try {
			// ziskání vybrané hibernate session
			Session session = sessionFactory.getCurrentSession();

			// dotaz
			String dotaz1 = "select distinct i from Member i " + "join fetch i.cshRegNumber " + "join fetch i.rodneCislo "

					+ "join fetch i.trvaleBydliste " + "where i.birthDay between :start and :end ";
			if (kategorie != 6) {
				dotaz1 += "AND i.active = 1 ";

			}
			@SuppressWarnings("unchecked")
			List<Member> list2 = session.createQuery(dotaz1 + "ORDER BY i.birthDay ASC")
					.setParameter("start", interval.getDateStart()).setParameter("end", interval.getDateEnd()).list();
			members = list2;
		} catch (Exception e) {
			throw new RuntimeException("Chyba vypisu èlenù.", e);
		}
		// vrací list members dle kategorie
		return members;
	}

	@Override
	public List<Member> searchAllMembers(String name, int kategorie) {
		List<Member> list = new ArrayList<>();

		// vytvoøení intervalu dle kategorie
		Interval interval = getInterval(kategorie);

		String word = "%" + name;
		word += "%";

		try {
			// ziskání vybrané hibernate session
			Session session = sessionFactory.getCurrentSession();

			// dotaz

			String dotaz1 = "select distinct i from Member i join fetch i.cshRegNumber " + "join fetch i.rodneCislo "
					+ "join fetch i.trvaleBydliste " + "where i.birthDay between :start and :end "
					+ "and (i.firstName like :word or i.lastName like :word) ";

			// jestli neaktivni zobrazit
			if (kategorie != 6) {
				dotaz1 += "AND i.active = 1 ";

			}

			@SuppressWarnings("unchecked")
			List<Member> members = session.createQuery(dotaz1 + "ORDER BY i.birthDay ASC")
					.setParameter("start", interval.getDateStart()).setParameter("end", interval.getDateEnd())
					.setParameter("word", word).list();
			list = members;

		} catch (Exception e) {
			throw new RuntimeException("Chyba ètení z DB list entit Member." + e);
		}
		return list;
	}

	@Override
	public Member getMember(int id) {
		// ziskání vybrané hibernate session
		Session session = sessionFactory.getCurrentSession();

		return session.get(Member.class, id);
	}

	@Override
	public MemberFull getMemberFull(int id) {

		try {
			// ziskání vybrané hibernate session
			Session session = sessionFactory.getCurrentSession();

			// dotaz
			Member member = session.get(Member.class, id);
			RodneCislo rodneCislo = member.getRodneCislo();
			TrvaleBydliste trvaleBydliste = member.getTrvaleBydliste();
			CshRegNumber cshRegNumber = member.getCshRegNumber();
			
			// kontrola praznych objektu
			if (rodneCislo == null) {
				rodneCislo = new RodneCislo();
			}
			if (trvaleBydliste == null) {
				trvaleBydliste = new TrvaleBydliste();
			}
			if (cshRegNumber == null) {
				cshRegNumber = new CshRegNumber();
			}

			return new MemberFull(member, rodneCislo, cshRegNumber, trvaleBydliste);

		} catch (Exception e) {
			throw new RuntimeException("Chyba pøi práci s databází a vyèítaní MemberFull z id member - " + id, e);
		}
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

		public Interval(Date dateStart, Date dateEnd) {
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
