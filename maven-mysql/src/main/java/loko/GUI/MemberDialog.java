package loko.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import loko.DAO.IFMailsDAO;
import loko.DAO.IFPhoneDAO;
import loko.DAO.MembersDAO;
import loko.core.Mail;
import loko.core.MailsMember;
import loko.core.MemberFull;
import loko.core.Phone;
import loko.core.PhonesMeber;
import loko.tableModel.MailsTableModel;
import loko.tableModel.PhonesTableModel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;


import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import java.awt.CardLayout;
import com.toedter.calendar.JDateChooser;


import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
/**
 * 
 * @author Dave
 *
 *Dialogov� okno pro editaci �lena
 */
public class MemberDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JDateChooser dateChooser;

	
	private MembersDAO membersDAO;
	private MembersSearchApp membersSearchApp;
	private MemberFull memberFull;
	private PhonesMeber phones;
	private MailsMember mails;
	private IFMailsDAO mailsDAO;
	private IFPhoneDAO phoneDAO;
	private JTable tableMails;
	private JTable tablePhone;
	private int id_member;
	private final String[] role = {"Hr��", "Hr�� B�ka", " Hr��-souzenci" , "�innovn�k", "LimitkaD", "LimitkaV", "Rodi�"};
	private JTextField textFieldRC;
	private JTextField textFieldTrvaleBydliste;
	private JTextField textFieldCHFReg;
	private JCheckBox chckbxAktivni;
	//clipce nechce podporovat zobrazeni private JComboBox<String> comboBoxRole;
	private JComboBox comboBoxRole;

	/**
	 * Create the dialog.
	 */
	public MemberDialog(int id_member, MembersDAO membersDAO, IFMailsDAO mailsDAO,IFPhoneDAO phoneDAO, MembersSearchApp membersSearchApp) {
		setTitle("Editace \u010Dlena");
		this.id_member = id_member;
		this.membersDAO = membersDAO;
		this.mailsDAO = mailsDAO;
		this.phoneDAO = phoneDAO;
		this.phones = phoneDAO.getPhonesMember(id_member);
		
		this.memberFull = membersDAO.getMemberFull(id_member);
		System.out.println(memberFull);
		this.mails = mailsDAO.getMailsMember(id_member);
		this.membersSearchApp =  membersSearchApp;
		
		setBounds(100, 100, 749, 406);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));
		{
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			contentPanel.add(tabbedPane, "name_3747592994167045");
			{
				JPanel panel_1 = new JPanel();
				tabbedPane.addTab("Obecn� informace", null, panel_1, null);
				panel_1.setLayout(null);
				{
					JLabel lblFirstName = new JLabel("K\u0159esn\u00ED jm\u00E9no:");
					lblFirstName.setFont(new Font("Times New Roman", Font.PLAIN, 12));
					lblFirstName.setBounds(23, 11, 89, 25);
					panel_1.add(lblFirstName);
				}
				{
					JLabel lblLastName = new JLabel("P\u0159\u00EDjmen\u00ED:");
					lblLastName.setFont(new Font("Times New Roman", Font.PLAIN, 12));
					lblLastName.setBounds(23, 47, 89, 14);
					panel_1.add(lblLastName);
				}
				{
					JLabel lblBirthDay = new JLabel("Datum Narozen\u00ED:");
					lblBirthDay.setFont(new Font("Times New Roman", Font.PLAIN, 12));
					lblBirthDay.setBounds(23, 83, 89, 14);
					panel_1.add(lblBirthDay);
				}
				
				textFieldFirstName = new JTextField();
				textFieldFirstName.setBounds(139, 11, 177, 25);
				panel_1.add(textFieldFirstName);
				textFieldFirstName.setColumns(10);
				textFieldFirstName.setText(memberFull.getFirstName());
				
				textFieldLastName = new JTextField();
				textFieldLastName.setBounds(139, 42, 177, 25);
				panel_1.add(textFieldLastName);
				textFieldLastName.setColumns(10);
				textFieldLastName.setText(memberFull.getLastName());
				
				dateChooser = new JDateChooser();
				dateChooser.setBounds(139, 83, 177, 20);
				panel_1.add(dateChooser);
				dateChooser.setDate(memberFull.getBirthDay());
				
				JLabel lblRodnslo = new JLabel("Rodn\u00E1 \u010D\u00EDslo:");
				lblRodnslo.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				lblRodnslo.setBounds(23, 118, 95, 14);
				panel_1.add(lblRodnslo);
				
				textFieldRC = new JTextField();
				textFieldRC.setBounds(139, 115, 177, 20);
				panel_1.add(textFieldRC);
				textFieldRC.setColumns(10);
				textFieldRC.setText(memberFull.getRodneCislo());
				
				JLabel lblTrvBydlit = new JLabel("Trv. bydli\u0161t\u011B:");
				lblTrvBydlit.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				lblTrvBydlit.setBounds(23, 147, 89, 14);
				panel_1.add(lblTrvBydlit);
				
				textFieldTrvaleBydliste = new JTextField();
				textFieldTrvaleBydliste.setBounds(139, 146, 555, 20);
				panel_1.add(textFieldTrvaleBydliste);
				textFieldTrvaleBydliste.setColumns(10);
				textFieldTrvaleBydliste.setText(memberFull.getTrvaleBydliste());
				
				JLabel lblRegsh = new JLabel("Reg. \u010D. \u010CSH:");
				lblRegsh.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				lblRegsh.setBounds(23, 179, 89, 14);
				panel_1.add(lblRegsh);
				
				textFieldCHFReg = new JTextField();
				textFieldCHFReg.setBounds(139, 177, 114, 20);
				panel_1.add(textFieldCHFReg);
				textFieldCHFReg.setColumns(10);
				textFieldCHFReg.setText(memberFull.getChfRegistrace());
				
				chckbxAktivni = new JCheckBox("aktivni");
				chckbxAktivni.setBounds(139, 267, 97, 23);
				panel_1.add(chckbxAktivni);
				if(memberFull.getActive() > 0) {
					chckbxAktivni.setSelected(true);
				}
				else {
					chckbxAktivni.setSelected(false);
				}
					
				
				JLabel lblStav = new JLabel("Stav:");
				lblStav.setBounds(23, 271, 46, 14);
				panel_1.add(lblStav);
				
				JLabel lblRole = new JLabel("Role:");
				lblRole.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				lblRole.setBounds(23, 210, 95, 14);
				panel_1.add(lblRole);
				
				JLabel lblRegu = new JLabel("*max 6 \u010D\u00EDsel");
				lblRegu.setFont(new Font("Times New Roman", Font.PLAIN, 11));
				lblRegu.setBounds(263, 177, 89, 14);
				panel_1.add(lblRegu);
			
				//comboBoxRole = new JComboBox<String>(role);
				comboBoxRole = new JComboBox(role);
				
				comboBoxRole.setBounds(139, 208, 177, 20);
				panel_1.add(comboBoxRole);
				comboBoxRole.setSelectedIndex(memberFull.getId_odd_kategorie()-1);
				
			}
			{
				JPanel panel = new JPanel();
				tabbedPane.addTab("Kontakty", null, panel, null);
				panel.setLayout(null);
				
				JButton btnNovMail = new JButton("Nov\u00FD mail");
				btnNovMail.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AddMailDialog dialog = new AddMailDialog(id_member, mailsDAO, MemberDialog.this);
						dialog.setVisible(true);
					}
				});
				btnNovMail.setBounds(10, 262, 89, 23);
				panel.add(btnNovMail);
				
				JButton btnNovTelefon = new JButton("Nov\u00FD Telefon");
				btnNovTelefon.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AddPhoneDialog dialog = new AddPhoneDialog(id_member, phoneDAO, MemberDialog.this);
						dialog.setVisible(true);
					}
				});
				btnNovTelefon.setBounds(352, 262, 114, 23);
				panel.add(btnNovTelefon);
				
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(10, 33, 303, 202);
				panel.add(scrollPane);
				
				tableMails = new JTable();
				
				MailsTableModel model = new MailsTableModel(mails.getMails());
				tableMails.setModel(model);
				scrollPane.setViewportView(tableMails);
				
				
				JLabel lblMail = new JLabel("Mail:");
				lblMail.setBounds(10, 11, 46, 14);
				panel.add(lblMail);
				
				JButton btnEditMailu = new JButton("Edit mailu");
				btnEditMailu.setBounds(109, 262, 105, 23);
				panel.add(btnEditMailu);
				
				JButton btnSmazatMail = new JButton("Smazat mail");
				btnSmazatMail.setFont(new Font("Times New Roman", Font.PLAIN, 9));
				btnSmazatMail.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
					// vrat vybran� ��dek
						int row  = tableMails.getSelectedRow();
						
						// kontrola, �e je vybrany ��dek
						if(row < 0) {
							JOptionPane.showMessageDialog(null, "Musite vybrat radek.");
							return;
						}
					//potvrzeni �e opravdu chcete smazat
						int response = JOptionPane.showConfirmDialog(
								null, "Opravdu chcete smazat mail?", "Confirm", 
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (response != JOptionPane.YES_OPTION) {
							return;
						}
						Mail tempMail =  (Mail) tableMails.getValueAt(row, MailsTableModel.OBJECT_COL);
						
						System.out.println(id_member);
						
					//smazat zaznam
						mailsDAO.deleteMail(tempMail.getId());
					//refresh tabulkz
						obnovitNahledMail();
						// show success message
						JOptionPane.showMessageDialog(null,
								"Mail usp�n� smaz�n.", "Mail smaz�n",
								JOptionPane.INFORMATION_MESSAGE);
					
					}
				});
				btnSmazatMail.setBounds(224, 262, 89, 23);
				panel.add(btnSmazatMail);
				
				JScrollPane scrollPane_1 = new JScrollPane();
				scrollPane_1.setBounds(352, 33, 339, 202);
				panel.add(scrollPane_1);
				
				tablePhone = new JTable();
				PhonesTableModel modelP = new PhonesTableModel(phones.getPhones());
				tablePhone.setModel(modelP);
				scrollPane_1.setViewportView(tablePhone);
				
				JButton btnEditTel = new JButton("Edit tel.");
				btnEditTel.setBounds(485, 262, 89, 23);
				panel.add(btnEditTel);
				
				JButton btnSmazatTel = new JButton("Smazat tel.");
				btnSmazatTel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// vrat vybran� ��dek
						int row  = tablePhone.getSelectedRow();
						
						// kontrola, �e je vybrany ��dek
						if(row < 0) {
							JOptionPane.showMessageDialog(null, "Musite vybrat radek.");
							return;
						}
					//potvrzeni �e opravdu chcete smazat
						int response = JOptionPane.showConfirmDialog(
								null, "Opravdu chcete smazat tento telefon?", "Confirm", 
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (response != JOptionPane.YES_OPTION) {
							return;
						}
						Phone tempPhone =  (Phone) tablePhone.getValueAt(row, PhonesTableModel.OBJECT_COL);
						
					//smazat zaznam
						phoneDAO.deletePhone(tempPhone.getId());
					//refresh tabulkz
						obnovitNahledPhone();
						// show success message
						JOptionPane.showMessageDialog(null,
								"Telefon usp�n� smaz�n.", "Telefon smaz�n",
								JOptionPane.INFORMATION_MESSAGE);
					}
				});
				btnSmazatTel.setBounds(585, 262, 106, 23);
				panel.add(btnSmazatTel);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						// nad�ten� z formul��e  get date from form
						String firstName = textFieldFirstName.getText();
						String lastName = textFieldLastName.getText();
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String date = sdf.format(dateChooser.getDate());
						Date birthDay = Date.valueOf(date);
						
						String rodneCislo = textFieldRC.getText();
						String trvaleBydliste = textFieldTrvaleBydliste.getText();
						String chfRegistrace = textFieldCHFReg.getText();
						int id_kategorie_odd = comboBoxRole.getSelectedIndex() + 1;
						int active = 0;
						if (chckbxAktivni.isSelected()) {
							active = 1;
						} 
						System.out.println(dateChooser.getDateFormatString() + "  "+ dateChooser.getDate());
						
						memberFull.setFirstName(firstName);
						memberFull.setLastName(lastName);
						memberFull.setBirthDay(birthDay);
						
						memberFull.setRodneCislo(rodneCislo);
						memberFull.setTrvaleBydliste(trvaleBydliste);
						memberFull.setChfRegistrace(chfRegistrace);
						memberFull.setActive(active);
						System.out.println("id role : " + id_kategorie_odd);
						memberFull.setId_odd_kategorie(id_kategorie_odd);
						
						membersDAO.updateMember(memberFull, memberFull.getId());
						// zavrit dialogove okno
						setVisible(false);
						dispose();
						
						// obnovit vypis list
						membersSearchApp.refreshMembersView();
						
					//potvrdit ulo�en�
						JOptionPane.showMessageDialog(null, "Usp�m� p�id�no");
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	public void obnovitNahledMail() {
		try {
			this.mails = mailsDAO.getMailsMember(id_member);
			MailsTableModel model = new MailsTableModel(mails.getMails());
			tableMails.setModel(model);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "chyba" + e);
		}

	}
	public void obnovitNahledPhone() {
		try {
			this.phones = phoneDAO.getPhonesMember(id_member);
			PhonesTableModel modelP = new PhonesTableModel(phones.getPhones());
			tablePhone.setModel(modelP);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "chyba" + e);
		}

	}
}
