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
        int hammingNumber = 0;
        for (int i = 0; i < blocksSize; i ++) {
            if (blocks[i] != i + 1 && blocks[i] != 0) {
                hammingNumber++;
            }
        }

        return hammingNumber;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sumOfHamming = 0;
        for (int i = 0; i < blocksSize; i ++) {
            if (blocks[i] != i + 1 && blocks[i] != 0) {
                sumOfHamming += getManhattanDistance(blocks[i], i);
            }
        }

        return sumOfHamming;
    }

    private int getManhattanDistance(int block, int idx) {
        block--;
        int i = idx/width;
        int j = idx%width;

        int horizontalDistance = Math.abs(block % width - j);
        int verticalDistance = Math.abs(block / width - i);

        return horizontalDistance + verticalDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

    private String getBlocks() {
        StringBuilder toReturn = new StringBuilder("");
        for (int i = 0; i < blocksSize; i ++) {
            toReturn.append(blocks[i] + " ");
        }
        return toReturn.toString();
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles2 = {{8, 1, 3},{4, 0, 2},{7 ,6 ,5}};
        Board board2 = new Board(tiles2);
        System.out.println("Print: \n" + board2.toString());
        System.out.println("Hamming: " + board2.hamming());
        System.out.println("Manhattan: " + board2.manhattan());

        int[][] tiles1 = {{0, 1, 3, 12},{4, 5, 2, 13},{7 ,8 ,6, 14},{9 ,10 ,11, 15}};
        Board board1 = new Board(tiles1);
        System.out.println("Print: \n" + board1.toString());
        System.out.println("Hamming: " + board1.hamming());
        System.out.println("Manhattan: " + board1.manhattan());
    }

}
