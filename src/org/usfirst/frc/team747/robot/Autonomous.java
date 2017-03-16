package org.usfirst.frc.team747.robot;


import edu.wpi.first.wpilibj.smartdashboard.*;

import org.usfirst.frc.team747.robot.autonomous.CrossLine;
import org.usfirst.frc.team747.robot.autonomous.ScoreGear;




public class Autonomous{
    
    public enum AutoMode{
        AUTOMODE_NONE,
        AUTOMODE_CROSS_LINE,
        AUTOMODE_SCORE_GEAR,
    }
    
    private SendableChooser autoChooser1;
//    private SendableChooser autoChooser2;
    
    public Autonomous(){
        autoChooser1 = new SendableChooser();
//        autoChooser2 = new SendableChooser();
        
        
        autoChooser1.addDefault("No autonomous", AutoMode.AUTOMODE_NONE);
        autoChooser1.addObject("Cross Line", AutoMode.AUTOMODE_CROSS_LINE);
        autoChooser1.addObject("Score Gear", AutoMode.AUTOMODE_SCORE_GEAR);
        SmartDashboard.putData("Auto mode", autoChooser1);
        
        
    }
    
    public void startMode(){
        
    	System.out.println("In Auto.StartMode");
    	
    	
        AutoMode selectedAutoMode = (AutoMode)(autoChooser1.getSelected());
                    
        switch (selectedAutoMode){
            case AUTOMODE_CROSS_LINE:
                new CrossLine().start();
                break;
            case AUTOMODE_SCORE_GEAR:
                new ScoreGear().start();
                break;
            case AUTOMODE_NONE:
                //DO NOTHING
            default:
                break;
            }
    }
}