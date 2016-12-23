package org.firstinspires.ftc.teamcode.qorobotics;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by Derek Zhang on 10/4/16.
 */

@TeleOp(name = "Tank Drive R-TEAM", group = "Red")
@Disabled
public class RedTankDrive extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        //motors that allow robot to move
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("mrBL");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("mrBR");

        DcMotor armLeft = hardwareMap.dcMotor.get("mrAL");
        DcMotor armRight = hardwareMap.dcMotor.get("mrAR");

        DcMotor ballCollector = hardwareMap.dcMotor.get("mrBC");

        DcMotor catapult = hardwareMap.dcMotor.get("mrCT");

        ////////////////////////////////

        backLeftMotor.resetDeviceConfigurationForOpMode();
        backRightMotor.resetDeviceConfigurationForOpMode();

        armLeft.resetDeviceConfigurationForOpMode();
        armRight.resetDeviceConfigurationForOpMode();

        ballCollector.resetDeviceConfigurationForOpMode();

        catapult.resetDeviceConfigurationForOpMode();

        ////////////////////////////////

        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        armLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        ////////////////////////////////

        boolean driveIsForward = true;

        //for gamepad 1
        boolean bButtonPrev = false, bButtonCurr;

        ////////////////////////////////

        waitForStart();

        ////////////////////////////////

        while (opModeIsActive()) {

            bButtonCurr = gamepad1.b;

            if (bButtonCurr && !bButtonPrev) {

                if (driveIsForward) {//this toggle is then to drive backward

                    backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

                    backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                }
                else {//this toggle is then to drive forward

                    backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

                    backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                }

                driveIsForward = !driveIsForward;
            }

            bButtonPrev = bButtonCurr;

            ////////////////////////////////

            //left throttle
            backLeftMotor.setPower(-gamepad1.left_stick_y);
            //right throttle
            backRightMotor.setPower(-gamepad1.right_stick_y);

            ////////////////////////////////

            armLeft.setPower(adjustForSafety(gamepad2.left_stick_x, !gamepad2.left_bumper));
            armRight.setPower(adjustForSafety(gamepad2.right_stick_x, !gamepad2.right_bumper));
            
            ////////////////////////////////

            if (gamepad2.y) {

                catapult.setPower(1.0);
            }
            else if (gamepad2.a) {

                catapult.setPower(-1.0);
            }
            else {

                catapult.setPower(0.0);
            }

            ////////////////////////////////

            if (gamepad2.dpad_up) {

                ballCollector.setPower(1.0);
            }
            else if (gamepad2.dpad_down) {

                ballCollector.setPower(-1.0);
            }
            else {

                ballCollector.setPower(0.0);
            }

            ////////////////////////////////

            idle();
        }

        ////////////////////////////////

        backLeftMotor.setPower(0.0);
        backRightMotor.setPower(0.0);

        armLeft.setPower(0.0);
        armRight.setPower(0.0);

        ballCollector.setPower(0.0);
    }

    private float adjustForSafety(float throttle, boolean safety) {

        if (safety) {

            throttle = 0.12F * Math.signum(throttle);
        }

        return throttle;
    }
}
