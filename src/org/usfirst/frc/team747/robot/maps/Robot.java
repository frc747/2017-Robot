package org.usfirst.frc.team747.robot.maps;

/**
 * Robot
 * 
 * A map file to translate physical hardware address of components
 * to logical address.
 * 
 * The main purpose of this class is to be able to have multiple configurations
 * that can be swapped in and out of the code to allow to use with multiple
 * drive bases. This means that we can develop different bases that have 
 * differently addressed components, or missing components, but the code
 * will still execute on the base without issue. 
 */
public final class Robot {
  
  /**
   * Two talons are used to run the shooter, one for the left wheel and one for the right wheel.
   */
  public enum Shooter{
    LEFT_SHOOTER(5),
    RIGHT_SHOOTER(6);
    
    private int value;

    private Shooter(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }
  
  /**
   * The drive train uses four talons, two per side.
   */
  public enum DriveTrain{
    FRONT_LEFT(1),
    FRONT_RIGHT(2),
    REAR_LEFT(3),
    REAR_RIGHT(4);
    
    private int value;
    
    private DriveTrain(int value) {
      this.value = value;
    }
    
    public int getValue(){
      return value;
    }
  }
}
