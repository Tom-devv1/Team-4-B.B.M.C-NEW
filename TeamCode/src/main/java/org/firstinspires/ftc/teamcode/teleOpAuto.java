package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="Basic: Iterative OpMode", group="Iterative OpMode")
public class teleOpAuto extends LinearOpMode {

    public DcMotor frontRight, frontLeft, backRight, backLeft;

    @Override
    public void runOpMode() {

        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        forward();
        sleep(1000);

        turnLeft();
        sleep(1000);

    }

    public void stopRobot() {
        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
    }

    public void forward() {
        frontLeft.setPower(1);
        backLeft.setPower(1);
        frontRight.setPower(1);
        backRight.setPower(1);
    }

    public void backward() {
        frontLeft.setPower(-1);
        backLeft.setPower(-1);
        frontRight.setPower(-1);
        backRight.setPower(-1);
    }

    public void turnLeft() {
        frontLeft.setPower(-1.0);
        backLeft.setPower(-1.0);
        frontRight.setPower(1.0);
        backRight.setPower(1.0);
    }

    public void turnRight() {
        frontLeft.setPower(1.0);
        backLeft.setPower(1.0);
        frontRight.setPower(-1.0);
        backRight.setPower(-1.0);
    }
}