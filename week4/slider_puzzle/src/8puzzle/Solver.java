import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private boolean solvable;
    private int moves;
    private SearchNode current;

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> pqTwin = new MinPQ<>();

        SearchNode node;
        SearchNode nodeTwin;

        pq.insert(new SearchNode(initial, 0, null));
        pqTwin.insert(new SearchNode(initial.twin(), 0, null));

        while (!(pq.min().board.isGoal() || pqTwin.min().board.isGoal())) {
            node = pq.delMin();
            nodeTwin = pqTwin.delMin();

            for (Board board : node.board.neighbors()) {
                if (node.moves == 0 || !board.equals(node.previous.board)) {
                    pq.insert(new SearchNode(board, node.moves + 1, node));
                }
            }

            for (Board board : nodeTwin.board.neighbors()) {
                if (nodeTwin.moves == 0 || !board.equals(nodeTwin.previous.board)) {
                    pqTwin.insert(new SearchNode(board, nodeTwin.moves + 1, nodeTwin));
                }
            }

        }

        solvable = pq.min().board.isGoal();
        current = pq.min();
        moves = pq.min().moves;
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return moves;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        Stack<Board> boards = new Stack<>();
        SearchNode node = current;

        while (node != null) {
            boards.push(node.board);
            node = node.previous;
        }
        return boards;
    }

    private class SearchNode implements Comparable<SearchNode> {
        Board board;
        SearchNode previous;
        int moves;
        int priority;


        SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            priority = this.moves + this.board.manhattan();
            this.previous = previous;
        }

        @Override
        public int compareTo(SearchNode that) {
            if (this.priority == that.priority) {
                return this.board.manhattan() - this.board.manhattan();
            } else {
                return this.priority - that.priority;
            }
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        int[][] tiles = {{0,1,3},{4,2,5},{7,8,6}};
        Board board = new Board(tiles);

        Solver solver = new Solver(board);

        System.out.println(solver.isSolvable());
        System.out.println(solver.moves());
        System.out.println("Solution:");
        for (Board currBoard : solver.solution()) {
            System.out.println(currBoard);
        }
    }

}
