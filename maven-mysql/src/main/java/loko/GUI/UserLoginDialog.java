package loko.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import loko.DAO.*;
import loko.core.User;
import service.IFMembersService;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserLoginDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3075207613660115541L;

	private final JPanel contentPanel = new JPanel();

	private IFMembersService membersService; // servisni trida pro DAO
	//private EmployeeDAO employeeDAO;
	private UserDAO userDAO; // pro testovaní pøidat = new UserDAO();	
	private IFMembersDAO membersDAO;
	private JPasswordField passwordField;
	private JComboBox comboBoxUser;
	
	
	public void setMembersService(IFMembersService membersService) {		
		this.membersService = membersService;		
	}
	
	public void setUserDAO(UserDAO theUserDAO) {
		userDAO = theUserDAO;
	}
	
	public void setMembersDAO(IFMembersDAO theMemberDAO) {
		membersDAO = theMemberDAO;
	}

	public void populateUsers(List<User> users) {
		// vytvoøí pole a naslednì naplni box
		comboBoxUser.setModel(new DefaultComboBoxModel(users.toArray(new User[0]))); // vytvoøeni pole s udaji user a do listu pridaní vrácené hodnoty z .toString
	}

	/**
	 * Create the dialog.
	 */
	public UserLoginDialog() {
		
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, "2, 2, fill, fill");
			panel.setLayout(null);
			{
				JLabel lblUivatel = new JLabel("U\u017Eivatel:");
				lblUivatel.setBounds(10, 11, 55, 23);
				panel.add(lblUivatel);
			}
			
			comboBoxUser = new JComboBox();
			comboBoxUser.setBounds(75, 12, 132, 23);
			//inicializace listu
			
			/*
			 * pro testovaní vytvoreni seznamu login
			 */
			//List<User> themUser = userDAO.getUsers(true, 0);
			//populateUsers(themUser);
			
			panel.add(comboBoxUser);
			
			JLabel lblHeslo = new JLabel("Heslo");
			lblHeslo.setBounds(10, 42, 55, 23);
			panel.add(lblHeslo);
			
			passwordField = new JPasswordField();
			passwordField.setBounds(70, 46, 137, 23);
			panel.add(passwordField);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						performUserLogin();
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
						 System.exit(0);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	public JComboBox getUserComboBox() {
		return comboBoxUser;
	}
	
	private void performUserLogin() {
		
		try {
			// get the user id
			if (comboBoxUser.getSelectedIndex() == -1) {						
				JOptionPane.showMessageDialog(UserLoginDialog.this, "You must select a user.", "Error", JOptionPane.ERROR_MESSAGE);				
				return;
			}
			
			
			//Vytvoøení user a naplnìni hodnotami
			User theUser = (User) comboBoxUser.getSelectedItem();
			int userId = theUser.getId();
			boolean admin = theUser.isAdmin();
			
			// naètení password
			String plainTextPassword = new String(passwordField.getPassword());
			theUser.setPassword(plainTextPassword);
			
			
			// Kontrola hesla s heslem zakodovaným v DB
			// volání DAO pro validaci password
			boolean isValidPassword = userDAO.authenticate(theUser);
			if (isValidPassword) {
				// hide the login window
				setVisible(false);

				// now show the main app window
				MembersSearchApp frame = new MembersSearchApp(membersService,userId, admin, userDAO);
				frame.setLoggedInUserName(theUser.getFirstName(), theUser.getLastName());
				frame.refreshMembersView();
				
				frame.setVisible(true);
				
			}
			else {
				// show error message
				JOptionPane.showMessageDialog(this, "Invalid login", "Invalid Login",
						JOptionPane.ERROR_MESSAGE);

				return;			
			}
		}
		catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error during login", "Error",
					JOptionPane.ERROR_MESSAGE);			
		}
	}
}



