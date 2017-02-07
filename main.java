package navigateBot;

import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class main {
	
	//Mptor setup
	BaseRegulatedMotor leftMD = new EV3LargeRegulatedMotor(MotorPort.D);
	BaseRegulatedMotor rightMA = new EV3LargeRegulatedMotor(MotorPort.C);
	
	//Test alt sensor
	
	//Setup Colour sensor
	static Port colorSensorPort = SensorPort.S1;
	static EV3ColorSensor colorSensor = new EV3ColorSensor(colorSensorPort);
	SampleProvider sampleProvider = new EV3ColorSensor(colorSensorPort);
	int sampleSize = sampleProvider.sampleSize();
	
	public static void main(String[] args) {
		colorSensor.getRGBMode();
	}
	
	void moveTwo(){ //Moves robot 2cm
		leftMD.setSpeed(110);
		rightMA.setSpeed(110);
		leftMD.rotate(41, true);
		rightMA.rotate(41, true);
		Delay.msDelay(500);
	}
	
	float[] getSample(){ //Gets samples
		float[] sample = new float[sampleSize];
		sampleProvider.fetchSample(sample,  0);
		return sample;
	}
}
