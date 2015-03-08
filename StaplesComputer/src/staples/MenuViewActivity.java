package staples;

import staples.computer.R;
import staples.computer.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class MenuViewActivity extends Activity{
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.menu_view);
    }
    
    
    public void onClickComputer(View v){
    	Intent startComputer = new Intent(MenuViewActivity.this, staples.computer.StaplesComputerActivity.class);
    	startActivityForResult(startComputer, 0);
    }
    
    
    public void onClickMeuble(View v){
    	Intent startMeuble = new Intent(MenuViewActivity.this, staples.bureau.MeubleActivity.class);
    	startActivityForResult(startMeuble, 0);
    }
}
