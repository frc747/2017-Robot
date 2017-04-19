package org.usfirst.frc.team747.robot.subsystems;


import java.io.IOException;

import org.usfirst.frc.team747.robot.OI;
import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.commands.ShooterStopCommand;
import org.usfirst.frc.team747.robot.maps.RobotMap.Shooter;
import org.usfirst.frc.team747.robot.maps.RobotMap.ShooterSpeed;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

public class ShooterSubsystem extends Subsystem {

  private CANTalon talonShooterLeft1 = new CANTalon(Shooter.LEFT_1.getValue()),
                   talonShooterLeft2 = new CANTalon(Shooter.LEFT_2.getValue()),
                   talonShooterRight1 =	new CANTalon(Shooter.RIGHT_1.getValue()),
                   talonShooterRight2 =	new CANTalon(Shooter.RIGHT_2.getValue());
  
  
	StringBuilder sb = new StringBuilder();
	int loops = 0;
	
  public ShooterSubsystem(){
	  	
	  	
		//Set the 2nd motor on each side to be follower motors
		talonShooterLeft2.changeControlMode(CANTalon.TalonControlMode.Follower);
		talonShooterLeft2.set(talonShooterLeft1.getDeviceID());
		
		talonShooterRight2.changeControlMode(CANTalon.TalonControlMode.Follower);
		talonShooterRight2.set(talonShooterRight1.getDeviceID());
		
		talonShooterLeft1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		talonShooterRight1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
	  	
	    talonShooterLeft1.setInverted(true);
	    talonShooterLeft1.reverseSensor(true);
	    talonShooterRight1.reverseSensor(true);
	   
		talonShooterLeft1.configNominalOutputVoltage(+0.0f,-0.0f);
		talonShooterLeft1.configPeakOutputVoltage(+12.0f, -12.0f);
		talonShooterRight1.configNominalOutputVoltage(+0.0f,-0.0f);
		talonShooterRight1.configPeakOutputVoltage(+12.0f, -12.0f);
		
		talonShooterLeft1.setPID(	.1, 0, 1.5, .06, 0, 0, 0);
		talonShooterRight1.setPID(	.1, 0, 1.5, .05825, 0, 0, 0);

	    talonShooterLeft1.setProfile(0);
		talonShooterRight1.setProfile(0);
		
  }
 
  public void initDefaultCommand() {
    setDefaultCommand(new ShooterStopCommand());
  }

  
  /*
   * Used to determine the shooter and indexer speeds on the fly from the dashboard
   */
  public void setShooterVoltage(double leftShooterVoltage, double rightShooterVoltage) {
	  	
	  	talonShooterLeft1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	  	talonShooterRight1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	  	talonShooterLeft1.set(leftShooterVoltage);
	  	talonShooterRight1.set(rightShooterVoltage);
  
  }
  
  public void setShooterSpeed(){
	  	
	    talonShooterLeft1.changeControlMode(CANTalon.TalonControlMode.Speed);
	  	talonShooterRight1.changeControlMode(CANTalon.TalonControlMode.Speed);
	  	
	  	/*
	  	 * Brian - left shooter RPM was changed to 1850 at Lehigh because we believed it needed more juice (we observed that the left
	  	 * side was spinning around 50 RPMS slower, I might have only looked at one instance and not all of the data), reverted this change
	  	 * because we realized it was not the power of our shooter that gave us a problem, but instead it was the angle of our shooter
	  	 * depreciating as the competition went on
	  	 */
	    
	  	talonShooterLeft1.set(1800);
	    talonShooterRight1.set(1800);
	    shooterLogging();
	    
	    
  }
    
  public void shooterRev(){
	   
	  	talonShooterLeft1.changeControlMode(CANTalon.TalonControlMode.Speed);
	  	talonShooterRight1.changeControlMode(CANTalon.TalonControlMode.Speed);
	    talonShooterLeft1.set(1800);
	    talonShooterRight1.set(1800);
	   
  }
  
  public void shooterStop(){
	  	
	  	talonShooterLeft1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	  	talonShooterRight1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	  	talonShooterLeft1.set(0);
	  	talonShooterRight1.set(0);
  
  }
    
  public void reverseShooter(){
	
	    talonShooterLeft1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	  	talonShooterRight1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	  	talonShooterLeft1.set(-.25);
	  	talonShooterRight1.set(-.25);
  
  }
  
  
  public double getMotorLeftSpeed(){
	  
	  return (talonShooterLeft1.getEncVelocity());
	  
  }
  public double getMotorRightSpeed(){
	  
	  return (talonShooterRight1.getEncVelocity());
	  
  }

  
  public void shooterLogging () {
		
		

  	/* prepare line to print */
  	if(++loops >= 10) {
  	  	double motorOutputLeft = talonShooterLeft1.getOutputVoltage() / talonShooterLeft1.getBusVoltage();
  	  	double motorOutputRight = talonShooterRight1.getOutputVoltage() / talonShooterRight1.getBusVoltage();
  		
  	  	
  	  	
  	  	
  		sb.append(motorOutputLeft + "," + talonShooterLeft1.getSpeed() + "," + talonShooterLeft1.getOutputVoltage() + "," + talonShooterLeft2.getOutputVoltage() + "," + talonShooterLeft1.getBusVoltage() + ",");
  		sb.append(motorOutputRight + "," + talonShooterRight1.getSpeed() + "," + talonShooterRight1.getOutputVoltage() + "," + talonShooterRight2.getOutputVoltage() + "," + talonShooterRight1.getBusVoltage() + ",");
  		sb.append( talonShooterLeft1.getP() + "," + talonShooterLeft1.getI() + "," + talonShooterLeft1.getD() + "," + talonShooterLeft1.getF() + ",");
  		sb.append( talonShooterRight1.getP() + "," + talonShooterRight1.getI() + "," + talonShooterRight1.getD() + "," + talonShooterRight1.getF() + "\n" );
  		//sb.append( Robot.INDEXER.getSpeed() + "," + Robot.INDEXER.getP() + "," + Robot.INDEXER.getI() + "," + Robot.INDEXER.getD() + "," + Robot.INDEXER.getF() + "\n");
  	
  		try {
  		    
  		    /*
  		     * added this if statement to stop the crashing of the robot code when running the shooter; this issue is brought about
  		     * by our incomplete logging
  		     */
  		    
  		    /*
  		     * 
  		     */
  		    if (Robot.bw != null) {
  	            Robot.bw.write(sb.toString());
  		    }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        	loops = 0;
        	System.out.println(sb.toString());
        }
    sb.setLength(0);
  	}
}