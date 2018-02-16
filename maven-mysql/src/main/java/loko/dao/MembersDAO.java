package loko.dao;

import java.util.List;

import loko.entity.CshRegNumber;
import loko.entity.Member;
import loko.entity.RodneCislo;
import loko.entity.TrvaleBydliste;
import loko.value.MemberFull;

/**
 * Rozhrani pro p��stup k dat�m z tabulky clen_seznam.
 * 
 * @author Erik Markovi�
 *
 */

public interface MembersDAO {
	
	/**
	 * metoda ma�e z�znam o �lenovy a jeho dal�� udaje jako telefon, mail, trvale
	 * bydli�t�, rodn� ��slo, cshreg
	 * 
	 * @param id - id entity
	 */
	public void deleteMember(int id);
	
	/**
	 * Provede p�id�n� entit Member, CshRegNumber, RodneCislo,
	 * 					 TrvaleBydliste do DB
	 * 
	 * @param member - Entita Member, kter� m� b�t ulo�ena do DB
	 * 
	 * @param rodneCislo - Entita RodneCislo, kter� m� b�t ulo�ena do DB
	 * 
	 * @param trvaleBydliste - Entita TrvaleBydliste, kter� m� b�t ulo�ena do DB
	 * 
	 * @param cshRegNumber - Entita CshRegNumber, kter� m� b�t ulo�ena do DB
	 * 
	 * @return - vrac� id nov� entity Member
	 */
	public int addMemberFull(Member member, RodneCislo rodneCislo, 
														TrvaleBydliste trvaleBydliste, CshRegNumber cshRegNumber);
	
	/**
	 * Aktualizace zaznamu entity Member
	 * 
	 * @param member - Entita, kde byli zm�n�ny �daje, kter� maj� b�t nahrany do DB
	 * 
	 */
	public void updateMember(Member member);
	
	/**
	 * Provede aktualizaci entit Member, CshRegNumber, RodneCislo,
	 * 					 TrvaleBydliste do DB
	 * 
	 * 
	 * @param member - Entita Member, kter� m� b�t ulo�ena do DB
	 * 
	 * @param rodneCislo - Entita RodneCislo, kter� m� b�t ulo�ena do DB
	 * 
	 * @param trvaleBydliste - Entita TrvaleBydliste, kter� m� b�t ulo�ena do DB
	 * 
	 * @param cshRegNumber - Entita CshRegNumber, kter� m� b�t ulo�ena do DB
	 * 
	 */
	public void updateMemberFull(Member member, RodneCislo rodneCislo,
																TrvaleBydliste trvaleBydliste, CshRegNumber cshRegNumber);
	
	/**
	 * Vrac� list tabulky clen seznam z DB
	 * 
	 * @return - List naplneny entitami Member z DB
	 */
	public List<Member> getAllMember();
	
	/**
	 * Vrac� seznam �len� s kontakty (tabulky Phone a Mail)
	 * 
	 * 
	 * 
	 * @return - vraci list objektu MemberList vyu�it� pro model GUI
	 */
	public List<Member> getAllMemberList(int kategorie);
	
	/**
	 * Vrac9 list entir Member dle kategorie
	 * 
	 * @param kategorie - filtr dle zvolen� kategorie
	 * 
	 * @return - vrac� list entit member dle kategorie
	 */
	public List<Member> searchAllMembers(String name,int kategorie) ;
	
	/**
	 * vrac� vybranou entitu Member 
	 * 
	 * @param id - id entity Member
	 * @return vraci Member
	 */
	public Member getMember(int id);
	
	/**
	 * vrac� vybran�ho �lena s rodn�m ��slem, trval�m bydli�t�m a ��slem registra�n�ho pr�kazu
	 * @param id - id entity Member
	 * @return - objekt MemberFull pro model GUI
	 */
	public MemberFull getMemberFull(int id);
}
