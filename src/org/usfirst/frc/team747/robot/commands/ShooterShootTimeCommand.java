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
public class ShooterShootTimeCommand extends Command {

	public ShooterShootTimeCommand(double timeOutAmount) {
		
    	setTimeout(timeOutAmount);
		requires(Robot.SHOOTER);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
	}

	@Override
	protected void execute() {
//		Robot.SHOOTER.setShooterSpeed(OI.getLeftShooterSpeed(), OI.getRightShooterSpeed(), OI.getIndexerSpeed());
//		System.out.println("RightRPM: " + Robot.SHOOTER.getMotorRightSpeed() + "  ");
//		System.out.println("LeftRPM: " + Robot.SHOOTER.getMotorLeftSpeed() + "  ");

		Robot.SHOOTER.shooterStart();
//		System.out.println("Shooting");
		
	}

	@Override
	protected boolean isFinished() {
//		return false;
        return isTimedOut();
	}
	
	protected void end(){
		Robot.SHOOTER.shooterStop();
	}
}
