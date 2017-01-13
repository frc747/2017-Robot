package org.usfirst.frc.team747.robot.maps;

public final class Robot {
  
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
