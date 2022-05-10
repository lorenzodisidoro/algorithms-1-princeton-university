package week4.slider_puzzle.src;

public class Board {
    private final int[] blocks;
    private final int blocksSize;
    private final int width;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null)
            throw new java.lang.IllegalArgumentException();

        if (tiles.length != tiles[0].length)
            throw new java.lang.IllegalArgumentException();

        width = tiles.length;
        blocksSize = width*width;
        blocks = new int[blocksSize];

        for (int i = 0; i < blocksSize; i++) {
            blocks[i] = tiles[getBlockRowCol(i)[0]][getBlockRowCol(i)[1]];
        }
    }

    private int[] getBlockRowCol(int i) {
        return new int[]{i/width, i%width};
    }

    // string representation of this board
    public String toString() {
        StringBuilder toReturn = new StringBuilder("");
        toReturn.append(dimension());
        toReturn.append("\n");

        for (int i = 0; i < blocksSize; i ++) {
            toReturn.append(blocks[i]);
            toReturn.append(" ");
            if (i % width == width - 1) {
                toReturn.append("\n");
            }
        }

        return toReturn.toString();
    }

    // board dimension n
    public int dimension() {
        return width;
    }

    // number of tiles out of place
    public int hamming() {
        return 0;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles1 = {{0, 1, 3},{4, 2, 5},{7 ,8 ,6}};
        Board board1 = new Board(tiles1);
        board1.toString();
    }

}
