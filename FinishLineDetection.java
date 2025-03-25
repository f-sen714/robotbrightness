import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

class FinishLineDetection implements Behavior {
    private EV3ColorSensor colorSensor;
    private MovePilot pilot;
    private long startTime;
    
    public FinishLineDetection(EV3ColorSensor colorSensor, MovePilot pilot, long startTime) {
        this.colorSensor = colorSensor;
        this.pilot = pilot;
        this.startTime = startTime;
    }

    public boolean takeControl() {
        float[] sample = new float[1];
        colorSensor.getRedMode().fetchSample(sample, 0);
        return sample[0] < 0.05; 
    }

    public void action() {
        pilot.stop();
        long endTime = System.currentTimeMillis();
        long elapsedTime = (endTime - startTime) / 1000; // convert to seconds

        LCD.clear();
        LCD.drawString("Finish Line!", 0, 2);
        LCD.drawString("Time: " + elapsedTime + "s", 0, 3);
        lejos.hardware.Button.waitForAnyPress(); //waits for restart
    }

    public void suppress() {
        //dont need suppress
    }
}