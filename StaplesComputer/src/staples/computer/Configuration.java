/**
 * Create by Laurence de Villers
 * www.laurencedevillers.com
 * Staples number 1587820
 */
package staples.computer;

public class Configuration extends Option{

	private String nom = "Configuration";
	private int niveau; 
	public Configuration(String nom, double prix, int niveau, int scu) {
		super(nom, prix,  scu);
		this.niveau = niveau;
		// TODO Auto-generated constructor stub
	}
	
	public String getNom(){
		return nom;
	}
	
	public int getNiveau(){
		return niveau;
	}
}
