package org.usfirst.frc.team747.robot.maps;

public final class DriverStation {
  
  public enum ControlMap {
    LEFT_JOYSTICK(1),
    RIGHT_JOYSTICK(2);
    
    private int value;
    
    private ControlMap(int value){
      this.value = value;
    }
    
    public int getValue(){
      return value;
    }
  }
  
  /**
   * Logitech Extreme 3D Pro Joystick Mapping
   */
  public enum Joystick {
    // Button Addresses
    BUTTON_1(1),
    BUTTON_2(2),
    BUTTON_3(3),
    BUTTON_4(4),
    TRIGGER(5),
    SECONDARY_TRIGGER(6),
    BUTTON_7(7),
    BUTTON_8(8),
    BUTTON_9(9),
    BUTTON_10(10),
    BUTTON_11(11),
    BUTTON_12(12),
    // Axis Addresses
    X_AXIS(0),
    Y_AXIS(1),
    Z_AXIS(2),
    THROTTLE_AXIS(3),
    HAT_X_AXIS(4),
    HAT_Y_AXIS(5);

    private int value;

    private Joystick(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }

  /**
   * "Controller Gampad (F310)" Controller Mapping
   */
  public enum GamePad {
    // Button Addresses
    A_BUTTON(1),
    B_BUTTON(2),
    X_BUTTON(3),
    Y_BUTTON(4),
    LB_BUTTON(5),
    RB_BUTTON(6),
    BACK_BUTTON(7),
    START_BUTTON(8),
    L_STICK_BUTTON(9),
    R_STICK_BUTTON(10),
    // Axis Addresses
    L_X_AXIS(0),
    L_Y_AXIS(1),
    L_TRIGGER(2),
    R_TRIGGER(3),
    R_X_AXIS(4),
    R_Y_AXIS(5);

    private int value;

    private GamePad(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }
}
