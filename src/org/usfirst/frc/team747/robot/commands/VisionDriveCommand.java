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
        
        double navXAngle = Robot.getNavXAngleRadians();
        
        System.out.println(this.targetId);
        
        if (target != null) {
            this.targetActive = true;
            this.targetFound = true;
            
            /* Rich Stuff
             * Commented Out
             */
            
//            double targetAngleFromCamera = Math.toRadians(target.getAngleDegrees());
//            double targetDistanceFromCamera = target.getDistance();
//            
//            double targetDistanceXCamera = targetDistanceFromCamera * Math.sin(targetAngleFromCamera);
//            double targetDistanceZCamera = targetDistanceFromCamera * Math.cos(targetAngleFromCamera);
//                        
//            double navXOffsetX = 14.5;
//            double navXOffsetZ = 16.375;
//            double centerOffsetX = 8.125;
//            double centerOffsetZ = 0;                             
//            double targetDistanceX = targetDistanceXCamera + navXOffsetX;
//            double targetDistanceZ = targetDistanceZCamera + navXOffsetZ;
//            
//            //double targetDistanceNavX = Math.sqrt(targetDistanceX * targetDistanceX + targetDistanceZ * targetDistanceZ);
//            double targetAngleNavX = Math.atan(targetDistanceX / targetDistanceZ);
//            
//            double centerDistanceX = navXOffsetX - centerOffsetX;
//            double centerDistanceZ = navXOffsetZ - centerOffsetZ;            
//            
//            double centerAngleNavX = Math.atan(centerDistanceX / centerDistanceZ);
//            
//            double toRotate = targetAngleNavX - centerAngleNavX;
//            
//            this.targetAngle = navXAngle + toRotate;
//            this.targetDistance = target.getDistance() - this.stopPoint;
//        } else {
//            this.targetActive = false;
//        }
//        if (this.targetFound) {
//            double position = Robot.DRIVE_TRAIN.convertTicksToInches(Robot.DRIVE_TRAIN.getCombindedEncoderPosition());
//            
//            if (!targetActive) {
//                this.targetDistance -= position;
//                this.targetAngle -= navXAngle;
//            }
//            
//            System.out.println(targetDistance);
//            System.out.println(targetAngle);
//            
//            Robot.DRIVE_TRAIN.driveToTarget(this.targetAngle, this.targetDistance, DRIVE_MAX_POWER);
//        } else {
//            Robot.DRIVE_TRAIN.stop();
//        }
//        Robot.DRIVE_TRAIN.resetEcoders();
//        Robot.resetNavXAngle();
            
            //Rich Code End
           
            /* Brian Stuff
             * Rewriting logic to see if I can get it to work
             */
            double targetAngleFromCamera = Math.toRadians(target.getAngleDegrees());
            double targetDistanceFromCamera = target.getDistance();
            
            double targetDistanceXCamera = targetDistanceFromCamera * Math.sin(targetAngleFromCamera);
            double targetDistanceZCamera = targetDistanceFromCamera * Math.cos(targetAngleFromCamera);

            //distance of the camera from our origin (front-right side from robot's perspective)
            //double cameraOffsetX = 6.375;
            double cameraOffsetX = 22.75;
            double cameraOffsetZ = 0;
            
            //this is currently the front-center of the robot and is not likely to change
            double gearSecureOffsetX = 14.625;
            double gearSecureOffsetZ = 0;
            
            //navX is at the center of the robot
            double navXOffsetX = 14;
            double navXOffsetZ = 15;

            double targetPositionX = targetDistanceXCamera + cameraOffsetX;
            double targetPositionZ = targetDistanceZCamera - cameraOffsetZ;

            //these are the distances the target is from the front-center part of the robot
            double targetDistanceXGearSecure = targetPositionX - gearSecureOffsetX;
            double targetDistanceZGearSecure = targetPositionZ + gearSecureOffsetZ;
            
            double targetAngleFromGearSecure = Math.atan(targetDistanceXGearSecure / targetDistanceZGearSecure);
            double targetDistanceFromGearSecure = Math.hypot(targetDistanceXGearSecure, targetDistanceZGearSecure);
            
            //distances the target is from the NavX
            double targetDistanceXNavX = targetPositionX + navXOffsetX;
            double targetDistanceZNavX = targetPositionZ + navXOffsetZ;
            
            double targetAngleFromNavX = Math.atan(targetDistanceXNavX / targetDistanceZNavX);
            double targetDistanceFromNavX = Math.hypot(targetDistanceXNavX, targetDistanceZNavX);
            
            //Use the Law of Sines to relate the angle with respect to the NavX to the angle with respect to the "Gear Secure" location
            //double navXWithRelationToGearSecure = Math.asin(Math.sin(180 - targetAngleFromGearSecure) * targetDistanceFromGearSecure / targetDistanceFromNavX);
            
            double toRotate = targetAngleFromNavX; 
            
            this.targetAngle = navXAngle + toRotate;
            this.targetDistance = targetDistanceZGearSecure - this.stopPoint;

        } else {
        	this.targetActive = false;
        }
        if (this.targetFound) {
            double position = Robot.DRIVE_TRAIN.convertTicksToInches(Robot.DRIVE_TRAIN.getCombindedEncoderPosition());
            
            if (!targetActive) {
                this.targetDistance -= position;
                this.targetAngle -= navXAngle;
            }
            
            System.out.println(this.targetDistance);
            System.out.println(this.targetAngle);
            
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
