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

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import loko.entity.User;
import loko.service.IFMembersService;

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
	//private IFUserDAO userDAO; // pro testovan� p�idat = new UserDAO();	

	private JPasswordField passwordField;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBoxUser;
	
	
	public void setMembersService(IFMembersService membersService) {		
		this.membersService = membersService;		
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void populateUsers() {
		if(membersService != null) {
			// vytvo�� pole a nasledn� naplni box
			List<User> users = membersService.getUsers(true, 0);
			comboBoxUser.setModel(new DefaultComboBoxModel(users.toArray(new User[0]))); // vytvo�eni pole s udaji user a do listu pridan� vr�cen� hodnoty z .toString
		}
		else {
			LOGGER.warning("Neni instance na servisn� t��du.");			
		}
	}

	/**
	 * Vytvo�en� okna.
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
	 * Kontrola hesla p�i p�ihla�ov�n� u�ivatele
	 */
	private void performUserLogin() {
		
		try {
			// Vybr�n� u�ivatele
			if (comboBoxUser.getSelectedIndex() == -1) {						
				JOptionPane.showMessageDialog(UserLoginDialog.this, "You must select a user.", "Error", JOptionPane.ERROR_MESSAGE);				
				return;
			}
			
			//Vytvo�en� user a napln�ni hodnotami select
			User theUser = (User) comboBoxUser.getSelectedItem();
			int userId = theUser.getId();
			boolean admin = theUser.isAdmin();
			
			// na�ten� password
			// Kontrola hesla s heslem zakodovan�m v DB
			// vol�n� Service pro validaci password user
			boolean isValidPassword = membersService.authenticate(new String(passwordField.getPassword()).getBytes("UTF-8"), userId);
			
			if (isValidPassword) {
				// validace v po��dku, skryt� okna a spu�t�n� membersearch okna
				LOGGER.info("Ove�en� p�ihla�en� u�ivatele " + theUser.getFirstName() + " " + theUser.getLastName());
				setVisible(false);

				// Neni otev�en� okna membersearch (hlavn� okno)
				MembersSearchApp frame = new MembersSearchApp(membersService,userId, admin);
				frame.setLoggedInUserName(theUser.getFirstName(), theUser.getLastName());
				frame.refreshMembersView();
				
				frame.setVisible(true);
				LOGGER.fine("Spu�teno hlavn� okno.");
				
			}
			else {
				//chybn� heslo
				LOGGER.info("Chybn� heslo p�i p�ihla�en� u�ivatele " + theUser.getFirstName() + " " + theUser.getLastName());
				
				// Vysko�en� okna s informaci, �e bylo zad�no nespr�vn� heslo
				JOptionPane.showMessageDialog(this, "Invalid login", "Invalid Login",
						JOptionPane.ERROR_MESSAGE);
				
				return;			
			}
		}
		catch (Exception exc) {
			// zalogov�n�
			LOGGER.warning("Vyhozeni vyj�mky p�i kontrole p�ihla�ovac�ch �daj� - " + exc.toString());
			// vypis do konzole
			exc.printStackTrace();
			// vyskakovac� okno, �e nastala chyba behem logov�n�
			JOptionPane.showMessageDialog(this, "Error during login.", "Error",
					JOptionPane.ERROR_MESSAGE);
			
		}
	}
}