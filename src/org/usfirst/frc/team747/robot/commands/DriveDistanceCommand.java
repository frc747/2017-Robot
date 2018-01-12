package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

import com.ctre.phoenix.motorcontrol.can.*;          //fixed both of these to the potential correct imports
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

import edu.wpi.first.wpilibj.command.Command;

public class DriveDistanceCommand extends Command {
    
    private double inchesToTravel;
    private double speed;

    public DriveDistanceCommand(double distanceInches, double speed) {
        requires(Robot.DRIVE_TRAIN);
        
        this.inchesToTravel = (distanceInches / 16);
        this.speed = speed;
        
    }

    protected void initialize() {
    	
//        Robot.DRIVE_TRAIN.resetLeftEncoder();
//        Robot.DRIVE_TRAIN.resetRightEncoder();
        Robot.DRIVE_TRAIN.resetBothEncoders();
        Robot.resetNavXAngle();
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
        
        final double medianInchesTraveled = Robot.DRIVE_TRAIN.simpleConvertTicksToInches(medianDistanceTraveled);
        
        System.out.println("isFINISHED ****** MedianTicks: " + Double.toString(medianDistanceTraveled) + 
                "   MedianInches CONVERTED: " + Double.toString(medianInchesTraveled));
        
        return Math.abs(medianInchesTraveled)  > Math.abs(inchesToTravel);
        
    }

    protected void end() {
        
//        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.changeControlMode(TalonControlMode.PercentVbus);
//        Robot.DRIVE_TRAIN.talonDriveRightPrimary.changeControlMode(TalonControlMode.PercentVbus);
//        Robot.DRIVE_TRAIN.talonDriveLeftSlave.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
//        Robot.DRIVE_TRAIN.talonDriveRightSlave.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
//        Robot.DRIVE_TRAIN.set(0, 0);
        
        /*Fix made to the code above, all four of the talons were being set to PercentVbus when the two 
         * "Slave" talons were instead supposed to be set to Follower
         */
        
        
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.changeControlMode(BaseMotorController.PercentVbus);   //made this line and next fit the new import
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.changeControlMode(TalonSRX.PercentVbus);
        Robot.DRIVE_TRAIN.talonDriveLeftSlave.changeControlMode(CANTalon.TalonControlMode.Follower);  //left this one and the next alone ftm
        Robot.DRIVE_TRAIN.talonDriveRightSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        Robot.DRIVE_TRAIN.set(0, 0);
        Robot.DRIVE_TRAIN.resetBothEncoders();
        Robot.resetNavXAngle();
    }

    protected void interrupted() {
        this.end();
    }
}
