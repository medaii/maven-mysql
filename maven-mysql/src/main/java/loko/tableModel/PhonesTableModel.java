package loko.tableModel;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import loko.core.Phone;
/**
 * 
 * @author Erik Markovi�
 *
 */
public class PhonesTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1440605148176039322L;
	public static final int OBJECT_COL = -1;
	private static final int NAME = 0;
	private static final int PHONE = 1;
	
	
	private String[] columnNames = { "Jm�no", "Telefon" };
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
	public Class<?> getClassCol(int col) {
		
		switch (col) {
			case NAME:
				return String.class;
			case PHONE:
				return String.class;
			case OBJECT_COL:
				return Phone.class;
			default:
				return String.class;
			}
	}
	//vraceni typ hodnoty ve sloupci
	public Class<?> getColumnClass(int c) {		
			return getClassCol(c);
	}

}
