import java.util.*;

/**
 * Main class for all the algorithms
 * 1)BFS
 * 2)DFID
 * 3)A*
 * 4)IDA*
 * 5)DFBnB
 */
public class Algorithms {
    private GameBoard root;
    private GameManager gameManager;
    private final MyFrame frame;
    private boolean isOpen;
    private String outputPath = "";
    private String num = "";
    private String cost = "inf";
    private final int valueAdder = 0;
    private static final char[] color3 = new char[]{'R', 'B', 'G'};
    private static final char[] color4 = new char[]{'R', 'B', 'G', 'Y'};
    MatrixDrawer matrixDrawer;
    private final Stack<GameBoard> path = new Stack<>();


    /**
     * The breadth first search algorithm to find the minimal steps to get from root board to get to the goal board
     * by using queue, that's the order of the expansion from the root after it all his neighbors etc...
     *
     * @param - Starting board
     */

    public Algorithms(MyFrame frame, GameBoard gb, GameManager gm) {
        this.frame = frame;
        this.root = gb;
        this.gameManager = gm;
        gm.run();
    }

    public void bfs(GameBoard root) {
        isOpen = root.isOpen();
        GameBoard poopedOne;
        Queue<GameBoard> openList = new LinkedList<>();
        Hashtable<GameBoard, GameBoard> openListHash = new Hashtable<>();
        Hashtable<GameBoard, GameBoard> closedList = new Hashtable<>();
        openList.add(root);
        openListHash.put(root, root);

        while (!openList.isEmpty()) {
            GameBoard currBoard = openList.poll();
            gameManager.update(currBoard.getBoard(), currBoard.getStateValue());
            frame.repaint();
            try {
                Thread.sleep(gameManager.getVelocity());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            poopedOne = currBoard;
            openListHash.remove(currBoard);
            closedList.put(currBoard, currBoard);
            for (GameBoard child : currBoard.expandMove()) {
                if (!openListHash.containsKey(child) && !closedList.containsKey(child)) {
                    openList.add(child);
                    openListHash.put(child, child);
                }
                if (child.isGoal()) {
                    gameManager.update(child.getBoard(), child.getStateValue());
                    frame.repaint();
                    buildAnswer(child);
                    return;
                }


            }

            if (isOpen) {
                System.out.println("Now pooped:");
                poopedOne.printState();
                printFrontier(openListHash);
            }
        }
        outputPath += "no path";
        num = Long.toString(GameBoard.totalOpen);


    }

    public void dfid(GameBoard root) {
        for (int depth = 1; depth < Integer.MAX_VALUE; depth++) {
            Hashtable<GameBoard, GameBoard> loopAvoidance = new Hashtable<>();
            GameBoard result = limitedDFS(root, depth, loopAvoidance);
            if (result.getInfo() != 1) {
                return;
            }
        }
        System.out.println("No path");
    }

    /**
     * Recursively function that's combine between dfs to a bfs to avoid the problem of the space that's exists in bfs when
     * we not save all the states just the states that we work on.
     */
    private GameBoard limitedDFS(GameBoard curr, int depth, Hashtable<GameBoard, GameBoard> loopAvoidance) {
        /* 0 = False, 1 = True, 2 = Fail */
        GameBoard cutoff = new GameBoard(curr.getBoard());
        gameManager.update(curr.getBoard(), curr.getStateValue());
        try {
            Thread.sleep(gameManager.getVelocity());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        frame.repaint();
        cutoff.setInfo(1);

        if (curr.isGoal()) {
            buildAnswer(curr);
            return curr;
        } else if (depth == 0) {
            return cutoff;
        } else {
            loopAvoidance.put(curr, curr);
            cutoff.setInfo(0);
            for (GameBoard child : curr.expandMove()) {
                if (loopAvoidance.contains(child)) continue;
                GameBoard result = limitedDFS(child, depth - 1, loopAvoidance); /* Recursion step go inside to the next successor */
                if (result.getInfo() == 1) {
                    cutoff.setInfo(1);
                } else if (result.getInfo() != 2) {
                    return result;

                }
            }
            loopAvoidance.remove(curr);
            if (cutoff.getInfo() == 1) {
            } else {
                cutoff.setInfo(2); /* means it failed and this is not the way  */

            }
            return cutoff;

        }
    }

    private void printFrontier(Hashtable<GameBoard, GameBoard> loopAvoidance) {
        System.out.println("Open list:");
        for (Map.Entry<GameBoard, GameBoard> entry : loopAvoidance.entrySet()) {
            if (entry.getKey().getInfo() == 0)
                entry.getKey().printState();
        }
    }


    public void aStar(GameBoard root) {
        isOpen = root.isOpen();
        int size = root.getSize();
        GameBoard poopedOne;
        PriorityQueue<GameBoard> openListQueue = new PriorityQueue<>(new GameBoard.GameBoardComparator());
        Hashtable<GameBoard, GameBoard> openListHash = new Hashtable<>();
        HashSet<GameBoard> closedList = new HashSet<>();
        openListQueue.add(root);
        openListHash.put(root, root);
        while (!openListQueue.isEmpty()) {
            GameBoard curr = openListQueue.poll();
            gameManager.update(curr.getBoard(), curr.getStateValue());
            frame.repaint();
            try {
                Thread.sleep(gameManager.getVelocity());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            poopedOne = curr;
            openListHash.remove(curr);
            closedList.add(curr);
            if (curr.isGoal()) {
                buildAnswer(curr);
                gameManager.update(curr.getBoard(),curr.getStateValue());
                frame.repaint();
                return;
            }
            for (GameBoard child : curr.expandMove()) {
                child.setChildAfterHeuristic(child.getStateValue() + heuristic(child, size));
                if (!openListQueue.contains(child) && !closedList.contains(child)) {
                    openListQueue.add(child);
                    openListHash.put(child, child);
                } else if (openListHash.containsKey(child) && openListHash.get(child).getChildAfterHeuristic() > child.getChildAfterHeuristic()) {
                    openListQueue.remove(openListHash.get(child));
                    openListHash.remove(child);
                    openListQueue.add(child);

                }
            }
            if (isOpen) {
                System.out.println("Now pooped:");
                poopedOne.printState();
                printFrontier(openListHash);
            }
        }
        outputPath += "no path";
        num = Long.toString(GameBoard.totalOpen);

    }

    public void idaStar(GameBoard root) {
        isOpen = root.isOpen();
        Stack<GameBoard> openListStack = new Stack<>();
        GameBoard poopedOne;
        Hashtable<GameBoard, GameBoard> loopAvoidance = new Hashtable<>();
        int size = root.getSize();
        int maxH = heuristic(root, size);
        while (maxH != Integer.MAX_VALUE) {
            int minF = Integer.MAX_VALUE;
            openListStack.add(root);
            loopAvoidance.put(root, root);
            while (!openListStack.isEmpty()) {
                GameBoard curr = openListStack.pop();
                gameManager.setThreshold(maxH);
                gameManager.update(curr.getBoard(), curr.getStateValue());
                frame.repaint();
                try {
                    Thread.sleep(gameManager.getVelocity());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                poopedOne = curr;
                if (curr.getInfo() == 1) {  /*means marked 'out'*/
                    loopAvoidance.remove(curr);
                } else {
                    curr.setInfo(1);
                    openListStack.add(curr);
                    for (GameBoard child : curr.expandMove()) {
                        child.setChildAfterHeuristic(child.getStateValue() + heuristic(child, size));
                        if (child.getChildAfterHeuristic() > maxH) {
                            minF = Math.min(minF, child.getChildAfterHeuristic());
                            continue;
                        }
                        if (loopAvoidance.containsKey(child) && loopAvoidance.get(child).getInfo() == 1) {
                            continue;
                        }
                        if (loopAvoidance.containsKey(child) && loopAvoidance.get(child).getInfo() != 1) {
                            if (loopAvoidance.get(child).getChildAfterHeuristic() > child.getChildAfterHeuristic()) {
                                openListStack.remove(loopAvoidance.get(child));
                                loopAvoidance.remove(child);
                            } else {
                                continue;
                            }
                        }
                        if (child.isGoal()) {
                            buildAnswer(child);
                            gameManager.update(child.getBoard(),child.getStateValue());
                            frame.repaint();
                            return;
                        }
                        openListStack.add(child);
                        loopAvoidance.put(child, child);
                    }
                }
                if (isOpen) {
                    System.out.println("Now pooped:");
                    poopedOne.printState();
                    printFrontier(loopAvoidance);
                }
            }
            root.setInfo(0);
            maxH = minF;

        }
        outputPath += "no path";
        num = Long.toString(GameBoard.totalOpen);

    }

    public void dfbnb(GameBoard root) {
        isOpen = root.isOpen();
        GameBoard poopedOne;


        Stack<GameBoard> st = new Stack<>();
        ArrayList<GameBoard> childList = new ArrayList<>();
        Hashtable<GameBoard, GameBoard> loopAvoidance = new Hashtable<>();
        GameBoard result = null;

        int t = Integer.MAX_VALUE;
        root.setChildAfterHeuristic(heuristic(root,root.getSize()));
        st.add(root);
        loopAvoidance.put(root, root);


        while (!st.isEmpty()) {
            GameBoard curr = st.pop();
            gameManager.setThreshold(t);
            gameManager.update(curr.getBoard(), curr.getStateValue());
            try {
                Thread.sleep(gameManager.getVelocity());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frame.repaint();
            poopedOne = curr;
            if (curr.getInfo() == 1) {
                loopAvoidance.remove(curr);
            } else {
                curr.setInfo(1); /* Mark as out */
                st.add(curr);  /* Reload the stuck with the following current node as marked out to know the path we at right now  */
                childList = curr.expandMove();/* Return all the possible movements as list */
                setChildHeuristicDistances(childList);
                childList.sort(new GameBoard.GameBoardComparator());
                for (int i = 0 ; i < childList.size(); i++) {
                    GameBoard child = childList.get(i);
                    if (child.getChildAfterHeuristic() >= t) {
                        returnNewChildList(childList,  i- 1);
                    } else if (loopAvoidance.containsKey(child) && loopAvoidance.get(child).getInfo() == 1) {
                        childList.remove(child);
                        i--;
                    } else if (loopAvoidance.containsKey(child) && loopAvoidance.get(child).getInfo() == 0) {
                        if (loopAvoidance.get(child).getChildAfterHeuristic() <= child.getChildAfterHeuristic()) {
                            childList.remove(child);
                            i--;
                        } else {
                            st.remove(loopAvoidance.get(child));
                            loopAvoidance.remove(child);
                        }
                    } else if (child.isGoal()) {
                        t = child.getChildAfterHeuristic();
                        result = child;
                        returnNewChildList(childList, i - 1);

                    }
                }

            }
            Collections.reverse(childList);
            for (GameBoard gameBoard : childList) {
                st.add(gameBoard);
                loopAvoidance.put(gameBoard, gameBoard);

            }
            if (isOpen) {
                System.out.println("Now pooped:");
                poopedOne.printState();
                printFrontier(loopAvoidance);
            }
        }
        if (result != null) {
            gameManager.update(result.getBoard(), result.getStateValue());
            frame.repaint();
            buildAnswer(result);
        } else {
            outputPath += "no path";
            num = Long.toString(GameBoard.totalOpen);
        }
    }

    /**
     * My heuristic method works base on manhattan distances by calculating the distances vector of each two pair of colors between the current board
     * to the goal board and return the smallest distances.
     * I Iterate over each color until  finishing calculate all the distances of the specific color.
     */
    private int heuristic(GameBoard child, int size) {
        Hashtable<Character, Integer> colorMap = new Hashtable<>();
        fillColor(colorMap, size);
        boolean[][] onGoalValue = new boolean[child.getBoard().length][child.getBoard().length];
        boolean[][] onBoardValue = new boolean[child.getBoard().length][child.getBoard().length];
        char[] colors;
        if (child.getSize() == 3) {
            colors = color3;
        } else {
            colors = color4;
        }
        int sumMoves = 0;
        for (char color : colors) {
            sumMoves += finishThisColorHeuristicValue(child, color, colorMap, onBoardValue, onGoalValue);
        }
        return sumMoves;
    }

    private int finishThisColorHeuristicValue(GameBoard child, char s, Hashtable<Character, Integer> colorMap, boolean[][] trueValues, boolean[][] onGoalValue) {
        int min, sum = 0, boardI = 0, boardJ = 0, goalI = 0, goalJ = 0;
        while (colorMap.get(s) > 0) {
            min = Integer.MAX_VALUE;
//            for (int i = 0; i < child.getSize(); i++) {
//                for (int j = 0; j < child.getSize(); j++) {
//                    if (child.getBoard()[i][j] == s && !trueValues[i][j]) {
            for (Pair p : child.getMappedBoard().get(s)) {
                if (!trueValues[p.getI()][p.getJ()]) {
                    int[] tempMin = searchForClosestGoal(p.getI(), p.getJ(), s, onGoalValue);
                    if (tempMin[0] < min) {
                        min = tempMin[0];
                        goalI = tempMin[1];
                        goalJ = tempMin[2];
                        boardI = p.getI();
                        boardJ = p.getJ();
                    }
                }

            }
            sum += min;
            int count = colorMap.get(s);
            trueValues[boardI][boardJ] = true;
            onGoalValue[goalI][goalJ] = true;
            colorMap.put(s, count - 1);
        }
        sum *= GameBoard.afterMoveValue(s);
        return sum;
    }

    /**
     * Function to fill the hashmap that counting how many colors have been counted.
     */
    private void fillColor(Hashtable<Character, Integer> colorMap, int size) {
        int counter = 0;
        if (size == 3)
            while (counter < size) {
                if (counter == 0) {
                    colorMap.put('R', 2);
                } else if (counter == 1) {
                    colorMap.put('B', 2);
                } else {
                    colorMap.put('G', 2);
                }
                counter++;
            }
        else {
            while (counter < 4) {
                if (counter == 0) {
                    colorMap.put('R', 4);
                } else if (counter == 1) {
                    colorMap.put('B', 4);
                } else if (counter == 2) {
                    colorMap.put('G', 4);
                } else {
                    colorMap.put('Y', 4);
                }
                counter++;
            }
        }
    }

    /**
     * Iterate over the goal indexes and calculate the distances between the board .
     *
     * @param row         - Index of board row coordinate.
     * @param column      - Index of board column coordinate.
     * @param s           - Specific color.
     * @param onGoalValue - Boolean array that's sign if I have been visited already at this goal coordinates.
     * @return sum, IndexI, IndexJ
     */
    private int[] searchForClosestGoal(int row, int column, char s, boolean[][] onGoalValue) {
        int[] ans = new int[3];
        int dx, dy, sum = Integer.MAX_VALUE, indexI = 0, indexJ = 0;
        for (Pair p : GameBoard.getMappedGoals().get(s)) {
            if (!onGoalValue[p.getI()][p.getJ()]) {
                dx = Math.abs(row - p.getI());
                dy = Math.abs(column - p.getJ());
                if (sum > dx + dy) {
                    sum = dx + dy;
                    indexI = p.getI();
                    indexJ = p.getJ();

                }
            }

        }
        ans[0] = sum;
        ans[1] = indexI;
        ans[2] = indexJ;
        return ans;
    }


    private void buildAnswer(GameBoard curr) {
        cost = Integer.toString(curr.getStateValue());
        while (curr.getParent() != null) {
            path.add(curr);
            curr = curr.getParent();
        }
        path.add(curr);
        buildPathWell2(path);
        num = Long.toString(GameBoard.totalOpen);

    }


    private void buildPathWell2(Stack<GameBoard> path) {
        int counter = 0;
        GameBoard firstState = null, secondState;

//        System.out.println("--------------------");
//        System.out.println("---PRINT-THE-PATH---");
//        System.out.println("--------------------");
        while (!path.isEmpty() && counter < 2) {
            if (counter == 0) {
//                path.peek().printState();
                firstState = path.pop();

                counter++;
            } else {
                secondState = path.peek();
                outputPath += checkWhereStringChanged(firstState, secondState);
                counter = 0;
            }
        }
    }

    private String checkWhereStringChanged(GameBoard firstState, GameBoard secondState) {
        String secondString = "";
        String firstString = "";
        for (int i = 0; i < firstState.getBoard().length; i++) {
            for (int j = 0; j < firstState.getBoard().length; j++) {
                if (firstState.getBoard()[i][j] != secondState.getBoard()[i][j]) {
                    if (firstState.getBoard()[i][j] == '_') {
                        if (!secondState.isGoal()) {
                            secondString = secondState.getBoard()[i][j] + ":(" + (i + 1) + "," + (j + 1) + ")--";
                        } else {
                            secondString = secondState.getBoard()[i][j] + ":(" + (i + 1) + "," + (j + 1) + ")";
                        }
//                        valueAdder += GameBoard.afterMoveValue(secondState.getBoard()[i][j]);
                    } else {
                        firstString = "(" + (i + 1) + "," + (j + 1) + "):";
                    }
                }
            }

        }

        return firstString + secondString;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public String getNum() {
        return num;
    }

    public String getCost() {
        return cost;
    }


    private void returnNewChildList(ArrayList<GameBoard> childList, int i) {
        if(i == childList.size() - 1)
            return;
        returnNewChildList (childList, ++i);
        childList.remove(i);
    }


    private void setChildHeuristicDistances(ArrayList<GameBoard> childList) {
        for (GameBoard child : childList) {
            int size = child.getSize();
            child.setChildAfterHeuristic(child.getStateValue() + heuristic(child, size));
        }
    }
}
