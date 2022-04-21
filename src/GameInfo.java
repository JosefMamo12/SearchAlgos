import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class represent all the info that we get from the
 * input.txt file and convert it into mine objects.
 */
public class GameInfo {

    private String sizeOfBoard;
    private String algo;
    private boolean noOpen;
    private String[][] init;
    private String[][] goal;

    public GameInfo(String path) {
        File f = new File(path);
        int index = 0;
        int rowCounter = 0;
        Scanner sc;
        try {
            sc = new Scanner(f);
            while (index < 10) {
                if (index == 0) {
                    algo = sc.nextLine();
                } else if (index == 1) {
                    noOpen = !"no open".equals(sc.nextLine());
                } else if (index == 2) {
                    sizeOfBoard = sc.nextLine();
                    if (sizeOfBoard.equals("small")) {
                        init = new String[3][3];
                        goal = new String[3][3];
                    } else {
                        init = new String[5][5];
                        goal = new String[5][5];
                    }
                } else if (init != null && index >= 3 && index <= 5) {
                    init[rowCounter] = sc.nextLine().split(",");
                    rowCounter++;
                } else if (index == 6) {
                    rowCounter = 0;
                    sc.nextLine();
                }
                else if (goal != null && index >= 7) {
                    goal[rowCounter] = sc.nextLine().split(",");
                    rowCounter++;
                }
                index++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getSizeOfBoard() {
        return sizeOfBoard;
    }

    public String getAlgo() {
        return algo;
    }

    public boolean isNoOpen() {
        return noOpen;
    }

    public String[][] getInit() {
        return init;
    }

    public String[][] getGoal() {
        return goal;
    }
}
