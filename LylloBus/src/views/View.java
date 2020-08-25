package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;

import com.toedter.calendar.JDateChooser;

import controllers.Controller;
import lylloBus.Bookings;
import models.Model;

public class View extends JFrame {
	private Model model;
	private static JFrame frame;
	private static JTabbedPane mainPanel;
	
	//tabs panels
	private JPanel jPClient, jPReceipt, jPCar, jPBooking, jPRoutes;
	
	//tabs panels icons
	ImageIcon iClient = new ImageIcon("images/client.png");
	ImageIcon iReceipt = new ImageIcon("images/receipt.png");
	ImageIcon iCar = new ImageIcon("images/van.png");
	ImageIcon iBooking = new ImageIcon("images/calendar.png");
	ImageIcon iRoutes = new ImageIcon("images/routes.png");
	
	//Login panel
	private JPanel jPLogin;
	private JLabel jLUser, jLUserPassword;
	private JTextField jTUser;
	private JPasswordField jTUserPassword;
	private JButton jBLogin;
	
	//clients panel
	private JTable jTClients;
	private JScrollPane jPTableClients;
	private JPanel jPEditClient, jPButtonsClient;
	private JLabel jLName, jLNif, jLEmail, jLAddress, jLPostCode, jLPhone, jLState;
	private JTextField jTFName, jTFNif, jTFEmail, jTFAddress, jTFPostCode,jTFPhone;
	private JComboBox jCBState;
	private JButton jBAddClient,jBUpdateClient,jBDeleteClient;
	
	//receipts
	private JScrollPane jPTableReceipts;
	private JTable jTReceipts;
	private JPanel jPEditReceipt, jPButtonsReceipt;
	private JLabel jLType, jLReceiptClient, jLValue, jLEmissionDate, jLPaymentState;
	private JComboBox jCBType, jCBReceiptClient, jCBPayment;
	private JTextField jTFValue;
	private JDateChooser emissionDate;
	private JButton jBAddReceipt, jBUpdateReceipt, jBDeleteReceipt;
	
	//cars panel
	private JScrollPane jPTableCars;
	private JTable jTCars;
	private JPanel jPEditCar, jPButtonsCar;
	private JLabel jLPlate, jLFuel, jLSeats;
	private JTextField jTFPlate, jTFuel, jTFSeats;
	private JButton jBAddCar, jBUpdateCar, jBDeleteCar;
	
	//bookings panel
	private JScrollPane jSPBookings;
	private JList jLBookings;
	private JPanel jPDateTime, jPEditBooking,jPDate, jPButtonsBooking, jPDayBookings, jPDatePrint;
	private JButton jBAddBooking, jBUpdateBooking, jBDeleteBooking, jBPrint;
	private JLabel jLDate, jLDescription, jLBookingClient, jLDay, jLOrigin, jLDestination, jLTime;
	private JDateChooser jDCBooking;
	private JTextField jTFDescription, jTFOrigin, jTFDestination;
	private JComboBox jCBookingClient;
	private JFormattedTextField jTFTime;
	
	//Routes panel
	private JPanel jPMap, jPRoutesInf, jPNoPeople, jPGroup, jPTripType;
	private JLabel jLFrom, jLTo, jLDistance, jLBudget, jLServiceType, jLNoPeople, jLWeekDays, jLGroup, jLTripType;
	private JTextField jTFrom, jTFTo, jTFDistance, jTFBudget, jTFServiceType, jTFNoPeople;
	private JButton jBBudget, jBMin, jBPlus;
	private JRadioButton jRBRegular, jRBOccasional, jRBSchool, jRBIndividual, jRBOneWay, jRBTwoWays;
	private List<JCheckBox> weekdays;
	private JCheckBox jCBMonday, jCBTuesday, jCBWednesday, jCBThursday, jCBFriday, jCBSaturday, jCBSunday;
	//browser settings
	private CefClient client = null;
	private CefApp cefApp;
	static CefBrowser browser = null;
	
	
	
	public void openFrame(){
		frame.setSize(900,700);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		this.setVisible(false);
	}

