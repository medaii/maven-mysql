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

public interface IFMembersService {

	/**
	 * servis MembersDAO metod
	 */

	int deleteMember(int id);

	int addMemberFull(MemberFull member);

	int updateMember(Member member, int id);

	int updateMember(MemberFull member, int id);

	List<Member> getAllMember();

	List<MemberList> getAllMemberList(boolean active, int kategorie);

	List<MemberList> searchAllMembers(String name, boolean active, int kategorie);

	Member getMember(int id);

	MemberFull getMemberFull(int id);

	/**
	 * servis PhoneDAO
	 */
	int deletePhone(int id);

	int addPhone(Phone phone);

	int updatePhone(Phone phone, int id);

	Map<Integer, PhonesMeber> getAllPhonesMembers();

	PhonesMeber getPhonesMember(int id_member);

	Phone getPhone(int id);

	/**
	 * servis MailDAO
	 */
	int deleteMail(int id);

	int addMail(Mail mail);

	int updateMail(Mail mail, int id);

	Map<Integer, MailsMember> getAllMailMembers();

	MailsMember getMailsMember(int id_member);

	Mail getMail(int id);

	/**
	 * metody UserDAO
	 */
	int updateUser(User theUser);

	int changePassword(User theUser, String newPassword);

	List<User> getUsers(boolean admin, int userId);

	/** 
	 * @return kontrola shody zadan�ho hesla a hesla v DB
	 */
	boolean authenticate(byte[] password, int id); 
	
	/**
	 * @password -  heslo nezakodovane v poli byte
	 * return zakodovan� heslo
	 */
	public String encryptPassword(byte[] password);
	
	int addUser(User theUser);

	

}