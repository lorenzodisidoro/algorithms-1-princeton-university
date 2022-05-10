package week3.collinear.src;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;


public class FastCollinearPoints {
    private int currentSegmentIndex = 0;
    private int numberOfSegments = 10;
    private LineSegment[] segments = new LineSegment[numberOfSegments];

    public FastCollinearPoints(Point[] points) {
        checkPoints(points, points.length);
        fastCollinearPoints(points, points.length);
    }

    private void checkPoints(Point[] points, int size) {
        if (points == null)
            throw new IllegalArgumentException("Null values");

        for (int i = 0; i < size; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("Null points not are allowed");
            for (int j = 0; j < size; j++) {
                if (points[j] == null)
                    throw new IllegalArgumentException("Null points are not allowed");
                if (i != j) {
                    if (points[i].compareTo(points[j]) == 0)
                        throw new IllegalArgumentException("Same points are not allowed");
                }
            }
        }
    }

    private void resizesegments() {
        LineSegment[] temp = new LineSegment[segments.length * 2];
        for (int resizeTracker = 0; resizeTracker < segments.length; resizeTracker++) {
            temp[resizeTracker] = segments[resizeTracker];
        }

        segments = temp;
    }

    private void fastCollinearPoints(Point[] points, int size) {
        ArrayList<Integer> currentSegmentList = new ArrayList<Integer>();

        for (int i = 0; i < size; i++) {
            Point temp = points[i];
            points[i] = points[0];
            points[0] = temp;

            Point[] sorted = points.clone();
            Arrays.sort(sorted, 1, size, temp.slopeOrder());

            double prevSlope = temp.slopeTo(sorted[1]);
            int segLenTracker = 1;
            currentSegmentList.clear();
            currentSegmentList.add(0);
            currentSegmentList.add(1);
            int minSeg = 2;

            for (int k = 2; k < size; k++) {
                double newSlope = temp.slopeTo(sorted[k]);

                if (prevSlope != newSlope || k == size - 1) {

                    Point lastPoint = sorted[k - 1];
                    if (k == size - 1 && prevSlope == newSlope) {
                        lastPoint = sorted[k];
                        currentSegmentList.add(k);
                        segLenTracker++;
                    }

                    if (segLenTracker > minSeg) {
                        Point maxPoint = temp;
                        Point minPoint = temp;

                        for (int innerCurcurrentSegmentIndex : currentSegmentList) {
                            if (sorted[innerCurcurrentSegmentIndex].compareTo(maxPoint) > 0)
                                maxPoint = sorted[innerCurcurrentSegmentIndex];

                            if (sorted[innerCurcurrentSegmentIndex].compareTo(minPoint) <= 0)
                                minPoint = sorted[innerCurcurrentSegmentIndex];
                        }

                        if (currentSegmentIndex == segments.length) {
                            resizesegments();
                        }

                        if (temp.compareTo(minPoint) == 0) {

                            segments[currentSegmentIndex] = new LineSegment(minPoint, maxPoint);
                            currentSegmentIndex++;
                        }
                    }
                    segLenTracker = 1;
                    currentSegmentList.clear();
                    currentSegmentList.add(0);
                    currentSegmentList.add(k);
                }
                else {
                    segLenTracker++;
                    currentSegmentList.add(k);
                }
                prevSlope = newSlope;

            }

        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] segmentCloned = new LineSegment[currentSegmentIndex];
        int cloneTracker = 0;
        for (LineSegment segment : segments) {
            if (segment != null) {
                segmentCloned[cloneTracker] = segment;
                // StdOut.println(segment.toString());
                cloneTracker++;
            }
        }

        assert (cloneTracker == currentSegmentIndex);

        return segmentCloned;
    }

    public static void main(String[] args) {
        int n = 6;
        Point[] points = new Point[n];
        points[0] = new Point(19000, 10000);
        points[1] = new Point(18000, 10000);
        points[2] = new Point(32000, 10000);
        points[3] = new Point(21000, 10000);
        points[4] = new Point(1234, 5678);
        points[5] = new Point(14000, 10000);

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}