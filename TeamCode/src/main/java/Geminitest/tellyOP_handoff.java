package Geminitest;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name="Basic: Handoff TellyOp", group="Iterative OpMode")
public class tellyOP_handoff extends OpMode {

    public DcMotor frontRight, frontLeft, backRight, backLeft;
    public DcMotor intakeMotor;
    private Shooter shooter = new Shooter();

    private boolean isGamepad1Driving = true;
    private boolean previousA1 = false;
    private boolean previousA2 = false;

    @Override
    public void init() {
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        shooter.init(hardwareMap);
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
        Gamepad operatorGamepad = isGamepad1Driving ? gamepad2 : gamepad1;

        double y = -activeGamepad.left_stick_y;
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

        if (operatorGamepad.right_bumper) {
            intakeMotor.setPower(0.85);
        } else if (operatorGamepad.left_bumper) {
            intakeMotor.setPower(-0.85);
        } else {
            intakeMotor.setPower(0.0);
        }

        shooter.run(operatorGamepad.right_trigger, operatorGamepad.y);
    }
}
