package loko.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import loko.core.User;
import service.IFMembersService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
/**
 * 
 * @author Erik Markoviè
 *
 */
public class ChangePassword extends JDialog {


	private static final long serialVersionUID = 8661708719647586881L;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final JPanel contentPanel = new JPanel();
	private JPasswordField passwordOld;
	private JPasswordField passwordNew1;
	private JPasswordField passwordNew2;
	
	/**
	 * Otevøení okna pro zmìnu hesla User.
	 */
	public ChangePassword(User user, IFMembersService membersService, MembersSearchApp membersSearchApp, boolean isAdmin) {
		this.setModal(true);

		
		setTitle("Zm\u011Bna hesla");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblUser = new JLabel("User: " + user.getFirstName() + " " + user.getLastName());
		lblUser.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblUser.setBounds(10, 11, 237, 14);
		contentPanel.add(lblUser);
		JLabel lblStarHeslo;		
		if(isAdmin) {
			lblStarHeslo = new JLabel("");			
		}
		else {
			lblStarHeslo = new JLabel("Star\u00E9 heslo:");			
		}
		lblStarHeslo.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblStarHeslo.setBounds(10, 49, 99, 14);
		contentPanel.add(lblStarHeslo);
		lblStarHeslo.setEnabled(!isAdmin);
		
		JLabel lblNovHeslo = new JLabel("Nov\u00E9 heslo:");
		lblNovHeslo.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblNovHeslo.setBounds(10, 74, 99, 14);
		contentPanel.add(lblNovHeslo);
		
		JLabel lblNovHeslo_1 = new JLabel("Nov\u00E9 heslo 2.:");
		lblNovHeslo_1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblNovHeslo_1.setBounds(10, 99, 99, 14);
		contentPanel.add(lblNovHeslo_1);
		
		passwordOld = new JPasswordField();
		passwordOld.setBounds(119, 46, 128, 17);
		if (!isAdmin) {
			contentPanel.add(passwordOld);
		}
		
		passwordNew1 = new JPasswordField();
		passwordNew1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		passwordNew1.setBounds(119, 71, 128, 17);
		contentPanel.add(passwordNew1);
		
		passwordNew2 = new JPasswordField();
		passwordNew2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		passwordNew2.setBounds(119, 96, 128, 17);
		contentPanel.add(passwordNew2);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				// tlaèítko pro zmìnu hesla 
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						boolean isValidPassword;
						// je-li pøihlašený uživatel admin, tak nemusí znát staré heslo a pøeskakuje validaci
						if(isAdmin) {
							isValidPassword = true;
						}
						// validace zadaného hesla
						else {
							// naètení password
							String plainTextPassword = new String(passwordOld.getPassword());
							user.setPassword(plainTextPassword);
							
							// Kontrola hesla s heslem zakodovaným v DB
							// volání DAO pro validaci password
							isValidPassword = membersService.authenticate(user);
						}	
						//zmìma hesla v DB
						// validace probìhala
						if(isValidPassword) {
							String newPassword = new String(passwordNew1.getPassword());
							String newPassword2 = new String(passwordNew2.getPassword());
							
							// kontrola zadaní dvakrát stejnì nové heslo
							if(newPassword.equals(newPassword2) && !newPassword.equals("")) {
								LOGGER.setLevel(Level.INFO);
								
								// kontrola, že bylo uspìšnì uloženo do DB
								if(membersService.changePassword(user, newPassword) > 0){
									LOGGER.info("Zmìmìno heslo u uživatele id:" + user.getId());
									JOptionPane.showMessageDialog(null, "Heslo zmìnìno.");
									setVisible(false);
								}
								
								// chyba pøi uložení do DB
								else {
									LOGGER.info("Nezdaøilo se zmìnit heslo u uživatele id:" + user.getId());
									JOptionPane.showMessageDialog(null, "Nezdaøila se zmìna hesla.");
									LOGGER.warning("Chyba uložení hesla do DB u user - " + user.getId());
									return;
								}
							}
							
							// chybnì zadané nové heslo
							else{
								JOptionPane.showMessageDialog(null, "Potvzení nového hesla se neshoduje s novým heslem.");
							}
						}
						
						// chybnì zadané stavající heslo
						else {
							JOptionPane.showMessageDialog(null, "Chybnì zadané heslo.");
							LOGGER.info("Chybnì zadané stavající heslo. user - " + user.getId());
							return;
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
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
