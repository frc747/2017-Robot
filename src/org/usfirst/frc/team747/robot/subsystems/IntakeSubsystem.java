package org.usfirst.frc.team747.robot.subsystems;

import org.usfirst.frc.team747.robot.commands.DriveCommand;
import org.usfirst.frc.team747.robot.maps.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeSubsystem extends Subsystem {

	private CANTalon talonIntake = new CANTalon(RobotMap.Intake.INTAKE.getValue());

	public IntakeSubsystem() {
		super();
	}
	
	@Override
	protected void initDefaultCommand() {
		this.setDefaultCommand(new DriveCommand());
	}
	
	public void set(boolean run) {
		this.set(run, false);
	}
	
	public void set(boolean run, boolean forward) {
		double speed = 0;
		if (run) {
			speed = 1.0;
			if (!forward) {
				speed *= -1;
			}
		}
		this.talonIntake.set(speed);
	}

	public void stop() {
		this.set(false);
	}

}
