package org.usfirst.frc.team747.robot.vision;

import org.opencv.core.Rect;
//import org.usfirst.frc.team747.robot.Robot;

public class GearTargetTemplate extends TargetTemplate {

    private static final double TARGET_SECTION_WIDTH = 2.0;
    private static final double TARGET_SECTION_HEIGHT = 5.0;

    private static final double TARGET_WIDTH = 10.25;

    private static final double TARGET_SECTION_RATIO = TARGET_SECTION_WIDTH / TARGET_SECTION_HEIGHT;
    private static final double TARGET_WIDTH_RATIO = TARGET_SECTION_WIDTH / TARGET_WIDTH;

    private static final double TARGET_SECTION_RATIO_VARIANCE = 0.75;
    private static final double TARGET_RATIO_VARIANCE = 0.75;

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

//            Target target = this.visionProcessor.getTarget(this.targetId);
            
//            double navXAngle = Robot.getNavXAngleRadians();
            
            //angle is in radians at this point, needs to be converted to degrees by the end of the command
            double targetAngleFromCamera = angle;
            double targetDistanceFromCamera = distance;
                
            double targetDistanceXCamera = targetDistanceFromCamera * Math.sin(targetAngleFromCamera);
            double targetDistanceZCamera = targetDistanceFromCamera * Math.cos(targetAngleFromCamera);

            //distance of the camera from our origin (front-right side from robot's perspective)
            double cameraOffsetX = 22.625;
//            double cameraOffsetX = 6.375;
            double cameraOffsetZ = 0;
               
            //this is currently the front-center of the robot and is not likely to change
            double gearSecureOffsetX = 14.5; //was 14.625
            double gearSecureOffsetZ = 0;
                
            //navX is at the center of the robot
            double navXOffsetX = 14.375;
            double navXOffsetZ = 19.625;

            double targetPositionX = targetDistanceXCamera + cameraOffsetX;
            double targetPositionZ = targetDistanceZCamera - cameraOffsetZ;

            //these are the distances the target is from the front-center part of the robot
            double targetDistanceXGearSecure = targetPositionX - gearSecureOffsetX;
            double targetDistanceZGearSecure = targetPositionZ + gearSecureOffsetZ;
                
            double targetAngleFromGearSecure = Math.atan(targetDistanceXGearSecure / targetDistanceZGearSecure);
            double targetDistanceFromGearSecure = Math.hypot(targetDistanceXGearSecure, targetDistanceZGearSecure);
                
            //distances the target is from the NavX
            double targetDistanceXNavX = targetPositionX - navXOffsetX;
            double targetDistanceZNavX = targetPositionZ + navXOffsetZ;
                
            double targetAngleFromNavX = Math.atan(targetDistanceXNavX / targetDistanceZNavX);
            double targetDistanceFromNavX = Math.hypot(targetDistanceXNavX, targetDistanceZNavX);

            return new Target(targetAngleFromNavX, targetDistanceFromGearSecure);
        }
        return null;
    }

    @Override
    public int getContourCount() {
        return 2;
    }

}
