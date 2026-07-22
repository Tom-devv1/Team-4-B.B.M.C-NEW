package Geminitest;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Basic Telly Auto Right", group="Autonomous")
public class tellyop_autoright extends LinearOpMode {

    public DcMotor frontRight, frontLeft, backRight, backLeft;
    public DcMotor intakeMotor;
    private Shooter shooter = new Shooter();
    private ElapsedTime runtime = new ElapsedTime();

    // Encoder Constants
    static final double     COUNTS_PER_MOTOR_REV    = 537.7 ;    // GoBilda 5202/5203 series
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;
    static final double     WHEEL_DIAMETER_CM       = 10.4 ;     // 104mm wheels
    static final double     COUNTS_PER_CM           = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_CM * 3.1415);
    static final double     TRACK_WIDTH_CM          = 36.0;      // Distance between left and right wheels

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

        // Reset encoders
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        shooter.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            telemetry.addData("Status", "Running Auto");
            telemetry.update();
            
            // 1. Move forward 10 cm
            telemetry.addData("Step", "Forward 10cm");
            telemetry.update();
            encoderDrive(0.5, 10, 10, 2.0);

            // 2. Turn right 90 degrees
            // Distance = (TrackWidth * PI) / 4 for 90 degrees
            double turnDistance = (TRACK_WIDTH_CM * Math.PI) / 4.0;
            telemetry.addData("Step", "Turn Right 90 deg");
            telemetry.update();
            encoderDrive(0.4, turnDistance, -turnDistance, 3.0);

            // 3. Move forward 30 cm
            telemetry.addData("Step", "Forward 30cm");
            telemetry.update();
            encoderDrive(0.5, 30, 30, 4.0);

            stopRobot();

            // Spin up shooter
            telemetry.addData("Status", "Spinning up shooter");
            telemetry.update();
            shooter.run(0, true);
            sleep(1500);

            // Feed to shooter
            if (opModeIsActive()) {
                telemetry.addData("Status", "Feeding intake");
                telemetry.update();
                intakeMotor.setPower(0.85);
                sleep(1000);
            }

            stopMechanisms();
            telemetry.addData("Status", "Done");
            telemetry.update();
        }
    }

    public void stopRobot() {
        frontLeft.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
    }

    public void stopMechanisms() {
        intakeMotor.setPower(0);
        shooter.run(0, false);
    }

    public void encoderDrive(double speed, double leftCm, double rightCm, double timeoutS) {
        int newFrontLeftTarget;
        int newBackLeftTarget;
        int newFrontRightTarget;
        int newBackRightTarget;

        if (opModeIsActive()) {
            newFrontLeftTarget = frontLeft.getCurrentPosition() + (int)(leftCm * COUNTS_PER_CM);
            newBackLeftTarget = backLeft.getCurrentPosition() + (int)(leftCm * COUNTS_PER_CM);
            newFrontRightTarget = frontRight.getCurrentPosition() + (int)(rightCm * COUNTS_PER_CM);
            newBackRightTarget = backRight.getCurrentPosition() + (int)(rightCm * COUNTS_PER_CM);

            frontLeft.setTargetPosition(newFrontLeftTarget);
            backLeft.setTargetPosition(newBackLeftTarget);
            frontRight.setTargetPosition(newFrontRightTarget);
            backRight.setTargetPosition(newBackRightTarget);

            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            frontLeft.setPower(Math.abs(speed));
            backLeft.setPower(Math.abs(speed));
            frontRight.setPower(Math.abs(speed));
            backRight.setPower(Math.abs(speed));

            while (opModeIsActive() &&
                   (runtime.seconds() < timeoutS) &&
                   (frontLeft.isBusy() && frontRight.isBusy())) {
                telemetry.addData("Target",  "%7d :%7d", newFrontLeftTarget,  newFrontRightTarget);
                telemetry.addData("Actual",  "%7d :%7d", frontLeft.getCurrentPosition(), frontRight.getCurrentPosition());
                telemetry.update();
            }

            stopRobot();

            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}
