package loko.GUI;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;


import loko.DAO.*;
import loko.core.MemberList;
import loko.core.User;
import loko.tableModel.MembersListTableModel;

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class MembersSearchApp  extends JFrame{

	private MembersDAO membersDAO;
	private IFMailsDAO mailsDAO;
	private IFPhoneDAO phoneDAO;
	private UserDAO userDAO;
	private JFrame frame;
	private int userId;
	private boolean admin;
	private JLabel lblUser;
	private JTable tableMembers;
	private JTextField textFieldSearchName;

	/**
	 * Launch the application.
	 * Aplikace první spustí logovací aplikaci
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					MembersDAO membersDAO = new MembersDAO();
					UserDAO userDAO = new UserDAO();
					
					// Get users
					List<User> users = userDAO.getUsers(true, 0);

					// Show login dialog
					UserLoginDialog dialog = new UserLoginDialog();
					dialog.populateUsers(users);
					dialog.setMembersDAO(membersDAO);
					dialog.setUserDAO(userDAO);
				
					
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MembersSearchApp(int theUserId, boolean theAdmin, MembersDAO theMemberDAO, UserDAO theUserDAO) {
		
			userId = theUserId;
			admin = theAdmin;
			membersDAO = theMemberDAO;
			mailsDAO = DAOFactory.createDAO(IFMailsDAO.class);
			phoneDAO = DAOFactory.createDAO(IFPhoneDAO.class);
			userDAO = theUserDAO;
			
			
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		setTitle("Loko Vršovice - Kartoteka");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 753, 632);
		
		
		JPanel TopPanel = new JPanel();
		getContentPane().add(TopPanel, BorderLayout.NORTH);
		GridBagLayout gbl_TopPanel = new GridBagLayout();
		gbl_TopPanel.columnWidths = new int[]{0, 194, 46, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_TopPanel.rowHeights = new int[]{14, 0};
		gbl_TopPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_TopPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		TopPanel.setLayout(gbl_TopPanel);
		
		JLabel lblLogin = new JLabel("Login: ");
		lblLogin.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		GridBagConstraints gbc_lblLogin = new GridBagConstraints();
		gbc_lblLogin.insets = new Insets(0, 0, 0, 5);
		gbc_lblLogin.gridx = 0;
		gbc_lblLogin.gridy = 0;
		TopPanel.add(lblLogin, gbc_lblLogin);
		
		lblUser = new JLabel("User");
		lblUser.setFont(new Font("Times New Roman", Font.BOLD, 15));
		GridBagConstraints gbc_lblUser = new GridBagConstraints();
		gbc_lblUser.insets = new Insets(0, 0, 0, 5);
		gbc_lblUser.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblUser.gridx = 1;
		gbc_lblUser.gridy = 0;
		TopPanel.add(lblUser, gbc_lblUser);
		
		JButton btnZavt = new JButton("Zav\u0159\u00EDt");
		btnZavt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnZavt.setForeground(Color.WHITE);
		btnZavt.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnZavt.setBackground(Color.BLACK);
		GridBagConstraints gbc_btnZavt = new GridBagConstraints();
		gbc_btnZavt.gridwidth = 2;
		gbc_btnZavt.gridx = 17;
		gbc_btnZavt.gridy = 0;
		TopPanel.add(btnZavt, gbc_btnZavt);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel membersPanel = new JPanel();
		membersPanel.setToolTipText("");
		tabbedPane.addTab("Èlenové", null, membersPanel, null);
		membersPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		membersPanel.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblHledan = new JLabel("Hledan\u00ED:");
		lblHledan.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		GridBagConstraints gbc_lblHledan = new GridBagConstraints();
		gbc_lblHledan.insets = new Insets(0, 0, 0, 5);
		gbc_lblHledan.anchor = GridBagConstraints.EAST;
		gbc_lblHledan.gridx = 1;
		gbc_lblHledan.gridy = 0;
		panel.add(lblHledan, gbc_lblHledan);
		
		textFieldSearchName = new JTextField();
		GridBagConstraints gbc_textFieldSearchName = new GridBagConstraints();
		gbc_textFieldSearchName.insets = new Insets(0, 0, 0, 5);
		gbc_textFieldSearchName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSearchName.gridx = 2;
		gbc_textFieldSearchName.gridy = 0;
		panel.add(textFieldSearchName, gbc_textFieldSearchName);
		textFieldSearchName.setColumns(10);
		
		JButton btnHledat = new JButton("Hledat");
		btnHledat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// zislat vyhledavane slovo
				
				//zavolani si pres DAO pro data
				
				//kdyz je prazny seznam
				
				//tisk
				try {
					String name =  textFieldSearchName.getText();
					
					List<MemberList> membersList = null;
					if (name != null && name.trim().length() > 0) {
						membersList = membersDAO.seachAllMembers(name, true);
					} else {
						membersList = membersDAO.getAllMemberList(true);
					}
					for (MemberList temp : membersList) {
						System.out.println(temp);
					}
					//vytvoøení modelu pro naplnìní tabulky
					MembersListTableModel model = new MembersListTableModel(membersList);
					
					tableMembers.setModel(model);
					
				}
				catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Chyba lisen - " + e2);
				}
				
			}
		});
		GridBagConstraints gbc_btnHledat = new GridBagConstraints();
		gbc_btnHledat.insets = new Insets(0, 0, 0, 5);
		gbc_btnHledat.gridx = 3;
		gbc_btnHledat.gridy = 0;
		panel.add(btnHledat, gbc_btnHledat);
		
		JLabel lblKategorie = new JLabel("Dle kategorie:");
		GridBagConstraints gbc_lblKategorie = new GridBagConstraints();
		gbc_lblKategorie.insets = new Insets(0, 0, 0, 5);
		gbc_lblKategorie.anchor = GridBagConstraints.EAST;
		gbc_lblKategorie.gridx = 4;
		gbc_lblKategorie.gridy = 0;
		panel.add(lblKategorie, gbc_lblKategorie);
		
		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 5;
		gbc_comboBox.gridy = 0;
		panel.add(comboBox, gbc_comboBox);
		
		JPanel panel_3 = new JPanel();
		membersPanel.add(panel_3, BorderLayout.SOUTH);
		
		JButton btnAddMember = new JButton("P\u0159idat \u010Dlena");
		btnAddMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddMemberDialog dialog = new AddMemberDialog(membersDAO,mailsDAO, phoneDAO, MembersSearchApp.this);
				dialog.setVisible(true);
			}
		});
		panel_3.add(btnAddMember);
		
		JButton btnEditovatlena = new JButton("Editovat \u010Dlena");
		btnEditovatlena.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// vrat vybraný øádek
				int row  = tableMembers.getSelectedRow();
				
				// kontrola, že je vybrany øádek
				if(row < 0) {
					JOptionPane.showMessageDialog(null, "Musite vybrat radek.");
					return;
				}
				MemberList tempmemberList =  (MemberList) tableMembers.getValueAt(row, MembersListTableModel.OBJECT_COL);
				int id_member = tempmemberList.getId();
				
				MemberDialog dialog = new MemberDialog(id_member,membersDAO,mailsDAO, phoneDAO, MembersSearchApp.this);
				dialog.setVisible(true);
			}
		});
		panel_3.add(btnEditovatlena);
		
		JScrollPane scrollPane = new JScrollPane();
		membersPanel.add(scrollPane, BorderLayout.CENTER);
		
		tableMembers = new JTable();
		scrollPane.setViewportView(tableMembers);
		tableMembers.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("User", null, panel_1, null);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Nastaveni user
	 */
	public void setLoggedInUserName(String firstName, String lastName) {
		lblUser.setText(firstName + " " + lastName);
	}
	
	/**
	 * obnovení seznamu èlenù
	 * 
	 */
	public void refreshMembersView() {

		try {
			List<MemberList> members = membersDAO.getAllMemberList(true);
			
			// create the model and update the "table"
			MembersListTableModel model = new MembersListTableModel(members);

			tableMembers.setModel(model);
		} catch (Exception exc) {
			System.out.println("Chyba vytvoreni modelu - " + exc);
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}
}
