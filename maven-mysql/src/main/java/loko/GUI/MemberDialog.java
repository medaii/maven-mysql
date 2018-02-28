package loko.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import loko.entity.Mail;
import loko.entity.Phone;
import loko.service.MembersService;
import loko.tableModel.MailsTableModel;
import loko.tableModel.PhonesTableModel;
import loko.value.MailsMember;
import loko.value.MemberFull;
import loko.value.PhonesMeber;

import javax.swing.JLabel;
import javax.swing.JOptionPane;


import javax.swing.JTextField;

import javax.swing.JTabbedPane;
import java.awt.CardLayout;

import com.toedter.calendar.JDateChooser;


import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 * 
 * @author Erik Markovi�
 *
 *Dialogov� okno pro editaci �lena
 */
public class MemberDialog extends JDialog {

	private static final long serialVersionUID = 8676803454227556101L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JDateChooser dateChooser;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private MembersService membersService;
	private MemberFull memberFull;
	private PhonesMeber phones;
	private MailsMember mails;
	private JTable tableMails;
	private JTable tablePhone;
	private int id_member;
	private final String[] role = {"Hr��", "Hr�� B�ka", " Hr��-souzenci" , "�innovn�k", "LimitkaD", "LimitkaV", "Rodi�"};
	private JTextField textFieldRC;
	private JTextField textFieldTrvaleBydliste;
	private JTextField textFieldCHFReg;
	private JCheckBox chckbxAktivni;
	private MailsTableModel modelMail;
	private PhonesTableModel modelP;
	//clipce nechce podporovat zobrazeni private JComboBox<String> comboBoxRole;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBoxRole;

