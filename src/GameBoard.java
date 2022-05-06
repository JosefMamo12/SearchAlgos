
import java.util.*;

public class GameBoard {
    private final String[][] board;

    private static String[][] goal;
    private final int size;

    public static HashMap<String, LinkedList<Pair>> getMappedGoals() {
        return mappedGoals;
    }

    public int getSize() {
        return size;
    }

    private static HashMap<String, LinkedList<Pair>> mappedGoals;
    private GameBoard parent;
    private static boolean boardFlag = false;
    static long totalOpen = 0;
    private boolean isOpen;
    private int info = 0;
    private int stateValue = 0;
    private int stateScale;
    static int cycle = 0;
    private int childAfterHeuristic = 0;


    public GameBoard getParent() {
        return parent;
    }


    public GameBoard(GameInfo gi) {
        size = gi.getInit().length;
        board = gi.getInit();
        goal = gi.getGoal();
        initializeBoard3Or5(gi.getInit());
        initializeBoard3Or5(gi.getGoal());
        parent = null;
        isOpen = gi.isNoOpen();

    }

    public GameBoard(String[][] board) {
        this.board = board;
        this.size = board.length;
    }

    private void initializeBoard3Or5(String[][] mat) {
        mappedGoals = new HashMap<>();
        String[][] temp;
        if (!boardFlag) {
            temp = board;
        } else {
            temp = goal;
        }
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                if ("R".equals(mat[i][j])) {
                    temp[i][j] = mat[i][j];
                    if (boardFlag && !mappedGoals.containsKey("R")) {
                        mappedGoals.put("R", new LinkedList<>());
                        mappedGoals.get("R").add(new Pair(i, j));
                    } else if (boardFlag) {
                        mappedGoals.get("R").add(new Pair(i, j));
                    }
                } else if ("Y".equals(mat[i][j])) {
                    temp[i][j] = mat[i][j];
                    if (boardFlag && !mappedGoals.containsKey("Y")) {
                        mappedGoals.put("Y", new LinkedList<>());
                        mappedGoals.get("Y").add(new Pair(i, j));
                    } else if (boardFlag) {
                        mappedGoals.get("Y").add(new Pair(i, j));
                    }
                } else if ("B".equals(mat[i][j])) {
                    temp[i][j] = mat[i][j];
                    if (boardFlag && !mappedGoals.containsKey("B")) {
                        mappedGoals.put("B", new LinkedList<>());
                        mappedGoals.get("B").add(new Pair(i, j));
                    } else if (boardFlag) {
                        mappedGoals.get("B").add(new Pair(i, j));
                    }
                } else if ("G".equals(mat[i][j])) {
                    temp[i][j] = mat[i][j];
                    if (boardFlag && !mappedGoals.containsKey("G")) {
                        mappedGoals.put("G", new LinkedList<>());
                        mappedGoals.get("G").add(new Pair(i, j));
                    } else if (boardFlag) {
                        mappedGoals.get("G").add(new Pair(i, j));
                    }
                } else
                    temp[i][j] = mat[i][j];
            }
        }
        boardFlag = true;
        totalOpen++;
    }

    public ArrayList<GameBoard> expandMove() {
        ArrayList<GameBoard> gb = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j].equals("_")) {
                    moveLeft(i, j, gb);
                    moveRight(i, j, gb);
                    moveUp(i, j, gb);
                    moveDown(i, j, gb);

                }
            }
        }
        cycle++;
        return gb;
    }

    private void moveRight(int row, int column, ArrayList<GameBoard> gb) {
        if (column + 1 < size && !board[row][column + 1].equals("_")) {

            String[][] nextState = new String[size][size];
            copyState(nextState);


            String temp = nextState[row][column + 1];
            nextState[row][column + 1] = nextState[row][column];
            createChild(row, column, nextState, temp, gb);
        }

    }

    private void moveLeft(int row, int column, ArrayList<GameBoard> gb) {
        if (column - 1 >= 0 && !board[row][column - 1].equals("_")) {

            String[][] nextState = new String[size][size];
            copyState(nextState);


            String temp = nextState[row][column - 1];
            nextState[row][column - 1] = nextState[row][column];
            createChild(row, column, nextState, temp, gb);
        }

    }

    private void moveUp(int row, int column, ArrayList<GameBoard> gb) {
        if (row - 1 >= 0 && !board[row - 1][column].equals("_")) {

            String[][] nextState = new String[size][size];
            copyState(nextState);


            String temp = nextState[row - 1][column];
            nextState[row - 1][column] = nextState[row][column];
            createChild(row, column, nextState, temp, gb);

        }

    }


    private void moveDown(int row, int column, ArrayList<GameBoard> gb) {
        if (row + 1 < size && !board[row + 1][column].equals("_")) {

            String[][] nextState = new String[size][size];
            copyState(nextState);


            String temp = nextState[row + 1][column];
            nextState[row + 1][column] = nextState[row][column];
            createChild(row, column, nextState, temp, gb);
        }

    }

    private void createChild(int row, int column, String[][] nextState, String temp, ArrayList<GameBoard> gb) {
        nextState[row][column] = temp;
        if (parent != null && Arrays.deepEquals(nextState, parent.board))
            return;
        GameBoard child = new GameBoard(nextState);
        totalOpen++;
        child.parent = this;
        child.stateValue += this.stateValue + afterMoveValue(temp);
        child.stateScale = cycle;
        gb.add(child);


    }

    public static int afterMoveValue(String color) {
        switch (color) {
            case "R":
            case "Y":
                return 1;
            case "B":
                return 2;
            case "G":
                return 10;
            default:
                return 0;
        }
    }

    private void copyState(String[][] nextState) {
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
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        if (size == 3)
            System.out.println("----");
        else
            System.out.println("-------");

    }

    public String[][] getBoard() {
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

    public void setChildAfterHeuristic(int heuristic) {
        childAfterHeuristic = heuristic;
    }

    public int getChildAfterHeuristic() {
        return childAfterHeuristic;
    }

    static class GameBoardComparator implements Comparator<GameBoard> {
        @Override
        public int compare(GameBoard o1, GameBoard o2) {
            if (o1.childAfterHeuristic == o2.childAfterHeuristic) {
                if (o1.stateScale > o2.stateScale)
                    return 1;
                else if (o1.stateScale < o2.stateScale)
                    return -1;
            } else {
                if (o1.childAfterHeuristic > o2.childAfterHeuristic)
                    return 1;
                else return -1;
            }
            return 0;
        }
    }

}
