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
 * Implementace pro p��stup k dat�m z tabulky clen_seznam pomoci Hibernate.
 * 
 * @author Erik Markovi�
 *
 */
@Repository
public class MembersHibernateDAOImpl implements MembersDAO {
	@Autowired
	private SessionFactory sessionFactory;

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	// vytvo�en� konstruktoru
	public MembersHibernateDAOImpl() {
	}

	@Override
	public void deleteMember(int id) {
		// vyuziti vztahu mezi tabulkami a cascade typ ALL
		// smaz�n�m radku v tabulce clen_seznam se sma�ou ostatn� zaznamy v DB, kter�
		// jsou spojeny primarn�m kli�em member

		// zisk�n� vybran� hibernate session
		Session session = sessionFactory.getCurrentSession();

		// dotaz
		Member member = session.get(Member.class, id);
		session.delete(member);
	}

	@Override
	public int addMemberFull(Member member, RodneCislo rodneCislo, TrvaleBydliste trvaleBydliste,
			CshRegNumber cshRegNumber) {

		// zisk�n� vybran� hibernate session
		Session session = sessionFactory.getCurrentSession();

		// vytvoreni member v DB
		session.save(member); // cascade nastaveny na all, ale moznost, ze nebude zadane ani jedno z
													// nasledujicich udaju

		// vytvoreni rodn�ho ��sla
		rodneCislo.setMember(member);
		session.save(rodneCislo);

		// vytvoreni registracin�ho ��sla
		cshRegNumber.setMember(member);
		session.save(cshRegNumber);

		// vytvoreni trval�ho bydli�t�
		// pridani do member
		trvaleBydliste.setMember(member);
		session.save(trvaleBydliste);

		return member.getId(); // vraci nove id pro otevreni noveho okna k editaci
	}

	@Override
	public void updateMember(Member tempMember) {
		// zisk�n� vybran� hibernate session
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
			// zisk�n� vybran� hibernate session
			Session session = sessionFactory.getCurrentSession();

			// zm�na entity Member
			// session.saveOrUpdate(tempMember);

			// commit transaction
			// session.getTransaction().commit();

			// na�teni entity Member
			Member member = session.get(Member.class, tempMember.getId());
			member.setMember(tempMember);

			// nastaveni rodneho ��sla
			RodneCislo rodneCislo = member.getRodneCislo();
			if (rodneCislo == null) {
				member.setRodneCislo(tempRodneCislo);
				rodneCislo = tempRodneCislo;
			} else {
				rodneCislo.setRodne_cislo(tempRodneCislo.getRodne_cislo());
			}

			// nastaven� trval�ho bydli�t�
			TrvaleBydliste trvaleBydliste = member.getTrvaleBydliste();
			if (trvaleBydliste == null) {
				member.setTrvaleBydliste(tempTrvaleBydliste);
				trvaleBydliste = tempTrvaleBydliste;
			} else {
				trvaleBydliste.setAdresa(tempTrvaleBydliste.getAdresa());
			}

			// nastaven� CSH registrace
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
			LOGGER.warning("Chyba z�pisu Rodn�ho ��sla, trval�ho bydli�t� a ��sla registrace do DB!");
			throw new RuntimeException("Chyba z�pisu Rodn�ho ��sla, trval�ho bydli�t� a ��sla registrace do DB!"
					+ tempRodneCislo + tempTrvaleBydliste + tempCshRegNumber, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Member> getAllMember() {
		// list ktery naplnime z DB
		List<Member> list = new ArrayList<>();

		try {
			// zisk�n� vybran� hibernate session
			Session session = sessionFactory.getCurrentSession();
			// dotaz

			list = session.createQuery("select i from Member i join fetch i.cshRegNumber " + "join fetch i.rodneCislo "
					+ "join fetch i.phones " + "join fetch i.trvaleBydliste").list();

		} catch (Exception e) {
			throw new RuntimeException("Chyba p�i dotzu pro list entit Member", e);
		}
		return list;
	}

	// TODO - p�i kategorii 6 se m�n� active na false - zm�nit impl GUI
	@Override
	public List<Member> getAllMemberList(int kategorie) {
		List<Member> members;

		// vytvo�en� intervalu dle kategorie
		Interval interval = getInterval(kategorie);

		try {
			// zisk�n� vybran� hibernate session
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
			throw new RuntimeException("Chyba vypisu �len�.", e);
		}
		// vrac� list members dle kategorie
		return members;
	}

	@Override
	public List<Member> searchAllMembers(String name, int kategorie) {
		List<Member> list = new ArrayList<>();

		// vytvo�en� intervalu dle kategorie
		Interval interval = getInterval(kategorie);

		String word = "%" + name;
		word += "%";

		try {
			// zisk�n� vybran� hibernate session
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
			throw new RuntimeException("Chyba �ten� z DB list entit Member." + e);
		}
		return list;
	}

	@Override
	public Member getMember(int id) {
		// zisk�n� vybran� hibernate session
		Session session = sessionFactory.getCurrentSession();

		return session.get(Member.class, id);
	}

	@Override
	public MemberFull getMemberFull(int id) {

		try {
			// zisk�n� vybran� hibernate session
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
			throw new RuntimeException("Chyba p�i pr�ci s datab�z� a vy��tan� MemberFull z id member - " + id, e);
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
