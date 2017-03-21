package navigateBot;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

/* A bunch of functions that I wrote in the early stages of testing the robot.
 * I have dumped them here for future reference.
 * Many of these functions are primitive and not very accurate, the ones inside the Robby class
 * use the more accurate functions.
 * All of these are written to minimise imports.
 */

public final class Utils {
	
	//Setup Colour sensor, future ref only.
	Port colorSensorPort = SensorPort.S1;
	EV3ColorSensor colorSensor = new EV3ColorSensor(colorSensorPort);
	SampleProvider CsampleProvider = colorSensor.getMode("RGB"); //new EV3ColorSensor(colorSensorPort);
	int CsampleSize = CsampleProvider.sampleSize();
	
	//Gets samples for colour sensor
	public float[] getCSample(){ 
		float[] sample = new float[CsampleSize];
		CsampleProvider.fetchSample(sample,  0);
		return sample;
	}
	
	//Moves robot 2cm
	public void moveTwo(){ 
//		gyro.getAngleMode();
		Motor.D.setSpeed(110);
		Motor.C.setSpeed(110);
		Motor.D.rotate(41, true);
		Motor.C.rotate(41, true);
		Delay.msDelay(500);
	}
	
	//90 degrees right
	public static void rotateNineClockwise(){
		Motor.D.setSpeed(110);
		Motor.C.setSpeed(110);
		Motor.D.rotate(180, true);
		Motor.C.rotate(-180, true);
		Delay.msDelay(3000);
	}
	
	//90 degrees left
	public static void rotateNineAntiC(){
		Motor.D.setSpeed(110);
		Motor.C.setSpeed(110);
		Motor.D.rotate(-180, true);
		Motor.C.rotate(180, true);
		Delay.msDelay(3000);
	}
	
	//A space is 6cm changed to do the math in Robby class
	public static void moveXForward(int spaces){
		//41 is 2cm
		spaces *= 123;
		int delay = 200 * spaces;
		Motor.D.setSpeed(110);
		Motor.C.setSpeed(110);
		Motor.D.rotate(spaces, true);
		Motor.C.rotate(spaces, true);
		Delay.msDelay(delay);
	}
	
	//This is here for use in the main if needed as I don't import it in main.
	public static void delay(int time){
		Delay.msDelay(time);
	}
	
	public static ArrayList<Coordinate> getPath(TreeNode[] p){
		ArrayList<Coordinate> path = new ArrayList<Coordinate>();
		for(int i = 0; i < p.length; ++i){
			path.add(((Coordinate)((DefaultMutableTreeNode) p[i]).getUserObject()));
		}
		/*for(int i = 0; i < path.size(); ++i){
			System.out.println(path.get(i).x + " " + path.get(i).y);
		}*/
		return path;
	}

}
