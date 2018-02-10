package loko.tableModel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import loko.entity.Phone;
/**
 * 
 * @author Erik Markoviè
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
	private boolean change = false;
	
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
	/**
	 * editovatelnost
	 */
  public boolean isCellEditable(int row, int col) {
  	// uvadí které sloupce jsou editovatelné
    if (col < 0) {
        return false;
    } else {
        return true;
    }
  }
  /**
   * implementace pro mìnìní parametru pøímo v tabulce
   */
  public void setValueAt(Object value, int row, int col) {
  	change = true;
  	Phone tempPhone = phones.get(row);
  	if (col == 0) {
				tempPhone.setName((String)value);
				phones.set(row, tempPhone);
		}
  	if (col==1) {
  		tempPhone.setPhone((String)value);
  		phones.set(row, tempPhone);
		}
  		fireTableCellUpdated(row, col);
  }
  /**
   * 
   */
  public boolean getChange(){
  	return change;
  }
}
