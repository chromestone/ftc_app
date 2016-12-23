package org.firstinspires.ftc.teamcode.qorobotics.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by derekzhang on 12/9/16.
 */

@TeleOp(name = "Gamepad test", group = "Test")
@Disabled
public class GamepadTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();

        while (opModeIsActive()) {

            telemetry.addData("gamepad1", "" + gamepad1);
            telemetry.addData("gamepad2", "" + gamepad2);

            telemetry.update();

            idle();
        }
    }
}
