package staples.computer;

import java.util.EventListener;
import java.util.Vector;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
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
	      Ordinateur ordi = Ordinateur.getInstance();
	      initRow(ordi.getOptions(), ordi);
	      
	}
	
	public void initRow(Vector<Option> ordiVecteur, Ordinateur ordi){
		TableLayout tabLayout = (TableLayout)findViewById(R.id.tableLayout);

		TableRow ligne = new TableRow(this);
		TextView textOrdi = new TextView(this);
		textOrdi.setText(ordi.getString());
		TextView textSkuOrdi = new TextView(this);
		textSkuOrdi.setText(""+ordi.getSku());
		textSkuOrdi.setTextAppearance(this, android.R.style.TextAppearance_Large);
		textSkuOrdi.setPadding(10, 0, 0, 0);
		ligne.addView(textOrdi);
		ligne.addView(textSkuOrdi);
		tabLayout.addView(ligne);
		if(ordi.getRabais()!=0){
			TableRow ligneRabais = new TableRow(this);
			TextView textRabais = new TextView(this);
			textRabais.setText("Rabais liquidation "+String.format("%.2f",ordi.getRabais())+"$");
			TextView textRabaisMontant = new TextView(this);
			textRabaisMontant.setText(""+(ordi.getTauxLiquid()*100)+"%");
			textRabaisMontant.setTextAppearance(this, android.R.style.TextAppearance_Large);
			textRabaisMontant.setPadding(10, 0, 0, 0);
			ligneRabais.addView(textRabais);
			ligneRabais.addView(textRabaisMontant);
			tabLayout.addView(ligneRabais);
		}
	
		for(int i=0; i< ordiVecteur.size();i++)
		{

			TableRow tr = new TableRow(this);
			String textSku =""+ordiVecteur.get(i).getSku();  
			String textDescrip=""+ordiVecteur.get(i).getString();
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

    ;
	}

}
