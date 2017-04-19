package org.usfirst.frc.team747.robot.vision;

public class VisionUtillity {

	private static final int X = 0;
	private static final int Y = 0;
	private static final int Z = 2;
	private static final int ROLL = 0;
	private static final int PITCH = 1;
	private static final int YAW = 2;
	
	private static final double[] boilerCameraPosition = {12.0, 0.0, -12.0};
	private static final double[] boilerCameraRotation = {0.0, Math.toRadians(45), 0.0};
	private static final double[] boilerAlignmentPosition = {0.0, 0.0, 0.0};
	
	private static Target translateTarget(Target visionTarget, double[] cameraPosition, double[] cameraRotation, double[] alignmentPosition) {
        
        double[] cameraTarget = new double[3];
        
        cameraTarget[X] = Math.sin(visionTarget.getAngle()) * visionTarget.getDistance();
        cameraTarget[Z] = Math.cos(cameraRotation[PITCH]) * visionTarget.getDistance();
        cameraTarget[YAW] = visionTarget.getAngle();

        System.out.println("PITCH: " + cameraRotation[PITCH]);
        System.out.println("Target Dist: " + visionTarget.getDistance());
        System.out.println("DIRECT Dist: " + cameraTarget[Z]);

        double[] targetPositionNavX = new double[3];
        double[] targetRotationNavX = new double[3];
        
        targetPositionNavX[X] = cameraTarget[X] + cameraPosition[X];
        targetPositionNavX[Z] = cameraTarget[Z] + cameraPosition[Z];
        targetRotationNavX[YAW] = Math.atan(targetPositionNavX[X] / targetPositionNavX[Z]);
        
        double targetAlginmentDistance = targetPositionNavX[Z] - alignmentPosition[Z];
		
		Target newTarget = new Target(targetRotationNavX[YAW], targetAlginmentDistance);
		return newTarget;
	}
	
	public static Target translateBoilerTarget(Target visionTarget) {
		return translateTarget(visionTarget, boilerCameraPosition, boilerCameraRotation, boilerAlignmentPosition);
	}
	
	public static Target translateGearTarget(Target visionTarget) {
		return translateTarget(visionTarget, boilerCameraPosition, boilerCameraRotation, boilerAlignmentPosition);
	}

}
