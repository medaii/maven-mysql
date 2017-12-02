package loko.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import loko.DAO.IFMailsDAO;
import loko.core.Mail;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
/**
 * 
 * @author Erik Markovi�
 *
 */
public class AddMailDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldOdMail;
	private JTextField textFieldMail;
	private Mail mail = null;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Create the dialog.
	 */
	public AddMailDialog(int id_member,Boolean newMail,IFMailsDAO mailsDAO, MemberDialog memberDialog,Mail mail) {
		memberDialog.setVisible(false);
		if (newMail && (mail!=null)) {
			setTitle("P\u0159id\u00E1n\u00ED mailu");
		} else {
			this.mail = mail;
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
		textFieldOdMail.setBounds(123, 23, 183, 21);
		contentPanel.add(textFieldOdMail);
		textFieldOdMail.setColumns(10);
		if (!newMail) {
			textFieldOdMail.setText(mail.getName());
		}
		
		
		textFieldMail = new JTextField();
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
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(textFieldMail.getText().isEmpty() || textFieldOdMail.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "nezadane povinne udaje!");
						}
						else {
							//ulo�it mail
							if (newMail) {
								Mail mail = new Mail(id_member, textFieldOdMail.getText(), textFieldMail.getText());
								int id_newmail = mailsDAO.addMail(mail);
								LOGGER.info("Vytvo�en� nov� mail pod id = " + id_newmail);
								
							} else {
								mail.setName(textFieldOdMail.getText());
								mail.setMail(textFieldMail.getText());
								
								// kontrola ulo�en�
								if(mailsDAO.updateMail(mail, mail.getId()) < 1) {
										LOGGER.warning("Chyba z�pisu do DB a mail nezm�n�n!");
										JOptionPane.showMessageDialog(null, "Nezm�n�no!");
									return;
								}
							}
							
							//zavreni okna a otevreni editace
							setVisible(false);
							dispose();
							
							//obnovit vypis
							memberDialog.obnovitNahledMail();
							memberDialog.setVisible(true);
						//potvrdit ulo�en�
							String zprava = newMail? "Usp�n� p�id�no":"Usp�n� zm�n�no";
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
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
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
