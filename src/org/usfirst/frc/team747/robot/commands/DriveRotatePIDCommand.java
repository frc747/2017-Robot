package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *Negative degrees is anti-clockwise and positive degrees is clockwise
 */
public class DriveRotatePIDCommand extends Command {
    
    private double speed;
    private double degrees;

    public DriveRotatePIDCommand(double speed, double degrees) {
        requires(Robot.DRIVE_TRAIN);
        
        this.speed = speed;
        this.degrees = degrees;
    }

    protected void initialize() {
        Robot.resetNavXAngle();
    }

    protected void execute() {
        Robot.DRIVE_TRAIN.set(speed, -speed);
        
    }

    protected boolean isFinished() {
    	System.out.println("NavX: " + Robot.getNavXAngle());
    	System.out.println("Robot: " + this.degrees);
    	boolean stopRotate = false;
    	
    	if (Robot.getNavXAngle() <= (this.degrees + 1) && Robot.getNavXAngle() >= (this.degrees - 1)){
    		System.out.println("****************SHOULD BE STOPPING!!!");
    		stopRotate = true;
    	}
    	return stopRotate;
        
    }

    protected void end() {
        Robot.DRIVE_TRAIN.set(0, 0);
    }

    protected void interrupted() {
        this.end();
    }
}
