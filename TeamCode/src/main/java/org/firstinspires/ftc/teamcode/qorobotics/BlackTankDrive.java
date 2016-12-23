package org.firstinspires.ftc.teamcode.qorobotics;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * TeleOp mode for QO Black Team 2016-2017
 *
 * Created by Derek Zhang on 10/12/16.
 */

@TeleOp(name = "Tank Drive B-TEAM", group = "Black")
public class BlackTankDrive extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        //motors that allow robot to move (my convention is: mOTOr Front Left = mrFL)
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("mrFL");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("mrFR");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("mrBL");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("mrBR");

        DcMotor scissorLift1 = hardwareMap.dcMotor.get("mrSL1");
        DcMotor scissorLift2 = hardwareMap.dcMotor.get("mrSL2");

        //Servo arm1 = hardwareMap.servo.get("soA1");
        //Servo arm2 = hardwareMap.servo.get("soA2");

        ////////////////////////////////

        frontLeftMotor.resetDeviceConfigurationForOpMode();
        frontRightMotor.resetDeviceConfigurationForOpMode();
        backLeftMotor.resetDeviceConfigurationForOpMode();
        backRightMotor.resetDeviceConfigurationForOpMode();

        scissorLift1.resetDeviceConfigurationForOpMode();
        scissorLift2.resetDeviceConfigurationForOpMode();

        //arm1.resetDeviceConfigurationForOpMode();
        //arm2.resetDeviceConfigurationForOpMode();

        ////////////////////////////////

        //scissorLift2.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        ////////////////////////////////

        boolean driveIsForward = true;

        boolean bButtonPrev = false, bButtonCurr;

//      boolean clutching = false;

//      boolean xButtonPrev = false, xButtonCurr;

        boolean quarterPower = false;

        boolean yButtonPrev = false, yButtonCurr;

        ////////////////////////////////

        //gamepad1.setJoystickDeadzone(0.4F);

        ////////////////////////////////

        waitForStart();

        ////////////////////////////////

        while (opModeIsActive()) {

            bButtonCurr = gamepad1.b;

            if (bButtonCurr && !bButtonPrev) {

                if (driveIsForward) {//this toggle is then to drive backward

                    frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                    backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

                    frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                    backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                }
                else {//this toggle is then to drive forward

                    frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                    backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

                    frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                    backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                }

                driveIsForward = !driveIsForward;
            }

            bButtonPrev = bButtonCurr;

            ////////////////////////////////

            float leftThrottle = gamepad1.left_stick_y / (quarterPower ? 4 : 1);
            frontLeftMotor.setPower(leftThrottle);
            backLeftMotor.setPower(leftThrottle);

            float rightThrottle = gamepad1.right_stick_y / (quarterPower ? 4 : 1);
            frontRightMotor.setPower(rightThrottle);
            backRightMotor.setPower(rightThrottle);

            ////////////////////////////////

            if (gamepad1.dpad_up) {

                scissorLift1.setPower(0.5);
                scissorLift2.setPower(0.5);
            }
            else if (gamepad1.dpad_down) {

                scissorLift1.setPower(-0.5);
                scissorLift2.setPower(-0.5);
            }
            else {

                scissorLift1.setPower(0.0);
                scissorLift2.setPower(0.0);
            }

            ////////////////////////////////

            yButtonCurr = gamepad1.y;

            if (yButtonCurr && !yButtonPrev) {

                quarterPower = !quarterPower;
            }

            yButtonPrev = yButtonCurr;

            ////////////////////////////////
/*
            xButtonCurr = gamepad1.x;

            if (xButtonCurr && !xButtonPrev) {

                if (clutching) {

                    arm1.setPosition(0.0);
                    arm2.setPosition(0.0);
                }
                else {

                    arm1.setPosition(1.0);
                    arm2.setPosition(1.0);
                }

                clutching = !clutching;
            }

            xButtonPrev = xButtonCurr;
*/
            ////////////////////////////////

            idle();
        }

        ////////////////////////////////

        frontLeftMotor.setPower(0.0);
        backLeftMotor.setPower(0.0);

        frontRightMotor.setPower(0.0);
        backRightMotor.setPower(0.0);

        scissorLift1.setPower(0.0);
        scissorLift2.setPower(0.0);
    }
}
