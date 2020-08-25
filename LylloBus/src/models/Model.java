package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import lylloBus.Bookings;
import lylloBus.Cars;
import lylloBus.Clients;
import lylloBus.Receipts;
import net.proteanit.sql.DbUtils;

public class Model {
	protected Connection DBConnection = null;
	protected Statement stmt = null;
	protected ArrayList<Bookings> bookings = new ArrayList<Bookings>();
	
	public void connectDB(){
		try {
			Class.forName("org.sqlite.JDBC");
			DBConnection = DriverManager.getConnection("jdbc:sqlite:lyllobus.db");
		} catch (Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		System.out.println("Opened database successfully");
	}
	
	public Connection getDBConection(){
		return DBConnection;
	}
	
	public void loadTable(){
		try {
			String tables = "";
			stmt = DBConnection.createStatement();
			
			String SQL = "SELECT name FROM sqlite_master WHERE type='table';";
			ResultSet rs    = stmt.executeQuery(SQL);
			
			while (rs.next()){
				tables += rs.getString("name") + "\n";
			}
			
			if(tables.contains("CLIENTS") == false){
				System.out.println("CLIENTS doesn't existe");
				String clientSQL = "CREATE TABLE CLIENTS (clientID INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR NOT NULL, nif VARCHAR NOT NULL, email VARCHAR, address VARCHAR NOT NULL, postCode VARCHAR NOT NULL, phone VARCHAR NOT NULL, state VARCHAR)";
				stmt.executeUpdate(clientSQL);
			}
			
			if(tables.contains("RECEIPTS") == false){
				System.out.println("RECEIPTS doesn't existe");
				String receiptSQL = "CREATE TABLE RECEIPTS (receiptNo INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, type VARCHAR NOT NULL, FOREIGN KEY(clientID) REFERENCES CLIENTS(clientID) NOT NULL, value DOUBLE NOT NULL, emissionDate DATE NOT NULL, paymentState VARCHAR NOT NULL);";
				stmt.executeUpdate(receiptSQL);
			}
			
			if(tables.contains("CARS") == false){
				System.out.println("CARS doesn't existe");
				String carSQL = "CREATE TABLE CARS (plate VARCHAR PRIMARY KEY UNIQUE, fuel VARCHAR, seats INTEGER)";
				stmt.executeUpdate(carSQL);
			}
			
			if(tables.contains("BOOKINGS") == false){
				System.out.println("BOOKINGS doesn't existe");
				String bookingSQL = "CREATE TABLE BOOKINGS (bookingID INTEGER PRIMARY KEY AUTOINCREMENT, bookingDate DATE NOT NULL, bookingTime TIME NOT NULL, description VARCHAR, origin VARCHAR NOT NULL, destination VARCHAR NOT NULL, FOREIGN KEY(clientID) REFERENCES clients(clientID) NOT NULL";
				stmt.executeUpdate(bookingSQL);
			}
			
			stmt.close();			
		} catch (Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		System.out.println("Tables created successfully");
	}
	
	public void loadData(String tableName, JTable table){
		try {
			String query = "SELECT * FROM "+ tableName ;
			PreparedStatement pst;
			pst = DBConnection.prepareStatement(query);
			ResultSet rs    = pst.executeQuery();
			
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadBookings(JList list, String date){
		try {
			String query = "SELECT * FROM BOOKINGS WHERE bookingDate = '" + date + "' ORDER BY bookingTime";
			PreparedStatement pst;
			pst = DBConnection.prepareStatement(query);
			ResultSet rs    = pst.executeQuery();
			
			DefaultListModel listModel = new DefaultListModel<>();
			
			while(rs.next()){
				listModel.addElement(new Bookings(rs.getInt("bookingID"), rs.getString("bookingDate"), rs.getString("bookingTime"), rs.getString("description"), rs.getString("origin"), rs.getString("destination"), rs.getInt("clientID")));
			}
			
			list.setModel(listModel);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getField(String tableName){
		String fields = "";
		if(tableName == "CLIENTS"){
			fields = "name, nif, email, address, postCode, phone, state";
		}
		else if(tableName == "RECEIPTS"){
			fields = "type, clientID, value, emissionDate, paymentState";
		}
		else if(tableName == "CARS"){
			fields = "plate, fuel, seats";
		}
		else if(tableName == "BOOKINGS"){
			fields = "bookingDate, bookingTime, description, origin, destination, clientID";
		}
		return fields;
	}
	
	public void tableInsert(String tableName, String values){
		String fields = getField(tableName);
		
		try {
			String query = "INSERT INTO " + tableName + " (" + fields + ") VALUES (" + values + ")";
			Statement stmt = DBConnection.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public void tableDelete(String tableName, String tableID, JTable table){
		try {
			int selectedRow = table.getSelectedRow();
			TableModel tableModel = table.getModel();
			String query;
			if(tableName == "CARS"){
				query = "DELETE FROM "+ tableName +" WHERE "+ tableID +" = '" + tableModel.getValueAt(selectedRow, 0) + "'";
			} else {
				query = "DELETE FROM "+ tableName +" WHERE "+ tableID +"=" + tableModel.getValueAt(selectedRow, 0) ;
			}
			
			Statement stmt = DBConnection.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
			
		} catch (SQLException es) {
			// TODO Auto-generated catch block
			es.printStackTrace();
		} 
	}
	
	public void clientComboBox(JComboBox comboBox){
		try {
			String query = "SELECT * FROM CLIENTS";
			
			PreparedStatement stmt = DBConnection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				comboBox.addItem(rs.getString("name"));
			}
			stmt.close();
			
		} catch (SQLException es) {
			// TODO Auto-generated catch block
			es.printStackTrace();
		}
	}
	
	public int getClientId(String clientName){
		int clientId = 0;
		try {
			String query = "SELECT clientID, name FROM CLIENTS WHERE name ='" + clientName + "'" ;
			PreparedStatement stmt = DBConnection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			
			clientId = rs.getInt("clientID");
			stmt.close();
			
		} catch (SQLException es) {
			// TODO Auto-generated catch block
			es.printStackTrace();
		}
		return clientId;
	}
	
	public String getClientName(int clientId){
		String clientName = "";
		try {
			String query = "SELECT clientID, name FROM CLIENTS WHERE clientID ='" + clientId + "'" ;
			PreparedStatement stmt = DBConnection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				clientName = rs.getString("name");
			}
			stmt.close();
			
		} catch (SQLException es) {
			// TODO Auto-generated catch block
			es.printStackTrace();
		}
		return clientName;
	}

}
