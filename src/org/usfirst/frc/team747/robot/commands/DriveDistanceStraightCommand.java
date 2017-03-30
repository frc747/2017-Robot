package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveDistanceStraightCommand extends Command {
    
    private double inchesToTravel,
    			   speed,
    			   targetAngle;

    public DriveDistanceStraightCommand(double distanceInches, double speed, double angle) {
        requires(Robot.DRIVE_TRAIN);
        
        this.inchesToTravel = distanceInches;
        this.speed = speed;
        this.targetAngle = angle;
        
        
    }

    protected void initialize() {
        Robot.DRIVE_TRAIN.resetLeftEncoder();
        Robot.DRIVE_TRAIN.resetRightEncoder();
    }

    protected void execute() {
    	Robot.DRIVE_TRAIN.driveStraight(speed, targetAngle);
        
    }

    protected boolean isFinished() {
        
        
        final double medianDistanceTraveled = (Robot.DRIVE_TRAIN.getLeftEncoderPosition() + Robot.DRIVE_TRAIN.getRightEncoderPosition())/2;
        
        final double medianInchesTraveled = Robot.DRIVE_TRAIN.convertInchesToTicks(medianDistanceTraveled);
        
        return Math.abs(medianInchesTraveled)  >= Math.abs(inchesToTravel);
    }

    protected void end() {
        Robot.DRIVE_TRAIN.set(0, 0);
    }

    protected void interrupted() {
        this.end();
    }
}
