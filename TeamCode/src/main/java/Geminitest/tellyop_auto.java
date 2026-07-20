package Geminitest;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="Basic Telly Auto", group="Autonomous")
public class tellyop_auto extends LinearOpMode {

    public DcMotor frontRight, frontLeft, backRight, backLeft;
    public DcMotor intakeMotor;
    private Shooter shooter = new Shooter();

    @Override
    public void runOpMode() {

        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        shooter.init(hardwareMap);

        waitForStart();

        forward();
        sleep(1000);

        turnLeft();
        sleep(1000);

        stopRobot();

        shooter.run(1.0, true);
        sleep(1500);

        intakeMotor.setPower(0.85);
        sleep(1000);

        stopMechanisms();
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

    public void stopMechanisms() {
        intakeMotor.setPower(0);
        shooter.run(0, false);
    }
}
