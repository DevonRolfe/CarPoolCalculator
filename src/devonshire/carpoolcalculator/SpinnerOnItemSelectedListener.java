package devonshire.carpoolcalculator;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class SpinnerOnItemSelectedListener extends ViewPersonActivity implements OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent,View view, int pos, long id) {
    	
    	if(parent.getSelectedItem().toString() == "Devon"){

    	}
    			
    }

    public void onNothingSelected(AdapterView<?> parent) {
      // Do nothing.
    }
}
