/**
 * Create by Laurence de Villers
 * www.laurencedevillers.com
 * Staples number 1587820
 */
package staples.computer;

public class Option {
	
	private String nom;
	private double prix;
	private int sku;
	
	public Option( String nom, double prix, int sku){
		this.nom = nom;
		this.prix = prix;
		this.sku = sku;
		
	}
	
	public String getNom(){
		return nom;
	}
	
	public double getPrix(){
		return prix;
	}
	
	public int getSku(){
		return sku;
	}
	
	public String getString(){
		return nom+" "+String.format("%.2f",prix)+"$";
	}
}
