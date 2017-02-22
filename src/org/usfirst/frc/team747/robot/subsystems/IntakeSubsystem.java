package org.usfirst.frc.team747.robot.subsystems;

import org.usfirst.frc.team747.robot.commands.IntakeStopCommand;
import org.usfirst.frc.team747.robot.maps.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

public class IntakeSubsystem extends Subsystem {

    private CANTalon talonIntake = new CANTalon(RobotMap.Intake.INTAKE.getValue());

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new IntakeStopCommand());
        this.talonIntake.setInverted(false);
    }

    public void set(boolean run) {
        this.set(run, true);
    }

    public void set(boolean run, boolean forward) {
        double speed = 0;
        if (run) {
            speed = 1.0;
            if (!forward) {
                speed *= -1;
            }
        }
        this.talonIntake.set(speed);
    }

    public void stop() {
        this.set(false);
    }

}
