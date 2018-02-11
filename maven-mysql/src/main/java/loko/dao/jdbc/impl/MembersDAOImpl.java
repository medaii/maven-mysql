package loko.dao.jdbc.impl;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import loko.dao.DAOFactory;
import loko.dao.MailsDAO;
import loko.dao.MembersDAO;
import loko.dao.PhoneDAO;
import loko.db.executor.impl.DBSqlExecutor;
import loko.entity.Mail;
import loko.entity.Member;
import loko.entity.Phone;
import loko.value.MailsMember;
import loko.value.MemberFull;
import loko.value.MemberList;
import loko.value.PhonesMeber;


/**
 * 
 * @author Erik Markovi�
 *
 */

public class MembersDAOImpl implements MembersDAO {
	private DBSqlExecutor sqlExecutor;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	// vytvo�en� konstruktoru
	public MembersDAOImpl(DBSqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}
	/**
	 * 
	 * metoda ma�e z�znam o �lenovy a jeho dal�� udaje jako telefon, mail, trvale bydli�t�, rodn� ��slo, cshreg
	 * @param id -  �lena
	 * @return
	 */
	public void deleteMember(int id) {
		// vytvoreni p�istupu k mail�m a telefonum
		MailsDAO mailsDao = DAOFactory.createDAO(MailsDAO.class);
		PhoneDAO phoneDAO = DAOFactory.createDAO(PhoneDAO.class);
		MailsMember mails = mailsDao.getMailsMember(id);
		PhonesMeber phones = phoneDAO.getPhonesMember(id);
		
		if (mails != null) {
			for (Mail mail : mails.getMails()) {
				try{
					mailsDao.deleteMail(mail.getId());
					LOGGER.info("Mail id - " + mail.getId() + " smaz�n.");
				}
				catch (Exception e) {
					String message = "CHYBA. Mail id - " + mail.getId() + " nen� smaz�n.";
					LOGGER.warning(message);
					throw new RuntimeException(message, e);					
				}
			}
		}
		if (phones != null) {
			for (Phone phone : phones.getPhones()) {
				try{
					phoneDAO.deletePhone(phone.getId());
					LOGGER.info("Telefon id - " + phone.getId() + " smaz�n.");
				}
				catch (Exception e) {
					String message = "CHYBA. Telefon id - " + phone.getId() + " nen� smaz�n.";
					LOGGER.warning(message);
					throw new RuntimeException(message, e);					
				}			
			}
		}
		//smaz�n� trval�ho bydli�t�
		String dotaz = "DELETE FROM clen_trvala_adresa " + "WHERE id_osoby = ?";
		String zprava;
		try {
			sqlExecutor.deleteRow(dotaz, id);
			zprava ="Trvale bysli�t� smaz�no. id �lena:" + id;
		} catch (Exception e) {
			zprava = "Trval� bydli�t� nesmaz�no id �lena:" + id;
			throw new RuntimeException(zprava, e);
		}		
		LOGGER.info(zprava);
		
		//smazan� rodn�ho ��sla
		dotaz = "DELETE FROM clen_rodne_cislo " + "WHERE id_osoby = ?";
		try {
			sqlExecutor.deleteRow(dotaz, id);
			zprava = "Rodn� ��slo smaz�no. id �lena:" + id;
		} catch (Exception e) {			
			zprava = "Rodn� ��slo bydli�t� nesmaz�no id �lena:" + id;
			throw new RuntimeException(zprava, e);
		}
		LOGGER.warning(zprava);
		
		//smazan� ��sla registrace
		dotaz = "DELETE FROM cshRegC " + "WHERE id_osoby = ?";
		try {
			sqlExecutor.deleteRow(dotaz, id);
			zprava = "��slo registrace smaz�no. id �lena:" + id;
		} 
		catch (Exception e) {
			zprava = "��slo registrace nesmaz�no. id �lena:" + id;
			throw new RuntimeException(zprava, e);
		}
		LOGGER.warning(zprava);
		
		//smazan� �lena ze seznamu
		dotaz = "DELETE FROM clen_seznam " + "WHERE id = ?";	
		sqlExecutor.deleteRow(dotaz, id);
	}
	/**
	 * 
	 * @param member
	 * @return nov� id
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
			LOGGER.info("Vytvo�eno nov� rodn� ��slo id:"+idRC + "k �lenovy id:"+ id);
			dotaz = "insert into clen_trvala_adresa" + " (id_osoby, adresa)" + " values (?, ?)";
			String[] hodnotyTR = { String.valueOf(id),member.getTrvaleBydliste()};
			int idTR = sqlExecutor.insertDotaz(dotaz, hodnotyTR);
			LOGGER.info("Vytvo�eno nov� trval� bydli�t� id:"+idTR + "k �lenovy id:"+ id);
			
			dotaz = "insert into cshRegC" + " (id_osoby, regCislo)" + " values (?, ?)";
			String[] hodnotyReg = { String.valueOf(id),member.getChfRegistrace()};
			int idReg = sqlExecutor.insertDotaz(dotaz, hodnotyReg);
			LOGGER.info("Vytvo�eno nov� ��slo registrace �SH id:"+idReg + "k �lenovy id:"+ id);			
		}
		return id;
	}
	/**
	 * 
	 * @param member
	 *            - objekt ktery m� b�t nahr�n do DB
	 * @param id
	 *            - id member na DB
	 * @return - vrac� po�et zm�n�n�ch ��dku nebo -1 p�i chyb�
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
	 *            - objekt ktery m� b�t nahr�n do DB
	 * @param id
	 *            - id member na DB
	 * @return - vrac� po�et zm�n�n�ch ��dku nebo -1 p�i chyb�
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
	 * Vrac� list tabulky clen seznam z DB
	 * @return
	 */
	public List<Member> getAllMember(){
		List<Member>  list = new ArrayList<>();
		ArrayList<String[]> r = new ArrayList<>();// databaze vr�ti v�sledek do listu
		
		String dotaz = "Select * from clen_seznam ORDER BY clen_seznam.datum_narozeni ASC";
		
		sqlExecutor.getData(dotaz,r);
		
		for (String[] a : r) {			
			Member temp = convertRowToMember(a);
			if(temp == null) {
				LOGGER.warning("Chybn� pole");
			}
			else {
				list.add(temp);
			}		
		}
		
		
		return list;
	}
	/**
	 * Vrac� seznam �len� s kontakty
	 * @return
	 */
	public List<MemberList> getAllMemberList(boolean active, int kategorie){
		List<MemberList>  list = new ArrayList<>();
		ArrayList<String[]> r = new ArrayList<>();// databaze vr�ti v�sledek do listu
		
		MailsDAO mailsDao = DAOFactory.createDAO(MailsDAO.class);
		PhoneDAO phoneDAO = DAOFactory.createDAO(PhoneDAO.class);
		
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
			//String date = sdf.format(cal.getTime());
			
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
				LOGGER.warning("Chybn� pole");
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
		
		MailsDAO mailsDao = DAOFactory.createDAO(MailsDAO.class);
		PhoneDAO phoneDAO = DAOFactory.createDAO(PhoneDAO.class);
		
		Map<Integer, MailsMember> mailsMap = mailsDao.getAllMailMembers();
		Map<Integer, PhonesMeber> phoneMap = phoneDAO.getAllPhonesMembers();
		
		ArrayList<String[]> r = new ArrayList<>();// databaze vr�ti v�sledek do listu
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
				LOGGER.warning("Chybn� pole");
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
	 * vrac� vybran�ho �lena 
	 * @param id
	 * @return
	 */
	public Member getMember(int id) {
		Member member = null;
		ArrayList<String[]> r = new ArrayList<>();// databaze vr�ti v�sledek do listu
		String dotaz ="Select * from clen_seznam"
									+ " where id = " + id  ;
		sqlExecutor.getData(dotaz,r);
		for (String[] a : r) {			
			member = convertRowToMember(a);
		}
				
		return member;
	}
	/**
	 * vrac� vybran�ho �lena s rodn�m ��slem, trval�m bydli�t�m a ��slem registra�n�ho pr�kazu
	 * @param id
	 * @return
	 */
	public MemberFull getMemberFull(int id) {
		MemberFull memberFull = null;
		ArrayList<String[]> r = new ArrayList<>();// databaze vr�ti v�sledek do listu
		String dotaz ="SELECT clen_seznam.id, kjmeno as firstName, pjmeno as lastName, datum_narozeni, poznamka as note,"
										+ " aktivni as active, id_odd_kategorie, zacal, clen_rodne_cislo.rodne_cislo,"
										+ " clen_trvala_adresa.adresa, cshRegC.regCislo FROM `clen_seznam`,`clen_rodne_cislo`,"
										+ "`clen_trvala_adresa`,`cshRegC` "
										+ "WHERE clen_seznam.id = clen_rodne_cislo.id_osoby "
										+ "AND clen_seznam.id = clen_trvala_adresa.id_osoby AND clen_seznam.id = cshRegC.id_osoby "
										+ "AND clen_seznam.id = " + id  ;
		String[] hodnoty = {};
		int countRow =sqlExecutor.getCountRow(dotaz, hodnoty);
		// kdy� je po�et ��dku rovn� 0, tak nastala chyba p�i dotazu z DB, nejsp� chyb� z�znam o p�idan�ch tabulk�ch
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
		MembersDAO membersDAO = DAOFactory.createDAO(MembersDAO.class);
		
		membersDAO.getMemberFull(168);
	}
}
