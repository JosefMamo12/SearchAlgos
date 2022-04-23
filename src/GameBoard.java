import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class GameBoard {
    private final myNode[][] board;
    public static myNode[][] goal;
    private final int size;
    private final ArrayList<GameBoard> children = new ArrayList<>();
    private GameBoard parent;
    private static boolean boardFlag = false;
    static int totalOpen = 0;
    private boolean isOpen;
    private int info = 0;
    private int stateValue = 0;
    private int stateScale;
    static int cycle = 0;


    public GameBoard getParent() {
        return parent;
    }


    public GameBoard(GameInfo gi) {
        size = gi.getInit().length;
        board = new myNode[size][size];
        goal = new myNode[size][size];
        initializeBoard3Or5(gi.getInit());
        initializeBoard3Or5(gi.getGoal());
        parent = null;
        isOpen = gi.isNoOpen();

    }

    public GameBoard(myNode[][] board) {
        this.board = board;
        this.size = board.length;


    }


    private void initializeBoard3Or5(String[][] mat) {
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
        cycle++;
    }

    private void moveRight(int row, int column) {
        if (column + 1 < size && !board[row][column].getColor().equals("_") && board[row][column + 1].getColor().equals("_")) {
            if (this.parent != null && parent.board[row][column + 1].getColor().equals(board[row][column].getColor())) {
                return;
            }
            myNode[][] nextState = new myNode[size][size];
            copyState(nextState);

            myNode temp = nextState[row][column + 1];
            nextState[row][column + 1] = nextState[row][column];
            createChild(row, column, nextState, temp);
//            System.out.println(totalOpen + " Right");
//            child.printState();
        }

    }

    private void moveLeft(int row, int column) {
        if (column - 1 >= 0 && !board[row][column].getColor().equals("_") && board[row][column - 1].getColor().equals("_")) {
            if (this.parent != null && parent.board[row][column - 1].getColor().equals(board[row][column].getColor())) {
                return;
            }
            myNode[][] nextState = new myNode[size][size];
            copyState(nextState);

            myNode temp = nextState[row][column - 1];
            nextState[row][column - 1] = nextState[row][column];
            createChild(row, column, nextState, temp);
//            System.out.println(totalOpen + " Left");
//            child.printState();
        }

    }

    private void moveUp(int row, int column) {
        if (row - 1 >= 0 && !board[row][column].getColor().equals("_") && board[row - 1][column].getColor().equals("_")) {
            if (this.parent != null && parent.board[row - 1][column].getColor().equals(board[row][column].getColor())) {
                return;
            }
            myNode[][] nextState = new myNode[size][size];
            copyState(nextState);

            myNode temp = nextState[row - 1][column];
            nextState[row - 1][column] = nextState[row][column];
            createChild(row, column, nextState, temp);
//            System.out.println(totalOpen + " Up");
//            child.printState();
        }

    }

    private void createChild(int row, int column, myNode[][] nextState, myNode temp) {
        nextState[row][column] = temp;

        GameBoard child = new GameBoard(nextState);
        children.add(child);
        child.parent = this;
        child.stateValue += this.stateValue + board[row][column].getValue();
        child.stateScale = cycle;

        totalOpen++;
    }

    private void moveDown(int row, int column) {
        if (row + 1 < size && !board[row][column].getColor().equals("_") && board[row + 1][column].getColor().equals("_")) {
            if (this.parent != null && parent.board[row + 1][column].getColor().equals(board[row][column].getColor())) {
                return;
            }
            myNode[][] nextState = new myNode[size][size];
            copyState(nextState);

            myNode temp = nextState[row + 1][column];
            nextState[row + 1][column] = nextState[row][column];
            createChild(row, column, nextState, temp);
//            System.out.println(totalOpen + " Down");
//            child.printState();
        }

    }
    private void copyState(myNode[][] nextState) {
        for (int i = 0; i < size; i++) {
            System.arraycopy(board[i], 0, nextState[i], 0, size);
        }
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

    public boolean isOpen() {
        return isOpen;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public int getStateValue() {
        return stateValue;
    }
    public void setStateValue(int value){
        stateValue += value;
    }
    static class GameBoardComparator implements Comparator <GameBoard>{
        @Override
        public int compare(GameBoard o1, GameBoard o2) {
            if(o1.stateValue == o2.stateValue){
                if(o1.stateScale > o2.stateScale)
                    return 1;
                else if(o1.stateScale < o2.stateScale)
                    return -1;
            }else{
                if(o1.stateValue > o2.stateValue)
                    return 1;
                else if(o1.stateValue < o2.stateValue)
                    return -1;
            }
            return 0;
        }
    }

}
