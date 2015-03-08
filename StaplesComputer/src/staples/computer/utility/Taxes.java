package staples.computer.utility;

public class Taxes {
	
	final double TPS = 0.05;
	final double TVQ = 0.095;
	
	private static Taxes instance;
	
	private Taxes(){
		;
	}
	public static Taxes getInstance(){
		if(instance==null){
			instance = new Taxes();
		}
		return instance;
	}
	
	public double getTPS(){
		return TPS;
	}
	
	public double getTVQ(){
		return TVQ;
	}
	
	public double calculPriceWithTaxes(double price){
		
		price+= price * TPS;
		price+= price *TVQ;
		price = Math.round(price*100.0)/100.00;
		
		return price;
	}

}
