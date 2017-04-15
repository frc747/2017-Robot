package org.usfirst.frc.team747.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team747.robot.maps.AutonomousConfig;
import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.commands.DriveDistanceSimpleCommand;
import org.usfirst.frc.team747.robot.commands.DriveDistanceStraightCommand;
import org.usfirst.frc.team747.robot.commands.DriveRotateSimpleCommand;
import org.usfirst.frc.team747.robot.commands.PauseCommand;
import org.usfirst.frc.team747.robot.commands.SimpleVisionDriveCommand;

public class VisionGear extends CommandGroup {
    
    public  VisionGear() {
        
        requires(Robot.DRIVE_TRAIN);

        addSequential(new SimpleVisionDriveCommand(Robot.VISION_TRACKING_FRONT, "GEAR", 4));
        addSequential(new DriveRotateSimpleCommand(0.3, Robot.targetOffsetAngle));
        addSequential(new PauseCommand(1));
        addSequential(new DriveDistanceSimpleCommand(Robot.targetOffsetDistance, 0.25));
    }
}
