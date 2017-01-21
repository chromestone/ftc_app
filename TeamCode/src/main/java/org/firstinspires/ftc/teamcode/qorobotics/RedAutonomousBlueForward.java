package org.firstinspires.ftc.teamcode.qorobotics;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;

/**
 * just goes forward until ball pushed and parks
 *
 * Created by Derek Zhang on 12/26/16.
 */

@Autonomous(name = "Auto Forward", group = "Red")
public class RedAutonomousBlueForward extends LinearOpMode {

    //private static final double MAX_PERP_FORWARD_SPEED = 1.0;
    private static final double MAX_PARA_FORWARD_SPEED = 1.0;

    //approx velocity in feet per second
    //TO DEREK AND WHOM IT MAY CONCERN: DO NOT TOUCH THIS
    //INSTEAD ADJUST TIMING ---> INDEPENDENTLY <-- FOR EACH STEP
    private static final double APPROX_VEL = 2.22;

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

        ////////////////////////////////

        frontMotor.resetDeviceConfigurationForOpMode();
        rightMotor.resetDeviceConfigurationForOpMode();
        leftMotor.resetDeviceConfigurationForOpMode();
        backMotor.resetDeviceConfigurationForOpMode();

        ////////////////////////////////

        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        ////////////////////////////////

        waitForStart();

        ////////////////////////////////

        runMotor(0.0, MAX_PARA_FORWARD_SPEED, ((7.46 + .2) / APPROX_VEL) + 0.2);
/*
        frontMotor.setPower(1.0);
        backMotor.setPower(-1.0);

        //
        leftMotor.setPower(1.0);
        rightMotor.setPower(1.0);

        wait(0.5);

        frontMotor.setPower(-1.0);
        backMotor.setPower(1.0);

        //
        leftMotor.setPower(1.0);
        rightMotor.setPower(1.0);

        wait(0.3);

        runMotor(0.0, MAX_PARA_FORWARD_SPEED, 0.3);*/

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
