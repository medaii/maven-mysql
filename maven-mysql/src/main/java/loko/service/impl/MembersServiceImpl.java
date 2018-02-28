package loko.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import loko.dao.MailsDAO;
import loko.dao.MembersDAO;
import loko.dao.PhoneDAO;
import loko.entity.CshRegNumber;
import loko.entity.Mail;
import loko.entity.Member;
import loko.entity.Phone;
import loko.entity.RodneCislo;
import loko.entity.TrvaleBydliste;
import loko.service.MembersService;
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
@Service
public class MembersServiceImpl implements MembersService {

	// instance na DAO dle entit
	@Autowired
	private MembersDAO membersDAO;
	
	@Autowired
	private MailsDAO mailsDAO;
	
	@Autowired
	private PhoneDAO phoneDAO;

	// instance se žádají od factory, která posle instanci na DAO pomoci JDBC nebo
	// Hibernate
	
	public MembersServiceImpl(MembersDAO membersDAO, MailsDAO mailsDAO, PhoneDAO phoneDAO) {
		this.membersDAO = membersDAO;
		this.mailsDAO = mailsDAO;
		this.phoneDAO = phoneDAO;
	}

	/*
	 * metody poskytnute MembersDAO
	 */

	@Transactional
	@Override
	public void deleteMember(int id) {
		membersDAO.deleteMember(id);
	}

