package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class PIDDriveRevolutionsCommand extends Command {
	
    //execute is called every 20ms and isFinished is called right after execute
    //add a button to Ryan's joystick that will default the drive train back to DriveWithJoystickCommand
    
	private final double revolutions;
	
	private final static double STOP_THRESHOLD = Robot.DRIVE_TRAIN.convertInchesToRevs(.375);
	
	private int onTargetCount;
	
	private final static int ON_TARGET_MINIMUM_COUNT = 25 * 20; //Checks to make sure you are on target for half a second (which is 25)
	
    private static final double MAX_VOLTAGE = 12;
    private static final double MIN_VOLTAGE = 0;
	
	public PIDDriveRevolutionsCommand(double revolutions) {
		this.revolutions = revolutions / 4;
		requires(Robot.DRIVE_TRAIN);
	}
	
	protected void initialize() {
	    onTargetCount = 0;
	    
	    Robot.DRIVE_TRAIN.resetBothEncoders();
	    Robot.resetNavXAngle();
        Robot.DRIVE_TRAIN.enablePositionControl();
        
        /*
         * April 20th: Brian - Comfortable PID values that we found are P = 3, I = 0, and D = 950.
         * Testing done with George and Corey, we dropped using I. Still need to test the use of
         * different drive distances. April 20th (end of the night): Brian - after testing shorter
         * distances, particularly 25 inches, we found that the drive train does not arrive at the
         * desired location.
         */
        
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.setPID(0.85, 0.0005, 85.0);
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.setPID(0.85, 0.0005, 85.0);
        
        //values for the long distance drive
//        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.setPID(3.0, 0.0001, 950.0);
//        Robot.DRIVE_TRAIN.talonDriveRightPrimary.setPID(3.0, 0.0001, 950.0);
        
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.configNominalOutputVoltage(+MIN_VOLTAGE,-MIN_VOLTAGE);
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.configPeakOutputVoltage(+MAX_VOLTAGE, -MAX_VOLTAGE);
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.configNominalOutputVoltage(+MIN_VOLTAGE,-MIN_VOLTAGE);
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.configPeakOutputVoltage(+MAX_VOLTAGE, -MAX_VOLTAGE);
        
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
