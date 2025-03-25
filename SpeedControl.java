package maybewehavecancer;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class SpeedControl implements Behavior {
	private EV3ColorSensor colorSensor;
	private MovePilot pilot;
	private boolean suppressed = false;
	
    public SpeedControl(EV3ColorSensor colorSensor, MovePilot pilot) {
        this.colorSensor = colorSensor;
        this.pilot = pilot;
    }
    
    public boolean takeControl() {
        return true; 
    }

    public void action() {
        suppressed = false;
        float[] sample = new float[1];

        while (!suppressed) {
            colorSensor.getRedMode().fetchSample(sample, 0);
            if (sample[0] > 0.3) {
                pilot.setLinearSpeed(20);
            } else {
                pilot.setLinearSpeed(5); 
            }
            try {
                Thread.sleep(100); // Small delay
            } catch (InterruptedException e) {
            	Thread.currentThread().interrupt();
            }
        }
    }

    public void suppress() {
        suppressed = true; 
    }
}
