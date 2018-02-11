package loko.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import loko.dao.DAOFactory;
import loko.dao.MailsDAO;
import loko.entity.Mail;
import loko.entity.Phone;
import loko.value.MemberFull;
import service.IFMembersService;

import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;

import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
/**
 * 
 * @author Erik Markoviè
 *
 */
public class AddMemberDialog extends JDialog {


	private static final long serialVersionUID = -2496886616425471958L;
	private MemberFull memberFull;
	private final String[] role = {"Hráè", "Hráè Bèka", " Hráè-souzenci" , "Èinnovník", "LimitkaD", "LimitkaV", "Rodiè"};
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldFirstName;
	private JTextField textFieldLastName;
	private JTextField textFieldRC;
	private JTextField textFieldOdMail1;
	private JTextField textFieldMail1;
	private JTextField textFieldOdMail2;
	private JTextField textFieldMail2;
	private JTextField textFieldOdTel1;
	private JTextField textFieldTel1;
	private JTextField textFieldOdTel2;
	private JTextField textFieldTel2;
	private JDateChooser dateChooser;
	private JTextField textFieldRegC;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox;

	/**
	 * Vytvoøení okna.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AddMemberDialog(IFMembersService membersService,MembersSearchApp membersSearchApp) {
		
		setBounds(100, 100, 737, 598);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblKesnJmno = new JLabel("K\u0159esn\u00ED jm\u00E9no");
			lblKesnJmno.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			lblKesnJmno.setBounds(24, 63, 113, 27);
			contentPanel.add(lblKesnJmno);
		}
		{
			JLabel lblLastName = new JLabel("P\u0159\u00EDjmen\u00ED");
			lblLastName.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			lblLastName.setBounds(24, 101, 113, 32);
			contentPanel.add(lblLastName);
		}
		{
			JLabel lblDatumNarozen = new JLabel("Datum narozen\u00ED");
			lblDatumNarozen.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			lblDatumNarozen.setBounds(24, 144, 113, 32);
			contentPanel.add(lblDatumNarozen);
		}
		{
			JLabel lblRodnslo = new JLabel("Rodn\u00E9 \u010D\u00EDslo");
			lblRodnslo.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			lblRodnslo.setBounds(24, 187, 113, 37);
			contentPanel.add(lblRodnslo);
		}
		{
			JLabel lblKontakty = new JLabel("Kontakty:");
			lblKontakty.setFont(new Font("Times New Roman", Font.BOLD, 16));
			lblKontakty.setBounds(353, 21, 119, 27);
			contentPanel.add(lblKontakty);
		}
		{
			JLabel lblZkladnInformace = new JLabel("Z\u00E1kladn\u00ED informace");
			lblZkladnInformace.setFont(new Font("Times New Roman", Font.BOLD, 16));
			lblZkladnInformace.setBounds(24, 27, 206, 14);
			contentPanel.add(lblZkladnInformace);
		}
		{
			textFieldFirstName = new JTextField();
			textFieldFirstName.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			textFieldFirstName.setBounds(144, 66, 164, 24);
			contentPanel.add(textFieldFirstName);
			textFieldFirstName.setColumns(10);
		}
		{
			textFieldLastName = new JTextField();
			textFieldLastName.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			textFieldLastName.setBounds(144, 107, 167, 24);
			contentPanel.add(textFieldLastName);
			textFieldLastName.setColumns(10);
		}
		{
			textFieldRC = new JTextField();
			textFieldRC.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			textFieldRC.setBounds(144, 192, 167, 27);
			contentPanel.add(textFieldRC);
			textFieldRC.setColumns(10);
		}
		
		JLabel lblMail = new JLabel("Mail:");
		lblMail.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		lblMail.setBounds(513, 69, 46, 14);
		contentPanel.add(lblMail);
		
		JLabel lblVlastnkMailu1 = new JLabel("Vlastn\u00EDk mailu");
		lblVlastnkMailu1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblVlastnkMailu1.setBounds(364, 110, 108, 14);
		contentPanel.add(lblVlastnkMailu1);
		
		JLabel lblAdresaMailu1 = new JLabel("Adresa mailu");
		lblAdresaMailu1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblAdresaMailu1.setBounds(364, 153, 108, 14);
		contentPanel.add(lblAdresaMailu1);
		
		JLabel lblVlastnkMailu2 = new JLabel("Vlastn\u00EDk mailu");
		lblVlastnkMailu2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblVlastnkMailu2.setBounds(364, 198, 97, 14);
		contentPanel.add(lblVlastnkMailu2);
		
		JLabel lblAdresaMailu = new JLabel("Adresa Mailu");
		lblAdresaMailu.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblAdresaMailu.setBounds(364, 236, 108, 14);
		contentPanel.add(lblAdresaMailu);
		
		textFieldOdMail1 = new JTextField();
		textFieldOdMail1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textFieldOdMail1.setBounds(513, 107, 151, 20);
		contentPanel.add(textFieldOdMail1);
		textFieldOdMail1.setColumns(10);
		
		textFieldMail1 = new JTextField();
		textFieldMail1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textFieldMail1.setBounds(513, 150, 151, 20);
		contentPanel.add(textFieldMail1);
		textFieldMail1.setColumns(10);
		
		textFieldOdMail2 = new JTextField();
		textFieldOdMail2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textFieldOdMail2.setBounds(513, 195, 151, 20);
		contentPanel.add(textFieldOdMail2);
		textFieldOdMail2.setColumns(10);
		
		textFieldMail2 = new JTextField();
		textFieldMail2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textFieldMail2.setBounds(513, 233, 151, 20);
		contentPanel.add(textFieldMail2);
		textFieldMail2.setColumns(10);
		
		JLabel lblTelefon = new JLabel("Telefon");
		lblTelefon.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		lblTelefon.setBounds(513, 290, 46, 14);
		contentPanel.add(lblTelefon);
		
		JLabel lblVlastnkTel = new JLabel("Vlastn\u00EDk tel.");
		lblVlastnkTel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblVlastnkTel.setBounds(364, 321, 108, 14);
		contentPanel.add(lblVlastnkTel);
		
		JLabel lblTelefon_1 = new JLabel("Telefon");
		lblTelefon_1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblTelefon_1.setBounds(364, 359, 97, 14);
		contentPanel.add(lblTelefon_1);
		
		JLabel lblVlastnkTel_1 = new JLabel("Vlastn\u00EDk tel.");
		lblVlastnkTel_1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblVlastnkTel_1.setBounds(363, 397, 109, 14);
		contentPanel.add(lblVlastnkTel_1);
		
		JLabel lblTelefon_2 = new JLabel("Telefon");
		lblTelefon_2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblTelefon_2.setBounds(364, 433, 108, 14);
		contentPanel.add(lblTelefon_2);
		
		textFieldOdTel1 = new JTextField();
		textFieldOdTel1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textFieldOdTel1.setBounds(513, 315, 151, 20);
		contentPanel.add(textFieldOdTel1);
		textFieldOdTel1.setColumns(10);
		
		textFieldTel1 = new JTextField();
		textFieldTel1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textFieldTel1.setBounds(513, 356, 151, 20);
		contentPanel.add(textFieldTel1);
		textFieldTel1.setColumns(10);
		
		textFieldOdTel2 = new JTextField();
		textFieldOdTel2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textFieldOdTel2.setBounds(513, 394, 151, 20);
		contentPanel.add(textFieldOdTel2);
		textFieldOdTel2.setColumns(10);
		
		textFieldTel2 = new JTextField();
		textFieldTel2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textFieldTel2.setText("");
		textFieldTel2.setBounds(513, 430, 151, 20);
		contentPanel.add(textFieldTel2);
		textFieldTel2.setColumns(10);
		
		JLabel lblTrvalBydlit = new JLabel("Trval\u00E9 bydli\u0161t\u011B");
		lblTrvalBydlit.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblTrvalBydlit.setBounds(24, 235, 113, 14);
		contentPanel.add(lblTrvalBydlit);
		
		dateChooser = new JDateChooser();
		dateChooser.getCalendarButton().setFont(new Font("Times New Roman", Font.PLAIN, 12));
		dateChooser.setBounds(147, 144, 161, 20);
		contentPanel.add(dateChooser);
		
		
		JLabel lblRegsh = new JLabel("\u010C. reg. \u010CSH");
		lblRegsh.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblRegsh.setBounds(24, 362, 113, 14);
		contentPanel.add(lblRegsh);
		
		textFieldRegC = new JTextField();
		textFieldRegC.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textFieldRegC.setBounds(144, 356, 164, 24);
		contentPanel.add(textFieldRegC);
		textFieldRegC.setColumns(10);
		
		JLabel lblZaazen = new JLabel("Za\u0159azen\u00ED:");
		lblZaazen.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblZaazen.setBounds(24, 400, 113, 14);
		contentPanel.add(lblZaazen);
		
		
		comboBox = new JComboBox(role);
		comboBox.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		comboBox.setBounds(144, 394, 164, 24);
		contentPanel.add(comboBox);
		
		JLabel lblPoznmka = new JLabel("Pozn\u00E1mka:");
		lblPoznmka.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblPoznmka.setBounds(24, 446, 113, 14);
		contentPanel.add(lblPoznmka);
		
		JTextPane textPaneNote = new JTextPane();
		textPaneNote.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textPaneNote.setBounds(143, 440, 165, 75);
		contentPanel.add(textPaneNote);
		
		JTextPane textPaneTrvaleBydliste = new JTextPane();
		textPaneTrvaleBydliste.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textPaneTrvaleBydliste.setBounds(144, 235, 161, 101);
		contentPanel.add(textPaneTrvaleBydliste);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				// tlaèitko uložit
				JButton okButton = new JButton("Ulo\u017Eit");
				okButton.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// kontrola poviných udajù
						if (!(textFieldFirstName.getText().isEmpty() || textFieldLastName.getText().isEmpty() || dateChooser.getDate() == null)) {
							String firstName = textFieldFirstName.getText();
							String lastName = textFieldLastName.getText();
							
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String date = sdf.format(dateChooser.getDate());
							Date birthDay = Date.valueOf(date);
							
							String note = textPaneNote.getText();
							int active = 1;
							int id_odd_kategorie = comboBox.getSelectedIndex() + 1;
							
							Calendar cal = Calendar.getInstance();
							date = sdf.format(cal.getTime());
							Date enterDate = Date.valueOf(date);
							
							String rodneCislo = textFieldRC.getText();
							String trvaleBydliste = textPaneTrvaleBydliste.getText();
							String chfRegistrace = textFieldRegC.getText();
							
							memberFull = new MemberFull(firstName, lastName, birthDay, note, active, id_odd_kategorie, enterDate,
							rodneCislo, trvaleBydliste, chfRegistrace);	
							int id_member = membersService.addMemberFull(memberFull);
							
							// ulozeni mailu
							if(!textFieldMail1.getText().isEmpty()) {
								if(textFieldOdMail1.getText().isEmpty()) {
									textFieldOdMail1.setText("");
								}
								Mail mail = new Mail(id_member, textFieldOdMail1.getText(), textFieldMail1.getText());
								MailsDAO mailsDAO = DAOFactory.createDAO(MailsDAO.class);
								mailsDAO.addMail(mail);
								LOGGER.info("Pøidán nový mail. id member: " + id_member + " mail: " + mail.toString());
							}
							if(!textFieldMail2.getText().isEmpty()) {
								if(textFieldOdMail2.getText().isEmpty()) {
									textFieldOdMail2.setText("");
								}
								Mail mail = new Mail(id_member, textFieldOdMail2.getText(), textFieldMail2.getText());
								MailsDAO mailsDAO = DAOFactory.createDAO(MailsDAO.class);
								mailsDAO.addMail(mail);
								LOGGER.info("Pøidán nový mail. id member: " + id_member + " mail: " + mail.toString());
							}
							// ulozeni telefonu
							if(!textFieldTel1.getText().isEmpty()) {
								if(textFieldOdTel1.getText().isEmpty()) {
									textFieldOdTel1.setText("");
								}
								Phone phone = new Phone(id_member, textFieldOdTel1.getText(), textFieldTel1.getText());
								
								membersService.addPhone(phone);
								LOGGER.info("Pøidán nové telefoní èíslo. id member: " + id_member + " telefon: " + phone.toString());
							}
							// kontrola jestli je vyplnený text
							if(!textFieldTel2.getText().isEmpty()) {
								if(textFieldOdTel2.getText().isEmpty()) {
									textFieldOdTel2.setText("");
								}
								Phone phone = new Phone(id_member, textFieldOdTel2.getText(), textFieldTel2.getText());
								
								membersService.addPhone(phone);
								LOGGER.info("Pøidán nové telefoní èíslo. id member: " + id_member + " telefon: " + phone.toString());
							}
							
							
							//zavreni okna a otevreni editace
							setVisible(false);
							dispose();
							LOGGER.info("Zavøení okna " + this.toString());
							// obnovit vypis list
							membersSearchApp.refreshMembersView();
							
						//potvrdit uložení
							LOGGER.info("Uspìšnì pøidaný èlen.");
							JOptionPane.showMessageDialog(null, "Uspìšmì pøidáno");
							// otevreni editace
													
							MemberDialog dialog = new MemberDialog(id_member,membersService, membersSearchApp);
							dialog.setVisible(true);
							LOGGER.info("Uložení a otevøení novéhé okna MemberDialog.");
							
							
						} else {
								// nezadané povinné údaje
								JOptionPane.showMessageDialog(null, "nezadane povinne udaje!");
								LOGGER.info("Pokus uložit záznam bez uvedení povinných údajù " + this.toString());
						}
						
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				// tlaèitko Cancel
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						LOGGER.info("Návrat bez uložení z " + this.toString());
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
