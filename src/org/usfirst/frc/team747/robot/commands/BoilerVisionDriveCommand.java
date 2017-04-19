package org.usfirst.frc.team747.robot.commands;

import org.usfirst.frc.team747.robot.Robot;
import org.usfirst.frc.team747.robot.vision.Target;
import org.usfirst.frc.team747.robot.vision.VisionTracking;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BoilerVisionDriveCommand extends VisionDriveCommand {
	
    public BoilerVisionDriveCommand(VisionTracking visionProcessor) {
        super(visionProcessor, "BOILER");
    }
    
    /**
     * @return double[] values {X, Z, Pitch}
     */
    protected double[] cameraPosition() {
    	double[] pos = {0.0, 0.0, 0.0};
    	return pos;
    }

    /**
     * Point in space offset from the navx to have the target at the end of vision tracking.
     * @return double[] values {X, Z}
     */
    protected double[] alignmentPostion() {
    	double[] pos = {0.0, 0.0};
    	return pos;
    }

}
