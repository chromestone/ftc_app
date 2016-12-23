package org.firstinspires.ftc.teamcode.qorobotics.test;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.qorobotics.util.Ticker;

/**
 * Created by Derek Zhang on 11/10/16.
 */

@Autonomous(name = "ServoTest", group = "Test")
@Disabled
public class ServoTester extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Servo servo = hardwareMap.servo.get("servo");
        //CRServo crservo = hardwareMap.crservo.get("crservo");//continuous servo

        double increment = 0.01;
        double position = 0;

        waitForStart();

        servo.setPosition(0.0);

        Ticker ticker = new Ticker();

        while (opModeIsActive()) {

            if (gamepad1.left_bumper) {

                position -= increment;
            }
            else if (gamepad1.right_bumper) {

                position += increment;
            }

            position = Range.clip(position, 0.0, 1.0);

            servo.setPosition(position);

            ticker.waitForTick(40L);

            idle();
        }
    }
}
