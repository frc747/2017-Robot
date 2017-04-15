package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

public class NewDriveDistanceStraightCommand extends Command {
    
    private double navXInitialAngle = 0;
    private double encoderEndThreshold = 0;
    private double encodersDistanceDriven = 0;
    private double driveSpeed = 0;
    private double timeout;
    private boolean firstRun;
    private boolean goingForward;
    private Timer timer = new Timer();
    
    public NewDriveDistanceStraightCommand(double distanceInches, double speed, double timeout) {
        requires(Robot.DRIVE_TRAIN);
        encoderEndThreshold = distanceInches;
        firstRun=true;
        driveSpeed = speed;
        this.timeout = timeout;
    }
    
    public NewDriveDistanceStraightCommand(double distanceInches, double speed){
        this(distanceInches, speed, 15);
    }

    private static double[] driveStraightAngle(double powSetpoint, double angleDifference, double tuningConstant) {                                                                                                                      //memes
        
        double firstValue = powSetpoint + (angleDifference * tuningConstant);
        double secondValue = powSetpoint - (angleDifference * tuningConstant);
        
        return new double[] {firstValue, secondValue};
    }
    
    protected void initialize() {
//        Robot.DRIVE_TRAIN.resetLeftEncoder();
//        Robot.DRIVE_TRAIN.resetRightEncoder();
    }

    protected void execute() {
    	if(firstRun) {
    	    timer.stop();
    	    timer.reset();
    	    timer.start();
    	    Robot.DRIVE_TRAIN.resetBothEncoders();
    	    Robot.resetNavXAngle();
    	    firstRun = false;
    	}
        
    	encodersDistanceDriven = Robot.DRIVE_TRAIN.simpleConvertTicksToInches(Robot.DRIVE_TRAIN.getCombindedEncoderPosition());
    	
    	double[] pow = {0, 0};
    	
    	if(encoderEndThreshold >= 0) {
    	    pow = driveStraightAngle(driveSpeed, Robot.getNavXAngle(), 0.01); //Forward
    	//    Robot.DRIVE_TRAIN.autoDrive.drive(pow[0], pow[1]);
    	}else{
    	    pow = driveStraightAngle(-driveSpeed, Robot.getNavXAngle(), 0.01); //Backward
    	//    Robot.DRIVE_TRAIN.autoDrive.drive(pow[0], pow[1]);
    	}
    	
        System.out.println("EXECUTE SpeedOFFSET ****** left encoder =" + Double.toString(Robot.DRIVE_TRAIN.getLeftEncoderPosition()) + 
                "  right encoder get=" + Double.toString(Robot.DRIVE_TRAIN.getRightEncoderPosition()));
    }

    protected boolean isFinished() {
        
        boolean end = false;
        
        if(firstRun) {
            Robot.DRIVE_TRAIN.resetBothEncoders();
            navXInitialAngle = Robot.getNavXAngle();
            encodersDistanceDriven = 0;
            firstRun = false;
            return false;
        }
        
        encodersDistanceDriven = Robot.DRIVE_TRAIN.simpleConvertTicksToInches(Robot.DRIVE_TRAIN.getCombindedEncoderPosition());
        
        if(encoderEndThreshold>=0) {
            end = (encodersDistanceDriven > encoderEndThreshold)|| timer.get() > timeout; 
        } else {
            end = (encodersDistanceDriven < encoderEndThreshold)|| timer.get() > timeout;
        }
        
        if(end) {
            encodersDistanceDriven = 0;
            firstRun = true;
            timer.stop();
            timer.reset();
        }
        
        return end;
    }

    protected void end() {
        Robot.DRIVE_TRAIN.set(0, 0);
    }

    protected void interrupted() {
        this.end();
    }
}
