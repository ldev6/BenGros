/**
 * Create by Laurence de Villers
 * www.laurencedevillers.com
 * Staples number 1587820
 */
package staples.computer.utility;

import staples.computer.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CostumDialog extends Activity{

	private Dialog dialog;
	public CostumDialog(Context context){
		dialog = new Dialog(context);
		dialog.setContentView(R.layout.custom_dialog);
		
		TextView text = (TextView) dialog.findViewById(R.id.textViewPrixAjout);
		text.setText(R.string.prix);
		TextView textSku = (TextView) dialog.findViewById(R.id.textViewSkuAjout);
		textSku.setText(R.string.sku);
		
		Button bCancel = (Button) dialog.findViewById(R.id.buttonAjoutCancel);
		bCancel.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				dialog.dismiss();
				
			}
		
		});
		
	}
	
	public void setTitle(String title){
		dialog.setTitle(title);
	}
	
	public void setOkListener(OnClickListener listener){
		Button bOk = (Button) dialog.findViewById(R.id.buttonAjoutOk);
		bOk.setOnClickListener(listener);
	}
	
	public void close() {
		dialog.dismiss();
	}
	public Dialog getDialog(){
		return dialog;
	}
	public void show(){
	    dialog.show();
	}

}
