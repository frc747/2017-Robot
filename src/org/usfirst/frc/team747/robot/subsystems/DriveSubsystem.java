package org.usfirst.frc.team747.robot.subsystems;

import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.commands.DriveCommand;
import org.usfirst.frc.team747.robot.maps.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveSubsystem extends Subsystem {

    private static final double DEFAULT_DISTANCE_PRECISION = 3; // Inches
    private static final double DEFAULT_ANGLE_PRECISION = 1; // Radians?
    private static final double DEFAULT_DISTANCE_VARIANCE = 30;
    private static final double DEFAULT_ANGLE_VARIANCE = 1; // Radians?
    
    public CANTalon talonDriveLeftPrimary = new CANTalon(RobotMap.DriveTrain.LEFT_FRONT.getValue()),
            talonDriveLeftSlave = new CANTalon(RobotMap.DriveTrain.LEFT_REAR.getValue()),
            talonDriveLeftThree = new CANTalon(RobotMap.DriveTrain.LEFT_THREE.getValue()),
            talonDriveRightPrimary = new CANTalon(RobotMap.DriveTrain.RIGHT_FRONT.getValue()),
            talonDriveRightSlave = new CANTalon(RobotMap.DriveTrain.RIGHT_REAR.getValue()),
            talonDriveRightThree = new CANTalon(RobotMap.DriveTrain.RIGHT_THREE.getValue());
            
    
    public DriveSubsystem() {
        super();
        this.talonDriveLeftPrimary.setInverted(true);
        this.talonDriveLeftSlave.setInverted(true);
        this.talonDriveLeftThree.setInverted(true);
        
        this.talonDriveRightPrimary.setInverted(false);
        this.talonDriveRightSlave.setInverted(false);
        this.talonDriveRightThree.setInverted(false);
        
        this.talonDriveRightPrimary.reverseSensor(true);
        

        this.talonDriveLeftSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        this.talonDriveLeftSlave.set(this.talonDriveLeftPrimary.getDeviceID());
        
        this.talonDriveRightSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        this.talonDriveRightSlave.set(this.talonDriveRightPrimary.getDeviceID());
        
        this.talonDriveLeftThree.changeControlMode(CANTalon.TalonControlMode.Follower);
        this.talonDriveLeftThree.set(this.talonDriveLeftPrimary.getDeviceID());
        
        this.talonDriveRightThree.changeControlMode(CANTalon.TalonControlMode.Follower);
        this.talonDriveRightThree.set(this.talonDriveRightPrimary.getDeviceID());
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
    
    public void resetEcoders() {
    	this.resetLeftEncoder();
    	this.resetRightEncoder();
    }
    
    public double getLeftEncoderPosition() {
        return talonDriveLeftPrimary.getPosition();
    }
    
    public double getRightEncoderPosition() {
        return talonDriveRightPrimary.getPosition();
    }
    
    public double getCombindedEncoderPosition() {
        return (getLeftEncoderPosition() + getRightEncoderPosition()) / 2;
    }
    
    public double convertInchesToTicks(double inchesToTravel) {
        
        //static hardware values (Encoder is grayhill 63R128, r128 is 128 pulsePerRevolution)
        final double wheelCircumference = 6.25 * Math.PI,
                     ticksPerEncoder = 128;
                
        //Calculate how many ticks per inch
        final double ticksPerInch = ticksPerEncoder / wheelCircumference;
        
        final double encoderTicks = inchesToTravel / ticksPerInch;
        
        return encoderTicks;
    }
    
    public double convertTicksToInches(double ticks) {
        
        //static hardware values (Encoder is grayhill 63R128, r128 is 128 pulsePerRevolution)
        final double wheelCircumference = 6.25 * Math.PI,
                     ticksPerEncoder = 128;
                
        //Calculate how many ticks per inch
        final double inchesPerTick = wheelCircumference / ticksPerEncoder;
        
        final double inches = ticks * inchesPerTick;
        
        return inches;
    }
    
    public void driveStraight ( double speed, double targetAngle) {
        
        double angleDeviation,
               desiredAngle,
               currentAngle,
               speedReductionFactor,
               convertedAngle;
        
        desiredAngle = targetAngle;
        currentAngle = Robot.getNavXAngle360();
        
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
    
    public void skewDrive(double power, double diff) {
        double left = power * (0.25 * diff + .75);
        double right = power * (-0.25 * diff + .75);
        set(left, right);
    }
    
    public void spinDrive(double power) {
        set(power, -power);
    }
    
    public void driveToTarget(double angle, double distance, double power) {
        driveToTarget(angle, distance, power, DEFAULT_DISTANCE_VARIANCE, DEFAULT_ANGLE_VARIANCE);
    }
    
    public void driveToTarget(double angle, double distance, double power, double distanceVariance, double angleVariance) {
        driveToTarget(angle, distance, power, distanceVariance, angleVariance, DEFAULT_DISTANCE_PRECISION, DEFAULT_ANGLE_PRECISION);
    }
    
    public void driveToTarget(double angle, double distance, double power, double distanceVariance, double angleVariance, double distancePrecision, double anglePrecision) {
        
        double distanceAbs = Math.abs(distance);

        double angleAbs = Math.abs(angle);
        double skew = angle / angleAbs;

        if (angleAbs < anglePrecision) {
            skew = 0;
        } else if (angleAbs < anglePrecision) {
            skew *= angleAbs / anglePrecision;
        }

        if (distanceAbs < distancePrecision) {
            // @TODO Try to turn.
            if (skew != 0) {
                Robot.DRIVE_TRAIN.spinDrive(1.25 * skew * power);
            } else {
                Robot.DRIVE_TRAIN.stop();
            }
            return;
        } else if (distanceAbs < distanceVariance) {
            power *= distanceAbs / distanceVariance;
            power = Math.max(Math.abs(power), 0.1) * Math.abs(power) / power;
            System.out.println("Power: " + power);
        }

        Robot.DRIVE_TRAIN.skewDrive(power, skew);
    }

}
