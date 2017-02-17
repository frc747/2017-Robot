package org.usfirst.frc.team747.robot.subsystems;

import org.usfirst.frc.team747.robot.maps.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class ClimberSubsystem extends Subsystem {

	private CANTalon talonClimb1 = new CANTalon(Robot.Climber.CLIMB_1.getValue()),
					 talonClimb2 = new CANTalon(Robot.Climber.CLIMB_2.getValue());
	
	public ClimberSubsystem() {
		LiveWindow.addActuator("Climber System", "Climb Motor Speed 1", talonClimb1);
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

