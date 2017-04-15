package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Command;

public class PIDDriveInches extends PIDDriveRevolutions {
	
	public PIDDriveInches(double inches) {
		super(Robot.DRIVE_TRAIN.convertInchesToRevs(inches));
	}

}
