package lylloBus;

public class Clients {
	protected int clientID;
	protected String name, NIF, state, email, address, postCode, phone;
	
	public Clients(int clientID, String name, String NIF, String state, String email, String address, String postCode, String phone){
		this.clientID = clientID;
		this.name = name;
		this.NIF = NIF;
		this.state = state;
		this.email = email;
		this.address = address;
		this.postCode = postCode;
		this.phone = phone;
	}
	
	public String getClient(){
		return "ID: " + clientID + "\nName: " + name + "\nEmail: " + email + "\nPhone: " + phone + "\nAddress: " + address + "\nPost Code: " + postCode + "\nNIF: " + NIF + "\nState: " + state;
	}
	
	public int getID(){
		return clientID;
	}
	
	public String getName(){
		return name;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public String getAddress(){
		return address;
	}
	
	public String getPostCode(){
		return postCode;
	}
	
	public String getNIF(){
		return NIF;
	}
	
	public String getState(){
		return state;
	}
	

}
