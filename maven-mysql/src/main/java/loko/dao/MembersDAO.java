package loko.dao;

import java.util.List;

import loko.entity.CshRegNumber;
import loko.entity.Member;
import loko.entity.RodneCislo;
import loko.entity.TrvaleBydliste;
import loko.value.MemberFull;

/**
 * Rozhrani pro pøístup k datùm z tabulky clen_seznam.
 * 
 * @author Erik Markoviè
 *
 */

public interface MembersDAO {
	
	/**
	 * metoda maže záznam o èlenovy a jeho další udaje jako telefon, mail, trvale
	 * bydlištì, rodné èíslo, cshreg
	 * 
	 * @param id - id entity
	 */
	public void deleteMember(int id);
	
	/**
	 * Provede pøidání entit Member, CshRegNumber, RodneCislo,
	 * 					 TrvaleBydliste do DB
	 * 
	 * @param member - Entita Member, která má být uložena do DB
	 * 
	 * @param rodneCislo - Entita RodneCislo, která má být uložena do DB
	 * 
	 * @param trvaleBydliste - Entita TrvaleBydliste, která má být uložena do DB
	 * 
	 * @param cshRegNumber - Entita CshRegNumber, která má být uložena do DB
	 * 
	 * @return - vrací id nové entity Member
	 */
	public int addMemberFull(Member member, RodneCislo rodneCislo, 
														TrvaleBydliste trvaleBydliste, CshRegNumber cshRegNumber);
	
	/**
	 * Aktualizace zaznamu entity Member
	 * 
	 * @param member - Entita, kde byli zmìnìny údaje, které mají být nahrany do DB
	 * 
	 */
	public void updateMember(Member member);
	
	/**
	 * Provede aktualizaci entit Member, CshRegNumber, RodneCislo,
	 * 					 TrvaleBydliste do DB
	 * 
	 * 
	 * @param member - Entita Member, která má být uložena do DB
	 * 
	 * @param rodneCislo - Entita RodneCislo, která má být uložena do DB
	 * 
	 * @param trvaleBydliste - Entita TrvaleBydliste, která má být uložena do DB
	 * 
	 * @param cshRegNumber - Entita CshRegNumber, která má být uložena do DB
	 * 
	 */
	public void updateMemberFull(Member member, RodneCislo rodneCislo,
																TrvaleBydliste trvaleBydliste, CshRegNumber cshRegNumber);
	
	/**
	 * Vrací list tabulky clen seznam z DB
	 * 
	 * @return - List naplneny entitami Member z DB
	 */
	public List<Member> getAllMember();
	
	/**
	 * Vrací seznam èlenù s kontakty (tabulky Phone a Mail)
	 * 
	 * 
	 * 
	 * @return - vraci list objektu MemberList využitý pro model GUI
	 */
	public List<Member> getAllMemberList(int kategorie);
	
	/**
	 * Vrac9 list entir Member dle kategorie
	 * 
	 * @param kategorie - filtr dle zvolené kategorie
	 * 
	 * @return - vrací list entit member dle kategorie
	 */
	public List<Member> searchAllMembers(String name,int kategorie) ;
	
	/**
	 * vrací vybranou entitu Member 
	 * 
	 * @param id - id entity Member
	 * @return vraci Member
	 */
	public Member getMember(int id);
	
	/**
	 * vrací vybraného èlena s rodným èíslem, trvalím bydlištìm a èíslem registraèního prùkazu
	 * @param id - id entity Member
	 * @return - objekt MemberFull pro model GUI
	 */
	public MemberFull getMemberFull(int id);
}
