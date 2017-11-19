package loko.tableModel;


import java.util.List;

import javax.swing.table.AbstractTableModel;

import loko.core.Member;

public class MembersTableModel extends AbstractTableModel {
		
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

		@Override
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}
	}