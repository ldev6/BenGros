package staples.bureau;

import staples.Objet;

public class Meuble extends Objet{

	private static Meuble instance;
	private String strMeuble;
	private Meuble(){
		super();
		strMeuble="Meuble";
	}
	
	public static Meuble getInstance(){
		if(instance ==null){
			instance = new Meuble();
		}
		return instance;
	}
	
	public String getString(){
		return strMeuble+" "+this.prix+"$";
	}
	
	public void removePlanReplace(){
		for(int i=0; i<this.option.size(); i++) {
			if(option.get(i) instanceof PlanReplaceChairs){
				option.remove(i);
			}
		}
	}
	
	public void removePlanReparation(){
		for(int i=0; i<this.option.size(); i++) {
			if(option.get(i) instanceof PlanRepairBureauVerre|| option.get(i) instanceof PlanRepairMeuble){
				option.remove(i);
			}
		}
	}
	
}
