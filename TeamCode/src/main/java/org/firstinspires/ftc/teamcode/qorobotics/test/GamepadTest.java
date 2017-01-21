package org.firstinspires.ftc.teamcode.qorobotics.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * This tests the joystick (direction) of the gamepad
 *
 * left should be negative x
 * up should be negative y
 *
 * Created by derekzhang on 12/9/16.
 */

@TeleOp(name = "Gamepad test", group = "Test")
@Disabled
public class GamepadTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();

        while (opModeIsActive()) {

            telemetry.addData("1 left y", "" + gamepad1.left_stick_x);
            telemetry.addData("1 left x", "" + gamepad1.left_stick_y);

            telemetry.update();

            idle();
        }
    }
}
