package org.usfirst.frc.team747.robot;

//import org.usfirst.frc.team747.robot.commands.ClimberClimbUpFastCommand;
//import org.usfirst.frc.team747.robot.commands.ClimberClimbUpSlowCommand;
//import org.usfirst.frc.team747.robot.commands.ShooterShootCommand;
//import org.usfirst.frc.team747.robot.commands.SimpleVisionDriveCommand;
//import org.usfirst.frc.team747.robot.commands.VisionDriveCommand;
//import org.usfirst.frc.team747.robot.commands.DriveDistanceCommand;
//import org.usfirst.frc.team747.robot.commands.IntakeCommand;
//import org.usfirst.frc.team747.robot.commands.ShooterIndexerReverseCommand;
//import org.usfirst.frc.team747.robot.commands.ShooterReverseCommand;
//import org.usfirst.frc.team747.robot.commands.ShootButton;
//import org.usfirst.frc.team747.robot.commands.IndexerReverseButton;
import org.usfirst.frc.team747.robot.commands.*;
import org.usfirst.frc.team747.robot.maps.DriverStation;
//import org.usfirst.frc.team869.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
            BUTTON_REV_SHOOTER
                = new JoystickButton(CONTROLLER_OPERATOR, DriverStation.GamePad.BUTTON_RB.getValue()),
            BUTTON_REVERSE_SHOOTER
                = new JoystickButton(CONTROLLER_OPERATOR, DriverStation.GamePad.BUTTON_LB.getValue()),
            BUTTON_INTAKE_FORWARD
                = new JoystickButton(CONTROLLER_OPERATOR, DriverStation.GamePad.BUTTON_A.getValue()),
            BUTTON_INTAKE_BACK
                = new JoystickButton(CONTROLLER_OPERATOR, DriverStation.GamePad.BUTTON_B.getValue()),
            BUTTON_DRIVE_DISTANCE
                = new JoystickButton(CONTROLLER_OPERATOR, DriverStation.GamePad.BUTTON_START.getValue()),
            BUTTON_DRIVE_REVERSE_DISTANCE
                = new JoystickButton(CONTROLLER_OPERATOR, DriverStation.GamePad.STICK_LEFT.getValue()),
            BUTTON_RESET_ENCODERS
                = new JoystickButton(CONTROLLER_OPERATOR, DriverStation.GamePad.BUTTON_BACK.getValue()),
            BUTTON_DRIVE_ROTATE
                = new JoystickButton(CONTROLLER_OPERATOR, DriverStation.GamePad.STICK_RIGHT.getValue()),
            BUTTON_CLIMB_FAST
                = new JoystickButton(CONTROLLER_OPERATOR, DriverStation.GamePad.BUTTON_X.getValue()),
            BUTTON_CLIMB_SLOW
                = new JoystickButton(CONTROLLER_OPERATOR, DriverStation.GamePad.BUTTON_Y.getValue()),
            BUTTON_GEAR
                = new JoystickButton(JOYSTICK_DRIVER_LEFT, DriverStation.Joystick.BUTTON_2.getValue()),
            BUTTON_SECOND_GEAR
                = new JoystickButton(JOYSTICK_DRIVER_RIGHT, DriverStation.Joystick.BUTTON_2.getValue());
