package org.usfirst.frc.team747.robot.subsystems;

import java.io.IOException;

import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.commands.DriveCommand;
import org.usfirst.frc.team747.robot.maps.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveSubsystem extends Subsystem {

    private static final double DEFAULT_DISTANCE_PRECISION = 1; // Inches
    private static final double DEFAULT_ANGLE_PRECISION = Math.toRadians(5); // Change to Radians
    private static final double DEFAULT_DISTANCE_VARIANCE = 2;
    private static final double DEFAULT_ANGLE_VARIANCE = Math.toRadians(10); // Change to Radians
    
    public CANTalon talonDriveLeftPrimary = new CANTalon(RobotMap.DriveTrain.LEFT_FRONT.getValue()),
            talonDriveLeftSlave = new CANTalon(RobotMap.DriveTrain.LEFT_REAR.getValue()),
            talonDriveRightPrimary = new CANTalon(RobotMap.DriveTrain.RIGHT_FRONT.getValue()),
            talonDriveRightSlave = new CANTalon(RobotMap.DriveTrain.RIGHT_REAR.getValue());

    //public RobotDrive autoDrive = new RobotDrive(talonDriveLeftPrimary, talonDriveLeftSlave, talonDriveRightPrimary, talonDriveRightSlave);
    
    private static final double ENCODER_TICKS = 4096;
//    250 for peanut, 128 for competition 
    private static final double WHEEL_CIRCUMFERNCE = 18.85; //18.875 then was 18.85

    private static final double MAX_VOLTAGE = 12;
    private static final double MIN_VOLTAGE = 0;
    
    //Gear Distance IN REVOLUTIONS 3.7125 (needed like another inch or so; trying 3.725
    
    private static final double TICKS_PER_INCH = ENCODER_TICKS / WHEEL_CIRCUMFERNCE;
    
	StringBuilder sb = new StringBuilder();
	int loops = 0;
    
    public DriveSubsystem() {
        super();
        
        /*
         * Newly added WIP PID Implementation (BRICH 4/10/17)
         */
        
        this.talonDriveLeftPrimary.setInverted(true);
        this.talonDriveLeftSlave.setInverted(true);
        
        this.talonDriveRightPrimary.setInverted(false);
        this.talonDriveRightSlave.setInverted(false);
        
        // originally: left was false and right was true 8/5/17
        this.talonDriveLeftPrimary.reverseSensor(true);
        this.talonDriveRightPrimary.reverseSensor(false);     

        this.talonDriveLeftSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        this.talonDriveLeftSlave.set(this.talonDriveLeftPrimary.getDeviceID());
        
        this.talonDriveRightSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
        this.talonDriveRightSlave.set(this.talonDriveRightPrimary.getDeviceID());

        /*
         * This next 4 lines of code were used with the old grayhill quadrature encoders
         */
        
//        talonDriveLeftPrimary.setFeedbackDevice(FeedbackDevice.QuadEncoder);
//        talonDriveRightPrimary.setFeedbackDevice(FeedbackDevice.QuadEncoder);
//        
//        talonDriveLeftPrimary.configEncoderCodesPerRev((int) ENCODER_TICKS);
//        talonDriveRightPrimary.configEncoderCodesPerRev((int) ENCODER_TICKS);
      
        talonDriveLeftPrimary.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        talonDriveRightPrimary.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        
//        talonDriveLeftPrimary.configNominalOutputVoltage(+MIN_VOLTAGE,-MIN_VOLTAGE);
//        talonDriveLeftPrimary.configPeakOutputVoltage(+MAX_VOLTAGE, -MAX_VOLTAGE);
//        talonDriveRightPrimary.configNominalOutputVoltage(+MIN_VOLTAGE,-MIN_VOLTAGE);
//        talonDriveRightPrimary.configPeakOutputVoltage(+MAX_VOLTAGE, -MAX_VOLTAGE);
        
        talonDriveLeftPrimary.setAllowableClosedLoopErr(0); // always servo
        talonDriveRightPrimary.setAllowableClosedLoopErr(0); // always servo
        
        talonDriveLeftPrimary.setProfile(0);
        talonDriveRightPrimary.setProfile(0);
        
        talonDriveLeftPrimary.setF(0.1489);
        talonDriveRightPrimary.setF(0.1489);
        
        talonDriveLeftPrimary.setMotionMagicCruiseVelocity(269); //706
        talonDriveLeftPrimary.setMotionMagicAcceleration(269); //706
        talonDriveRightPrimary.setMotionMagicCruiseVelocity(269); //706
        talonDriveRightPrimary.setMotionMagicAcceleration(269); //706
        
        this.talonDriveLeftPrimary.reverseOutput(true);
        this.talonDriveRightPrimary.reverseOutput(false);
        
        /*
         * These are the informally tuned, but somewhat effective fast values for the PID
         */

//        talonDriveLeftPrimary.setPID(1.5, 0.0, 10.0);
        
//        talonDriveLeftPrimary.setP(3.0);
//        talonDriveLeftPrimary.setI(0.0);
//        talonDriveLeftPrimary.setD(950.0);
//        talonDriveLeftPrimary.setF(0.0);
//        
//        talonDriveRightPrimary.setP(3.0);
//        talonDriveRightPrimary.setI(0.0);
//        talonDriveRightPrimary.setD(950.0);
//        talonDriveRightPrimary.setF(0.0);
        
//        talonDriveLeftPrimary.setP(3.0);
//        talonDriveLeftPrimary.setI(0.0000125);
//        talonDriveLeftPrimary.setD(4096.0); 
//        talonDriveLeftPrimary.setF(0.0);
//        
//        talonDriveRightPrimary.setP(3.0);
//        talonDriveRightPrimary.setI(0.0000125);
//        talonDriveRightPrimary.setD(4096.0); 
//        talonDriveRightPrimary.setF(0.0);
        
        

//      talonDriveLeftPrimary.setP(0.8);
//      talonDriveLeftPrimary.setI(0.000005);
//      talonDriveLeftPrimary.setD(20.0); 
//      talonDriveLeftPrimary.setF(0.0);
//      
//      talonDriveRightPrimary.setP(0.8);
//      talonDriveRightPrimary.setI(0.000005);
//      talonDriveRightPrimary.setD(20.0); 
//      talonDriveRightPrimary.setF(0.0);
        
        //old working stuff
        
    }
    
    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new DriveCommand());
    }

    public void set(double left, double right) {
        this.talonDriveLeftPrimary.set(left);
        this.talonDriveRightPrimary.set(right);
    }
    
    public void setPID(double leftRevolutions, double rightRevolutions) {
        this.talonDriveLeftPrimary.set(leftRevolutions);
        this.talonDriveRightPrimary.set(rightRevolutions);
    }
    
    public double convertRevsToInches(double revs) {
        return revs * WHEEL_CIRCUMFERNCE;
    }
    
    public double convertInchesToRevs(double inches) {
        return inches / WHEEL_CIRCUMFERNCE;
    }
    
    public double convertRevsToTicks(double revs) {
        return revs * ENCODER_TICKS;
    }
    
    public double convertTicksToRevs(double ticks) {
        return ticks / ENCODER_TICKS;
    }
    
    public double convertInchesToTicks(double inches) {
        return convertRevsToTicks(convertInchesToRevs(inches));
    }
    
    public double convertTicksToInches(double ticks) {
        return convertRevsToInches(convertTicksToRevs(ticks));
    }
    
    public void changeControlMode(TalonControlMode mode) {
        this.talonDriveLeftPrimary.changeControlMode(mode);
        this.talonDriveRightPrimary.changeControlMode(mode);
    }
    
    /*
     * We found out that we do not need the enable/disable control commands
     */
    
    public void talonEnableControl() {
        talonDriveLeftPrimary.enableControl();
        talonDriveRightPrimary.enableControl();
    }
    
    public void talonDisableControl() {
        talonDriveLeftPrimary.disableControl();
        talonDriveRightPrimary.disableControl();
    }
    
    public void enablePositionControl() {
        this.changeControlMode(TalonControlMode.MotionMagic);
//        this.talonEnableControl();
    }

    public void enableVBusControl() {
//        this.talonDisableControl();
        this.changeControlMode(TalonControlMode.PercentVbus);
        
    }
    
    public void resetLeftEncoder() {
        talonDriveLeftPrimary.setPosition(0);
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    public void resetRightEncoder() {
        talonDriveRightPrimary.setPosition(0);
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void resetBothEncoders(){
        this.enableVBusControl();
    	this.talonDriveRightPrimary.setEncPosition(0);
    	this.talonDriveLeftPrimary.setEncPosition(0);
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
        
    public double getLeftEncoderPosition() {
        return talonDriveLeftPrimary.getEncPosition();
    }
    
    public double getRightEncoderPosition() {
        return talonDriveRightPrimary.getEncPosition();
    }

    public double getLeftPosition() {
        return talonDriveLeftPrimary.getPosition();
    }
        
    public double getRightPosition() {
        return talonDriveRightPrimary.getPosition();
    }
    
    public double getCombindedEncoderPosition() {
        return (getLeftEncoderPosition() + getRightEncoderPosition()) / 2;
    }
    
    public double simpleConvertInchesToTicks(double inchesToTravel) {
        
        //static hardware values (Encoder is grayhill 63R128, r128 is 128 pulsePerRevolution)
        final double wheelCircumference = 18.75,
                     ticksPerEncoder = 128;
                
        //Calculate how many ticks per inch
        final double ticksPerInch = ticksPerEncoder / wheelCircumference;
        
        final double encoderTicks = inchesToTravel / ticksPerInch;
        
        return encoderTicks;
    }
    
    /*
     * Brian (Aryan) - A convertTicksToInches "RATIO" has been formed because of the
     * time I was messing around with the DriveDistanceCommand and began changing the
     * convertTicksToInches/wheelCircumference to see if it would fix the problems we were
     * having (dumb idea) and I forgot to change it back. Calculating 18.75/20.5 the "RATIO"
     * formed is equal to about 0.91 meaning that we must divide whatever actual distance
     * we want to travel by 0.91 to find the distance we want to input into the code
     */
    
    public double simpleConvertTicksToInches(double ticks) {
        
        //static hardware values (Encoder is grayhill 63R128, r128 is 128 pulsePerRevolution)
//        final double wheelCircumference = 6.25 * Math.PI,
//                     ticksPerEncoder = 128;
        final double wheelCircumference = 18.75,
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
        double left = power * (0.5 * diff + .5);
        double right = power * (-0.5 * diff + .5);
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
        
        double direction = distance / distanceAbs;

        double angleAbs = Math.abs(angle);
        double skew = angle / angleAbs;

        if (angleAbs < anglePrecision) {
            skew = 0;
        } else if (angleAbs < angleVariance) {
            skew *= angleAbs / angleVariance;
        }

        if (distanceAbs < distancePrecision) {
            // Try to turn.
            if (skew != 0) {
                Robot.DRIVE_TRAIN.spinDrive(skew * power);
            } else {
                Robot.DRIVE_TRAIN.stop();
            }
            return;
        } else if (distanceAbs < distanceVariance) {
            power *= distanceAbs / distanceVariance;
            power = Math.max(Math.abs(power), 0.1);
        }

        this.skewDrive(power, skew);
    }
}
