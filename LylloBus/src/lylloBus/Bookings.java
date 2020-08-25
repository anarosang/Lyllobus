package lylloBus;

public class Bookings {
	protected int bookingID, clientID;
	protected String date, time, description, origin, destination;
	
	public Bookings(int bookingID, String date, String time, String description, String origin, String destination, int clientID){
		this.bookingID = bookingID;
		this.date = date;
		this.time = time;
		this.description = description;
		this.origin = origin;
		this.destination = destination;
		this.clientID = clientID;
	}
	
	public String getBooking(){
		return "ID: " + bookingID + "\nDate: " + date + "\nDescription: " + description + "\nPick up address: " + origin + "\nDestination address: " + destination + "\nClient: " + clientID;
	}
	
	public int getID(){
		return bookingID;
	}
	
	public String getDate(){
		return date;
	}
	
	public String getTime(){
		return time;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getOrigin(){
		return origin;
	}
	
	public String getDestination(){
		return destination;
	}
	
	public int getClientID(){
		return clientID;
	}

}