//            BUTTON_INDEXER_FORWARD
//                = new JoystickButton(CONTROLLER_OPERATOR, DriverStation.GamePad.BUTTON_BACK.getValue()),
//            BUTTON_SHOOTER_VOLTAGE
//                = new JoystickButton(CONTROLLER_OPERATOR, DriverStation.GamePad.BUTTON_START.getValue());

            //BUTTON_BOILER
            //    = new JoystickButton(JOYSTICK_DRIVER_RIGHT, DriverStation.Joystick.BUTTON_2.getValue());

	public static final ShootButton BUTTON_FIRE = new ShootButton();
	public static final IndexerReverseButton BUTTON_REVERSE_INDEXER = new IndexerReverseButton();

    static Preferences prefs;
    
    public OI() {
        BUTTON_INTAKE_FORWARD.whileHeld(new IntakeCommand());
        BUTTON_INTAKE_BACK.whileHeld(new IntakeCommand(false));
        BUTTON_REVERSE_INDEXER.whileHeld(new IndexerReverseCommand());
        BUTTON_REVERSE_SHOOTER.whileHeld(new ShooterReverseCommand());
        BUTTON_FIRE.whileHeld(new ShootBallsCommand());
        BUTTON_REV_SHOOTER.whileHeld(new ShooterRevCommand());
        BUTTON_CLIMB_SLOW.whileHeld(new ClimberClimbUpSlowCommand());
        BUTTON_CLIMB_FAST.whileHeld(new ClimberClimbUpFastCommand());
//        BUTTON_SHOOTER_VOLTAGE.whileHeld(new ShooterShootVoltageCommand());
//        BUTTON_INDEXER_FORWARD.whileHeld(new ShooterIndexerForwardCommand());
//        if (OI.getClimbState()) {
//        	BUTTON_CLIMB_SLOW.whileHeld(new ClimberClimbUpSlowCommand());
//        	
//        } else {
//            BUTTON_CLIMB_FAST.whileHeld(new ClimberClimbUpFastCommand());
//        }

        
        BUTTON_GEAR.toggleWhenPressed(new VisionDriveCommand(Robot.VISION_TRACKING_FRONT, "GEAR", 8));
        BUTTON_SECOND_GEAR.toggleWhenPressed(new VisionDriveCommand(Robot.VISION_TRACKING_FRONT, "GEAR", 8));
        //        BUTTON_BOILER.toggleWhenPressed(new VisionDriveCommand(Robot.VISION_TRACKING_REAR, "BOILER", 0));
        BUTTON_DRIVE_DISTANCE.toggleWhenPressed(new PIDDriveInchesCommand(25)); //3.725 Revolutions * 18.85 in. per revolutions
        BUTTON_DRIVE_REVERSE_DISTANCE.toggleWhenPressed(new PIDDriveInchesCommand(-25)); //75.25 inches for distance to pin
        BUTTON_DRIVE_ROTATE.toggleWhenPressed(new PIDDriveRotateWithVisionCommand(Robot.VISION_TRACKING_REAR, "BOILER"));
        //        BUTTON_DRIVE_ROTATE.toggleWhenPressed(new PIDDriveRotateCommand(-45));
        BUTTON_RESET_ENCODERS.whenPressed(new ResetSensorsCommand());

//        BUTTON_DRIVE_ROTATE.toggleWhenPressed(new PIDDriveInchesCommand(75.25));
        
        new Notifier(() -> updateOI()).startPeriodic(0.100); //value in seconds
    }
    
    public void updateOI() {
        SmartDashboard.putNumber("Left Encoder Position:", Robot.DRIVE_TRAIN.getLeftEncoderPosition() * 4);
        SmartDashboard.putNumber("Right Encoder Position:", Robot.DRIVE_TRAIN.getRightEncoderPosition() * 4);
        SmartDashboard.putNumber("Left Position (Revolutions):", Robot.DRIVE_TRAIN.getLeftPosition() * 4);
        SmartDashboard.putNumber("Right Position (Revolutions):", Robot.DRIVE_TRAIN.getRightPosition() * 4);
        SmartDashboard.putNumber("Left Position (Inches):", Robot.DRIVE_TRAIN.convertRevsToInches(Robot.DRIVE_TRAIN.getLeftPosition()) * 4);
        SmartDashboard.putNumber("Right Position (Inches):", Robot.DRIVE_TRAIN.convertRevsToInches(Robot.DRIVE_TRAIN.getRightPosition()) * 4);
        SmartDashboard.putNumber("NavX Angle:", Robot.getNavXAngle());
        SmartDashboard.putNumber("Distance to Target:", Robot.getCVDistance(Robot.VISION_TRACKING_REAR, "BOILER"));
        SmartDashboard.putNumber("Degrees to Target:", Robot.getCVAngle(Robot.VISION_TRACKING_REAR, "BOILER"));
    }
    
    public static boolean getClimbState(){
        return CONTROLLER_OPERATOR.getRawButton(DriverStation.GamePad.BUTTON_Y.getValue()) && 
                CONTROLLER_OPERATOR.getRawButton(DriverStation.GamePad.BUTTON_X.getValue());
    }

    public static double getLeftShooterSpeed() {
        prefs = Preferences.getInstance();
        return prefs.getDouble("Motor1", 1);
    }

    public static double getRightShooterSpeed() {
        prefs = Preferences.getInstance();
        return prefs.getDouble("Motor2", 1);
    }

    public static double getIndexerSpeed() {
        prefs = Preferences.getInstance(); // had it at .4 seemed to work but let's try faster to give it inertia
        return prefs.getDouble("Indexer", .65);
    }
  
	public static boolean getShootButton() {
		return CONTROLLER_OPERATOR.getRawAxis(DriverStation.GamePad.TRIGGER_RIGHT.getValue())
                >= 0.5;
	}
	
	public static boolean getIndexerReverseButton() {
		return CONTROLLER_OPERATOR.getRawAxis(DriverStation.GamePad.TRIGGER_LEFT.getValue())
                >= 0.5;
	}
    
}
