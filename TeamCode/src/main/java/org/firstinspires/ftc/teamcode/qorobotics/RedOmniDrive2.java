package org.firstinspires.ftc.teamcode.qorobotics;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.Range;

/**
 * uses two game-pads
 *
 * second game-pad
 *
 * Created by Derek Zhang on 12/27/16.
 */

@TeleOp(name = "O-Drive R-TEAM 2 Contr.", group = "Red")
public class RedOmniDrive2 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor frontMotor = hardwareMap.dcMotor.get("mrF");
        DcMotor rightMotor = hardwareMap.dcMotor.get("mrR");
        DcMotor leftMotor = hardwareMap.dcMotor.get("mrL");
        DcMotor backMotor = hardwareMap.dcMotor.get("mrB");

        DcMotor catapult = hardwareMap.dcMotor.get("mrCT");

        ////////////////////////////////

        frontMotor.resetDeviceConfigurationForOpMode();
        rightMotor.resetDeviceConfigurationForOpMode();
        leftMotor.resetDeviceConfigurationForOpMode();
        backMotor.resetDeviceConfigurationForOpMode();

        catapult.resetDeviceConfigurationForOpMode();

        ////////////////////////////////

        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //catapult.setDirection(DcMotorSimple.Direction.REVERSE);

        ////////////////////////////////

        /*
        boolean driveIsForward = true;

        //for gamepad 1
        boolean bButtonPrev = false, bButtonCurr;
        */

        ////////////////////////////////

        waitForStart();

        ////////////////////////////////

        while (opModeIsActive()) {

            //
            frontMotor.setPower(Range.clip(gamepad1.left_stick_x - gamepad1.right_stick_x, -1F, 1F));
            backMotor.setPower(Range.clip(gamepad1.left_stick_x + gamepad1.right_stick_x, -1F, 1F));

            //
            leftMotor.setPower(Range.clip(-gamepad1.left_stick_y - gamepad1.right_stick_x, -1F, 1F));
            rightMotor.setPower(Range.clip(-gamepad1.left_stick_y + gamepad1.right_stick_x, -1F, 1F));

            ////////////////////////////////

            if (gamepad2.dpad_up) {

                catapult.setPower(1.0);
            }
            else if (gamepad2.dpad_down) {

                catapult.setPower(-1.0);
            }
            else {

                catapult.setPower(0.0);
            }

            ////////////////////////////////

            //idle();
        }

        ////////////////////////////////

        frontMotor.setPower(0.0);
        backMotor.setPower(0.0);
        leftMotor.setPower(0.0);
        rightMotor.setPower(0.0);
    }
}
