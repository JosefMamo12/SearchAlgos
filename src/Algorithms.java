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
    Stack<GameBoard> path = new Stack<>();

    public List<GameBoard> bfs(GameBoard root) {
        isOpen = root.isOpen();
        Queue<GameBoard> openList = new LinkedList<>();
        HashSet<GameBoard> openListHash = new HashSet<>();
        HashSet<GameBoard> closedList = new HashSet<>();
        openList.add(root);
        openListHash.add(root);
        boolean isSolution = false;
        while (!openList.isEmpty() && !isSolution) {
            GameBoard currBoard = openList.poll();
            closedList.add(currBoard);
            currBoard.expandMove();
            for (GameBoard child : currBoard.getChildren()) {
                if (!openListHash.contains(child) && !closedList.contains(child)) {
                    openList.add(child);
                    openListHash.add(child);
                }
                if (child.isGoal()) {
                    buildAnswer(child);
                    return path;
                }


            }

            if (isOpen) {
                for (GameBoard entry : openListHash) {
                    entry.printState();
                    System.out.println("-----");
                }
                System.out.println();
                System.out.println("Next Layer");
            }
        }
        if (!isSolution) {
            outputPath += "no path";
            num = Integer.toString(GameBoard.totalOpen);
        }
        return null;
    }

    public void dfid(GameBoard root) {
        for (int depth = 1; depth < Integer.MAX_VALUE; depth++) {
            System.out.println(depth);
            HashSet<GameBoard> loopAvoidance = new HashSet<>();
            GameBoard result = limitedDFS(root, depth, loopAvoidance);
            if (result.getInfo() != 1) {
                return;
            }
        }
        System.out.println("No path");
    }

    /**
     * Recursivly function thats combine between dfs to a bfs to avoid the problem of the space thats exists in bfs when
     * we not save all the states just the states that we work on.
     *
     * @param curr
     * @param depth
     * @param loopAvoidance
     * @return
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
            curr.expandMove();
            for (GameBoard child : curr.getChildren()) {
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
                cutoff.setInfo(2);
            }
            return cutoff;

        }
    }

    public void aStar(GameBoard root) {
        isOpen = root.isOpen();
        PriorityQueue<GameBoard> openList = new PriorityQueue<>(new GameBoard.GameBoardComparator());
        Hashtable<GameBoard,GameBoard> openListHash = new Hashtable<>();
        HashSet<GameBoard> closedList = new HashSet<>();
        openList.add(root);
        openListHash.put(root,root);
        while (!openList.isEmpty()) {
            GameBoard curr = openList.poll();
            if (curr.isGoal()) {
                buildAnswer(curr);
                return;
            }
            closedList.add(curr);
            curr.expandMove();
            for (GameBoard child : curr.getChildren()) {
                if (!openList.contains(child) && !closedList.contains(child)) {
                    openList.add(child);
                    openListHash.put(child,child);
                } else if (openList.contains(child) && openListHash.containsKey(child) && openListHash.get(child).getStateValue() > child.getStateValue()) {
                    openList.remove(openListHash.get(child));
                    openList.add(child);
                }
            }

        }
        if (isOpen) {
            for (Map.Entry<GameBoard,GameBoard> entry : openListHash.entrySet()) {
                entry.getKey().printState();
                System.out.println("-----");
            }
            System.out.println();
            System.out.println("Next Layer");
        }


        outputPath += "no path";
        num = Integer.toString(GameBoard.totalOpen);

    }


    private void buildAnswer(GameBoard curr) {
        while (curr.getParent() != null) {
            path.add(curr);
            num = Integer.toString(GameBoard.totalOpen);
            curr = curr.getParent();
        }
        path.add(curr);
        printPathWell2(path);
        cost = Integer.toString(valueAdder);
    }


    private void printPathWell2(Stack<GameBoard> path) {
        int counter = 0;
        GameBoard firstState = null, secondState;

        System.out.println("-------------------");
        while (!path.isEmpty() && counter < 2) {
            if (counter == 0) {
                path.peek().printState();
                System.out.println("-----");
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
                if (!firstState.getBoard()[i][j].getColor().equals(secondState.getBoard()[i][j].getColor())) {
                    if (firstState.getBoard()[i][j].getColor().equals("_")) {
                        if (!secondState.isGoal()) {
                            secondString = secondState.getBoard()[i][j].getColor() + ":(" + (i + 1) + "," + (j + 1) + ")--";
                        } else {
                            secondString = secondState.getBoard()[i][j].getColor() + ":(" + (i + 1) + "," + (j + 1) + ")";
                        }
                        valueAdder += secondState.getBoard()[i][j].getValue();
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
}
