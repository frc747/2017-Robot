package org.usfirst.frc.team747.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team747.robot.OI;
import org.usfirst.frc.team747.robot.Robot;

/**
 * ShooterShootCommand
 * 
 * Spins the shooter wheels using the joystick throttles as inputs for the
 * speed.
 */
public class ShootBallsCommand extends Command {

	public ShootBallsCommand() {
		requires(Robot.SHOOTER);
		requires(Robot.INDEXER);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
	}

	@Override
	protected void execute() {
		
		Robot.SHOOTER.setShooterSpeed();
		Robot.INDEXER.indexerStart();
		
		//Uncomment if you want to use Shooter voltage instead. //TODO change this to IF/THEN
		//Robot.SHOOTER.setShooterVoltage(.8,.8);
		
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
