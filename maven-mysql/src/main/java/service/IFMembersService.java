package service;

import java.util.List;
import java.util.Map;

import loko.entity.Mail;
import loko.entity.Member;
import loko.entity.Phone;
import loko.entity.User;
import loko.value.MailsMember;
import loko.value.MemberFull;
import loko.value.MemberList;
import loko.value.PhonesMeber;

/**
*
* Servisn� t��da pro DAO (obstaravaj�c� skupinu seznam �len�)
* 
* @author Erik Markovic
*
*
*/

public interface IFMembersService {

	/**
	 * metody poskytnute MembersDAO
	 */
	
	/**
	 * Vymaz�n� entity Member
	 * 
	 * @param id -  id entity pro smaz�ni z DB
	 * 
	 */
	public void deleteMember(int id);

	/**
	 * P�ijmut� p�epravky MemberFull, kde jsou hodnoty pro tvorbu nov�ch entit Member, CSHRegNumber, RodneCislo,
	 * 					 TrvaleBydliste.
	 * 
	 * @param - MemberFull je p�epravka hodnot pro entity Member, CSHRegNumber, RodneCislo,
	 * 					 TrvaleBydliste.
	 */
	public int addMemberFull(MemberFull member);
	
	/**
	 * Aktualizace udaju v entite Member v DB
	 */
	public void updateMember(Member member, int id);
	
	/**
	 * Aktualizace �daj� pomoci p�epravky v entit�ch Member, CSHRegNumber, RodneCislo,
	 * 					 TrvaleBydliste.
	 * @param - MemberFull je p�epravka hodnot pro entity Member, CSHRegNumber, RodneCislo,
	 * 					 TrvaleBydliste.
	 *  
	 */
	public void updateMember(MemberFull member, int id);

	/**
	 * Pro nacten� v�ech �len� v seznamu �len� v DB
	 * 
	 * @return - vrac� list napln�n� v�emi entitami Member z DB
	 */
	public List<Member> getAllMember();
	
	/**
	 * Pro na�ten� entit Member, Mail, Phone dle parametru active z DB
	 * 
	 * @param active - pri true vyp�e jen aktivn� �leny
	 * 
	 * @param kategorie - mo�nost filtru dle v�kov� kategorie
	 * 
	 *@return - vraci p�epravku MemberList, kter� obsahuje Member, Mail a Phone
	 *
	 */
	public List<MemberList> getAllMemberList(boolean active, int kategorie);
	
	/**
	 * Funkce vraci seznam �lenu dle hledan�ch znaku v k�esn�m jm�n� nebo p��jmen�
	 * 
	 * @param name - hledan� znaky
	 * 
	 * @param active - mo�nost filtru dle v�kov� kategorie
	 * 
	 * @param kategorie -  mo�nost filtru dle v�kov� kategorie
	 */
	public List<MemberList> searchAllMembers(String name, boolean active, int kategorie);
	
	/**
	 * Vrac� entitu Member z DB
	 * 
	 * @param id - id po�adovan� entity z DB
	 * 
	 * @return Member - vrac� po�adovanou entitu z DB
	 */
	public Member getMember(int id);
	
	/**
	 * Vraci po�adovanou p�epravku s hodnotami z entit Member,, CSHRegNumber, RodneCislo,
	 * 					 TrvaleBydliste.
	 * 
	 * @param id - id Member pro kter�ho m� b�t p�ipravena p�epravka 
	 * 
	 * @return MemberFull - je p�epravka hodnot pro entity Member, CSHRegNumber, RodneCislo,
	 * 					 TrvaleBydliste.
	 */
	public MemberFull getMemberFull(int id);

	/*
	 * Metody poskytuj�c�  PhoneDAO
	 */
	
	/**
	 * Smaz�n� entity Phone v DB
	 * 
	 * @param id - id entity Phone 
	 * 
	 */
	public void deletePhone(int id);
	
	/**
	 * P�idan� entity Phone do DB
	 * 
	 * @param Phone -  entita Phone, kter� m� b�t p�idan� do DB
	 */
	public int addPhone(Phone phone);
	
