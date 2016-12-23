package org.firstinspires.ftc.teamcode.qorobotics;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

/**
 * Deez nutz
 * Created by derekzhang on 12/20/16.
 */

public class RedOmniDrive extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor frontMotor = hardwareMap.dcMotor.get("mrF");
        DcMotor rightMotor = hardwareMap.dcMotor.get("mrR");
        DcMotor leftMotor = hardwareMap.dcMotor.get("mrL");
        DcMotor backMotor = hardwareMap.dcMotor.get("mrB");

        ////////////////////////////////

        frontMotor.resetDeviceConfigurationForOpMode();
        rightMotor.resetDeviceConfigurationForOpMode();
        leftMotor.resetDeviceConfigurationForOpMode();
        backMotor.resetDeviceConfigurationForOpMode();

        ////////////////////////////////

        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backMotor.setDirection(DcMotorSimple.Direction.REVERSE);

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

                    leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                    backMotor.setDirection(DcMotorSimple.Direction.REVERSE);

                    rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                    frontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                }
                else {//this toggle is then to drive forward

                    leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                    backMotor.setDirection(DcMotorSimple.Direction.FORWARD);

                    rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                    frontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                }

                driveIsForward = !driveIsForward;
            }

            bButtonPrev = bButtonCurr;

            ////////////////////////////////

            //
            frontMotor.setPower(gamepad1.left_stick_x);
            backMotor.setPower(gamepad1.left_stick_x);
            //
            leftMotor.setPower(Range.clip(-gamepad1.left_stick_y - gamepad1.right_stick_x, -1F, 1F));
            rightMotor.setPower(Range.clip(-gamepad1.left_stick_y + gamepad1.right_stick_x, -1F, 1F));

            ////////////////////////////////

            idle();
        }

        ////////////////////////////////

        frontMotor.setPower(0.0);
        backMotor.setPower(0.0);
        leftMotor.setPower(0.0);
        rightMotor .setPower(0.0);
    }
}
