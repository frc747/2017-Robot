package org.usfirst.frc.team747.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team747.robot.maps.AutonomousConfig;
import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.commands.DriveDistanceSimpleCommand;

public class CrossLine extends CommandGroup {
    
    public  CrossLine() {
        
        requires(Robot.DRIVE_TRAIN);

        addSequential(new DriveDistanceSimpleCommand(AutonomousConfig.CrossLine.DISTANCE, AutonomousConfig.CrossLine.SPEED));
//        
    }
}
