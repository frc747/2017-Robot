package org.usfirst.frc.team747.robot;

import org.usfirst.frc.team747.robot.commands.ClimberClimbUpCommand;
import org.usfirst.frc.team747.robot.commands.ClimbCommand;
import org.usfirst.frc.team747.robot.commands.ClimberClimbDownCommand;
import org.usfirst.frc.team747.robot.commands.ShooterShootCommand;
import org.usfirst.frc.team747.robot.maps.DriverStation;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


public class OI {

    private static final Joystick LEFT_JOYSTICK = new Joystick(DriverStation.ControlMap.JOYSTICK_LEFT.getValue()),
            RIGHT_JOYSTICK = new Joystick(DriverStation.ControlMap.JOYSTICK_RIGHT.getValue()),
            CONTROLLER_OPERATOR = new Joystick(DriverStation.ControlMap.OPERATOR.getValue());

    static Preferences prefs;

    public OI() {
        Button fireButton = new JoystickButton(LEFT_JOYSTICK, DriverStation.Joystick.TRIGGER.getValue());
        Button fireButton2 = new JoystickButton(RIGHT_JOYSTICK, DriverStation.Joystick.TRIGGER.getValue());
        Button climbButton = new JoystickButton(CONTROLLER_OPERATOR, DriverStation.GamePad.LB_BUTTON.getValue());
        Button climbButton2 = new JoystickButton(CONTROLLER_OPERATOR, DriverStation.GamePad.RB_BUTTON.getValue());
        Button buttonButton = new JoystickButton(CONTROLLER_OPERATOR, DriverStation.GamePad.BACK_BUTTON.getValue());
        fireButton.whileHeld(new ShooterShootCommand());
        fireButton2.whileHeld(new ShooterShootCommand());
        climbButton.whileHeld(new ClimberClimbUpCommand());
        climbButton2.whileHeld(new ClimberClimbDownCommand());
        buttonButton.whileHeld(new ClimbCommand());
        
    }

    public static double getLeftShooterSpeed() {
        prefs = Preferences.getInstance();
        return prefs.getDouble("Motor1", 0);
    }

    public static double getRightShooterSpeed() {
        prefs = Preferences.getInstance();
        return prefs.getDouble("Motor2", 0);
    }

    public static double getIndexerSpeed() {
        prefs = Preferences.getInstance();
        return prefs.getDouble("Indexer", 0);
    }
    public static double getClimberSpeed() {
    	return CONTROLLER_OPERATOR.getRawAxis(DriverStation.GamePad.L_X_AXIS.getValue());
    }

}
