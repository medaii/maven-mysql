package loko.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.util.List;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import service.IFMembersService;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import loko.entity.User;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class UserLoginDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3075207613660115541L;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final JPanel contentPanel = new JPanel();

	private IFMembersService membersService; // servisni trida pro DAO
	//private EmployeeDAO employeeDAO;
	//private IFUserDAO userDAO; // pro testovaní pøidat = new UserDAO();	

	private JPasswordField passwordField;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBoxUser;
	
	
	public void setMembersService(IFMembersService membersService) {		
		this.membersService = membersService;		
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void populateUsers() {
		if(membersService != null) {
			// vytvoøí pole a naslednì naplni box
			List<User> users = membersService.getUsers(true, 0);
			comboBoxUser.setModel(new DefaultComboBoxModel(users.toArray(new User[0]))); // vytvoøeni pole s udaji user a do listu pridaní vrácené hodnoty z .toString
		}
		else {
			LOGGER.warning("Neni instance na servisní tøídu.");			
		}
	}

	/**
	 * Vytvoøení okna.
	 */
	@SuppressWarnings("rawtypes")
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
				lblUivatel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
				lblUivatel.setBounds(10, 11, 55, 23);
				panel.add(lblUivatel);
			}
			
			comboBoxUser = new JComboBox();
			comboBoxUser.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			comboBoxUser.setBounds(75, 12, 132, 23);
			
			panel.add(comboBoxUser);
			
			JLabel lblHeslo = new JLabel("Heslo:");
			lblHeslo.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			lblHeslo.setBounds(10, 46, 55, 23);
			panel.add(lblHeslo);
			
			passwordField = new JPasswordField();
			passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			passwordField.setBounds(75, 46, 137, 23);
			panel.add(passwordField);
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
						performUserLogin();
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
						 System.exit(0);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	@SuppressWarnings("rawtypes")
	public JComboBox getUserComboBox() {
		return comboBoxUser;
	}
	
	/**
	 * Kontrola hesla pøi pøihlašování uživatele
	 */
	private void performUserLogin() {
		
		try {
			// Vybrání uživatele
			if (comboBoxUser.getSelectedIndex() == -1) {						
				JOptionPane.showMessageDialog(UserLoginDialog.this, "You must select a user.", "Error", JOptionPane.ERROR_MESSAGE);				
				return;
			}
			
			//Vytvoøení user a naplnìni hodnotami select
			User theUser = (User) comboBoxUser.getSelectedItem();
			int userId = theUser.getId();
			boolean admin = theUser.isAdmin();
			
			// naètení password
			// Kontrola hesla s heslem zakodovaným v DB
			// volání Service pro validaci password user
			boolean isValidPassword = membersService.authenticate(new String(passwordField.getPassword()).getBytes("UTF-8"), userId);
			
			if (isValidPassword) {
				// validace v poøádku, skrytí okna a spuštìní membersearch okna
				LOGGER.info("Oveøené pøihlašení uživatele " + theUser.getFirstName() + " " + theUser.getLastName());
				setVisible(false);

				// Neni otevøení okna membersearch (hlavní okno)
				MembersSearchApp frame = new MembersSearchApp(membersService,userId, admin);
				frame.setLoggedInUserName(theUser.getFirstName(), theUser.getLastName());
				frame.refreshMembersView();
				
				frame.setVisible(true);
				LOGGER.fine("Spušteno hlavní okno.");
				
			}
			else {
				//chybné heslo
				LOGGER.info("Chybné heslo pøi pøihlašení uživatele " + theUser.getFirstName() + " " + theUser.getLastName());
				
				// Vyskoèení okna s informaci, že bylo zadáno nesprávné heslo
				JOptionPane.showMessageDialog(this, "Invalid login", "Invalid Login",
						JOptionPane.ERROR_MESSAGE);
				
				return;			
			}
		}
		catch (Exception exc) {
			// zalogování
			LOGGER.warning("Vyhozeni vyjímky pøi kontrole pøihlašovacích údajù - " + exc.toString());
			// vypis do konzole
			exc.printStackTrace();
			// vyskakovací okno, že nastala chyba behem logování
			JOptionPane.showMessageDialog(this, "Error during login.", "Error",
					JOptionPane.ERROR_MESSAGE);
			
		}
	}
}