package loko.tableModel;

import java.util.List;

import javax.swing.table.AbstractTableModel;


import loko.core.Phone;
/**
 * 
 * @author Erik Markoviè
 *
 */
public class PhonesTableModel extends AbstractTableModel {

	public static final int OBJECT_COL = -1;
	private static final int NAME = 0;
	private static final int PHONE = 1;
	
	
	private String[] columnNames = { "Jméno", "Telefon" };
	private List<Phone> phones;
	
	
	public PhonesTableModel (List<Phone> phones) {
		this.phones = phones;
	}

	@Override
	public int getRowCount() {
		
		return this.phones.size();
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

		Phone tempPhone = phones.get(row);

		switch (col) {
		case NAME:
			return tempPhone.getName();
		case PHONE:
			return tempPhone.getPhone();
		case OBJECT_COL:
			return tempPhone;
		default:
			return tempPhone.getName();
		}
	}
	
	public Class getColumnClass(int c) {		
			return getValueAt(0, c).getClass();
	}

}
