package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name="Basic: Iterative OpMode", group="Iterative OpMode")
public class teleOp extends OpMode {

    public DcMotor frontRight, frontLeft, backRight, backLeft;


    @Override
    public void init() {
        backLeft = hardwareMap.get(DcMotor.class, "frontRight"); // Moves front right motor
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft"); // moves front left motor
        backRight = hardwareMap.get(DcMotor.class, "backRight"); // moves back right motor
        frontRight = hardwareMap.get(DcMotor.class, "backLeft"); // moves back left motor

        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    @Override
    public void loop() {
        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

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
