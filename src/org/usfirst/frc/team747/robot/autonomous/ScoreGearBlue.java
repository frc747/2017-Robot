package org.usfirst.frc.team747.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team747.robot.maps.AutonomousConfig;
import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.commands.*;

public class ScoreGearBlue extends CommandGroup {
    
    public  ScoreGearBlue() {
        
        requires(Robot.DRIVE_TRAIN);

        addSequential(new DriveDistanceSimpleCommand(AutonomousConfig.ScoreGearBlue.DISTANCE, AutonomousConfig.ScoreGearBlue.SPEED));
        addSequential(new PauseCommand(2));
        addSequential(new DriveDistanceSimpleCommand(6, .3));
    }
}
