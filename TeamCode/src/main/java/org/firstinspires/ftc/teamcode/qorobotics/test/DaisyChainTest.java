package org.firstinspires.ftc.teamcode.qorobotics.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.Iterator;

/**
 * Attempt at reaching more than two motors at a legacy module's port
 * seems to be more in depth than this
 *
 * Created by derekzhang on 11/21/16.
 */

@Autonomous(name = "Daisy Chain Test", group = "Test")
@Disabled
public class DaisyChainTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();

        telemetry.addData("Size", "" + hardwareMap.dcMotor.size());
        Iterator<DcMotor> iterator = hardwareMap.dcMotor.iterator();
        for (int i = 0; i < 6 && iterator.hasNext(); i++) {

            DcMotor motor= iterator.next();
            telemetry.addData("@" + i, "" + motor.getPortNumber());
            //motor.setPower(0.2);
        }

        telemetry.update();

        while (opModeIsActive()) {

            idle();
        }
    }
}
