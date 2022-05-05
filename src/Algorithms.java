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
    private boolean isOpen;
    private String outputPath = "";
    private String num = "";
    private String cost = "inf";
    private int valueAdder = 0;
    private static String[] color3 = new String[]{"R", "B", "G"};
    private static String[] color4 = new String[]{"R", "B", "G", "Y"};
    private final Stack<GameBoard> path = new Stack<>();

    public void bfs(GameBoard root) {
        isOpen = root.isOpen();
        GameBoard poopedOne;
        Queue<GameBoard> openList = new LinkedList<>();
        HashSet<GameBoard> openListHash = new HashSet<>();
        HashSet<GameBoard> closedList = new HashSet<>();
        openList.add(root);
        openListHash.add(root);
        boolean isSolution = false;
        while (!openList.isEmpty() && !isSolution) {
            GameBoard currBoard = openList.poll();
            poopedOne = currBoard;
            openListHash.remove(currBoard);
            closedList.add(currBoard);
            for (GameBoard child : currBoard.expandMove()) {
                if (!openListHash.contains(child) && !closedList.contains(child)) {
                    openList.add(child);
                    openListHash.add(child);
                }
                if (child.isGoal()) {
                    buildAnswer(child);
                    return;
                }


            }

            if (isOpen) {
                System.out.println("Now pooped:");
                poopedOne.printState();
                System.out.println("Open List: ");
                for (GameBoard entry : openListHash) {
                    entry.printState();
                }
                System.out.println();
            }
        }
        if (!isSolution) {
            outputPath += "no path";

        }
    }

    public void dfid(GameBoard root) {
        isOpen = root.isOpen();
        for (int depth = 1; depth < Integer.MAX_VALUE; depth++) {
            System.out.println(depth);
            HashSet<GameBoard> loopAvoidance = new HashSet<>();
            GameBoard result = limitedDFS(root, depth, loopAvoidance);
            printFroniter(loopAvoidance);
            if (result.getInfo() != 1) {
                return;
            }
        }
        System.out.println("No path");
    }

    /**
     * Recursivly function thats combine between dfs to a bfs to avoid the problem of the space thats exists in bfs when
     * we not save all the states just the states that we work on.
     */
    private GameBoard limitedDFS(GameBoard curr, int depth, HashSet<GameBoard> loopAvoidance) {
        /* 0 = False, 1 = True, 2 = Fail */
        GameBoard cutoff = new GameBoard(curr.getBoard());
        cutoff.setInfo(1);
        if (curr.isGoal()) {
            buildAnswer(curr);
            return curr;
        } else if (depth == 0) {
            return cutoff;
        } else {
            loopAvoidance.add(curr);
            cutoff.setInfo(0);
            for (GameBoard child : curr.expandMove()) {
                if (loopAvoidance.contains(child)) continue;
                GameBoard result = limitedDFS(child, depth - 1, loopAvoidance);
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

    private void printFroniter(HashSet<GameBoard> loopAvoidance) {
        for (GameBoard gb : loopAvoidance) {
            gb.printState();
        }
    }


    public void aStar(GameBoard root) {
        isOpen = root.isOpen();
        int size = root.getSize();
        PriorityQueue<GameBoard> openListQueue = new PriorityQueue<>(new GameBoard.GameBoardComparator());
        Hashtable<GameBoard, GameBoard> openListHash = new Hashtable<>();
        HashSet<GameBoard> closedList = new HashSet<>();
        openListQueue.add(root);
        openListHash.put(root, root);
        while (!openListQueue.isEmpty()) {
            GameBoard curr = openListQueue.poll();
            openListHash.remove(curr);
            closedList.add(curr);
            if (curr.isGoal()) {
                buildAnswer(curr);
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
                for (Map.Entry<GameBoard, GameBoard> entry : openListHash.entrySet()) {
                    entry.getKey().printState();
                    System.out.println("-----");
                }
                System.out.println();
                System.out.println("Next Layer");
            }

        }


        outputPath += "no path";
        num = Long.toString(GameBoard.totalOpen);

    }

    public void idaStar(GameBoard root) {
        Stack<GameBoard> openListStack = new Stack<>();
        Hashtable<GameBoard, GameBoard> loopAvoidance = new Hashtable<>();
        int size = root.getSize();
        int maxH = heuristic(root, size);
        while (maxH != Integer.MAX_VALUE) {
            int minF = Integer.MAX_VALUE;
            openListStack.add(root);
            loopAvoidance.put(root, root);
            while (!openListStack.isEmpty()) {
                GameBoard curr = openListStack.pop();
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
                            return;
                        }
                        openListStack.add(child);
                        loopAvoidance.put(child, child);
                    }
                }

            }
            root.setInfo(0);
            maxH = minF;

        }
    }

    public void dfbnb(GameBoard root) {
        int size = root.getSize();
        Stack<GameBoard> st = new Stack<>();
        ArrayList<GameBoard> childList = new ArrayList<>();
        Hashtable<GameBoard, GameBoard> loopAvoidance = new Hashtable<>();
        GameBoard result = null;
        int t = Integer.MAX_VALUE;
        root.setChildAfterHeuristic(root.getStateValue() + heuristic(root, size));
        st.add(root);
        loopAvoidance.put(root, root);
        while (!st.isEmpty()) {
            GameBoard curr = st.pop();
            if (curr.getInfo() == 1) {
                loopAvoidance.remove(curr);
            } else {
                curr.setInfo(1); /* Mark as out */
                st.add(curr);  /* Reload the stuck with the following current node as marked out to know the path we at right now  */
                childList = curr.expandMove();/* Return all the possible movements as list */
//                System.out.println("Size: " + childList.size() + " Open List:");
//
//                childList.forEach(GameBoard::printState);
                setChildHeuristicDistances(childList);
                childList.sort(new GameBoard.GameBoardComparator());
                for (int i = 0; i < childList.size(); i++) {
                    GameBoard child = childList.get(i);
                    if (child.getChildAfterHeuristic() >= t) {
                        childList = returnNewChildList(childList, i);
                    } else if (loopAvoidance.containsKey(child) && loopAvoidance.get(child).getInfo() == 1) {
                        childList.remove(child);
                    } else if (loopAvoidance.containsKey(child) && loopAvoidance.get(child).getInfo() == 0) {
                        if (loopAvoidance.get(child).getChildAfterHeuristic() <= child.getChildAfterHeuristic()) {
                            childList.remove(child);
                        } else {
                            st.remove(loopAvoidance.get(child));
                            loopAvoidance.remove(child);
                        }
                    } else if (child.isGoal()) {
                        t = child.getChildAfterHeuristic();
                        result = child;
                        childList = returnNewChildList(childList, i);

                    }
                }

            }
            for (int i = childList.size() - 1; i >= 0; i--) {
                st.add(childList.get(i));
                loopAvoidance.put(childList.get(i), childList.get(i));

            }
        }
        if (result != null) {
            buildAnswer(result);
        }
    }


    private int heuristic(GameBoard child, int size) {
        Hashtable<String, Integer> colorMap = new Hashtable<>();
        fillColor(colorMap, size);
        boolean[][] onGoalValue = new boolean[child.getBoard().length][child.getBoard().length];
        boolean[][] onBoardValue = new boolean[child.getBoard().length][child.getBoard().length];
        String[] colors;
        if (child.getSize() == 3) {
            colors = color3;
        } else {
            colors = color4;
        }
        int sumMoves = 0;
        for (String color : colors) {
            sumMoves += finishThisColorHeuristicValue(child, color, colorMap, onBoardValue, onGoalValue);
        }
        return sumMoves;
    }

    private int finishThisColorHeuristicValue(GameBoard child, String s, Hashtable<String, Integer> colorMap, boolean[][] trueValues, boolean[][] onGoalValue) {
        int min, sum = 0, boardI = 0, boardJ = 0, goalI = 0, goalJ = 0;
        while (colorMap.get(s) > 0) {
            min = Integer.MAX_VALUE;
            for (int i = 0; i < child.getSize(); i++) {
                for (int j = 0; j < child.getSize(); j++) {
                    if (child.getBoard()[i][j].equals(s) && !trueValues[i][j]) {
                        int[] tempMin = searchForClosest(child, i, j, s, trueValues, onGoalValue);
                        if (tempMin[0] < min) {
                            min = tempMin[0];
                            goalI = tempMin[1];
                            goalJ = tempMin[2];
                            boardI = i;
                            boardJ = j;
                        }
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

    private void fillColor(Hashtable<String, Integer> colorMap, int size) {
        int counter = 0;
        if (size == 3)
            while (counter < size) {
                if (counter == 0) {
                    colorMap.put("R", 2);
                } else if (counter == 1) {
                    colorMap.put("B", 2);
                } else {
                    colorMap.put("G", 2);
                }
                counter++;
            }
        else {
            while (counter < 4) {
                if (counter == 0) {
                    colorMap.put("R", 4);
                } else if (counter == 1) {
                    colorMap.put("B", 4);
                } else if (counter == 2) {
                    colorMap.put("G", 4);
                } else {
                    colorMap.put("Y", 4);
                }
                counter++;
            }
        }
    }


    private int[] searchForClosest(GameBoard child, int row, int column, String s, boolean[][] trueValues, boolean[][] onGoalValue) {
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
        while (curr.getParent() != null) {
            path.add(curr);
            num = Long.toString(GameBoard.totalOpen);
            curr = curr.getParent();
        }
        path.add(curr);
        printPathWell2(path);
        cost = Integer.toString(valueAdder);

    }


    private void printPathWell2(Stack<GameBoard> path) {
        int counter = 0;
        GameBoard firstState = null, secondState;

        System.out.println("--------------------");
        System.out.println("---PRINT-THE-PATH---");
        System.out.println("--------------------");
        while (!path.isEmpty() && counter < 2) {
            if (counter == 0) {
                path.peek().printState();
//                System.out.println("-----");
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
                if (!firstState.getBoard()[i][j].equals(secondState.getBoard()[i][j])) {
                    if (firstState.getBoard()[i][j].equals("_")) {
                        if (!secondState.isGoal()) {
                            secondString = secondState.getBoard()[i][j] + ":(" + (i + 1) + "," + (j + 1) + ")--";
                        } else {
                            secondString = secondState.getBoard()[i][j] + ":(" + (i + 1) + "," + (j + 1) + ")";
                        }
                        valueAdder += GameBoard.afterMoveValue(secondState.getBoard()[i][j]);
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

    public int getValueAdder() {
        return valueAdder;
    }


    private ArrayList<GameBoard> returnNewChildList(ArrayList<GameBoard> childList, int i) {
        ArrayList<GameBoard> tempGameBoardList = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            tempGameBoardList.add(childList.get(j));
        }
        return tempGameBoardList;
    }


    private void setChildHeuristicDistances(ArrayList<GameBoard> childList) {
        for (GameBoard child : childList) {
            int size = child.getSize();
            child.setChildAfterHeuristic(child.getStateValue() + heuristic(child, size));
        }
    }
}
