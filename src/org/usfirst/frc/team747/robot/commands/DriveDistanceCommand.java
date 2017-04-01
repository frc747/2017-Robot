package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

public class DriveDistanceCommand extends Command {
    
    private double inchesToTravel;
    private double speed;
    private double inchesToStop;
    private int SAFE_ZONE = 50;
    private double SAFE_SPEED = 0.1;
    private double speedFix;
    private Timer timer = new Timer();

    public DriveDistanceCommand(double distanceInches, double speed) {
        requires(Robot.DRIVE_TRAIN);
        
        this.inchesToTravel = distanceInches;
        this.speed = speed;
        this.speedFix = speed / Math.abs(speed) ;
    }

    protected void initialize() {
        Robot.DRIVE_TRAIN.resetLeftEncoder();
        Robot.DRIVE_TRAIN.resetRightEncoder();
        
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.configNominalOutputVoltage(+0.0f,-0.0f);
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.configPeakOutputVoltage(+12.0f, -12.0f);
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.configNominalOutputVoltage(+0.0f,-0.0f);
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.configPeakOutputVoltage(+12.0f, -12.0f);
        
//        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.reverseSensor(false);
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.setPID(   0, 0, 0, 1, 0, 0, 0);
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.setPID(  0, 0, 0, 1, 0, 0, 0);
        
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.setProfile(0);
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.setProfile(0);
        
        
    }

    protected void execute() {

        final double medianDistanceTraveled = (Robot.DRIVE_TRAIN.getLeftEncoderPosition() + Robot.DRIVE_TRAIN.getRightEncoderPosition())/2;
        final double medianInchesTraveled = Robot.DRIVE_TRAIN.convertTicksToInches(medianDistanceTraveled); 
        
        Robot.DRIVE_TRAIN.talonDriveLeftSlave.changeControlMode(TalonControlMode.Follower);
        Robot.DRIVE_TRAIN.talonDriveRightSlave.changeControlMode(TalonControlMode.Follower);
        
        
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.changeControlMode(TalonControlMode.Position);
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.changeControlMode(TalonControlMode.Position);
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.set(200);
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.set(-200);
        
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
//        return isDone;
        final double medianDistanceTraveled = (Robot.DRIVE_TRAIN.getLeftEncoderPosition() + Robot.DRIVE_TRAIN.getRightEncoderPosition())/2;
        final double medianInchesTraveled = Robot.DRIVE_TRAIN.convertTicksToInches(medianDistanceTraveled); 
        
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
