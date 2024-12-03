package org.firstinspires.ftc.teamcode.huskyteers.hardware;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArmSlide {
    public static final int EXTEND_POSITION = 100;
    public static final int RETRACT_POSITION = 0;
    final private DcMotor motor1;
    final private DcMotor motor2;
    public ArmSlide(HardwareMap hardwareMap) {
        motor1 = hardwareMap.get(DcMotor.class, "armSlide1");
        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2 = hardwareMap.get(DcMotor.class, "armSlide2");
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void setPosition(int position) {
        motor1.setTargetPosition(position);
        motor2.setTargetPosition(position);
    }

    public Action extendArm() {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (!initialized) {
                    setPosition(EXTEND_POSITION);
                    initialized = true;
                }
                return motor1.isBusy()&& motor2.isBusy();
            }
        };
    }

    public Action retractArm() {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                if (!initialized) {
                    setPosition(RETRACT_POSITION);
                    initialized = true;
                }
                return motor1.isBusy()&& motor2.isBusy();
            }
        };
    }
}
