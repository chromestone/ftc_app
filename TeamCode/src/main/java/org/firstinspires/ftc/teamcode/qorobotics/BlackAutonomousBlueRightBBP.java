package org.firstinspires.ftc.teamcode.qorobotics;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;

/**
 * QUALIFIER 2
 *
 * BBP - Ball, Beacon & Park center
 *
 * Note: ---->UNTESTED<---- this is as untested as the qualifier 1 autonomous
 *
 * Created by Derek Zhang on 12/24/16.
 */

@Autonomous(name = "Auto Blue Right BP", group = "Black")
@Disabled
public class BlackAutonomousBlueRightBBP extends LinearOpMode {

    private static final double MAX_LEFT_SPEED = 1.0;
    private static final double MAX_RIGHT_SPEED = 1.0;

    //approx velocity in feet per second
    //TO DEREK AND WHOM IT MAY CONCERN: DO NOT TOUCH THIS
    //INSTEAD ADJUST TIMING ---> INDEPENDENTLY <-- FOR EACH STEP
    private static final double APPROX_VEL = 2.4;

    //time for whole robot to turn 45 degrees at HALF max speed
    private static final double TURN_45_TIME = 0.35;

    ////////////////////////////////

    private DcMotor frontLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor backRightMotor = null;

    @Override
    public void runOpMode() throws InterruptedException {

        frontLeftMotor = hardwareMap.dcMotor.get("mrFL");
        frontRightMotor = hardwareMap.dcMotor.get("mrFR");
        backLeftMotor = hardwareMap.dcMotor.get("mrBL");
        backRightMotor = hardwareMap.dcMotor.get("mrBR");

        CRServo crServo = hardwareMap.crservo.get("crso");

        ColorSensor colorSensor = hardwareMap.colorSensor.get("cs");

        ////////////////////////////////

        frontLeftMotor.resetDeviceConfigurationForOpMode();
        frontRightMotor.resetDeviceConfigurationForOpMode();
        backLeftMotor.resetDeviceConfigurationForOpMode();
        backRightMotor.resetDeviceConfigurationForOpMode();

        crServo.resetDeviceConfigurationForOpMode();

        colorSensor.resetDeviceConfigurationForOpMode();

        ////////////////////////////////

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        ////////////////////////////////

        waitForStart();

        ////////////////////////////////

        //move forward to ram ball
        //distance is approx: 7ft
        runMotor(MAX_LEFT_SPEED, MAX_RIGHT_SPEED, (7.0 / APPROX_VEL));

        //give robot time to stop
        wait(0.2);

        //REVERSE THRUSTERS!!! align so that next turn will align left side of robot to left beacon button
        //distance is very small
        runMotor(-MAX_LEFT_SPEED, -MAX_RIGHT_SPEED, ((7 - 6.0625 + 0.429) / APPROX_VEL));

        wait(0.2);

        //turn 90 degrees right
        runMotor(MAX_LEFT_SPEED / 2.0, -MAX_RIGHT_SPEED / 2.0, TURN_45_TIME * 2.0);

        wait(0.2);

        //move forward until robot is approx 2 inches or so away from the beacon
        //distance is approx: what I said in the code; too lazy to calculate
        runMotor(MAX_LEFT_SPEED, MAX_RIGHT_SPEED, (((2.0 - 1.5 + .824) + (4.0 - 1.0 / 6.0)) / APPROX_VEL));

        //FULL STOP! (not necessary but for safety)
        setMotorPower(0.0, 0.0);

        ////////////////////////////////
        //NOTE: A sweep from LEFT TO RIGHT should be done
        //scans a beacon with a moving servo; sets a decision (ColorState)

        ColorState state = ColorState.NA;

        float hsvValues[] = {0F,0F,0F};

        double runtime = 0.5 / (52.0 / 60.0);//rotate 180 degrees which is half a revolution given servo is @ 52 RPM

        crServo.setPower(1.0);

        resetStartTime();
        while (opModeIsActive() && (getRuntime() < runtime)) {

            Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

            float hue = hsvValues[0];

            //I'm using indirect logic; I'm paranoid that the beacon may have been pressed
            //I mean in my defense you never know right? like lets say robots crash and BOOM
            //button pressed and now its already blue; and then you press it and boom its red
            //which means you did someting wong
            //red
            //assume 0 <= hue <= 360
            if (hue <= 25 || hue >= 335) {

                //rotated more than 90 degrees
                if (getRuntime() >= runtime / 2.0) {

                    //blue button must be on the left
                    state = ColorState.LEFT;
                }
                else {

                    //blue button must on the right
                    state = ColorState.RIGHT;
                }

                break;
            }

            idle();
        }

        crServo.setPower(0.0);

        ////////////////////////////////

        if (state == ColorState.LEFT) {


            //RAM THE BEACON!!! but don't overdo it
            runMotor(MAX_LEFT_SPEED, MAX_RIGHT_SPEED, 0.20);

            wait(0.2);

            //REVERSE THRUSTERS!!! park on the center tile
            runMotor(-MAX_LEFT_SPEED, -MAX_RIGHT_SPEED, (5.5 / APPROX_VEL));

            //for safety
            setMotorPower(0.0, 0.0);
        }
        else if (state == ColorState.RIGHT) {

            //go backward one foot to allow room for turning maneuvers
            //distance is approx: 1ft
            runMotor(-MAX_LEFT_SPEED, -MAX_RIGHT_SPEED, (1.0 / APPROX_VEL));

            wait(0.2);

            //turn 90 degrees right
            runMotor(MAX_LEFT_SPEED / 2.0, -MAX_RIGHT_SPEED / 2.0, TURN_45_TIME * 2.0);

            wait(0.2);

            //move forward so next turn aligns beacon presser to right button
            //distance is approx: 0.224ft
            runMotor(MAX_LEFT_SPEED, MAX_RIGHT_SPEED, (0.224 / APPROX_VEL));//((1.0 - 0.688) / APPROX_VEL));

            wait(0.2);

            //turn 90 degrees left
            runMotor(-MAX_LEFT_SPEED / 2.0, MAX_RIGHT_SPEED / 2.0, TURN_45_TIME * 2.0);

            wait(0.2);

            //RAM THE BEACON!!! but don't overdo it
            runMotor(MAX_LEFT_SPEED, MAX_RIGHT_SPEED, 0.20);

            wait(0.2);

            //REVERSE THRUSTERS!!! give some room to adjust angle slightly for parking on center
            runMotor(-MAX_LEFT_SPEED, -MAX_RIGHT_SPEED, (1 / APPROX_VEL));

            wait(0.2);

            //slight turn ahead (behind)
            runMotor(MAX_LEFT_SPEED / 2.0, -MAX_LEFT_SPEED / 2.0, 0.05);

            wait(0.2);

            //REVERSE THRUSTERS!!! go park on center
            runMotor(-MAX_LEFT_SPEED, -MAX_RIGHT_SPEED, (4.5 / APPROX_VEL));

            //for safety
            setMotorPower(0.0, 0.0);
        }

        ////////////////////////////////

        setMotorPower(0.0, 0.0);
    }

    private void setMotorPower(double leftPower, double rightPower) {

        frontLeftMotor.setPower(leftPower);
        backLeftMotor.setPower(leftPower);

        frontRightMotor.setPower(rightPower);
        backRightMotor.setPower(rightPower);
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
