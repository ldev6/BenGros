/**
 * Create by Laurence de Villers
 * www.laurencedevillers.com
 * Staples number 1587820
 */
package staples;

import java.util.Calendar;
import java.util.EventListener;
import java.util.Vector;

import staples.bureau.Meuble;
import staples.computer.ArticleSupplementaire;
import staples.computer.Option;
import staples.computer.Ordinateur;
import staples.computer.R;
import staples.computer.R.id;
import staples.computer.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class skuActivity extends Activity{
	
	private ListView listView;
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  setContentView(R.layout.sku);
	      setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	     
	      
	      Bundle extras = getIntent().getExtras();
		   if(extras != null) {
	        	if(extras.containsKey("FROM_ORDI")){
	        		 Ordinateur ordi = Ordinateur.getInstance();
	       	      	 initRow(ordi.getOptions(), ordi);
		       	}
	        	else if(extras.containsKey("FROM_MEUBLE")){
	        		Meuble meuble = Meuble.getInstance();
	      	        initRow(meuble.getOptions(), meuble);

	        		
	           	}
		   }
	      
	}
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.menu, menu);
	        return true;
	    }
	 
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle item selection
	        switch (item.getItemId()) {
	            case R.id.return_menu:
	            	Intent menuActivity = new Intent(skuActivity.this, MenuViewActivity.class);
	            	startActivityForResult(menuActivity, 0);
	                return true;
	            default:
	                return super.onOptionsItemSelected(item);
	        }
	 }
	
	public void initRow(Vector<Option> optionVecteur, Objet objet){
		TableLayout tabLayout = (TableLayout)findViewById(R.id.tableLayout);
		TableRow ligne = new TableRow(this);
		TextView text = new TextView(this);
			
		text.setText(objet.getString());
		TextView textSkuObjet = new TextView(this);
		textSkuObjet.setText(""+objet.getSku());
		textSkuObjet.setTextAppearance(this, android.R.style.TextAppearance_Large);
		textSkuObjet.setPadding(10, 0, 0, 0);
		
		ligne.addView(text);
		ligne.addView(textSkuObjet);
		tabLayout.addView(ligne);
		
		if(objet instanceof Ordinateur){
			if(((Ordinateur)objet).getRabais()!=0){
				TableRow ligneRabais = new TableRow(this);
				TextView textRabais = new TextView(this);
				textRabais.setText("Rabais liquidation "+String.format("%.2f",((Ordinateur)objet).getRabais())+"$");
				TextView textRabaisMontant = new TextView(this);
				textRabaisMontant.setText(""+(((Ordinateur)objet).getTauxLiquid()*100)+"%");
				textRabaisMontant.setTextAppearance(this, android.R.style.TextAppearance_Large);
				textRabaisMontant.setPadding(10, 0, 0, 0);
				ligneRabais.addView(textRabais);
				ligneRabais.addView(textRabaisMontant);
				tabLayout.addView(ligneRabais);
			}
		}
		for(int i=0; i< optionVecteur.size();i++)
		{

			TableRow tr = new TableRow(this);
			String textSku =""+optionVecteur.get(i).getSku();  
			String textDescrip=""+optionVecteur.get(i).getString();
			tr.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
			TextView textViewDescrip = new TextView(this);
			textViewDescrip.setText(textDescrip);
        
			TextView textView = new TextView(this);
			textView.setText(textSku);
			textView.setTextAppearance(this,android.R.style.TextAppearance_Large);
			textView.setPadding(10, 0, 0, 0);
			tr.addView(textViewDescrip);
			tr.addView(textView);
			
			tabLayout.addView(tr);
		}

		Vector <ArticleSupplementaire> articleSupVect = objet.getVecteurArticleSup();
		for(int i=0; i< articleSupVect.size();i++)
		{

			TableRow tr = new TableRow(this);
			String textSku =""+articleSupVect.get(i).getSku();  
			String textDescrip=""+articleSupVect.get(i).getString();
			tr.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
			TextView textViewDescrip = new TextView(this);
			textViewDescrip.setText(textDescrip);
        
			TextView textView = new TextView(this);
			textView.setText(textSku);
			textView.setTextAppearance(this,android.R.style.TextAppearance_Large);
			textView.setPadding(10, 0, 0, 0);
			tr.addView(textViewDescrip);
			tr.addView(textView);
			
			tabLayout.addView(tr);
		}

	}

}
