package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeCommand extends Command {
	
	private final boolean forward;
	
	public IntakeCommand() {
		this(true);	// By default drive forward.
	}

    public IntakeCommand(boolean forward) {
        requires(Robot.INTAKE);
        this.forward = forward;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.INTAKE.set(true, this.forward);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.INTAKE.set(false, this.forward);
    }

}
