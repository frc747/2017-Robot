package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

public class DriveBrakeCommand extends Command {
    
    private double speed;

    public DriveBrakeCommand(double direction) {
        requires(Robot.DRIVE_TRAIN);
        this.speed = direction;
        setTimeout(.2);
    }

    protected void initialize() {
    }

    protected void execute() {
        Robot.DRIVE_TRAIN.set(speed, speed);
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.changeControlMode(TalonControlMode.PercentVbus);
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.changeControlMode(TalonControlMode.PercentVbus);
        Robot.DRIVE_TRAIN.talonDriveLeftSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        Robot.DRIVE_TRAIN.talonDriveRightSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        Robot.DRIVE_TRAIN.set(0, 0);
    }

    protected void interrupted() {
        this.end();
    }
}
