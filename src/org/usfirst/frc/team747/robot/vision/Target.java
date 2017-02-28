package org.usfirst.frc.team747.robot.vision;

public class Target {

    private final double angle;
    private final double distance;

    public Target(double angle, double distance) {
        this.angle = angle;
        this.distance = distance;
    }

    public double getAngle() {
        return this.angle;
    }

    public double getAngleDegrees() {
        return Math.toDegrees(this.angle);
    }

    public double getDistance() {
        return this.distance;
    }

}
