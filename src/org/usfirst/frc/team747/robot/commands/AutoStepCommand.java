package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Negative degrees is anti-clockwise and positive degrees is clockwise
 */
public class AutoStepCommand extends Command {
    
    private static final double DISTANCE_PRECISION = 5;
    private static final double ANGLE_PRECISION = 5;

    private final double degrees;
    private final double distance;
    private final double speed;
    
    private double remainingDistance;
    private double remainingAngle;
    
    private double updatedNavXAngle;
    private double updatedSpeed;
    private double updatedDistance;

    public AutoStepCommand(double degrees, double distance, double speed) {
        requires(Robot.DRIVE_TRAIN);

        this.distance = distance;
        this.degrees = degrees;
        this.speed = speed;
    }

    protected void initialize() {
        this.remainingAngle = this.degrees;
        this.remainingDistance = this.distance;
        Robot.resetNavXAngle();
        Robot.DRIVE_TRAIN.resetBothEncoders();
    }

    protected void execute() {
        if (remainingDistance < 0){
            updatedNavXAngle = -Robot.getNavXAngle();
            updatedSpeed = -this.speed;
            updatedDistance = -Robot.DRIVE_TRAIN.simpleConvertTicksToInches(Robot.DRIVE_TRAIN.getCombindedEncoderPosition());
        } else {
            updatedNavXAngle = Robot.getNavXAngle();
            updatedSpeed = this.speed;
            updatedDistance = Robot.DRIVE_TRAIN.simpleConvertTicksToInches(Robot.DRIVE_TRAIN.getCombindedEncoderPosition());
        }
        this.remainingAngle -= updatedNavXAngle;
        this.remainingDistance -= updatedDistance;
        Robot.DRIVE_TRAIN.driveToTarget(0, this.remainingDistance, updatedSpeed);
        Robot.resetNavXAngle();
        Robot.DRIVE_TRAIN.resetBothEncoders();
        
        System.out.println("Angle Remaining: " + this.remainingAngle);
        System.out.println("Distance Remaining: " + this.remainingDistance);
    }

    protected boolean isFinished() {
       if (Math.abs(this.remainingAngle) < ANGLE_PRECISION && Math.abs(this.remainingDistance) < DISTANCE_PRECISION) {
            return true;
        }
        return false;
    }

    protected void end() {
        Robot.DRIVE_TRAIN.set(0, 0);
    }

    protected void interrupted() {
        this.end();
    }
}
