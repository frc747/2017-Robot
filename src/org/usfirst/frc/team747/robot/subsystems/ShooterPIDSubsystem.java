package org.usfirst.frc.team747.robot.subsystems;

import org.usfirst.frc.team747.robot.commands.ShooterStopCommand;
import org.usfirst.frc.team747.robot.maps.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class ShooterPIDSubsystem extends PIDSubsystem {
	
	private CANTalon talonLeft1Shooter = new CANTalon(Robot.Shooter.LEFT_SHOOTER_1.getValue()),
              talonLeft2Shooter = new CANTalon(Robot.Shooter.LEFT_SHOOTER_2.getValue()),
              talonRight1Shooter = new CANTalon(Robot.Shooter.RIGHT_SHOOTER_1.getValue()),
              talonRight2Shooter = new CANTalon(Robot.Shooter.RIGHT_SHOOTER_2.getValue()),
              talonIndexer = new CANTalon(Robot.Shooter.INDEXER.getValue());
	  

    // Initialize your subsystem here
    public ShooterPIDSubsystem() {
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        //enable(); //- Enables the PID controller.
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
        setDefaultCommand(new ShooterStopCommand());
        talonLeft1Shooter.setInverted(true);
        talonLeft2Shooter.setInverted(true);
        
        
    }

    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
        return 0.0;
    }

    protected void usePIDOutput(double outputLeft, double outputRight, double indexerOutput) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    	
    	
        talonLeft1Shooter.set(outputLeft);
        talonLeft2Shooter.set(outputLeft);
        talonRight1Shooter.set(outputRight);
        talonRight2Shooter.set(outputRight);
        talonIndexer.set(indexerOutput);
        
        
        
        
    }
}
