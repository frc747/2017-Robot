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
                   talonShooterRight2 =	new CANTalon(Shooter.RIGHT_2.getValue()),
                   talonIndexer	= new CANTalon(Shooter.INDEXER.getValue());
  
  private boolean indexerRunning = false;
  private int indexerJamCount = 0; //May have to move this out to teleOp mode so it doesn't keep resetting. Not sure. 
  
	StringBuilder sb = new StringBuilder();
	int loops = 0;
	
  public ShooterSubsystem(){
	  	
	  	ShooterSpeed 	shooter_I = ShooterSpeed.I,
	  					shooter_P = ShooterSpeed.P,
	  					shooter_D = ShooterSpeed.D,
	  					shooter_F = ShooterSpeed.F,
	  					shooter_Profile = ShooterSpeed.PROFILE,
	  					shooter_RampRate = ShooterSpeed.RAMPRATE,
	  					shooter_IZone = ShooterSpeed.SHOOTER_IZONE,
	  					indexer_Speed = ShooterSpeed.INDEXER_SPEED;
	  	
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
		

		talonShooterLeft1.setPID(	shooter_P.getDouble(), shooter_I.getDouble(), 
									shooter_D.getDouble(), shooter_F.getDouble(), 
									shooter_IZone.getValue(), shooter_RampRate.getDouble(), 
									shooter_Profile.getValue());
		
		talonShooterRight1.setPID(	shooter_P.getDouble(), shooter_I.getDouble(), 
									shooter_D.getDouble(), shooter_F.getDouble(), 
									shooter_IZone.getValue(), shooter_RampRate.getDouble(), 
									shooter_Profile.getValue());
		
		talonShooterRight1.setProfile(0);
		talonShooterLeft1.setProfile(0);
		
  }
 
  public void initDefaultCommand() {
    setDefaultCommand(new ShooterStopCommand());
  }

  
  /*
   * Used to determine the shooter and indexer speeds on the fly
   */
  public void setShooterSpeed(double leftShooterSpeed, double rightShooterSpeed, double indexerSpeed) {
    talonShooterLeft1.set(leftShooterSpeed);
//    talonShooterLeft2.set(leftShooterSpeed);
    talonShooterRight1.set(rightShooterSpeed);
//    talonShooterRight2.set(rightShooterSpeed);
    talonIndexer.set(indexerSpeed);
    
    System.out.println("LeftSpeed: " + talonShooterLeft1.getSpeed() + " RightSpeed: " + talonShooterRight1.getSpeed());
    System.out.println("LeftPercent: " + leftShooterSpeed + "RightPercent: " + rightShooterSpeed);
    System.out.println("LeftP: " + talonShooterLeft1.getOutputVoltage()/talonShooterLeft1.getBusVoltage() + " RightP:" + talonShooterRight1.getOutputVoltage()/talonShooterRight1.getBusVoltage());
    System.out.println("Left1Out: " + talonShooterLeft1.getOutputVoltage() + " Left2Out: " + talonShooterLeft2.getOutputVoltage() + " Right1Out: " + talonShooterRight1.getOutputVoltage() + " Right2Out: " + talonShooterRight2.getOutputVoltage());
    
    
  }
  
  /*
   * Used once the shooter and indexer correct speed values are determined
   */
  public void shooterStart(){
	  
	  	talonShooterLeft1.changeControlMode(CANTalon.TalonControlMode.Speed);
	  	talonShooterRight1.changeControlMode(CANTalon.TalonControlMode.Speed);
		talonIndexer.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	  	
//	    talonShooterLeft1.set(OI.getLeftShooterSpeed());
//	    talonShooterRight1.set(OI.getRightShooterSpeed());
//	    talonIndexer.set(.30);
	   
	    talonShooterLeft1.set(1800);
	    talonShooterRight1.set(1800);
	    
	    if ((Math.abs(talonShooterLeft1.getSpeed()) > 1750) && (Math.abs(talonShooterRight1.getSpeed()) > 1750)
				&& (Math.abs(talonShooterLeft1.getSpeed()) < 1850) && (Math.abs(talonShooterRight1.getSpeed()) < 1850)){
	    	talonIndexer.set(.3);
	    	indexerRunning = true;
	    } else {
	    	talonIndexer.set(0);
	    	indexerRunning = false;
	    	indexerJamCount = 0;
	    }
	    
	    //if the indexer is jammed stop it and spin it backwards a certain distance
	    if (indexerRunning && talonIndexer.getSpeed() <= 10 && indexerJamCount >=10) {
	    	indexerRunning = false;
	    	
	    	talonIndexer.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	    	talonIndexer.set(0);
	    	
	    	talonIndexer.changeControlMode(CANTalon.TalonControlMode.Position);
	    	talonIndexer.set(talonIndexer.getPosition() - 50);
	    	
	    	indexerJamCount = 0; 
	    	
	    }
	    
	    indexerJamCount ++;
	    shooterLogging();	    
  }
  
  public void shooterStop(){
	  talonShooterLeft1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	  talonShooterRight1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	  talonIndexer.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	  talonShooterLeft1.set(0);
	  talonShooterRight1.set(0);
	  talonIndexer.set(0);
  }
  
  public void reverseIndexer(){
	  
	  talonIndexer.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	  talonIndexer.set(-.3);
  }
  
  public double getMotorLeftSpeed(){
	  
	  return (talonShooterLeft1.getEncVelocity());
	  
  }
  public double getMotorRightSpeed(){
	  
	  return (talonShooterRight1.getEncVelocity());
	  
  }
  
  public void shooterLogging () {
		
		

  	/* prepare line to print */
//  	if(++loops >= 10) {
  	  	double motorOutputLeft = talonShooterLeft1.getOutputVoltage() / talonShooterLeft1.getBusVoltage();
  	  	double motorOutputRight = talonShooterRight1.getOutputVoltage() / talonShooterRight1.getBusVoltage();
  		
  	  	
  	  	
  	  	
  		sb.append(motorOutputLeft + "," + talonShooterLeft1.getSpeed() + "," + talonShooterLeft1.getOutputVoltage() + "," + talonShooterLeft2.getOutputVoltage() + "," + talonShooterLeft1.getBusVoltage() + ",");
  		sb.append(motorOutputRight + "," + talonShooterRight1.getSpeed() + "," + talonShooterRight1.getOutputVoltage() + "," + talonShooterRight2.getOutputVoltage() + "," + talonShooterRight1.getBusVoltage() + ",");
  		sb.append( talonShooterLeft1.getP() + "," + talonShooterLeft1.getI() + "," + talonShooterLeft1.getD() + "," + talonShooterLeft1.getF() + ",");
  		sb.append( talonShooterRight1.getP() + "," + talonShooterRight1.getI() + "," + talonShooterRight1.getD() + "," + talonShooterRight1.getF() + "\n");
  		try {
			Robot.bw.write(sb.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        	loops = 0;
        	System.out.println(sb.toString());
//        }
    sb.setLength(0);
  }
}