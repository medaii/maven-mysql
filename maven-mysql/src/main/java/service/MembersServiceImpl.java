package service;

import java.util.List;
import java.util.Map;

import loko.dao.DAOFactory;
import loko.dao.IFMailsDAO;
import loko.dao.IFMembersDAO;
import loko.dao.IFPhoneDAO;
import loko.dao.IFUserDAO;
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
 * @author Erik Markovic
 *
 *Servisn� t��da pro DAO
 */
public class MembersServiceImpl implements IFMembersService {

	private IFMembersDAO membersDAO;
	private IFMailsDAO mailsDAO;
	private IFPhoneDAO phoneDAO;
	private IFUserDAO userDAO;

	//membersDAO = DAOFactory.createDAO(IFMembersDAO.class);
	
	public MembersServiceImpl() {
		membersDAO = (IFMembersDAO)DAOFactory.createDAO(IFMembersDAO.class);
		this.mailsDAO = (IFMailsDAO)DAOFactory.createDAO(IFMailsDAO.class);
		this.phoneDAO = (IFPhoneDAO)DAOFactory.createDAO(IFPhoneDAO.class);
		this.userDAO = (IFUserDAO)DAOFactory.createDAO(IFUserDAO.class); 
		
	}
	/* (non-Javadoc)
	 * @see service.IFMembersService#deleteMember(int)
	 */
	
	// mmembers service
	@Override
	public void deleteMember(int id) {
		membersDAO.deleteMember(id);
	}

	/* (non-Javadoc)
	 * @see service.IFMembersService#addMemberFull(loko.core.MemberFull)
	 */
	@Override
	public int addMemberFull(MemberFull member) {
		return membersDAO.addMemberFull(member);
	}

	/* (non-Javadoc)
	 * @see service.IFMembersService#updateMember(loko.core.Member, int)
	 */
	@Override
	public int updateMember(Member member, int id) {
		return membersDAO.updateMember(member, id);
	}

	/* (non-Javadoc)
	 * @see service.IFMembersService#updateMember(loko.core.MemberFull, int)
	 */
	@Override
	public int updateMember(MemberFull member, int id) {
		return membersDAO.updateMember(member, id);
	}

	/* (non-Javadoc)
	 * @see service.IFMembersService#getAllMember()
	 */
	@Override
	public List<Member> getAllMember() {
		return membersDAO.getAllMember();
	}

	/* (non-Javadoc)
	 * @see service.IFMembersService#getAllMemberList(boolean, int)
	 */
	@Override
	public List<MemberList> getAllMemberList(boolean active, int kategorie) {
		return membersDAO.getAllMemberList(active, kategorie);
	}

	/* (non-Javadoc)
	 * @see service.IFMembersService#searchAllMembers(java.lang.String, boolean, int)
	 */
	@Override
	public List<MemberList> searchAllMembers(String name, boolean active, int kategorie) {
		return membersDAO.searchAllMembers(name, active, kategorie);
	}

	/* (non-Javadoc)
	 * @see service.IFMembersService#getMember(int)
	 */
	@Override
	public Member getMember(int id) {
		return membersDAO.getMember(id);
	}

	/* (non-Javadoc)
	 * @see service.IFMembersService#getMemberFull(int)
	 */
	@Override
	public MemberFull getMemberFull(int id) {
		return membersDAO.getMemberFull(id);
	}

	/* (non-Javadoc)
	 * @see service.IFMembersService#deletePhone(int)
	 */
	
	// phone service
	@Override
	public void deletePhone(int id) {
		phoneDAO.deletePhone(id);
	}

	/* (non-Javadoc)
	 * @see service.IFMembersService#addPhone(loko.core.Phone)
	 */
	@Override
	public int addPhone(Phone phone) {
		return phoneDAO.addPhone(phone);
	}

	/* (non-Javadoc)
	 * @see service.IFMembersService#updatePhone(loko.core.Phone, int)
	 */
	@Override
	public int updatePhone(Phone phone, int id) {
		return phoneDAO.updatePhone(phone, id);
	}

	/* (non-Javadoc)
	 * @see service.IFMembersService#getAllPhonesMembers()
	 */
	@Override
	public Map<Integer, PhonesMeber> getAllPhonesMembers() {
		return phoneDAO.getAllPhonesMembers();
	}

	/* (non-Javadoc)
	 * @see service.IFMembersService#getPhonesMember(int)
	 */
	@Override
	public PhonesMeber getPhonesMember(int id_member) {
		return phoneDAO.getPhonesMember(id_member);
	}

	/* (non-Javadoc)
	 * @see service.IFMembersService#getPhone(int)
	 */
	@Override
	public Phone getPhone(int id) {
		return phoneDAO.getPhone(id);
	}

	/* (non-Javadoc)
	 * @see service.IFMembersService#deleteMail(int)
	 */
	@Override
	public void deleteMail(int id) {
		mailsDAO.deleteMail(id);
	}

	/* (non-Javadoc)
	 * @see service.IFMembersService#addMail(loko.core.Mail)
	 */
	
	//mail sevice
	@Override
	public int addMail(Mail mail) {
		return mailsDAO.addMail(mail);
	}

	/* (non-Javadoc)
	 * @see service.IFMembersService#updateMail(loko.core.Mail, int)
	 */
	@Override
	public int updateMail(Mail mail, int id) {
		return mailsDAO.updateMail(mail, id);
	}

	/* (non-Javadoc)
	 * @see service.IFMembersService#getAllMailMembers()
	 */
	@Override
	public Map<Integer, MailsMember> getAllMailMembers() {
		return mailsDAO.getAllMailMembers();
	}

	/* (non-Javadoc)
	 * @see service.IFMembersService#getMailsMember(int)
	 */
	@Override
	public MailsMember getMailsMember(int id_member) {
		return mailsDAO.getMailsMember(id_member);
	}

	/* (non-Javadoc)
	 * @see service.IFMembersService#getMail(int)
	 */
	@Override
	public Mail getMail(int id) {
		return mailsDAO.getMail(id);
	}
	
	/* (non-Javadoc)
	 * @see service.IFMembersService#updateUser(loko.core.User)
	 */
	
	//user service
	@Override
	public void updateUser(User theUser) {
		userDAO.updateUser(theUser);
	}
	/* (non-Javadoc)
	 * @see service.IFMembersService#changePassword(loko.core.User, java.lang.String)
	 */
	@Override
	public void changePassword(User theUser, String newPassword) {
		userDAO.changePassword(theUser, newPassword);
	}
	/* (non-Javadoc)
	 * @see service.IFMembersService#getUsers(boolean, int)
	 */
	@Override
	public List<User> getUsers(boolean admin, int userId) {
		return userDAO.getUsers(admin, userId);
	}
	/** 
	 * @return kontrola shody zadan�ho hesla a hesla v DB
	 */
	@Override
	public boolean authenticate(byte[] password,int id) {
		return userDAO.authenticate(password, id);
	}
	/**
	 * @password -  heslo nezakodovane v poli byte
	 * return zakodovan� heslo
	 */
	public String encryptPassword(byte[] password) {
		return PasswordUtils.encryptPassword(password);
	}
	
	@Override
	public int addUser(User theUser) {		
		return userDAO.addUser(theUser);
	}
}
