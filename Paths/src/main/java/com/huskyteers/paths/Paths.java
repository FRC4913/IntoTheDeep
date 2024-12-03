package com.huskyteers.paths;

import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.Pose2d;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class Paths {
    // Predefined positions for the autonomous robot to possibly start
    public enum StartPosition {
        LEFT(new Pose2d(-36, 60, Math.toRadians(0))),  // Example left start
        RIGHT(new Pose2d(0, 60, Math.toRadians(0))); // Custom position 1

        private final Pose2d coordinates;

        StartPosition(Pose2d coordinates) {
            this.coordinates = coordinates;
        }

        public Pose2d getCoordinates() {
            return coordinates;
        }
    }

    private final StartPosition startPosition;

    public Paths(StartPosition startPosition) {
        this.startPosition = startPosition;
    }
    public void executePath(TrajectoryActionBuilder actionBuilder) {
        switch (startPosition) {
            case LEFT:
                leftPath(actionBuilder);
                break;
            case RIGHT:
                rightPath(actionBuilder);
                break;
            default:
                throw new IllegalArgumentException("Invalid starting position");
        }
    }
    // This is a switch statement function to run different paths based on the starting position

    private List<Runnable> actions = new ArrayList<>();

    public void addAction(Runnable action) {
        actions.add(action);
    }

    public void executeActions() {
        for (Runnable action : actions) {
            action.run();
        }
    }

    public void leftPath(TrajectoryActionBuilder actionBuilder) {
        actionBuilder.setPoseEstimate(startPosition.getCoordinates());
        addAction(() -> actionBuilder.strafeTo(new Vector2d(10, 10)));
        addAction(() -> actionBuilder.turn(Math.toRadians(90)));
        addAction(() -> actionBuilder.strafeTo(new Vector2d(20, 20)));
        // Add more actions as needed

        executeActions();
    }

    public void rightPath(TrajectoryActionBuilder actionBuilder) {
        addAction(() -> actionBuilder.strafeTo(new Vector2d(-10, -10)));
        addAction(() -> actionBuilder.turn(Math.toRadians(-90)));
        addAction(() -> actionBuilder.strafeTo(new Vector2d(-20, -20)));
        // Add more actions as needed
    }

    public void examplePath(TrajectoryActionBuilder actionBuilder) {
        addAction(() -> actionBuilder.strafeTo(new Vector2d(10, 10)));
    }
}
