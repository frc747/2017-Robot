
package org.usfirst.frc.team747.robot;

import org.usfirst.frc.team747.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team747.robot.subsystems.IntakeSubsystem;
import org.usfirst.frc.team747.robot.subsystems.ClimberSubsystem;
import org.usfirst.frc.team747.robot.subsystems.ShooterSubsystem;
import org.usfirst.frc.team747.robot.vision.AxisM1004Specs;
import org.usfirst.frc.team747.robot.vision.AxisM1011Specs;
import org.usfirst.frc.team747.robot.vision.BoilerTargetTemplate;
import org.usfirst.frc.team747.robot.vision.GearTargetTemplate;
import org.usfirst.frc.team747.robot.vision.TargetTemplate;
import org.usfirst.frc.team747.robot.vision.VisionTracking;


import java.util.HashMap;

import com.kauailabs.navx.frc.AHRS;

import java.io.File;
import java.time.Instant;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.cscore.AxisCamera;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.vision.VisionThread;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    public static final DriveSubsystem DRIVE_TRAIN = new DriveSubsystem();
    public static final IntakeSubsystem INTAKE = new IntakeSubsystem();
    public static final ShooterSubsystem SHOOTER = new ShooterSubsystem();
    public static final ClimberSubsystem CLIMBER = new ClimberSubsystem();
	public static File logs;
	public static BufferedWriter bw;
	public static FileWriter fw;
    public static VisionTracking VISION_TRACKING_FRONT = null;
    public static VisionTracking VISION_TRACKING_REAR = null;
    private VisionThread visionThreadFront = null;
    private VisionThread visionThreadRear = null;
    public static OI oi = null;

    private static final AHRS NAV_X = new AHRS (SPI.Port.kMXP);
    
    public static double getNavXAngle() {
    	return - NAV_X.getYaw();
    }
    
    public static double getNavXAngleRadians() {
    	return Math.toRadians(getNavXAngle());
    }

    public static double getNavXAngle360() {
        
        double angle = getNavXAngle();
        
        if (angle < 0) {
        	angle += 360;
        }
        
        return angle;
    }
    
    public static void resetNavXAngle() {
    	NAV_X.zeroYaw();
    }
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {

        AxisCamera frontCamera = new AxisCamera("axis", "10.7.47.7");
        AxisCamera rearCamera = new AxisCamera("axis", "axis-camera.local");
        HashMap<String, TargetTemplate> frontTemplates = new HashMap<String, TargetTemplate>();
        frontTemplates.put("GEAR", new GearTargetTemplate());
        //frontTemplates.put("RETRIEVAL", new RetrievalTargetTemplate());
        VISION_TRACKING_FRONT = new VisionTracking(new AxisM1004Specs(), frontTemplates);
        
        HashMap<String, TargetTemplate> rearTemplates = new HashMap<String, TargetTemplate>();
        rearTemplates.put("BOILER", new BoilerTargetTemplate());
        VISION_TRACKING_REAR = new VisionTracking(new AxisM1011Specs(), rearTemplates);
        
        if (oi == null) {
            oi = new OI();
        }
        
        visionThreadFront = new VisionThread(frontCamera, VISION_TRACKING_FRONT, pipeline -> {
        	// Do nothing on each frame.
            //System.out.println("GEAR TARGETS: " + pipeline.targets.size());
        });
        visionThreadFront.start();
        
        visionThreadRear = new VisionThread(rearCamera, VISION_TRACKING_REAR, pipeline -> {
            // Do nothing on each frame.
            //System.out.println("BOILER TARGETS: " + pipeline.targets.size());
        });
        visionThreadRear.start();
        
        resetNavXAngle();
    }

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		/**
		 * Setup Gyro recalibration in this block
		 */
		
		if(logs != null && logs.exists()){
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}


	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		System.out.println(Instant.now().toEpochMilli());
		System.out.println("PRINTED BITCH");
		
		
    	try {
    		logs = new File("/U/Logs/shooterLog" + Instant.now().toEpochMilli() + ".csv");
    		if(!logs.exists()){
    			logs.createNewFile();
    		}
			fw = new FileWriter(logs);
			bw = new BufferedWriter(fw);
		
			Robot.bw.write("outLeft,spdLeft,voltOutLeft1,voltOutLeft2,busVoltLeft,outRight,spdRight,voltOutRight1,voltOutRight2,busVoltRight,leftP,leftI,leftD,leftF,rightP,rightI,rightD,rightF\n");
  		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
