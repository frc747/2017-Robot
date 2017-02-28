package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveDistanceCommand extends Command {
    
    private double inchesToTravel;
    private double speed;

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
        Robot.DRIVE_TRAIN.set(speed, speed);

        System.out.println("LeftEncoder Ticks: " + Robot.DRIVE_TRAIN.talonDriveLeftPrimary.getPosition());
        System.out.println("RightEncoder Ticks: " + Robot.DRIVE_TRAIN.talonDriveRightPrimary.getPosition());
    }
    
    protected boolean isFinished() {
        
        final double medianDistanceTraveled = (Robot.DRIVE_TRAIN.getLeftEncoderPosition() + Robot.DRIVE_TRAIN.getRightEncoderPosition())/2;
        
        final double medianInchesTraveled = Robot.DRIVE_TRAIN.convertEncoderTicksToInches(medianDistanceTraveled);
        
        return Math.abs(medianInchesTraveled) > Math.abs(inchesToTravel);        
    }

    protected void end() {
        Robot.DRIVE_TRAIN.set(0, 0);
    }

    protected void interrupted() {
        this.end();
    }
}
