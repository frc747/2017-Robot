package org.usfirst.frc.team747.robot.autonomous;

//import org.usfirst.frc.team747.robot.maps.AutonomousConfig;
import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.commands.DriveDistanceCommand;
import org.usfirst.frc.team747.robot.commands.DriveRotateCommand;
import org.usfirst.frc.team747.robot.commands.ShootBallsTimedCommand;
import org.usfirst.frc.team747.robot.commands.PauseCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootBallsFromRed extends CommandGroup {
    
    public  ShootBallsFromRed() {
        requires(Robot.DRIVE_TRAIN);
        requires(Robot.SHOOTER);

//        addSequential(new ShootBallsTimedCommand(8));
//        addSequential(new PauseCommand(.5));
//        addSequential(new DriveDistanceCommand(8, -.5));
//        addSequential(new PauseCommand(.5));
//        addSequential(new DriveRotateCommand(.5, 90));
//        addSequential(new PauseCommand(.5));
//        addSequential(new DriveDistanceCommand(55, -.5));
        
        /*
         * Brian - the above code is the outdated Red shootBallsFromRed code, this new
         * code is able to start perpendicular to the driver station wall next
         * to the boiler and then end up in a configuration similar to the
         * shootBallsFromBlue autonomous
         */
        
        /*
         * Brian - Autonomous Logic:
         *      To Rotate Clockwise - you must have a postive speed and angle
         *      To Rotate Counter Clockwise - you must have a negative speed
         *          and angle
         *      Drive Distance will drive in either direction depending on
         *          if the speed is set for positive or negative
         */
        
        addSequential(new DriveDistanceCommand(40, -.19)); // was 45
        addSequential(new PauseCommand(.5));
        addSequential(new DriveRotateCommand(-.50, -35));
        addSequential(new PauseCommand(.5));
        addSequential(new DriveDistanceCommand(28, .30)); //66
        addSequential(new ShootBallsTimedCommand(4));
        addSequential(new PauseCommand(.5));
        addSequential(new DriveDistanceCommand(12, -.30));
        addSequential(new PauseCommand(.5));
        addSequential(new DriveRotateCommand(.50, 35));
        addSequential(new PauseCommand(.5));
        addSequential(new DriveDistanceCommand(55, -.70));

    }
}
