
package org.usfirst.frc.team747.robot;

import org.usfirst.frc.team747.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team747.robot.subsystems.IntakeSubsystem;
import org.usfirst.frc.team747.robot.subsystems.ClimberSubsystem;
import org.usfirst.frc.team747.robot.subsystems.ShooterSubsystem;

import com.kauailabs.navx.frc.AHRS;

import java.io.File;
import java.time.Instant;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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
	public static OI oi;
	public static File logs;
	public static BufferedWriter bw;
	public static FileWriter fw;

	private static final AHRS NAV_X = new AHRS (SPI.Port.kMXP);

    public static double getNavX360Angle(){
        double angle360   = 0;
        final int    halfCircle = 180;
        
		if (NAV_X.getYaw() < 0){
			angle360 = (halfCircle + NAV_X.getYaw()) + halfCircle;
		} else {
			angle360 = NAV_X.getYaw();
		}
		return angle360;
	}
    
    
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
	       oi = new OI();
	       
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
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		
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
