package org.usfirst.frc.team747.robot.subsystems;

import org.usfirst.frc.team747.robot.commands.ShooterStopCommand;
import org.usfirst.frc.team747.robot.maps.Robot;
import edu.wpi.first.wpilibj.Encoder;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import com.ctre.CANTalon;

public class ShooterSubsystem extends Subsystem {

  private CANTalon talonLeft1Shooter = new CANTalon(Robot.Shooter.LEFT_SHOOTER_1.getValue()),
                   talonLeft2Shooter = new CANTalon(Robot.Shooter.LEFT_SHOOTER_2.getValue()),
                   talonRight1Shooter = new CANTalon(Robot.Shooter.RIGHT_SHOOTER_1.getValue()),
                   talonRight2Shooter = new CANTalon(Robot.Shooter.RIGHT_SHOOTER_2.getValue());
  			
  public ShooterSubsystem(){
	  LiveWindow.addActuator("Shooter System", "Left Motor Speed 1", talonLeft1Shooter);
  }
  
  
  
  public void initDefaultCommand() {
    setDefaultCommand(new ShooterStopCommand());
    talonLeft1Shooter.setInverted(true);
    talonLeft2Shooter.setInverted(true);
  }

  public void setShooterSpeed(double leftShooterSpeed, double rightShooterSpeed) {
    talonLeft1Shooter.set(leftShooterSpeed);
    talonLeft2Shooter.set(leftShooterSpeed);
    talonRight1Shooter.set(rightShooterSpeed);
    talonRight2Shooter.set(rightShooterSpeed);
    
  }
  public double getMotorLeftSpeed(){
	  
	  return ((talonLeft1Shooter.getEncVelocity()/1024)*60);
	  
  }
  public double getMotorRightSpeed(){
	  
	  return ((talonRight1Shooter.getEncVelocity()/1024)*60);
	  
  }
}