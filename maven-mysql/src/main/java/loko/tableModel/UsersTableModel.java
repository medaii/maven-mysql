package loko.tableModel;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import loko.core.User;
/**
 * 
 * @author Erik Markovi�
 *
 */
public class UsersTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6444027184471963691L;
	public static final int OBJECT_COL = -1;
	private static final int NAME_COL = 0;
	private static final int EMAIL_COL = 1;
	private static final int IS_ADMIN_COL = 2;
	
	private String[] columnNames = { "Jm�no", "Email",
	"Je Admin" };
	private List<User> users;
	
	public UsersTableModel(List<User> users) {
		this.users = users;
	}
	
	//po�et sloupc�
	public int getColumnCount() {
		return columnNames.length;
	}

	//po�et ��adku
	public int getRowCount() {
		return users.size();
	}

	//n�zvy slouopc�
	public String getColumnName(int col) {
		return columnNames[col];
	}

	//vracen� hodnoty
	public Object getValueAt(int row, int col) {

		User tempUser = users.get(row);

		switch (col) {
			case NAME_COL:
				return (tempUser.getLastName() + tempUser.getFirstName());
			case EMAIL_COL:
				return tempUser.getEmail();
			case IS_ADMIN_COL:
				return tempUser.isAdmin();
			case OBJECT_COL:
				return tempUser;
			default:
				return tempUser.getLastName();
		}
	}
	
	public Class<?> getClassCol(int col) {
		switch (col) {
			case NAME_COL:
				return String.class;
			case EMAIL_COL:
				return String.class;
			case IS_ADMIN_COL:
				return Boolean.class;
			case OBJECT_COL:
				return User.class;
			default:
				return String.class;
		}
	}
	
	// vr��� ��dek
	public Class<?> getColumnClass(int c) {
		return getClassCol(c);
	}	
	
}
