package week1.percolation.src;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // in probability and statistics, 1.96 is the approximate value of the 97.5
    // percentile point of the standard normal distribution.
    private static final double CONFIDENCE_INTERVAL = 1.96;

    // percolation for each trials
    private final double[] percolations;

    // grind size
    private final int size;

    // experiment trials
    private final int trials;

    /**
     * Perform independent trials on an n-by-n grid
     * For example, if sites are opened in a 20-by-20 lattice according to the snapshots below,
     * then our estimate of the "percolation threshold" is 204/400 = 0.51 because the system percolates when the 204th site is opened.
     *
     * @param n
     * @param t
     */
    public PercolationStats(int n, int t) {
        isValid(n, t);
        size = n;
        trials = t;
        percolations = new double[trials];

        for (int trial = 0; trial < trials; trial++) {
            Percolation p = new Percolation(n);
            int numberOfOpenedBox = 0;

            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    numberOfOpenedBox += 1;
                }
            }

            addPercolations(trial, numberOfOpenedBox);
        }

    }

    private void addPercolations(int trial, int numberOfOpenedBox) {
        double percolationThreshold = (double) numberOfOpenedBox / (size * size);
        percolations[trial] = percolationThreshold;
    }

    private void isValid(int n, int t) {
        if (n <= 0 || t <= 0)
            throw new IllegalArgumentException("n and traial must be > 0");
    }

    // sample mean of percolation threshold
    // StdStats.mean() Returns the average value in the specified array
    public double mean() {
        return StdStats.mean(percolations);
    }

    // sample standard deviation of percolation threshold
    // StdStats.stddev() Returns the sample standard deviation in the specified array.
    public double stddev() {
        return StdStats.stddev(percolations);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_INTERVAL * stddev() / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_INTERVAL * stddev() / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats p = new PercolationStats(n, trials);
        System.out.println(p.confidenceLo());
        System.out.println(p.confidenceHi());
        System.out.println(p.mean());
        System.out.println(p.stddev());
    }

}
