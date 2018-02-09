package loko.DAO;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import loko.DB.DBHibernateSqlExecutor;
import loko.core.CshRegNumber;
import loko.core.Mail;
import loko.core.MailsMember;
import loko.core.Member;
import loko.core.MemberFull;
import loko.core.MemberList;
import loko.core.Phone;
import loko.core.PhonesMeber;
import loko.core.RodneCislo;
import loko.core.TrvaleBydliste;

/**
 * 
 * @author Erik Markoviè
 *
 */

public class MembersHibernateDAOImpl implements IFMembersDAO {
	private DBHibernateSqlExecutor dbHibernateSqlExecutor;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	// vytvoøení konstruktoru
	public MembersHibernateDAOImpl(DBHibernateSqlExecutor dbHibernateSqlExecutor) {
		this.dbHibernateSqlExecutor = dbHibernateSqlExecutor;
	}

	/**
	 * 
	 * metoda maže záznam o èlenovy a jeho další udaje jako telefon, mail, trvale
	 * bydlištì, rodné èíslo, cshreg
	 * 
	 * @param id
	 *          - èlena
	 * @return
	 */
	public int deleteMember(int id) {
		// vyuziti vztahu mezi tabulkami a cascade typ ALL
		// smazáním radku v tabulce clen_seznam se smažou ostatní zaznamy v DB, které
		// jsou spojeny primarním klièem member
		return dbHibernateSqlExecutor.deleteObject(id, new Member());
	}

	/**
	 * 
	 * @param memberFull
	 *          - MemberFull využivano pro tvorbu Modelu v SWING proto posila tento
	 *          objekt, ktery obsahuje hodnoty pro objekt Member, RodneCislo,
	 *          TrvaleBydliste,CshRegNumber
	 * 
	 * @return nové id
	 */
	public int addMemberFull(MemberFull memberFull) {

		// zavolani si instance sessionfactory
		SessionFactory factory = dbHibernateSqlExecutor.getSessionFactory();

		try (Session session = factory.getCurrentSession();) {
			// start a transaction
			session.beginTransaction();

			Member member;
			RodneCislo rodneCislo;
			TrvaleBydliste trvaleBydliste;
			CshRegNumber cshRegNumber;
			// vytvoreni member
			member = new Member(memberFull.getFirstName(), memberFull.getLastName(), memberFull.getBirthDay(),
					memberFull.getNote(), memberFull.getActive(), memberFull.getId_odd_kategorie(), memberFull.getEnterDate());
			session.save(member); // cascade nastaveny na all, ale moznost, ze nebude zadane ani jedno z
														// nasledujicich udaju

			// vytvoreni rodného èísla
			if (memberFull.getRodneCislo() != null) {
				rodneCislo = new RodneCislo(memberFull.getRodneCislo());
				// pridani do member
				rodneCislo.setMember(member);
				session.save(rodneCislo);
			}

			// vytvoreni registraciního èísla
			if (memberFull.getChfRegistrace() != null) {
				cshRegNumber = new CshRegNumber(memberFull.getChfRegistrace());
				// pridani do member
				cshRegNumber.setMember(member);
				session.save(cshRegNumber);
			}

			// vytvoreni trvalého bydlištì
			if (memberFull.getTrvaleBydliste() != null) {
				trvaleBydliste = new TrvaleBydliste(memberFull.getTrvaleBydliste());
				// pridani do member
				trvaleBydliste.setMember(member);
				session.save(trvaleBydliste);
			}

			// commit transaction
			session.getTransaction().commit();

			return member.getId(); // vraci nove id pro otevreni noveho okna k editaci

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.warning("Chyba zápisu memberFull do DB!");
			return 0;
		}
	}

	/**
	 * 
	 * @param member
	 *          - objekt ktery ma nahradit stavajici radek v DB
	 * 
	 * @param id
	 *          - id member na DB
	 * 
	 * @return - vrací poèet zmìnìných øádku nebo -1 pøi chybì
	 * 
	 *         id, kjmeno, pjmeno, datum_narozeni, poznamka, aktivni,
	 *         id_odd_kategorie, zacal
	 */
	public int updateMember(Member member, int id) {
		return dbHibernateSqlExecutor.updateObject(member);
	}

