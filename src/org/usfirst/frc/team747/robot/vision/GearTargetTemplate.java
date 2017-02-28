package org.usfirst.frc.team747.robot.vision;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class GearTargetTemplate extends TargetTemplate {

    private static final double TARGET_SECTION_WIDTH = 2.0;
    private static final double TARGET_SECTION_HEIGHT = 5.0;

    private static final double TARGET_WIDTH = 10.25;

    private static final double TARGET_SECTION_RATIO = TARGET_SECTION_WIDTH / TARGET_SECTION_HEIGHT;
    private static final double TARGET_WIDTH_RATIO = TARGET_SECTION_WIDTH / TARGET_WIDTH;

    private static final double TARGET_SECTION_RATIO_VARIANCE = 0.1;
    private static final double TARGET_RATIO_VARIANCE = 0.1;

    private static final double TARGET_SECTION_RATIO_MIN = TARGET_SECTION_RATIO - TARGET_SECTION_RATIO_VARIANCE;
    private static final double TARGET_SECTION_RATIO_MAX = TARGET_SECTION_RATIO + TARGET_SECTION_RATIO_VARIANCE;

    private static final double TARGET_WIDTH_RATIO_MIN = TARGET_WIDTH_RATIO - TARGET_RATIO_VARIANCE;
    private static final double TARGET_WIDTH_RATIO_MAX = TARGET_WIDTH_RATIO - TARGET_RATIO_VARIANCE;

    @Override
    public boolean isSector(Rect contour) {
        double contourRatio = (double) contour.width / (double) contour.height;
        return Math.abs(contourRatio - TARGET_SECTION_RATIO) <= TARGET_SECTION_RATIO_VARIANCE;
    }

    @Override
    public Target getTarget(Rect rect1, Rect rect2, CameraSpecs camera) {

        double ratio1 = (double) rect1.width / (double) rect1.height;
        double ratio2 = (double) rect2.width / (double) rect2.height;

        boolean left = rect1.x < rect2.x;

        int targetWidth = 0;
        if (left) {
            targetWidth = (rect2.x + rect2.width) - rect1.x;
        } else {
            targetWidth = (rect1.x + rect1.width) - rect2.x;
        }

        double widthRatio = (double) rect1.width / (double) targetWidth;

        if (Math.abs((double) ratio1 - TARGET_SECTION_RATIO) <= TARGET_SECTION_RATIO_VARIANCE
                && Math.abs((double) ratio2 - TARGET_SECTION_RATIO) <= TARGET_SECTION_RATIO_VARIANCE
                && Math.abs((double) widthRatio - TARGET_WIDTH_RATIO) <= TARGET_RATIO_VARIANCE) {
            int targetCenterX = (rect1.x + rect2.x) / 2;

            double distance = TARGET_WIDTH * camera.getDistanceConstantHorizontal() / (targetWidth);

            double delta = (targetCenterX - 0.5 * (double) camera.getImageWidth())
                            * TARGET_WIDTH / targetWidth;

            double angle = Math.atan(delta / distance);

            return new Target(angle, distance);
        }
        return null;
    }

    @Override
    public int getContourCount() {
        return 2;
    }

}
