/**
 * Create by Laurence de Villers
 * www.laurencedevillers.com
 * Staples number 1587820
 */
package staples.computer;

import java.util.Vector;

import staples.Objet;
import staples.computer.utility.Taxes;

import android.util.Log;

public class Ordinateur extends Objet{
	
	private static Ordinateur instance= null;
	private double rabais = 0;
	private double TAUXLIQUID = 0.05;
	
	private Ordinateur(){
		super();
	}
	public static Ordinateur getInstance(){
		if(instance==null){
			instance = new Ordinateur();
		}
		return instance;
	}

	
	
	
	public void reset(){
		prix= 0;
		rabais= 0;
		sku=0;
		categorie= 0;
		option.clear();
		articleSup.clear();
	}
	
		
	public double calculPrixTotal(){
		
		double prixTotal = prix - rabais;
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
	public double getRabais(){
		return this.rabais;
	}
	public void addRabais(){
		this.rabais = this.prix*TAUXLIQUID;
		this.rabais = Math.round(this.rabais*100.00)/100.00;
	}
	
	public void removeRabais(){
		this.rabais=0;
	}
	
	public String getString(){
		return "Ordinateur "+this.prix+"$";
	}
	
	public double getTauxLiquid(){
		return TAUXLIQUID;
	}
	public void resetVect(){
		option.clear();
	}

	public void removeAccidentel(){
		for(int i=0; i< option.size(); i++){
			if(option.get(i) instanceof Accidentel){
				option.remove(i);
			}
		}
	}
	
	public void removeRecup(){
		for(int i=0; i< option.size(); i++){
			if(option.get(i) instanceof PlanRecup){
				option.remove(i);
			}
		}
	}
	
	public void removeConfig(){
		for(int i=0; i< option.size(); i++){
			if(option.get(i) instanceof Configuration){
				option.remove(i);
			}
		}
	}
	
	
}
