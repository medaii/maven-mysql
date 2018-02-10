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
	 * @return nové id
	 */
	public int addMemberFull(MemberFull member);
	/**
	 * 
	 * @param member
	 *            - objekt ktery má být nahrán do DB
	 * @param id
	 *            - id member na DB
	 * @return - vrací poèet zmìnìných øádku nebo -1 pøi chybì
	 * id, kjmeno, pjmeno, datum_narozeni, poznamka, aktivni, id_odd_kategorie, zacal
	 */
	public int updateMember(Member member, int id);
	/**
	 * 
	 * @param memberfull
	 *            - objekt ktery má být nahrán do DB
	 * @param id
	 *            - id member na DB
	 * @return - vrací poèet zmìnìných øádku nebo -1 pøi chybì
	 * id, kjmeno, pjmeno, datum_narozeni, poznamka, aktivni, id_odd_kategorie, zacal
	 */
	public int updateMember(MemberFull member, int id);
	/**
	 * Vrací list tabulky clen seznam z DB
	 * @return
	 */
	public List<Member> getAllMember();
	/**
	 * Vrací seznam èlenù s kontakty
	 * @return
	 */
	public List<MemberList> getAllMemberList(boolean active, int kategorie);
	public List<MemberList> searchAllMembers(String name, boolean active,int kategorie) ;
	/**
	 * vrací vybraného èlena 
	 * @param id
	 * @return
	 */
	public Member getMember(int id);
	/**
	 * vrací vybraného èlena s rodným èíslem, trvalím bydlištìm a èíslem registraèního prùkazu
	 * @param id
	 * @return
	 */
	public MemberFull getMemberFull(int id);
}
