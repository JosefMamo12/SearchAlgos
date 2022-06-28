

import java.util.*;

public class GameBoard  {
    //    private final String[][] board;
    private final char[][] board;
    //    private static String[][] goal;
    private  int size;
    private HashMap<Character, LinkedList<Pair>> mappedBoard;
    private GameBoard parent;
    private static boolean boardFlag = false;
    static long totalOpen = 0;
    private boolean isOpen;
    private int info = 0;
    private int stateValue = 0;
    private int stateScale;
    private int childAfterHeuristic = 0;

    static int cycle = 0;
    private static HashMap<Character, LinkedList<Pair>> mappedGoals;
    public static char[][] goal;

    public GameBoard getParent() {
        return parent;
    }


    public GameBoard(GameInfo gi) {
        size = gi.getInit().length;
        board = gi.getInit();
        goal = gi.getGoal();
        mappedGoals = new HashMap<>();
        mappedBoard = new HashMap<>();
        initializeBoard3Or5(gi.getInit());
        initializeBoard3Or5(gi.getGoal());
        parent = null;
        isOpen = gi.isNoOpen();


    }
    public GameBoard(char[][] init, char [][] goal){
        size = init.length;
        board = init;
        GameBoard.goal = goal;
        mappedGoals = new HashMap<>();
        mappedBoard = new HashMap<>();
        initializeBoard3Or5(init);
        initializeBoard3Or5(goal);
    }

    public GameBoard(char[][] board) {
        this.board = board;
        this.size = board.length;
        mappedBoard = new HashMap<>();
        if (size == 3) {
            mappedBoard.put('R', new LinkedList<>());
            mappedBoard.put('B', new LinkedList<>());
            mappedBoard.put('G', new LinkedList<>());
        } else {
            mappedBoard.put('R', new LinkedList<>());
            mappedBoard.put('B', new LinkedList<>());
            mappedBoard.put('G', new LinkedList<>());
            mappedBoard.put('Y', new LinkedList<>());
        }
    }

