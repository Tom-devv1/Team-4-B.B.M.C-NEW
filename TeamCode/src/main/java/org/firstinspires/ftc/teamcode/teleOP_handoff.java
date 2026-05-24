package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name="Basic: Iterative OpMode", group="Iterative OpMode")
public class teleOP_handoff extends OpMode {

    public DcMotor frontRight, frontLeft, backRight, backLeft;
    private boolean isGamepad1Driving = true;
    private boolean previousA1 = false;
    private boolean previousA2 = false;

    @Override
    public void init() {
        frontRight = hardwareMap.get(DcMotor.class, "frontRight"); // Moves front right motor
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft"); // moves front left motor
        backRight = hardwareMap.get(DcMotor.class, "backRight"); // moves back right motor
        backLeft = hardwareMap.get(DcMotor.class, "backLeft"); // moves back left motor

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    @Override
    public void loop() {


        if(gamepad1.a && !previousA1) {
            if (isGamepad1Driving) {
                gamepad2.rumbleBlips(2);
                gamepad1.rumble(500);
            }
            isGamepad1Driving = true;
        }

        if(gamepad2.a && !previousA2) {
            if (isGamepad1Driving) {
                gamepad2.rumbleBlips(2);
                gamepad1.rumble(500);
            }
            isGamepad1Driving = false;
        }

        previousA1 = gamepad1.a;
        previousA2 = gamepad2.a;

        Gamepad activeGamepad = isGamepad1Driving ? gamepad1 : gamepad2;





        double y = -activeGamepad.left_stick_y; // Remember, Y stick value is reversed
        double x = activeGamepad.left_stick_x;
        double rx = activeGamepad.right_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);


    }
}
