package org.usfirst.frc.team747.robot;

import org.usfirst.frc.team747.robot.commands.ClimberClimbUpCommand;
import org.usfirst.frc.team747.robot.commands.ClimberClimbDownCommand;
import org.usfirst.frc.team747.robot.commands.ShooterShootCommand;
import org.usfirst.frc.team747.robot.commands.VisionDriveCommand;
import org.usfirst.frc.team747.robot.commands.DriveDistanceCommand;
import org.usfirst.frc.team747.robot.commands.IntakeCommand;
import org.usfirst.frc.team747.robot.maps.DriverStation;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public static final Joystick JOYSTICK_DRIVER_LEFT = new Joystick(DriverStation.Controller.DRIVER_LEFT.getValue()),
            JOYSTICK_DRIVER_RIGHT = new Joystick(DriverStation.Controller.DRIVER_RIGHT.getValue()),
            CONTROLLER_OPERATOR = new Joystick(DriverStation.Controller.OPERATOR.getValue());

    public static final JoystickButton BUTTON_DRIVE_SLOW_DRIVER
                = new JoystickButton(JOYSTICK_DRIVER_LEFT, DriverStation.Joystick.BUTTON_7.getValue()),
            BUTTON_DRIVE_SLOW_OPERATOR
                = new JoystickButton(CONTROLLER_OPERATOR, DriverStation.GamePad.BUTTON_LB.getValue()),
            BUTTON_INTAKE_FORWARD
                = new JoystickButton(CONTROLLER_OPERATOR, DriverStation.GamePad.BUTTON_A.getValue()),
            BUTTON_INTAKE_BACK
                = new JoystickButton(CONTROLLER_OPERATOR, DriverStation.GamePad.BUTTON_B.getValue()),
            BUTTON_DRIVE_DISTANCE
                = new JoystickButton(CONTROLLER_OPERATOR, DriverStation.GamePad.BUTTON_START.getValue()),
            BUTTON_FIRE
                = new JoystickButton(CONTROLLER_OPERATOR, DriverStation.GamePad.TRIGGER_RIGHT.getValue()),
            BUTTON_CLIMB
                = new JoystickButton(CONTROLLER_OPERATOR, DriverStation.GamePad.BUTTON_X.getValue()),
            BUTTON_CLIMB_DOWN
                = new JoystickButton(CONTROLLER_OPERATOR, DriverStation.GamePad.BUTTON_Y.getValue()),
            BUTTON_GEAR
                = new JoystickButton(JOYSTICK_DRIVER_LEFT, DriverStation.Joystick.BUTTON_2.getValue()),
            BUTTON_BOILER
                = new JoystickButton(JOYSTICK_DRIVER_RIGHT, DriverStation.Joystick.BUTTON_2.getValue());

    static Preferences prefs;
    
    public OI() {
        BUTTON_INTAKE_FORWARD.whileHeld(new IntakeCommand());
        BUTTON_INTAKE_BACK.whileHeld(new IntakeCommand(false));
        BUTTON_DRIVE_DISTANCE.whenPressed(new DriveDistanceCommand((6.25 * Math.PI), 0.1));
        BUTTON_FIRE.whileHeld(new ShooterShootCommand());
        BUTTON_CLIMB.whileHeld(new ClimberClimbUpCommand());
        BUTTON_CLIMB_DOWN.whileHeld(new ClimberClimbDownCommand());
        BUTTON_GEAR.toggleWhenPressed(new VisionDriveCommand(Robot.VISION_TRACKING_FRONT, "GEAR", 3));
        BUTTON_BOILER.toggleWhenPressed(new VisionDriveCommand(Robot.VISION_TRACKING_REAR, "BOILER", 0));
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
}
