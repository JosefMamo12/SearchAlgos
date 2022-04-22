import java.io.*;
import java.util.Scanner;
import java.util.Timer;

public class Ex1 {
    public static void main(String[] args) {
        String path = "";
        if (args.length > 0)
            path = args[0];
        else {
            path = "C:\\Users\\yosim\\IdeaProjects\\Ex1\\src\\input2.txt";
        }
        long start = 0, end = 0;
        GameInfo info = new GameInfo(path);
        GameBoard gb = new GameBoard(info);
        Algorithms a = new Algorithms();

        switch (info.getAlgo()) {
            case "BFS":
                start = System.currentTimeMillis();
                a.bfs(gb);
                end = System.currentTimeMillis();
                break;
            case "DFID":
                start = System.currentTimeMillis();
                a.dfid(gb);
                end = System.currentTimeMillis();
                break;
            case ("A*"):
                start = System.currentTimeMillis();
                a.aStar(gb);
                end = System.currentTimeMillis();

                break;
        }
        double elapsedTime = (end - start);
        elapsedTime /= 1000;

        try {
            FileWriter fw = new FileWriter("output.txt");
            fw.write(a.getOutputPath() + "\nNum: " + a.getNum() + "\nCost: " + a.getCost() + "\n" + elapsedTime + " seconds");
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

