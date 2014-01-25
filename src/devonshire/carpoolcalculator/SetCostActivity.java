package devonshire.carpoolcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SetCostActivity extends Activity implements OnClickListener{

	CarPoolData data;
	EditText textbox;
	Button setCostButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_cost);
		// Show the Up button in the action bar.
		setupActionBar();
		
		data = new CarPoolData(getApplicationContext(), getFilesDir());
		
		textbox = (EditText) findViewById(R.id.set_cost_input);
		
		TextView setPrompt = (TextView) findViewById(R.id.set_cost_prompt);
		setPrompt.append(" (Currently set to: R" + data.getCostPerTrip());
		
		setCostButton = (Button) findViewById(R.id.button_set_cost);
		setCostButton.setOnClickListener(this);
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
		getMenuInflater().inflate(R.menu.set_cost, menu);
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
	public void onClick(View view) {
		data.setCostPerTrip(Integer.parseInt(textbox.getText().toString()));
		finish();
	}

}
