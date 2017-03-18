package org.usfirst.frc.team747.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team747.robot.maps.AutonomousConfig;
import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.commands.DriveDistanceCommand;

public class ScoreGear extends CommandGroup {
    
    public  ScoreGear() {
        
        requires(Robot.DRIVE_TRAIN);

        addSequential(new DriveDistanceCommand(AutonomousConfig.ScoreGear.DISTANCE, AutonomousConfig.ScoreGear.SPEED));
       // addSequential(new DriveDistanceCommand(AutonomousConfig.ScoreGear.DISTANCE2, AutonomousConfig.ScoreGear.SPEED2));

    }
}
