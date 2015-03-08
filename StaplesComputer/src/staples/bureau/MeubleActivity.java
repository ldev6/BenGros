package staples.bureau;

import java.util.Vector;

import staples.Plan;
import staples.skuActivity;
import staples.computer.Accidentel;
import staples.computer.ArticleSupplementaire;
import staples.computer.Configuration;
import staples.computer.Option;
import staples.computer.PlanOrdiBureau;
import staples.computer.PlanPortatifs;
import staples.computer.PlanRecup;
import staples.computer.R;
import staples.computer.StaplesComputerActivity;
import staples.computer.utility.CostumDialog;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;

public class MeubleActivity extends Activity{

	private int MEUBLE = 1;
	private int BUREAUVERRE = 2;
	private int annee;
	private boolean isRemplace = false;
	private Vector<Option> vectOption = new Vector<Option>();
	private Vector<Option> vectOptionWithPrice = new Vector<Option>();
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.bureau);
        
        TableRow tableFauteuil = (TableRow) findViewById(R.id.tableRowFauteuil);
        tableFauteuil.setVisibility(tableFauteuil.GONE);
        init();
    }
    
    
    public void actionPrixMeuble(View v){
    	final CostumDialog dialog = new CostumDialog(this);
    	dialog.setTitle("Prix Meuble");
    	dialog.setOkListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		
				EditText ordiTextPrix = (EditText) (dialog.getDialog()).findViewById(R.id.editTextPrixAjout);
				String ordiPrix = ordiTextPrix.getText().toString();
				
				EditText ordiTextSku = (EditText) (dialog.getDialog()).findViewById(R.id.editTextSkuAjout);
				String ordiSku = ordiTextSku.getText().toString();
				
				Meuble meuble = Meuble.getInstance();
				//resetInformation();
				if(!ordiPrix.equals("")){
			    	double prix =  Double.parseDouble(ordiPrix);
			    	EditText prixEditText =(EditText) findViewById(R.id.editPrixMeuble);
			    	prixEditText.setText(""+prix);
			    	meuble.setPrix(prix);
			    	setTotalPrix();
		    	}
				
				if(!ordiSku.equals("")){
		    		int sku =  Integer.parseInt(ordiSku);
		    		meuble.setSku(sku);
		    	}
				dialog.close();
			}
    	
    	});
    	dialog.show();
    }
    

    public void radioClick(View v){
    
    	Meuble meuble = Meuble.getInstance();
    	meuble.resetVect();
    	RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
    	int typeMeuble = 0;
    	 
    	int checkedRadioButton = radioGroup.getCheckedRadioButtonId();
    	TableRow tableRow = (TableRow) findViewById(R.id.tableRowFauteuil);
    	
    	switch (checkedRadioButton) {
    	
    	  case R.id.radioButtonMeuble : typeMeuble = MEUBLE ;
    	                   	              break;
    	  case R.id.radioButtonBVerre : typeMeuble = BUREAUVERRE;
    	  tableRow.setVisibility(View.GONE);
    			                          break;
    	}
    	EditText prixEditText =(EditText) findViewById(R.id.editPrixMeuble);
    	String  prixText = prixEditText.getText().toString(); 
    	
    	resetSomeInformation();
    	meuble.resetVect();
    	if(!prixText.equals("")){
	    	double prix =  Double.parseDouble(prixText);
	    	if(prix<=149.99 && typeMeuble==1){
	    		  tableRow.setVisibility(View.VISIBLE);
	    	}
	    	meuble.setCategorie(typeMeuble);
	    	chooseOptionWithPrice(prix);
	    	setTotalPrix();
    	}
    	
    }
    
    public void radioAnneeClick(View v){
        
    	RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroupAnnee);
        annee = 0;
    	int checkedRadioButton = radioGroup.getCheckedRadioButtonId();
    	switch (checkedRadioButton) {
    	
    	  case R.id.radioBAnnee1 : annee = 1 ;
    	                   	              break;
    	  case R.id.radioBAnnee2 : annee = 2;
    			                          break;
    	}
    	Meuble meuble = Meuble.getInstance();
    	meuble.resetVect();
    	addPlan(annee);
    	
    	setTotalPrix();	
    }
    
    
    public boolean addPlan(int annee){
    	Meuble meuble = Meuble.getInstance();
    	if( vectOptionWithPrice.size()!=0){
		     for(int i=0; i<vectOptionWithPrice.size(); i++){
		    	 
		    	 if (meuble.getCategorie()==MEUBLE && isRemplace==false &&vectOptionWithPrice.get(i)instanceof PlanRepairMeuble &&  ((Plan) vectOptionWithPrice.get(i)).getAnnee() == annee  ){
		    		 meuble.addVector(vectOptionWithPrice.get(i));
		    		 EditText prixPlan = (EditText) findViewById(R.id.editPrixPlan);
			    	 prixPlan.setText(String.format("%.2f",vectOptionWithPrice.get(i).getPrix())+"$");
			    	
		    		 return true;
		    	 }
		    	 else  if (meuble.getCategorie()==MEUBLE && isRemplace==true &&vectOptionWithPrice.get(i)instanceof PlanReplaceChairs &&  ((Plan) vectOptionWithPrice.get(i)).getAnnee() == annee  ){
		    		 meuble.addVector(vectOptionWithPrice.get(i));
		    		 EditText prixPlan = (EditText) findViewById(R.id.editPrixPlan);
			    	 prixPlan.setText(String.format("%.2f",vectOptionWithPrice.get(i).getPrix())+"$");
			    	
		    		 return true;
		    	 }
		    	 else if(meuble.getCategorie()==BUREAUVERRE && vectOptionWithPrice.get(i)instanceof PlanRepairBureauVerre &&  ((Plan) vectOptionWithPrice.get(i)).getAnnee() == annee ){
		    		 meuble.addVector(vectOptionWithPrice.get(i));
		    		 EditText prixPlan = (EditText) findViewById(R.id.editPrixPlan);
			    	 prixPlan.setText(String.format("%.2f",vectOptionWithPrice.get(i).getPrix())+"$");
		    		 return true;
		    	 }
		     }
    	}
		return false;
    }

    public void chooseOptionWithPrice(double prix){
    	vectOptionWithPrice.clear();
    	   for(int i=0; i<vectOption.size(); i++){
  	    	 if ( vectOption.get(i)instanceof Plan ){
  	    		 Plan plan =(Plan) vectOption.get(i);
  	    		 if(plan.checkInRange(prix)){
  	    			vectOptionWithPrice.add(plan);
  	    		 }
  	    	 }
  	     }
    }
    
    public void actionAddItem(View v){
		
		final CostumDialog dialog = new CostumDialog(this);
		dialog.setTitle("Ajouter un item");
		dialog.setOkListener(new OnClickListener(){

			public void onClick(View arg0) {
				
				EditText articleTextPrix = (EditText) (dialog.getDialog()).findViewById(R.id.editTextPrixAjout);
				String articlePrix = articleTextPrix.getText().toString();
				
				EditText articleTextSku = (EditText) (dialog.getDialog()).findViewById(R.id.editTextSkuAjout);
				String articleSku = articleTextSku.getText().toString();
				if(!articlePrix.equals("")){
			    	double prix =  Double.parseDouble(articlePrix);
			    	ArticleSupplementaire article = new ArticleSupplementaire(prix);
			    	
			    	if(!articleSku.equals("")){
			    		int sku =  Integer.parseInt(articleSku);
			    		article.setSku(sku);
			    	}
			    	Meuble.getInstance().addArticleSup(article);
			    	setTotalPrix();
		    	}
				dialog.close();
			}
		});
	    dialog.show();
	}

   public void setTotalPrix(){
	 EditText prixTotal = (EditText) findViewById(R.id.prixTotal);
	 prixTotal.setText(String.format("%.2f",Meuble.getInstance().calculPrixTotal())+"$");
	 
	 EditText prixTotalTaxe = (EditText) findViewById(R.id.prixTotalTaxe);
	 prixTotalTaxe.setText(String.format("%.2f",Meuble.getInstance().calculPrixTotalTaxe())+"$");
   }
   
   public void actionSku(View v){
 	  Intent addEventActivity = new Intent(MeubleActivity.this, skuActivity.class);
 	  addEventActivity.putExtra("FROM_MEUBLE", "FROM_MEUBLE");
      startActivity(addEventActivity);
   }
   
   
   public void checkReplace(View v){
   	CheckBox chaise = (CheckBox) findViewById(R.id.checkChaise);
    RadioButton radioButton = (RadioButton) findViewById(R.id.radioBAnnee2);
    TextView textPlan = (TextView) findViewById(R.id.plan);
 	EditText prixPlan = (EditText) findViewById(R.id.editPrixPlan);
	prixPlan.setText("");
	Meuble meuble = Meuble.getInstance();
    
   	if(chaise.isChecked()){
   		isRemplace = true;
   		meuble.removePlanReparation();
   		textPlan.setText(R.string.Remplacement);
   		radioButton.setVisibility(radioButton.GONE);
   		
   	}
   	else{
   		isRemplace = false;
   		meuble.removePlanReplace();
   		textPlan.setText(R.string.Plan);
   		radioButton.setVisibility(radioButton.VISIBLE);
   	    vectOptionWithPrice.clear();
   		chooseOptionWithPrice(meuble.getPrix());	
   	}	
   	setTotalPrix();
   }
   
   public void resetSomeInformation(){
   	vectOptionWithPrice.clear();
   	EditText prixPlan = (EditText) findViewById(R.id.editPrixPlan);
	prixPlan.setText("");

   	EditText prixTotal = (EditText) findViewById(R.id.prixTotal);
   	prixTotal.setText("");
   	 
   	EditText prixTotalTaxe = (EditText) findViewById(R.id.prixTotalTaxe);
   	prixTotalTaxe.setText("");
   	 
   	RadioGroup radioGroupAnnee = (RadioGroup) findViewById(R.id.radiogroupAnnee);
   	radioGroupAnnee.clearCheck();
   	 
   	 CheckBox checkChaise = (CheckBox) findViewById(R.id.checkChaise);
   	 checkChaise.setChecked(false);
   	 this.isRemplace=false;
   	 
     TableRow tableFauteuil = (TableRow) findViewById(R.id.tableRowFauteuil);
     tableFauteuil.setVisibility(tableFauteuil.GONE);
   	
   }
   
   public void resetInfo(View v){
   	resetInformation();
   }
   
   public void resetInformation(){
	   Meuble meuble = Meuble.getInstance();
	   meuble.reset();
       EditText prixMeuble = (EditText) findViewById(R.id.editPrixMeuble);
       prixMeuble.setText("");
       RadioGroup radioGroupAnnee = (RadioGroup) findViewById(R.id.radiogroup);
  	   radioGroupAnnee.clearCheck();
  	   resetSomeInformation();
   }
   public void init(){
   	
	   //Plan de remplacement Fauteuils/Chaises
	   vectOption.add(new PlanReplaceChairs("Plan de rempl. Fauteuil/Chaises", 6.99 , 1, 0.0,69.99, 732503 ));
	   vectOption.add(new PlanReplaceChairs("Plan de rempl. Fauteuil/Chaises", 12.99 , 1, 70.0, 149.99, 567432));
	   
	   //Plan de réparation Meuble Fauteuils/Chaises
	   vectOption.add(new PlanRepairMeuble("Plan de répar. Fauteuil/Chaises", 14.99 , 1, 0.0, 149.99, 567435));
	   vectOption.add(new PlanRepairMeuble("Plan de répar. Fauteuil/Chaises", 24.99 , 2, 0.0, 149.99, 567444));
	   vectOption.add(new PlanRepairMeuble("Plan de répar. Fauteuil/Chaises", 19.99 , 1, 150.0, 299.99, 785248));
	   vectOption.add(new PlanRepairMeuble("Plan de répar. Fauteuil/Chaises", 34.99 , 2, 150.0, 299.99, 675816));
	   vectOption.add(new PlanRepairMeuble("Plan de répar. Fauteuil/Chaises", 39.99 , 1, 300.0, 499.99, 785318));
	   vectOption.add(new PlanRepairMeuble("Plan de répar. Fauteuil/Chaises", 54.99 , 2, 300.0, 499.99, 567445));
	   vectOption.add(new PlanRepairMeuble("Plan de répar. Fauteuil/Chaises", 59.99 , 1, 500.0, 749.99, 785369));
	   vectOption.add(new PlanRepairMeuble("Plan de répar. Fauteuil/Chaises", 84.99 , 2, 500.0, 749.99, 567447));
	   vectOption.add(new PlanRepairMeuble("Plan de répar. Fauteuil/Chaises", 79.99 , 1, 750.0, 999.99, 785420));
	   vectOption.add(new PlanRepairMeuble("Plan de répar. Fauteuil/Chaises", 109.99 , 2, 750.0, 999.99, 567448));
	   vectOption.add(new PlanRepairMeuble("Plan de répar. Fauteuil/Chaises", 159.99 , 1, 1000.0, 1999.99, 785447));
	   vectOption.add(new PlanRepairMeuble("Plan de répar. Fauteuil/Chaises", 219.99 , 2, 1000.0, 1999.99, 567449));
	   vectOption.add(new PlanRepairMeuble("Plan de répar. Fauteuil/Chaises", 179.99 , 1, 2000.0, 2999.99, 785473));
	   vectOption.add(new PlanRepairMeuble("Plan de répar. Fauteuil/Chaises", 329.99 , 2, 2000.0, 2999.99, 567450));
	   vectOption.add(new PlanRepairMeuble("Plan de répar. Fauteuil/Chaises", 259.99 , 1, 3000.0, 4999.99, 785577));
	   vectOption.add(new PlanRepairMeuble("Plan de répar. Fauteuil/Chaises", 439.99 , 2, 3000.0, 4999.99, 567451));
	   vectOption.add(new PlanRepairMeuble("Plan de répar. Fauteuil/Chaises", 399.99 , 1, 5000.0, 7499.99, 785603));
	   vectOption.add(new PlanRepairMeuble("Plan de répar. Fauteuil/Chaises", 699.99 , 2, 5000.0, 7499.99, 567452));

	   //Plan de réparation pour Bureau de Verres
	   vectOption.add(new PlanRepairBureauVerre("Plan de répar. Bureau de verres", 19.99 , 1, 0.0, 149.99, 732529));
	   vectOption.add(new PlanRepairBureauVerre("Plan de répar. Bureau de verres", 29.99 , 2, 0.0, 149.99, 732667));
	   vectOption.add(new PlanRepairBureauVerre("Plan de répar. Bureau de verres", 24.99 , 1, 150.0, 299.99, 732550));
	   vectOption.add(new PlanRepairBureauVerre("Plan de répar. Bureau de verres", 44.99 , 2, 150.0, 299.99, 732680));
	   vectOption.add(new PlanRepairBureauVerre("Plan de répar. Bureau de verres", 49.99 , 1, 300.0, 499.99, 732552));
	   vectOption.add(new PlanRepairBureauVerre("Plan de répar. Bureau de verres", 69.99 , 2, 300.0, 499.99, 732706));
	   vectOption.add(new PlanRepairBureauVerre("Plan de répar. Bureau de verres", 69.99 , 1, 500.0, 749.99, 732560));
	   vectOption.add(new PlanRepairBureauVerre("Plan de répar. Bureau de verres", 109.99 , 2, 500.0, 749.99, 732712));
	   vectOption.add(new PlanRepairBureauVerre("Plan de répar. Bureau de verres", 99.99 , 1, 750.0, 999.99, 732576));
	   vectOption.add(new PlanRepairBureauVerre("Plan de répar. Bureau de verres", 139.99 , 2, 750.0, 999.99, 732714));
	   vectOption.add(new PlanRepairBureauVerre("Plan de répar. Bureau de verres", 199.99 , 1, 1000.0, 1999.99, 732586));
	   vectOption.add(new PlanRepairBureauVerre("Plan de répar. Bureau de verres", 279.99 , 2, 1000.0, 1999.99, 732717));
	   vectOption.add(new PlanRepairBureauVerre("Plan de répar. Bureau de verres", 219.99 , 1, 2000.0, 2999.99, 732594));
	   vectOption.add(new PlanRepairBureauVerre("Plan de répar. Bureau de verres", 409.99 , 2, 2000.0, 2999.99, 732725));
	   vectOption.add(new PlanRepairBureauVerre("Plan de répar. Bureau de verres", 319.99 , 1, 3000.0, 4999.99, 732641));
	   vectOption.add(new PlanRepairBureauVerre("Plan de répar. Bureau de verres", 559.99 , 2, 3000.0, 4999.99, 732726));
	   vectOption.add(new PlanRepairBureauVerre("Plan de répar. Bureau de verres", 499.99 , 1, 5000.0, 7499.99, 732654));
	   vectOption.add(new PlanRepairBureauVerre("Plan de répar. Bureau de verres", 899.99 , 2, 5000.0, 7499.99, 732728));

   }
}
