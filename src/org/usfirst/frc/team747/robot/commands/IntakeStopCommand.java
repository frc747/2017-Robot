package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeStopCommand extends Command {

    public IntakeStopCommand() {
        requires(Robot.INTAKE);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.INTAKE.set(false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.INTAKE.set(false);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.INTAKE.set(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	
    }

}