	public View(Model model, Controller controller) {
		// TODO Auto-generated constructor stub
		this.model = model;
		
		addWindowListener(controller);
		controller.addView(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 371, 245);
		setResizable(false);
		setLocationRelativeTo(null);
		
		setTitle("Login");
		jPLogin = new JPanel();
	    jPLogin.setBorder(new EmptyBorder(5, 5, 5, 5));
	    setContentPane(jPLogin);
	    jPLogin.setLayout(null);
	    
	    jLUser = new JLabel("Username");
		jLUser.setBounds(65, 23, 73, 72);
		jPLogin.add(jLUser);
		
		jLUserPassword = new JLabel("Password");
		jLUserPassword.setBounds(65, 84, 73, 72);
		jPLogin.add(jLUserPassword);
		
		jTUser = new JTextField();
		jTUser.setBounds(148, 49, 136, 20);
		jPLogin.add(jTUser);
		jTUser.setColumns(10);
		
		jTUserPassword = new JPasswordField();
		jTUserPassword.setColumns(10);
		jTUserPassword.setBounds(148, 110, 136, 20);
		jPLogin.add(jTUserPassword);
		
		jBLogin = new JButton("Login");
		jBLogin.setBounds(239, 150, 89, 23);
		jPLogin.add(jBLogin);
		jBLogin.setActionCommand("Login");
		jBLogin.addActionListener(controller);
		
		setVisible(true);
		
		//Mainframe
		frame = new JFrame();
		frame.setTitle("LylloBus");
		frame.setResizable(false);
		frame.setSize(1200, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainPanel = new JTabbedPane();
		
		//CLIENTS TAB
		jPClient = new JPanel(new BorderLayout());
		
		jPEditClient = new JPanel(new GridLayout(4,4, 10, 10));
		jPEditClient.setBorder(new EmptyBorder(5, 5, 20, 5));
		
		jLName = new JLabel("Name: ");
		jPEditClient.add(jLName);
		jTFName = new JTextField();
		jPEditClient.add(jTFName);
		
		jLNif = new JLabel("NIF: ");
		jPEditClient.add(jLNif);
		jTFNif = new JTextField(9);
		jPEditClient.add(jTFNif);
		
		jLAddress = new JLabel("Address: ");
		jPEditClient.add(jLAddress);
		jTFAddress = new JTextField();
		jPEditClient.add(jTFAddress);
		
		jLEmail = new JLabel("Email: ");
		jPEditClient.add(jLEmail);
		jTFEmail = new JTextField();
		jPEditClient.add(jTFEmail);
		
		jLPostCode = new JLabel("Post code: ");
		jPEditClient.add(jLPostCode);
		jTFPostCode = new JTextField();
		jPEditClient.add(jTFPostCode);
		
		jLPhone = new JLabel("Phone: ");
		jPEditClient.add(jLPhone);
		jTFPhone = new JTextField();
		jPEditClient.add(jTFPhone);
		
		jLState = new JLabel("State: ");
		jPEditClient.add(jLState);
		String[] state = {"Open", "Old", "Closed", "Future"};
		jCBState = new JComboBox(state);
		jCBState.setSelectedIndex(0);
		jPEditClient.add(jCBState);
		
		jPEditClient.add(new JLabel());
		
		jPButtonsClient = new JPanel();
		jBAddClient = new JButton("Add");
		jBAddClient.setActionCommand("AddClient");
		jBAddClient.addActionListener(controller);
		jPButtonsClient.add(jBAddClient);
		
		jBUpdateClient = new JButton("Update");
		jBUpdateClient.setActionCommand("UpdateClient");
		jBUpdateClient.addActionListener(controller);
		jPButtonsClient.add(jBUpdateClient);
		
		jBDeleteClient = new JButton("Delete");
		jBDeleteClient.setActionCommand("DeleteClient");
		jBDeleteClient.addActionListener(controller);
		jPButtonsClient.add(jBDeleteClient);
		jPEditClient.add(jPButtonsClient);
		
		jPClient.add(jPEditClient, BorderLayout.PAGE_START);
		
		jPTableClients = new JScrollPane();
		jTClients = new JTable();
		jTClients.setRowSelectionAllowed(true);
		jTClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTClients.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt){
				int selectedRow = jTClients.getSelectedRow();
				TableModel tableModel = jTClients.getModel();
				
				jTFName.setText(tableModel.getValueAt(selectedRow, 1).toString());
				jTFNif.setText(tableModel.getValueAt(selectedRow, 2).toString());
				jTFAddress.setText(tableModel.getValueAt(selectedRow, 4).toString());
				jTFEmail.setText(tableModel.getValueAt(selectedRow, 3).toString());
				jTFPostCode.setText(tableModel.getValueAt(selectedRow, 5).toString());
				jTFPhone.setText(tableModel.getValueAt(selectedRow, 6).toString());
				jCBState.setSelectedItem(tableModel.getValueAt(selectedRow, 7).toString());
			}
		});
		jPTableClients.setViewportView(jTClients);
		
		jPClient.add(jPTableClients, BorderLayout.CENTER);
		mainPanel.addTab("Clients", iClient, jPClient, "Clients list");
		mainPanel.setMnemonicAt(0, KeyEvent.VK_1);
		
		
		//RECEIPTS TAB
		jPReceipt = new JPanel(new BorderLayout());
		
		jPEditReceipt = new JPanel(new GridLayout(3,4, 10, 10));
		jPEditReceipt.setBorder(new EmptyBorder(5, 5, 20, 5));
		
		jLType = new JLabel("Type: ");
		jPEditReceipt.add(jLType);
		String[] type = {"Normal", "Simplified", "Budget"};
		jCBType = new JComboBox(type);
		jPEditReceipt.add(jCBType);
		
		jLReceiptClient = new JLabel("Client: ");
		jPEditReceipt.add(jLReceiptClient);
		jCBReceiptClient = new JComboBox();
		jPEditReceipt.add(jCBReceiptClient);
		
		jLValue = new JLabel("Value: ");
		jPEditReceipt.add(jLValue);
		jTFValue = new JTextField();
		jPEditReceipt.add(jTFValue);
		
		jLEmissionDate = new JLabel("Emission :");
		jPEditReceipt.add(jLEmissionDate);
		emissionDate = new JDateChooser();
		emissionDate.setDateFormatString("yyyy-MM-dd");
		jPEditReceipt.add(emissionDate);
		
		
		jLPaymentState = new JLabel("Payment state: ");
		jPEditReceipt.add(jLPaymentState);
		String[] payment = { "Paid", "Canceled", "Waiting"};
		jCBPayment = new JComboBox(payment);
		jPEditReceipt.add(jCBPayment);
		
		jPEditReceipt.add(new JLabel());
		
		jPButtonsReceipt = new JPanel();
		jBAddReceipt = new JButton("Add");
		jBAddReceipt.setActionCommand("AddReceipt");
		jBAddReceipt.addActionListener(controller);
		jPButtonsReceipt.add(jBAddReceipt);
		
		jBUpdateReceipt = new JButton("Update");
		jBUpdateReceipt.setActionCommand("UpdateReceipt");
		jBUpdateReceipt.addActionListener(controller);
		jPButtonsReceipt.add(jBUpdateReceipt);
		
		jBDeleteReceipt = new JButton("Delete");
		jBDeleteReceipt.setActionCommand("DeleteReceipt");
		jBDeleteReceipt.addActionListener(controller);
		jPButtonsReceipt.add(jBDeleteReceipt);
		jPEditReceipt.add(jPButtonsReceipt);
		
		jPReceipt.add(jPEditReceipt, BorderLayout.PAGE_START);
		
		jPTableReceipts = new JScrollPane();
		jTReceipts = new JTable();
		jTReceipts.setRowSelectionAllowed(true);
		jTReceipts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTReceipts.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt){
				int selectedRow = jTReceipts.getSelectedRow();
				TableModel tableModel = jTReceipts.getModel();
				
				jCBType.setSelectedItem(tableModel.getValueAt(selectedRow, 1).toString());
				jCBReceiptClient.setSelectedItem(model.getClientName(Integer.parseInt(tableModel.getValueAt(selectedRow, 2).toString())));
				jTFValue.setText(tableModel.getValueAt(selectedRow, 3).toString());
				Date receiptDate = null;
				try {
					receiptDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(tableModel.getValueAt(selectedRow, 4).toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				emissionDate.setDate(receiptDate);
				jCBPayment.setSelectedItem(tableModel.getValueAt(selectedRow, 5).toString());
			}
		});
		jPTableReceipts.setViewportView(jTReceipts);
		
		jPReceipt.add(jPTableReceipts, BorderLayout.CENTER);
		mainPanel.addTab("Receipts", iReceipt, jPReceipt, "Receipts list");
		mainPanel.setMnemonicAt(1, KeyEvent.VK_2);
		
		
		//CARS TAB
		jPCar = new JPanel(new BorderLayout());
		
		jPEditCar = new JPanel(new GridLayout(2,4, 10, 10));
		jPEditCar.setBorder(new EmptyBorder(5, 5, 20, 5));
		
		jLPlate = new JLabel("Plate: ");
		jPEditCar.add(jLPlate);
		jTFPlate = new JTextField();
		jPEditCar.add(jTFPlate);
		
		jLFuel = new JLabel("Fuel: ");
		jPEditCar.add(jLFuel);
		jTFuel = new JTextField();
		jPEditCar.add(jTFuel);
		
		jLSeats = new JLabel("Seats: ");
		jPEditCar.add(jLSeats);
		jTFSeats = new JTextField();
		jPEditCar.add(jTFSeats);
		
		jPEditCar.add(new JLabel());
		
		jPButtonsCar = new JPanel();
		jBAddCar = new JButton("Add");
		jBAddCar.setActionCommand("AddCar");
		jBAddCar.addActionListener(controller);
		jPButtonsCar.add(jBAddCar);
		
		jBUpdateCar = new JButton("Update");
		jBUpdateCar.setActionCommand("UpdateCar");
		jBUpdateCar.addActionListener(controller);
		jPButtonsCar.add(jBUpdateCar);
		
		jBDeleteCar = new JButton("Delete");
		jBDeleteCar.setActionCommand("DeleteCar");
		jBDeleteCar.addActionListener(controller);
		jPButtonsCar.add(jBDeleteCar);
		jPEditCar.add(jPButtonsCar);
		
		jPCar.add(jPEditCar, BorderLayout.PAGE_START);
		
		jPTableCars = new JScrollPane();
		jTCars = new JTable();
		jTCars.setRowSelectionAllowed(true);
		jTCars.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTCars.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt){
				int selectedRow = jTCars.getSelectedRow();
				TableModel tableModel = jTCars.getModel();
				
				jTFPlate.setText(tableModel.getValueAt(selectedRow, 0).toString());
				jTFuel.setText(tableModel.getValueAt(selectedRow, 1).toString());
				jTFSeats.setText(tableModel.getValueAt(selectedRow, 2).toString());
			}
		});
		jPTableCars.setViewportView(jTCars);
		
		jPCar.add(jPTableCars, BorderLayout.CENTER);
		mainPanel.addTab("Cars", iCar, jPCar, "Cars list");
		mainPanel.setMnemonicAt(2, KeyEvent.VK_3);
		
		
		//CALENDAR TAB
		jPBooking = new JPanel(new BorderLayout());
		
		jPDayBookings = new JPanel(new BorderLayout());
		jPDayBookings.setPreferredSize(new Dimension(450,700));
		
		jPDatePrint = new JPanel();
		DateFormat dateFormat = new SimpleDateFormat("dd MMMMM yyyy");
		Date date = new Date();
		jLDay = new JLabel(dateFormat.format(date) + "                  ");
		jLDay.setFont(new Font(null, Font.BOLD, 25));
		jPDatePrint.add(jLDay);
		
		jBPrint = new JButton("Print Schedule");
		jBPrint.setActionCommand("Print");
		jBPrint.addActionListener(controller);
		jPDatePrint.add(jBPrint);
		jPDayBookings.add(jPDatePrint, BorderLayout.PAGE_START);
		
		jPEditBooking = new JPanel(new GridLayout(6,2));
		jPEditBooking.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		jLOrigin = new JLabel("Origin: ");
		jPEditBooking.add(jLOrigin);
		jTFOrigin = new JTextField();
		jPEditBooking.add(jTFOrigin);
		
		jLDestination = new JLabel("Destination: ");
		jPEditBooking.add(jLDestination);
		jTFDestination = new JTextField();
		jPEditBooking.add(jTFDestination);
		
		jPDate = new JPanel(new GridLayout(0,2));
		jLDate = new JLabel("Date: ");
		jPDate.add(jLDate);
	    jDCBooking = new JDateChooser();
	    jDCBooking.setDateFormatString("yyyy-MM-dd");
	    jPDate.add(jDCBooking);
	    jPEditBooking.add(jPDate);
	    
	    jPDateTime = new JPanel(new GridLayout(0,3));
	    jPDateTime.add(new JLabel());
	    jLTime = new JLabel("Time: ");
	    jPDateTime.add(jLTime);
	    jTFTime = new JFormattedTextField(new SimpleDateFormat("HH:mm:ss"));
	    jTFTime.setText("00:00:00");
	    jPDateTime.add(jTFTime);
	    jPEditBooking.add(jPDateTime);
	    
	    jLDescription = new JLabel("Description: ");
	    jPEditBooking.add(jLDescription);
	    jTFDescription = new JTextField();
	    jTFDescription.setBorder(jTFOrigin.getBorder());
	    jPEditBooking.add(jTFDescription);
	    
	    jLBookingClient = new JLabel("Client: ");
	    jPEditBooking.add(jLBookingClient);
	    jCBookingClient = new JComboBox();
	    jPEditBooking.add(jCBookingClient);
	    
	    jPEditBooking.add(new JLabel());
	    
	    jPButtonsBooking = new JPanel();
		jBAddBooking = new JButton("Add");
		jBAddBooking.setActionCommand("AddBooking");
		jBAddBooking.addActionListener(controller);
		jPButtonsBooking.add(jBAddBooking);
		
		jBUpdateBooking = new JButton("Update");
		jBUpdateBooking.setActionCommand("UpdateBooking");
		jBUpdateBooking.addActionListener(controller);
		jPButtonsBooking.add(jBUpdateBooking);
		
		jBDeleteBooking = new JButton("Delete");
		jBDeleteBooking.setActionCommand("DeleteBooking");
		jBDeleteBooking.addActionListener(controller);
		jPButtonsBooking.add(jBDeleteBooking);
		jPEditBooking.add(jPButtonsBooking);
		
		jPDayBookings.add(jPEditBooking, BorderLayout.PAGE_END);
		
		jLBookings = new JList();
		jLBookings.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				Bookings booked = (Bookings) jLBookings.getSelectedValue();
				
				if(booked != null){
					jTFOrigin.setText(booked.getOrigin());
					jTFDestination.setText(booked.getDestination());
					try {
						jDCBooking.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(booked.getDate()));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					jTFTime.setText(booked.getTime());
					jTFDescription.setText(booked.getDescription());
					jCBookingClient.setSelectedItem(model.getClientName(booked.getClientID()));
				}
				
			}
			
		});
		jSPBookings = new JScrollPane(jLBookings);
		jPDayBookings.add(jSPBookings, BorderLayout.CENTER);
		
		jPBooking.add(jPDayBookings, BorderLayout.LINE_END);
		
		
		Calendar calendar = new Calendar();
		calendar.getCalendar().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt){
				int selectedRow = calendar.getCalendar().getSelectedRow();
				int selectedColumn = calendar.getCalendar().getSelectedColumn();
				TableModel tableModel = calendar.getCalendar().getModel();
				
				String day = String.format("%02d", tableModel.getValueAt(selectedRow, selectedColumn));
				String month = String.format("%02d", calendar.getMonth());
				int year = calendar.getYear();
				
				String date = year + "-" + month + "-" + day;
				model.loadBookings(jLBookings, date);
				
				jLDay.setText(day + " " + calendar.getlblMonth() + " " + year);
			}
		});
		
		jPBooking.add(calendar.pnlCalendar, BorderLayout.CENTER);
		mainPanel.addTab("Bookings", iBooking, jPBooking, "Bookings");
		mainPanel.setMnemonicAt(3, KeyEvent.VK_4);
		
		
		//ROUTES TAB
		jPRoutes = new JPanel(new BorderLayout());
		
		//map panel
		final CefSettings settings = new CefSettings();
        settings.windowless_rendering_enabled = false;
        cefApp = CefApp.getInstance(settings);
        client = cefApp.createClient();
	    browser = client.createBrowser("http://www.google.com/maps", false, false);
	    jPRoutes.add(browser.getUIComponent(), BorderLayout.CENTER);
	    
	    //route information panel 
	    jPRoutesInf = new JPanel(new GridLayout(13,2));
	    jPRoutesInf.setBorder(new EmptyBorder(5, 5, 5, 5));
	    
	    jLDistance = new JLabel("Distance: ");
	    jPRoutesInf.add(jLDistance);
	    
	    jTFDistance = new JTextField();
	    jTFDistance.setEditable(false);
	    jPRoutesInf.add(jTFDistance);
	    
	    jLNoPeople = new JLabel("Number of people: ");
	    jPRoutesInf.add(jLNoPeople);
	    
	    jPNoPeople = new JPanel(new GridLayout(1,3));
	    
	    jBMin = new JButton("-");
	    jBMin.setActionCommand("jBMin");
	    jBMin.addActionListener(controller);
	    jPNoPeople.add(jBMin);
	    
	    jTFNoPeople = new JTextField("0");
	    jTFNoPeople.setHorizontalAlignment(jTFNoPeople.CENTER);
	    jPNoPeople.add(jTFNoPeople);
	    
	    jBPlus = new JButton("+");
	    jBPlus.setActionCommand("jBPlus");
	    jBPlus.addActionListener(controller);
	    jPNoPeople.add(jBPlus);
	    
	    jPRoutesInf.add(jPNoPeople);
	    
	    jLTripType = new JLabel("Trip type: ");
	    jPRoutesInf.add(jLTripType);
	    jPTripType = new JPanel(new GridLayout(0,2));
	    jRBOneWay = new JRadioButton("One way");
	    jRBOneWay.setActionCommand("jRBOneWay");
	    jRBOneWay.addActionListener(controller);
	    jPTripType.add(jRBOneWay);
	    jRBTwoWays = new JRadioButton("Two way");
	    jRBTwoWays.setActionCommand("jRBTwoWays");
	    jRBTwoWays.addActionListener(controller);
	    jPTripType.add(jRBTwoWays);
	    jPRoutesInf.add(jPTripType);
	    
	    jLServiceType = new JLabel("Service type: ");
	    jPRoutesInf.add(jLServiceType);
	    
	    jPRoutesInf.add(new JLabel());
	    jRBRegular = new JRadioButton("Regular");
	    jRBRegular.setHorizontalAlignment(jRBRegular.CENTER);
	    jRBRegular.setActionCommand("jRBRegular");
	    jRBRegular.addActionListener(controller);
	    jRBRegular.setSelected(true);
	    jPRoutesInf.add(jRBRegular);
	    
	    jRBOccasional = new JRadioButton("Occasional");
	    jRBOccasional.setHorizontalAlignment(jRBOccasional.CENTER);
	    jRBOccasional.setActionCommand("jRBOccasional");
	    jRBOccasional.addActionListener(controller);
	    jPRoutesInf.add(jRBOccasional);
	    
	    jLGroup = new JLabel("Group: ");
	    jPRoutesInf.add(jLGroup);
	    jPGroup = new JPanel(new GridLayout(0,2));
	    jRBSchool = new JRadioButton("School");
	    jRBSchool.setActionCommand("jRBSchool");
	    jRBSchool.addActionListener(controller);
	    jRBSchool.setEnabled(false);
	    jPGroup.add(jRBSchool);
	    jRBIndividual = new JRadioButton("Individual");
	    jRBIndividual.setActionCommand("jRBIndividual");
	    jRBIndividual.addActionListener(controller);
	    jRBIndividual.setEnabled(false);
	    jPGroup.add(jRBIndividual);
	    jPRoutesInf.add(jPGroup);
	    
	    jLWeekDays = new JLabel("Weekdays: ");
	    jPRoutesInf.add(jLWeekDays);
	    jPRoutesInf.add(new JLabel());
	    
	    weekdays = new ArrayList<>();
	    jCBMonday = new JCheckBox("Monday");
	    weekdays.add(jCBMonday);
	    jPRoutesInf.add(jCBMonday);
	    
	    jCBTuesday = new JCheckBox("Tuesday");
	    weekdays.add(jCBTuesday);
	    jPRoutesInf.add(jCBTuesday);
	    
	    jCBWednesday = new JCheckBox("Wednesday");
	    jCBWednesday.setActionCommand("jCBWednesday");
	    weekdays.add(jCBWednesday);
	    jPRoutesInf.add(jCBWednesday);
	    
	    jCBThursday = new JCheckBox("Thursday");
	    jCBThursday.setActionCommand("jCBThursday");
	    weekdays.add(jCBThursday);
	    jPRoutesInf.add(jCBThursday);
	    
	    jCBFriday = new JCheckBox("Friday");
	    jCBFriday.setActionCommand("jCBFriday");
	    weekdays.add(jCBFriday);
	    jPRoutesInf.add(jCBFriday);
	    
	    jCBSaturday = new JCheckBox("Saturday");
	    jCBSaturday.setActionCommand("jCBSaturday");
	    weekdays.add(jCBSaturday);
	    jPRoutesInf.add(jCBSaturday);
	    
	    jCBSunday = new JCheckBox("Sunday");
	    jCBSunday.setActionCommand("jCBSunday");
	    weekdays.add(jCBSunday);
	    jPRoutesInf.add(jCBSunday);
	    jPRoutesInf.add(new JLabel());
	    
	    jLBudget = new JLabel("Budget: ");
	    jPRoutesInf.add(jLBudget);
	    
	    jTFBudget = new JTextField();
	    jTFBudget.setEnabled(false);
	    jPRoutesInf.add(jTFBudget);
	    
	    jPRoutesInf.add(new JLabel());
	    
	    jBBudget = new JButton("Calculate Budget");
	    jBBudget.setActionCommand("jBBudget");
	    jBBudget.addActionListener(controller);
	    jPRoutesInf.add(jBBudget);
	    
	    jPRoutesInf.setPreferredSize(new Dimension(300,700));
	    jPRoutes.add(jPRoutesInf, BorderLayout.LINE_END);
	    mainPanel.addTab("Routes", iRoutes, jPRoutes, "Routes");
		mainPanel.setMnemonicAt(3, KeyEvent.VK_5);
		
		frame.add(mainPanel);	
	}
	
	//login
	public JTextField getjTUser(){
		return jTUser;
	}
	
	public JPasswordField getjTUserPassword(){
		return jTUserPassword;
	}
	
	public void showMainFrame(){
		this.setVisible(false);
		frame.setVisible(true);
	}
	
	//clients
	public JTable getjTClients(){
		return jTClients;
	}
	public void cleanClients(){
		jTFName.setText("");
		jTFNif.setText("");
		jTFAddress.setText("");
		jTFEmail.setText("");
		jTFPostCode.setText("");
		jTFPhone.setText("");
		jCBState.setSelectedItem("");
	}
	public JTextField getjTFName(){
		return jTFName;
	}
	public JTextField getjTFNif(){
		return jTFNif;
	}
	public JTextField getjTFAddress(){
		return jTFAddress;
	}
	public JTextField getjTFEmail(){
		return jTFEmail;
	}
	public JTextField getjTFPostCode(){
		return jTFPostCode;
	}
	public JTextField getjTFPhone(){
		return jTFPhone;
	}
	public JComboBox getjCBState(){
		return jCBState;
	}
	
	//receipts 
	public JTable getjTReceipts(){
		return jTReceipts;
	}
	public void cleanReceipts(){
		jCBType.setSelectedItem("");
		jCBReceiptClient.setSelectedItem("");
		jTFValue.setText("");
		emissionDate.setCalendar(null);
		jCBPayment.setSelectedItem("");
	}
	public JComboBox getjCBType(){
		return jCBType;
	}
	public JComboBox getjCBReceiptClient(){
		return jCBReceiptClient;
	}
	public JTextField getjTFValue(){
		return jTFValue;
	}
	public JDateChooser getEmissionDate(){
		return emissionDate;
	}
	public JComboBox getjCBPayment(){
		return jCBPayment;
	}
	
	//cars
	public JTable getjTCars(){
		return jTCars;
	}
	public void cleanCars(){
		jTFPlate.setText("");
		jTFuel.setText("");
		jTFSeats.setText("");
	}
	public JTextField getjTFPlate(){
		return jTFPlate;
	}
	public JTextField getjTFuel(){
		return jTFuel;
	}
	public JTextField getjTFSeats(){
		return jTFSeats;
	}
	
	//calendar tab
	public JList getjLBookings(){
		return jLBookings;
	}
	public void setjLDay(Date date) {
		// TODO Auto-generated method stub
		jLDay.setText(new SimpleDateFormat("dd MMMMM yyyy").format(date));
	}
	public JTextField getjTFOrigin(){
		return jTFOrigin;
	}
	public JTextField getjTFDestination(){
		return jTFDestination;
	}
	public JDateChooser getjDCBooking(){
		return jDCBooking;
	}
	public JTextField getjTFTime(){
		return jTFTime;
	}
	public JTextField getjTADescription(){
		return jTFDescription;
	}
	public JComboBox getjCBookingClient(){
		return jCBookingClient;
	}
	public void cleanBookings(){
		jTFOrigin.setText("");
		jTFDestination.setText("");
		jDCBooking.setDate(null);
		jTFTime.setText("00:00:00");
		jTFDescription.setText("");
		jCBookingClient.setSelectedItem(null);
	}
	
	//route tab
	public CefApp getcefApp(){
		return cefApp;
	}
	
	public JTextField getjTFDistance(){
		return jTFDistance;
	}
	
	public JTextField getjTFNoPeople(){
		return jTFNoPeople;
	}
	
	public JRadioButton getjRBSchool(){
		return jRBSchool;
	}
	
	public JRadioButton getjRBIndividual(){
		return jRBIndividual;
	}
	
	public JRadioButton getjRBOneWay(){
		return jRBOneWay;
	}
	
	public JRadioButton getjRBTwoWays(){
		return jRBTwoWays;
	}
    
	public JRadioButton getjRBRegular(){
    	return jRBRegular;
    }
    
    public JRadioButton getjRBOccasional(){
    	return jRBOccasional;
    }
    
    public int countWeekdays(){
    	int numDays = 0;
    	for ( JCheckBox checkbox : weekdays ) {
    	    if( checkbox.isSelected() )
    	    {
    	    	numDays += 1;
    	    }
    	}
		return numDays;
    }
    
    public JCheckBox getjCBMonday(){
    	return jCBMonday;
    }
    
    public JCheckBox getjCBTuesday(){
    	return jCBTuesday;
    }
    
    public JCheckBox getjCBWednesday(){
    	return jCBWednesday;
    }
    
    public JCheckBox getjCBThursday(){
    	return jCBThursday;
    }
    
    public JCheckBox getjCBFriday(){
    	return jCBFriday;
    }
    
    public JCheckBox getjCBSaturday(){
    	return jCBSaturday;
    }
    
    public JCheckBox getjCBSunday(){
    	return jCBSunday;
    }
    
    public JTextField getjTFBudget(){
    	return jTFBudget;
    }
	
	//given two addresses, calculates the driving distance
	public Double getDistance() {
		String origin = null, destination = null;
		String currentURL = browser.getURL();
		String[] URLSplit = currentURL.split("/");
		for(int i=0;i<URLSplit.length;i++){
			//System.out.println(URLSplit[i].toString());
			if(URLSplit[i].toString().matches(".*dir") == true){
				origin = URLSplit[i+1];
				destination = URLSplit[i+2];
				//System.out.println("" + origin + "\n" + destination);
			}
		}
		
		String info = null;
		try {
		    URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+ origin +"&destinations="+ destination +"&key=AIzaSyBhlseEcuZ2Vl0kbJN6JsP1vCS79WJf3Zw");

		    BufferedReader in = new BufferedReader(
		        new InputStreamReader(url.openStream()));

		        String inputLine;
		        while ((inputLine = in.readLine()) != null) {
		        	info += inputLine; //saves the info
		        }
		        in.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		//String info = "null{   'destination_addresses' : [ 'Av. Lusíada, 1500-392 Lisboa, Portugal' ],   'origin_addresses' : [ 'R. Adelaide Cabete, 2660 Santo António dos Cavaleiros, Portugal' ],   'rows' : [      {         'elements' : [            {               'distance' : {                  'text' : '6.8 mi',                  'value' : 10928               },               'duration' : {                  'text' : '12 mins',                  'value' : 714               },               'status' : 'OK'            }         ]      }   ],   'status' : 'OK'}";
		String[] infoSplit = info.split("\"");
		double distance = 0;
		for(int i=0;i<infoSplit.length;i++){
			//System.out.println(distance[i].toString());
			if(infoSplit[i].toString().matches(".*mi") == true){
				distance = Double.parseDouble(infoSplit[i].toString().replaceAll(" [A-Za-z]+", ""));
				distance = distance*1.609344;
			}
		}
		
		return distance;
	}

	

}
