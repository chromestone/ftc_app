package org.firstinspires.ftc.teamcode.qorobotics;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Base code for autonomous in Qualifier 2
 *
 * don't use this, really lol
 *
 * this code doesn't do what I meant for it to do. however I'm including it for no reason
 * other than future insight?
 *
 * Created by Derek Zhang on 1/19/17.
 */

public class DoNotUse extends LinearOpMode {

    //perpendicular to forward drive direction
    private static final double MAX_PERP_FORWARD_SPEED = 1.0;//technically "strafe"
    private static final double MAX_PARA_FORWARD_SPEED = 1.0;//technically forward and back

    //approx velocity in feet per second
    //TO DEREK AND WHOM IT MAY CONCERN: DO NOT TOUCH THIS
    //INSTEAD ADJUST TIMING ---> INDEPENDENTLY <-- FOR EACH STEP
    /*
    there are two velocities because the speed forward is 2 motors and
    can be generalized as <1, 0>
    by scaling down the speed by a constant K (the actual velocity)
    speed left and right must also have a magnitude of one
    speed diagonal has all for motors spinning. since the two pairs
    of motors are orthogonal and independent
    diagonal velocity is <1, 1> with a magnitude of root two
    now multiply that constant K back to the magnitude
    -I have no clue what I wrote-
     */
    private static final double SLIDE_VEL = 2.22;
    private static final double DIAGONAL_VEL = SLIDE_VEL * Math.pow(2.0, 0.5);

    //time to turn 45 degrees at HALF max speed
    private static final double TURN_45_TIME = 0.26;

    private DcMotor frontMotor = null;
    private DcMotor rightMotor = null;
    private DcMotor leftMotor = null;
    private DcMotor backMotor = null;

    @Override
    public void runOpMode() throws InterruptedException {

        frontMotor = hardwareMap.dcMotor.get("mrF");
        rightMotor = hardwareMap.dcMotor.get("mrR");
        leftMotor = hardwareMap.dcMotor.get("mrL");
        backMotor = hardwareMap.dcMotor.get("mrB");

        ColorSensor colorSensor = hardwareMap.colorSensor.get("cs");

        ////////////////////////////////

        frontMotor.resetDeviceConfigurationForOpMode();
        rightMotor.resetDeviceConfigurationForOpMode();
        leftMotor.resetDeviceConfigurationForOpMode();
        backMotor.resetDeviceConfigurationForOpMode();

        ////////////////////////////////

        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        colorSensor.enableLed(false);

        ////////////////////////////////

        waitForStart();

        ////////////////////////////////

        //velocity <0, max>
        //move forward yeah
        runMotor(0.0, MAX_PARA_FORWARD_SPEED, (0.327 / SLIDE_VEL));

        wait(0.2);

        //move to first beacon
        runMotor(MAX_PERP_FORWARD_SPEED, MAX_PARA_FORWARD_SPEED, (2.0 / DIAGONAL_VEL));

        wait(0.2);

        frontMotor.setPower(-1.0);
        backMotor.setPower(1.0);

        //
        leftMotor.setPower(1.0);
        rightMotor.setPower(-1.0);

        wait(TURN_45_TIME * 2.0 );

        setMotorPower(0.0, 0.0);

        wait(0.2);

        runMotor(MAX_PERP_FORWARD_SPEED, MAX_PARA_FORWARD_SPEED, (2.0 / DIAGONAL_VEL));

        wait(0.2);

        float hsvValues[] = {0F,0F,0F};

        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

        float hue = hsvValues[0];

        //blue
        if (hue >= 215F && hue <= 265F) {

            //RAM THE BEACON!!! but don't overdo it
            runMotor(MAX_PERP_FORWARD_SPEED, 0.0, 0.2);

            wait(0.2);

            //step back
            runMotor(-MAX_PERP_FORWARD_SPEED, 0.0, 0.15);

            wait(0.2);

            //move to next beacon
            runMotor(0.0, MAX_PARA_FORWARD_SPEED, (3.96 / SLIDE_VEL));
        }
        else {

            //move to other side of beacon
            runMotor(0.0, MAX_PARA_FORWARD_SPEED, (0.22 / SLIDE_VEL));

            if (hue >= 215F && hue <= 265F) {

                //RAM THE BEACON!!! but don't overdo it
                runMotor(MAX_PERP_FORWARD_SPEED, 0.0, 0.2);

                wait(0.2);

                //step back
                runMotor(-MAX_PERP_FORWARD_SPEED, 0.0, 0.15);

                wait(0.2);

                //move to next beacon
                runMotor(0.0, MAX_PARA_FORWARD_SPEED, ((3.96 - 0.22) / SLIDE_VEL));
            }
            else {

                //move to next beacon
                runMotor(0.0, MAX_PARA_FORWARD_SPEED, (3.96 / SLIDE_VEL));
            }
        }

        ////////////////////////////////
        //second beacon

        wait(0.2);

        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

        hue = hsvValues[0];

        //blue
        if (hue >= 215F && hue <= 265F) {

            //RAM THE BEACON!!! but don't overdo it
            runMotor(MAX_PERP_FORWARD_SPEED, 0.0, 0.2);

            wait(0.2);

            //go backward to center
            runMotor(-MAX_PERP_FORWARD_SPEED, -MAX_PARA_FORWARD_SPEED, (6.43 / DIAGONAL_VEL));
        }
        else {

            //move to other side of beacon
            runMotor(0.0, MAX_PARA_FORWARD_SPEED, (0.22 / SLIDE_VEL));

            if (hue >= 215F && hue <= 265F) {

                //RAM THE BEACON!!! but don't overdo it
                runMotor(MAX_PERP_FORWARD_SPEED, 0.0, 0.2);

                wait(0.2);

                //step back
                runMotor(-MAX_PERP_FORWARD_SPEED, 0.0, 0.15);

                wait(0.2);

                //go backward to center
                runMotor(-MAX_PERP_FORWARD_SPEED, -MAX_PARA_FORWARD_SPEED, (6.43 / DIAGONAL_VEL));
            }
            else {

                //go backward to center
                runMotor(-MAX_PERP_FORWARD_SPEED, -MAX_PARA_FORWARD_SPEED, (6.43 / DIAGONAL_VEL));
            }
        }

        ////////////////////////////////

        setMotorPower(0.0, 0.0);
    }

    private void setMotorPower(double perpendicularForwardPower, double parallelForwardPower) {

        frontMotor.setPower(perpendicularForwardPower);
        backMotor.setPower(perpendicularForwardPower);

        leftMotor.setPower(parallelForwardPower);
        rightMotor.setPower(parallelForwardPower);
    }

    private void wait(double time) throws InterruptedException {

        resetStartTime();
        while (opModeIsActive() && (getRuntime() < time)) {

            //idle();
        }
    }

    private void runMotor(double leftPower, double rightPower, double time) throws InterruptedException {

        setMotorPower(leftPower, rightPower);

        wait(time);

        setMotorPower(0.0, 0.0);
    }
}
