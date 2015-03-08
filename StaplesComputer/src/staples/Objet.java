package staples;

import java.util.Vector;

import staples.computer.ArticleSupplementaire;
import staples.computer.Option;
import staples.computer.utility.Taxes;

public class Objet {
	
	protected double prix = 0;
	protected int sku = 0;
	protected Vector<Option> option = new Vector<Option>();
	protected Vector<ArticleSupplementaire> articleSup = new Vector<ArticleSupplementaire>();
	protected int categorie = 0;
	
	public void setPrix(double prix){
		this.prix = prix;
	}
	
	public double getPrix(){
		return prix;
	}
	public void setSku(int sku){
		this.sku = sku;
	}
	
	public void setCategorie(int categorie){
		this.categorie = categorie;
	}
	
	public int getSku(){
		return sku;
	}
	
	public void addVector(Option op)
	{ 
		this.option.add(op);
	}
	public int getCategorie(){
		return categorie;
	}
	
	
	public void addArticleSup(ArticleSupplementaire article){
		this.articleSup.add(article);
	}
	
	public void reset(){
		prix= 0;
		sku=0;
		categorie= 0;
		option.clear();
		articleSup.clear();
	}
	
	
	public Vector<ArticleSupplementaire> getVecteurArticleSup(){
		return articleSup;
	}
	
	public double calculPrixTotal(){
		double prixTotal = prix ;
		for(int i=0; i<option.size();i++){
			prixTotal += option.get(i).getPrix();
		}
		for(int i=0; i<articleSup.size(); i++){
			prixTotal += articleSup.get(i).getPrix();
		}
		prixTotal = Math.round(prixTotal*100.0)/100.00;
		return prixTotal;
	}		
	
	
	public double calculPrixTotalTaxe(){
		double prixTotalTaxe =Taxes.getInstance().calculPriceWithTaxes(calculPrixTotal());
		return prixTotalTaxe;
	}
	
	
	public String getString(){
		return "";
	}
	
	public void resetVect(){
		option.clear();
	}
	
	public Vector<Option> getOptions(){
		return this.option;
	}

}
