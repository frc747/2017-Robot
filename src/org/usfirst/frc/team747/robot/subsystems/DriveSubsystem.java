package org.usfirst.frc.team747.robot.subsystems;

import org.usfirst.frc.team747.robot.commands.DriveCommand;
import org.usfirst.frc.team747.robot.maps.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveSubsystem extends Subsystem {

	private CANTalon talonDriveLeftFront = new CANTalon(RobotMap.DriveTrain.LEFT_FRONT.getValue()),
			talonDriveLeftRear = new CANTalon(RobotMap.DriveTrain.LEFT_REAR.getValue()),
			talonDriveRightFront = new CANTalon(RobotMap.DriveTrain.RIGHT_FRONT.getValue()),
			talonDriveRightRear = new CANTalon(RobotMap.DriveTrain.RIGHT_REAR.getValue());

	public DriveSubsystem() {
		super();
		this.talonDriveLeftFront.setInverted(false);
		this.talonDriveLeftRear.setInverted(false);
		this.talonDriveRightFront.setInverted(true);
		this.talonDriveRightRear.setInverted(true);
	}
	
	@Override
	protected void initDefaultCommand() {
		this.setDefaultCommand(new DriveCommand());
	}

	public void changeControlMode(TalonControlMode mode) {
		this.talonDriveLeftFront.changeControlMode(mode);
		this.talonDriveLeftRear.changeControlMode(mode);
		this.talonDriveRightFront.changeControlMode(mode);
		this.talonDriveRightRear.changeControlMode(mode);
	}
	
	public void set(double left, double right) {
		this.set(left, left, right, right);
	}
	
	public void set(double leftFront, double leftRear, double rightFront, double rightRear) {
		this.talonDriveLeftFront.set(leftFront);
		this.talonDriveLeftRear.set(leftRear);
		this.talonDriveRightFront.set(rightFront);
		this.talonDriveRightRear.set(rightRear);
	}

	public void stop() {
		TalonControlMode mode = this.talonDriveLeftFront.getControlMode();

		double leftFront = 0;
		double leftRear = 0;
		double rightFront = 0;
		double rightRear = 0;
		
		switch (mode) {
		case Position:
			leftFront = this.talonDriveLeftFront.getPosition();
			leftRear = this.talonDriveLeftRear.getPosition();
			rightFront = this.talonDriveRightFront.getPosition();
			rightRear = this.talonDriveRightRear.getPosition();
			break;
		case PercentVbus:
		case Speed:
		case Voltage:
		default:
			// Values should be 0;
			break;
		}
		
		this.set(leftFront, leftRear, rightFront, rightRear);
	}

}