	/**
	 * Vytvo�en� okna MemberDialog.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MemberDialog(int id_member,MembersService membersService, MembersSearchApp membersSearchApp) {
		this.setModal(true);
		
		//nastaven� n�zvu okna
		setTitle("Editace \u010Dlena");
		
		// inicializace prom�n�ch
		this.membersService = membersService;		
		this.id_member = id_member;
		
		this.phones = membersService.getPhonesMember(id_member);
		
		this.memberFull = membersService.getMemberFull(id_member);
		this.mails = membersService.getMailsMember(id_member);		
		
		//inicializace okna
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
				textFieldFirstName.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				textFieldFirstName.setBounds(139, 11, 177, 25);
				panel_1.add(textFieldFirstName);
				textFieldFirstName.setColumns(10);
				textFieldFirstName.setText(memberFull.getFirstName());
				
				textFieldLastName = new JTextField();
				textFieldLastName.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				textFieldLastName.setBounds(139, 42, 177, 25);
				panel_1.add(textFieldLastName);
				textFieldLastName.setColumns(10);
				textFieldLastName.setText(memberFull.getLastName());
				
				dateChooser = new JDateChooser();
				dateChooser.getCalendarButton().setFont(new Font("Times New Roman", Font.PLAIN, 12));
				dateChooser.setBounds(139, 83, 177, 20);
				panel_1.add(dateChooser);
				dateChooser.setDate(memberFull.getBirthDay());
				
				JLabel lblRodnslo = new JLabel("Rodn\u00E1 \u010D\u00EDslo:");
				lblRodnslo.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				lblRodnslo.setBounds(23, 118, 95, 14);
				panel_1.add(lblRodnslo);
				
				textFieldRC = new JTextField();
				textFieldRC.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				textFieldRC.setBounds(139, 115, 177, 20);
				panel_1.add(textFieldRC);
				textFieldRC.setColumns(10);
				textFieldRC.setText(memberFull.getRodneCislo());
				
				JLabel lblTrvBydlit = new JLabel("Trv. bydli\u0161t\u011B:");
				lblTrvBydlit.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				lblTrvBydlit.setBounds(23, 147, 89, 14);
				panel_1.add(lblTrvBydlit);
				
				textFieldTrvaleBydliste = new JTextField();
				textFieldTrvaleBydliste.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				textFieldTrvaleBydliste.setBounds(139, 146, 555, 20);
				panel_1.add(textFieldTrvaleBydliste);
				textFieldTrvaleBydliste.setColumns(10);
				textFieldTrvaleBydliste.setText(memberFull.getTrvaleBydliste());
				
				JLabel lblRegsh = new JLabel("Reg. \u010D. \u010CSH:");
				lblRegsh.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				lblRegsh.setBounds(23, 179, 89, 14);
				panel_1.add(lblRegsh);
				
				textFieldCHFReg = new JTextField();
				textFieldCHFReg.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				
				//��slo registrace m��e b�t jen ��sla...kontrola zadavan� z klavesnice
				textFieldCHFReg.addKeyListener(new KeyAdapter() {
					@Override
					public void keyTyped(KeyEvent e) {
						char c = e.getKeyChar();
			      if (!((c >= '0') && (c <= '9') ||
			         (c == KeyEvent.VK_BACK_SPACE) ||
			         (c == KeyEvent.VK_DELETE))) {
			        getToolkit().beep();
			        e.consume();
			      }
					}
				});
				textFieldCHFReg.setBounds(139, 177, 114, 20);
				panel_1.add(textFieldCHFReg);
				textFieldCHFReg.setColumns(10);
				textFieldCHFReg.setText(memberFull.getChfRegistrace());
				
				chckbxAktivni = new JCheckBox("aktivni");
				chckbxAktivni.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				chckbxAktivni.setBounds(139, 267, 97, 23);
				panel_1.add(chckbxAktivni);
				if(memberFull.getActive() > 0) {
					chckbxAktivni.setSelected(true);
				}
				else {
					chckbxAktivni.setSelected(false);
				}
					
				
				JLabel lblStav = new JLabel("Stav:");
				lblStav.setFont(new Font("Times New Roman", Font.PLAIN, 12));
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
				comboBoxRole.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				
				comboBoxRole.setBounds(139, 208, 177, 20);
				panel_1.add(comboBoxRole);
				comboBoxRole.setSelectedIndex(memberFull.getId_odd_kategorie()-1);
				
			}
			{
				JPanel kontakty = new JPanel();
				tabbedPane.addTab("Kontakty", null, kontakty, null);
				kontakty.setLayout(null);
				
				// tla��tko na vytvo�en� nov�ho mailu
				JButton btnNovMail = new JButton("Nov\u00FD mail");
				btnNovMail.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				btnNovMail.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//otev�en� nov�ho okna addMailDialog
						AddMailDialog dialog = new AddMailDialog(id_member,true, membersService, MemberDialog.this, null);
						dialog.setVisible(true);
						LOGGER.info("Otev�en� okna pro p�idan� mailu");
					}
				});
				btnNovMail.setBounds(10, 262, 89, 23);
				kontakty.add(btnNovMail);
				
				// tla��tko na vytvo�en� nov�ho telefon�ho ��sla
				JButton btnNovTelefon = new JButton("Nov\u00FD Telefon");
				btnNovTelefon.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				btnNovTelefon.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// otev�en� nov�ho okna addPhoneDialog
						AddPhoneDialog dialog = new AddPhoneDialog(id_member,true, membersService, MemberDialog.this, null);
						dialog.setVisible(true);
						LOGGER.info("Otev�en� okna pro p�idan� mailu");
					}
				});
				btnNovTelefon.setBounds(352, 262, 114, 23);
				kontakty.add(btnNovTelefon);
				
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(10, 33, 303, 202);
				kontakty.add(scrollPane);
				
				tableMails = new JTable();
				tableMails.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				

				modelMail = new MailsTableModel(mails.getMails());
				tableMails.setModel(modelMail);
				scrollPane.setViewportView(tableMails);
			
				
				JLabel lblMail = new JLabel("Mail:");
				lblMail.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				lblMail.setBounds(10, 11, 46, 14);
				kontakty.add(lblMail);
				
				// tla��tko pro editaci mailu
				JButton btnEditMailu = new JButton("Edit mailu");
				btnEditMailu.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				btnEditMailu.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// vrat vybran� ��dek
						int row  = tableMails.getSelectedRow();
						
						// kontrola, �e je vybrany ��dek
						if(row < 0) {
							JOptionPane.showMessageDialog(null, "Musite vybrat radek.");
							LOGGER.info("Tla��tko editace mailu - nebyl vybran ��dek.");
							return;
						}
						// na�ten� dat do objektu Mail a otev�eno okno pro editaci mailu
						Mail tempMail =  (Mail) tableMails.getValueAt(row, MailsTableModel.OBJECT_COL);
						
						AddMailDialog dialog = new AddMailDialog(id_member,false,membersService, MemberDialog.this, tempMail );
						dialog.setVisible(true);
						LOGGER.info("Otev�en� okna pro editaci mailu");						
					}
				});
				btnEditMailu.setBounds(109, 262, 105, 23);
				kontakty.add(btnEditMailu);
				
				// tla��tko na maz�n� mailu
				JButton btnSmazatMail = new JButton("Smazat m.");
				btnSmazatMail.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				btnSmazatMail.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
					// vrat vybran� ��dek
						int row  = tableMails.getSelectedRow();
						
						// kontrola, �e je vybrany ��dek
						if(row < 0) {
							JOptionPane.showMessageDialog(null, "Musite vybrat radek.");
							LOGGER.info("Tla��tko smaz�n� mailu - nebyl vybran ��dek.");
							return;
						}
					//potvrzeni �e opravdu chcete smazat						
						int response = JOptionPane.showConfirmDialog(
								null, "Opravdu chcete smazat mail?", "Confirm", 
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (response != JOptionPane.YES_OPTION) {
							LOGGER.info("Zru�en� p��kazu na smaz�n� mailu.");
							return;
						}
						//na�ten� objektu Mail a smaz�n� z DB
						Mail tempMail =  (Mail) tableMails.getValueAt(row, MailsTableModel.OBJECT_COL);
						
						LOGGER.info("Ma�e se member id = " + id_member);
					//smazat zaznam
						membersService.deleteMail(tempMail.getId());
					//refresh tabulkz
						obnovitNahledMail();
						// show success message
						LOGGER.info("Mail usp�n� smaz�n.");
						JOptionPane.showMessageDialog(null,
								"Mail usp�n� smaz�n.", "Mail smaz�n",
								JOptionPane.INFORMATION_MESSAGE);
					
					}
				});
				btnSmazatMail.setBounds(224, 262, 89, 23);
				kontakty.add(btnSmazatMail);
				
				JScrollPane scrollPane_1 = new JScrollPane();
				scrollPane_1.setBounds(352, 33, 339, 202);
				kontakty.add(scrollPane_1);
				
				tablePhone = new JTable();
				tablePhone.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				modelP = new PhonesTableModel(phones.getPhones());
				tablePhone.setModel(modelP);
				scrollPane_1.setViewportView(tablePhone);
				
				// tla��tko editace telefonu
				JButton btnEditTel = new JButton("Edit tel.");
				btnEditTel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				btnEditTel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					// vrat vybran� ��dek
						int row  = tablePhone.getSelectedRow();
						
						// kontrola, �e je vybrany ��dek
						if(row < 0) {
							JOptionPane.showMessageDialog(null, "Musite vybrat radek.");
							LOGGER.info("Tla��tko editace telefonu - nebyl vybran ��dek.");
							return;
						}
						// vytvo�en� objektu Phone a otev�en� okna AddPhoneDialog
						Phone tempPhone =  (Phone) tablePhone.getValueAt(row, PhonesTableModel.OBJECT_COL);
						AddPhoneDialog dialog = new AddPhoneDialog(id_member,false, membersService, MemberDialog.this, tempPhone);
						dialog.setVisible(true);
						LOGGER.info("Otev�en� okna pro editaci telefonu");	
					}
				});
				btnEditTel.setBounds(485, 262, 89, 23);
				kontakty.add(btnEditTel);
				
				// tla��tko pro smazan� telefonu
				JButton btnSmazatTel = new JButton("Smazat tel.");
				btnSmazatTel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				btnSmazatTel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// vrat vybran� ��dek
						int row  = tablePhone.getSelectedRow();
						
						// kontrola, �e je vybrany ��dek
						if(row < 0) {
							JOptionPane.showMessageDialog(null, "Musite vybrat radek.");
							LOGGER.info("Tla��tko smaz�n� telefonu - nebyl vybran ��dek.");
							return;
						}
						//potvrzeni �e opravdu chcete smazat
						int response = JOptionPane.showConfirmDialog(
								null, "Opravdu chcete smazat tento telefon?", "Confirm", 
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (response != JOptionPane.YES_OPTION) {
							LOGGER.info("Zru�en� p��kazu na smaz�n� telefonu.");
							return;
						}
						// vytvo�en� objektu Phone pro smaz�n� telefonu
						Phone tempPhone =  (Phone) tablePhone.getValueAt(row, PhonesTableModel.OBJECT_COL);
						
						//smazat zaznam
						membersService.deletePhone(tempPhone.getId());
						//refresh tabulkz
						obnovitNahledPhone();
						// show success message
						LOGGER.info("Telefon� ��slo smaz�no.");
						JOptionPane.showMessageDialog(null,
								"Telefon usp�n� smaz�n.", "Telefon smaz�n",
								JOptionPane.INFORMATION_MESSAGE);
					}
				});
				btnSmazatTel.setBounds(585, 262, 106, 23);
				kontakty.add(btnSmazatTel);
				
				JLabel lblTelefon = new JLabel("Telefon:");
				lblTelefon.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				lblTelefon.setBounds(352, 11, 46, 14);
				kontakty.add(lblTelefon);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				// ulo�en� a n�vrat do hlavn�ho okna
				JButton okButton = new JButton("OK");
				okButton.setFont(new Font("Times New Roman", Font.PLAIN, 12));
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
						
						// ulo�en� hodnot do objektu memberFull
						memberFull.setFirstName(firstName);
						memberFull.setLastName(lastName);
						memberFull.setBirthDay(birthDay);
						
						memberFull.setRodneCislo(rodneCislo);
						memberFull.setTrvaleBydliste(trvaleBydliste);
						memberFull.setChfRegistrace(chfRegistrace);
						memberFull.setActive(active);
						memberFull.setId_odd_kategorie(id_kategorie_odd);
						
						// ulo�en� do DB
						membersService.updateMember(memberFull);
						// zavrit dialogove okno
						setVisible(false);
						dispose();
						
						// kontrola zm�ny hodnot mailu
					
						if(modelMail.getChange()) {
							LOGGER.info("Zm�ma hodnot mailu.");
							int countRow = modelMail.getRowCount();
							for (int i = 0; i < countRow; i++) {
								Mail tempMail = (Mail) modelMail.getValueAt(i, MailsTableModel.OBJECT_COL);
								membersService.updateMail(tempMail);
							}
						}
						else {
							LOGGER.info("Nen� zm�na hodnot mailu.");
						}
						
						// kontrola zm�ny hodnot telefonu
						if(modelP.getChange()) {
							int countRow = modelP.getRowCount();
							for (int i = 0; i < countRow; i++) {
								Phone tempPhone = (Phone) modelP.getValueAt(i, PhonesTableModel.OBJECT_COL);
								membersService.updatePhone(tempPhone);
							}
							LOGGER.info("Zm�ma hodnot Telefonu.");
						}
						else {
							LOGGER.info("Nen� zm�na hodnot telefonu.");
						}
						
						// obnovit vypis list
						membersSearchApp.refreshMembersView();
						
					//potvrdit ulo�en�
						JOptionPane.showMessageDialog(null, "Usp�m� p�id�no");
						LOGGER.info("Usp�n� ulo�eny zm�ny a n�vrat do hlavn�ho okna.");
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				// tla��tko Cancel
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						LOGGER.info("Bez ulo�en� n�vrat do hlavn�ho okna.");
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	/**
	 * obnoven� tabulky mail
	 */
	public void obnovitNahledMail() {
		try {
			this.mails = membersService.getMailsMember(id_member);
			MailsTableModel model = new MailsTableModel(mails.getMails());
			tableMails.setModel(model);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "chyba" + e);
			LOGGER.warning("Chyba p�i obnov� tabulky mail� - " + e);
			e.getLocalizedMessage();
		}
	}
	/**
	 * Obnoven� tabulky telefon�ch ��sel
	 */
	public void obnovitNahledPhone() {
		try {
			this.phones = membersService.getPhonesMember(id_member);
			PhonesTableModel modelP = new PhonesTableModel(phones.getPhones());
			tablePhone.setModel(modelP);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "chyba" + e);
			LOGGER.warning("Chyba p�i obnov� tabulky telefon�ch ��sel - " + e);
		}
	}
}
