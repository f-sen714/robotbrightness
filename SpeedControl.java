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

        while (!suppressed) { // continuously check brightness
            colorSensor.getRedMode().fetchSample(sample, 0);
            
            // darkness exits speedcontrol
            if (sample[0] < 0.05) { 
                return;
            }

            if (sample[0] > 0.5) { //adjust for fast speed
                pilot.setLinearSpeed(20); 
            } else { 
                pilot.setLinearSpeed(10);
            }
            try {
                Thread.sleep(100); // delay
            } catch (InterruptedException e) {
            	Thread.currentThread().interrupt();
            }
        }
    }

    public void suppress() {
        suppressed = true; 
    }
}
