package loko.tableModel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import loko.core.Mail;



/**
 * 
 * @author Erik Markoviè
 *
 */
public class MailsTableModel extends AbstractTableModel{
	public static final int OBJECT_COL = -1;
	private static final int NAME = 0;
	private static final int MAIL = 1;
	
	
	private String[] columnNames = { "Jméno", "Mail" };
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
	
	public Class getColumnClass(int c) {		
			return getValueAt(0, c).getClass();
	}

}
