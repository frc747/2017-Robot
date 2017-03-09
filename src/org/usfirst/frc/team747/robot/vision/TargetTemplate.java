package org.usfirst.frc.team747.robot.vision;

import org.opencv.core.Rect;

public abstract class TargetTemplate {

    public abstract boolean isSector(Rect contour);
    public abstract Target getTarget(Rect contour1, Rect contour2, CameraSpecs camera);
    public abstract int getContourCount();
}
