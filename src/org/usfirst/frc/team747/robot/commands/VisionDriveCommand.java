package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.vision.Target;
import org.usfirst.frc.team747.robot.vision.VisionTracking;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class VisionDriveCommand extends Command {
	
	public static final int X = 0;
	public static final int Z = 1;
	public static final int PITCH = 2;
	public static final int YAW = 2;

    
    private static final double DRIVE_MAX_POWER = 0.3;
    private VisionTracking visionProcessor;
    private String targetId;
    
    private double targetAngle = 0;
    private double targetDistance = 0;
    private boolean targetActive = false;
    private boolean targetFound = false;

    public VisionDriveCommand(VisionTracking visionProcessor, String targetId) {
        this.visionProcessor = visionProcessor;
        this.targetId = targetId;
        requires(Robot.DRIVE_TRAIN);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        this.targetActive = false;
        Robot.DRIVE_TRAIN.resetBothEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Target visionTarget = this.visionProcessor.getTarget(this.targetId);
        
        double navXAngle = Robot.getNavXAngleRadians();
        
        System.out.println(this.targetId);
        
        if (visionTarget != null) {
            this.targetActive = true;
            this.targetFound = true;
            
            double[] cameraPosition = this.cameraPosition();
            double[] alignmentPosition = this.alignmentPostion();
            
            double[] cameraTarget = new double[3];
            
            cameraTarget[X] = Math.sin(visionTarget.getAngle()) * visionTarget.getDistance();
            double visionTargetDPrime = Math.cos(visionTarget.getAngle()) * visionTarget.getDistance();
            cameraTarget[Z] = Math.cos(cameraPosition[PITCH]) * visionTargetDPrime;
            cameraTarget[YAW] = visionTarget.getAngle();
            
            double[] visionTargetNavX = new double[3];
            
            visionTargetNavX[X] = cameraTarget[X] + cameraPosition[X];
            visionTargetNavX[Z] = cameraTarget[Z] + cameraPosition[Z];
            visionTargetNavX[YAW] = Math.atan(cameraTarget[X] / cameraTarget[Z]);
            
            this.targetAngle = navXAngle + visionTargetNavX[YAW];
            this.targetDistance = cameraTarget[Z];

        } else {
        	this.targetActive = false;
        }
        if (this.targetFound) {
            double position = Robot.DRIVE_TRAIN.simpleConvertTicksToInches(Robot.DRIVE_TRAIN.getCombindedEncoderPosition());
            
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
    
    /**
     * @return double[] values {X, Z, Pitch}
     */
    protected double[] cameraPosition() {
    	double[] pos = {0.0, 0.0, 0.0};
    	return pos;
    }

    /**
     * @return double[] values {X, Z}
     */
    protected double[] alignmentPostion() {
    	double[] pos = {0.0, 0.0};
    	return pos;
    }
}