    private void initializeBoard3Or5(char[][] mat) {

        char[][] temp;
        if (!boardFlag) {
            temp = board;
        } else {
            temp = goal;
        }
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                if ('R' == mat[i][j]) {
                    temp[i][j] = mat[i][j];
                    if (boardFlag && !mappedGoals.containsKey('R')) {
                        mappedGoals.put('R', new LinkedList<>());
                        mappedGoals.get('R').add(new Pair(i, j));
                    } else if (boardFlag) {
                        mappedGoals.get('R').add(new Pair(i, j));
                    } else if (!mappedBoard.containsKey('R')) {
                        mappedBoard.put('R', new LinkedList<>());
                        mappedBoard.get('R').add(new Pair(i, j));
                    } else
                        mappedBoard.get('R').add(new Pair(i, j));
                } else if ('Y' == mat[i][j]) {
                    temp[i][j] = mat[i][j];
                    if (boardFlag && !mappedGoals.containsKey('Y')) {
                        mappedGoals.put('Y', new LinkedList<>());
                        mappedGoals.get('Y').add(new Pair(i, j));
                    } else if (boardFlag) {
                        mappedGoals.get('Y').add(new Pair(i, j));
                    } else if (!mappedBoard.containsKey('Y')) {
                        mappedBoard.put('Y', new LinkedList<>());
                        mappedBoard.get('Y').add(new Pair(i, j));
                    } else
                        mappedBoard.get('Y').add(new Pair(i, j));
                } else if ('B' == mat[i][j]) {
                    temp[i][j] = mat[i][j];
                    if (boardFlag && !mappedGoals.containsKey('B')) {
                        mappedGoals.put('B', new LinkedList<>());
                        mappedGoals.get('B').add(new Pair(i, j));
                    } else if (boardFlag) {
                        mappedGoals.get('B').add(new Pair(i, j));
                    } else if (!mappedBoard.containsKey('B')) {
                        mappedBoard.put('B', new LinkedList<>());
                        mappedBoard.get('B').add(new Pair(i, j));
                    } else
                        mappedBoard.get('B').add(new Pair(i, j));
                } else if ('G' == mat[i][j]) {
                    temp[i][j] = mat[i][j];
                    if (boardFlag && !mappedGoals.containsKey('G')) {
                        mappedGoals.put('G', new LinkedList<>());
                        mappedGoals.get('G').add(new Pair(i, j));
                    } else if (boardFlag) {
                        mappedGoals.get('G').add(new Pair(i, j));
                    } else if (!mappedBoard.containsKey('G')) {
                        mappedBoard.put('G', new LinkedList<>());
                        mappedBoard.get('G').add(new Pair(i, j));
                    } else
                        mappedBoard.get('G').add(new Pair(i, j));
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
                if (board[i][j] == '_') {
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
        if (column + 1 < size && board[row][column + 1] != '_') {
            char[][] nextState = new char[size][size];
            copyState(nextState);


            char temp = nextState[row][column + 1];
            nextState[row][column + 1] = nextState[row][column];
            createChild(row, column, nextState, temp, gb, 'r');
        }

    }

    private void moveLeft(int row, int column, ArrayList<GameBoard> gb) {
        if (column - 1 >= 0 && board[row][column - 1] != '_') {


            char[][] nextState = new char[size][size];
            copyState(nextState);


            char temp = nextState[row][column - 1];
            nextState[row][column - 1] = nextState[row][column];
            createChild(row, column, nextState, temp, gb, 'l');
        }

    }

    private void moveUp(int row, int column, ArrayList<GameBoard> gb) {
        if (row - 1 >= 0 && board[row - 1][column] != '_') {

            char[][] nextState = new char[size][size];
            copyState(nextState);


            char temp = nextState[row - 1][column];
            nextState[row - 1][column] = nextState[row][column];
            createChild(row, column, nextState, temp, gb, 'u');

        }

    }


    private void moveDown(int row, int column, ArrayList<GameBoard> gb) {
        if (row + 1 < size && board[row + 1][column] != '_') {

            char[][] nextState = new char[size][size];
            copyState(nextState);


            char temp = nextState[row + 1][column];
            nextState[row + 1][column] = nextState[row][column];
            createChild(row, column, nextState, temp, gb, 'd');
        }

    }

    private void createChild(int row, int column, char[][] nextState, char temp, ArrayList<GameBoard> gb, char direction) {
        nextState[row][column] = temp;
        if (parent != null && Arrays.deepEquals(nextState, parent.board))
            return;
        GameBoard child = new GameBoard(nextState);
        clone(child.mappedBoard);
        if (direction == 'r') {
            for (Pair p : mappedBoard.get(temp)) {
                if (p.getI() == row && p.getJ() == column + 1) {
                    child.mappedBoard.get(temp).remove(p);
                    break;
                }
            }
        } else if (direction == 'l') {
            for (Pair p : mappedBoard.get(temp)) {
                if (p.getI() == row && p.getJ() == column - 1) {
                    child.mappedBoard.get(temp).remove(p);
                    break;
                }
            }
        } else if (direction == 'u') {
            for (Pair p : mappedBoard.get(temp)) {
                if (p.getI() == row - 1 && p.getJ() == column) {
                    child.mappedBoard.get(temp).remove(p);
                    break;
                }
            }
        } else if (direction == 'd') {
            for (Pair p : mappedBoard.get(temp)) {
                if (p.getI() == row + 1 && p.getJ() == column) {
                    child.mappedBoard.get(temp).remove(p);
                    break;

                }
            }

        }
        child.mappedBoard.get(temp).push(new Pair(row, column));
        child.parent = this;
        child.stateValue += this.stateValue + afterMoveValue(temp);
        child.stateScale = cycle;
        gb.add(child);
        totalOpen++;


    }

    private void clone(HashMap<Character, LinkedList<Pair>> mappedBoard) {
        for (Map.Entry<Character, LinkedList<Pair>> entry : this.mappedBoard.entrySet()) {
            if (entry.getKey() == 'R') {
                for (Pair p : entry.getValue()) {
                    mappedBoard.get('R').add(new Pair(p.getI(), p.getJ()));
                }
            } else if (entry.getKey() == 'G') {
                for (Pair p : entry.getValue()) {
                    mappedBoard.get('G').add(new Pair(p.getI(), p.getJ()));
                }
            } else if (entry.getKey() == 'Y') {
                for (Pair p : entry.getValue()) {
                    mappedBoard.get('Y').add(new Pair(p.getI(), p.getJ()));
                }
            } else if (entry.getKey() == 'B') {
                for (Pair p : entry.getValue()) {
                    mappedBoard.get('B').add(new Pair(p.getI(), p.getJ()));
                }
            }
        }
    }

    public static int afterMoveValue(char color) {
        switch (color) {
            case 'R':
            case 'Y':
                return 1;
            case 'B':
                return 2;
            case 'G':
                return 10;
            default:
                return 0;
        }
    }

    private void copyState(char[][] nextState) {
        for (int i = 0; i < size; i++) {
            System.arraycopy(board[i], 0, nextState[i], 0, size);
        }
    }

    public boolean isGoal() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != goal[i][j])
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

    public char[][] getBoard() {
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
    public HashMap<Character, LinkedList<Pair>> getMappedBoard() {
        return mappedBoard;
    }
    public static HashMap<Character, LinkedList<Pair>> getMappedGoals() {
        return mappedGoals;
    }
    public int getSize() {
        return size;
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
