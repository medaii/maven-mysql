package loko.dao;

import java.util.List;

import loko.entity.Member;
import loko.value.MemberFull;
import loko.value.MemberList;

public interface MembersDAO {
	
	/**
	 *  Smazazni zaznamu member z DB
	 * @param id - id entity
	 */
	public void deleteMember(int id);
	
	/**
	 * 
	 * @param memberFull -  objekt pro pro model v GUI využitý pro uložení editovaných nebo novì zadaných údajù do DB
	 * @return id nové entity Member
	 */
	public int addMemberFull(MemberFull member);
	
	/**
	 * 
	 * @param member - Entita, kde byli zmìnìny údaje, které mají být nahrany do DB
	 * @param id - id member na DB
	 * 
	 * 
	 */
	public void updateMember(Member member, int id);
	
	/**
	 * 
	 * @param memberfull - objekt pro pro model v GUI využitý pro uložení editovaných nebo novì zadaných údajù do DB
	 * @param id - id member na DB
	 */
	public void updateMember(MemberFull member, int id);
	
	/**
	 * Vrací list tabulky clen seznam z DB
	 * @return - List naplneny entitami Member z DB
	 */
	public List<Member> getAllMember();
	
	/**
	 * Vrací seznam èlenù s kontakty (tabulky Phone a Mail)
	 * @return - vraci list objektu MemberList využitý pro model GUI
	 */
	public List<MemberList> getAllMemberList(boolean active, int kategorie);
	
	/**
	 * 
	 * @param name - hledaný èast text v sloupcich køesního jména nebo pøíjmení
	 * @param active - filtr aktivních èlenù
	 * @param kategorie - filtr vìkové kategorie
	 * @return - vrací list objektu MemberList pro model GUI
	 */
	public List<MemberList> searchAllMembers(String name, boolean active,int kategorie) ;
	
	/**
	 * vrací vybranou entitu Member 
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
