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
	
	//Mptor setup
	static BaseRegulatedMotor leftMD = new EV3LargeRegulatedMotor(MotorPort.D);
	static BaseRegulatedMotor rightMA = new EV3LargeRegulatedMotor(MotorPort.C);
	
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
		
	static void moveTwo(){ //Moves robot 2cm
		leftMD.setSpeed(110);
		rightMA.setSpeed(110);
		leftMD.rotate(41, true);
		rightMA.rotate(41, true);
		Delay.msDelay(900);
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
			if(probDistHash.get(i) >= .95){
				return probDistHash.get(i);
		}
		}
		return 0.0;
	}
	static int getKey(){
			for(int i = 0; i < probDistHash.size(); i ++){
				if(probDistHash.get(i) >= .95){
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
	
	static void MoveAndUpdate(){
		
		moveTwo();
		//i don't think the blueorWhite method will work right.
		for(int i = 1; i < colors.length; i++){
			
			//is this the correct update for after a move?
				probDistHash.put(i, p_move*norm*probDistHash.get(i-1) + p_stay*p_move*norm*probDistHash.get(i));
				
		}	
	}

	
	static int Localization(){
		//NEED A while loop that stops when a prob has gotten to a certain percent...
		Initialization();
		while(checkDist() <= .95){
			//fetches a sample currentVal which is either blue: true or white: false
			boolean currentVal = blueOrWhite();
			//just checking the current val.
			
			StringBuffer sb = new StringBuffer(16);
			//this append doesnt seem to be working.
			sb.append("firstProb: ");
//			for(int i = 0; i < colors.length; i ++){
//				sb.append(probDistHash.get(i));
//				sb.append(", ");
//			}
			//checks the prob that its at the 4th blue. (only one spot where 4 blue)
			sb.append(30);
			LCD.drawString(sb.toString(),1,1);
			//calculating the normalization value
			for(int i = 0; i < probDistHash.size(); i++){
				totalProb += probDistHash.get(i);
			}
			norm = 1/totalProb;
			totalProb = 0;
			
			for(int i = 0; i < colors.length; i++){
				if (currentVal == colors[i]){
					probDistHash.put(i, norm*sensor_right*probDistHash.get(i));
				}
				else{
					probDistHash.put(i, norm*sensor_wrong*probDistHash.get(i));
				}
			}
			
			//calculating the normalization value
			for(int i = 0; i < probDistHash.size(); i++){
				totalProb += probDistHash.get(i);
			}
			norm = 1/totalProb;
			totalProb = 0;
			MoveAndUpdate();
		}
		LCD.drawInt(getKey(), 1, 1);
		return getKey(); 
		//you want to return the value from the hashmap that has the greatest probability inside of it... 
		//so in your array hashmap if [1:0.4, 2:0.8] <-- you return the second group of values and take the index which indicates the position you are at in your array which demonstrates the position on the board..
				}
		

	public static void startL() {
		colorSensor.getRGBMode();
//		moveTwo();
		Localization();

	}
	
	
	
}