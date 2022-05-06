package week3.collinear.src;

import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {
    private Point[] points;
    private LineSegment segments[];
    private int numberOfSegments;
    private LinkedList<Point> collinearPoints;

    /**
     * Finds all line segments containing 4 or more points
     * - Think of p as the origin.
     * - For each other point q, determine the slope it makes with p.
     * - Sort the points according to the slopes they makes with p.
     *   Check if any 3 (or more) adjacent points in the sorted order have equal slopes with
     *   respect to p. If so, these points, together with p, are collinear.
     *
     * @param argPoints
     */
    public FastCollinearPoints(Point[] argPoints) {
        checkPoints(argPoints);
        points = argPoints;
        Arrays.sort(points);

        segments = new LineSegment[2];
        numberOfSegments = 0;
        collinearPoints = new LinkedList<>();

        for (int i = 0; i < points.length; i++) {
            Point p = points[i]; // point p
            double previousSlope = 0.0;

            for (int j = i + 1; j < points.length; j++) {
                Point q = points[j]; // point q
                double currentSlope = p.slopeTo(q);

                if (currentSlope != previousSlope) {
                    if (collinearPoints.size() >= 3) {
                        LineSegment newLineSegment = new LineSegment(collinearPoints.getFirst(), collinearPoints.getLast());
                        enqueue(newLineSegment);
                        collinearPoints.getFirst().drawTo(collinearPoints.getLast());
                        // StdDraw.show();
                    }
                }

                collinearPoints.add(q);
                previousSlope = currentSlope;
            }
        }
    }

    private void resize() {
        segments = Arrays.copyOf(segments, segments.length + 1);
    }

    private void enqueue(LineSegment newLineSegment) {
        if (newLineSegment == null) {
            throw new IllegalArgumentException();
        }

        resize();
        segments[numberOfSegments++] = newLineSegment;
    }

    /**
     * Corner cases. Throw an IllegalArgumentException if the argument to the
     * constructor is null, if any point in the array is null, or if the argument
     * to the constructor contains a repeated point.
     *
     * @throw IllegalArgumentException
     */
    private void checkPoints(Point[] argPoints) {
        if (argPoints == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < argPoints.length; i++)
            for (int j = 0; j < points.length; j++) {
                if (points[i] == null || points[j] == null)
                    throw new IllegalArgumentException();

                if (i != j && points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }

    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

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
