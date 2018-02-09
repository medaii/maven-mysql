package loko.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import loko.core.User;
import service.IFMembersService;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
/**
 * 
 * @author Erik Markoviè
 *
 */
public class UserDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 533543518747791069L;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldFirstName;
	private JTextField textFieldSurName;
	private JTextField textFieldMail;
	private JCheckBox checkBoxAdmin;
	
	private User user;
	


	/**
	 * Otevøení nového okna vytvoøení nového uživatele nebo editaci.
	 */
	public UserDialog(User user,IFMembersService membersService, MembersSearchApp membersSearchApp, boolean isAdmin, boolean addUser) {
		this.user = user;
		if(addUser) {
			if(!isAdmin) {
				setVisible(false);
			}
			setTitle("P\u0159idat nov\u00E9ho u\u017Eivatele");
		}
		else {
			setTitle("Editace user");
		}
		
		
		this.setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblUser = new JLabel("User:");
			lblUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			lblUser.setBounds(10, 11, 78, 23);
			contentPanel.add(lblUser);
		}
		{
			JLabel lblNameuser = new JLabel("nameUser");
			lblNameuser.setFont(new Font("Times New Roman", Font.BOLD, 12));
			lblNameuser.setBounds(98, 15, 127, 14);
			contentPanel.add(lblNameuser);
		}
		{
			JLabel lblKesniJmno = new JLabel("K\u0159esni jm\u00E9no");
			lblKesniJmno.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			lblKesniJmno.setBounds(10, 51, 98, 14);
			contentPanel.add(lblKesniJmno);
		}
		{
			textFieldFirstName = new JTextField();
			textFieldFirstName.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			textFieldFirstName.setBounds(121, 48, 115, 20);
			contentPanel.add(textFieldFirstName);
			textFieldFirstName.setColumns(10);
		}
		{
			JLabel lblPjmen = new JLabel("P\u0159\u00EDjmen\u00ED");
			lblPjmen.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			lblPjmen.setBounds(10, 80, 98, 14);
			contentPanel.add(lblPjmen);
		}
		{
			textFieldSurName = new JTextField();
			textFieldSurName.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			textFieldSurName.setBounds(121, 79, 115, 20);
			contentPanel.add(textFieldSurName);
			textFieldSurName.setColumns(10);
		}
		{
			JLabel lblEmail = new JLabel("Email");
			lblEmail.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			lblEmail.setBounds(10, 123, 98, 14);
			contentPanel.add(lblEmail);
		}
		{
			textFieldMail = new JTextField();
			textFieldMail.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			textFieldMail.setBounds(121, 120, 237, 20);
			contentPanel.add(textFieldMail);
			textFieldMail.setColumns(10);
		}
		{
			JLabel lblAdmin = new JLabel("Admin");
			lblAdmin.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			lblAdmin.setBounds(10, 185, 46, 14);
			contentPanel.add(lblAdmin);
		}
		
		checkBoxAdmin = new JCheckBox("");
		checkBoxAdmin.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		checkBoxAdmin.setBounds(121, 181, 97, 23);
		contentPanel.add(checkBoxAdmin);
		
		checkBoxAdmin.setEnabled(isAdmin);
		
		if(!addUser){
			setAddDialog();
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				
				// vytvoøení nového uživatele
				JButton okButton = new JButton("OK");
				okButton.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
					// nadètení z formuláøe  get date from form
						String firstName = textFieldFirstName.getText();
						String lastName = textFieldSurName.getText();
						String mail = textFieldMail.getText();
						boolean admin = checkBoxAdmin.isSelected();
						
						if(addUser) {
							if(!(firstName.isEmpty() || firstName == "") || !(lastName.isEmpty() || firstName == "") || !(mail.isEmpty() || mail == "")) {
								User theUser = new User(lastName, firstName, mail, admin, "java");
								if(membersService.addUser(theUser) > 0) {
									
								}
								else {
									LOGGER.warning("Chyba pøi uložení objektu do DB user - " + user.toString());
									JOptionPane.showMessageDialog(null, "Nezdaøila se zmìna údajù.");
								}
							}
							else {
								
								JOptionPane.showMessageDialog(null, "Nezdaøila se zmìna údajù, jelikož nejsou vyplnìné všechny udaje.");
								return;
							}
						}
						else {
								// uložení zmeny do databáze
								User theUser = new User(user.getId(),lastName, firstName, mail, admin);
								if(membersService.updateUser(theUser) > 0) {
									LOGGER.info("Zmìna udaje uživatele zmìnìna id uživate: " + theUser.getId());
									}
								//chyba pøi ukladání zmìn do DB
								else {
									LOGGER.warning("Chyba pøi uložení objektu do DB user - " + user.toString());
									JOptionPane.showMessageDialog(null, "Nezdaøila se zmìna údajù.");
									}
						}
						membersSearchApp.refreshUsersView();
						setVisible(false);
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
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	private void setAddDialog() {
		textFieldFirstName.setText(user.getFirstName());
		textFieldSurName.setText(user.getLastName());
		textFieldMail.setText(user.getEmail());
		checkBoxAdmin.setSelected(user.isAdmin());
	}
}
