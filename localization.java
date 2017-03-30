package navigateBot;
import java.util.HashMap;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
//pathfinding tut https://www.youtube.com/watch?v=-L-WgKMFuhE

public class localization {
	
	
	//Test alt sensor
	
	//Setup Colour sensor
	static Port colorSensorPort = SensorPort.S1;
	static EV3ColorSensor colorSensor = new EV3ColorSensor(colorSensorPort);
	static SampleProvider sampleProvider = colorSensor.getMode("RGB");
	static int sampleSize = sampleProvider.sampleSize();
	
	
	
	
	//pathfinding
	//https://www.youtube.com/watch?v=-L-WgKMFuhE
	//Setup Localization ??
	//https://www.youtube.com/watch?v=9a42_zEeeA0
	//will need [0,1] move right and [0,-1] move left
	
	static Boolean[] colors = {true, false, true, false, false, true, true, false, true, true, false, false, true, true, true, false, true, true, true, false, false, true, true, true, false, false, false, true, true, true, true, false, false, false};
	//prob sensor value is right / wrong
	static double sensor_right = 0.9;
	static double sensor_wrong = 1 - sensor_right;
	//prob we actually moved
	static double p_move = 1.0;
	static double p_stay = 1.0 - p_move;
	//initial distribution of each value.
	static double pinit = 1.0 / colors.length;
	//should this have a double or a float?
    static HashMap<Integer, Double> probDistHash;
	//normalization value;
    static double norm;
    static double totalProb;
    static double threshold = .65;
    //testing
    private static int counter = 0;
		
	/*static void moveTwo(){ //Moves robot 2cm
		leftMD.setSpeed(110);
		rightMA.setSpeed(110);
		leftMD.rotate(41, true);
		rightMA.rotate(41, true);
		Delay.msDelay(900);
	}*/
    
    static Coordinate getCoordinates(int locValue){
    	/*if(locValue % 3 == 2) {
    		locValue += 2;
    		Robby.moveCMForward(4, 100);
    	}
		if(locValue % 3 == 1) {
			locValue += 1;
			Robby.moveCMForward(2, 100);
		}*/

    	if(locValue > 19){
    		int subt = locValue - 19;
    		locValue -= subt;
    		Robby.moveCMBackward(subt*2,100);
    	}
    	else if(locValue < 19){
    		int add = 19 - locValue;
    		locValue += add;
    		Robby.moveCMForward(add*2,100);
    	}
		int finY = locValue / 3;
		int finX = 12;
		return new Coordinate(finX,finY);
    }

	 //should return a float array with 3 values (rgb)
	static float[] getSample(){ //Gets samples
		float[] sample = new float [sampleSize];
		sampleProvider.fetchSample(sample,  0);
		float[] rgb = {sample[0], sample[1], sample[2]};
		return rgb;
	}
	
	//if true blue if false white.
	static boolean blueOrWhite(){
		//calls getSample to get the sample.. duh`
		float[] sample = getSample();
		if (sample[2] < .1 && sample[2] > .03){
			return true;
		}
		else
		return false;
		
	}
	
	//finds a value in the probDistHash that is greater than .7 so should theoretically know where it is.
	static Double checkDist(){
		for(int i = 0; i < probDistHash.size(); i ++){
			if(probDistHash.get(i) >= threshold){
				return probDistHash.get(i);
			}
		}
		return 0.0;
	}
	
	static int getKey(){
			for(int i = 0; i < probDistHash.size(); i ++){
				if(probDistHash.get(i) >= threshold){
					//double check that i-1 is correct...
					System.out.println(i);
					Delay.msDelay(5000);
					return i;
				}
			}
			return -1;
	}
	
	static void Initialization(){
		//set up prob distribution
//		for(int i = 0; i < pDistr.length; i ++){
//			pDistr[i] = pinit;
//		}	
		//initialize the HashMap array.
		probDistHash = new HashMap<Integer,Double>(colors.length); 
		for(int i = 0; i < colors.length; i++){
			probDistHash.put(i, pinit);
		}
		
	}
	


	
	static int Localization(){
		//NEED A while loop that stops when a prob has gotten to a certain percent...
		Initialization();
		
		while(checkDist() <= threshold){
			
			
			//get currvalue was here but moving it to fit the test code.
			
			totalProb = 0;

			boolean currentVal = blueOrWhite();
			System.out.println(currentVal);

			//had normalization here but will move it to fit the quiz code
			
			for(int i = 0; i < colors.length; i++){
				
				//fetches a sample currentVal which is either blue: true or white: false
				
				if (currentVal == colors[i]){
					//maybe i shouldnt have the norm value here. trying to make a new normalize method
					probDistHash.put(i, sensor_right*probDistHash.get(i));
				}
				else{
					probDistHash.put(i, sensor_wrong*probDistHash.get(i));
				}
			}
			
			//calculating the normalization value
			//probsum
			totalProb = 0;
			for(int i = 0; i < probDistHash.size(); i++){
				totalProb += probDistHash.get(i);
			}
			norm = 1/totalProb;
			
			for (int i = 0; i< colors.length; i++){probDistHash.put(i,probDistHash.get(i)*norm);}
			
			MoveAndUpdate();
			
			//calculating the normalization value
			//probsum
			totalProb = 0;
			for(int i = 0; i < probDistHash.size(); i++){
				totalProb += probDistHash.get(i);
			}
			norm = 1/totalProb;
			
			for (int i = 0; i< colors.length; i++){probDistHash.put(i,probDistHash.get(i)*norm);}
			
		}
//		LCD.drawInt(getKey(), 1, 1);
		Delay.msDelay(1000);
		return getKey(); 
		//you want to return the value from the hashmap that has the greatest probability inside of it... 
		//so in your array hashmap if [1:0.4, 2:0.8] <-- you return the second group of values and take the index which indicates the position you are at in your array which demonstrates the position on the board..
				}
	
	static void MoveAndUpdate(){
//		moveTwo();
		Robby.moveCMForward(2, 100);
		Delay.msDelay(1000);
		double[] temp = new double[probDistHash.size()];
		
		for(int i = 0; i < colors.length; i++){
			temp[i] = probDistHash.get(i);
		}
		
		for(int i = 0; i < colors.length; i++){
			//is this the correct update for after a move?
			if(i == 0){
				probDistHash.put(i, p_stay*temp[i]);	

			}
			else{
				probDistHash.put(i, p_move*temp[i-1] + p_stay*temp[i]);	
			}
		}	
	}
		

	public static int startL() {
		colorSensor.getRGBMode();
		return Localization();

	}
	
	
	
}