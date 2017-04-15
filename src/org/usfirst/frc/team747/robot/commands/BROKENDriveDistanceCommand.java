package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

public class BROKENDriveDistanceCommand extends Command {
    
    private double inchesToTravel;
    private double speed;
    private double inchesToStop;
    private int SAFE_ZONE = 50;
    private double SAFE_SPEED = 0.1;
    private double speedFix;
    private Timer timer = new Timer();

    public BROKENDriveDistanceCommand(double distanceInches, double speed) {
        requires(Robot.DRIVE_TRAIN);
        
        this.inchesToTravel = distanceInches;
        this.speed = speed;
        this.speedFix = speed / Math.abs(speed) ;
    }

    protected void initialize() {
        Robot.DRIVE_TRAIN.resetLeftEncoder();
        Robot.DRIVE_TRAIN.resetRightEncoder();
        
    }

    protected void execute() {

        final double medianDistanceTraveled = (Robot.DRIVE_TRAIN.getLeftEncoderPosition() + Robot.DRIVE_TRAIN.getRightEncoderPosition())/2;
        final double medianInchesTraveled = Robot.DRIVE_TRAIN.simpleConvertTicksToInches(medianDistanceTraveled); 
        
        
//        if ((Math.abs(inchesToTravel) - Math.abs(medianInchesTraveled)) <= SAFE_ZONE) {
//            Robot.DRIVE_TRAIN.set(SAFE_SPEED * speedFix , SAFE_SPEED * speedFix);
//        } else if ((Math.abs(inchesToTravel) - Math.abs(medianInchesTraveled)) <= SAFE_ZONE) { 
//            Robot.DRIVE_TRAIN.set(SAFE_SPEED * speedFix , SAFE_SPEED * speedFix); 
//        } else {
//            Robot.DRIVE_TRAIN.set(this.speed, this.speed);
//        }

        
        
//    else if ((Math.abs(inchesToTravel) - Math.abs(medianInchesTraveled)) <= SAFE_ZONE) { 
//        Robot.DRIVE_TRAIN.set(SAFE_SPEED * speedFix , SAFE_SPEED * speedFix);
//    } else if ((Math.abs(inchesToTravel) - Math.abs(medianInchesTraveled)) <= SAFE_ZONE) { 
//        Robot.DRIVE_TRAIN.set(SAFE_SPEED * speedFix , SAFE_SPEED * speedFix);
//    }
        
//        this.speed *= ((Math.abs(inchesToTravel) - Math.abs(medianInchesTraveled)) / Math.abs(inchesToTravel));
//        if (this.speed < 0.15){
//            Robot.DRIVE_TRAIN.set(0.15, 0.15);
//        } else {
//            Robot.DRIVE_TRAIN.set(this.speed, this.speed);
//        }

//        
//        if (Math.abs(medianInchesTraveled) < Math.abs(inchesToTravel) && !isDone) {
//            Robot.DRIVE_TRAIN.set(speed, speed);
//        } else if (Math.abs(medianInchesTraveled) > Math.abs(inchesToTravel) && !isDone && !stop) {
//            Robot.DRIVE_TRAIN.set(0.3, 0.3);
//            isDone = (medianInchesTraveled) > Math.abs(inchesToTravel);
//            if (isDone = true) {
//                stop = true;
//            }
//        }
        

        
        System.out.println("LeftEncoder Ticks: " + Robot.DRIVE_TRAIN.talonDriveLeftPrimary.getPosition());
        System.out.println("RightEncoder Ticks: " + Robot.DRIVE_TRAIN.talonDriveRightPrimary.getPosition());
        
    }
    
    protected boolean isFinished() {

    	final double medianDistanceTraveled = (Robot.DRIVE_TRAIN.getLeftEncoderPosition() + Robot.DRIVE_TRAIN.getRightEncoderPosition())/2;
        final double medianInchesTraveled = Robot.DRIVE_TRAIN.simpleConvertTicksToInches(medianDistanceTraveled); 
        
        return (Math.abs(medianInchesTraveled) > Math.abs(inchesToTravel));
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
