package navigateBot;

import java.util.ArrayList;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Robby {

	// 125*125
	//10 blocks along the local path, 14 from the corner

	// Motor setup
	static BaseRegulatedMotor leftMD = new EV3LargeRegulatedMotor(MotorPort.D);
	static BaseRegulatedMotor rightMA = new EV3LargeRegulatedMotor(MotorPort.C);

	// Button setup
	Port buttPort = SensorPort.S4;
	EV3TouchSensor button = new EV3TouchSensor(buttPort);
	SampleProvider BsampleProvider = button.getTouchMode();
	int BsampleSize = BsampleProvider.sampleSize();

	// Gyro setup
	Port gyroPort = SensorPort.S3;
	EV3GyroSensor gyro = new EV3GyroSensor(gyroPort);
	SampleProvider GsampleProvider = gyro.getAngleMode();
	int GsampleSize = GsampleProvider.sampleSize();

	// Ultrasound setup
	Port ultraPort = SensorPort.S2;
	EV3UltrasonicSensor sonic = new EV3UltrasonicSensor(ultraPort);
	SampleProvider USampleProv = sonic.getDistanceMode();
	int UsampleSize = USampleProv.sampleSize();

	public Robby() {
		rightMA.synchronizeWith(new BaseRegulatedMotor[] { leftMD }); // Not needed anymore but left in for future use after this project
		gyro.reset();
	}

	// Gets gyro samples
	public float[] getGsample() {
		float[] sample = new float[GsampleSize];
		GsampleProvider.fetchSample(sample, 0);
		return sample;
	}

	// Gets ultrasound samples
	public float[] getUsample() {
		float[] sample = new float[UsampleSize];
		USampleProv.fetchSample(sample, 0);
		return sample;
	}
	
	public float[] getBsample(){
		float[] sample = new float[BsampleSize];
		BsampleProvider.fetchSample(sample, 0);
		return sample;
	}
	
	public float[] enterGoal(){
		//Set speed here so that you don't have to type it twice if we need to change it
		int speedInit = 250;
		moveCMForward(2, 100);
		//TODO Add turn to angle here.
		turnTo(45);
		leftMD.setSpeed(speedInit);
		rightMA.setSpeed(speedInit);
		while(true){
			float[] ultraRead = getUsample();
			float[] buttPress = getBsample();
			leftMD.forward();
			rightMA.forward();
			//Reduce speed so it doesn't smash into the back of the goal; moving the goal or breaking the robot. 
			if (ultraRead[ultraRead.length -1] < 0.07){
				leftMD.setSpeed(speedInit/5);
				rightMA.setSpeed(speedInit/5);
			}
			if (buttPress[buttPress.length -1] == 1){
				Sound.beep();
				//get colour sensor reading
				leftMD.stop();
				rightMA.stop();
				float[] rgb = localization.getSample();
				
				return rgb;
			}
		}
	}
	
	public void exixG(){
		moveCMBackward(36, 250);
	}

	// Takes the distance needed to travel and based on the wheel size works how
	// far it has to rotate the wheels
	public static void moveCMForward(double cm, int speed) {
		double dist = cm;
		double circum = 3.14 * 5.6;
		double rotation = dist / circum;
		double degrees = rotation * 360;

		int delay = (int) (140 * cm);
		leftMD.setSpeed(speed);
		rightMA.setSpeed(speed);
		leftMD.rotate((int) degrees, true);
		rightMA.rotate((int) degrees, true);
		Delay.msDelay(delay);
	}

	// Same as above but for reverse
	public void moveCMBackward(double cm, int speed) {
		double dist = cm;
		double circum = 3.14 * 5.6;
		double rotation = dist / circum;
		double degrees = rotation * 360;

		double toNegate = degrees * 2;
		double negative = degrees - toNegate;

		int delay = (int) (140 * cm);
		leftMD.setSpeed(speed);
		rightMA.setSpeed(speed);
		leftMD.rotate((int) negative, true);
		rightMA.rotate((int) negative, true);
		Delay.msDelay(delay);
	}

	// Turns to the desired angle using a proportional controller
	public void turnTo(double angle) {
		int KP = 200;

		int TP = 0;
		while (true) {

			float[] reading = getGsample();
			float error = (float) (angle - reading[reading.length - 1]);

			float turn = KP * error;
			turn = turn / 100;

			float powerR = (TP + turn);
			float powerL = (TP - turn);

			if (powerR > 0) {
				rightMA.setSpeed(Math.abs(powerR));
				rightMA.backward();
			}

			else if (powerR < 0) {
				rightMA.setSpeed(powerR);
				rightMA.forward();
			}

			if (powerL > 0) {
				leftMD.setSpeed(Math.abs(powerR));
				leftMD.backward();
				
			}

			else if (powerL < 0) {
				leftMD.setSpeed(powerL);
				leftMD.forward();
			}

			if (error < 1 && error > -1) {
				leftMD.stop();
				rightMA.stop();
				break;
			}
		}
	}
	
	public ArrayList<Coordinate> optimisePath(ArrayList<Coordinate> path){
		ArrayList<Coordinate> optimized = new ArrayList<Coordinate>();
		for (int i = 0; i < path.size(); ++i){
			optimized.add(path.get(i));
			if(optimized.size() >= 3){
				int size = optimized.size();
				Coordinate three = optimized.get(size -1);
				Coordinate two = optimized.get(size -2);
				Coordinate one = optimized.get(size -3);
				
				int sum1 = one.getX()*(two.getY() - three.getY());
				int sum2 = two.getX()*(three.getY() - one.getY());
				int sum3 = three.getX()*(one.getY() - two.getY());
				
				int colinear = sum1 + sum2 + sum3;
				if(colinear == 0){
					optimized.remove(size -2);
				}
			}
		}
		return optimized;
	}

	// Takes a path and calls the trig function passing in the current position
	// and the position to move to.
	public void followPathTrig(ArrayList<Coordinate> p) {
		for (int i = 0; i < p.size() - 1; ++i) {
			
			Coordinate curr = p.get(i);
			Coordinate next = p.get(i + 1);
			
			if(curr.getX() == next.getX()){
				turnTo(0);
			}
			trig(curr, next, 6);
		}
	}

	// Works out the angle and distance needed using trig then calls the move
	// and turn functions
	public void trig(Coordinate s, Coordinate g, int cm) {

		double xDiff = g.getX() - s.getX();
		double yDiff = g.getY() - s.getY();

		xDiff *= cm;
		yDiff *= cm;

		double xSquare = xDiff * xDiff;
		double ySquare = yDiff * yDiff;

		double lSquare = xSquare + ySquare;
		double Hyp = Math.sqrt(lSquare);

		double angle = Math.atan2(xDiff, yDiff);
		angle = angle * 180 / 3.14159;
		
		//Prints the angle to screen
		/*StringBuffer sb = new StringBuffer(16);
		sb.append("V: ");
		sb.append(angle);
		LCD.drawString(sb.toString(), 1, 1);
		System.out.println(angle);*/
		
		if ((int) angle == 180.00 || (int) angle == -180.00) {
			moveCMBackward(Hyp, 250);
		} else {
			turnTo(angle);
		}
		
		Delay.msDelay(300);
		
		moveCMForward(Hyp, 250);

	}
}