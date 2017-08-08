package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.maps.AutonomousConfig;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PIDDriveRevolutionsCommand extends Command {
	
    //execute is called every 20ms and isFinished is called right after execute
    //add a button to Ryan's joystick that will default the drive train back to DriveWithJoystickCommand
    
	private double driveRevolutions;
    private double driveP;
    private double driveI;
    private double driveD;
    
	
	private final static double ENCODER_COMPENSATION_VALUE = 1;

    private static final double MAX_VOLTAGE = 12;
    private static final double MIN_VOLTAGE = 1.9;

    //STOP_THRESHOLD_REAL was 3 inches and is now 8 inches in an attempt to cut back on time
    private final static double STOP_THRESHOLD_REAL = 3.0;
    private final static double STOP_THRESHOLD_ADJUSTED = Robot.DRIVE_TRAIN.convertInchesToRevs(STOP_THRESHOLD_REAL / ENCODER_COMPENSATION_VALUE);

    private final static int I_ZONE_IN_REVOLUTIONS = 50; //100;
    private final static double I_ACCUM_RESET_BENCHMARK_IN_INCHES = 15.0;
    private double IAccumDistanceCounter;
    private double IAccumDistanceTraveled;
    
    private boolean firstPass;
    
    private int onTargetCount = 0;
    
    private final static int TARGET_COUNT_ONE_SECOND = 50;
    
    //Half a second is being multiplied by the user input to achieve the desired "ON_TARGET_COUNT"
    private final static double ON_TARGET_MINIMUM_COUNT = TARGET_COUNT_ONE_SECOND * 0.25;

    
	private String specificDistanceName = null;
    
	private double specificDistanceInches;
	
	private double specificDistanceP;
	
	private double specificDistanceI;
	
	private double specificDistanceD;
	

    //values that worked for 75 gear inches and 25 inches respectively (PID: 2.1, 0.0, 4.0; 0.85, 0.0, 1.35)

	/*
	 * A lot of test values were being implemented in attempts to cut time
	 */
	
    private final static double FORWARD_TO_GEAR_DISTANCE = 68.25;//was 75.25
    private final static double FORWARD_TO_GEAR_P = 2.65;//1.5
    private final static double FORWARD_TO_GEAR_I = 0.0001;//0.0005
    private final static double FORWARD_TO_GEAR_D = 1250.0;//450.0

    private final static double REVERSE_AWAY_FROM_CENTER_GEAR_DISTANCE = -30.0;
    private final static double REVERSE_AWAY_FROM_CENTER_GEAR_P = 2.1;
    private final static double REVERSE_AWAY_FROM_CENTER_GEAR_I = 0.0005;
    private final static double REVERSE_AWAY_FROM_CENTER_GEAR_D = 250.0;

    private final static double FORWARD_TO_FRONT_OF_KEY_DISTANCE = 48.0;//48.0 //72.0 //90.0
    private final static double FORWARD_TO_FRONT_OF_KEY_P = 1.6;//1.5 //1.3 //2.15
    private final static double FORWARD_TO_FRONT_OF_KEY_I = 0.0001;
    private final static double FORWARD_TO_FRONT_OF_KEY_D = 450.0;//450.0 //250.0 //450.0

    private final static double FORWARD_TO_SHOOT_DISTANCE = 33;
    private final static double FORWARD_TO_SHOOT_P = 1.5; //4.5 / 32;
    private final static double FORWARD_TO_SHOOT_I = 0.01; //0.005 / 32;
    private final static double FORWARD_TO_SHOOT_D = 15; //200.0 / 32;
    
    //for 30 inch reverse DON'T USE THIS NUMBER
//    private final static double SHORT_DISTANCE_P = 3.0;
//    private final static double SHORT_DISTANCE_I = 0.000335;
//    private final static double SHORT_DISTANCE_D = 950.0;

    //for 75.25 inch forward DON'T USE THIS NUMBER
//    private final static double LONG_DISTANCE_P = 3.0;
//    private final static double LONG_DISTANCE_I = 0.0001;
//    private final static double LONG_DISTANCE_D = 950.0; //was at 1.5

    /*
     * April 22nd: (CURRENT) NEED TO FIX THE LONG AND SHORT DISTANCE DIFFERENTIAL
     * 
     * P, I, D for driving 75.25 inches with the "more correct numbers"; (0.85, 0.0, 3.5)
     * 
     * CLUNKY VALUES FOR LONG DISTANCE DRIVE (REALLY FAST, 2 second):
     *      P, I, D: (3.0, 0.0001, 950.0)
     *      Robot.DRIVE_TRAIN.talonDriveRightPrimary.setPID(3.0, 0.0001, 950.0);
     */

	
	public PIDDriveRevolutionsCommand(double revolutions, double P, double I, double D) {
	    requires(Robot.DRIVE_TRAIN);
	      
	    this.driveRevolutions = revolutions / ENCODER_COMPENSATION_VALUE;
		this.driveP = P;
		this.driveI = I;
		this.driveD = D;
	}
	
	public PIDDriveRevolutionsCommand(String specificDistance, boolean reverse) {
	    this(0, 0, 0, 0);
	    
	    this.specificDistanceName = specificDistance;
	    
        switch (this.specificDistanceName) {
            case AutonomousConfig.PIDDriveDistances.FORWARD_TO_CENTER_GEAR:
                specificDistanceInches = FORWARD_TO_GEAR_DISTANCE;
                specificDistanceP = FORWARD_TO_GEAR_P;
                specificDistanceI = FORWARD_TO_GEAR_I;
                specificDistanceD = FORWARD_TO_GEAR_D;
                break;
            case AutonomousConfig.PIDDriveDistances.REVERSE_AWAY_FROM_CENTER_GEAR:
                specificDistanceInches = REVERSE_AWAY_FROM_CENTER_GEAR_DISTANCE;
                specificDistanceP = REVERSE_AWAY_FROM_CENTER_GEAR_P;
                specificDistanceI = REVERSE_AWAY_FROM_CENTER_GEAR_I;
                specificDistanceD = REVERSE_AWAY_FROM_CENTER_GEAR_D;
                break;
            case AutonomousConfig.PIDDriveDistances.FORWARD_TO_FRONT_OF_KEY:
                specificDistanceInches = FORWARD_TO_FRONT_OF_KEY_DISTANCE;
                specificDistanceP = FORWARD_TO_FRONT_OF_KEY_P;
                specificDistanceI = FORWARD_TO_FRONT_OF_KEY_I;
                specificDistanceD = FORWARD_TO_FRONT_OF_KEY_D;
                break;
            case AutonomousConfig.PIDDriveDistances.FORWARD_TO_SHOOT:
                specificDistanceInches = FORWARD_TO_SHOOT_DISTANCE;
                specificDistanceP = FORWARD_TO_SHOOT_P;
                specificDistanceI = FORWARD_TO_SHOOT_I;
                specificDistanceD = FORWARD_TO_SHOOT_D;
                break;
            default:
                specificDistanceInches = 0.0;
                specificDistanceP = 0;
                specificDistanceI = 0;
                specificDistanceD = 0;
                break;
        }
        
	    if (reverse) {
	        this.driveRevolutions = -Robot.DRIVE_TRAIN.convertInchesToRevs(specificDistanceInches / ENCODER_COMPENSATION_VALUE);
	    } else {
	        this.driveRevolutions = Robot.DRIVE_TRAIN.convertInchesToRevs(specificDistanceInches / ENCODER_COMPENSATION_VALUE);
	    }
	    this.driveP = specificDistanceP;
	    this.driveI = specificDistanceI;
	    this.driveD = specificDistanceD;
	}
	
		
	protected void initialize() {
	    
//	    SmartDashboard.putString("specificDistanceName:", specificDistanceName);
	    
	    onTargetCount = 0;
	    IAccumDistanceTraveled = 0;
	    IAccumDistanceCounter = 0;
	    
	    firstPass = false;
	    Robot.DRIVE_TRAIN.resetBothEncoders();
	    Robot.resetNavXAngle();
        Robot.DRIVE_TRAIN.enablePositionControl();
        
        
        /*
         * April 20th: Brian - Comfortable PID values that we found are P = 3, I = 0, and D = 950.
         * Testing done with George and Corey, we dropped using I. Still need to test the use of
         * different drive distances. April 20th (end of the night): Brian - after testing shorter
         * distances, particularly 25 inches, we found that the drive train does not arrive at the
         * desired location.
         */
        
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.setPID(driveP, driveI, driveD);
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.setPID(driveP, driveI, driveD);
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.setF(0.1489);
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.setF(0.1489);

//        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.ClearIaccum();
//        Robot.DRIVE_TRAIN.talonDriveRightPrimary.ClearIaccum();
        
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.configNominalOutputVoltage(+MIN_VOLTAGE,-MIN_VOLTAGE);
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.configPeakOutputVoltage(+MAX_VOLTAGE, -MAX_VOLTAGE);
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.configNominalOutputVoltage(+MIN_VOLTAGE,-MIN_VOLTAGE);
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.configPeakOutputVoltage(+MAX_VOLTAGE, -MAX_VOLTAGE);
        
//        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.setCloseLoopRampRate(rampRate);
//        Robot.DRIVE_TRAIN.talonDriveRightPrimary.setCloseLoopRampRate(rampRate);
        
//        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.setAllowableClosedLoopErr(2); //was 6
//        Robot.DRIVE_TRAIN.talonDriveRightPrimary.setAllowableClosedLoopErr(2); //was 6
        
        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.setIZone(I_ZONE_IN_REVOLUTIONS);
        Robot.DRIVE_TRAIN.talonDriveRightPrimary.setIZone(I_ZONE_IN_REVOLUTIONS);

        Robot.DRIVE_TRAIN.setPID(driveRevolutions, driveRevolutions);
	}
	
	protected void execute() {
	    SmartDashboard.putNumber("STOP THRESHOLD:", Robot.DRIVE_TRAIN.convertRevsToInches(STOP_THRESHOLD_ADJUSTED));
	    SmartDashboard.putNumber("Closed-Loop Error Left:", Robot.DRIVE_TRAIN.talonDriveLeftPrimary.getClosedLoopError());
        SmartDashboard.putNumber("Closed-Loop Error Right:", Robot.DRIVE_TRAIN.talonDriveRightPrimary.getClosedLoopError());
	    SmartDashboard.putNumber("I Accum Left:", Robot.DRIVE_TRAIN.talonDriveLeftPrimary.GetIaccum());
        SmartDashboard.putNumber("I Accum Right:", Robot.DRIVE_TRAIN.talonDriveRightPrimary.GetIaccum());
        
//        IAccumDistanceTraveled = Robot.DRIVE_TRAIN.convertRevsToInches((Robot.DRIVE_TRAIN.getRightPosition() + Robot.DRIVE_TRAIN.getLeftPosition()) * 4);
//	    
//	    if ((Math.abs(IAccumDistanceTraveled) - Math.abs(IAccumDistanceCounter)) >= Math.abs(I_ACCUM_RESET_BENCHMARK_IN_INCHES)) {
//	        IAccumDistanceCounter = IAccumDistanceTraveled;
//	        Robot.DRIVE_TRAIN.talonDriveLeftPrimary.ClearIaccum();
//	        Robot.DRIVE_TRAIN.talonDriveRightPrimary.ClearIaccum();
//	    }
//	    
//	    if ((Math.abs(IAccumDistanceTraveled) >= Math.abs(driveRevolutions) && !firstPass)) {
//	        firstPass = true;
//            Robot.DRIVE_TRAIN.talonDriveLeftPrimary.ClearIaccum();
//            Robot.DRIVE_TRAIN.talonDriveRightPrimary.ClearIaccum();
//	    }
	    
	}
	
	@Override
	protected boolean isFinished() {
		double leftPosition = Robot.DRIVE_TRAIN.getLeftPosition();
		double rightPosition = Robot.DRIVE_TRAIN.getRightPosition();
		
		if (leftPosition > (driveRevolutions - STOP_THRESHOLD_ADJUSTED) && leftPosition < (driveRevolutions + STOP_THRESHOLD_ADJUSTED) &&
		    rightPosition > (driveRevolutions - STOP_THRESHOLD_ADJUSTED) && rightPosition < (driveRevolutions + STOP_THRESHOLD_ADJUSTED)) {
		    onTargetCount++;
		} else {
		    onTargetCount = 0;
		}
		
		return (onTargetCount > ON_TARGET_MINIMUM_COUNT);
	}
	
	protected void end() {
		Robot.DRIVE_TRAIN.enableVBusControl();
		Robot.DRIVE_TRAIN.resetBothEncoders();
		Robot.resetNavXAngle();
		Robot.DRIVE_TRAIN.stop();
	}
	
	protected void interrupted() {
		end();
	}

}
