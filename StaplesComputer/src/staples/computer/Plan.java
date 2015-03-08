package staples.computer;

public class Plan extends Option{

	private String nom = "Plan de réparation";
	private double rangeMin;
	private double rangeMax;
	private int annee;
	
	public Plan(String nom, double prix, int annee, double rangeMin, double rangeMax, int scu) {
		super(nom, prix, scu);
		
		this.annee = annee;
		this.rangeMin = rangeMin;
		this.rangeMax = rangeMax;
	}
	
	public String getNom(){
		return nom;
	}
	
	public int getAnnee(){
		return annee;
	}
	public boolean checkInRange( double prix ) {
		if( prix >= rangeMin && prix <= rangeMax )
			return true;
		else
			return false;
	}
}
