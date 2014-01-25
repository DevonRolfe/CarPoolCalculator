package devonshire.carpoolcalculator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import android.content.Context;
import android.widget.Toast;

public class CarPoolData {
	
	Context context;
	File directory;
	File dataFile;
	File namesFile;
	Toast toastFNF;
	Toast toastIOE;
	
	int numUsers = 0;
	double userFees[][];
	CarPoolUser users[];
	String userNames[];
	double costPerTrip = 0.0; //Must set while reading in data

	
	public CarPoolData(Context c, File dir){
		
		context = c;
		directory = dir;
		
		dataFile = new File("" + directory + "/" + "CPC_user_data.txt");
		namesFile = new File("" + directory + "/" + "CPC_user_names.txt");
		toastFNF = Toast.makeText(context, "File Not Found", Toast.LENGTH_SHORT);
		toastIOE = Toast.makeText(context, "IO Exception", Toast.LENGTH_SHORT);
		
		this.loadData();
		
	}

	public void loadData(){

		//Perform checks
		
		this.checkForFiles();
		
		//Count Carpool Users
		
		this.setNumUsers();
		
		//Make users array to link id numbers to names
		users = new CarPoolUser[numUsers];
		
		//Make 2D array for fees per person
		userFees = new double[numUsers][numUsers];
		
		//Populate users[]
		
		try{
			Scanner userData = new Scanner(namesFile).useDelimiter("#");
			//Load ID numbers
			
			for(int i = 0; i < numUsers; i++){
				try{
					users[i] = new CarPoolUser(userData.nextInt());
					userData.nextLine();
				}
				catch(InputMismatchException ime){
					//users[i] = new CarPoolUser(0, 0.0, 0.0, 0.0, 0.0, 0.0);
					//String testText = "" + userData.nextInt() + " " + userData.nextDouble() + " " + userData.nextDouble() + " " + userData.nextDouble() + " " + userData.nextDouble() + " " + userData.nextDouble();
					Toast toastInputMismatch = Toast.makeText(context, "INPUT MISMATCH on user " + (i +1) + " of " + numUsers + " users.", Toast.LENGTH_SHORT);
					toastInputMismatch.show();
				}
				catch(NoSuchElementException nse){
					Toast toastNse = Toast.makeText(context, "NSE on user " + (i +1) + " of " + numUsers + " users.", Toast.LENGTH_SHORT);
					toastNse.show();
				}
			}
			
			userData.close();
		}
		catch(FileNotFoundException fnf){
			toastFNF.show();
		}
		
		//Get Usernames
		int tempID;
		String tempName;
		boolean found;
		
		for(int i = 0; i < numUsers; i++){
			tempID = 0;
			tempName = null;
			found = false;
			
			try{
				Scanner userData = new Scanner(namesFile).useDelimiter("#");
				
				while(userData.hasNextLine() && found == false){
					
					tempID = userData.nextInt();	
					tempName = userData.next();
					
					if(tempID == users[i].getID()){
						found = true;
						users[i].setName(tempName);
					}
						
					userData.nextLine();
				}
				
				userData.close();
				
				if (found == false){
					users[i].setName("NAME NOT FOUND");
				}	
			}
			catch(FileNotFoundException FNF){
				users[i].setName("NO FILE");
			}
			
			catch(NullPointerException np){
				Toast toastNp = Toast.makeText(context, "NULL POINTER while reading user data, must be problem with loading data" , Toast.LENGTH_SHORT);
				toastNp.show();
			}
		}

		//Load userFees array
		
		this.loadFees();
		
	}

	private void checkForFiles(){
		//Try open CPC_user_data.txt
		
		try {
		    Scanner sc = new Scanner(dataFile);
		    sc.close();
		}
		catch (FileNotFoundException fnf) {

		//Does not exist, therefore make it
			try{
				//Show TOAST    
			    Toast toastMkFile = Toast.makeText(context, "Setting up CPC_user_data.txt", Toast.LENGTH_SHORT);
			    toastMkFile.show();
			    
			    //Create file
			    PrintWriter writer = new PrintWriter(dataFile);
			    writer.println("0.0#");
			    writer.close();
			}
			catch(FileNotFoundException noFile){
				
				//Error Message
				Toast toastNoFile = Toast.makeText(context, "File not created: " + noFile, Toast.LENGTH_SHORT);
				toastNoFile.show();
			}
		}
		
		//Check that CDC_user_names.txt exists

		try {
		    Scanner sc = new Scanner(namesFile);
		    sc.close();
		}
		catch (FileNotFoundException fnf) {

		//Does not exist, therefore make it
			try{
				//Show TOAST    
			    Toast toastMkFile = Toast.makeText(context, "Setting up CPC_user_names.txt", Toast.LENGTH_SHORT);
			    toastMkFile.show();
			    
			    //Create file
			    PrintWriter writer = new PrintWriter(namesFile);
			    writer.println("000#Nobody#");
			    writer.close();
			}
			catch(FileNotFoundException noFile){

				//Show error message
				
				Toast toastNoFile = Toast.makeText(context, "File not created: " + noFile, Toast.LENGTH_SHORT);
				toastNoFile.show();
			}
		}
		
	}
	
