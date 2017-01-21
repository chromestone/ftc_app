package org.firstinspires.ftc.teamcode.qorobotics;

import android.support.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;

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

        DcMotor scissorLift1 = hardwareMap.dcMotor.get("mrSL");
        //DcMotor scissorLift2 = hardwareMap.dcMotor.get("mrSL2");

        CRServo arm1 = hardwareMap.crservo.get("soA1");
        CRServo arm2 = hardwareMap.crservo.get("soA2");

        DcMotor catapult = hardwareMap.dcMotor.get("mrCT");

        CRServo clawArm1 = hardwareMap.crservo.get("csoCA1");
        CRServo clawArm2 = hardwareMap.crservo.get("csoCA2");

        Servo claw = hardwareMap.servo.get("soCW");

        ////////////////////////////////

        frontLeftMotor.resetDeviceConfigurationForOpMode();
        frontRightMotor.resetDeviceConfigurationForOpMode();
        backLeftMotor.resetDeviceConfigurationForOpMode();
        backRightMotor.resetDeviceConfigurationForOpMode();

        scissorLift1.resetDeviceConfigurationForOpMode();
        //scissorLift2.resetDeviceConfigurationForOpMode();

        arm1.resetDeviceConfigurationForOpMode();
        arm2.resetDeviceConfigurationForOpMode();

        catapult.resetDeviceConfigurationForOpMode();

        clawArm1.resetDeviceConfigurationForOpMode();
        clawArm2.resetDeviceConfigurationForOpMode();

        claw.resetDeviceConfigurationForOpMode();

        ////////////////////////////////

        //scissorLift2.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        arm2.setDirection(DcMotorSimple.Direction.REVERSE);

        clawArm2.setDirection(DcMotorSimple.Direction.REVERSE);

        ////////////////////////////////

        //toggle for drive direction
        boolean driveIsForward = true;

        boolean bButtonPrev = false, bButtonCurr;

        //toggle for big ball clutching
        boolean clutching = false;

        boolean xButtonPrev = false, xButtonCurr;

        //toggle for quarter max power of robot
        boolean quarterPower = false;

        boolean yButtonPrev = false, yButtonCurr;

        //clutching small ball
        boolean clawClutch = false;

        boolean aButtonPrev = false, aButtonCurr;

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
                //scissorLift2.setPower(0.5);
            }
            else if (gamepad1.dpad_down) {

                scissorLift1.setPower(-0.5);
                //scissorLift2.setPower(-0.5);
            }
            else {

                scissorLift1.setPower(0.0);
                //scissorLift2.setPower(0.0);
            }

            ////////////////////////////////

            yButtonCurr = gamepad1.y;

            if (yButtonCurr && !yButtonPrev) {

                quarterPower = !quarterPower;
            }

            yButtonPrev = yButtonCurr;

            ////////////////////////////////

            xButtonCurr = gamepad1.x;

            if (xButtonCurr && !xButtonPrev) {

                if (clutching) {

                    arm1.setPower(0.0);
                    arm2.setPower(0.0);
                }
                else {

                    arm1.setPower(-1.0);
                    arm2.setPower(-1.0);
                }

                clutching = !clutching;
            }

            xButtonPrev = xButtonCurr;

            ////////////////////////////////
            if (!clutching) {

                if (gamepad1.dpad_left) {

                    arm1.setPower(1.0);
                    arm2.setPower(1.0);

                    //arm1.setPosition(0.0);
                    //arm2.setPosition(0.0);
                } else if (gamepad1.dpad_right) {

                    arm1.setPower(-1.0);
                    arm2.setPower(-1.0);
                } else {

                    arm1.setPower(0.0);
                    arm2.setPower(0.0);

                    //arm1.setPosition(1.0);
                    // arm2.setPosition(1.0);
                }
            }

            ////////////////////////////////

            aButtonCurr = gamepad1.a;

            if (aButtonCurr && !aButtonPrev) {

                if (clawClutch) {

                    claw.setPosition(0.0);
                }
                else {

                    claw.setPosition(1.0);
                }

                clawClutch = !clawClutch;
            }

            aButtonPrev = aButtonCurr;

            ////////////////////////////////

            if (gamepad1.left_bumper) {

                clawArm1.setPower(1.0);
                clawArm2.setPower(1.0);
            }
            else if (gamepad1.right_bumper) {

                clawArm1.setPower(-1.0);
                clawArm2.setPower(-1.0);
            }
            else {

                clawArm1.setPower(0.0);
                clawArm2.setPower(0.0);
            }

            ////////////////////////////////

            if (gamepad1.right_trigger > 0.0) {

                catapult.setPower(1.0);
            }
            else if (gamepad1.left_trigger > 0.0) {

                catapult.setPower(-1.0);
            }
            else {

                catapult.setPower(0.0);
            }

            ////////////////////////////////

            /*
            try {

                idle();
            }
            catch (InterruptedException e) {

                break;
            }
            */
        }

        ////////////////////////////////

        frontLeftMotor.setPower(0.0);
        backLeftMotor.setPower(0.0);

        frontRightMotor.setPower(0.0);
        backRightMotor.setPower(0.0);

        scissorLift1.setPower(0.0);
        //scissorLift2.setPower(0.0);

        catapult.setPower(0.0);

        clawArm1.setPower(0.0);
        clawArm2.setPower(0.0);

        claw.setPosition(0.0);
    }
}
