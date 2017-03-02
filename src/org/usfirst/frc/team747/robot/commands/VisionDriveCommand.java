package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.vision.Target;
import org.usfirst.frc.team747.robot.vision.VisionTracking;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class VisionDriveCommand extends Command {
    
    private static final double DRIVE_MAX_POWER = 0.3;
    private VisionTracking visionProcessor;
    private String targetId;
    
    private final double stopPoint;
    
    private double targetAngle = 0;
    private double targetDistance = 0;
    private boolean targetActive = false;
    private boolean targetFound = false;

    public VisionDriveCommand(VisionTracking visionProcessor, String targetId, double stopPoint) {
        this.visionProcessor = visionProcessor;
        this.targetId = targetId;
        this.stopPoint = stopPoint;
        requires(Robot.DRIVE_TRAIN);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        this.targetActive = false;
        Robot.DRIVE_TRAIN.resetEcoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Target target = this.visionProcessor.getTarget(this.targetId);
        
        double navXAngle = Robot.getNavXAngle();
        
        System.out.println(this.targetId);
        
        if (target != null) {
            this.targetActive = true;
            this.targetFound = true;
            
            double targetAngleFromCamera = target.getAngleDegrees();
            double targetDistanceFromCamera = target.getDistance();
            
            double targetDistanceXCamera = targetDistanceFromCamera * Math.sin(targetAngleFromCamera);
            double targetDistanceZCamera = targetDistanceFromCamera * Math.cos(targetAngleFromCamera);
            
            double navXOffsetX = 21;
            double navXOffsetZ = 28;
            
            double centerOffsetX = 6;
            double centerOffsetZ = 0;
            
            double targetDistanceX = targetDistanceXCamera + navXOffsetX;
            double targetDistanceZ = targetDistanceZCamera + navXOffsetZ;
            
            //double targetDistanceNavX = Math.sqrt(targetDistanceX * targetDistanceX + targetDistanceZ * targetDistanceZ);
            double targetAngleNavX = Math.atan(targetDistanceX / targetDistanceZ);
            
            double centerDistanceX = navXOffsetX - centerOffsetX;
            double centerDistanceZ = navXOffsetZ - centerOffsetZ;
            
            double centerAngleNavX = Math.atan(centerDistanceX / centerDistanceZ);
            
            double toRotate = targetAngleNavX - centerAngleNavX;
            
            this.targetAngle = navXAngle + toRotate;
            this.targetDistance = target.getDistance() - this.stopPoint;
            
        } else {
        	this.targetActive = false;
        }
        if (this.targetFound) {
            double position = Robot.DRIVE_TRAIN.convertTicksToInches(Robot.DRIVE_TRAIN.getCombindedEncoderPosition());
            
            if (!targetActive) {
                this.targetDistance -= position;
                this.targetAngle -= navXAngle;
            }
            
            System.out.println(targetDistance);
            System.out.println(targetAngle);
            
            Robot.DRIVE_TRAIN.driveToTarget(this.targetAngle, this.targetDistance, DRIVE_MAX_POWER);
        } else {
            Robot.DRIVE_TRAIN.stop();
        }
        Robot.DRIVE_TRAIN.resetEcoders();
        Robot.resetNavXAngle();
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
