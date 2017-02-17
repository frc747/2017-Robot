package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.OI;
import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.maps.DriverStation;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveCommand extends Command {

    public DriveCommand() {
        requires(Robot.DRIVE_TRAIN);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.DRIVE_TRAIN.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double left = -OI.JOYSTICK_DRIVER_LEFT.getRawAxis(DriverStation.Joystick.AXIS_Y.getValue());
    	double right = -OI.JOYSTICK_DRIVER_RIGHT.getRawAxis(DriverStation.Joystick.AXIS_Y.getValue());
    	
    	Robot.DRIVE_TRAIN.set(left, right);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.DRIVE_TRAIN.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
