package devonshire.carpoolcalculator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class ShowTotalsActivity extends Activity{

	CarPoolData data;
	int numUsers = 0;
	boolean toggleHelpView = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_totals);
		
		data = new CarPoolData(getApplicationContext(), getFilesDir());
		numUsers = data.getNumUsers();
		
		TextView dataDisplay = (TextView) findViewById(R.id.total_fees_textview);
		dataDisplay.setTypeface(Typeface.MONOSPACE);
		
		dataDisplay.setText("|          |");
		
		for(int i = 1; i < numUsers; i++){
			dataDisplay.append(String.format("%-10s",data.getUserNameByID(i)));
			dataDisplay.append("|");
		}
		dataDisplay.append("\n|");
		
		for(int i = 0; i < numUsers; i++){
			dataDisplay.append(String.format("%10s","----------"));
			dataDisplay.append("|");
		}
		dataDisplay.append("\n");
		
		for(int i = 1; i < numUsers; i++){
			dataDisplay.append("|");
			dataDisplay.append(String.format("%-10s", data.getUserNameByID(i)));
			dataDisplay.append("|");
			for(int j = 1; j < numUsers; j++){
				if(i==j){
					dataDisplay.append(String.format("%10s", "-"));
					dataDisplay.append("|");
				}
				else{
					dataDisplay.append(String.format("%10.2f", data.getFeesByID(i, j)));
					dataDisplay.append("|");
				}
			}
			dataDisplay.append("\n");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.show_totals, menu);
		return true;
	}

	public void shareViaWhatsapp(View view){
		
		StringBuilder output = new StringBuilder();
		
		boolean feesDue;
		//for every user
		for(int i = 1; i < numUsers; i++){
			feesDue = false;
			//check whether any fees are due
			for(int j = 1; j < numUsers; j++){
				if(data.getFeesByID(i, j) != 0){
					feesDue = true;
				}
			}
			
			//if due, add strings saying how much
			if(feesDue == true){
				output.append("" + data.getUserNameByID(i) + " owes:\n");
				for(int j = 1; j < numUsers; j++){
					if(data.getFeesByID(i, j) != 0){
						output.append("" + data.getUserNameByID(j) + " R" + data.getFeesByID(i, j) + "\n");
					}
				}
				
				output.append("\n");
			}
		}

		
		Intent waIntent = new Intent(Intent.ACTION_SEND);
	   
		waIntent.setType("text/plain");
	    waIntent.setPackage("com.whatsapp");
	    
	    waIntent.putExtra(Intent.EXTRA_TEXT, output.toString());//
		startActivity(Intent.createChooser(waIntent, "Share with"));
	}
}
