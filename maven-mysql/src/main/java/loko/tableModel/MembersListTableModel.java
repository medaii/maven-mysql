package loko.tableModel;

import java.sql.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import loko.entity.Mail;
import loko.entity.Phone;
import loko.value.MemberList;
/**
 * 
 * @author Erik Markoviè
 *
 */
public class MembersListTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int OBJECT_COL = -1;
	private static final int NAME = 0;
	private static final int BIRTH_DAY = 1;
	private static final int MAILS = 2;
	private static final int PHONES = 3;
	
	private String[] columnNames = { "Jméno", "Datum Narození", "Maily", "Telefon" };
	private List<MemberList> membersList;

	
	public MembersListTableModel(List<MemberList> membersList) {
		this.membersList = membersList;
	}

	@Override
	public int getRowCount() {
		return membersList.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}
	@Override
	public Object getValueAt(int row, int col) {

		MemberList tempMembers = membersList.get(row);

		switch (col) {
		case NAME:
			return (tempMembers.getLastName() + " " + tempMembers.getFirstName());
		case BIRTH_DAY:
			return tempMembers.getBirthDay();
		case MAILS:
			return tempMembers.getMails();
		case PHONES:
			return tempMembers.getPhones();
		case OBJECT_COL:
			return tempMembers;
		default:
			return tempMembers.getLastName();
		}
	}
	//vracení typ hodnot v tabulce dle sloupce
	public Class<?> getClassCol(int col) {
		switch (col) {
			case NAME:
				return String.class;
			case BIRTH_DAY:
				return Date.class;
			case MAILS:
				return Mail.class;
			case PHONES:
				return Phone.class;
			case OBJECT_COL:
				return MemberList.class;
			default:
				return String.class;
		}
	}
	// dotaz na typ hodnoty ve sloupci
	public Class<?> getColumnClass(int c) {
		return getClassCol(c);
	}
	
}
