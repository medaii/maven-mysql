package loko.DAO;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;



import loko.DB.DBSqlExecutor;
import loko.core.Mail;
import loko.core.MailsMember;
import loko.core.Member;
import loko.core.MemberFull;
import loko.core.MemberList;
import loko.core.Phone;
import loko.core.PhonesMeber;


/**
 * 
 * @author Erik Markoviè
 *
 */

public class MembersDAOImpl implements IFMembersDAO {
	private DBSqlExecutor sqlExecutor;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	// vytvoøení konstruktoru
	public MembersDAOImpl(DBSqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}
	/**
	 * 
	 * metoda maže záznam o èlenovy a jeho další udaje jako telefon, mail, trvale bydlištì, rodné èíslo, cshreg
	 * @param id -  èlena
	 * @return
	 */
	public int deleteMember(int id) {
		// vytvoreni pøistupu k mailùm a telefonum
		IFMailsDAO mailsDao = DAOFactory.createDAO(IFMailsDAO.class);
		IFPhoneDAO phoneDAO = DAOFactory.createDAO(IFPhoneDAO.class);
		MailsMember mails = mailsDao.getMailsMember(id);
		PhonesMeber phones = phoneDAO.getPhonesMember(id);
		
		if (mails != null) {
			for (Mail mail : mails.getMails()) {
				if(mailsDao.deleteMail(mail.getId()) > 0) {
					LOGGER.warning("Mail id - " + mail.getId() + " smazán.");
				}
				else {

					LOGGER.warning("CHYBA. Mail id - " + mail.getId() + " není smazán.");
				}
			}
		}
		if (phones != null) {
			for (Phone phone : phones.getPhones()) {
				if(phoneDAO.deletePhone(phone.getId()) > 0) {
					LOGGER.warning("Telefon id - " + phone.getId() + " smazán.");
				}
				else {

					LOGGER.warning("CHYBA. Telefon id - " + phone.getId() + " není smazán.");
				}
			}
		}
		//smazání trvalého bydlištì
		String dotaz = "DELETE FROM clen_trvala_adresa " + "WHERE id_osoby = ?";
		String zprava;
		zprava = (sqlExecutor.deleteRow(dotaz, id)> 0)?"Trvale byslištì smazáno. id èlena:" + id :
																										"Trvalé bydlištì nesmazáno id èlena:" + id;
		LOGGER.warning(zprava);
		
		//smazaní rodného èísla
		dotaz = "DELETE FROM clen_rodne_cislo " + "WHERE id_osoby = ?";
		zprava = (sqlExecutor.deleteRow(dotaz, id)> 0)?"Rodné èíslo smazáno. id èlena:" + id:
																									"Rodné èíslo bydlištì nesmazáno id èlena:" + id;
		LOGGER.warning(zprava);
		
		//smazaní èísla registrace
		dotaz = "DELETE FROM cshRegC " + "WHERE id_osoby = ?";
		zprava = (sqlExecutor.deleteRow(dotaz, id)> 0)?"Èíslo registrace smazáno. id èlena:" + id:
																									"Èíslo registrace nesmazáno. id èlena:" + id;
		LOGGER.warning(zprava);
		
		//smazaní èlena ze seznamu
		dotaz = "DELETE FROM clen_seznam " + "WHERE id = ?";
		return sqlExecutor.deleteRow(dotaz, id);

	}
	/**
	 * 
	 * @param member
	 * @return nové id
	 */
	public int addMemberFull(MemberFull member) {
		String dotaz = "insert into clen_seznam" + " (kjmeno, pjmeno, datum_narozeni, poznamka, aktivni, id_odd_kategorie, zacal)" + " values (?, ?, ?, ?, ?, ?, ?)";
		
		String[] hodnoty = { member.getFirstName(), member.getLastName(),String.valueOf(member.getBirthDay()),
				member.getNote(),String.valueOf(member.getActive()), String.valueOf(member.getId_odd_kategorie()),
				String.valueOf(member.getEnterDate())};
		int id = sqlExecutor.insertDotaz(dotaz, hodnoty);
		
		if(id > 0 ) {
			dotaz = "insert into clen_rodne_cislo" + " (id_osoby, rodne_cislo)" + " values (?, ?)";
			String[] hodnotyRC = { String.valueOf(id),member.getRodneCislo()};
			int idRC = sqlExecutor.insertDotaz(dotaz, hodnotyRC);
			LOGGER.info("Vytvoøeno nové rodné èíslo id:"+idRC + "k èlenovy id:"+ id);
			dotaz = "insert into clen_trvala_adresa" + " (id_osoby, adresa)" + " values (?, ?)";
			String[] hodnotyTR = { String.valueOf(id),member.getTrvaleBydliste()};
			int idTR = sqlExecutor.insertDotaz(dotaz, hodnotyTR);
			LOGGER.info("Vytvoøeno nové trvalé bydlištì id:"+idTR + "k èlenovy id:"+ id);
			
			dotaz = "insert into cshRegC" + " (id_osoby, regCislo)" + " values (?, ?)";
			String[] hodnotyReg = { String.valueOf(id),member.getChfRegistrace()};
			int idReg = sqlExecutor.insertDotaz(dotaz, hodnotyReg);
			LOGGER.info("Vytvoøeno nové èíslo registrace ÈSH id:"+idReg + "k èlenovy id:"+ id);			
		}
		return id;
	}
	/**
	 * 
	 * @param member
	 *            - objekt ktery má být nahrán do DB
	 * @param id
	 *            - id member na DB
	 * @return - vrací poèet zmìnìných øádku nebo -1 pøi chybì
	 * id, kjmeno, pjmeno, datum_narozeni, poznamka, aktivni, id_odd_kategorie, zacal
	 */
	public int updateMember(Member member, int id){
		String dotaz = "update clen_seznam" + " set kjmeno = ?, pjmeno = ?, datum_narozeni = ? , poznamka = ?, aktivni = ?"
									+ ", id_odd_kategorie = ? , zacal = ? where id = ?";
		String[] hodnoty = { member.getFirstName(), member.getLastName(),String.valueOf(member.getBirthDay()),
												member.getNote(),String.valueOf(member.getActive()), String.valueOf(member.getId_odd_kategorie()),
												String.valueOf(member.getEnterDate()),String.valueOf(id) };
		int resurm = sqlExecutor.setDotaz(dotaz, hodnoty);

		return resurm;
	}
	/**
	 * 
	 * @param memberfull
	 *            - objekt ktery má být nahrán do DB
	 * @param id
	 *            - id member na DB
	 * @return - vrací poèet zmìnìných øádku nebo -1 pøi chybì
	 * id, kjmeno, pjmeno, datum_narozeni, poznamka, aktivni, id_odd_kategorie, zacal
	 */
	public int updateMember(MemberFull member, int id){
		String dotaz = "update clen_seznam" + " set kjmeno = ?, pjmeno = ?, datum_narozeni = ? , poznamka = ?, aktivni = ?"
									+ ", id_odd_kategorie = ? , zacal = ? where id = ?";
		String[] hodnoty = { member.getFirstName(), member.getLastName(),String.valueOf(member.getBirthDay()),
												member.getNote(),String.valueOf(member.getActive()), String.valueOf(member.getId_odd_kategorie()),
												String.valueOf(member.getEnterDate()),String.valueOf(id) };
		int resurm = sqlExecutor.setDotaz(dotaz, hodnoty);
		// zapis rodneho cisla
		if (resurm >0) {
			dotaz = "update clen_rodne_cislo" + " set rodne_cislo = ?"
					+ " where id_osoby = ?";
			String[] hodnotyRC = { member.getRodneCislo(),String.valueOf(id) };
			resurm = sqlExecutor.setDotaz(dotaz, hodnotyRC);
			//zapis trvaleho bydliste
			if (resurm > 0 ) {
				dotaz = "update clen_trvala_adresa" + " set adresa = ?"
						+ " where id_osoby = ?";
				String[] hodnotyTR = { member.getTrvaleBydliste(),String.valueOf(id) };
				resurm = sqlExecutor.setDotaz(dotaz, hodnotyTR);
				//zapis cisla registrace
				if (resurm > 0) {
					dotaz = "update cshRegC" + " set regCislo = ?"
							+ " where id_osoby = ?";
					String[] hodnotyRegC = { member.getChfRegistrace(),String.valueOf(id) };
					resurm = sqlExecutor.setDotaz(dotaz, hodnotyRegC);
				}
			}
		}
		
		return resurm;
	}
	/**
	 * Vrací list tabulky clen seznam z DB
	 * @return
	 */
	public List<Member> getAllMember(){
		List<Member>  list = new ArrayList<>();
		ArrayList<String[]> r = new ArrayList<>();// databaze vráti výsledek do listu
		
		String dotaz = "Select * from clen_seznam ORDER BY clen_seznam.datum_narozeni ASC";
		
		sqlExecutor.getData(dotaz,r);
		
		for (String[] a : r) {			
			Member temp = convertRowToMember(a);
			if(temp == null) {
				LOGGER.warning("Chybné pole");
			}
			else {
				list.add(temp);
			}		
		}
		
		
		return list;
	}
	/**
	 * Vrací seznam èlenù s kontakty
	 * @return
	 */
	public List<MemberList> getAllMemberList(boolean active, int kategorie){
		List<MemberList>  list = new ArrayList<>();
		ArrayList<String[]> r = new ArrayList<>();// databaze vráti výsledek do listu
		
		IFMailsDAO mailsDao = DAOFactory.createDAO(IFMailsDAO.class);
		IFPhoneDAO phoneDAO = DAOFactory.createDAO(IFPhoneDAO.class);
		
		Map<Integer, MailsMember> mailsMap = mailsDao.getAllMailMembers();
		Map<Integer, PhonesMeber> phoneMap = phoneDAO.getAllPhonesMembers();
		String dotaz;
		String where = "";
		if(active){
			where += "WHERE aktivni= 1 ";
		}
		if(kategorie > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			int nowYear = cal.get(Calendar.YEAR);			
			int nowMonth = cal.get(Calendar.MONTH);
			cal.set(Calendar.MONTH, Calendar.JANUARY);
			cal.set(Calendar.DAY_OF_MONTH, 1);			
			String date = sdf.format(cal.getTime());
			
			switch (kategorie) {
			case 1:
				if(nowMonth > 6) {
					cal.set(Calendar.YEAR, nowYear - 18);
				}
				else {
					cal.set(Calendar.YEAR, nowYear - 19);
				}
				  where += "AND datum_narozeni < '" + sdf.format(cal.getTime()) + "' ";
				break;
			case 2:
				if(nowMonth > 6) {
					cal.set(Calendar.YEAR, nowYear - 18);
				}
				else {
					cal.set(Calendar.YEAR, nowYear - 19);
				}
				where += "AND datum_narozeni > '" + sdf.format(cal.getTime()) + "' ";
				break;
			case 3:
				if(nowMonth > 6) {
					cal.set(Calendar.YEAR, nowYear - 18);
				}
				else {
					cal.set(Calendar.YEAR, nowYear - 19);
				}
				where += "AND datum_narozeni > '" + sdf.format(cal.getTime()) + "' ";
				if(nowMonth > 6) {
						cal.set(Calendar.YEAR, nowYear - 14);
				}
				else {
						cal.set(Calendar.YEAR, nowYear - 15);
				}
				where += "AND datum_narozeni < '" + sdf.format(cal.getTime()) + "' ";
					
				break;
			case 4:
				if(nowMonth > 6) {
					cal.set(Calendar.YEAR, nowYear - 14);
				}
				else {
					cal.set(Calendar.YEAR, nowYear - 15);
				}
				where += "AND datum_narozeni > '" + sdf.format(cal.getTime()) + "' ";
				if(nowMonth > 6) {
						cal.set(Calendar.YEAR, nowYear - 10);
				}
				else {
						cal.set(Calendar.YEAR, nowYear - 11);
				}
				where += "AND datum_narozeni < '" + sdf.format(cal.getTime()) + "' ";
				break;
			case 5:
				if(nowMonth > 6) {
					cal.set(Calendar.YEAR, nowYear - 10);
				}
				else {
					cal.set(Calendar.YEAR, nowYear - 11);
				}
				where += "AND datum_narozeni > '" + sdf.format(cal.getTime()) + "' ";
					
				break;	
			default:
				break;
			}
		}
		if (active && kategorie !=6) {
			dotaz = "Select * from clen_seznam " + where +" ORDER BY clen_seznam.datum_narozeni ASC";
			
		} else {
			dotaz = "Select * from clen_seznam ORDER BY clen_seznam.datum_narozeni ASC";
		}
		
		sqlExecutor.getData(dotaz,r);
		
		for (String[] a : r) {			
			Member member = convertRowToMember(a);
			
			if(member == null) {
				LOGGER.warning("Chybné pole");
			}
			else {
				
				List<Mail> mails;
				List<Phone> phones;
				if(mailsMap.containsKey(member.getId())) {
					MailsMember mailsMember = mailsMap.get(member.getId());
					mails = mailsMember.getMails();
				}
				else {
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
		
		
		return list;
	}
	public List<MemberList> searchAllMembers(String name, boolean active,int kategorie) {
		List<MemberList>  list = new ArrayList<>();
		String word = "%" + name;
		word +="%";
		
		IFMailsDAO mailsDao = DAOFactory.createDAO(IFMailsDAO.class);
		IFPhoneDAO phoneDAO = DAOFactory.createDAO(IFPhoneDAO.class);
		
		Map<Integer, MailsMember> mailsMap = mailsDao.getAllMailMembers();
		Map<Integer, PhonesMeber> phoneMap = phoneDAO.getAllPhonesMembers();
		
		ArrayList<String[]> r = new ArrayList<>();// databaze vráti výsledek do listu
		String dotaz;
		if(active) {
			dotaz = "Select * from clen_seznam"
					+ " where (kjmeno like '"+ word + "' OR pjmeno like '" + word + "') AND aktivni = 1 " ;			
		}
		else {
			dotaz = "Select * from clen_seznam"
					+ " where kjmeno like '"+ word + "' OR pjmeno like '" + word + "' " ;
			
		}
		
		sqlExecutor.getData(dotaz,r);
		
		for (String[] a : r) {
			Member member = convertRowToMember(a);
			
			if(member == null) {
				LOGGER.warning("Chybné pole");
			}
			else {				
				List<Mail> mails = new ArrayList<>();
				List<Phone> phones;
				if(mailsMap.containsKey(member.getId())) {
					MailsMember mailsMember = mailsMap.get(member.getId());
					mails = mailsMember.getMails();
				}
				else {
					Mail mail = new Mail(member.getId(), "", "");
					mails.add(mail);
				}
				if (phoneMap.containsKey(member.getId())) {
					PhonesMeber phonesMember = phoneMap.get(member.getId());
					phones = phonesMember.getPhones();
				} else {
					phones = null;
				}
				LOGGER.fine("Mail" + mails);
				
				MemberList memberList = new MemberList(member, mails, phones);
				list.add(memberList);
			}	
		}
		
		
		return list;
	}
	/**
	 * vrací vybraného èlena 
	 * @param id
	 * @return
	 */
	public Member getMember(int id) {
		Member member = null;
		ArrayList<String[]> r = new ArrayList<>();// databaze vráti výsledek do listu
		String dotaz ="Select * from clen_seznam"
									+ " where id = " + id  ;
		sqlExecutor.getData(dotaz,r);
		for (String[] a : r) {			
			member = convertRowToMember(a);
		}
				
		return member;
	}
	/**
	 * vrací vybraného èlena s rodným èíslem, trvalím bydlištìm a èíslem registraèního prùkazu
	 * @param id
	 * @return
	 */
	public MemberFull getMemberFull(int id) {
		MemberFull memberFull = null;
		ArrayList<String[]> r = new ArrayList<>();// databaze vráti výsledek do listu
		String dotaz ="SELECT clen_seznam.id, kjmeno as firstName, pjmeno as lastName, datum_narozeni, poznamka as note,"
										+ " aktivni as active, id_odd_kategorie, zacal, clen_rodne_cislo.rodne_cislo,"
										+ " clen_trvala_adresa.adresa, cshRegC.regCislo FROM `clen_seznam`,`clen_rodne_cislo`,"
										+ "`clen_trvala_adresa`,`cshRegC` "
										+ "WHERE clen_seznam.id = clen_rodne_cislo.id_osoby "
										+ "AND clen_seznam.id = clen_trvala_adresa.id_osoby AND clen_seznam.id = cshRegC.id_osoby "
										+ "AND clen_seznam.id = " + id  ;
		String[] hodnoty = {};
		int countRow =sqlExecutor.getCountRow(dotaz, hodnoty);
		// když je poèet øádku rovný 0, tak nastala chyba pøi dotazu z DB, nejspíš chybí záznam o pøidaných tabulkách
		if(countRow < 1){			
			if(!opravaTabulkyMember(id)){
				LOGGER.severe("CHYBA TABULKY v DB");
			}			
		}
		sqlExecutor.getData(dotaz,r);
		
		for (String[] a : r) {			
			memberFull = convertRowToMemberFull(a);
		}
		
		return memberFull;
	}
	/**
	 * 
	 * @param temp
	 * @return jednoduchy objekt member
	 */
	private Member convertRowToMember(String[] temp) {
		Member tempMember;
		try {
			if(temp.length ==8 && (temp[0] !="id")) {
				int id = Integer.parseInt(temp[0]);
				String firstName = temp[1];
				String lastName = temp[2];
				Date birthDay = Date.valueOf(temp[3]);
				String note = temp[4];
				int active = Integer.parseInt(temp[5]);
				int id_odd_kategorie = Integer.parseInt(temp[6]);
				Date enterDate = Date.valueOf(temp[7]); 
				
				tempMember = new Member(id, firstName, lastName, birthDay, note, active, id_odd_kategorie, enterDate);			
			}
			else {
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
			if(temp.length ==11 && (temp[0] !="id")) {
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

				tempMember = new MemberFull(id, firstName, lastName, birthDay, note, active, id_odd_kategorie, enterDate, rodneCislo, trvaleBydliste, chfRegistrace);			
			}
			else {
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
		String[] hodnoty2 = {String.valueOf(id)};
		if (sqlExecutor.getCountRow(dotaz2, hodnoty2)<1) {
			String dotaz21 = "insert into clen_trvala_adresa" + " (id_osoby, adresa)" + " values (?, ?)";
			String[] hodnoty21 = { String.valueOf(id), ""};
			sqlExecutor.insertDotaz(dotaz21, hodnoty21);
		}
		// kontrola csh registrace
		String dotaz3 = "SELECT * FROM `cshRegC` WHERE id_osoby=?";
		String[] hodnoty3 = {String.valueOf(id)};
		if (sqlExecutor.getCountRow(dotaz3, hodnoty3)<1) {
			String dotaz31 = "insert into cshRegC" + " (id_osoby, regCislo)" + " values (?, ?)";
			String[] hodnoty31 = { String.valueOf(id), ""};
			sqlExecutor.insertDotaz(dotaz31, hodnoty31);
		}
	// kontrola rodneho cisla
		String dotaz4 = "SELECT * FROM `clen_rodne_cislo` WHERE id_osoby=?";
		String[] hodnoty4 = {String.valueOf(id)};
		if (sqlExecutor.getCountRow(dotaz4, hodnoty4)<1) {
			String dotaz41 = "insert into clen_rodne_cislo" + " (id_osoby, rodne_cislo)" + " values (?, ?)";
			String[] hodnoty41 = { String.valueOf(id), ""};
			sqlExecutor.insertDotaz(dotaz41, hodnoty41);
		}
		
		return true;
	}
	public static void main(String[] args) {
	
		// TODO Auto-generated method stub
		IFMembersDAO membersDAO = DAOFactory.createDAO(IFMembersDAO.class);
		
		membersDAO.getMemberFull(168);
	}
}
