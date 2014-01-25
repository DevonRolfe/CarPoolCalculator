package devonshire.carpoolcalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MenuActivity extends Activity {

	CarPoolData data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		data = new CarPoolData(getApplicationContext(), getFilesDir());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	 /** Called when the user clicks the Add Person button */
    public void addPersonMenu(View view) {
    	Intent intent = new Intent(this, AddPersonActivity.class);
    	startActivity(intent);
    }
   
    
    public void viewPersonMenu(View view) {
    	//data.loadData();
    	
    	Intent intent = new Intent(this, ViewPersonActivity.class);
    	startActivity(intent);
    }
    
    public void addTripMenu(View view) {
    	
    	Intent intent = new Intent(this, AddTripActivity.class);
    	startActivity(intent);
    }

    public void setTripCost(View view){
    	Intent intent = new Intent(this, SetCostActivity.class);
    	startActivity(intent);
    }
    
    public void showAllTotals(View view){
    	Intent intent = new Intent(this, ShowTotalsActivity.class);
    	startActivity(intent);
    }
    
    public void clearAllTotals(View view){
    	 new AlertDialog.Builder(this)
         .setIcon(android.R.drawable.ic_dialog_alert)
         .setTitle("Clear data?")
         .setMessage("Are you sure you want to set all user fees to 0?")
         .setPositiveButton("Clear", new DialogInterface.OnClickListener() {

             @Override
             public void onClick(DialogInterface dialog, int which) {

                 data.clearData();  
             }

         })
         .setNegativeButton("Cancel", null)
         .show();
    }

    public void exitCPC(View view){
    	
    	data.saveData();
    	System.exit(0);
    	
    }
}
