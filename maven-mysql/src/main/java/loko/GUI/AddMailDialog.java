package loko.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import loko.entity.Mail;
import service.IFMembersService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
/**
 * 
 * @author Erik Markoviè
 *
 */
public class AddMailDialog extends JDialog {


	private static final long serialVersionUID = 1830227783097815784L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldOdMail;
	private JTextField textFieldMail;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Vytvoøení okna
	 */
	public AddMailDialog(int id_member,Boolean newMail,IFMembersService membersService, MemberDialog memberDialog,Mail mail) {
		memberDialog.setVisible(false);
		if (newMail && (mail!=null)) {
			setTitle("P\u0159id\u00E1n\u00ED mailu");
		} else {
			setTitle("Editace mailu");			
		}
		setBounds(100, 100, 450, 171);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblMailPat = new JLabel("Mail pat\u0159\u00ED:");
		lblMailPat.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblMailPat.setBounds(10, 23, 91, 21);
		contentPanel.add(lblMailPat);
		
		JLabel lblMail = new JLabel("Mail");
		lblMail.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblMail.setBounds(10, 55, 91, 21);
		contentPanel.add(lblMail);
		
		textFieldOdMail = new JTextField();
		textFieldOdMail.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textFieldOdMail.setBounds(123, 23, 183, 21);
		contentPanel.add(textFieldOdMail);
		textFieldOdMail.setColumns(10);
		if (!newMail) {
			textFieldOdMail.setText(mail.getName());
		}
		
		
		textFieldMail = new JTextField();
		textFieldMail.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textFieldMail.setBounds(122, 55, 184, 21);
		contentPanel.add(textFieldMail);
		if (!newMail) {
			textFieldMail.setText(mail.getMail());
		}
		textFieldMail.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				// tlaèítko OK
				JButton okButton = new JButton("OK");
				okButton.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// kontra povinných údajù
						if(textFieldMail.getText().isEmpty() || textFieldOdMail.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "nezadane povinne udaje!");
							LOGGER.info("Nezadané povinné údaje " + this.toString());
						}
						else {
							//uložit mail
							if (newMail) {
								// uložení objektu mail
								Mail mail = new Mail(id_member, textFieldOdMail.getText(), textFieldMail.getText());
								int id_newmail = membersService.addMail(mail);
								LOGGER.info("Vytvoøené nový mail pod id = " + id_newmail);
								
							} else {
								// zmìna parametru v objektu mail
								mail.setName(textFieldOdMail.getText());
								mail.setMail(textFieldMail.getText());
								
								// kontrola uložení
								try {
									membersService.updateMail(mail, mail.getId());
								} catch (RuntimeException e2) {
									LOGGER.warning("Chyba zápisu do DB a mail nezmìnìn!" + e);
									JOptionPane.showMessageDialog(null, "Nezmìnìno!");
									return;
								}
							}
							
							//zavreni okna a otevreni editace
							setVisible(false);
							dispose();
							LOGGER.info("Zavøení okna " + this.toString() + ". Návrat do hlavního okna.");
							//obnovit vypis
							memberDialog.obnovitNahledMail();
							memberDialog.setVisible(true);
						//potvrdit uložení
							String zprava = newMail? "Uspìšnì pøidáno":"Uspìšnì zmìnìno";
							LOGGER.info(zprava); 
							JOptionPane.showMessageDialog(null, zprava);
							
						}
							
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				// tlaèítko Cancel
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						LOGGER.info("Zavøení okna " + this.toString() + ". Návrat do hlavního okna.");
					//obnovit vypis
						memberDialog.obnovitNahledMail();
						memberDialog.setVisible(true);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
