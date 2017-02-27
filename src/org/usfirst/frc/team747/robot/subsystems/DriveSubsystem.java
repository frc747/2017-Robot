package org.usfirst.frc.team747.robot.subsystems;

import org.usfirst.frc.team747.robot.commands.DriveCommand;
import org.usfirst.frc.team747.robot.maps.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveSubsystem extends Subsystem {

	public CANTalon talonDriveLeftPrimary = new CANTalon(RobotMap.DriveTrain.LEFT_FRONT.getValue()),
			talonDriveLeftSlave = new CANTalon(RobotMap.DriveTrain.LEFT_REAR.getValue()),
			talonDriveRightPrimary = new CANTalon(RobotMap.DriveTrain.RIGHT_FRONT.getValue()),
			talonDriveRightSlave = new CANTalon(RobotMap.DriveTrain.RIGHT_REAR.getValue());

	public AHRS navX = new AHRS (SPI.Port.kMXP);
	
	public DriveSubsystem() {
		super();
		this.talonDriveLeftPrimary.setInverted(true);
		this.talonDriveLeftSlave.setInverted(true);
		this.talonDriveRightPrimary.setInverted(false);
		this.talonDriveRightSlave.setInverted(false);
		this.talonDriveRightPrimary.reverseSensor(true);

		this.talonDriveLeftSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		this.talonDriveLeftSlave.set(this.talonDriveLeftPrimary.getDeviceID());
		
		this.talonDriveRightSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		this.talonDriveRightSlave.set(this.talonDriveRightPrimary.getDeviceID());
	}
	
	@Override
	protected void initDefaultCommand() {
		this.setDefaultCommand(new DriveCommand());
	}

	public void changeControlMode(TalonControlMode mode) {
		this.talonDriveLeftPrimary.changeControlMode(mode);
		this.talonDriveRightPrimary.changeControlMode(mode);
	}
	
	public void set(double left, double right) {
		this.talonDriveLeftPrimary.set(left);
		this.talonDriveRightPrimary.set(right);
	}

	public void resetLeftEncoder() {
	    talonDriveLeftPrimary.setPosition(0);
	}
	
	public void resetRightEncoder() {
	    talonDriveRightPrimary.setPosition(0);
	}
	
	public double getLeftEncoderPosition() {
	    return talonDriveLeftPrimary.getPosition();
	}
	
	public double getRightEncoderPosition() {
	    return talonDriveRightPrimary.getPosition();
	}
	
    public double convertEncoderTicksToInches(double inchesToTravel) {
        
        //static hardware values (Encoder is grayhill 63R128, r128 is 128 pulsePerRevolution)
        final double wheelCircumference = 6.25 * Math.PI, 
                     ticksPerEncoder = 128;
                
        //Calculate how many ticks per inch
        final double ticksPerInch = ticksPerEncoder / wheelCircumference;
        
        final double encoderTicks = inchesToTravel / ticksPerInch;
        
        return encoderTicks;
    }

    public double getNavX360Angle(){
        double angle360   = 0;
        final int    halfCircle = 180;
  
  if (this.navX.getYaw() < 0){
      angle360 = (halfCircle + this.navX.getYaw()) + halfCircle;
      
  } else {
      angle360 = this.navX.getYaw();
  }
  return angle360;
}    
    
    public void driveStraight ( double speed, double targetAngle) {
        
        double angleDeviation,
               desiredAngle,
               currentAngle,
               speedReductionFactor,
               convertedAngle;
        
        desiredAngle = targetAngle;
        currentAngle = this.getNavX360Angle();
        
        if (currentAngle <= 360 && currentAngle >= 350){
            convertedAngle = currentAngle - 360;
        } else{
            convertedAngle = currentAngle;
        }
        
        angleDeviation = desiredAngle - convertedAngle;
        
        speedReductionFactor = (Math.abs(angleDeviation) * (50/11))/100;
        
        System.out.println("angleDeviation:" + angleDeviation + " desiredAngle:" + desiredAngle
                                + " currentAngle:" + currentAngle + " speedReducFactor:" + speedReductionFactor);
        
        if (angleDeviation < 0){
            System.out.println("SpeedReduc<0");
            //reduce right drive turns right. Less than 0 means it's left of the angle, turn right
            this.set((speed * Math.abs(speedReductionFactor)), speed);
        } else if (angleDeviation > 0){
            System.out.println("SpeedReduc>0");
            this.set(speed, (speed * Math.abs(speedReductionFactor)));
        } else {
            System.out.println("SpeedReduc=0");
            this.set(speed, speed);
        }
        
    }
    
	public void stop() {
		TalonControlMode mode = this.talonDriveLeftPrimary.getControlMode();

		double left = 0;
		double right = 0;
		
		switch (mode) {
		case Position:
			left = this.talonDriveLeftPrimary.getPosition();
			right = this.talonDriveRightPrimary.getPosition();
			break;
		case PercentVbus:
		case Speed:
		case Voltage:
		default:
			// Values should be 0;
			break;
		}
		
		this.set(left, right);
	}

}