	/**
	 * Aktualizace udaj� entity Phone v DB
	 * 
	 * @param phone - entita,kterou m� b�t nahrazen z�znam v DB
	 * 
	 */
	public void updatePhone(Phone phone, int id);
	
	/**
	 * Vraceni seznamu telefon�ch ��sel v map� podle kl��e id Member
	 * 
	 * @return Map<Integer, PhonesMeber> - vrac� mapu s kl��em id Member a hodnotou seznam telefon�ch ��sel
	 */
	public Map<Integer, PhonesMeber> getAllPhonesMembers();
	
	/**
	 * Vrac� p�epravku napln�nou entitou Member listem telefon�ch ��sel, kter� jsou s n�m ve vztahu
	 * 
	 * @param id Memeber - id member kter� po�adujeme
	 * 
	 * @return PhonesMeber - vrac� p�epravku napln�nou entitou Member listem telefon�ch ��sel, kter� jsou s n�m ve vztahu
	 */
	public PhonesMeber getPhonesMember(int id_member);
	
	/**
	 * Vrac� po�adovanou entitu Phone z DB dle id Phone
	 * 
	 * @param id - Id Phone
	 * 
	 */
	public Phone getPhone(int id);
	
	//Metody poskytuj�c�  MailDAO	
	/**
	 * Vyma�e po�adovanou entitu z DB dle id Mail
	 * 
	 * @param id -  id Mail
	 */
	public void deleteMail(int id);
	
	/**
	 * P�id� novou entitu Mail do DB
	 * 
	 * @param mail - Entita, kter� m� b�t p�idan� do DB
	 */
	public int addMail(Mail mail);

	/**
	 * Aktualizace udaj� entity Mail v DB
	 * 
	 * @param mail - Entita Mail, kterou m� b�t nahrazen z�znam v DB
	 * 
	 * @param id - id Mail
	 */
	public void updateMail(Mail mail, int id);

	/**
	 * Vraceni seznamu mail� v map� podle kl��e id Member
	 * 
	 * @return Map<Integer, MailsMeber> - vrac� mapu s kl��em id Member a hodnotou seznam mail�
	 */
	public Map<Integer, MailsMember> getAllMailMembers();

	/**
	 * Vrac� p�epravku napln�nou entitou Member listem mail�, kter� jsou s n�m ve vztahu
	 * 
	 * @param id Memeber - id member kter� po�adujeme
	 * 
	 * @return MailsMeber - vrac� p�epravku napln�nou entitou Member listem mail�, kter� jsou s n�m ve vztahu
	 */
	public MailsMember getMailsMember(int id_member);
	

	/**
	 * Vrac� po�adovaou entitu Mail z DB
	 * 
	 * @param id - id Mail po�dovan� entity
	 * 
	 * @return Mail - po�adovan� entita
	 */
	public Mail getMail(int id);
	
	//user service
	//TODO p�esunout do samostatn�ho servisu

	/**
	 * Aktualizace udaj� entity User v DB
	 * 
	 * @param theUser - Entita User, kterou m� b�t nahrazen z�znam v DB
	 */
	public void updateUser(User theUser);

	/**
	 * Zm�na stavaj�c�ho hesla za nov�
	 * 
	 * @param theUser -  Entita, kde m� b�t provedena zm�na
	 */
	public void changePassword(User theUser, String newPassword);
	
	/**
	 * Vraci seznam v�ech entit User v DB
	 * 
	 * @param userId - Dle p�istupov�ch pr�v se filtruje seznam p�istupn�ch entit z tabulky users
	 * 
	 * @return List<User> - Seznam entit User
	 * 
	 */
	public List<User> getUsers(boolean admin, int userId);
	/** 
	 * Kontrola shody zadan�ho hesla a hesla v DB
	 * 
	 * @return boolean - true znamena shodu
	 */
	public boolean authenticate(byte[] password,int id);
	
	/**
	 * P�idan� nov� entity User do DB
	 * 
	 * @param theUser - Entita User, kter� m� b�t p�idan� do DB
	 */
	public int addUser(User theUser) ;

}