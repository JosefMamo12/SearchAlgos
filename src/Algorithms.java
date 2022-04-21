import java.util.*;
/**
  Main class for all the algorithms
    1)BFS
    2)DFID
    3)A*
    4)IDA*
    5)DFBnB

 */
public class Algorithms {
    String outputPath = "";
    String num = "";
    String cost = "inf";
    int valueAdder = 0;

    public List<GameBoard> bfs(GameBoard root) {
        Stack<GameBoard> path = new Stack<>();
        Queue<GameBoard> openList = new LinkedList<>();
        Hashtable<GameBoard, GameBoard> openListHash = new Hashtable<>();
        Hashtable<GameBoard, GameBoard> closedList = new Hashtable<>();

        openList.add(root);
        openListHash.put(root, root);
        boolean isSolution = false;
        while (!openList.isEmpty() && !isSolution) {
            GameBoard currBoard = openList.poll();
            closedList.put(currBoard, currBoard);
            currBoard.expandMove();

            for (GameBoard child : currBoard.getChildren()) {
                if (child.isGoal()) {
                    while (child.getParent() != null) {
                        path.add(child);
                        num = Integer.toString(GameBoard.totalOpen);
                        child = child.getParent();
                    }
                    path.add(child);
                    printPathWell2(path);
                    cost = Integer.toString(valueAdder);
                    return path;
                }
                if (!openListHash.containsKey(child) && !closedList.containsKey(child)) {
                    openList.add(child);
                    openListHash.put(child, child);
//                    child.printState();
//                    System.out.println("-----");
                }

            }


//            for (GameBoard child:openList) {
//                child.printState();
//                System.out.println("-----");
//            }
//            System.out.println();
//            System.out.println("Next Layer");
        }
        if (!isSolution) {
            outputPath += "no path";
            num = Integer.toString(GameBoard.totalOpen);
        }
        return null;
    }

//    private void printPathWell(Stack<GameBoard> path) {
//        while (!path.isEmpty()) {
//            if (!path.peek().getStatePath().equals("") && path.size() != 1) {
//                outputPath += path.pop().getStatePath() + "--";
//            } else {
//                outputPath += path.pop().getStatePath();
//            }
//        }
//        System.out.println();
//    }

    private void printPathWell2(Stack<GameBoard> path) {
        int counter = 0;
        GameBoard firstState = null;
        GameBoard secondState = null;

        while (!path.isEmpty() && counter < 2) {
            if (counter == 0) {
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

}
