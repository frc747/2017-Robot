package org.usfirst.frc.team747.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team747.robot.Robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI;

/**
 *
 */
public class NavXRotate extends Command {

	PIDController rotateController;
	double rotateToAngleRate;
	AHRS NAV_X = new AHRS(SPI.Port.kMXP); 
	
	static final double kP = 0.03;
	static final double kI = 0.00;
	static final double kD = 0.00;
	static final double kF = 0.00;
	
	static final double kToleranceDegrees = 2.0f;    
	    
	static final double kTargetAngleDegrees = 90.0f;
	    
    public NavXRotate() {
    	requires(Robot.DRIVE_TRAIN);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	rotateController = new PIDController(kP, kI, kD, kF, NAV_X, this);
        rotateController.setInputRange(-180.0f,  180.0f);
        rotateController.setOutputRange(-1.0, 1.0);
        rotateController.setAbsoluteTolerance(kToleranceDegrees);
        rotateController.setContinuous(true);
        rotateController.disable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
