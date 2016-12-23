package org.firstinspires.ftc.teamcode.qorobotics.test;

import android.content.Context;
import android.hardware.*;

import com.qualcomm.robotcore.eventloop.opmode.*;

import org.firstinspires.ftc.teamcode.qorobotics.util.Ticker;

/**
 * Displays the accelerations of robot controller on the driver station
 *
 * Created by Derek Zhang on 11/12/16.
 */
@Autonomous(name = "Accel Test", group = "Test")
@Disabled
public class AccelerometerTest extends LinearOpMode implements SensorEventListener {

    private final Object lock = new Object();

    //private long lastUpdate = 0L;

    private float[] accelerations = null;

    @Override
    public void runOpMode() throws InterruptedException {

        //lastUpdate = System.currentTimeMillis();

        //initialization is good habit
        accelerations = new float[]{-1F, -1F, -1F};


        Context context = hardwareMap.appContext;

        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL, 10 * 1000);

        waitForStart();

        Ticker ticker = new Ticker();

        while (opModeIsActive()) {

            synchronized (lock) {

                telemetry.addData("X", accelerations[0]);
                telemetry.addData("Y", accelerations[1]);
                telemetry.addData("Z", accelerations[2]);
            }

            telemetry.update();


            ticker.waitForTick(40L);

            idle();
        }
        
        sensorManager.flush(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        /*Sensor sensor = event.sensor;

        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            long curTime = System.nanoTime() / 1000000;

            if ((curTime - lastUpdate) > 10) {

                //long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;*/

                //accelerations = event.values;

                synchronized (lock) {

                    System.arraycopy(event.values, 0, accelerations, 0, 3);
                }
            /*}
        }*/
    }

    /*
        //output is actually the "old" value
    private static float lowPass(float input, float output) {

        //return alpha * output + (1 - alpha) * input;
        return output + ALPHA * (input - output);
    }
     */

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
/*
    private class Pair {

        private float prevNum;
        private float nextNum;
        private boolean prevCompleted;
        private boolean nextCompleted;

        public Pair(){

            this.prevNum = 0F;
            nextNum = 0F;
            prevCompleted = false;
            nextCompleted = false;
        }

        public void reset() {

            prevCompleted = false;
            nextCompleted = false;
        }

        public boolean isValid() {

            return prevCompleted && nextCompleted;
        }

        public void setPrevNum(int prevNum) {

            this.prevNum = prevNum;
            prevCompleted = true;
        }

        public void setNextNum(int nextNum) {

            this.nextNum = nextNum;
            nextCompleted = true;
        }
    }
    */
}
