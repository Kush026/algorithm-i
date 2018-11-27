import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private int n;
    private int[][] board;

    public Board(int[][] blocks) {
        if (blocks == null) throw new IllegalArgumentException("Invalid method argument");

        this.n = blocks.length;
        this.board = copy(blocks);
    }

    public int dimension() {
        return this.n;
    }

    public int hamming() {
        int value = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int number = i*n+j+1;
                if (number == 9) break;
                if (number != this.board[i][j]) value++;
            }
        }
        return value;
    }

    public int manhattan() {
        int value = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) continue;
                int goalI = (board[i][j]-1)/n;
                int goalJ = (board[i][j]-1)%n;

                value += Math.abs(goalI-i)+Math.abs(goalJ-j);
            }
        }
        return value;
    }

    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int number = n*i+j+1;
                if (number == 9) break;
                if (board[i][j] != number) return false;
            }
        }
        return true;
    }

    public Board twin() {
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n-1; j++) {
                if (this.board[i][j] != 0 && this.board[i][j+1] != 0) {
                    return new Board(swappedBlock(this.board, i, j, i, j+1));
                }
            }
        }

        throw new RuntimeException("Unknown error!");
    }

    public boolean equals(Object y) {
        if (y == null) return false;
        if (this == y) return true;

        if (y.getClass() != this.getClass()) return false;

        Board yBoard = (Board) y;

        if (yBoard.dimension() != this.dimension()) return false;

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.getBoard()[i][j] != yBoard.getBoard()[i][j]) return false;
            }
        }

        return true;
    }

    private int[][] getBoard() {
        return board;
    }

    public Iterable<Board> neighbors() {
        List<Board> boards = new ArrayList<>();

        int[] zeroLocation = zeroLocation();

        int zeroI = zeroLocation[0];
        int zeroJ = zeroLocation[1];
        int[][] board1;
        if (zeroI-1 >= 0) {
            board1 = swappedBlock(this.board, zeroI, zeroJ, zeroI-1, zeroJ);
            boards.add(new Board(board1));
        }

        if (zeroI+1 < this.n) {
            board1 = swappedBlock(this.board, zeroI, zeroJ, zeroI+1, zeroJ);
            boards.add(new Board(board1));
        }

        if (zeroJ-1 >= 0) {
            board1 = swappedBlock(this.board, zeroI, zeroJ, zeroI, zeroJ-1);
            boards.add(new Board(board1));
        }

        if (zeroJ+1 < this.n) {
            board1 = swappedBlock(this.board, zeroI, zeroJ, zeroI, zeroJ+1);
            boards.add(new Board(board1));
        }

        return boards;
    }

    private static int[][] copy(int[][] blocks) {
        int[][] copy = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            copy[i] = Arrays.copyOf(blocks[i], blocks.length);
        }
        return copy;
    }

    private int[][] swappedBlock(int[][] blocks, int i, int j, int k, int l) {
        int[][] copy = copy(blocks);
        int temp = copy[i][j];
        copy[i][j] = copy[k][l];
        copy[k][l] = temp;

        return copy;
    }

    private int[] zeroLocation() {
        int[] location = new int[2];

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.board[i][j] == 0) {
                    location[0] = i;
                    location[1] = j;
                    return location;
                }
            }
        }

        throw new RuntimeException("0 does not exists");
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();

        buffer.append(this.n+"\n");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                buffer.append(String.format("%2d ", this.board[i][j]));
            }
            buffer.append("\n");
        }

        return buffer.toString();
    }

}
