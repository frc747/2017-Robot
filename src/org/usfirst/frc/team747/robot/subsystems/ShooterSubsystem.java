package org.usfirst.frc.team747.robot.subsystems;

import org.usfirst.frc.team747.robot.commands.ShooterStopCommand;
import org.usfirst.frc.team747.robot.maps.RobotMap.Shooter;
import org.usfirst.frc.team747.robot.maps.RobotMap.ShooterSpeed;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

public class ShooterSubsystem extends Subsystem {

  private CANTalon talonShooterLeft1 = new CANTalon(Shooter.LEFT_1.getValue()),
                   talonShooterLeft2 = new CANTalon(Shooter.LEFT_2.getValue()),
                   talonShooterRight1 =	new CANTalon(Shooter.RIGHT_1.getValue()),
                   talonShooterRight2 =	new CANTalon(Shooter.RIGHT_2.getValue()),
                   talonIndexer	= new CANTalon(Shooter.INDEXER.getValue());
  			
  public ShooterSubsystem(){
	  	
	  	ShooterSpeed 	shooter_I = ShooterSpeed.I,
	  					shooter_P = ShooterSpeed.P,
	  					shooter_D = ShooterSpeed.D,
	  					shooter_F = ShooterSpeed.F,
	  					shooter_Profile = ShooterSpeed.PROFILE,
	  					shooter_RampRate = ShooterSpeed.RAMPRATE,
	  					shooter_IZone = ShooterSpeed.SHOOTER_IZONE,
	  					indexer_Speed = ShooterSpeed.INDEXER_SPEED;
	  	
	  	
	  
	    talonShooterLeft1.setInverted(true);
//	    talonShooterLeft2.setInverted(true);  //Don't need to invert the second motor since it follows direction of first
	   
	    
		//Set the 2nd motor on each side to be follower motors
		talonShooterLeft2.changeControlMode(CANTalon.TalonControlMode.Follower);
		talonShooterLeft2.set(talonShooterLeft1.getDeviceID());
		
		talonShooterRight2.changeControlMode(CANTalon.TalonControlMode.Follower);
		talonShooterRight2.set(talonShooterRight1.getDeviceID());
		
		
		talonShooterLeft1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		talonShooterRight1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		
		 talonShooterLeft1.reverseSensor(true); //reverses reading of encoder to put in Phase with Motor
		 
		 
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
  }
  
  /*
   * Used once the shooter and indexer correct speed values are determined
   */
  public void shooterStart(){
	  	talonShooterLeft1.changeControlMode(CANTalon.TalonControlMode.Speed);
	  	talonShooterRight1.changeControlMode(CANTalon.TalonControlMode.Speed);
	  	
	    talonShooterLeft1.set(ShooterSpeed.SPEED.getDouble());
	    talonShooterRight1.set(ShooterSpeed.SPEED.getDouble());
	    talonIndexer.set(ShooterSpeed.INDEXER_SPEED.getDouble());
  }
  
  public void shooterStop(){
	  talonShooterLeft1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	  talonShooterRight1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	  talonShooterLeft1.set(0);
	  talonShooterRight1.set(0);
  }
  
  public double getMotorLeftSpeed(){
	  
	  return (talonShooterLeft1.getEncVelocity());
	  
  }
  public double getMotorRightSpeed(){
	  
	  return (talonShooterRight1.getEncVelocity());
	  
  }
}