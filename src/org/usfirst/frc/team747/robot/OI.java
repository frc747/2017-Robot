package org.usfirst.frc.team747.robot;

import org.usfirst.frc.team747.robot.commands.ShooterShootCommand;
import org.usfirst.frc.team747.robot.maps.DriverStation;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {

  private static final Joystick LEFT_JOYSTICK  = new Joystick(DriverStation.ControlMap.LEFT_JOYSTICK.getValue()),
                                RIGHT_JOYSTICK = new Joystick(DriverStation.ControlMap.RIGHT_JOYSTICK.getValue());
  
  public OI(){
    Button fireButton  = new JoystickButton(LEFT_JOYSTICK, DriverStation.Joystick.TRIGGER.getValue());
    Button fireButton2 = new JoystickButton(RIGHT_JOYSTICK, DriverStation.Joystick.TRIGGER.getValue());
    
    fireButton.whileHeld(new ShooterShootCommand());
    fireButton2.whileHeld(new ShooterShootCommand());
  }

  public static double getLeftShooterSpeed() {
    return LEFT_JOYSTICK.getRawAxis(DriverStation.Joystick.THROTTLE_AXIS.getValue()) - 1.0;
  }

  public static double getRightShooterSpeed() {
    return RIGHT_JOYSTICK.getRawAxis(DriverStation.Joystick.THROTTLE_AXIS.getValue()) - 1.0;
  }

}
