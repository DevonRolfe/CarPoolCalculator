package devonshire.carpoolcalculator;


public class CarPoolUser{
	String userName = "not a real name";
	int userID;	
	
	public CarPoolUser(int id){
		userID = (int)id;
	}
	
	public void setName(String name){
		userName = name;
	}
	
	public int getID(){
		return userID;
	}
	
	public String getName(){
		return userName;
	}
	
	public String toString(){
		return userID + " " + userName;
	}

}
