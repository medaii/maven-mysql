package loko.DAO;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

public class MembersDAO {
	private DBSqlExecutor con;
	
	// vytvoøení konstruktoru
	public MembersDAO() {
		con = DBSqlExecutor.getInstance();
	}
	
	public int deleteMember(int id) {
		
		return -1;
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
		int id = con.insertDotaz(dotaz, hodnoty);
		
		if(id > 0 ) {
			dotaz = "insert into clen_rodne_cislo" + " (id_osoby, rodne_cislo)" + " values (?, ?)";
			String[] hodnotyRC = { String.valueOf(id),member.getRodneCislo()};
			int idRC = con.insertDotaz(dotaz, hodnotyRC);
			
			dotaz = "insert into clen_trvala_adresa" + " (id_osoby, adresa)" + " values (?, ?)";
			String[] hodnotyTR = { String.valueOf(id),member.getTrvaleBydliste()};
			int idTR = con.insertDotaz(dotaz, hodnotyTR);
			
			dotaz = "insert into cshRegC" + " (id_osoby, regCislo)" + " values (?, ?)";
			String[] hodnotyReg = { String.valueOf(id),member.getChfRegistrace()};
			int idReg = con.insertDotaz(dotaz, hodnotyReg);			
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
		int resurm = con.setDotaz(dotaz, hodnoty);

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
		int resurm = con.setDotaz(dotaz, hodnoty);
		// zapis rodneho cisla
		if (resurm >0) {
			dotaz = "update clen_rodne_cislo" + " set rodne_cislo = ?"
					+ " where id_osoby = ?";
			String[] hodnotyRC = { member.getRodneCislo(),String.valueOf(id) };
			resurm = con.setDotaz(dotaz, hodnotyRC);
			//zapis trvaleho bydliste
			if (resurm > 0 ) {
				dotaz = "update clen_trvala_adresa" + " set adresa = ?"
						+ " where id_osoby = ?";
				String[] hodnotyTR = { member.getTrvaleBydliste(),String.valueOf(id) };
				resurm = con.setDotaz(dotaz, hodnotyTR);
				//zapis cisla registrace
				if (resurm > 0) {
					dotaz = "update cshRegC" + " set regCislo = ?"
							+ " where id_osoby = ?";
					String[] hodnotyRegC = { member.getChfRegistrace(),String.valueOf(id) };
					resurm = con.setDotaz(dotaz, hodnotyRegC);
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
		
		con.getData(dotaz,r);
		
		for (String[] a : r) {			
			Member temp = convertRowToMember(a);
			if(temp == null) {
				System.out.println("chybne pole");
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
	public List<MemberList> getAllMemberList(boolean active){
		List<MemberList>  list = new ArrayList<>();
		ArrayList<String[]> r = new ArrayList<>();// databaze vráti výsledek do listu
		
		IFMailsDAO mailsDao = DAOFactory.createDAO(IFMailsDAO.class);
		IFPhoneDAO phoneDAO = DAOFactory.createDAO(IFPhoneDAO.class);
		
		Map<Integer, MailsMember> mailsMap = mailsDao.getAllMailMembers();
		Map<Integer, PhonesMeber> phoneMap = phoneDAO.getAllPhonesMembers();
		String dotaz;
		if (active) {
			dotaz = "Select * from clen_seznam WHERE aktivni= 1 ORDER BY clen_seznam.datum_narozeni ASC";
		} else {
			dotaz = "Select * from clen_seznam ORDER BY clen_seznam.datum_narozeni ASC";
		}
		
		con.getData(dotaz,r);
		
		for (String[] a : r) {			
			Member member = convertRowToMember(a);
			
			if(member == null) {
				System.out.println("chybne pole");
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
	public List<MemberList> seachAllMembers(String name, boolean active) {
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
		System.out.println(dotaz);
		con.getData(dotaz,r);
		
		for (String[] a : r) {
			Member member = convertRowToMember(a);
			
			if(member == null) {
				System.out.println("chybne pole");
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
				System.out.println("p5e "+ mails);
				
				
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
		con.getData(dotaz,r);
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
		con.getData(dotaz,r);
		System.out.println(dotaz);
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
	public static void main(String[] args) {
	
		// TODO Auto-generated method stub
		MembersDAO membersDAO = new MembersDAO();
		
		membersDAO.getMemberFull(168);
	}
}
