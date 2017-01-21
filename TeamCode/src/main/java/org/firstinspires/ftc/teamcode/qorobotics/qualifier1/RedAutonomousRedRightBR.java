package org.firstinspires.ftc.teamcode.qorobotics.qualifier1;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * QUALIFIER 1 (very not refined)
 *
 * Ball Ramp (BR)
 *
 * Created by Derek Zhang on 12/9/16.
 */

@Autonomous(name = "Auto Red Right BR", group = "Red")
@Disabled
public class RedAutonomousRedRightBR extends LinearOpMode {

    private static final double MAX_LEFT_SPEED = 1.0;
    private static final double MAX_RIGHT_SPEED = 1.0;

    //approx velocity in feet per second
    //TO DEREK AND WHOM IT MAY CONCERN: DO NOT TOUCH THIS
    //INSTEAD ADJUST TIMING ---> INDEPENDENTLY <-- FOR EACH STEP
    private static final double APPROX_VEL = 2.4;

    //time to turn 45 degrees at HALF max speed
    private static final double TURN_45_TIME = 0.35;

    ////////////////////////////////

    private DcMotor backLeftMotor = null;
    private DcMotor backRightMotor = null;

    @Override
    public void runOpMode() throws InterruptedException {

        backLeftMotor = hardwareMap.dcMotor.get("mrBL");
        backRightMotor = hardwareMap.dcMotor.get("mrBR");

        ////////////////////////////////

        backLeftMotor.resetDeviceConfigurationForOpMode();
        backRightMotor.resetDeviceConfigurationForOpMode();

        ////////////////////////////////

        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //crServo.setDirection(DcMotorSimple.Direction.REVERSE);

        ////////////////////////////////

        waitForStart();

        ////////////////////////////////

        //move forward until front wheels touch front edge of second tile
        //distance is approx: 2ft + unknown dist
        runMotor(MAX_LEFT_SPEED, MAX_RIGHT_SPEED, (2 / APPROX_VEL) + 0.035);//0.875);//1.75);

        //give robot time to stop
        runMotor(0.0, 0.0, 0.2);

        //turn 45 degrees left
        runMotor(-MAX_LEFT_SPEED / 2.0, MAX_RIGHT_SPEED / 2.0, TURN_45_TIME);//0.7);//0.35);

        runMotor(0.0, 0.0, 0.2);

        //forward ram ball
        //distance is approx: 5.71ft
        runMotor(MAX_LEFT_SPEED, MAX_RIGHT_SPEED, (5.71 / APPROX_VEL));

        runMotor(0.0, 0.0, 0.2);

        //turn 90 degrees left + additional 20 degrees
        runMotor(-MAX_LEFT_SPEED / 2.0, MAX_RIGHT_SPEED / 2.0, TURN_45_TIME * (110.0 / 45.0));

        runMotor(0.0, 0.0, 0.2);

        //forward onto ramp
        runMotor(MAX_LEFT_SPEED, MAX_RIGHT_SPEED, (6.86 / APPROX_VEL));

        /*
        resetStartTime();
        while (opModeIsActive() && (getRuntime() < 2.0)) {

            idle();
        }
        */

        ////////////////////////////////

        setMotorPower(0.0, 0.0);
    }

    private void setMotorPower(double leftPower, double rightPower) {

        backLeftMotor.setPower(leftPower);

        backRightMotor.setPower(rightPower);
    }

    private void wait(double time) throws InterruptedException {

        resetStartTime();
        while (opModeIsActive() && (getRuntime() < time)) {

            idle();
        }
    }

    private void runMotor(double leftPower, double rightPower, double time) throws InterruptedException {

        setMotorPower(leftPower, rightPower);

        wait(time);
    }
}
