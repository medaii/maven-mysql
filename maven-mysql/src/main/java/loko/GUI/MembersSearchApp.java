package loko.GUI;

import java.awt.EventQueue;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JFrame;


import loko.DAO.*;
import loko.core.MemberList;
import loko.core.User;
import loko.loger.LoggerLoko;
import loko.tableModel.MembersListTableModel;
import loko.tableModel.UsersTableModel;
import service.IFMembersService;
import service.MembersServiceImpl;

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * 
 * @author Erik Markoviè
 * 
 * Aplikace pro editaci kartoteky 
 *
 */
public class MembersSearchApp  extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4801718700352877970L;
	private IFMembersService membersService; // instance na service tridu
	private IFUserDAO userDAO = new UserDAO();
	private IFMembersDAO membersDAO;
	//private IFMailsDAO mailsDAO;
	private IFPhoneDAO phoneDAO;
	private JFrame frame;
	private int userId;
	private boolean admin;
	private JLabel lblUser;
	private JTable tableMembers;
	private JTextField textFieldSearchName;
	private JTable tableUser;
	private JButton btnPidanUivatele;
	private JComboBox comboBox;
	private int id_kategorie = 0;
	private final String[] kategorie = {"Všechny", "Muži", "Mládež" , "Dorostenci", "Žáci", "Mini a pøípravka",
																			"Vèetnì neaktivních"};
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Launch the application.
	 * Aplikace první spustí logovací aplikaci
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//vytvoøení LOGGER
					try {
            LoggerLoko.setup();
            LOGGER.setLevel(Level.WARNING);
					} catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems s vytvoreni logger souboru.");
					}
					
					// Výtvoøení okna pro pøihlášení
					UserLoginDialog dialog = new UserLoginDialog();
					
					//inicializace servisni tøidy a uživatelského listu
					dialog.setMembersService(new MembersServiceImpl());
					dialog.populateUsers();
					
					// zobrazení okna pro pøihlášení
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					LOGGER.info("Otevøení okna pro pøihlášení");

				} catch (Exception e) {
					//Chyba pøi spuštìní vlákna
					LOGGER.warning("Pøi spuštìni nastala chyba - " + e.toString());					
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Vytvoøení hlavního okna aplikace po pøihlašení
	 */
	public MembersSearchApp(IFMembersService membersService,int theUserId, boolean theAdmin) {
		this.membersService = membersService;
			userId = theUserId;
			admin = theAdmin;
			phoneDAO = DAOFactory.createDAO(IFPhoneDAO.class);
			
			
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
		textFieldSearchName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				 if (e.getKeyCode()==KeyEvent.VK_ENTER){
		        searchMember();
		    }
			}
		});
		GridBagConstraints gbc_textFieldSearchName = new GridBagConstraints();
		gbc_textFieldSearchName.insets = new Insets(0, 0, 0, 5);
		gbc_textFieldSearchName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSearchName.gridx = 2;
		gbc_textFieldSearchName.gridy = 0;
		panel.add(textFieldSearchName, gbc_textFieldSearchName);
		textFieldSearchName.setColumns(10);
		
		JButton btnHledat = new JButton("Hledat");
		btnHledat.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnHledat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchMember();
				
			}
		});
		GridBagConstraints gbc_btnHledat = new GridBagConstraints();
		gbc_btnHledat.insets = new Insets(0, 0, 0, 5);
		gbc_btnHledat.gridx = 3;
		gbc_btnHledat.gridy = 0;
		panel.add(btnHledat, gbc_btnHledat);
		
		JLabel lblKategorie = new JLabel("Dle kategorie:");
		lblKategorie.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		GridBagConstraints gbc_lblKategorie = new GridBagConstraints();
		gbc_lblKategorie.insets = new Insets(0, 0, 0, 5);
		gbc_lblKategorie.anchor = GridBagConstraints.EAST;
		gbc_lblKategorie.gridx = 4;
		gbc_lblKategorie.gridy = 0;
		panel.add(lblKategorie, gbc_lblKategorie);
		
		comboBox = new JComboBox(kategorie);
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {								
				id_kategorie = comboBox.getSelectedIndex();
				refreshMembersView();
			}
		});
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 5;
		gbc_comboBox.gridy = 0;
		panel.add(comboBox, gbc_comboBox);
		
		JPanel panel_3 = new JPanel();
		membersPanel.add(panel_3, BorderLayout.SOUTH);
		
		JButton btnAddMember = new JButton("P\u0159idat \u010Dlena");
		btnAddMember.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnAddMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddMemberDialog dialog = new AddMemberDialog(membersService, phoneDAO, MembersSearchApp.this);
				dialog.setVisible(true);
			}
		});
		panel_3.add(btnAddMember);
		
		JButton btnEditovatlena = new JButton("Editovat \u010Dlena");
		btnEditovatlena.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		btnEditovatlena.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// vrat vybraný øádek
				int row  = tableMembers.getSelectedRow();
				
				// kontrola, že je vybrany øádek
				if(row < 0) {
					JOptionPane.showMessageDialog(null, "Musite vybrat radek.");
					return;
				}
				
				editaceMember(row);
			}
		});
		panel_3.add(btnEditovatlena);
		
		JButton btnSmazatlena = new JButton("Smazat \u010Dlena");
		btnSmazatlena.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// vrat vybraný øádek
				int row  = tableMembers.getSelectedRow();
				
				// kontrola, že je vybrany øádek
				if(row < 0) {
					JOptionPane.showMessageDialog(null, "Musite vybrat radek.");
					return;
				}
				//potvrzeni že opravdu chcete smazat
				int response = JOptionPane.showConfirmDialog(
						null, "Opravdu chcete smazat mail?", "Confirm", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response != JOptionPane.YES_OPTION) {
					return;
				}
				//nadèteni z vypisu member
				MemberList tempmemberList =  (MemberList) tableMembers.getValueAt(row, MembersListTableModel.OBJECT_COL);
				int id_member = tempmemberList.getId();
				// smazaní zaznamu
				String zprava = "id: " + id_member;
				if(membersService.deleteMember(id_member) > 0) {
					LOGGER.warning("Smazán záznam èlena id:" + id_member);
					zprava += " byl smazán.";
					//refresh tabulky
					refreshMembersView();
				}
				else {
						LOGGER.warning("záznam nebyl úspìšnì smazán");
						zprava += " se nepodaøilo smazat";
				}
				JOptionPane.showMessageDialog(null,
						zprava,"Member smazán",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnSmazatlena.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		if(theAdmin) {
			panel_3.add(btnSmazatlena);			
		}
		
		JScrollPane scrollPane = new JScrollPane();
		membersPanel.add(scrollPane, BorderLayout.CENTER);
		
		tableMembers = new JTable();
		tableMembers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
        JTable table =(JTable) mouseEvent.getSource();
        Point point = mouseEvent.getPoint();
        int row = table.rowAtPoint(point);
        if (mouseEvent.getClickCount() == 2) {
            LOGGER.fine("doubleCLick na radek " + row);
            editaceMember(row);
        }
			}
		});
		scrollPane.setViewportView(tableMembers);
		tableMembers.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		
		JPanel User = new JPanel();
		tabbedPane.addTab("User", null, User, null);
		User.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		User.add(scrollPane_1, BorderLayout.CENTER);
		
		tableUser = new JTable();
		tableUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable table =(JTable) e.getSource();
        Point point = e.getPoint();
        int row = table.rowAtPoint(point);
        if (e.getClickCount() == 2) {
            LOGGER.fine("doubleCLick na radek user" + row);
            editUser(row);
        }
			}
		});
		scrollPane_1.setViewportView(tableUser);
		
		JPanel panel_1 = new JPanel();
		User.add(panel_1, BorderLayout.SOUTH);
		
		btnPidanUivatele = new JButton("P\u0159idan\u00ED u\u017Eivatele");
		btnPidanUivatele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnPidanUivatele.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		panel_1.add(btnPidanUivatele);
		
		JButton btnEditaceUdaj = new JButton("Editace udaj\u016F");
		btnEditaceUdaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			  // vrat vybraný øádek
				int row  = tableUser.getSelectedRow();
				
				// kontrola, že je vybrany øádek
				if(row < 0) {
					JOptionPane.showMessageDialog(null, "Musite vybrat radek.");
					return;
				}
				editUser(row);
			}
		});
		btnEditaceUdaj.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		panel_1.add(btnEditaceUdaj);
		
		JButton btnZmnaHesla = new JButton("Zm\u011Bna hesla");
		btnZmnaHesla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// vrat vybraný øádek
				int row  = tableUser.getSelectedRow();
				
				// kontrola, že je vybrany øádek
				if(row < 0) {
					JOptionPane.showMessageDialog(null, "Musite vybrat radek.");
					return;
				}
				User user = (User) tableUser.getValueAt(row, UsersTableModel.OBJECT_COL);
				ChangePassword dialog = new ChangePassword(user, userDAO,MembersSearchApp.this, admin );
				dialog.setVisible(true);
			}
		});
		btnZmnaHesla.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		panel_1.add(btnZmnaHesla);
		refreshUsersView();
		btnPidanUivatele.setEnabled(admin);
		initialize();
	}
	
	/**
	 * pøechod na editaci èlena
	 */
	public void editaceMember(int row) {
		MemberList tempmemberList =  (MemberList) tableMembers.getValueAt(row, MembersListTableModel.OBJECT_COL);
		int id_member = tempmemberList.getId();
		MemberDialog dialog = new MemberDialog(id_member,membersService, phoneDAO, MembersSearchApp.this);
		dialog.setVisible(true);
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
	 * vyhledavani clena
	 */
	
	private void searchMember() {
	// zislat vyhledavane slovo
		
		//zavolani si pres DAO pro data
		
		//kdyz je prazny seznam
		
		//tisk
		
		try {
			String name =  textFieldSearchName.getText();
			
			List<MemberList> membersList = null;
			if (name != null && name.trim().length() > 0) {
				membersList = membersService.searchAllMembers(name, true, id_kategorie);
			} else {
					refreshMembersView();
					return;
			}
			for (MemberList temp : membersList) {
				LOGGER.info("Vyhledavani:\r\n" + temp.toString());
			}
			//vytvoøení modelu pro naplnìní tabulky
			MembersListTableModel model = new MembersListTableModel(membersList);
			
			tableMembers.setModel(model);
			
		}
		catch (Exception e2) {
			JOptionPane.showMessageDialog(null, "Chyba lisen - " + e2);
		}
	}
	/**
	 * Otevøevni dialogu s editaci uètu 
	 * @param row - vybraný øádek v tabulce
	 */
	private void editUser(int row) {
		User user = (User) tableUser.getValueAt(row, UsersTableModel.OBJECT_COL);
		UserDialog dialog = new UserDialog(user, userDAO,MembersSearchApp.this, admin, false );
		dialog.setVisible(true);
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
			List<MemberList> members = membersService.getAllMemberList(true, id_kategorie);
			
			// create the model and update the "table"
			MembersListTableModel model = new MembersListTableModel(members);

			tableMembers.setModel(model);
		} catch (Exception exc) {
			LOGGER.warning("Chyba vytvoreni modelu - " + exc);
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}
	//naètení uživatelu
	public void refreshUsersView() {

		try {
			List<User> users = membersService.getUsers(admin, userId);

			// Vytvoøení tabulky s uživately
			UsersTableModel model = new UsersTableModel(users);

			tableUser.setModel(model);
			
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
}
