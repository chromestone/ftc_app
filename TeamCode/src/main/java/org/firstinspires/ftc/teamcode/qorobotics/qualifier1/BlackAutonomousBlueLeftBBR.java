package org.firstinspires.ftc.teamcode.qorobotics.qualifier1;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.teamcode.qorobotics.ColorState;

/**
 * QUALIFIER 1 (very not refined)
 *
 * One of the autonomous programs for the black team.
 * The path to beacons are different for each of the
 * four possible position in the FTC competition
 * Ball Beacon Ramp (BBR)
 *
 * Created by Derek Zhang on 10/31/16.
 */
@Autonomous(name = "Auto Red Right BBR", group = "Black")
//@Autonomous(name = "Auto Blue Left BBR", group = "Black")
@Disabled
public class BlackAutonomousBlueLeftBBR extends LinearOpMode {

    private static final double MAX_LEFT_SPEED = 0.95;
    private static final double MAX_RIGHT_SPEED = 1.0;

    //approx velocity in feet per second
    //TO DEREK AND WHOM IT MAY CONCERN: DO NOT TOUCH THIS
    //INSTEAD ADJUST TIMING ---> INDEPENDENTLY <-- FOR EACH STEP
    private static final double APPROX_VEL = 2.4;

    //time to turn 45 degrees at HALF max speed
    private static final double TURN_45_TIME = 0.35;

    ////////////////////////////////

    private DcMotor frontLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor backRightMotor = null;

