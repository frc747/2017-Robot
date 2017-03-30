package org.usfirst.frc.team747.robot.autonomous;

//import org.usfirst.frc.team747.robot.maps.AutonomousConfig;
import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.commands.DriveDistanceCommand;
import org.usfirst.frc.team747.robot.commands.DriveRotateCommand;
import org.usfirst.frc.team747.robot.commands.ShooterShootTimeCommand;
import org.usfirst.frc.team747.robot.commands.PauseCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootBallsFromRed extends CommandGroup {
    
    public  ShootBallsFromRed() {
        requires(Robot.DRIVE_TRAIN);
        requires(Robot.SHOOTER);
        
        addSequential(new ShooterShootTimeCommand(8));
        addSequential(new PauseCommand(.5));
        addSequential(new DriveDistanceCommand(8, -.5));
        addSequential(new PauseCommand(.5));
        addSequential(new DriveRotateCommand(.5, 60));
        addSequential(new PauseCommand(.5));
        addSequential(new DriveDistanceCommand(55, -.5));
        
        
        
    }
}
