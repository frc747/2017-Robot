package org.usfirst.frc.team747.robot.vision;

import org.opencv.core.MatOfPoint;

public abstract class TargetTemplate {
	
	public abstract boolean isSector(MatOfPoint contour);
	public abstract Target getTarget(MatOfPoint contour1, MatOfPoint contour2, CameraSpecs camera);
	public abstract int getContourCount();
}
