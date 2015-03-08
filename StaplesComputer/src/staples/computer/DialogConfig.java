/**
 * Create by Laurence de Villers
 * www.laurencedevillers.com
 * Staples number 1587820
 */
package staples.computer;

import java.util.Calendar;
import java.util.Vector;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

public class DialogConfig {
	
	private CharSequence[] items ;
	private AlertDialog alert ;
	public DialogConfig(Context context,Vector<Configuration> vectConfig,DialogInterface.OnClickListener dialogConfigListener){

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
		setArray(vectConfig);	
		builder.setTitle(R.string.dialogTitle);
		builder.setItems(items, dialogConfigListener);
		builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		alert = builder.create();
	}
	
	private void setArray(Vector<Configuration> vectConfig){
		items = new CharSequence[vectConfig.size()+1];
		int i;
		for(i=0; i<vectConfig.size();i++){
			items[i] =""+vectConfig.get(i).getString();
		}
		items[i]="Aucun";
	}
	
	public void show(){
		alert.show();
	}

}
