package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class PIDDriveDistanceCommand extends Command {
    
    private double inchesToTravel;
    private double ticksToTravel;
    private static double MOVE_THRESHOLD = 0.2;
    private double waitTime;
    private double endPosL, endPosR;
    private double errorLeft, errorRight;
    private int noProgressCountdown;

    public PIDDriveDistanceCommand(double distanceInches) {
        requires(Robot.DRIVE_TRAIN);
        
        this.inchesToTravel = distanceInches;
        this.ticksToTravel = Robot.DRIVE_TRAIN.convertInchesToTicks(distanceInches);
        errorLeft = 0;
        errorRight = 0;
        noProgressCountdown = 100;
    }

    protected void initialize() {
    	
//        Robot.DRIVE_TRAIN.resetLeftEncoder();
//        Robot.DRIVE_TRAIN.resetRightEncoder();
        Robot.DRIVE_TRAIN.resetBothEncoders();
        Robot.resetNavXAngle();
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.changeControlMode(TalonControlMode.Position);
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.changeControlMode(TalonControlMode.Position);
        
        System.out.println("RESET Should Be 0 ****** left encoder =" + Double.toString(Robot.DRIVE_TRAIN.getLeftEncoderPosition()) + 
                "   right encoder get=" + Double.toString(Robot.DRIVE_TRAIN.getRightEncoderPosition()));
        
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.setPID(   .1, 0, .5, 0, 0, 0, 0);
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.setPID(  .1, 0, .5, 0, 0, 0, 0);
        
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.configNominalOutputVoltage(+0.0f,-0.0f);
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.configPeakOutputVoltage(+12.0f, -12.0f);
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.configNominalOutputVoltage(+0.0f,-0.0f);
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.configPeakOutputVoltage(+12.0f, -12.0f);

        
        //Robot.DRIVE_TRAIN.talonEnableControl();
        
        Robot.DRIVE_TRAIN.setPositionPID(ticksToTravel, ticksToTravel);

        errorLeft = Math.abs(endPosL);
        errorRight = Math.abs(endPosR);
        noProgressCountdown = 100;
    }

    protected void execute() {
        
//        Robot.DRIVE_TRAIN.setPositionPID(ticksToTravel);
        
        System.out.println("EXECUTE ****** left encoder =" + Double.toString(Robot.DRIVE_TRAIN.getLeftEncoderPosition()) + 
                "  right encoder get=" + Double.toString(Robot.DRIVE_TRAIN.getRightEncoderPosition()));
    }

    protected boolean isFinished() {
        
        //might have to include a position threshold which makes the Drive Train stop within a plus or minus 5 ticks or so for example.
//        double currentPosition = Robot.DRIVE_TRAIN.getCombindedEncoderPosition();
//        if (currentPosition == ticksToTravel) {
//            return true;
//        }else {
//            return false;
//        }
        
        //attempting to use 1089's code
        
        double leftPos = Robot.DRIVE_TRAIN.talonDriveLeftPrimary.get();
        double rightPos = Robot.DRIVE_TRAIN.talonDriveRightPrimary.get();
        
        double newErrorLeft = Math.abs(endPosL) - Math.abs(leftPos);
        double newErrorRight = Math.abs(endPosR) - Math.abs(rightPos);
        
        if ((leftPos > endPosL - MOVE_THRESHOLD && leftPos < endPosL + MOVE_THRESHOLD)
                && (rightPos > endPosR - MOVE_THRESHOLD && rightPos < endPosR + MOVE_THRESHOLD)) {
            return true;
        }
        
        if ((newErrorLeft - errorLeft < MOVE_THRESHOLD && newErrorRight - errorRight < MOVE_THRESHOLD)) {
            noProgressCountdown--;
            if(noProgressCountdown <= 0) {
                return true;
            }
        } else {
            errorLeft = newErrorLeft;
            errorRight = newErrorRight;
        }
        return false;
        
    }

    protected void end() {
        
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.changeControlMode(TalonControlMode.PercentVbus);
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.changeControlMode(TalonControlMode.PercentVbus);
        Robot.DRIVE_TRAIN.talonDriveLeftSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        Robot.DRIVE_TRAIN.talonDriveRightSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        Robot.DRIVE_TRAIN.set(0, 0);
        System.out.println("Left Encoder: " + Robot.DRIVE_TRAIN.getLeftEncoderPosition());
        System.out.println("Right Encoder: " + Robot.DRIVE_TRAIN.getRightEncoderPosition());
//        Robot.DRIVE_TRAIN.resetBothEncoders();
        Robot.resetNavXAngle();
    }

    protected void interrupted() {
        this.end();
    }
}
