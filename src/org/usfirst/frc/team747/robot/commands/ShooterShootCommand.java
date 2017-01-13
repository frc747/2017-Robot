package org.usfirst.frc.team747.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team747.robot.OI;
import org.usfirst.frc.team747.robot.Robot;

/**
 * ShooterShootCommand
 * 
 * Spins the shooter wheels using the joystick throttles as inputs for the speed.
 */
public class ShooterShootCommand extends Command {
  
  public ShooterShootCommand() {
    requires(Robot.SHOOTER_SYSTEM);
  }

  @Override
  protected void execute() {
    Robot.SHOOTER_SYSTEM.setShooterSpeed(OI.getLeftShooterSpeed(),OI.getRightShooterSpeed());
  }


  @Override
  protected boolean isFinished() {
    return false;
  }
}