	@Transactional
	@Override
	public int addMemberFull(MemberFull memberFull) {
		// vytvoøení z pøepravky MemberFull entitu Member
		Member member = new Member(memberFull.getFirstName(), memberFull.getLastName(), memberFull.getBirthDay(),
				memberFull.getNote(), memberFull.getActive(), memberFull.getId_odd_kategorie(), memberFull.getEnterDate());
		// vytvoøení entity Rodné èíslo
		RodneCislo rodneCislo = new RodneCislo(memberFull.getRodneCislo());

		// vytvoøení entity Trvalé bydlištì
		TrvaleBydliste trvaleBydliste = new TrvaleBydliste(memberFull.getTrvaleBydliste());

		// vytvoøení entity CSHregistracniCislo
		CshRegNumber cshRegNumber = new CshRegNumber(memberFull.getChfRegistrace());

		return membersDAO.addMemberFull(member, rodneCislo, trvaleBydliste, cshRegNumber);
	}
	@Transactional
	@Override
	public void updateMember(Member member) {
		membersDAO.updateMember(member);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void updateMember(MemberFull memberFull) {
		// vytvoøení z pøepravky MemberFull entitu Member
		Member member = new Member(memberFull.getId(), memberFull.getFirstName(), memberFull.getLastName(),
				memberFull.getBirthDay(), memberFull.getNote(), memberFull.getActive(), memberFull.getId_odd_kategorie(),
				memberFull.getEnterDate());

		// vytvoøení entity Rodné èíslo
		RodneCislo rodneCislo = new RodneCislo(memberFull.getRodneCislo());

		// vytvoøení entity Trvalé bydlištì
		TrvaleBydliste trvaleBydliste = new TrvaleBydliste(memberFull.getTrvaleBydliste());

		// vytvoøení entity CSHregistracniCislo
		CshRegNumber cshRegNumber = new CshRegNumber(memberFull.getChfRegistrace());

		membersDAO.updateMemberFull(member, rodneCislo, trvaleBydliste, cshRegNumber);
	}

	@Transactional
	@Override
	public List<Member> getAllMember() {
		return membersDAO.getAllMember();
	}
	
	@Transactional
	@Override
	public List<MemberList> getAllMemberList(int kategorie) {
		List<MemberList> list = new ArrayList<>();
		// naètení listu entit Member dle vybrané kategorie
		List<Member> members = membersDAO.getAllMemberList(kategorie);

		// nactení k vybraným entitám Member telefony
		Map<Integer, MailsMember> mailsMap = mailsDAO.getAllMailMembers();

		// nactení k vybraným entitám Member maily
		Map<Integer, PhonesMeber> phoneMap = phoneDAO.getAllPhonesMembers();
		for (Member member : members) {
			if (member == null) {
				throw new RuntimeException("Neinicializován list s Member.");
			} else {

				List<Mail> mails;
				List<Phone> phones;
				if (mailsMap.containsKey(member.getId())) {
					MailsMember mailsMember = mailsMap.get(member.getId());
					mails = mailsMember.getMails();
				} else {
					mails = null;
				}
				if (phoneMap.containsKey(member.getId())) {
					PhonesMeber phonesMember = phoneMap.get(member.getId());
					phones = phonesMember.getPhones();
				} else {
					phones = null;
				}

				MemberList memberList = new MemberList(member, mails, phones);
				list.add(memberList);
			}
		}
		return list;
	}

	@Transactional
	@Override
	public List<MemberList> searchAllMembers(String name, int kategorie) {
		List<MemberList> list = new ArrayList<>();

		// naètení listu entit Member dle vybrané kategorie
		List<Member> members = membersDAO.searchAllMembers(name, kategorie);

		// nactení k vybraným entitám Member telefony
		Map<Integer, MailsMember> mailsMap = mailsDAO.getAllMailMembers();

		// nactení k vybraným entitám Member maily
		Map<Integer, PhonesMeber> phoneMap = phoneDAO.getAllPhonesMembers();
		for (Member member : members) {
			if (member == null) {
				throw new RuntimeException("Neinicializován list s Member.");
			} else {

				List<Mail> mails;
				List<Phone> phones;
				if (mailsMap.containsKey(member.getId())) {
					MailsMember mailsMember = mailsMap.get(member.getId());
					mails = mailsMember.getMails();
				} else {
					mails = null;
				}
				if (phoneMap.containsKey(member.getId())) {
					PhonesMeber phonesMember = phoneMap.get(member.getId());
					phones = phonesMember.getPhones();
				} else {
					phones = null;
				}

				MemberList memberList = new MemberList(member, mails, phones);
				list.add(memberList);
			}
		}
		return list;

	}

	@Transactional
	@Override
	public Member getMember(int id) {
		return membersDAO.getMember(id);
	}

	@Transactional
	@Override
	public MemberFull getMemberFull(int id) {
		return membersDAO.getMemberFull(id);
	}

	/*
	 * Metody poskytující PhoneDAO
	 */

	@Transactional
	@Override
	public void deletePhone(int id) {
		phoneDAO.deletePhone(id);
	}

	@Transactional
	@Override
	public int addPhone(Phone phone) {
		return phoneDAO.addPhone(phone);
	}

	@Transactional
	@Override
	public void updatePhone(Phone phone) {
		phoneDAO.updatePhone(phone);
	}

	@Transactional
	@Override
	public Map<Integer, PhonesMeber> getAllPhonesMembers() {
		return phoneDAO.getAllPhonesMembers();
	}

	@Transactional
	@Override
	public PhonesMeber getPhonesMember(int id_member) {
		return phoneDAO.getPhonesMember(id_member);
	}

	@Transactional
	@Override
	public Phone getPhone(int id) {
		return phoneDAO.getPhone(id);
	}

	// Metody poskytující MailDAO

	@Transactional
	@Override
	public void deleteMail(int id) {
		mailsDAO.deleteMail(id);
	}

	@Transactional
	@Override
	public int addMail(Mail mail) {
		return mailsDAO.addMail(mail);
	}

	@Transactional
	@Override
	public void updateMail(Mail mail) {
		mailsDAO.updateMail(mail);
	}

	@Transactional
	@Override
	public Map<Integer, MailsMember> getAllMailMembers() {
		return mailsDAO.getAllMailMembers();
	}

	@Transactional
	@Override
	public MailsMember getMailsMember(int id_member) {
		return mailsDAO.getMailsMember(id_member);
	}

	@Transactional
	@Override
	public Mail getMail(int id) {
		return mailsDAO.getMail(id);
	}
}
