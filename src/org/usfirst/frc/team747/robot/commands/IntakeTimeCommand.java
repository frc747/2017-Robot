package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeTimeCommand extends Command {
    
    public IntakeTimeCommand(double timeOutAmount) {
        requires(Robot.INTAKE);
        setTimeout(timeOutAmount);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.INTAKE.autonomous();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.INTAKE.set(false, true);
    }

}
