package loko.tableModel;


import java.sql.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;


import loko.core.Member;
/**
 * 
 * @author Erik Markoviè
 *
 */
public class MembersTableModel extends AbstractTableModel {
		
		/**
	 * 
	 */
	private static final long serialVersionUID = -1391344318853993366L;
		public static final int OBJECT_COL = -1;
		private static final int LAST_NAME_COL = 0;
		private static final int FIRST_NAME_COL = 1;
		private static final int BIRTH_DAY = 2;
		private static final int ACTIVE = 3;

		private String[] columnNames = { "Pøíjmení", "Køesní jméno", "Datum narození",
				"Aktivní" };
		private List<Member> members;

		public MembersTableModel(List<Member> theMembers) {
			members = theMembers;
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return members.size();
		}

		@Override
		public String getColumnName(int col) {
			return columnNames[col];
		}

		@Override
		public Object getValueAt(int row, int col) {

			Member tempMembers = members.get(row);

			switch (col) {
			case LAST_NAME_COL:
				return tempMembers.getLastName();
			case FIRST_NAME_COL:
				return tempMembers.getFirstName();
			case BIRTH_DAY:
				return tempMembers.getBirthDay();
			case ACTIVE:
				return tempMembers.getActive();
			case OBJECT_COL:
				return tempMembers;
			default:
				return tempMembers.getLastName();
			}
		}
		public Class<?> getClassCol(int col) {
			
			switch (col) {
				case LAST_NAME_COL:
					return String.class;
				case FIRST_NAME_COL:
					return String.class;
				case BIRTH_DAY:
					return Date.class;
				case ACTIVE:
					return Integer.class;
				case OBJECT_COL:
					return Member.class;
				default:
					return String.class;
				}
		}
		//vraceni typ hodnoty ve sloupci
		@Override
		public Class<?> getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}
	}