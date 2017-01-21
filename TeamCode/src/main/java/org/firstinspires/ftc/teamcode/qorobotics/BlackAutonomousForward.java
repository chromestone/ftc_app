package org.firstinspires.ftc.teamcode.qorobotics;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;

/**
 * QUALIFIER 2
 *
 * Timed robot going forward
 *
 * Created by Derek Zhang on 12/24/16.
 */

@Autonomous(name = "Auto Forward Time", group = "Black")
public class BlackAutonomousForward extends LinearOpMode {

    private static final double MAX_LEFT_SPEED = 1.0;
    private static final double MAX_RIGHT_SPEED = 1.0;

    //approx velocity in feet per second
    //TO DEREK AND WHOM IT MAY CONCERN: DO NOT TOUCH THIS
    //INSTEAD ADJUST TIMING ---> INDEPENDENTLY <-- FOR EACH STEP
    private static final double APPROX_VEL = 2.4;

    /*
    //time to turn 45 degrees at HALF max speed
    private static final double TURN_45_TIME = 0.35;
    */

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

        ////////////////////////////////

        frontLeftMotor.resetDeviceConfigurationForOpMode();
        frontRightMotor.resetDeviceConfigurationForOpMode();
        backLeftMotor.resetDeviceConfigurationForOpMode();
        backRightMotor.resetDeviceConfigurationForOpMode();

        ////////////////////////////////

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        ////////////////////////////////

        waitForStart();

        ////////////////////////////////

        runMotor(MAX_LEFT_SPEED, MAX_RIGHT_SPEED, ((7.46 - .26) / APPROX_VEL));

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
