import edu.princeton.cs.algs4.Queue;
import java.util.Arrays;

public class Board {
    private int[] blocks;
    private final int[][] tiles;
    private final int blocksSize;
    private final int width;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] inputTiles) {
        tiles = inputTiles;

        if (tiles == null)
            throw new java.lang.IllegalArgumentException();

        if (tiles.length != tiles[0].length)
            throw new java.lang.IllegalArgumentException();

        width = tiles.length;
        blocksSize = width*width;
        blocks = new int[blocksSize];

        for (int i = 0; i < blocksSize; i++) {
            blocks[i] = tiles[getTilesRowColFromBlockIndex(i)[0]][getTilesRowColFromBlockIndex(i)[1]];
        }
    }

    private int[] getTilesRowColFromBlockIndex(int i) {
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
        int i = getTilesRowColFromBlockIndex(idx)[0];
        int j = getTilesRowColFromBlockIndex(idx)[1];

        int horizontalDistance = Math.abs(block % width - j);
        int verticalDistance = Math.abs(block / width - i);

        return horizontalDistance + verticalDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this)
            return true;
        if (y == null)
            return false;

        Board currentBoard = (Board) y;

        if (currentBoard.dimension() != dimension())
            return false;

        for (int i = 0; i < blocksSize; i++) {
            if (blocks[i] != currentBoard.blocks[i])
                return false;
        }

        return true;
    }

    private int getBlanckBlockIndex() {
        for (int i = 0; i < blocksSize; i ++) {
            if (blocks[i] == 0) {
                return i;
            }
        }

        return -1;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int blanckBlockIndex = getBlanckBlockIndex();
        Queue<Board> neighborsQueue = new Queue<Board>();


        if (blanckBlockIndex / width != 0) {
            Board newBoard = new Board(this.tiles);
            neighborsQueue.enqueue(swap(newBoard, blanckBlockIndex, blanckBlockIndex - width));
        }

        if (blanckBlockIndex / width != width - 1) {
            Board newBoard = new Board(this.tiles);
            neighborsQueue.enqueue(swap(newBoard, blanckBlockIndex, blanckBlockIndex + width));
        }

        if (blanckBlockIndex % width != 0) {
            Board newBoard = new Board(this.tiles);
            neighborsQueue.enqueue(swap(newBoard, blanckBlockIndex, blanckBlockIndex - 1));
        }

        if (blanckBlockIndex % width != width - 1) {
            Board newBoard = new Board(this.tiles);
            neighborsQueue.enqueue(swap(newBoard, blanckBlockIndex, blanckBlockIndex + 1));
        }

        return neighborsQueue;
    }

    private Board swap(Board currentBoard, int blanckBlockIndex, int i) {
        int temp = currentBoard.blocks[blanckBlockIndex];
        currentBoard.blocks[blanckBlockIndex] = currentBoard.blocks[i];
        currentBoard.blocks[i] = temp;

        return currentBoard;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[] twinBlocks;

        if (blocks[0] != 0 && blocks[1] != 0)
            twinBlocks = swapBlocks(0, 1);
        else twinBlocks = swapBlocks(blocksSize - 2, blocksSize - 1);

        Board currentBoard = new Board(this.tiles);
        currentBoard.blocks = twinBlocks;

        return currentBoard;
    }

    private int[] swapBlocks(int i, int j) {
        int[] blocks = Arrays.copyOf(this.blocks, this.blocks.length);
        int swap = blocks[i];
        blocks[i] = blocks[j];
        blocks[j] = swap;
        return blocks;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles2 = {{8, 1, 3},{4, 0, 2},{7 ,6 ,5}};
        Board board2 = new Board(tiles2);
        System.out.println("\n*****************************");
        System.out.println("Print: \n" + board2.toString());
        System.out.println("Hamming: " + board2.hamming());
        System.out.println("Manhattan: " + board2.manhattan());

        System.out.println("Neighbors:");
        for (Board currBoard : board2.neighbors()) {
            System.out.println(currBoard);
        }

        int[][] tiles1 = {{0, 1, 3, 12},{4, 5, 2, 13},{7 ,8 ,6, 14},{9 ,10 ,11, 15}};
        Board board1 = new Board(tiles1);
        System.out.println("\n*****************************");
        System.out.println("Print: \n" + board1.toString());
        System.out.println("Hamming: " + board1.hamming());
        System.out.println("Manhattan: " + board1.manhattan());
        System.out.println("Neighbors: " + board1.neighbors());
    }

}
