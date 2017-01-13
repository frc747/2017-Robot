package org.usfirst.frc.team747.robot.subsystems;

import org.usfirst.frc.team747.robot.commands.ShooterStopCommand;
import org.usfirst.frc.team747.robot.maps.Robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ShooterSubsystem extends Subsystem {

  private Talon talonLeftShooter = new Talon(Robot.Shooter.LEFT_SHOOTER.getValue()),
      talonRightShooter = new Talon(Robot.Shooter.RIGHT_SHOOTER.getValue());

  public void initDefaultCommand() {
    setDefaultCommand(new ShooterStopCommand());
    talonLeftShooter.setInverted(true);
  }

  public void setShooterSpeed(double leftShooterSpeed, double rightShooterSpeed) {
    talonLeftShooter.set(leftShooterSpeed);
    talonRightShooter.set(rightShooterSpeed);
  }
}
