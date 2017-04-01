package org.usfirst.frc.team747.robot.autonomous;

//import org.usfirst.frc.team747.robot.maps.AutonomousConfig;
import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.commands.AutoStepCommand;
import org.usfirst.frc.team747.robot.commands.DriveDistanceCommand;
import org.usfirst.frc.team747.robot.commands.DriveDistanceStraightCommand;
import org.usfirst.frc.team747.robot.commands.DriveRotateCommand;
import org.usfirst.frc.team747.robot.commands.IntakeTimeCommand;
import org.usfirst.frc.team747.robot.commands.NewDriveDistanceStraightCommand;
import org.usfirst.frc.team747.robot.commands.ShooterShootTimeCommand;
import org.usfirst.frc.team747.robot.commands.ShooterRevCommand;
import org.usfirst.frc.team747.robot.commands.PauseCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class HopperShootBallsFromBlue extends CommandGroup {
    
    public  HopperShootBallsFromBlue() {
        requires(Robot.DRIVE_TRAIN);
        requires(Robot.SHOOTER);
        //overdrive 2 inches when going 0.3 speed
        
//        addSequential( new AutoStepCommand(0, -98.125, 0.5));
        addSequential(new DriveDistanceCommand(-100, -0.5));
//        addSequential(new PauseCommand(.5));
//       addSequential(new DriveRotateCommand(-0.5, -90));
//        addSequential(new PauseCommand(.5));
//        addSequential(new DriveDistanceCommand(-34.1875, -0.25));
//        addSequential(new PauseCommand(.5));
//        addSequential(new DriveDistanceCommand(8, 0.25));
//        addSequential(new PauseCommand(.5));
//        addSequential(new DriveRotateCommand(0.5, 90));
//        addParallel(new IntakeTimeCommand(5));
//        addSequential(new PauseCommand(.5));
//        addSequential(new DriveDistanceCommand(80, 0.25));
//        addParallel(new ShooterRevCommand());
        
        
//        addSequential(new ShooterShootTimeCommand(8));
//        addSequential(new PauseCommand(.5));
//        addSequential(new DriveDistanceCommand(8, -.5));
//        addSequential(new PauseCommand(.5));
//        addSequential(new DriveRotateCommand(-.5, -45));
//        addSequential(new PauseCommand(.5));
//        addSequential(new DriveDistanceCommand(55, -.5));
        
    }
}
