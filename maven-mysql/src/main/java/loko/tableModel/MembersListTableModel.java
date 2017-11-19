package loko.tableModel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import loko.core.Member;
import loko.core.MemberList;
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
	public Class getColumnClass(int c) {
		try {
			Class resume = getValueAt(0, c).getClass();
			
			return resume;
		} catch (NullPointerException e) {
			int row  = getRowCount();
			System.out.println(e);
			return this.getClass();
			
		}
	}
	
}
