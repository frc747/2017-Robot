package org.usfirst.frc.team747.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team747.robot.maps.AutonomousConfig;
import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.commands.DriveDistanceCommand;
import org.usfirst.frc.team747.robot.commands.DriveRotateCommand;
import org.usfirst.frc.team747.robot.commands.PauseCommand;
import org.usfirst.frc.team747.robot.commands.VisionDriveCommand;

public class VisionGear extends CommandGroup {
    
    public  VisionGear() {
        
        requires(Robot.DRIVE_TRAIN);

        addSequential(new VisionDriveCommand(Robot.VISION_TRACKING_FRONT, "GEAR", 4));
        addSequential(new DriveRotateCommand(0.3, Robot.targetOffsetAngle));
        addSequential(new PauseCommand(1));
        addSequential(new DriveDistanceCommand(Robot.targetOffsetDistance, 0.25));
    }
}
