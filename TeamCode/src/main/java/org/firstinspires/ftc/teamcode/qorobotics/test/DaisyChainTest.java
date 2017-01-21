package org.firstinspires.ftc.teamcode.qorobotics.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.Iterator;

/**
 * Attempt at reaching more than two motors at a legacy module's port
 * seems to be more in depth than this
 *
 * Yeah it doesn't work; but this could have applications (12/31/16)
 * Update: only returns a list of REGISTERED motors from the phone configuration list
 *
 * Created by Derek Zhang on 11/21/16.
 */

@TeleOp(name = "Daisy Chain Test", group = "Test")
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
