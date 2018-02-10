package loko.dao;

import java.util.List;

import loko.entity.Member;
import loko.value.MemberFull;
import loko.value.MemberList;

public interface IFMembersDAO {
	public int deleteMember(int id);
	/**
	 * 
	 * @param member
	 * @return nov� id
	 */
	public int addMemberFull(MemberFull member);
	/**
	 * 
	 * @param member
	 *            - objekt ktery m� b�t nahr�n do DB
	 * @param id
	 *            - id member na DB
	 * @return - vrac� po�et zm�n�n�ch ��dku nebo -1 p�i chyb�
	 * id, kjmeno, pjmeno, datum_narozeni, poznamka, aktivni, id_odd_kategorie, zacal
	 */
	public int updateMember(Member member, int id);
	/**
	 * 
	 * @param memberfull
	 *            - objekt ktery m� b�t nahr�n do DB
	 * @param id
	 *            - id member na DB
	 * @return - vrac� po�et zm�n�n�ch ��dku nebo -1 p�i chyb�
	 * id, kjmeno, pjmeno, datum_narozeni, poznamka, aktivni, id_odd_kategorie, zacal
	 */
	public int updateMember(MemberFull member, int id);
	/**
	 * Vrac� list tabulky clen seznam z DB
	 * @return
	 */
	public List<Member> getAllMember();
	/**
	 * Vrac� seznam �len� s kontakty
	 * @return
	 */
	public List<MemberList> getAllMemberList(boolean active, int kategorie);
	public List<MemberList> searchAllMembers(String name, boolean active,int kategorie) ;
	/**
	 * vrac� vybran�ho �lena 
	 * @param id
	 * @return
	 */
	public Member getMember(int id);
	/**
	 * vrac� vybran�ho �lena s rodn�m ��slem, trval�m bydli�t�m a ��slem registra�n�ho pr�kazu
	 * @param id
	 * @return
	 */
	public MemberFull getMemberFull(int id);
}
