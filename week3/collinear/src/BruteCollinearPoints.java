package week3.collinear.src;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;

public class BruteCollinearPoints {
    private Point[] points;
    private LineSegment[] segments;
    private int numberOfSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] argPoints) {
        checkPoints(argPoints);

        points = argPoints.clone();
        numberOfSegments = 0;
        segments = new LineSegment[2];

        Arrays.sort(this.points);

        // it is possible to solve the problem much faster than the brute-force solution :)
        for (int i = 0; i < points.length; i++)
            for (int j = i + 1; j < points.length - 1; j++)
                for (int k = j + 1; k < points.length - 1; k++)
                    for (int z = k + 1; z < points.length - 1; z++) {
                        if (points[i].slopeTo(points[j]) == points[j].slopeTo(this.points[k]) &&
                                points[j].slopeTo(points[k]) == points[k].slopeTo(points[z])) {

                            enqueue(new LineSegment(points[i], points[z]));

                            points[i].drawTo(points[z]);
                            StdDraw.show();
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

    /*
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
            for (int j = 0; j < argPoints.length; j++) {
                if (argPoints[i] == null || argPoints[j] == null)
                    throw new IllegalArgumentException();

                if (i != j && argPoints[i].compareTo(argPoints[j]) == 0)
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
        int n = 6;
        Point[] points = new Point[n];
        points[0] = new Point(19000, 10000);
        points[1] = new Point(18000, 10000);
        points[2] = new Point(32000, 10000);
        points[3] = new Point(21000, 10000);
        points[4] = new Point(1234, 5678);
        points[5] = new Point(14000, 10000);

        BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);

        System.out.println(bruteCollinearPoints.numberOfSegments());

        // ToDo: add other test cases
    }
}
