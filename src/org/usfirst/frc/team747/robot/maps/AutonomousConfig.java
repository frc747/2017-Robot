package org.usfirst.frc.team747.robot.maps;

/**
 * Distances are in inches
 * Rotations are in degrees
 * Times are in seconds
 * Speed values are in motor %
 */
public final class AutonomousConfig {
    
    private AutonomousConfig() {
    } 
    
    //TODO Determine Values for these classes
    
    public final class PIDDriveDistances{
       
        private PIDDriveDistances(){
        }
        
        public static final String FORWARD_TO_CENTER_GEAR = "FORWARD_TO_CENTER_GEAR",
                                   REVERSE_AWAY_FROM_CENTER_GEAR = "REVERSE_AWAY_FROM_CENTER_GEAR",
                                   FORWARD_TO_FRONT_OF_KEY = "FORWARD_TO_FRONT_OF_KEY",
                                   FORWARD_TO_SHOOT = "FORWARD_TO_SHOOT";
    }
    
    public final class CrossLine{
        
        private CrossLine(){
        }
        
        public static final double SPEED    = .6,
                                   DISTANCE = 73.25;
    }
    public final class ScoreGear{
        
        private ScoreGear(){
        }
        
        public static final double SPEED    = .19,
        						   ANGLE	= 0,
                                   DISTANCE = 71.5,
                                   SPEED2	= .17,
                                   DISTANCE2 = 15;
    }
public final class ScoreGearBlue{
        
        private ScoreGearBlue(){
        }
        
        public static final double SPEED    = .19,
                                   ANGLE    = 0,
                                   DISTANCE = 70,
                                   SPEED2   = .17,
                                   DISTANCE2 = 15;
    }
    
    /*
     *  Brian - Change for Lehigh. Distance from the driver station
     *  diamond-plate wall to the center peg has a difference of 1 inch
     *  (red being 1 more inch in length than blue)
     *  
     *  See DriveSubsytem/convertTicksToInches for an explanation as to why the
     *  score gear "DISTANCE" values have been changed from 65 inches to 71.5
     *  inches.
     *  
     *  ScoreGearRed/DISTANCE is increased by 1.1 Inches because of the
     *  convertTicksToInches "RATIO" explained in the DriveSubsystem
     *  
     *  ScoreGearBlue/DISTANCE was tuned to 70 inches on the practice field
     *  at Lehigh the back bumper of the robot was 110 inches off of the driver
     *  station wall
     */
    
    public final class ScoreGearRed{
        
        private ScoreGearRed(){
        }
        
        public static final double SPEED    = .19,
                                   ANGLE    = 0,
                                   DISTANCE = 71.1,
                                   SPEED2   = .17,
                                   DISTANCE2 = 15;
    }
    
}