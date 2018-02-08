package loko.core;

import java.util.ArrayList;
import java.util.List;

public class MailsMember {


	private int id_meber;
	private List<Mail> mails = new ArrayList<>();	
	
	public MailsMember(int id_meber) {
		this.id_meber = id_meber;
	}
	public MailsMember(int id_meber, Mail mails) {
		this.id_meber = id_meber;
		setMails(mails);
	}
	public int getId_meber() {
		return id_meber;
	}


	public void setId_meber(int id_meber) {
		this.id_meber = id_meber;
	}


	public List<Mail> getMails() {
		return mails;
	}


	public void setMails(Mail mails) {
		this.mails.add(mails);
	}	
	
	public void setMails(List<Mail> mails) {
		// clonovani, protoze list zanika po ukonceni session (fetch.LAZY)
		this.mails = mails.stream().collect(java.util.stream.Collectors.toList());
	}	

	@Override
	public String toString() {
		String mails = "";
		for (Mail mail : this.mails) {
			mails += mail.getMail() + ", ";
		}
		mails +="\n";
		return mails;
	}
	
}