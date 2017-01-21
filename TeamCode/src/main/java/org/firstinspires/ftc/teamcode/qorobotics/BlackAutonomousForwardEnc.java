package org.firstinspires.ftc.teamcode.qorobotics;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;

/**
 * QUALIFIER 2
 *
 * Robot goes forward based on encoder and hence distance
 *
 * Note: ---->UNTESTED<---- this is as untested as the qualifier 1 autonomous
 *
 * Created by Derek Zhang on 12/25/16.
 */

//@Autonomous(name = "Auto Forward Enc", group = "Black")
//@Disabled
public class BlackAutonomousForwardEnc extends LinearOpMode {

    private static final double MAX_LEFT_SPEED = 1.0;
    private static final double MAX_RIGHT_SPEED = 1.0;

    /*
    2 * PI * r = circumference; where r = 2.0 / 12.0 ft
    circumference is also distance (feet) per revolution
    it is also given that with hitechnic motor controller there are 1440 ticks in a revolution
    therefore 1440 ticks per revolution
    ticks per revolution divided by feet per revolution yields ticks per feet (rev. cancels)
    */
    private static final double TICKS_PER_FEET = 1440.0 / (2.0 * (Math.PI * 2.0 / 12.0));

    ////////////////////////////////

    private DcMotor frontLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor backRightMotor = null;

    //note this is a REFERENCE to ONE of the above four motors
    private DcMotor encoderMotor;

    @Override
    public void runOpMode() throws InterruptedException {

        frontLeftMotor = hardwareMap.dcMotor.get("mrFL");
        encoderMotor = frontRightMotor = hardwareMap.dcMotor.get("mrFR");
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

        encoderMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        encoderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        ////////////////////////////////

        waitForStart();

        ////////////////////////////////

        runMotorTo((int) ((7.46 + .2) * TICKS_PER_FEET));

        ////////////////////////////////

        setMotorPower(0.0, 0.0);
    }

    private void setMotorPower(double leftPower, double rightPower) {

        frontLeftMotor.setPower(leftPower);
        //backLeftMotor.setPower(leftPower);

        frontRightMotor.setPower(rightPower);
        backRightMotor.setPower(rightPower);
    }

    private void runMotorTo(int position) throws InterruptedException {

        encoderMotor.setTargetPosition(position);

        setMotorPower(MAX_LEFT_SPEED, MAX_RIGHT_SPEED);

        waitEncoderMotor();
    }

    private void waitEncoderMotor() throws InterruptedException {

        while (encoderMotor.isBusy() && opModeIsActive()) {

            //idle();
        }

        setMotorPower(0.0, 0.0);
    }
}
