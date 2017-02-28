package org.usfirst.frc.team747.robot.subsystems;

import org.usfirst.frc.team747.robot.commands.ShooterStopCommand;
import org.usfirst.frc.team747.robot.maps.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import com.ctre.CANTalon;

public class ShooterSubsystem extends Subsystem {

  private CANTalon talonShooterLeft1 = new CANTalon(RobotMap.Shooter.LEFT_1.getValue()),
                   talonShooterLeft2 = new CANTalon(RobotMap.Shooter.LEFT_2.getValue()),
                   talonShooterRight1 = new CANTalon(RobotMap.Shooter.RIGHT_1.getValue()),
                   talonShooterRight2 = new CANTalon(RobotMap.Shooter.RIGHT_2.getValue()),
                   talonIndexer = new CANTalon(RobotMap.Shooter.INDEXER.getValue());
  			
  public ShooterSubsystem(){
	  LiveWindow.addActuator("Shooter System", "Left Motor Speed 1", talonShooterLeft1);
  }
 
  public void initDefaultCommand() {
    setDefaultCommand(new ShooterStopCommand());
    talonShooterLeft1.setInverted(true);
    talonShooterLeft2.setInverted(true);
  }

  public void setShooterSpeed(double leftShooterSpeed, double rightShooterSpeed, double indexerSpeed) {
    talonShooterLeft1.set(leftShooterSpeed);
    talonShooterLeft2.set(leftShooterSpeed);
    talonShooterRight1.set(rightShooterSpeed);
    talonShooterRight2.set(rightShooterSpeed);
    talonIndexer.set(indexerSpeed);
  }
  public double getMotorLeftSpeed(){
	  
	  return ((talonShooterLeft1.getEncVelocity()/1024)*60);
	  
  }
  public double getMotorRightSpeed(){
	  
	  return ((talonShooterRight1.getEncVelocity()/1024)*60);
	  
  }
}