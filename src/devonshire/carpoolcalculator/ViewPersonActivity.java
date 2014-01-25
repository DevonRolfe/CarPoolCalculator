package devonshire.carpoolcalculator;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPersonActivity extends Activity implements OnItemSelectedListener {

	CarPoolData data;
	Context context;
	Bundle bundle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_person);
		
		context = getApplicationContext();
		data = new CarPoolData(context, getFilesDir());
		bundle = savedInstanceState;
		
		Spinner spinner = (Spinner) findViewById(R.id.person_chooser);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, data.getUserNames());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	    
		// Show the Up button in the action bar.
		setupActionBar();
		
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
		getMenuInflater().inflate(R.menu.view_person, menu);
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
	public void onItemSelected(AdapterView<?> adapter, View view, int pos,
			long id) {
		
		TextView textbox = (TextView) findViewById(R.id.fees_details);
		
		if(adapter.getSelectedItem().toString().equals("Nobody")){
			
			textbox.setText("");
			
			Toast hello = Toast.makeText(context, "Please select a carpool user", Toast.LENGTH_SHORT);
			hello.show();
		}
		else{
			
			textbox.setText("Showing fees for " + adapter.getSelectedItem() + "\n");
			textbox.append("\n");
			
			for(int i = 1; i < data.getNumUsers(); i++){
				if(i != pos){
					textbox.append("" + data.getUserNameByID(i) + ": R" + data.getFeesByID(pos, i) + "\n");
				}
			}
			
		}
		
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
