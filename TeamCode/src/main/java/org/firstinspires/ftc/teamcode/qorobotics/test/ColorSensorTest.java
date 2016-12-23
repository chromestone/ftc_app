package org.firstinspires.ftc.teamcode.qorobotics.test;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.qorobotics.ColorState;

/**
 * Created by derekzhang on 12/1/16.
 */

@Autonomous(name = "Color Sensor Test", group = "Test")
@Disabled
public class ColorSensorTest extends LinearOpMode {

    private static final double MAX_LEFT_SPEED = 1.0;
    private static final double MAX_RIGHT_SPEED = 1.0;

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

        colorSensor.resetDeviceConfigurationForOpMode();

        ////////////////////////////////

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);


        ////////////////////////////////

        waitForStart();

        ////////////////////////////////

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
