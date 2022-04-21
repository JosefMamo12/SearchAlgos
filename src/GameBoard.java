import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class GameBoard {
    private final myNode[][] board;
    public static myNode[][] goal;
    private final int size;
    private final ArrayList<GameBoard> children = new ArrayList<>();
    private GameBoard parent;
    private static boolean boardFlag = false;
    static int totalOpen = 0;

    public GameBoard getParent() {
        return parent;
    }


    public GameBoard(String path) {
        GameInfo gi = new GameInfo(path);
        size = gi.getInit().length;
        board = new myNode[size][size];
        goal = new myNode[size][size];
        initializeBoard3(gi.getInit());
        initializeBoard3(gi.getGoal());
        parent = null;

    }

    public GameBoard(myNode[][] board) {
        this.board = board;
        this.size = board.length;


    }


    private void initializeBoard3(String[][] mat) {
        myNode[][] temp;
        if (!boardFlag) {
            temp = board;
        } else {
            temp = goal;
        }
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                if ("R".equals(mat[i][j]))
                    temp[i][j] = new myNode(mat[i][j]);
                else if ("Y".equals(mat[i][j]))
                    temp[i][j] = new myNode(mat[i][j]);
                else if ("B".equals(mat[i][j]))
                    temp[i][j] = new myNode(mat[i][j]);
                else if ("G".equals(mat[i][j]))
                    temp[i][j] = new myNode(mat[i][j]);
                else
                    temp[i][j] = new myNode(mat[i][j]);

            }
        }
        boardFlag = true;
    }

    public void expandMove() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                moveDown(i, j);
                moveUp(i, j);
                moveLeft(i, j);
                moveRight(i, j);
            }
        }
    }

    private void moveRight(int row, int column) {
        if (column + 1 < size && !board[row][column].getColor().equals("_") && board[row][column + 1].getColor().equals("_")) {
            if(this.parent != null && parent.board[row][column + 1].getColor().equals(board[row][column].getColor())){
                return;
            }
            myNode[][] nextState = new myNode[size][size];
            copyState(nextState);

            myNode temp = nextState[row][column + 1];
            nextState[row][column + 1] = nextState[row][column];
            nextState[row][column] = temp;

            GameBoard child = new GameBoard(nextState);
            children.add(child);
            child.parent = this;
//            child.statePath = "(" + (row + 1) + "," + (column + 1) + "):" + board[row][column].getColor() + ":(" + (row + 1) + "," + (column + 2) + ")";
//            child.totalValue += this.totalValue + board[row][column].getValue();
            totalOpen ++;
        }

    }

    private void moveLeft(int row, int column) {
        if (column - 1 >= 0 && !board[row][column].getColor().equals("_") && board[row][column - 1].getColor().equals("_")) {
            if(this.parent != null && parent.board[row][column - 1].getColor().equals(board[row][column].getColor())){
                return;
            }
            myNode[][] nextState = new myNode[size][size];
            copyState(nextState);

            myNode temp = nextState[row][column - 1];
            nextState[row][column - 1] = nextState[row][column];
            nextState[row][column] = temp;

            GameBoard child = new GameBoard(nextState);
            children.add(child);
            child.parent = this;
//            child.statePath = "(" + (row + 1) + "," + (column + 1) + "):" + board[row][column].getColor() + ":(" + (row + 1) + "," + column + ")";
//            child.totalValue += this.totalValue + board[row][column].getValue();
            totalOpen++;
        }

    }

    private void moveUp(int row, int column) {
        if (row - 1 >= 0 && !board[row][column].getColor().equals("_") && board[row - 1][column].getColor().equals("_")) {
            if(this.parent != null && parent.board[row - 1][column].getColor().equals(board[row][column].getColor())){
                return;
            }
            myNode[][] nextState = new myNode[size][size];
            copyState(nextState);

            myNode temp = nextState[row - 1][column];
            nextState[row - 1][column] = nextState[row][column];
            nextState[row][column] = temp;

            GameBoard child = new GameBoard(nextState);
            children.add(child);
            child.parent = this;
//            child.statePath = "(" + (row + 1) + "," + (column + 1) + "):" + board[row][column].getColor() + ":(" + row  + "," + (column + 1) + ")";
//            child.totalValue += this.totalValue + board[row][column].getValue();
            totalOpen++;
        }

    }

    private void moveDown(int row, int column) {
        if (row + 1 < size && !board[row][column].getColor().equals("_") && board[row + 1][column].getColor().equals("_")) {
            if(this.parent != null && parent.board[row + 1][column].getColor().equals(board[row][column].getColor())){
                return;
            }
            myNode[][] nextState = new myNode[size][size];
            copyState(nextState);

            myNode temp = nextState[row + 1][column];
            nextState[row + 1][column] = nextState[row][column];
            nextState[row][column] = temp;

            GameBoard child = new GameBoard(nextState);
            children.add(child);
            child.parent = this;
//            child.statePath = "(" + (row + 1) + "," + (column + 1) + "):" + board[row][column].getColor() + ":(" + (row + 2)  + "," + (column + 1) + ")";
//            child.totalValue += this.totalValue + board[row][column].getValue();
            totalOpen ++;
        }

    }


    private void copyState(myNode[][] nextState) {
        for (int i = 0; i < size; i++) {
            System.arraycopy(board[i], 0, nextState[i], 0, size);
        }
    }

    private boolean isSameGameBoard(myNode[][] gb) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!board[i][j].equals(gb[i][j]))
                    return false;
            }
        }
        return true;
    }

    public boolean isGoal() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!board[i][j].equals(goal[i][j]))
                    return false;
            }
        }
        return true;
    }

    public ArrayList<GameBoard> getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameBoard gameBoard = (GameBoard) o;
        return Arrays.deepEquals(board, gameBoard.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    public void printState() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j].getColor() + " ");
            }
            System.out.println();
        }

    }

    public myNode[][] getBoard() {
        return board;
    }
}
