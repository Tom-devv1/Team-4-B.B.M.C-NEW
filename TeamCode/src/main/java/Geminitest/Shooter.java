package Geminitest;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {
    private DcMotor shooterMotor;

    public void init(HardwareMap hardwareMap) {
        shooterMotor = hardwareMap.get(DcMotor.class, "shooterMotor");
        shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void run(double triggerPower, boolean fullPowerOverride) {
        if (fullPowerOverride) {
            shooterMotor.setPower(1.0);
        } else {
            shooterMotor.setPower(triggerPower);
        }
    }

    public double getPower() {
        return shooterMotor.getPower();
    }
}