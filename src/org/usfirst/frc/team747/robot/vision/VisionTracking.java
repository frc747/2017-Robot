package org.usfirst.frc.team747.robot.vision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.wpilibj.vision.VisionPipeline;

/**
 * VisionTracking class.
 *
 * <p>
 * An OpenCV pipeline generated by GRIP.
 *
 * @author GRIP
 */
public class VisionTracking implements VisionPipeline {

	// Bounding rectangles should be made of > 60% of the paired contours
    private static final double BOUNDING_AREA_MINIMUM = .60;
	// Outputs
    private static double[] HSL_THRESHOLD_HUE = {47.0, 92.0};
    private static double[] HSL_THRESHOLD_SATURATION = {23.0, 255.0};
    private static double[] HSL_THRESHOLD_LUMINANCE = {30.0, 255.0};

    private final CameraSpecs cameraSpecs;
    private final Map<String, TargetTemplate> targetTemplates;
    public Map<String, Target> targets;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public VisionTracking(CameraSpecs cameraSpecs, Map<String, TargetTemplate> targetTemplates) {
        this.cameraSpecs = cameraSpecs;
        this.targetTemplates = targetTemplates;
    }

    /**
     * This is the primary method that runs the entire pipeline and updates the
     * outputs.
     */
    @Override
    public void process(Mat source) {
        // Step HSL_Threshold0:
        Mat hslThresholdOutput = hslThreshold(source, HSL_THRESHOLD_HUE, HSL_THRESHOLD_SATURATION, HSL_THRESHOLD_LUMINANCE);
        Mat desaturateOutput = desaturate(hslThresholdOutput);
        ArrayList<MatOfPoint> contours = findContours(desaturateOutput);
        analyzeCoutours(contours);
      }

    /**
     * Segment an image based on hue, saturation, and luminance ranges.
     *
     * @param input
     *            The image on which to perform the HSL threshold.
     * @param hue
     *            The min and max hue
     * @param sat
     *            The min and max saturation
     * @param lum
     *            The min and max luminance
     * @param output
     *            The image in which to store the output.
     * @return
     */
    private Mat hslThreshold(Mat input, double[] hue, double[] sat, double[] lum) {
        Mat output = new Mat();
        Imgproc.cvtColor(input, output, Imgproc.COLOR_BGR2HLS);
        Core.inRange(output, new Scalar(hue[0], lum[0], sat[0]), new Scalar(hue[1], lum[1], sat[1]),
                        output);
        return output;
    }

    /**
     * Converts a color image into shades of grey.
     *
     * @param input
     *            The image on which to perform the desaturate.
     * @param output
     *            The image in which to store the output.
     * @return
     */
    private Mat desaturate(Mat input) {
        Mat output = new Mat();
        switch (input.channels()) {
        case 1:
            // If the input is already one channel, it's already desaturated
            input.copyTo(output);
            break;
        case 3:
            Imgproc.cvtColor(input, output, Imgproc.COLOR_BGR2GRAY);
            break;
        case 4:
            Imgproc.cvtColor(input, output, Imgproc.COLOR_BGRA2GRAY);
            break;
        default:
            throw new IllegalArgumentException("Input to desaturate must have 1, 3, or 4 channels");
        }
        return output;
    }

    /**
     * Sets the values of pixels in a binary image to their distance to the
     * nearest black pixel.
     *
     * @param input
     *            The image on which to perform the Distance Transform.
     * @param type
     *            The Transform.
     * @param maskSize
     *            the size of the mask.
     * @param output
     *            The image in which to store the output.
     * @return
     */
    private ArrayList<MatOfPoint> findContours(Mat input) {
        ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(input, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        return contours;
    }

    /**
     * Analyze Contours for matching.
     *
     * @param contours
     *            Input contours for
     */
    public void analyzeCoutours(ArrayList<MatOfPoint> contours) {
        Map<String, ArrayList<Rect>> buckets = new HashMap<String, ArrayList<Rect>>();
        Map<String, Target> resultTargets = new HashMap<String, Target>();

        for (String key : this.targetTemplates.keySet()) {
            ArrayList<Rect> list = new ArrayList<Rect>();
            buckets.put(key, list);
        }

        for(MatOfPoint contour : contours) {
            Rect rect = Imgproc.boundingRect(contour);
            for (String key : this.targetTemplates.keySet()) {
                if (this.targetTemplates.get(key).isSector(rect)) {
                    buckets.get(key).add(rect);
                }
            }

            // Attempt partial Matching of two contour, to account for obstructions (Peg, Fuel, Gears)
            for(MatOfPoint contour2: contours) {
                if (contour == contour2) {
                    continue;
                }
                Rect rect2 = Imgproc.boundingRect(contour2);
                int right = Math.min(rect.x, rect2.x);
                int top = Math.min(rect.y, rect2.y);
                int left = Math.max(rect.x + rect.width, rect2.x + rect2.width);
                int bottom = Math.max(rect.y + rect.height, rect2.y + rect2.height);

                Rect bounding = new Rect(right, top, left - right, bottom - top);
                
                // Only test bounding rectangles of the parts make up the minimum area.
                if (rect.area() + rect2.area() > bounding.area() * BOUNDING_AREA_MINIMUM) {
	                for (String key : this.targetTemplates.keySet()) {
	                    if (this.targetTemplates.get(key).isSector(bounding)) {
	                        buckets.get(key).add(bounding);
	                    }
	                }
                }
            }
        }

        for (String key : buckets.keySet()) {
            ArrayList<Rect> bucket = buckets.get(key);
            Target t = null;

            for (Rect c1 : bucket) {
                for (Rect c2 : bucket) {
                    t = this.targetTemplates.get(key).getTarget(c1, c2, this.cameraSpecs);
                    if (t != null) {
                        resultTargets.put(key, t);
                        break;
                    }
                }
                if (t != null) {
                    break;
                }
            }
        }

        this.targets = resultTargets;
    }

    /**
     * Test if vision tracking has found a target.
     *
     * @return boolean If vision tracking has detected a target.
     */
    public Target getTarget(String name) {
        if (this.targets != null) {
            return this.targets.get(name);
        }
        return null;
    }
}
