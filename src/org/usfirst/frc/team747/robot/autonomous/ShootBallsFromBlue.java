package org.usfirst.frc.team747.robot.autonomous;

//import org.usfirst.frc.team747.robot.maps.AutonomousConfig;
import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.commands.DriveDistanceCommand;
import org.usfirst.frc.team747.robot.commands.DriveRotateCommand;
import org.usfirst.frc.team747.robot.commands.IntakeTimeCommand;
import org.usfirst.frc.team747.robot.commands.PIDDriveInchesCommand;
import org.usfirst.frc.team747.robot.commands.PIDDriveRotateCommand;
import org.usfirst.frc.team747.robot.commands.ShootBallsTimedCommand;
import org.usfirst.frc.team747.robot.commands.PauseCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootBallsFromBlue extends CommandGroup {
    
    public  ShootBallsFromBlue() {
        requires(Robot.DRIVE_TRAIN);
        requires(Robot.SHOOTER);

        addParallel(new IntakeTimeCommand(2));
        addSequential(new ShootBallsTimedCommand(4));
        addSequential(new PIDDriveInchesCommand(-20, 2.1, 0.0005, 250.0));
      addSequential(new DriveRotateCommand(-.5, -45));
        addSequential(new PIDDriveInchesCommand(-60, 2.65, 0.0001, 500));
          
//        addParallel(new IntakeTimeCommand(2));
//        addSequential(new ShootBallsTimedCommand(4));
//        addSequential(new PauseCommand(.5));
//        addSequential(new DriveDistanceCommand(12, -.5));
//        addSequential(new PauseCommand(.5));
//        addSequential(new DriveRotateCommand(-.5, -45));
//        addSequential(new PauseCommand(.5));
//        addSequential(new DriveDistanceCommand(70, -.5)); //was 55 inches
    }
}
