package org.firstinspires.ftc.teamcode.qorobotics;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Non linear opmode of the opmode of same name
 *
 * Created by Derek Zhang on 1/21/17.
 */

//@TeleOp(name = "Tank Drive B-TEAM2", group = "Black")
public class BlackTankDriveThingy extends OpMode {

    private DcMotor frontLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor backRightMotor = null;

    private DcMotor scissorLift1 = null;

    private CRServo arm1 = null;
    private CRServo arm2 = null;

    private DcMotor catapult = null;

    private CRServo clawArm1 = null;
    private CRServo clawArm2 = null;

    private Servo claw = null;

    ////////////////////////////////

    //toggle for drive direction
    private boolean driveIsForward = true;

    private boolean bButtonPrev = false, bButtonCurr;

    //toggle for big ball clutching
    private boolean clutching = false;

    private boolean xButtonPrev = false, xButtonCurr;

    //toggle for quarter max power of robot
    private boolean quarterPower = false;

    private boolean yButtonPrev = false, yButtonCurr;

    //clutching small ball
    private boolean clawClutch = false;

    private boolean aButtonPrev = false, aButtonCurr;

    @Override
    public void init() {

        frontLeftMotor = hardwareMap.dcMotor.get("mrFL");
        frontRightMotor = hardwareMap.dcMotor.get("mrFR");
        backLeftMotor = hardwareMap.dcMotor.get("mrBL");
        backRightMotor = hardwareMap.dcMotor.get("mrBR");

        scissorLift1 = hardwareMap.dcMotor.get("mrSL");

        arm1 = hardwareMap.crservo.get("soA1");
        arm2 = hardwareMap.crservo.get("soA2");

        catapult = hardwareMap.dcMotor.get("mrCT");

        clawArm1 = hardwareMap.crservo.get("csoCA1");
        clawArm2 = hardwareMap.crservo.get("csoCA2");

        claw = hardwareMap.servo.get("soCW");

        ////////////////////////////////

        frontLeftMotor.resetDeviceConfigurationForOpMode();
        frontRightMotor.resetDeviceConfigurationForOpMode();
        backLeftMotor.resetDeviceConfigurationForOpMode();
        backRightMotor.resetDeviceConfigurationForOpMode();

        scissorLift1.resetDeviceConfigurationForOpMode();

        arm1.resetDeviceConfigurationForOpMode();
        arm2.resetDeviceConfigurationForOpMode();

        catapult.resetDeviceConfigurationForOpMode();

        clawArm1.resetDeviceConfigurationForOpMode();
        clawArm2.resetDeviceConfigurationForOpMode();

        claw.resetDeviceConfigurationForOpMode();

        ////////////////////////////////

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        arm2.setDirection(DcMotorSimple.Direction.REVERSE);

        clawArm2.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {

        bButtonCurr = gamepad1.b;

        if (bButtonCurr && !bButtonPrev) {

            if (driveIsForward) {//this toggle is then to drive backward

                frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

                frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            }
            else {//this toggle is then to drive forward

                frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

                frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            }

            driveIsForward = !driveIsForward;
        }

        bButtonPrev = bButtonCurr;

        ////////////////////////////////

        float leftThrottle = gamepad1.left_stick_y / (quarterPower ? 4 : 1);
        frontLeftMotor.setPower(leftThrottle);
        backLeftMotor.setPower(leftThrottle);

        float rightThrottle = gamepad1.right_stick_y / (quarterPower ? 4 : 1);
        frontRightMotor.setPower(rightThrottle);
        backRightMotor.setPower(rightThrottle);

        ////////////////////////////////

        if (gamepad1.dpad_up) {

            scissorLift1.setPower(0.5);
            //scissorLift2.setPower(0.5);
        }
        else if (gamepad1.dpad_down) {

            scissorLift1.setPower(-0.5);
            //scissorLift2.setPower(-0.5);
        }
        else {

            scissorLift1.setPower(0.0);
            //scissorLift2.setPower(0.0);
        }

        ////////////////////////////////

        yButtonCurr = gamepad1.y;

        if (yButtonCurr && !yButtonPrev) {

            quarterPower = !quarterPower;
        }

        yButtonPrev = yButtonCurr;

        ////////////////////////////////

        xButtonCurr = gamepad1.x;

        if (xButtonCurr && !xButtonPrev) {

            if (clutching) {

                arm1.setPower(0.0);
                arm2.setPower(0.0);
            }
            else {

                arm1.setPower(-1.0);
                arm2.setPower(-1.0);
            }

            clutching = !clutching;
        }

        xButtonPrev = xButtonCurr;

        ////////////////////////////////
        if (!clutching) {

            if (gamepad1.dpad_left) {

                arm1.setPower(1.0);
                arm2.setPower(1.0);

                //arm1.setPosition(0.0);
                //arm2.setPosition(0.0);
            } else if (gamepad1.dpad_right) {

                arm1.setPower(-1.0);
                arm2.setPower(-1.0);
            } else {

                arm1.setPower(0.0);
                arm2.setPower(0.0);

                //arm1.setPosition(1.0);
                // arm2.setPosition(1.0);
            }
        }

        ////////////////////////////////

        aButtonCurr = gamepad1.a;

        if (aButtonCurr && !aButtonPrev) {

            if (clawClutch) {

                claw.setPosition(0.0);
            }
            else {

                claw.setPosition(1.0);
            }

            clawClutch = !clawClutch;
        }

        aButtonPrev = aButtonCurr;

        ////////////////////////////////

        if (gamepad1.left_bumper) {

            clawArm1.setPower(1.0);
            clawArm2.setPower(1.0);
        }
        else if (gamepad1.right_bumper) {

            clawArm1.setPower(-1.0);
            clawArm2.setPower(-1.0);
        }
        else {

            clawArm1.setPower(0.0);
            clawArm2.setPower(0.0);
        }

        ////////////////////////////////

        if (gamepad1.right_trigger > 0.0) {

            catapult.setPower(1.0);
        }
        else if (gamepad1.left_trigger > 0.0) {

            catapult.setPower(-1.0);
        }
        else {

            catapult.setPower(0.0);
        }
    }

    @Override
    public void stop() {

        frontLeftMotor.setPower(0.0);
        backLeftMotor.setPower(0.0);

        frontRightMotor.setPower(0.0);
        backRightMotor.setPower(0.0);

        scissorLift1.setPower(0.0);

        catapult.setPower(0.0);

        clawArm1.setPower(0.0);
        clawArm2.setPower(0.0);

        //claw.setPosition(0.0);
        claw.resetDeviceConfigurationForOpMode();
    }
}
