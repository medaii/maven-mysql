package service;

import java.util.List;
import java.util.Map;

import loko.DAO.DAOFactory;
import loko.DAO.IFMailsDAO;
import loko.DAO.IFMembersDAO;
import loko.DAO.IFPhoneDAO;
import loko.DAO.IFUserDAO;
import loko.DAO.UserDAO;
import loko.core.Mail;
import loko.core.MailsMember;
import loko.core.Member;
import loko.core.MemberFull;
import loko.core.MemberList;
import loko.core.Phone;
import loko.core.PhonesMeber;
import loko.core.User;
/**
 * 
 * @author Erik Markovic
 *
 *Servisní tøída pro DAO
 */
public class MembersServiceImpl implements IFMembersService {

	private IFMembersDAO membersDAO;
	//private IFMailsDAO mailsDAO;
	//private IFPhoneDAO phoneDAO;
	private IFUserDAO userDAO;

	//membersDAO = DAOFactory.createDAO(IFMembersDAO.class);
	
	public MembersServiceImpl() {
		membersDAO = (IFMembersDAO)DAOFactory.createDAO(IFMembersDAO.class);
		this.userDAO = new UserDAO(); // pridat do Factory
	}
	/**
	 * servis MembersDAO metod
	 */
	
	@Override
	public int deleteMember(int id) {
		return membersDAO.deleteMember(id);
	}

	@Override
	public int addMemberFull(MemberFull member) {
		return membersDAO.addMemberFull(member);
	}

	@Override
	public int updateMember(Member member, int id) {
		return membersDAO.updateMember(member, id);
	}

	@Override
	public int updateMember(MemberFull member, int id) {
		return membersDAO.updateMember(member, id);
	}

	@Override
	public List<Member> getAllMember() {
		return membersDAO.getAllMember();
	}

	@Override
	public List<MemberList> getAllMemberList(boolean active, int kategorie) {
		return membersDAO.getAllMemberList(active, kategorie);
	}

	@Override
	public List<MemberList> searchAllMembers(String name, boolean active, int kategorie) {
		return membersDAO.searchAllMembers(name, active, kategorie);
	}

	@Override
	public Member getMember(int id) {
		return membersDAO.getMember(id);
	}

	@Override
	public MemberFull getMemberFull(int id) {
		return membersDAO.getMemberFull(id);
	}

	/**
	 * servis PhoneDAO
	 */
	@Override
	public int deletePhone(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addPhone(Phone phone) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updatePhone(Phone phone, int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<Integer, PhonesMeber> getAllPhonesMembers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PhonesMeber getPhonesMember(int id_member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Phone getPhone(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteMail(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addMail(Mail mail) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateMail(Mail mail, int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<Integer, MailsMember> getAllMailMembers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MailsMember getMailsMember(int id_member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mail getMail(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * metody UserDAO
	 */
	@Override
	public int updateUser(User theUser) {
		return userDAO.updateUser(theUser);
	}
	@Override
	public int changePassword(User theUser, String newPassword) {
		return userDAO.changePassword(theUser, newPassword);
	}
	@Override
	public List<User> getUsers(boolean admin, int userId) {
		return userDAO.getUsers(admin, userId);
	}
	@Override
	public boolean authenticate(User theUser) {
		return userDAO.authenticate(theUser);
	}


}
