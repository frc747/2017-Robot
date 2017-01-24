package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TransferStopCommand extends Command {

    public TransferStopCommand() {
    	requires(Robot.TRANSFER_SYSTEM);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.TRANSFER_SYSTEM.stopTransfer();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }
}