	private void setNumUsers(){
		
		numUsers = 0;
		//Count lines
		
		try {
		   	Scanner sc = new Scanner(dataFile);
			
		    while (sc.hasNextLine()) {
		    	numUsers++;
		        sc.nextLine();
		    }
		    
		    sc.close();
		    
		    //Toast toastNumUsers = Toast.makeText(context, "" + numUsers, Toast.LENGTH_LONG);
		    //toastNumUsers.show();
		    
		}
		catch (FileNotFoundException fnf) {
		    //Show FNF error
		    toastFNF.show();
		}
	}
	
	public int getNumUsers(){
		return numUsers;
	}

	public void addUser(String name){
		//Add to names file
		try{
			PrintWriter pw = new PrintWriter(new FileWriter(namesFile,true));
			
			pw.println("" + numUsers + '#' + name + '#');
			pw.close();
		}
		catch(FileNotFoundException FNF){
			toastFNF.show();
		}
		catch(IOException ioe){
			toastIOE.show();
		}
		
		//Add to data file
		try{
			PrintWriter pw = new PrintWriter(dataFile);
			
			for(int i = 0; i < numUsers; i++){
				for(int j = 0; j < numUsers; j++){
					pw.write("" + userFees[i][j] + '#');	
				}
				
				pw.println("0.0#");
			}
			
			for(int i = 0; i < numUsers +1; i++){
				pw.write("0.0#");
			}
			pw.close();
		}
		catch(FileNotFoundException FNF){
			toastFNF.show();
		}
			//Write everyone's data to file, line by line, add 0.0 to each line
			//Write new line, all 0.0
		
		this.loadData();
	}
	
	public void loadFees(){
		try{
			Scanner userData = new Scanner(dataFile).useDelimiter("#");
		
			try{
				
			for(int i = 0; i < numUsers; i++){
				for(int j = 0; j < numUsers; j++){
					userFees[i][j] = userData.nextDouble();		
				}
				userData.nextLine();
			}
			
			}
			catch(InputMismatchException ime){
				Toast toastIME = Toast.makeText(context, "INPUT MISMATCH on loading fees " + userData.nextLine(), Toast.LENGTH_SHORT);
				toastIME.show();
			}
		}
		catch(FileNotFoundException FNF){
			toastFNF.show();
		}
		
		costPerTrip = userFees[0][0];
	}
	
	public String[] getUserNames(){
		
		String names[] = new String[numUsers];
		
		for(int i = 0; i < numUsers; i++){
			names[i] = users[i].userName;
		}
		
		return names;
	}

	public String getUserNameByID(int id){
		return users[id].getName();
	}

	public double getFeesByID(int id1, int id2){
		//id1 is ower
		//id2 is owed
		
		return userFees[id1][id2];
	}

	public void saveData(){
		
		this.getNumUsers();
		
		try{
			PrintWriter pw = new PrintWriter(dataFile);
			
			for(int i = 0; i < numUsers; i++){
				for(int j = 0; j < numUsers; j++){
					pw.write("" + userFees[i][j] + '#');	
				}
				pw.println();
			}
			pw.close();
		}
		catch(FileNotFoundException FNF){
			toastFNF.show();
		}		
	}

	public boolean addFeesByID(int passenger, int driver, int numPass){
		
		if(costPerTrip != 0.0){
			double costPerPass = costPerTrip/(numPass+1);

			userFees[passenger][driver] = userFees[passenger][driver] + costPerPass;
			
			this.saveData();
			
			this.simplifyFees();
			
			return true;
		}
		else{
			return false;
		}
	}

	public void setCostPerTrip(double cost) {
		userFees[0][0] = cost;
		
		this.saveData();		
	}

	public double getCostPerTrip() {
		return userFees[0][0];
	}

	private void simplifyFees(){
		this.loadData();
		
		for(int i = 1; i < numUsers; i++){
			for(int j = 1; j < numUsers; j++){
				if(userFees[i][j] >= userFees [j][i]){
					userFees[i][j] = userFees[i][j] - userFees[j][i];
					userFees[j][i] = 0;
				}
			}
		}
		
		this.saveData();		
	}
	
	public void clearData(){
		
		for(int i = 1; i < numUsers; i++){
			for(int j = 1; j < numUsers; j++){
				userFees[i][j] = 0.0;
			}
		}
		
		this.saveData();
	}
}
