package controllers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import lylloBus.Bookings;
import models.Model;
import views.View;

public class Controller implements ActionListener, WindowListener {
	private View view;
	private Model model;
	private int numPeople;
	int numberOfDay = 0;
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public Controller(Model model) {
		// TODO Auto-generated constructor stub
		System.out.println("Instance of Controller created.");
		this.model = model;
	}
	
	public void addView(View view){
		System.out.println("View set in Controller.");
		this.view = view;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		model.connectDB();
		model.loadTable();
		model.loadData("CLIENTS", view.getjTClients());
		model.loadData("RECEIPTS", view.getjTReceipts());
		model.loadData("CARS", view.getjTCars());
		model.clientComboBox(view.getjCBReceiptClient());
		model.clientComboBox(view.getjCBookingClient());
		
		Date date = new Date();
		
		model.loadBookings(view.getjLBookings(), dateFormat.format(date));
		view.getjLBookings().setCellRenderer(new DefaultListCellRenderer(){
			@Override
		    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		        Bookings booking = (Bookings) value;
		        String origin = booking.getOrigin();
		        String destination = booking.getDestination();
		        String description = booking.getDescription();
		        String client = model.getClientName(booking.getClientID());
		        
				String time = booking.getTime();
		        
		        String labelText = "<html>" + time + "<br/>&emsp;&emsp;From: " + origin + "<br/>&emsp;&emsp;To: " + destination + "<br/>&emsp;&emsp;" + client + "<br/>&emsp;&emsp;" + description;
		        setText(labelText);
		        setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, new Color(54, 146, 238)));
		        return this;
		    }
		});
		
	}	

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		view.getcefApp().dispose();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand() == "Login"){
			boolean validLogin = validateLogin(view.getjTUser().getText(), view.getjTUserPassword().getText());
			if(validLogin){
				view.showMainFrame();
			}
			else {
				JOptionPane.showMessageDialog(new JFrame(),"The username or password isn't valid.","Login invalid",JOptionPane.ERROR_MESSAGE);
			}
		}
		//Client tab buttons
		if(e.getActionCommand() == "AddClient"){
			String values = "'" + view.getjTFName().getText() + "', '" + view.getjTFNif().getText() + "', '" + view.getjTFEmail().getText()+ "', '" + view.getjTFAddress().getText() + "', '" + view.getjTFPostCode().getText()+ "', '" + view.getjTFPhone().getText() + "', '" + view.getjCBState().getSelectedItem() + "'";
			model.tableInsert("CLIENTS", values);
			model.loadData("CLIENTS", view.getjTClients());
			checkBoxUpload();
			view.cleanClients();
		}
		else
		if(e.getActionCommand() == "UpdateClient"){
			try {
				int selectedRow = view.getjTClients().getSelectedRow();
				TableModel tableModel = view.getjTClients().getModel();
				String query = "UPDATE CLIENTS SET name = '" + view.getjTFName().getText() + "', nif = '" + view.getjTFNif().getText() + "', email = '" + view.getjTFEmail().getText()+ "', address = '" + view.getjTFAddress().getText() + "', postCode = '" + view.getjTFPostCode().getText()+ "', phone = '" + view.getjTFPhone().getText() + "', state = '" + view.getjCBState().getSelectedItem() + "' WHERE clientID=" + tableModel.getValueAt(selectedRow, 0) ;
				Statement stmt = model.getDBConection().createStatement();
				stmt.executeUpdate(query);
				stmt.close();
				model.loadData("CLIENTS", view.getjTClients());
				checkBoxUpload();
				view.cleanClients();
				
			} catch (SQLException es) {
				// TODO Auto-generated catch block
				es.printStackTrace();
			} 
		}
		else
		if(e.getActionCommand() == "DeleteClient"){
			model.tableDelete("CLIENTS", "clientID", view.getjTClients());
			view.cleanClients();
			model.loadData("CLIENTS", view.getjTClients());
			checkBoxUpload();
		}
		else
		//Receipt tab buttons
		if(e.getActionCommand() == "AddReceipt"){
			int clientId = model.getClientId(view.getjCBReceiptClient().getSelectedItem().toString());
			try {
				String values = "'" + view.getjCBType().getSelectedItem() + "', " + clientId + ", " + Double.parseDouble(view.getjTFValue().getText()) + ", '" + view.getEmissionDate().getDate() + "', '" + view.getjCBPayment().getSelectedItem() + "'";
				model.tableInsert("RECEIPTS", values);
			}catch(Exception ex){
				JOptionPane.showMessageDialog(new JFrame(),ex,"Inserting Error",JOptionPane.ERROR_MESSAGE);
			}
			model.loadData("RECEIPTS", view.getjTReceipts());
			view.cleanReceipts();
		}
		else
		if(e.getActionCommand() == "UpdateReceipt"){
			try {
				int clientId = model.getClientId(view.getjCBReceiptClient().getSelectedItem().toString());
				int selectedRow = view.getjTReceipts().getSelectedRow();
				TableModel tableModel = view.getjTReceipts().getModel();
				String query = "UPDATE RECEIPTS SET type = '" + view.getjCBType().getSelectedItem() + "', clientID = " + clientId + ", value = " + Double.parseDouble(view.getjTFValue().getText()) + ", emissionDate = '" + view.getEmissionDate().getDate() + "', paymentState = '" + view.getjCBPayment().getSelectedItem() + "' WHERE receiptNo =" + tableModel.getValueAt(selectedRow, 0) ;
				Statement stmt = model.getDBConection().createStatement();
				stmt.executeUpdate(query);
				stmt.close();
				model.loadData("RECEIPTS", view.getjTReceipts());
				view.cleanReceipts();
					
			} catch (SQLException es) {
				// TODO Auto-generated catch block
				es.printStackTrace();
			} 
		}
		else
		if(e.getActionCommand() == "DeleteReceipt"){
			model.tableDelete("RECEIPTS", "receiptNo", view.getjTReceipts());
			view.cleanReceipts();
			model.loadData("RECEIPTS", view.getjTReceipts());
		}
		else
		//Car tab buttons
		if(e.getActionCommand() == "AddCar"){
			try {
				String values = "'" + view.getjTFPlate().getText() + "', '" + view.getjTFuel().getText() + "', " + Integer.parseInt(view.getjTFSeats().getText());
				model.tableInsert("CARS", values);
			}catch(Exception ex){
				JOptionPane.showMessageDialog(new JFrame(),ex,"Inserting Error",JOptionPane.ERROR_MESSAGE);
			}
			model.loadData("CARS", view.getjTCars());
			view.cleanCars();
		}
		else
		if(e.getActionCommand() == "UpdateCar"){
			try {
				int selectedRow = view.getjTCars().getSelectedRow();
				TableModel tableModel = view.getjTCars().getModel();
				String query = "UPDATE CARS SET fuel = '" + view.getjTFuel().getText() + "', seats = " + Integer.parseInt(view.getjTFSeats().getText()) + " WHERE plate ='" + tableModel.getValueAt(selectedRow, 0) + "'" ;
				Statement stmt = model.getDBConection().createStatement();
				stmt.executeUpdate(query);
				stmt.close();
				model.loadData("CARS", view.getjTCars());
				view.cleanCars();
				
			} catch (SQLException es) {
				// TODO Auto-generated catch block
				es.printStackTrace();
			} 
		}
		else
		if(e.getActionCommand() == "DeleteCar"){
			model.tableDelete("CARS", "plate", view.getjTCars());
			view.cleanCars();
			model.loadData("CARS", view.getjTCars());
		}
		else
		//Calendar tab buttons
		if(e.getActionCommand() == "AddBooking"){
			int clientId = model.getClientId(view.getjCBookingClient().getSelectedItem().toString());
			try {
				String values = "'" + dateFormat.format(view.getjDCBooking().getDate()) + "', '" + view.getjTFTime().getText() + "', '" + view.getjTADescription().getText() + "', '" + view.getjTFOrigin().getText() + "', '" + view.getjTFDestination().getText() + "'," + clientId;
				model.tableInsert("BOOKINGS", values);
			}catch(Exception ex){
				JOptionPane.showMessageDialog(new JFrame(),ex,"Inserting Error",JOptionPane.ERROR_MESSAGE);
			}
			model.loadBookings(view.getjLBookings(), dateFormat.format(view.getjDCBooking().getDate()));
			view.cleanBookings();
		}
		else
		if(e.getActionCommand() == "UpdateBooking"){
			try {
				int clientId = model.getClientId(view.getjCBookingClient().getSelectedItem().toString());
				Bookings selected = (Bookings) view.getjLBookings().getSelectedValue();
				
				String query = "UPDATE BOOKINGS SET bookingDate = '" + dateFormat.format(view.getjDCBooking().getDate()) + "', bookingTime = '" + view.getjTFTime().getText() + "', description = '" + view.getjTADescription().getText() + "', origin = '" + view.getjTFOrigin().getText() + "', destination = '" + view.getjTFDestination().getText() + "', clientID = " + clientId + " WHERE bookingID =" + selected.getID() ;
				Statement stmt = model.getDBConection().createStatement();
				stmt.executeUpdate(query);
				stmt.close();
				
			} catch (SQLException es) {
				// TODO Auto-generated catch block
				es.printStackTrace();
			}
			model.loadBookings(view.getjLBookings(), dateFormat.format(view.getjDCBooking().getDate()));
			view.setjLDay(view.getjDCBooking().getDate());
			view.cleanBookings();
		}
		else
		if(e.getActionCommand() == "DeleteBooking"){
			try {
				Bookings selected = (Bookings) view.getjLBookings().getSelectedValue();
				String date = selected.getDate();
				String query = "DELETE FROM BOOKINGS WHERE bookingID=" + selected.getID();
				Statement stmt = model.getDBConection().createStatement();
				stmt.executeUpdate(query);
				stmt.close();
				
				view.cleanBookings();
				model.loadBookings(view.getjLBookings(), date);
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else
		if(e.getActionCommand() == "Print"){
			PrinterJob pj = PrinterJob.getPrinterJob();
			pj.setJobName(" Print Component ");
			
			pj.setPrintable (new Printable() {
				public int print(Graphics pg, PageFormat pf, int pageNum) {
					if (pageNum > 0) return Printable.NO_SUCH_PAGE;
					Graphics2D g2 = (Graphics2D) pg;
					g2.translate(pf.getImageableX(), pf.getImageableY());
					view.getjLBookings().paint(g2);
					return Printable.PAGE_EXISTS;
			    }
			  
			});
			
			if (pj.printDialog() == false) return;
			
			try {
				pj.print();
			} catch (PrinterException ex) {
			    // handle exception
			}
		}
		else
		//Routes tab buttons
		if(e.getActionCommand() == "jBMin"){
			numPeople -= 1; 
			view.getjTFNoPeople().setText("" + numPeople);
		}
		else
		if(e.getActionCommand() == "jBPlus"){
			numPeople += 1; 
			view.getjTFNoPeople().setText("" + numPeople);
		}
		else
		if(e.getActionCommand() == "jRBSchool"){
			if(view.getjRBSchool().isSelected()){
				view.getjRBIndividual().setSelected(false);
			}
		}
		else
		if(e.getActionCommand() == "jRBIndividual"){
			if(view.getjRBIndividual().isSelected()){
				view.getjRBSchool().setSelected(false);
			}
		}
		else
		if(e.getActionCommand() == "jRBOneWay"){
			if(view.getjRBOneWay().isSelected()){
				view.getjRBTwoWays().setSelected(false);
			}
		}
		else
		if(e.getActionCommand() == "jRBTwoWays"){
			if(view.getjRBTwoWays().isSelected()){
				view.getjRBOneWay().setSelected(false);
			}
		}
		else
		if(e.getActionCommand() == "jRBRegular"){
			if(view.getjRBRegular().isSelected()){
				view.getjRBOccasional().setSelected(false);
				view.getjCBMonday().setEnabled(true);
				view.getjCBTuesday().setEnabled(true);
				view.getjCBWednesday().setEnabled(true);
				view.getjCBThursday().setEnabled(true);
				view.getjCBFriday().setEnabled(true);
				view.getjCBSaturday().setEnabled(true);
				view.getjCBSunday().setEnabled(true);
				view.getjRBIndividual().setEnabled(false);
				view.getjRBSchool().setEnabled(false);
			}
		}
		else
		if(e.getActionCommand() == "jRBOccasional"){
			if(view.getjRBOccasional().isSelected()){
				view.getjRBRegular().setSelected(false);
				view.getjCBMonday().setEnabled(false);
				view.getjCBTuesday().setEnabled(false);
				view.getjCBWednesday().setEnabled(false);
				view.getjCBThursday().setEnabled(false);
				view.getjCBFriday().setEnabled(false);
				view.getjCBSaturday().setEnabled(false);
				view.getjCBSunday().setEnabled(false);
				view.getjRBIndividual().setEnabled(true);
				view.getjRBSchool().setEnabled(true);
			}
		}
		else
		if(e.getActionCommand() == "jBBudget"){
			double distance = view.getDistance();
			
			DecimalFormat df = new DecimalFormat("#.#");
			view.getjTFDistance().setText(df.format(distance) + " km");
			double budget = 0;
			
			int noPeople = Integer.parseInt(view.getjTFNoPeople().getText());
			int noDays = view.countWeekdays();
			
			if(view.getjRBRegular().isSelected()){
				if(distance <= 8){
					budget += 7;
				} else{
					budget += 11;
				}
				
				if(view.getjRBTwoWays().isSelected()){
					budget = budget * 2;
				}
				
				budget = budget * noDays * 4;
			}
			else if (view.getjRBOccasional().isSelected()){
				if(view.getjRBSchool().isSelected()){
					budget = 15;
				}else{
					if(distance <= 12){
						budget += 30;
					}else {
						budget += 45;
					}
					
					if(view.getjRBTwoWays().isSelected()){
						budget = budget * 2;
					}
				}
			}
			
			budget = budget * noPeople;
			view.getjTFBudget().setText(budget + "€");
		}
	}
	
	public void checkBoxUpload(){
		view.getjCBReceiptClient().removeAllItems();
		view.getjCBookingClient().removeAllItems();
		model.clientComboBox(view.getjCBReceiptClient());
		model.clientComboBox(view.getjCBookingClient());
	}
	
	public boolean validateLogin(String username, String password){
		if(username.equals("lyllobus") && password.equals("lyllo2020bus")){
			return true;
		}
		
		return false;
	}

}