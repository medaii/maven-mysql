package loko.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import loko.DAO.UserDAO;
import loko.core.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChangePassword extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField passwordOld;
	private JPasswordField passwordNew1;
	private JPasswordField passwordNew2;
	
	private User user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			//ChangePassword dialog = new ChangePassword();
			//dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			//dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ChangePassword(User user, UserDAO userDAO, MembersSearchApp membersSearchApp) {
		this.setModal(true);
		this.user = user;
		
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
		
		JLabel lblStarHeslo = new JLabel("Star\u00E9 heslo:");
		lblStarHeslo.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblStarHeslo.setBounds(10, 49, 99, 14);
		contentPanel.add(lblStarHeslo);
		
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
		contentPanel.add(passwordOld);
		
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
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						String newPassword = new String(passwordNew1.getPassword());
						String newPassword2 = new String(passwordNew2.getPassword());
						if(newPassword.equals(newPassword2)) {
							System.out.println("yes");
						}
						else{
							JOptionPane.showMessageDialog(null, "Potvzení nového hesla se neshoduje s novým heslem.");
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
