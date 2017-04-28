package org.usfirst.frc.team747.robot.autonomous;

//import org.usfirst.frc.team747.robot.maps.AutonomousConfig;
import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.commands.PIDDriveRevolutionsCommand;
import org.usfirst.frc.team747.robot.commands.PIDDriveRotateCommand;
import org.usfirst.frc.team747.robot.commands.PIDDriveRotateWithVisionCommand;
import org.usfirst.frc.team747.robot.commands.ShootBallsTimedCommand;
import org.usfirst.frc.team747.robot.maps.AutonomousConfig;
import org.usfirst.frc.team747.robot.commands.PauseCommand;
import org.usfirst.frc.team747.robot.commands.IntakeTimeCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GearShootBallsFromBlue extends CommandGroup {
    
    public  GearShootBallsFromBlue() {
        requires(Robot.DRIVE_TRAIN);
        requires(Robot.SHOOTER);

        addParallel(new IntakeTimeCommand(2));        
        addSequential(new PIDDriveRevolutionsCommand(AutonomousConfig.PIDDriveDistances.FORWARD_TO_CENTER_GEAR, false));
        addSequential(new PauseCommand(1));
        addSequential(new PIDDriveRevolutionsCommand(AutonomousConfig.PIDDriveDistances.REVERSE_AWAY_FROM_CENTER_GEAR, false));
        addSequential(new PIDDriveRotateCommand(-90)); //changes between red and blue, red positive, blue negative
        addSequential(new PIDDriveRevolutionsCommand(AutonomousConfig.PIDDriveDistances.FORWARD_TO_FRONT_OF_KEY, false));
        addSequential(new PIDDriveRotateCommand(-45)); //changes between red and blue, red positive, blue negative
        addSequential(new PIDDriveRotateWithVisionCommand(Robot.VISION_TRACKING_REAR, "BOILER"));
        addSequential(new PIDDriveRevolutionsCommand(AutonomousConfig.PIDDriveDistances.FORWARD_TO_SHOOT, false));
        addSequential(new ShootBallsTimedCommand(4));
        
    }
}
