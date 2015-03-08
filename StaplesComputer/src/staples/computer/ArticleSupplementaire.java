/**
 * Create by Laurence de Villers
 * www.laurencedevillers.com
 * Staples number 1587820
 */
package staples.computer;

public class ArticleSupplementaire {
	
	private double prix;
	private int sku=0;
	String nom ;
	public ArticleSupplementaire(double prix){
		this.prix = prix;
		nom = "Article supplémentaire";
	}
	
	public void setSku(int sku){
		this.sku = sku;
	}
	
	
	public double getPrix(){
		return prix;
	}
	
	public int getSku(){
		return sku;
	}
	
	public String getString(){
		return nom+" "+String.format("%.2f",prix)+'$';
	}

}