	/**
	 * 
	 * @param memberFull
	 *          - MemberFull využivano pro tvorbu Modelu v SWING proto posila tento
	 *          objekt, ktery obsahuje hodnoty pro objekt Member, RodneCislo,
	 *          TrvaleBydliste,CshRegNumber
	 * @param id
	 *          - id member na DB
	 * @return - vrací poèet zmìnìných øádku nebo -1 pøi chybì
	 * 
	 */
	public int updateMember(MemberFull memberFull, int id) {

		// zavolani si instance sessionfactory
		SessionFactory factory = dbHibernateSqlExecutor.getSessionFactory();

		try (Session session = factory.getCurrentSession();) {
			// start a transaction
			session.beginTransaction();

			Member tempMember;
			Member member = session.get(Member.class, id);
			RodneCislo rodneCislo;
			TrvaleBydliste trvaleBydliste;
			CshRegNumber cshRegNumber;

			// vytvoreni editovaneho member
			tempMember = new Member(memberFull.getId(), memberFull.getFirstName(), memberFull.getLastName(),
					memberFull.getBirthDay(), memberFull.getNote(), memberFull.getActive(), memberFull.getId_odd_kategorie(),
					memberFull.getEnterDate());
			
			// update v DB
			member.setMember(tempMember);

			// vytvoreni rodného èísla
			if (memberFull.getRodneCislo() != null) {
				rodneCislo = member.getRodneCislo();
				// kdyz jeste zaznam neexistuje
				if (rodneCislo == null) {
					rodneCislo = new RodneCislo();
					rodneCislo.setMember(member);
				}

				rodneCislo.setRodne_cislo(memberFull.getRodneCislo());
				session.saveOrUpdate(rodneCislo);
			}

			// vytvoreni registraciního èísla
			if (memberFull.getChfRegistrace() != null) {
				cshRegNumber = member.getCshRegNumber();
				// kdyz jeste zaznam neexistuje
				if (cshRegNumber == null) {
					cshRegNumber = new CshRegNumber();
					cshRegNumber.setMember(member);
				}

				cshRegNumber.setRegCislo(memberFull.getChfRegistrace());
				session.saveOrUpdate(cshRegNumber);
			}

			// vytvoreni trvalého bydlištì
			if (memberFull.getTrvaleBydliste() != null) {
				trvaleBydliste = member.getTrvaleBydliste();
				// kdyz jeste zaznam neexistuje
				if (trvaleBydliste == null) {
					trvaleBydliste = new TrvaleBydliste();
					trvaleBydliste.setMember(member);
				}
				trvaleBydliste.setAdresa(memberFull.getTrvaleBydliste());
				session.saveOrUpdate(trvaleBydliste);
			}

			// commit transaction
			session.getTransaction().commit();

			return member.getId();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.warning("Chyba zápisu memberFull do DB!");
			return 0;
		}
	}

