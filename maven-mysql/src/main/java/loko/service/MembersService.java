package loko.service;

import java.util.List;
import java.util.Map;

import loko.entity.Mail;
import loko.entity.Member;
import loko.entity.Phone;
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

public interface MembersService {

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
	 * Ulo�en� nov�ch entit Member, CSHRegNumber, RodneCislo,
	 * 					 TrvaleBydliste do DB pomoci  p�epravky MemberFull,
	 * 					 kde jsou hodnoty pro tvorbu nov�ch entit Member, CSHRegNumber, RodneCislo,
	 * 					 TrvaleBydliste.
	 * 
	 * @param - MemberFull je p�epravka hodnot pro entity Member, CSHRegNumber, RodneCislo,
	 * 					 TrvaleBydliste.
	 */
	public int addMemberFull(MemberFull member);
	
	/**
	 * Aktualizace udaju v entite Member v DB
	 * 
	 * @param member - zm�n�na entita Member, kter� m� b�t ulo�ena do DB
	 * 
	 */
	public void updateMember(Member member);
	
	/**
	 * Aktualizace �daj� pomoci p�epravky v entit�ch Member, CSHRegNumber, RodneCislo,
	 * 					 TrvaleBydliste.
	 * @param - MemberFull je p�epravka hodnot pro entity Member, CSHRegNumber, RodneCislo,
	 * 					 TrvaleBydliste.
	 *  
	 */
	public void updateMember(MemberFull member);

	/**
	 * Pro nacten� v�ech �len� v seznamu �len� v DB
	 * 
	 * @return - vrac� list napln�n� v�emi entitami Member z DB
	 */
	public List<Member> getAllMember();
	
	/**
	 * Pro na�ten� entit Member, Mail, Phone dle parametru kategorie z DB
	 * 
	 * @param kategorie - mo�nost filtru dle v�kov� kategorie nebo aktivity
	 * 
	 *@return - vraci p�epravku MemberList, kter� obsahuje Member, Mail a Phone
	 *
	 */
	public List<MemberList> getAllMemberList(int kategorie);
	
	/**
	 * Funkce vraci seznam �lenu dle hledan�ch znaku v k�esn�m jm�n� nebo p��jmen�
	 * 
	 * @param name - hledan� znaky
	 * 
	 * @param active - mo�nost filtru dle v�kov� kategorie
	 * 
	 * @param kategorie -  mo�nost filtru dle v�kov� kategorie
	 */
	public List<MemberList> searchAllMembers(String name, int kategorie);
	
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
	public void updatePhone(Phone phone);
	
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
	public void updateMail(Mail mail);

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


}