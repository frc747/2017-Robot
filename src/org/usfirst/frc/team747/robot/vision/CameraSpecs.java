package org.usfirst.frc.team747.robot.vision;

public class CameraSpecs {

    private final int imageHeight;
    private final int imageWidth;
    private final double cameraHorizontalAngleRad;
    private final double cameraVerticalAngleRad;
    private final double distanceConstantHorizontal;
    private final double distanceConstantVertical;

    public CameraSpecs(int width, int height, double cameraHorizontalAngleRad) {
        this.imageWidth = width;
        this.imageHeight = height;

        this.cameraHorizontalAngleRad = cameraHorizontalAngleRad;
        this.cameraVerticalAngleRad = (double) this.imageHeight / (double) this.imageWidth * this.cameraHorizontalAngleRad;

        /*
         * Static calculation of VISION FACTOR to simplify the formula to calculate
         * distance. See the github issue about this,
         * https://github.com/frc747/2017-Vision/issues/1, for more info.
         */
        this.distanceConstantHorizontal = (double) this.imageWidth / (2 * Math.tan(this.cameraHorizontalAngleRad / 2));
        this.distanceConstantVertical = (double) this.imageHeight / (2 * Math.tan(this.cameraVerticalAngleRad / 2));
    }

    public int getImageHeight() {
        return this.imageHeight;
    }

    public int getImageWidth() {
        return this.imageWidth;
    }

    public double getDistanceConstantHorizontal() {
        return this.distanceConstantHorizontal;
    }

    public double getDistanceConstantVertical() {
        return this.distanceConstantVertical;
    }

}
