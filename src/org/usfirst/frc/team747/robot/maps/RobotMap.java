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
 */
public final class RobotMap {

    /**
     * The drive train uses four talons, two per side.
     */
    public enum DriveTrain {
        LEFT_FRONT(0),
        LEFT_REAR(1),
        RIGHT_FRONT(2),
        RIGHT_REAR(3);

        private int value;

        private DriveTrain(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * Intake uses 1 talon.
     */
    public enum Intake {
    	INTAKE(9);
    	
    	private int value;

        private Intake(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}