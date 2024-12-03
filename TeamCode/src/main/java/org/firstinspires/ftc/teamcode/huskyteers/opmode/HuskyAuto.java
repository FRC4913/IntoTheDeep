package org.firstinspires.ftc.teamcode.huskyteers.opmode;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.acmerobotics.roadrunner.Action;
import com.huskyteers.paths.Paths;

import org.firstinspires.ftc.teamcode.huskyteers.HuskyOpMode;

@Autonomous
public class HuskyAuto extends HuskyOpMode {
    @Override
    public void runOpMode() {
        instantiateMotors(new Pose2d(0, 0, 0));
        waitForStart();
        if (isStopRequested())
            return;

        while (opModeIsActive() && !isStopRequested()) {
            Paths paths = new Paths(Paths.StartPosition.LEFT);
            drive.setPoseEstimate(paths.startPosition.getCoordinates());
            paths.examplePath(drive.actionBuilder(drive.pose));
        }
    }
}
