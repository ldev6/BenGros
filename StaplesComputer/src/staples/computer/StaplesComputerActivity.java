/**
 * Create by Laurence de Villers
 * www.laurencedevillers.com
 * Staples number 1587820
 */
package staples.computer;

import java.util.Vector;

import staples.MenuViewActivity;
import staples.Plan;
import staples.skuActivity;
import staples.computer.utility.CostumDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class StaplesComputerActivity extends Activity {
	public final int BUREAU = 0;
	public final int LAPTOP = 1; 
	
	private Ordinateur ordi;
	private Vector<Option> vectOption = new Vector<Option>();
	private Vector<Option> vectOptionWithPrice = new Vector<Option>();
	private Vector<Configuration> vectConfiguration = new Vector<Configuration>();
	private int  annee ;
	
	private  CheckBox accidentCheck;
	private CheckBox recupCheck;
	private Boolean isAMac = false;
	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main);
        accidentCheck = (CheckBox) findViewById(R.id.checkBoxAccident);
   	    accidentCheck.setClickable(false);
   	    recupCheck = (CheckBox) findViewById(R.id.checkRecup);
   	    recupCheck.setClickable(false);
   	    EditText editRabaisText = (EditText) findViewById(R.id.editLiquidation);
   	    editRabaisText.setVisibility(View.INVISIBLE);
        ordi = Ordinateur.getInstance();
        createPlan();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.scan_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.scan:
            	Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        	    startActivityForResult(intent,0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
 }
    
    	
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
 		if(requestCode ==0){
 			String contents = intent.getStringExtra("SCAN_RESULT");
 			String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
 			
 			Log.v("nice", "shitecontents+"+contents);
 		}
 		else if (resultCode ==RESULT_CANCELED){
 			;
 		}
 		
 	}
    
    public void checkAccidentel(View v){
    	CheckBox accidentCheck = (CheckBox) findViewById(R.id.checkBoxAccident);
    	 if (accidentCheck.isChecked()) {
    		 addAccidentel(annee);
         }
    	 else{
    		 ordi.removeAccidentel();
    		 EditText editAccident = (EditText) findViewById(R.id.editAccidentel);
     		editAccident.setText("");
    	 }
    	 setTotalPrix();	
    }
    
    public void checkRecup(View v){
    	CheckBox recupCheck = (CheckBox) findViewById(R.id.checkRecup);
    	if (recupCheck.isChecked()) {
   		 	addRecup(annee);
        	}
    	else{
    		ordi.removeRecup();
    		EditText editRecup = (EditText) findViewById(R.id.editRecupD);
    		editRecup.setText("");
    	}
   	 	setTotalPrix();	
    }
    
    public void checkRabais(View v){
    	CheckBox liquidCheck = (CheckBox) findViewById(R.id.checkRabais);
    	EditText editRabaisText = (EditText) findViewById(R.id.editLiquidation);
    	if(liquidCheck.isChecked()){
    		ordi.addRabais();
    		editRabaisText.setTextColor((0xffff0000));
    		editRabaisText.setVisibility(View.VISIBLE);
    	}
    	else{
    		ordi.removeRabais();
    		editRabaisText.setVisibility(View.INVISIBLE);
    	}
		editRabaisText.setText("-"+String.format("%.2f",ordi.getRabais())+'$');
		setTotalPrix();
		
    }
    
    
    public void radioClick(View v){
    	RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
    	int ordinateur = 0;
    	 
    	int checkedRadioButton = radioGroup.getCheckedRadioButtonId();
    	TableRow tableRow = (TableRow) findViewById(R.id.tableRow4);
    	
    	switch (checkedRadioButton) {
    	
    	  case R.id.radioButton1 : ordinateur = BUREAU ;
    	  tableRow.setVisibility(View.GONE);
    	                   	              break;
    	  case R.id.radioButton2 : ordinateur = LAPTOP;
    	  tableRow.setVisibility(View.VISIBLE);
    			                          break;
    	}
    	EditText prixEditText =(EditText) findViewById(R.id.editText1);
    	String  prixText = prixEditText.getText().toString(); 
    	
    	resetSomeInformation();
    	ordi.resetVect();
    	if(!prixText.equals("")){
	    	double prix =  Double.parseDouble(prixText);
	    	prixEditText.setText(""+prix);
	    	ordi.setCategorie(ordinateur);
	    	chooseOptionWithPrice(prix);
	    	setTotalPrix();
    	}
    	
    }
    public void actionPrixOrdi(View v){
    	final CostumDialog dialog = new CostumDialog(this);
    	dialog.setTitle("Prix Ordinateur");
    	dialog.setOkListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
		
				EditText ordiTextPrix = (EditText) (dialog.getDialog()).findViewById(R.id.editTextPrixAjout);
				String ordiPrix = ordiTextPrix.getText().toString();
				
				EditText ordiTextSku = (EditText) (dialog.getDialog()).findViewById(R.id.editTextSkuAjout);
				String ordiSku = ordiTextSku.getText().toString();
				resetInformation();
				if(!ordiPrix.equals("")){
			    	double prix =  Double.parseDouble(ordiPrix);
			    	EditText prixEditText =(EditText) findViewById(R.id.editText1);
			    	prixEditText.setText(""+prix);
			    	ordi.setPrix(prix);
			    	setTotalPrix();
		    	}
				
				if(!ordiSku.equals("")){
		    		int sku =  Integer.parseInt(ordiSku);
		    		ordi.setSku(sku);
		    	}
				dialog.close();
			}
    	
    	});
    	dialog.show();
    }
    
    
    public void resetInfo(View v){
    	resetInformation();
    }
    
    public void resetInformation(){
    	ordi.reset();
        EditText prixOrdi = (EditText) findViewById(R.id.editText1);
        prixOrdi.setText("");
        RadioGroup radioGroupAnnee = (RadioGroup) findViewById(R.id.radiogroup);
   	    radioGroupAnnee.clearCheck();
    	resetSomeInformation();
    	recupCheck.setClickable(false);
        accidentCheck.setClickable(false);
        CheckBox liquidCheck = (CheckBox) findViewById(R.id.checkRabais);
   	    liquidCheck.setChecked(false);
   	    EditText editRabaisText = (EditText) findViewById(R.id.editLiquidation);
   	    editRabaisText.setText("");
   	    editRabaisText.setVisibility(View.INVISIBLE);
    }
    public void resetSomeInformation(){
    	vectOptionWithPrice.clear();
    	 
    	 EditText prixDonne = (EditText) findViewById(R.id.editRecupD);
    	 prixDonne.setText("");
	   	 
	   	 EditText prixPlan = (EditText) findViewById(R.id.editPrixPlan);
	   	 prixPlan.setText("");

	   	 EditText prixAccident = (EditText) findViewById(R.id.editAccidentel);
	   	 prixAccident.setText("");
    	
    	 EditText prixTotal = (EditText) findViewById(R.id.prixTotal);
    	 prixTotal.setText("");
    	 
    	 EditText prixTotalTaxe = (EditText) findViewById(R.id.prixTotalTaxe);
    	 prixTotalTaxe.setText("");
    	 
    	 RadioGroup radioGroupAnnee = (RadioGroup) findViewById(R.id.radiogroupAnnee);
    	 radioGroupAnnee.clearCheck();
    	 
    	 CheckBox accidentCheck = (CheckBox) findViewById(R.id.checkBoxAccident);
    	 accidentCheck.setChecked(false);
    	 
    	 CheckBox recupCheck2 = (CheckBox) findViewById(R.id.checkRecup);
    	 recupCheck2.setChecked(false);
    	 
    	 EditText editConfig = (EditText) findViewById(R.id.editConf);
    	 editConfig.setText("");
    	 
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
    	recupCheck.setClickable(true);
        accidentCheck.setClickable(true);
    	reafectOption(annee);
    	addPlan(annee);
    	setTotalPrix();	
    }
    
    public void setTotalPrix(){
    	 EditText prixTotal = (EditText) findViewById(R.id.prixTotal);
    	 prixTotal.setText(String.format("%.2f",ordi.calculPrixTotal())+"$");
    	 
    	 EditText prixTotalTaxe = (EditText) findViewById(R.id.prixTotalTaxe);
    	 prixTotalTaxe.setText(String.format("%.2f",ordi.calculPrixTotalTaxe())+"$");
    }
    
    
    public boolean addAccidentel(int annee){
     	if( vectOptionWithPrice.size()!=0){
		     for(int i=0; i<vectOptionWithPrice.size(); i++){
		    	 if(vectOptionWithPrice.get(i) instanceof Accidentel && ((Plan) vectOptionWithPrice.get(i)).getAnnee() == annee+1){
		    		 ordi.addVector(vectOptionWithPrice.get(i));
		    		 EditText prixAccident =(EditText) findViewById(R.id.editAccidentel);
		    		 prixAccident.setText(String.format("%.2f", vectOptionWithPrice.get(i).getPrix())+"$");
		    		 return true;
		    	 }
		     }
		}
		return false;
    }
    public boolean addRecup(int annee){
     	if( vectOptionWithPrice.size()!=0){
		     for(int i=0; i<vectOptionWithPrice.size(); i++){
		    	 if(vectOptionWithPrice.get(i) instanceof PlanRecup && ((Plan) vectOptionWithPrice.get(i)).getAnnee() == annee+1){
		    		 ordi.addVector(vectOptionWithPrice.get(i));
		    		 EditText prixRecupD =(EditText) findViewById(R.id.editRecupD);
		       		 prixRecupD.setText(String.format("%.2f",vectOptionWithPrice.get(i).getPrix())+"$");
		    		 return true;
		    	 }
		     }
		}
		return false;
    }
    public boolean addConfig(int option){
     	if( vectConfiguration.size()!=0){
		     for(int i=0; i<vectConfiguration.size(); i++){
		    	 if(vectConfiguration.get(i).getNiveau() == option){
		    		 ordi.addVector(vectConfiguration.get(i));
		    		 EditText prixConfig =(EditText) findViewById(R.id.editConf);
		       		 prixConfig.setText(String.format("%.2f",vectConfiguration.get(i).getPrix())+"$");
		    		 return true;
		    	 }
		     }
		}
		return false;
    }
    
       
   
    public boolean addPlan(int annee){
    	if( vectOptionWithPrice.size()!=0){
		     for(int i=0; i<vectOptionWithPrice.size(); i++){
		    	 
		    	 if (ordi.getCategorie()==LAPTOP && vectOptionWithPrice.get(i)instanceof PlanPortatifs &&  ((Plan) vectOptionWithPrice.get(i)).getAnnee() == annee  ){
		    		 ordi.addVector(vectOptionWithPrice.get(i));
		    		 EditText prixPlan = (EditText) findViewById(R.id.editPrixPlan);
			    	 prixPlan.setText(String.format("%.2f",vectOptionWithPrice.get(i).getPrix())+"$");
			    	
		    		 return true;
		    	 }
		    	 else if(ordi.getCategorie()==BUREAU && vectOptionWithPrice.get(i)instanceof PlanOrdiBureau &&  ((Plan) vectOptionWithPrice.get(i)).getAnnee() == annee ){
		    		 ordi.addVector(vectOptionWithPrice.get(i));
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
  
    public void actionSku(View v){
    	  Intent addEventActivity = new Intent(StaplesComputerActivity.this, skuActivity.class);
     	  addEventActivity.putExtra("FROM_ORDI", "FROM_ORDI");
          startActivity(addEventActivity);
    }
    public void confAction(View v){
    	DialogConfig dialog = new DialogConfig(this,vectConfiguration,dialogConfigListener);
    	dialog.show();
    }
    public void createPlan(){
    	
    	//Plan Ordinateur portable et mini portatifs
    	vectOption.add(new PlanPortatifs("Plan ordinateurs portatifs et mini", 44.99, 1, 0.0, 249.99, 420911));
    	vectOption.add(new PlanPortatifs("Plan ordinateurs portatifs et mini", 59.99, 2, 0.0, 249.99, 731523));
    	vectOption.add(new PlanPortatifs("Plan ordinateurs portatifs et mini", 59.99, 1, 250.00, 349.99, 731505));
    	vectOption.add(new PlanPortatifs("Plan ordinateurs portatifs et mini", 69.99, 2, 250.00, 349.99, 774809));
    	vectOption.add(new PlanPortatifs("Plan ordinateurs portatifs et mini", 79.99, 1, 350.00, 499.99, 731510));
    	vectOption.add(new PlanPortatifs("Plan ordinateurs portatifs et mini", 99.99, 2, 350.00, 499.99, 774854));
    	vectOption.add(new PlanPortatifs("Plan ordinateurs portatifs et mini", 129.99, 1, 500.00, 999.99, 731513));
    	vectOption.add(new PlanPortatifs("Plan ordinateurs portatifs et mini", 149.99, 2, 500.00, 999.99, 774855));
    	vectOption.add(new PlanPortatifs("Plan ordinateurs portatifs et mini", 179.99, 1, 1000.00,10000.00, 731517));
    	vectOption.add(new PlanPortatifs("Plan ordinateurs portatifs et mini", 199.99, 2, 1000.00,10000.00, 774812));
    	
       //PLan Accidentel 
    	vectOption.add(new Accidentel("Accidentel", 34.99, 2, 0.00, 249.99, 756034));
    	vectOption.add(new Accidentel("Accidentel", 44.99, 3, 0.00, 249.99, 785168));
    	vectOption.add(new Accidentel("Accidentel", 39.99, 2, 250.00, 499.99, 731531));
    	vectOption.add(new Accidentel("Accidentel", 49.99, 3, 250.00, 499.99, 731536));
    	vectOption.add(new Accidentel("Accidentel", 79.99, 2, 500.00, 999.99, 785164));
    	vectOption.add(new Accidentel("Accidentel", 99.99, 3, 500.00, 999.99, 816911));
    	vectOption.add(new Accidentel("Accidentel", 129.99, 2, 1000.00, 10000.00, 785167));
    	vectOption.add(new Accidentel("Accidentel", 149.99, 3, 1000.00, 10000.00, 816914));

    	//Plan Ordi Bureau
    	vectOption.add(new PlanOrdiBureau("Plan ordinateur de bureau", 59.99, 1, 100.0, 499.99, 731549));
    	vectOption.add(new PlanOrdiBureau("Plan ordinateur de bureau", 64.99, 2, 100.0, 499.99, 503101));
    	vectOption.add(new PlanOrdiBureau("Plan ordinateur de bureau", 99.99, 1, 500.0, 999.99, 731554));
    	vectOption.add(new PlanOrdiBureau("Plan ordinateur de bureau", 109.99, 2, 500.0, 999.99, 503104));
    	vectOption.add(new PlanOrdiBureau("Plan ordinateur de bureau", 149.99, 1, 1000.0, 1499.99, 731561 ));
    	vectOption.add(new PlanOrdiBureau("Plan ordinateur de bureau", 164.99, 2, 1000.0, 1499.99, 503105 ));
    	vectOption.add(new PlanOrdiBureau("Plan ordinateur de bureau", 199.99, 1, 1500.0, 10000.0, 731562 ));
    	vectOption.add(new PlanOrdiBureau("Plan ordinateur de bureau", 219.99, 2, 1500.0, 10000.0, 503106 ));


    	//Recuperation Donnee
    	vectOption.add(new PlanRecup("Récuperation des données", 29.99, 2,0.00, 10000.0, 732731 ));
    	vectOption.add(new PlanRecup("Récuperation des données", 39.99, 3,0.00, 10000.0, 732732 ));


    	
    	//Configuration
    	vectConfiguration.add(new Configuration("Niveau Ultra Windows", 149.00, 1, 953641));
    	vectConfiguration.add(new Configuration("Niveau Ultra MiniPortatifs", 99.00, 2, 953643));
    	vectConfiguration.add(new Configuration("Ensemble Supérieur", 149.00, 3, 887478));
    	vectConfiguration.add(new Configuration("Ensemble Avancé", 129.00, 4, 887477));
    	vectConfiguration.add(new Configuration("Ensemble base", 99.00, 5, 888888)); 
    	
    	//Configuration qui va être rajouter ultérieurement
    	//vectConfiguration.add(new Configuration("Niveau Ultra Mac", 149.00, 1, 953642));
    	//vectConfiguration.add(new Configuration("Niveau Ultra Ipad", 99.00, 1, 953644));
    	//vectConfiguration.add(new Configuration("Niveau Ultra Android", 99.00, 1, 953645));
    	//vectConfiguration.add(new Configuration("Niveau Ultra BlackBerry", 99.00, 1, 953646));
    	
    }
    
    DialogInterface.OnClickListener dialogConfigListener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int item) {
			EditText editConfig = (EditText) findViewById(R.id.editConf);
			if(item>=vectConfiguration.size()){
				editConfig.setText("");
				ordi.removeConfig();
			}
			else{
				editConfig.setText(String.format("%.2f",vectConfiguration.get(item).getPrix())+"$");
				ordi.removeConfig();
				addConfig(vectConfiguration.get(item).getNiveau());
			}
			setTotalPrix();	
		}
	};
	
	
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
			    	ordi.addArticleSup(article);
			    	setTotalPrix();
		    	}
				
				dialog.close();
			}
			
		});
	    dialog.show();
	}
	
	public void reafectOption(int annee){
		Vector<Option> tmp = (Vector<Option>)ordi.getOptions().clone();
		ordi.resetVect();
		for(int i=0; i<tmp.size(); i++){
 	    	 if ( tmp.get(i)instanceof PlanRecup ){
 	    		addRecup(annee);
 	    	 }
 	    	 else if( tmp.get(i)instanceof Accidentel){
 	    		addAccidentel(annee);
 	    	 }
 	    	 else if(tmp.get(i)instanceof Configuration){
 	    		 ordi.addVector(tmp.get(i));
 	    	 }
 	     }
	}
	
}