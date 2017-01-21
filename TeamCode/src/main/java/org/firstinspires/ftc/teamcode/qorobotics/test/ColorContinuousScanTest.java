package org.firstinspires.ftc.teamcode.qorobotics.test;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;


import org.firstinspires.ftc.teamcode.qorobotics.ColorState;

/**
 * Scans for beacon color while having the servo constantly rotate
 * (looking for blue side of beacon)
 *
 * Created by Derek Zhang on 12/29/16.
 */

@TeleOp(name = "Color Cont. Scan Test", group = "Test")
@Disabled
public class ColorContinuousScanTest extends LinearOpMode {

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
        //NOTE: A sweep from LEFT TO RIGHT should be done
        //scans a beacon with a moving servo; sets a decision (ColorState)

        ColorState state = ColorState.NA;

        float hsvValues[] = {0F,0F,0F};

        double runtime = 0.5 / (52.0 / 60.0);//rotate 180 degrees which is half a revolution + given servo is @ 52 RPM

        crServo.setPower(1.0);

        resetStartTime();
        while (opModeIsActive() && (getRuntime() < runtime)) {

            Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

            float hue = hsvValues[0];

            //assume 0 <= hue <= 360
            if (hue <= 25 || hue >= 335) {

                //rotated more than 90 degrees
                if (getRuntime() >= runtime / 2.0) {

                    //blue button must be on the left
                    state = ColorState.LEFT;
                }
                else {

                    //blue button must on the right
                    state = ColorState.RIGHT;
                }

                break;
            }

            idle();
        }

        crServo.setPower(0.0);

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
