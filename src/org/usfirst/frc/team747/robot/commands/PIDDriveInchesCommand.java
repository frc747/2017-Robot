package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

public class PIDDriveInchesCommand extends PIDDriveRevolutionsCommand {
	
	public PIDDriveInchesCommand(double inches, double P, double I, double D) {
		super(Robot.DRIVE_TRAIN.convertInchesToRevs(inches), P, I, D);
	}

}
