package org.usfirst.frc.team747.robot.maps;

/**
 * Robot
 * 
 * A map file to translate physical hardware address of components to logical
 * address.
 * 
 * The main purpose of this class is to be able to have multiple configurations
 * that can be swapped in and out of the code to allow to use with multiple
 * drive bases. This means that we can develop different bases that have
 * differently addressed components, or missing components, but the code will
 * still execute on the base without issue.
 * 
 * 
 * PID Tuning Notes:
 * 
 * RPM target - XXXrpm - F:.xxxx - P:.xx - I:.xx - D:X
 * 
 */
public final class Robot {

    /**
     * Two talons are used to run the shooter, one for the left wheel and one
     * for the right wheel.
     */
    public enum ShooterVals {
        LEFT_1(5),
        LEFT_2(6),
        RIGHT_1(7),
        RIGHT_2(8),
        INDEXER(9);

        private int value;

        private ShooterVals(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

    }
    
    /**
     * This will be the PID values for the Shooter
     */
    public enum ShooterSpeed {
    	SPEED(1500),
    	INDEXER_SPEED(100),
    	PROFILE(0),
    	F(0),
    	P(0),
    	I(0),
    	D(0),
    	RAMPRATE(0),
    	SHOOTER_IZONE(0);

        private int value;
        private double value2;

        private ShooterSpeed(int value) {
            this.value = value;
        }
        
        private ShooterSpeed(double value2){
        	this.value2 = value2;
        }

        public int getValue() {
            return value;
        }
        
        public double getDouble(){
        	return value2;
        }
    }

    /**
     * The drive train uses four talons, two per side.
     */
    public enum DriveTrainCan {
        LEFT_FRONT(1),
        LEFT_REAR(2),
        RIGHT_FRONT(3),
        RIGHT_REAR(4);

        private int value;

        private DriveTrainCan(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
