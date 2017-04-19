package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class PIDDriveRevolutionsCommand extends Command {
	
    //execute is called every 20ms and isFinished is called right after execute
    //add a button to Ryan's joystick that will default the drive train back to DriveWithJoystickCommand
    
	private final double revolutions;
	
	private final static double STOP_THRESHOLD = Robot.DRIVE_TRAIN.convertInchesToRevs(.375);
	
	private int onTargetCount;
	
	private final static int ON_TARGET_MINIMUM_COUNT = 25; //Checks to make sure you are on target for half a second
	
	public PIDDriveRevolutionsCommand(double revolutions) {
		this.revolutions = revolutions;
		requires(Robot.DRIVE_TRAIN);
	}
	
	protected void initialize() {
	    onTargetCount = 0;
	    
	    Robot.DRIVE_TRAIN.resetBothEncoders();
	    Robot.resetNavXAngle();
        Robot.DRIVE_TRAIN.enablePositionControl();
        Robot.DRIVE_TRAIN.setPID(revolutions, revolutions);
	}
	
	protected void execute() {
	    
	}
	
	@Override
	protected boolean isFinished() {
		double leftPosition = Robot.DRIVE_TRAIN.getLeftPosition();
		double rightPosition = Robot.DRIVE_TRAIN.getRightPosition();
		
		if (leftPosition > (revolutions - STOP_THRESHOLD) && leftPosition < (revolutions + STOP_THRESHOLD) &&
		    rightPosition > (revolutions - STOP_THRESHOLD) && rightPosition < (revolutions + STOP_THRESHOLD)) {
		    onTargetCount++;
		} else {
		    onTargetCount = 0;
		}
		
		return (onTargetCount > ON_TARGET_MINIMUM_COUNT);
	}
	
	protected void end() {
		Robot.DRIVE_TRAIN.enableVBusControl();
		Robot.DRIVE_TRAIN.resetBothEncoders();
		Robot.resetNavXAngle();
		Robot.DRIVE_TRAIN.stop();
	}
	
	protected void interrupted() {
		end();
	}

}
