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
public class IndexerReverseCommand extends Command {

	public IndexerReverseCommand() {
		requires(Robot.INDEXER);
	}

	@Override
	protected void execute() {

		Robot.INDEXER.reverseIndexer();
		
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
