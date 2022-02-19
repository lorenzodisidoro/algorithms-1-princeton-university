package week1.percolation.src;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int TOP = 0;
    private final int size;
    private final WeightedQuickUnionUF grid;
    private final boolean[][] opened;
    private final int bottom;
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be > 0");

        size = n;
        bottom = n * n + 1;
        grid = new WeightedQuickUnionUF(n * n + 2);
        opened = new boolean[n][n];
        openSites = 0;
    }

    /**
     * Checks to see if two given coordinates are valid, greater than 0
     * and smaller than n
     */
    private boolean isValid(int i, int j) {
        return i > 0 && j > 0 && i <= size && j <= size;
    }

    /**
     * Checks to see if two given coordinates are valid,
     * if not throw a new exception
     */
    private void isValidException(int i, int j) {
        if (!isValid(i, j))
            throw new IllegalArgumentException("n must be > 0 and <= n");
    }

    /**
     * Get the index of the box from (row, col)
     */
    private int getIndexFrom(int row, int col) {
        return size * (row - 1) + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        isValidException(row, col);

        if (isOpen(row, col)) {
            return;
        }

        openSites += 1;
        opened[row - 1][col - 1] = true;

        // case 1: for each element of first row -> must be joined to the 'TOP' element
        int currentIndex = getIndexFrom(row, col);
        if (row == 1) grid.union(currentIndex, TOP);


        // case 2: for each element of the latest row -> must be joined to the 'bottom' element
        if (row == size) grid.union(currentIndex, bottom);

        // case 3: for each in the middle rows opened -> check neighbouring and union them
        if (row > 1 && isOpen(row - 1, col)) {
            int neighbouringIndex = getIndexFrom(row - 1, col);
            grid.union(currentIndex, neighbouringIndex);
        }

        if (col > 1 && isOpen(row, col - 1)) {
            int neighbouringIndex = getIndexFrom(row, col - 1);
            grid.union(currentIndex, neighbouringIndex);
        }

        if (row < size && isOpen(row + 1, col)) {
            int neighbouringIndex = getIndexFrom(row + 1, col);
            grid.union(currentIndex, neighbouringIndex);
        }

        if (col < size && isOpen(row, col + 1)) {
            int neighbouringIndex = getIndexFrom(row, col + 1);
            grid.union(currentIndex, neighbouringIndex);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        isValidException(row, col);

        return opened[row - 1][col -1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > 0 && row < size + 1 || col > 0 && col < size + 1) {
            // includes virtual node (0) and (n)
            int currentIndex = getIndexFrom(row, col);
            return grid.find(TOP) == grid.find(currentIndex);
        } else throw new IllegalArgumentException("n must be > 0 and <= n");
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return grid.find(TOP) == grid.find(bottom);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        p.open(1, 1);
        p.open(1, 2);
        p.open(2, 2);
        p.open(3, 2);
        p.open(4, 2);
        boolean isfull = p.isFull(2, 2);
        boolean perc = p.percolates();

        System.out.println("isfull: " + isfull);
        System.out.println("percolates: " + perc);
    }
}