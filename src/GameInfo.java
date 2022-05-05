import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
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
        int insider = 0;
        int rowCounter = 0;
        Scanner sc;
        try {
            sc = new Scanner(f);
            while (index < 3) {
                if (index == 0) {
                    algo = sc.nextLine();
                    index++;
                } else if (index == 1) {
                    noOpen = !"no open".equals(sc.nextLine());
                    index++;
                } else if (index == 2) {
                    sizeOfBoard = sc.nextLine();
                    if (sizeOfBoard.equals("small")) {
//                        init = new char[3][3];
//                        goal = new char[3][3];
                        init = new String[3][3];
                        goal = new String[3][3];
                        while (insider < 7) {
                            while (insider <= 2) {
                                init[rowCounter] = sc.nextLine().split(",");
//                                String[] sr = sc.nextLine().split(",");
//                                for (int i = 0; i < sr.length; i++) {
//                                    init[rowCounter][i] = sr[i].charAt(0);
//                                }
                                rowCounter++;
                                insider++;
                            }
                            if (insider == 3) {
                                rowCounter = 0;
                                sc.nextLine();
                                insider++;
                            }
                            while (insider >= 4 && insider <= 6) {
                                goal[rowCounter] = sc.nextLine().split(",");
//                                String[] sr = sc.nextLine().split(",");
//                                for (int i = 0; i < sr.length; i++) {
//                                    goal[rowCounter][i] = sr[i].charAt(0);
//                                }
                                rowCounter++;
                                insider++;
                            }
                        }
                    } else {
                        init = new String[5][5];
                        goal = new String[5][5];
                        while (insider < 11) {
                            while (insider <= 4) {
                                init[rowCounter] = sc.nextLine().split(",");
//                                String[] sr = sc.nextLine().split(",");
//                                for (int i = 0; i < sr.length; i++) {
//                                    init[rowCounter][i] = sr[i].charAt(0);
//                                }
                                rowCounter++;
                                insider++;
                            }
                            if (insider == 5) {
                                rowCounter = 0;
                                sc.nextLine();
                                insider++;
                            }
                            while (insider >= 6 && insider <= 10) {
                                goal[rowCounter] = sc.nextLine().split(",");
//                                String[] sr = sc.nextLine().split(",");
//                                for (int i = 0; i < sr.length; i++) {
//                                    goal[rowCounter][i] = sr[i].charAt(0);
//                                }
                                rowCounter++;
                                insider++;
                            }
                        }

                    }
                    index++;
                }

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
