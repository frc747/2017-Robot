package org.usfirst.frc.team747.robot.autonomous;

//import org.usfirst.frc.team747.robot.maps.AutonomousConfig;
import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.commands.DriveDistanceCommand;
import org.usfirst.frc.team747.robot.commands.DriveRotateCommand;
import org.usfirst.frc.team747.robot.commands.ShootBallsTimedCommand;
import org.usfirst.frc.team747.robot.commands.PauseCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootBallsFromBlue extends CommandGroup {
    
    public  ShootBallsFromBlue() {
        requires(Robot.DRIVE_TRAIN);
        requires(Robot.SHOOTER);
        
        addSequential(new ShootBallsTimedCommand(8));
        addSequential(new PauseCommand(.5));
        addSequential(new DriveDistanceCommand(12, -.5));
        addSequential(new PauseCommand(.5));
        addSequential(new DriveRotateCommand(-.8, -45));
        addSequential(new PauseCommand(.5));
        addSequential(new DriveDistanceCommand(55, -.5));
    }
}
