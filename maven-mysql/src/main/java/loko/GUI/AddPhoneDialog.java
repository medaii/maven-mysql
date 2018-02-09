package loko.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import loko.core.Phone;
import service.IFMembersService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * 
 * @author Erik Markoviè
 *
 */
public class AddPhoneDialog extends JDialog {


	private static final long serialVersionUID = -8767176534424954073L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldJmeno;
	private JTextField textFieldTelefon;
private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	/**
	 * Create the dialog.
	 */
	public AddPhoneDialog(int id_member,Boolean newPhone,IFMembersService membersService, MemberDialog memberDialog, Phone phone) {		
		memberDialog.setVisible(false);
		// nastaveni názvu okna
		if (newPhone && (phone!=null)) {
			setTitle("P\u0159id\u00E1n\u00ED telefonu");
		} else {
			setTitle("Editace telefonu");
			
		}
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblJmeno = new JLabel("Jmeno:");
			lblJmeno.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			lblJmeno.setBounds(10, 27, 89, 14);
			contentPanel.add(lblJmeno);
		}
		{
			JLabel lblTelefon = new JLabel("Telefon:");
			lblTelefon.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			lblTelefon.setBounds(10, 52, 89, 14);
			contentPanel.add(lblTelefon);
		}
		{
			textFieldJmeno = new JTextField();
			textFieldJmeno.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			textFieldJmeno.setBounds(83, 24, 153, 20);
			contentPanel.add(textFieldJmeno);
			textFieldJmeno.setColumns(10);
			if (!newPhone) {
				textFieldJmeno.setText(phone.getName());
			}
		}
		{
			textFieldTelefon = new JTextField();
			textFieldTelefon.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			
			// kontrola ze se zadavaji jen cisla
			textFieldTelefon.addKeyListener(new KeyAdapter() {
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
			textFieldTelefon.setBounds(83, 49, 155, 20);
			contentPanel.add(textFieldTelefon);
			textFieldTelefon.setColumns(10);
			if (!newPhone) {
				textFieldTelefon.setText(phone.getPhone());
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						// kontrola zadání povinných udajù
						if(textFieldJmeno.getText().isEmpty() || textFieldTelefon.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Nezadane povinne udaje!");
							LOGGER.info("Nelze uložit, nezadané povinné údaje - " +  this.toString());
						}
						else {
							//uložit telefon
							if (newPhone) {
								Phone phone = new Phone(id_member, textFieldJmeno.getText(), textFieldTelefon.getText());
								int id_newphone = membersService.addPhone(phone);
								LOGGER.info("Èíslo bylo uloženo pod id: " + id_newphone);
							}
							else {
								phone.setName(textFieldJmeno.getText());
								phone.setPhone(textFieldTelefon.getText());
							 // kontrola uložení
								if(membersService.updatePhone(phone, phone.getId()) < 1) {
										LOGGER.warning("Chyba zpisu v DB a záznam telefonu nezmìnìn!");
										JOptionPane.showMessageDialog(null, "Chyba zápisu nezmìnìno!");
									return;
								}
							}
							
							//zavreni okna a otevreni editace
							setVisible(false);
							dispose();
							LOGGER.info("Okno zavøeno - " + this.toString());
							//obnovit vypis
							memberDialog.obnovitNahledPhone();
							memberDialog.setVisible(true);
						//potvrdit uložení
							String zprava = newPhone? "Uspìšnì pøidáno":"Uspìšnì zmìnìno";
							LOGGER.info(zprava + " telefoní èíslo.");
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
				cancelButton.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						memberDialog.setVisible(true);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
