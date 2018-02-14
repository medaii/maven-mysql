package service;

import java.util.List;
import java.util.Map;

import loko.dao.DAOFactory;
import loko.dao.MailsDAO;
import loko.dao.MembersDAO;
import loko.dao.PhoneDAO;
import loko.dao.UserDAO;
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
public class MembersServiceImpl implements IFMembersService {
	
	// instance na DAO dle entit
	private MembersDAO membersDAO;
	private MailsDAO mailsDAO;
	private PhoneDAO phoneDAO;
	private UserDAO userDAO;

	// instance se žádají od factory, která posle instanci na DAO pomoci JDBC nebo Hibernate
	public MembersServiceImpl() {
		membersDAO = (MembersDAO)DAOFactory.createDAO(MembersDAO.class);
		this.mailsDAO = (MailsDAO)DAOFactory.createDAO(MailsDAO.class);
		this.phoneDAO = (PhoneDAO)DAOFactory.createDAO(PhoneDAO.class);
		this.userDAO = (UserDAO)DAOFactory.createDAO(UserDAO.class); 
		
	}
	/*
	 * metody poskytnute MembersDAO
	 */
	
	@Override
	public void deleteMember(int id) {
		membersDAO.deleteMember(id);
	}

	@Override
	public int addMemberFull(MemberFull member) {
		return membersDAO.addMemberFull(member);
	}

	@Override
	public void updateMember(Member member, int id) {
		membersDAO.updateMember(member, id);
	}

	@Override
	public void updateMember(MemberFull member, int id) {
		membersDAO.updateMemberFull(member, id);
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
		//TODO dodìlat v implementaci filtrace dle parametru active a kategorie
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

	/*
	 * Metody poskytující  PhoneDAO
	 */
	
	@Override
	public void deletePhone(int id) {
		phoneDAO.deletePhone(id);
	}

	@Override
	public int addPhone(Phone phone) {
		return phoneDAO.addPhone(phone);
	}

	@Override
	public void updatePhone(Phone phone, int id) {
		//TODO vyøešit nadbiteènost parametru id
		 phoneDAO.updatePhone(phone, id);
	}

	@Override
	public Map<Integer, PhonesMeber> getAllPhonesMembers() {
		return phoneDAO.getAllPhonesMembers();
	}

	@Override
	public PhonesMeber getPhonesMember(int id_member) {
		return phoneDAO.getPhonesMember(id_member);
	}

	@Override
	public Phone getPhone(int id) {
		return phoneDAO.getPhone(id);
	}

	//Metody poskytující  MailDAO	
	
	@Override
	public void deleteMail(int id) {
		mailsDAO.deleteMail(id);
	}

	@Override
	public int addMail(Mail mail) {
		return mailsDAO.addMail(mail);
	}


	@Override
	public void updateMail(Mail mail, int id) {
		mailsDAO.updateMail(mail, id);
	}

	@Override
	public Map<Integer, MailsMember> getAllMailMembers() {
		return mailsDAO.getAllMailMembers();
	}

	@Override
	public MailsMember getMailsMember(int id_member) {
		return mailsDAO.getMailsMember(id_member);
	}


	@Override
	public Mail getMail(int id) {
		return mailsDAO.getMail(id);
	}
	
	//user service
	//TODO pøesunout do samostatného servisu

	@Override
	public void updateUser(User theUser) {
		userDAO.updateUser(theUser);
	}

	@Override
	public void changePassword(User theUser, String newPassword) {
		userDAO.changePassword(theUser, newPassword);
	}
	

	@Override
	public List<User> getUsers(boolean admin, int userId) {
		return userDAO.getUsers(admin, userId);
	}

	@Override
	public boolean authenticate(byte[] password,int id) {
		return userDAO.authenticate(password, id);
	}

	@Override
	public int addUser(User theUser) {		
		return userDAO.addUser(theUser);
	}
}
