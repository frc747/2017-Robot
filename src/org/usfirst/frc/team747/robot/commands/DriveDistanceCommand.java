package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveDistanceCommand extends Command {
    
    private double inchesToTravel;
    private double speed;
    
    private final double threshold = 15; // Inches
    private final double minSpeed = 0.1;

    public DriveDistanceCommand(double distanceInches, double speed) {
        requires(Robot.DRIVE_TRAIN);
        
        this.inchesToTravel = distanceInches;
        this.speed = speed;
    }

    protected void initialize() {
        Robot.DRIVE_TRAIN.resetLeftEncoder();
        Robot.DRIVE_TRAIN.resetRightEncoder();
    }

    protected void execute() {
        final double medianInchesTraveled = getDinstanceTravled();
        final double medianInchesTraveledAbs = Math.abs(medianInchesTraveled);
        
        if (medianInchesTraveledAbs < threshold) {
        	speed = Math.max(medianInchesTraveledAbs/threshold, minSpeed) * speed / Math.abs(speed);
        }
    	
        Robot.DRIVE_TRAIN.set(speed, speed);

        System.out.println("LeftEncoder Ticks: " + Robot.DRIVE_TRAIN.talonDriveLeftPrimary.getPosition());
        System.out.println("RightEncoder Ticks: " + Robot.DRIVE_TRAIN.talonDriveRightPrimary.getPosition());
    }
    
    protected boolean isFinished() {
        final double medianInchesTraveled = getDinstanceTravled();
        
        return Math.abs(medianInchesTraveled) > Math.abs(inchesToTravel);        
    }
    
    protected double getDinstanceTravled() {
    	 final double medianDistanceTraveled = (Robot.DRIVE_TRAIN.getLeftEncoderPosition() + Robot.DRIVE_TRAIN.getRightEncoderPosition())/2;
         
         final double medianInchesTraveled = Robot.DRIVE_TRAIN.convertTicksToInches(medianDistanceTraveled);
         
         return medianInchesTraveled;
    }

    protected void end() {
        Robot.DRIVE_TRAIN.set(0, 0);
    }

    protected void interrupted() {
        this.end();
    }
}