	/**
	 * Vrací list tabulky clen seznam z DB
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Member> getAllMember() {
		// list ktery naplnime z DB
		List<Member> list = new ArrayList<>();

		// zavolani instance sessionFactoru
		SessionFactory factory = dbHibernateSqlExecutor.getSessionFactory();

		// vytrvoreni session
		try (Session session = factory.getCurrentSession();) {
			// start a transaction
			session.beginTransaction();
			// dotaz

			list = session.createQuery("select i from Member i join fetch i.cshRegNumber " + "join fetch i.rodneCislo "
					+ "join fetch i.phones " + "join fetch i.trvaleBydliste").list();
			// commit transaction
			session.getTransaction().commit();

		} catch (Exception e) {
			LOGGER.warning("Chyba zápisu!");
		}
		return list;
	}

	/**
	 * Vrací seznam èlenù s kontakty
	 * 
	 * @active - natavi kriterie, jestli je clen aktivni
	 * 
	 * @kategorie - nastavi kriteria pro vek
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MemberList> getAllMemberList(boolean active, int kategorie) {
		List<MemberList> list = new ArrayList<>();

		IFMailsDAO mailsDao = DAOFactory.createDAO(IFMailsDAO.class);
		IFPhoneDAO phoneDAO = DAOFactory.createDAO(IFPhoneDAO.class);

		Map<Integer, MailsMember> mailsMap = mailsDao.getAllMailMembers();
		Map<Integer, PhonesMeber> phoneMap = phoneDAO.getAllPhonesMembers();

		
		
		List<Member> members;
		//date pro filtraci		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int nowYear = cal.get(Calendar.YEAR);
		int nowMonth = cal.get(Calendar.MONTH);
		
		
		java.util.Date dateEnd = new java.util.Date();
		cal.set(Calendar.YEAR, nowYear - 1000);
		java.util.Date dateStart = cal.getTime();
		cal.set(Calendar.YEAR, nowYear +1000);
		
		if (kategorie > 0) {
			// entity nastavene date z java.until proto tento zpusob
			// vytvoreni formatu
			
			switch (kategorie) {
			case 1:
				if (nowMonth > 6) {
					cal.set(Calendar.YEAR, nowYear - 18);
				} else {
					cal.set(Calendar.YEAR, nowYear - 19);
				}
				break;
				
			case 2:
				if (nowMonth > 6) {
					cal.set(Calendar.YEAR, nowYear - 18);
					dateEnd = cal.getTime();
				} else {
					dateEnd = cal.getTime();
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
				dateEnd = cal.getTime();
				break;
			}
		}
		
		
		// zavolani instance sessionFactoru
			SessionFactory factory = dbHibernateSqlExecutor.getSessionFactory();
			// vytrvoreni session
			try (Session session = factory.getCurrentSession();) {
				// start a transaction
				session.beginTransaction();
				// dotaz
				String dotaz1 = "select distinct i from Member i join fetch i.cshRegNumber " + "join fetch i.rodneCislo "
					
						+ "join fetch i.trvaleBydliste "
						+  "where i.birthDay between :start and :end ";
				if(active) {
					dotaz1 += "AND i.active = 1 ";
					System.out.println(dotaz1);
				}
				members = session.createQuery( dotaz1 + "ORDER BY i.birthDay ASC")
							.setParameter("start", dateStart).setParameter("end", dateEnd).list();
				
				for (Member member : members) {
					if (member == null) {
						LOGGER.warning("Chybné pole");
					} else {

						List<Mail> mails;
						List<Phone> phones;
						if (mailsMap.containsKey(member.getId())) {
							MailsMember mailsMember = mailsMap.get(member.getId());
							mails = mailsMember.getMails();
						} else {
							mails = null;
						}
						if (phoneMap.containsKey(member.getId())) {
							PhonesMeber phonesMember = phoneMap.get(member.getId());
							phones = phonesMember.getPhones();
						} else {
							phones = null;
						}

						MemberList memberList = new MemberList(member, mails, phones);
						list.add(memberList);
					}
				}
				
				// commit transaction
				session.getTransaction().commit();
			}
			catch (Exception e) {
				LOGGER.warning("Chyba zápisu!" + e);
			}
		return list;
	}

	/**
	 * list pro vyhledavani v seznamu dle jmena clena
	 */
	public List<MemberList> searchAllMembers(String name, boolean active, int kategorie) {
		
		List<MemberList> list = new ArrayList<>();
		String word = "%" + name;
		word += "%";
		
		IFMailsDAO mailsDao = DAOFactory.createDAO(IFMailsDAO.class);
		IFPhoneDAO phoneDAO = DAOFactory.createDAO(IFPhoneDAO.class);

		Map<Integer, MailsMember> mailsMap = mailsDao.getAllMailMembers();
		Map<Integer, PhonesMeber> phoneMap = phoneDAO.getAllPhonesMembers();

	// zavolani instance sessionFactoru
				SessionFactory factory = dbHibernateSqlExecutor.getSessionFactory();
				// vytrvoreni session
				try (Session session = factory.getCurrentSession();) {
					// start a transaction
					session.beginTransaction();
					// dotaz
					
					String dotaz1 = "select distinct i from Member i join fetch i.cshRegNumber " + "join fetch i.rodneCislo "						
							+ "join fetch i.trvaleBydliste "
							+  "where i.firstName like :word or i.lastName like :word ";

					@SuppressWarnings("unchecked")
					List<Member> members = session.createQuery( dotaz1 + "ORDER BY i.birthDay ASC")
								.setParameter("word", word).list();
					
					for (Member member : members) {
						if (member == null) {
							LOGGER.warning("Chybné pole");
						} else {

							List<Mail> mails;
							List<Phone> phones;
							if (mailsMap.containsKey(member.getId())) {
								MailsMember mailsMember = mailsMap.get(member.getId());
								mails = mailsMember.getMails();
							} else {
								mails = null;
							}
							if (phoneMap.containsKey(member.getId())) {
								PhonesMeber phonesMember = phoneMap.get(member.getId());
								phones = phonesMember.getPhones();
							} else {
								phones = null;
							}

							MemberList memberList = new MemberList(member, mails, phones);
							list.add(memberList);
						}
					}
					
					// commit transaction
					session.getTransaction().commit();
				} 
				catch (Exception e) {
					LOGGER.warning("Chyba zápisu!" + e);
				}
		return list;
	}

	/**
	 * vrací vybraného èlena
	 * 
	 * @param id
	 * @return Member
	 */
	public Member getMember(int id) {
		return (Member) dbHibernateSqlExecutor.getObject(id, Member.class);
	}

	/**
	 * vrací vybraného èlena s rodným èíslem, trvalím bydlištìm a èíslem
	 * registraèního prùkazu
	 * 
	 * @param id
	 * @return
	 */
	public MemberFull getMemberFull(int id) {

		// zavolani instance sessionFactoru
		SessionFactory factory = dbHibernateSqlExecutor.getSessionFactory();

		// vytrvoreni session
		try (Session session = factory.getCurrentSession();) {
			// start a transaction
			session.beginTransaction();
			// dotaz
			Member member = session.get(Member.class, id);
			RodneCislo rodneCislo = member.getRodneCislo();
			TrvaleBydliste trvaleBydliste = member.getTrvaleBydliste();
			CshRegNumber cshRegNumber = member.getCshRegNumber();
			// commit transaction
			session.getTransaction().commit();
			
			// kontrola praznych objektu
			if(rodneCislo==null) {
				rodneCislo = new RodneCislo();
			}
			if(trvaleBydliste==null) {
				trvaleBydliste = new TrvaleBydliste();
			}
			if(cshRegNumber==null) {
				cshRegNumber = new CshRegNumber();
			}
			
			return new MemberFull(member, rodneCislo, cshRegNumber, trvaleBydliste);
			
		} catch (Exception e) {
			LOGGER.warning("Chyba zápisu!");
			return null;
		}
		
	}

}
