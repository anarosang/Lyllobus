package lylloBus;

public class Cars {
	protected int seats;
	protected String plate, fuel;
	
	public Cars(String plate, String fuel, int seats){
		this.plate = plate;
		this.fuel = fuel;
		this.seats = seats;
	}
	
	public String getCar(){
		return "Plate: " + plate + "\nFuel source: " + fuel + "\nSeats: " + seats;
	}
	
	public String getPlate(){
		return plate;
	}
	
	public String getFuel(){
		return fuel;
	}
	
	public int getSeats(){
		return seats;
	}

}
