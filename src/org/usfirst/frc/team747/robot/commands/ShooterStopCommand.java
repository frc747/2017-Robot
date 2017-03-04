package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * ShooterStopCommand
 * 
 * Stops both shooter wheels from spinning by setting their speed to 0.
 */
public class ShooterStopCommand extends Command {

	public ShooterStopCommand() {
		requires(Robot.SHOOTER);
	}

	/**
	 * Stops the shooter wheels by setting their speed to 0
	 */
	@Override
	protected void execute() {
//		Robot.SHOOTER.setShooterSpeed(0.0, 0.0, 0.0);
		Robot.SHOOTER.shooterStop();
	}

	/**
	 * This command is the default command for the shooter subsystem. As such it
	 * will be scheduled automatically when the subsystem is not in use by
	 * another command.
	 * 
	 * isFinished() does not need to ever return true in this case, since it
	 * will be interrupted when another command using the shooter subsystem is
	 * scheduled.
	 */
	@Override
	protected boolean isFinished() {
		return false;
	}
}
