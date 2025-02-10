package com.huskyteers.paths

import com.acmerobotics.roadrunner.*
import kotlin.math.atan2

fun closeToBasketToRightmostBrick(actionBuilder: TrajectoryActionBuilder): TrajectoryActionBuilder {
    val angle = Rotation2d.exp(Math.toRadians(90.0))
    return actionBuilder.splineToSplineHeading(
        Pose2d(
            Vector2d(
                TILE_LENGTH * -2,
                TILE_LENGTH * -1
            ) - Vector2d(BRICK_WIDTH, BRICK_LENGTH) / 2.0 - clawOffset(angle),
            angle
        ),
        angle
    )
}

fun basketToCenterBrick(actionBuilder: TrajectoryActionBuilder): TrajectoryActionBuilder {
    val angle = Rotation2d.exp(Math.toRadians(90.0))
    return actionBuilder
        .setTangent(BASKET_ANGLE + Math.PI)
        .splineToSplineHeading(
            Pose2d(
                Vector2d(
                    TILE_LENGTH * -2 - BRICK_DISTANCE,
                    TILE_LENGTH * -1
                ) - Vector2d(BRICK_WIDTH, BRICK_LENGTH) / 2.0 - clawOffset(angle),
                angle
            ),
            angle
        )
}

fun basketToLeftmostBrick(
    actionBuilder: TrajectoryActionBuilder,
    rotateClawTo: (Double) -> Action
): TrajectoryActionBuilder {
    // The code takes the y displacement of the (leftmostBrick position) - (basket position)
    val yDistance = TILE_LENGTH * -2.0 - (TILE_LENGTH * -3 + BASKET_OFFSET.x - clawOffset(BASKET_ANGLE).x)
    val xDistance = TILE_LENGTH * -1 - (TILE_LENGTH * -3 + BASKET_OFFSET.y - clawOffset(BASKET_ANGLE).y)
    // This is the optimal angle where the robot can just go straight and turn at an angle. Time efficient
    val angleOffset = atan2(yDistance, xDistance)

    rotateClawTo(angleOffset)
    val angle = Rotation2d.exp(Math.toRadians(180.0-angleOffset))
    println(Math.toDegrees((BASKET_ANGLE + Math.PI).log()))
    return actionBuilder
        .afterDisp(0.0, rotateClawTo(angle.toDouble()))
        .setTangent(BASKET_ANGLE + Math.PI)
        .splineToSplineHeading(
            Pose2d(
                Vector2d(
                    TILE_LENGTH * -2.0 - BRICK_DISTANCE * 2,
                    TILE_LENGTH * -1
                ) - Vector2d(BRICK_WIDTH, BRICK_LENGTH) / 2.0 - clawOffset(angle),
                angle
            ),
            angle
        )
}

fun toBasket(actionBuilder: TrajectoryActionBuilder): TrajectoryActionBuilder {
    return actionBuilder
        .setTangent(Math.toRadians(270.0))
        .splineToSplineHeading(
            Pose2d(
                Vector2d(
                    TILE_LENGTH * -3,
                    TILE_LENGTH * -3
                ) + BASKET_OFFSET - clawOffset(BASKET_ANGLE),
                BASKET_ANGLE
            ),
            BASKET_ANGLE
        )
}

fun toParking(actionBuilder: TrajectoryActionBuilder): TrajectoryActionBuilder {
    val angleAboveWall = 20.0
    return actionBuilder
        .setTangent(Math.toRadians(angleAboveWall))
        .splineToSplineHeading(
            Pose2d(
                Vector2d(
                    TILE_LENGTH * 1.5,
                    TILE_LENGTH * -3 + HEIGHT / 2
                ),
                Math.toRadians(90.0),
            ),
            Math.toRadians(-angleAboveWall)
        )
}
