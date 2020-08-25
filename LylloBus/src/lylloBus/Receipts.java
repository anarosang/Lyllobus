package lylloBus;

public class Receipts {
	protected int receiptNumber;
	protected float value;
	protected String type, clientID, emissionDate, state, paymentState;
	
	public Receipts(int receiptNumber, String type, String clientID, float value, String emissionDate, String state, String paymentState){
		this.receiptNumber = receiptNumber;
		this.type = type;
		this.clientID = clientID;
		this.value = value;
		this.emissionDate = emissionDate;
		this.state = state;
		this.paymentState = paymentState;
	}
	
	public String getReceipt(){
		return "Number: " + receiptNumber + "\nType: " + type + "\nClient: " + clientID + "\nValue: " + value + "\nDate of emission: " + emissionDate + "\nState: " + state + "\nPayment status: " + paymentState;
	}
	
	public int getReceiptNumber(){
		return receiptNumber;
	}
	
	public String getType(){
		return type;
	}
	
	public String getClientID(){
		return clientID;
	}
	
	public Float getValue(){
		return value;
	}
	
	public String getEmissionDate(){
		return emissionDate;
	}
	
	public String getState(){
		return state;
	}
	
	public String getPaymentState(){
		return paymentState;
	}

}
