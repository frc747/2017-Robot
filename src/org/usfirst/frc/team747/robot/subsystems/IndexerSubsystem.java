package org.usfirst.frc.team747.robot.subsystems;

//import java.io.IOException;

import org.usfirst.frc.team747.robot.OI;
import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.commands.IndexerStopCommand;
import org.usfirst.frc.team747.robot.maps.RobotMap.Shooter;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.CANTalon;

public class IndexerSubsystem extends Subsystem {

  private CANTalon talonIndexer	= new CANTalon(Shooter.INDEXER.getValue());
  
  private boolean indexerRunning = false;
  private int indexerJamCount = 0; //May have to move this out to teleOp mode so it doesn't keep resetting. Not sure. 
  
  int loops = 0;
	
  public IndexerSubsystem(){
	  	
		
  }
 
  public void initDefaultCommand() {
    setDefaultCommand(new IndexerStopCommand());
  }

  
  /*
   * Used once the shooter and indexer correct speed values are determined
   */
  public void indexerStart(){

	    
		   if (Math.abs(Robot.SHOOTER.getMotorRightSpeed()) > 1750 && Math.abs(Robot.SHOOTER.getMotorLeftSpeed()) > 1750){
//		    	System.out.println("indexer moving");
				talonIndexer.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
			    talonIndexer.set(OI.getIndexerSpeed());
		    } else {
//		    	System.out.println("Indexer Stopped");
				talonIndexer.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
			    talonIndexer.set(0);
		    }
	    
  }
    
  public void indexerStop (){
      
      talonIndexer.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
      talonIndexer.set(0);
  }
  
  public void reverseIndexer(){
	  talonIndexer.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	  talonIndexer.set(OI.getIndexerSpeed());
  }
  
  public double indexerJambControl (){
	  
	  
	  double indexerSpeed = 0;
	  
	  
	  
	  if ((Math.abs(Robot.SHOOTER.getMotorLeftSpeed()) > 1400) 
			  	&& (Math.abs(Robot.SHOOTER.getMotorRightSpeed()) > 1400)
				&& (Math.abs(Robot.SHOOTER.getMotorLeftSpeed()) < 1850) 
				&& (Math.abs(Robot.SHOOTER.getMotorRightSpeed()) < 1850)){
			talonIndexer.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	    	talonIndexer.set(OI.getIndexerSpeed());
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
	  
	  
	  return indexerSpeed;
	  
  }
  
  public boolean isIndexerRunning(){
	  if (talonIndexer.getSpeed() != 0) {
		  return true;  
	  } else {
		  return false;
	  }
  }
  
}