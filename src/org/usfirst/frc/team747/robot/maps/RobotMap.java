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
     * Two talons are used to run the shooter, one for the left wheel and one
     * for the right wheel.
     */
    public enum Shooter {
        LEFT_1(5),
        LEFT_2(6),
        RIGHT_1(7),
        RIGHT_2(8),
        INDEXER(9);

        private int value;

        private Shooter(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum Climber {
        CLIMB_1(10),
        CLIMB_2(11);

        private int value;

        private Climber(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * The drive train uses four talons, two per side.
     */
    public enum DriveTrain {
        LEFT_FRONT(1),
        LEFT_REAR(2),
        RIGHT_FRONT(3),
        RIGHT_REAR(4);

        private int value;

        private DriveTrain(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