    @Override
    public void runOpMode() throws InterruptedException {

        frontLeftMotor = hardwareMap.dcMotor.get("mrFL");
        frontRightMotor = hardwareMap.dcMotor.get("mrFR");
        backLeftMotor = hardwareMap.dcMotor.get("mrBL");
        backRightMotor = hardwareMap.dcMotor.get("mrBR");

        CRServo crServo = hardwareMap.crservo.get("crso");

        ColorSensor colorSensor = hardwareMap.colorSensor.get("cs");

        ////////////////////////////////

        frontLeftMotor.resetDeviceConfigurationForOpMode();
        frontRightMotor.resetDeviceConfigurationForOpMode();
        backLeftMotor.resetDeviceConfigurationForOpMode();
        backRightMotor.resetDeviceConfigurationForOpMode();

        crServo.resetDeviceConfigurationForOpMode();

        colorSensor.resetDeviceConfigurationForOpMode();

        ////////////////////////////////

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //crServo.setDirection(DcMotorSimple.Direction.REVERSE);

        ////////////////////////////////

        waitForStart();

        ////////////////////////////////

        //move forward until front wheels touch front edge of second tile
        //distance is approx: 2ft + unknown dist
        runMotor(MAX_LEFT_SPEED, MAX_RIGHT_SPEED, (2 / APPROX_VEL) + 0.035);//0.875);//1.75);

        //give robot time to stop
        runMotor(0.0, 0.0, 0.2);

        //turn 45 degrees right
        runMotor(MAX_LEFT_SPEED / 2.0, -MAX_RIGHT_SPEED / 2.0, TURN_45_TIME + .05);//0.7);//0.35);

        runMotor(0.0, 0.0, 0.2);

        //forward ram ball
        //distance is approx: 5.71ft
        runMotor(MAX_LEFT_SPEED, MAX_RIGHT_SPEED, (5.71 / APPROX_VEL));

        runMotor(0.0, 0.0, 0.2);

        //backward until next turn aligns color sensor (robot right) to middle of tile
        //distance is approx: 3.77ft
        runMotor(-MAX_LEFT_SPEED, -MAX_RIGHT_SPEED, (3.77 / APPROX_VEL));

        runMotor(0.0, 0.0, 0.2);

        //turn 45 degrees right
        runMotor(MAX_LEFT_SPEED / 2.0, -MAX_RIGHT_SPEED / 2.0, TURN_45_TIME);

        runMotor(0.0, 0.0, 0.2);

        //move forward towards beacon
        //distance is approx: 6.63ft
        runMotor(MAX_LEFT_SPEED, MAX_RIGHT_SPEED, (6.63 / APPROX_VEL));

        setMotorPower(0.0, 0.0);

        ////////////////////////////////
        //NOTE: A sweep from LEFT TO RIGHT should be done
        //scan beacon and move servo

        float hsvValues[] = {0F,0F,0F};
        ColorState state = ColorState.NA;
        double runtime = 2.0;
        crServo.setPower(0.01);

        resetStartTime();
        while (opModeIsActive() && (getRuntime() < runtime)) {

            Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

            float hue = hsvValues[0];

            //blue
            if (hue >= 215F && hue <= 265F) {

                if (getRuntime() >= runtime / 2.0) {

                    state = ColorState.RIGHT;
                }
                else {

                    state = ColorState.LEFT;
                }

                break;
            }

            idle();
        }

        crServo.setPower(0.0);

        ////////////////////////////////

        //align robot to press left side of beacon
        if (state == ColorState.LEFT) {

            /*
            //backward until turn adjustment clearance met
            runMotor(-MAX_LEFT_SPEED, -MAX_RIGHT_SPEED, 2.0);

            runMotor(0.0, 0.0, 0.2);

            //turn 45 degrees left
            runMotor(-MAX_LEFT_SPEED / 2.0, MAX_RIGHT_SPEED / 2.0, TURN_45_TIME);

            runMotor(0.0, 0.0, 0.2);

            //forward until a next turn can align right side of robot to middle of tile
            runMotor(MAX_LEFT_SPEED, MAX_RIGHT_SPEED, 2.0);

            runMotor(0.0, 0.0, 0.2);

            //turn 45 degrees right
            runMotor(MAX_LEFT_SPEED / 2.0, -MAX_RIGHT_SPEED / 2.0, TURN_45_TIME);

            //forward until previous distance with beacon reached
            runMotor(MAX_LEFT_SPEED, MAX_RIGHT_SPEED, 2.0);
            */
            ////////////////////////////////

            //forward ram
            runMotor(MAX_LEFT_SPEED, MAX_RIGHT_SPEED, 0.1);

            runMotor(0.0, 0.0, 0.2);

            //backward until forward ram undone
            runMotor(-MAX_LEFT_SPEED, -MAX_RIGHT_SPEED, 0.1);

            ////////////////////////////////

            //backward until back wheels touch back edge of second tile
            //distance is approx: 3.77ft
            runMotor(-MAX_LEFT_SPEED, -MAX_RIGHT_SPEED, (3.77 / APPROX_VEL));

            runMotor(0.0, 0.0, 0.2);

            //turn 45 + 22.5 degrees right
            //runMotor(MAX_LEFT_SPEED / 2.0, -MAX_RIGHT_SPEED / 2.0, TURN_45_TIME * 1.5);

            //turn 45 degrees right
            runMotor(MAX_LEFT_SPEED / 2.0, -MAX_RIGHT_SPEED / 2.0, TURN_45_TIME);

            runMotor(0.0, 0.0, 0.2);

            //forward until on ramp
            //distance is approx: 5.14ft
            runMotor(MAX_LEFT_SPEED, MAX_RIGHT_SPEED, (5.14 / APPROX_VEL));
        }
        else if (state == ColorState.RIGHT) {

            //backward until turn adjustment clearance met
            runMotor(-MAX_LEFT_SPEED, -MAX_RIGHT_SPEED, (3.77 / APPROX_VEL));

            runMotor(0.0, 0.0, 0.2);

            //turn 45 degrees right
            runMotor(MAX_LEFT_SPEED / 2.0, -MAX_RIGHT_SPEED / 2.0, TURN_45_TIME);

            runMotor(0.0, 0.0, 0.2);

            //forward until a next turn can align left side of robot to middle of tile
            runMotor(MAX_LEFT_SPEED, MAX_RIGHT_SPEED, (0.5 / APPROX_VEL));

            runMotor(0.0, 0.0, 0.2);

            //turn 45 degrees left
            runMotor(-MAX_LEFT_SPEED / 2.0, MAX_RIGHT_SPEED / 2.0, TURN_45_TIME);

            //forward until previous distance with beacon reached
            //runMotor(MAX_LEFT_SPEED, MAX_RIGHT_SPEED, 2.0);

            ////////////////////////////////

            //forward ram
            runMotor(MAX_LEFT_SPEED, MAX_RIGHT_SPEED, 0.1);

            runMotor(0.0, 0.0, 0.2);

            //backward until forward ram undone
            runMotor(-MAX_LEFT_SPEED, -MAX_RIGHT_SPEED, 0.1);

            ////////////////////////////////

            //backward until back wheels touch back edge of second tile
            runMotor(-MAX_LEFT_SPEED, -MAX_RIGHT_SPEED, (3.77 / APPROX_VEL));

            runMotor(0.0, 0.0, 0.2);

            //turn 45 degrees right
            runMotor(MAX_LEFT_SPEED / 2.0, -MAX_RIGHT_SPEED / 2.0, TURN_45_TIME);

            runMotor(0.0, 0.0, 0.2);

            //forward until on ramp
            runMotor(MAX_LEFT_SPEED, MAX_RIGHT_SPEED, (5.3 / APPROX_VEL));
        }

        //

        /*
        resetStartTime();
        while (opModeIsActive() && (getRuntime() < 2.0)) {

            idle();
        }
        */

        ////////////////////////////////

        setMotorPower(0.0, 0.0);

        crServo.setPower(0.0);
    }

    private void setMotorPower(double leftPower, double rightPower) {

        frontLeftMotor.setPower(leftPower);
        backLeftMotor.setPower(leftPower);

        frontRightMotor.setPower(rightPower);
        backRightMotor.setPower(rightPower);
    }

    private void wait(double time) throws InterruptedException {

        resetStartTime();
        while (opModeIsActive() && (getRuntime() < time)) {

            idle();
        }
    }

    private void runMotor(double leftPower, double rightPower, double time) throws InterruptedException {

        setMotorPower(leftPower, rightPower);

        wait(time);
    }
}
