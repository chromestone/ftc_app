package org.firstinspires.ftc.teamcode.qorobotics.test;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.qorobotics.ColorState;

/**
 * Tests color sensor + servo sweep with increments
 * (looking for blue side of beacon)
 *
 * Created by Derek Zhang on 12/1/16.
 */

@TeleOp(name = "Color Sensor Test", group = "Test")
@Disabled
public class ColorSensorTest extends LinearOpMode {

    //private static final double MAX_LEFT_SPEED = 1.0;
    //private static final double MAX_RIGHT_SPEED = 1.0;

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

        ////////////////////////////////

        waitForStart();

        ////////////////////////////////

        ColorState state = ColorState.NA;

        float hsvValues[] = {0F,0F,0F};
        float hue;

        //for 45 degrees
        double runtime = 0.125 / (52.0 / 60.0);//rotate 45 degrees which is an eight of a revolution + given servo is @ 52 RPM

        crServo.setPower(1.0);//.01

        wait(runtime);

        crServo.setPower(0.0);

        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

        hue = hsvValues[0];

        //red
        if ((hue >= 0 && hue <= 25) || hue >= 335) {//hue >= 215F && hue <= 265F) {

            state = ColorState.RIGHT;
        }
        else {

            crServo.setPower(1.0);

            wait(runtime * 2.0);

            crServo.setPower(0.0);

            Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

            hue = hsvValues[0];

            //assume 0 <= hue <= 360
            if (hue <= 25 || hue >= 335) {

                state = ColorState.LEFT;
            }
        }

        ////////////////////////////////

        switch (state) {

            case LEFT: {

                runMotor(-1.0, 1.0, .4);
                break;
            }
            case RIGHT: {

                runMotor(1.0, -1.0, .4);
                break;
            }
            case NA: {

                runMotor(-1.0, -1.0, .4);
                break;
            }
            default: {

                break;
            }
        }

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
