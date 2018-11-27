import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.List;

public class Solver {

    private class BoardInstance implements Comparable<BoardInstance> {
        private Board board;
        private int moveCount = 0;
        private BoardInstance previousInstance;

        public BoardInstance(Board board) {
            this.board = board;
        }

        public BoardInstance(Board board, BoardInstance previousInstance) {
            this.board = board;
            this.previousInstance = previousInstance;
            this.moveCount = previousInstance.moveCount+1;
        }

        @Override
        public int compareTo(BoardInstance o) {
            int thisMan = this.board.manhattan();
            int thatMan = o.board.manhattan();
            return (thisMan-thatMan)+this.moveCount-o.moveCount;
        }
    }

    private BoardInstance lastInstance;

    public Solver(Board initial) {
        MinPQ<BoardInstance> pq = new MinPQ<>();
        pq.insert(new BoardInstance(initial));

        MinPQ<BoardInstance> twinPQ = new MinPQ<>();
        twinPQ.insert(new BoardInstance(initial.twin()));

        while (true) {
            this.lastInstance = run(pq);
            if (lastInstance != null || run(twinPQ) != null) return;
        }

    }

    private BoardInstance run(MinPQ<BoardInstance> pq) {
        if (pq.isEmpty()) return null;
        BoardInstance bestMove = pq.delMin();

        if (bestMove.board.isGoal()) return bestMove;

        for (Board board: bestMove.board.neighbors()) {
            if (bestMove.previousInstance == null || !board.equals(bestMove.previousInstance.board)) {
                pq.insert(new BoardInstance(board, bestMove));
            }
        }

        return null;
    }
    public boolean isSolvable() {
        return (this.lastInstance != null);
    }
    public int moves() {
        return isSolvable() ? lastInstance.moveCount : -1;
    }
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        BoardInstance move = lastInstance;
        List<Board> solution = new ArrayList<>();

        while (move != null) {
            solution.add(move.board);
            move = move.previousInstance;
        }

        return solution;
    }

}
