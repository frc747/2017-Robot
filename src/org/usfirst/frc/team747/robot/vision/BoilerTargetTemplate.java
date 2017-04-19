package org.usfirst.frc.team747.robot.vision;

import org.opencv.core.Rect;

public class BoilerTargetTemplate extends TargetTemplate {

    public static double TARGET_WIDTH = 15.0;
    public static double TARGET_HEIGHT = 8.0;

    public static double TARGET_TOP_WIDTH = TARGET_WIDTH;
    public static double TARGET_TOP_HEIGHT = 4.0;

    public static double TARGET_BOTTOM_WIDTH = TARGET_WIDTH;
    public static double TARGET_BOTTOM_HEIGHT = 2.0;

    public static double TARGET_GAP = 2.0;

    public static double TARGET_BOTTOM_DIFFERENCE = TARGET_BOTTOM_HEIGHT + TARGET_GAP;
    public static double TARGET_TOP_DIFFERENCE = TARGET_GAP + TARGET_TOP_HEIGHT;
    
    public static double Foools;

    public static double TARGET_DIFFERENCE_RATIO = TARGET_BOTTOM_DIFFERENCE / TARGET_TOP_DIFFERENCE;
    public static double TARGET_BOTTOM_RATIO = TARGET_BOTTOM_DIFFERENCE / TARGET_WIDTH;
    public static double TARGET_TOP_RATIO = TARGET_TOP_DIFFERENCE / TARGET_WIDTH;

    public static double TARGET_BOTTOM_DIFFERENCE_VARIANCE = 0.1;
    public static double TARGET_TOP_DIFFERENCE_VARIANCE = 0.1;

    public static double TARGET_DIFFERENCE_RATIO_VARIANCE = 0.2;
    public static double TARGET_BOTTOM_RATIO_VARIANCE = 0.2;
    public static double TARGET_TOP_RATIO_VARIANCE = 0.2;

    @Override
    public boolean isSector(Rect contour) {
        // If target is horizontal.
        return contour.height < contour.width;
    }

    @Override
    public Target getTarget(Rect rectTop, Rect rectBottom, CameraSpecs camera) {
        if (rectTop == rectBottom) {
            return null;
        }

        double differenceBottoms = rectBottom.y + rectBottom.height - (rectTop.y + rectTop.height);
        double differenceTops = rectBottom.y - rectTop.y;

        double differenceRatio = differenceBottoms / differenceTops;

        double targetWidth = (rectTop.width + rectBottom.width) / 2;
        double targetHeight = differenceTops / (TARGET_TOP_HEIGHT + TARGET_GAP) * TARGET_HEIGHT;

        double bottomRatio = differenceBottoms / targetWidth;
        double topRatio = differenceTops / targetWidth;

        if (Math.abs(differenceRatio - TARGET_DIFFERENCE_RATIO) < TARGET_DIFFERENCE_RATIO_VARIANCE
                && Math.abs(bottomRatio - TARGET_BOTTOM_RATIO) < TARGET_BOTTOM_RATIO_VARIANCE
                && Math.abs(topRatio - TARGET_TOP_RATIO) < TARGET_TOP_RATIO_VARIANCE) {

            double targetCenterX = (((double)(rectTop.x + rectBottom.x) + targetWidth) / 2);

            double distance = TARGET_WIDTH * camera.getDistanceConstantHorizontal() / (targetWidth);
            if (distance > 200) { 
            	return null;
            }
            
            double delta = (targetCenterX - 0.5 * (double) camera.getImageWidth())
                            * TARGET_WIDTH / targetWidth;
            
            double offCenter = (targetCenterX - 0.5 * (double) camera.getImageWidth());
            double percentOffCenter = offCenter / (camera.getImageWidth() / 2);
            double calculatedAngle = percentOffCenter * (camera.getHorizontalAngle() / 2);
            
            System.out.println("Angle: " + Math.toDegrees(calculatedAngle));

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
