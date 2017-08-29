package org.usfirst.frc.team747.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team747.robot.maps.AutonomousConfig;
import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.commands.*;

public class ScoreGearBlue extends CommandGroup {
    
    public  ScoreGearBlue() {
        
        requires(Robot.DRIVE_TRAIN);

        
        addParallel(new IntakeTimeCommand(2));    
        addSequential(new PIDDriveRevolutionsCommand(AutonomousConfig.PIDDriveDistances.FORWARD_TO_SHOOT, false));
        addSequential(new PIDDriveRotateCommand(-45));
        addSequential(new PIDDriveRotateWithVisionCommand(Robot.VISION_TRACKING_FRONT, "GEAR"));
        addSequential(new VisionDriveCommand(Robot.VISION_TRACKING_FRONT, "GEAR", 4));
        
//        addSequential(new DriveDistanceCommand(AutonomousConfig.ScoreGearBlue.DISTANCE, AutonomousConfig.ScoreGearBlue.SPEED));
//        addSequential(new PauseCommand(2));
//        addSequential(new DriveDistanceCommand(6, .3));
    }
}
