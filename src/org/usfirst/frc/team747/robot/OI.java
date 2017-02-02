package org.usfirst.frc.team747.robot;

import org.usfirst.frc.team747.robot.commands.ShooterShootCommand;
import org.usfirst.frc.team747.robot.maps.DriverStation;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {

  private static final Joystick LEFT_JOYSTICK  = new Joystick(DriverStation.ControlMap.LEFT_JOYSTICK.getValue()),
                                RIGHT_JOYSTICK = new Joystick(DriverStation.ControlMap.RIGHT_JOYSTICK.getValue());
  
  static Preferences prefs;
  
  
  public OI(){
    Button fireButton  = new JoystickButton(LEFT_JOYSTICK, DriverStation.Joystick.TRIGGER.getValue());
    Button fireButton2 = new JoystickButton(RIGHT_JOYSTICK, DriverStation.Joystick.TRIGGER.getValue());
    
    
    fireButton.whileHeld(new ShooterShootCommand());
    fireButton2.whileHeld(new ShooterShootCommand());
  }

  public static double getLeftShooterSpeed() {
		prefs = Preferences.getInstance();
	  System.out.println(prefs.getDouble("Motor1", 0));
	 return prefs.getDouble("Motor1", 0);
  }

  public static double getRightShooterSpeed() {
		prefs = Preferences.getInstance();
	  System.out.println(prefs.getDouble("Motor2", 0));
	 return prefs.getDouble("Motor2", 0);
  }

  public static double getIndexerSpeed() {
		prefs = Preferences.getInstance();
	  System.out.println(prefs.getDouble("Indexer", 0));
	 return prefs.getDouble("Indexer", 0);
  }

}
