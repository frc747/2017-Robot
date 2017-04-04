package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

import com.ctre.CANTalon.TalonControlMode;

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
    	
//        Robot.DRIVE_TRAIN.resetLeftEncoder();
//        Robot.DRIVE_TRAIN.resetRightEncoder();
    	Robot.DRIVE_TRAIN.resetBothEncoders();
        System.out.println("RESET Should Be 0 ****** left encoder =" + Double.toString(Robot.DRIVE_TRAIN.getLeftEncoderPosition()) + 
                "   right encoder get=" + Double.toString(Robot.DRIVE_TRAIN.getRightEncoderPosition()));
        
    }

    protected void execute() {
        
        Robot.DRIVE_TRAIN.set(speed, speed);
        
        System.out.println("EXECUTE ****** left encoder =" + Double.toString(Robot.DRIVE_TRAIN.getLeftEncoderPosition()) + 
                "  right encoder get=" + Double.toString(Robot.DRIVE_TRAIN.getRightEncoderPosition()));
    }

    protected boolean isFinished() {
        
        final double medianDistanceTraveled = (Robot.DRIVE_TRAIN.getLeftEncoderPosition() + Robot.DRIVE_TRAIN.getRightEncoderPosition())/2;
        
        final double medianInchesTraveled = Robot.DRIVE_TRAIN.convertTicksToInches(medianDistanceTraveled);
        
        System.out.println("isFINISHED ****** MedianTicks: " + Double.toString(medianDistanceTraveled) + 
                "   MedianInches CONVERTED: " + Double.toString(medianInchesTraveled));
        
        return Math.abs(medianInchesTraveled)  > Math.abs(inchesToTravel);
        
    }

    protected void end() {
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.changeControlMode(TalonControlMode.PercentVbus);
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.changeControlMode(TalonControlMode.PercentVbus);
        Robot.DRIVE_TRAIN.talonDriveLeftSlave.changeControlMode(TalonControlMode.PercentVbus);
        Robot.DRIVE_TRAIN.talonDriveRightSlave.changeControlMode(TalonControlMode.PercentVbus);
        Robot.DRIVE_TRAIN.set(0, 0);
    }

    protected void interrupted() {
        this.end();
    }
}
