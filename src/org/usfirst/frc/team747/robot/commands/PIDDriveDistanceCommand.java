package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class PIDDriveDistanceCommand extends Command {
    
    private double inchesToTravel;
    private double ticksToTravel;

    public PIDDriveDistanceCommand(double distanceInches) {
        requires(Robot.DRIVE_TRAIN);
        
        this.inchesToTravel = distanceInches;
        this.ticksToTravel = Robot.DRIVE_TRAIN.convertInchesToTicks(distanceInches);
        
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

    }

    protected void execute() {
        
        Robot.DRIVE_TRAIN.setPositionPID(ticksToTravel);
        
        System.out.println("EXECUTE ****** left encoder =" + Double.toString(Robot.DRIVE_TRAIN.getLeftEncoderPosition()) + 
                "  right encoder get=" + Double.toString(Robot.DRIVE_TRAIN.getRightEncoderPosition()));
    }

    protected boolean isFinished() {
        
        //might have to include a position threshold which makes the Drive Train stop within a plus or minus 5 ticks or so for example.
        double currentPosition = Robot.DRIVE_TRAIN.getCombindedEncoderPosition();
        if (currentPosition == ticksToTravel) {
            return true;
        }else {
            return false;
        }
        
    }

    protected void end() {
        
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.changeControlMode(TalonControlMode.PercentVbus);
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.changeControlMode(TalonControlMode.PercentVbus);
        Robot.DRIVE_TRAIN.talonDriveLeftSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        Robot.DRIVE_TRAIN.talonDriveRightSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        Robot.DRIVE_TRAIN.set(0, 0);
        Robot.DRIVE_TRAIN.resetBothEncoders();
        Robot.resetNavXAngle();
    }

    protected void interrupted() {
        this.end();
    }
}
