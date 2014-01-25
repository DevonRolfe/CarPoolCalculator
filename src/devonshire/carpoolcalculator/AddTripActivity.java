package devonshire.carpoolcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class AddTripActivity extends Activity implements OnClickListener{

	CarPoolData data;
	Spinner spinDriver, spinPass1, spinPass2, spinPass3, spinPass4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_trip);
		// Show the Up button in the action bar.
		setupActionBar();
		
		data = new CarPoolData(getApplicationContext(), getFilesDir());
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, data.getUserNames());
		
		spinDriver = (Spinner) findViewById(R.id.spinner_driver);
		spinDriver.setAdapter(adapter);
		
		spinPass1 = (Spinner) findViewById(R.id.spinner_pass1);
		spinPass1.setAdapter(adapter);
		spinPass2 = (Spinner) findViewById(R.id.spinner_pass2);
		spinPass2.setAdapter(adapter);
		spinPass3 = (Spinner) findViewById(R.id.spinner_pass3);
		spinPass3.setAdapter(adapter);
		spinPass4 = (Spinner) findViewById(R.id.spinner_pass4);
		spinPass4.setAdapter(adapter);		
		
		Button addTripButton = (Button) findViewById(R.id.button_add_trip);
		addTripButton.setOnClickListener(this);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_trip, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View arg0) {
		//Get data: driver, num passengers, array of passengers
		int driverID = spinDriver.getSelectedItemPosition();
		int numPass = 0;
		int passengersID[];
		
		if(spinPass1.getSelectedItemPosition() != 0){
			numPass++;
		}
		if(spinPass2.getSelectedItemPosition() != 0){
			numPass++;
		}
		if(spinPass3.getSelectedItemPosition() != 0){
			numPass++;
		}
		if(spinPass4.getSelectedItemPosition() != 0){
			numPass++;
		}
		
		boolean flag = false; //To show if fees were successfully added
		
		if(numPass != 0){
			passengersID = new int[numPass];
			
			if(spinPass1.getSelectedItemPosition() != 0){
				flag = data.addFeesByID(spinPass1.getSelectedItemPosition(), driverID, numPass);
			}
			if(spinPass2.getSelectedItemPosition() != 0){
				flag = data.addFeesByID(spinPass2.getSelectedItemPosition(), driverID, numPass);
			}
			if(spinPass3.getSelectedItemPosition() != 0){
				flag = data.addFeesByID(spinPass3.getSelectedItemPosition(), driverID, numPass);
			}
			if(spinPass4.getSelectedItemPosition() != 0){
				flag = data.addFeesByID(spinPass4.getSelectedItemPosition(), driverID, numPass);
			}
		}
		
		if(flag == true){
			Toast newTripConfirm = Toast.makeText(getApplicationContext(), "New trip added", Toast.LENGTH_SHORT);
			newTripConfirm.show();
			finish();
		}
		else{
			Toast newTripError = Toast.makeText(getApplicationContext(), "Please add the cost per trip in the main menu before adding trips", Toast.LENGTH_SHORT);
			newTripError.show();
		}
	}

}
