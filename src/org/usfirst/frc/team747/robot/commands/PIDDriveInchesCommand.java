package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Command;

public class PIDDriveInchesCommand extends PIDDriveRevolutionsCommand {
	
	public PIDDriveInchesCommand(double inches) {
		super(Robot.DRIVE_TRAIN.convertInchesToRevs(inches));
	}

}
