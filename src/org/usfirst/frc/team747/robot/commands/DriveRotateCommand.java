package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Negative degrees is anti-clockwise and positive degrees is clockwise
 */
public class DriveRotateCommand extends Command {
    
    private double speed;
    private double degrees;
    
    private final double threshold = 15; // Degrees
    private final double minSpeed = 0.1;
    
    private final double precission = 1.0;

    public DriveRotateCommand(double speed, double degrees) {
        requires(Robot.DRIVE_TRAIN);
        
        this.speed = speed;
        this.degrees = degrees;
    }

    protected void initialize() {
        Robot.resetNavXAngle();
    }

    protected void execute() {
    	double remaining = remainingDegrees();
    	double remainingAbs = Math.abs(remaining);
        
        if (remainingAbs < threshold) {
        	speed = Math.max(remainingAbs/threshold, minSpeed) * speed / Math.abs(speed);
        }
        
        Robot.DRIVE_TRAIN.set(speed, -speed);
        
    }

    protected boolean isFinished() {
//    	System.out.println("NavX: " + Robot.getNavXAngle());
//    	System.out.println("Robot: " + this.degrees);
    	boolean stopRotate = false;
    	
    	double remaining = remainingDegrees();
    	double remainingAbs = Math.abs(remaining);
    	
    	if (remainingAbs < precission){
//    		System.out.println("****************SHOULD BE STOPPING!!!");
    		stopRotate = true;
    	}
    	return stopRotate;
        
    }
    
    protected double remainingDegrees() {
    	return this.degrees - Robot.getNavXAngle();
    }

    protected void end() {
        Robot.DRIVE_TRAIN.set(0, 0);
    }

    protected void interrupted() {
        this.end();
    }
}
