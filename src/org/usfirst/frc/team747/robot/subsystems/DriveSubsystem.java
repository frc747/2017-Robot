package org.usfirst.frc.team747.robot.subsystems;

import org.usfirst.frc.team747.robot.commands.DriveCommand;
import org.usfirst.frc.team747.robot.maps.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveSubsystem extends Subsystem {

	private CANTalon talonDriveLeftPrimary = new CANTalon(RobotMap.DriveTrain.LEFT_FRONT.getValue()),
			talonDriveLeftSlave = new CANTalon(RobotMap.DriveTrain.LEFT_REAR.getValue()),
			talonDriveRightPrimary = new CANTalon(RobotMap.DriveTrain.RIGHT_FRONT.getValue()),
			talonDriveRightSlave = new CANTalon(RobotMap.DriveTrain.RIGHT_REAR.getValue());

	public DriveSubsystem() {
		super();
		this.talonDriveLeftPrimary.setInverted(false);
		this.talonDriveLeftSlave.setInverted(false);
		this.talonDriveRightPrimary.setInverted(true);
		this.talonDriveRightSlave.setInverted(true);

		this.talonDriveLeftSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		this.talonDriveLeftSlave.set(this.talonDriveLeftPrimary.getDeviceID());
		
		this.talonDriveRightSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		this.talonDriveRightSlave.set(this.talonDriveRightPrimary.getDeviceID());
	}
	
	@Override
	protected void initDefaultCommand() {
		this.setDefaultCommand(new DriveCommand());
	}

	public void changeControlMode(TalonControlMode mode) {
		this.talonDriveLeftPrimary.changeControlMode(mode);
		this.talonDriveRightPrimary.changeControlMode(mode);
	}
	
	public void set(double left, double right) {
		this.talonDriveLeftPrimary.set(left);
		this.talonDriveRightPrimary.set(right);
	}

	public void stop() {
		TalonControlMode mode = this.talonDriveLeftPrimary.getControlMode();

		double left = 0;
		double right = 0;
		
		switch (mode) {
		case Position:
			left = this.talonDriveLeftPrimary.getPosition();
			right = this.talonDriveRightPrimary.getPosition();
			break;
		case PercentVbus:
		case Speed:
		case Voltage:
		default:
			// Values should be 0;
			break;
		}
		
		this.set(left, right);
	}

}
