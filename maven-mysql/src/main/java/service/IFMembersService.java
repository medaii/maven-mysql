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
* Servisní tøída pro DAO (obstaravající skupinu seznam èlenù)
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
	 * Vymazání entity Member
	 * 
	 * @param id -  id entity pro smazáni z DB
	 * 
	 */
	public void deleteMember(int id);

	/**
	 * Pøijmutí pøepravky MemberFull, kde jsou hodnoty pro tvorbu nových entit Member, CSHRegNumber, RodneCislo,
	 * 					 TrvaleBydliste.
	 * 
	 * @param - MemberFull je pøepravka hodnot pro entity Member, CSHRegNumber, RodneCislo,
	 * 					 TrvaleBydliste.
	 */
	public int addMemberFull(MemberFull member);
	
	/**
	 * Aktualizace udaju v entite Member v DB
	 */
	public void updateMember(Member member, int id);
	
	/**
	 * Aktualizace údajù pomoci pøepravky v entitách Member, CSHRegNumber, RodneCislo,
	 * 					 TrvaleBydliste.
	 * @param - MemberFull je pøepravka hodnot pro entity Member, CSHRegNumber, RodneCislo,
	 * 					 TrvaleBydliste.
	 *  
	 */
	public void updateMember(MemberFull member, int id);

	/**
	 * Pro nactení všech èlenù v seznamu èlenù v DB
	 * 
	 * @return - vrací list naplnìný všemi entitami Member z DB
	 */
	public List<Member> getAllMember();
	
	/**
	 * Pro naètení entit Member, Mail, Phone dle parametru active z DB
	 * 
	 * @param active - pri true vypíše jen aktivní èleny
	 * 
	 * @param kategorie - možnost filtru dle vìkové kategorie
	 * 
	 *@return - vraci pøepravku MemberList, který obsahuje Member, Mail a Phone
	 *
	 */
	public List<MemberList> getAllMemberList(boolean active, int kategorie);
	
	/**
	 * Funkce vraci seznam èlenu dle hledaných znaku v køesním jménì nebo pøíjmení
	 * 
	 * @param name - hledané znaky
	 * 
	 * @param active - možnost filtru dle vìkové kategorie
	 * 
	 * @param kategorie -  možnost filtru dle vìkové kategorie
	 */
	public List<MemberList> searchAllMembers(String name, boolean active, int kategorie);
	
	/**
	 * Vrací entitu Member z DB
	 * 
	 * @param id - id požadované entity z DB
	 * 
	 * @return Member - vrací požadovanou entitu z DB
	 */
	public Member getMember(int id);
	
	/**
	 * Vraci požadovanou pøepravku s hodnotami z entit Member,, CSHRegNumber, RodneCislo,
	 * 					 TrvaleBydliste.
	 * 
	 * @param id - id Member pro kterého má být pøipravena pøepravka 
	 * 
	 * @return MemberFull - je pøepravka hodnot pro entity Member, CSHRegNumber, RodneCislo,
	 * 					 TrvaleBydliste.
	 */
	public MemberFull getMemberFull(int id);

	/*
	 * Metody poskytující  PhoneDAO
	 */
	
	/**
	 * Smazání entity Phone v DB
	 * 
	 * @param id - id entity Phone 
	 * 
	 */
	public void deletePhone(int id);
	
	/**
	 * Pøidaní entity Phone do DB
	 * 
	 * @param Phone -  entita Phone, která má být pøidaná do DB
	 */
	public int addPhone(Phone phone);
	
	/**
	 * Aktualizace udajù entity Phone v DB
	 * 
	 * @param phone - entita,kterou má být nahrazen záznam v DB
	 * 
	 */
	public void updatePhone(Phone phone, int id);
	
	/**
	 * Vraceni seznamu telefoních èísel v mapì podle klíèe id Member
	 * 
	 * @return Map<Integer, PhonesMeber> - vrací mapu s klíèem id Member a hodnotou seznam telefoních èísel
	 */
	public Map<Integer, PhonesMeber> getAllPhonesMembers();
	
	/**
	 * Vrací pøepravku naplnìnou entitou Member listem telefoních èísel, který jsou s ním ve vztahu
	 * 
	 * @param id Memeber - id member který požadujeme
	 * 
	 * @return PhonesMeber - vrací pøepravku naplnìnou entitou Member listem telefoních èísel, který jsou s ním ve vztahu
	 */
	public PhonesMeber getPhonesMember(int id_member);
	
	/**
	 * Vrací požadovanou entitu Phone z DB dle id Phone
	 * 
	 * @param id - Id Phone
	 * 
	 */
	public Phone getPhone(int id);
	
	//Metody poskytující  MailDAO	
	/**
	 * Vymaže požadovanou entitu z DB dle id Mail
	 * 
	 * @param id -  id Mail
	 */
	public void deleteMail(int id);
	
	/**
	 * Pøidá novou entitu Mail do DB
	 * 
	 * @param mail - Entita, která má být pøidaná do DB
	 */
	public int addMail(Mail mail);

	/**
	 * Aktualizace udajù entity Mail v DB
	 * 
	 * @param mail - Entita Mail, kterou má být nahrazen záznam v DB
	 * 
	 * @param id - id Mail
	 */
	public void updateMail(Mail mail, int id);

	/**
	 * Vraceni seznamu mailù v mapì podle klíèe id Member
	 * 
	 * @return Map<Integer, MailsMeber> - vrací mapu s klíèem id Member a hodnotou seznam mailù
	 */
	public Map<Integer, MailsMember> getAllMailMembers();

	/**
	 * Vrací pøepravku naplnìnou entitou Member listem mailù, který jsou s ním ve vztahu
	 * 
	 * @param id Memeber - id member který požadujeme
	 * 
	 * @return MailsMeber - vrací pøepravku naplnìnou entitou Member listem mailù, který jsou s ním ve vztahu
	 */
	public MailsMember getMailsMember(int id_member);
	

	/**
	 * Vrací požadovaou entitu Mail z DB
	 * 
	 * @param id - id Mail poždované entity
	 * 
	 * @return Mail - požadovaná entita
	 */
	public Mail getMail(int id);
	
	//user service
	//TODO pøesunout do samostatného servisu

	/**
	 * Aktualizace udajù entity User v DB
	 * 
	 * @param theUser - Entita User, kterou má být nahrazen záznam v DB
	 */
	public void updateUser(User theUser);

	/**
	 * Zmìna stavajícího hesla za nové
	 * 
	 * @param theUser -  Entita, kde má být provedena zmìna
	 */
	public void changePassword(User theUser, String newPassword);
	
	/**
	 * Vraci seznam všech entit User v DB
	 * 
	 * @param userId - Dle pøistupových práv se filtruje seznam pøistupných entit z tabulky users
	 * 
	 * @return List<User> - Seznam entit User
	 * 
	 */
	public List<User> getUsers(boolean admin, int userId);
	/** 
	 * Kontrola shody zadaného hesla a hesla v DB
	 * 
	 * @return boolean - true znamena shodu
	 */
	public boolean authenticate(byte[] password,int id);
	
	/**
	 * Pøidaní nové entity User do DB
	 * 
	 * @param theUser - Entita User, která má být pøidaná do DB
	 */
	public int addUser(User theUser) ;

}