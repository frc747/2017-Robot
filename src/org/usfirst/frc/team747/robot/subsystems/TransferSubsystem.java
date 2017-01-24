package org.usfirst.frc.team747.robot.subsystems;

import org.usfirst.frc.team747.robot.commands.TransferStopCommand;
import org.usfirst.frc.team747.robot.maps.Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

public class TransferSubsystem extends Subsystem {

	private CANTalon agitatorTalon = new CANTalon(Robot.Transfer.AGITATOR.getValue()),
					 loaderTalon   = new CANTalon(Robot.Transfer.LOADER.getValue());
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new TransferStopCommand());
	}
	
	public void setAgitatorSpeed(double speed){
		agitatorTalon.set(speed);
	}
	
	public void setLoaderSpeed(double speed){
		loaderTalon.set(speed);
	}
	
	public void stopTransfer(){
		agitatorTalon.set(0);
		loaderTalon.set(0);
	}

}
