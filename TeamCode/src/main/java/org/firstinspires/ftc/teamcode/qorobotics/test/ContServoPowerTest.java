package org.firstinspires.ftc.teamcode.qorobotics.test;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.qorobotics.util.Ticker;

/**
 * Tests power range on continuous servo
 * Hypothesis as of 12/29/16 is that most servos only have one power setting
 * (or least FTC only supports one speed??)
 *
 * Created by Derek Zhang on 12/29/16.
 */

@TeleOp(name = "Cont. Servo Power Test", group = "Test")
@Disabled
public class ContServoPowerTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        CRServo crServo = hardwareMap.crservo.get("crso");//continuous servo

        ////////////////////////////////

        crServo.resetDeviceConfigurationForOpMode();

        ////////////////////////////////

        waitForStart();

        ////////////////////////////////

        while (opModeIsActive()) {

            crServo.setPower(gamepad1.left_stick_y);

            telemetry.addData("Using power: ", String.valueOf(gamepad1.left_stick_y));
            telemetry.update();

            idle();
        }

        ////////////////////////////////

        crServo.setPower(0.0);
    }
}