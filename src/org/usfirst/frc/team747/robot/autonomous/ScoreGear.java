package org.usfirst.frc.team747.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team747.robot.maps.AutonomousConfig;
import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.commands.*;

public class ScoreGear extends CommandGroup {
    
    public  ScoreGear() {
        
        requires(Robot.DRIVE_TRAIN);

        //This autonomous will be used for PID Testing
        
        //this one line below is what was previously in the autonomous class
//        addSequential(new DriveDistanceCommand(AutonomousConfig.ScoreGear.DISTANCE, .19));

        addSequential(new PIDDriveDistanceCommand(50));
        addSequential(new PauseCommand(2));
        addSequential(new DriveDistanceCommand(6, .3));
        

    }
}
