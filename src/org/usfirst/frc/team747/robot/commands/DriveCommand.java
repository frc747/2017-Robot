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
        
        if (Math.abs(left) < 0.1) {
            left = 0;
        }
        if (Math.abs(right) < 0.1) {
            right = 0;
        }
        
        double speed = OI.JOYSTICK_DRIVER_LEFT.getThrottle();
        
        if (OI.BUTTON_DRIVE_SLOW_OPERATOR.get() || OI.BUTTON_DRIVE_SLOW_DRIVER.get()) {
            speed *= 0.50;
        }
        
        Robot.DRIVE_TRAIN.set(left * speed, right * speed);
        
//        System.out.println("LeftEncoder Ticks: " + Robot.DRIVE_TRAIN.talonDriveLeftPrimary.getPosition());
//        System.out.println("RightEncoder Ticks: " + Robot.DRIVE_TRAIN.talonDriveRightPrimary.getPosition());
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
