package org.firstinspires.ftc.teamcode.qorobotics.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.qorobotics.util.Ticker;

/**
 * Makes sure a servo works.
 *
 * First try a different port. controllers and modern robotics (or new to legacy) may be weird
 *
 * Created by Derek Zhang on 1/19/17.
 */

@TeleOp(name = "Servo Pos Test", group = "Test")
@Disabled
public class ServoPositionTest extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        Servo servo = hardwareMap.servo.get("soCW");

        ////////////////////////////////

        waitForStart();

        servo.setPosition(1.0);

        ////////////////////////////////

        while (opModeIsActive()) {

            telemetry.addData("Derek", servo.getPosition());

            telemetry.update();

            idle();
        }
    }
}
