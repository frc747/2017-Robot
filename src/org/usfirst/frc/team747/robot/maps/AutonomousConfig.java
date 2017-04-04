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
    
    
}