package loko.dao;

import java.util.List;

import loko.entity.Member;
import loko.value.MemberFull;
import loko.value.MemberList;

/**
 * Rozhrani pro p��stup k dat�m z tabulky clen_seznam.
 * 
 * @author Erik Markovi�
 *
 */

public interface MembersDAO {
	
	/**
	 *  Smazazni zaznamu member z DB
	 * @param id - id entity
	 */
	public void deleteMember(int id);
	
	/**
	 * 
	 * @param memberFull -  objekt pro pro model v GUI vyu�it� pro ulo�en� editovan�ch nebo nov� zadan�ch �daj� do DB
	 * @return id nov� entity Member
	 */
	public int addMemberFull(MemberFull member);
	
	/**
	 * 
	 * @param member - Entita, kde byli zm�n�ny �daje, kter� maj� b�t nahrany do DB
	 * @param id - id member na DB
	 * 
	 * 
	 */
	public void updateMember(Member member, int id);
	
	/**
	 * 
	 * @param memberfull - objekt pro pro model v GUI vyu�it� pro ulo�en� editovan�ch nebo nov� zadan�ch �daj� do DB
	 * @param id - id member na DB
	 */
	public void updateMemberFull(MemberFull member, int id);
	
	/**
	 * Vrac� list tabulky clen seznam z DB
	 * @return - List naplneny entitami Member z DB
	 */
	public List<Member> getAllMember();
	
	/**
	 * Vrac� seznam �len� s kontakty (tabulky Phone a Mail)
	 * @return - vraci list objektu MemberList vyu�it� pro model GUI
	 */
	public List<MemberList> getAllMemberList(boolean active, int kategorie);
	
	/**
	 * 
	 * @param name - hledan� �ast text v sloupcich k�esn�ho jm�na nebo p��jmen�
	 * @param active - filtr aktivn�ch �len�
	 * @param kategorie - filtr v�kov� kategorie
	 * @return - vrac� list objektu MemberList pro model GUI
	 */
	public List<MemberList> searchAllMembers(String name, boolean active,int kategorie) ;
	
	/**
	 * vrac� vybranou entitu Member 
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
