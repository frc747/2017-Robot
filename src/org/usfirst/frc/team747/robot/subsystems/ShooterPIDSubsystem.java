package org.usfirst.frc.team747.robot.subsystems;

import org.usfirst.frc.team747.robot.commands.ShooterStopCommand;
import org.usfirst.frc.team747.robot.maps.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class ShooterPIDSubsystem extends PIDSubsystem {

	public ShooterPIDSubsystem(double p, double i, double d) {
		super(p, i, d);
	}

	private CANTalon talonLeft1Shooter = new CANTalon(Robot.Shooter.LEFT_1.getValue()),
			talonLeft2Shooter = new CANTalon(Robot.Shooter.LEFT_2.getValue()),
			talonRight1Shooter = new CANTalon(Robot.Shooter.RIGHT_1.getValue()),
			talonRight2Shooter = new CANTalon(Robot.Shooter.RIGHT_2.getValue()),
			talonIndexer = new CANTalon(Robot.Shooter.INDEXER.getValue());

	public void initDefaultCommand() {
		setDefaultCommand(new ShooterStopCommand());
		talonLeft1Shooter.setInverted(true);
		talonLeft2Shooter.setInverted(true);
	}

	protected double returnPIDInput() {
		return 0.0;
	}

	protected void usePIDOutput(double outputLeft, double outputRight, double indexerOutput) {
		talonLeft1Shooter.set(outputLeft);
		talonLeft2Shooter.set(outputLeft);
		talonRight1Shooter.set(outputRight);
		talonRight2Shooter.set(outputRight);
		talonIndexer.set(indexerOutput);
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub

	}
}
