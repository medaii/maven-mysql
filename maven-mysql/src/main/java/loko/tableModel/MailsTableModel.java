package loko.tableModel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import loko.entity.Mail;
/**
 * 
 * @author Erik Markovi�
 *
 */
public class MailsTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6675150406782902003L;
	public static final int OBJECT_COL = -1;
	private static final int NAME = 0;
	private static final int MAIL = 1;
	private boolean change = false;
	
	private String[] columnNames = { "Jm�no", "Mail" };
	private List<Mail> mails;
	
	
	public MailsTableModel (List<Mail> mails) {
		this.mails = mails;
	}
	@Override
	public int getRowCount() {
		
		return this.mails.size();
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

		Mail tempMail = mails.get(row);

		switch (col) {
		case NAME:
			return tempMail.getName();
		case MAIL:
			return tempMail.getMail();
		case OBJECT_COL:
			return tempMail;
		default:
			return tempMail.getName();
		}
	}
	public Class<?> getClassCol(int col) {
			
		switch (col) {
			case NAME:
				return String.class;
			case MAIL:
				return String.class;
			case OBJECT_COL:
				return Mail.class;
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
  	// uvad� kter� sloupce jsou editovateln�
    if (col < 0) {
        return false;
    } else {
        return true;
    }
  }
  /**
   * implementace pro m�n�n� parametru p��mo v tabulce
   */
  public void setValueAt(Object value, int row, int col) {
  	change = true;
  	Mail tempMail = mails.get(row);
  	if (col == 0) {
				tempMail.setName((String)value);
				mails.set(row, tempMail);
		}
  	if (col==1) {
  		tempMail.setMail((String)value);
  		mails.set(row, tempMail);
		}
  		fireTableCellUpdated(row, col);
  }
  public boolean getChange() {
		return change;
	}
}
