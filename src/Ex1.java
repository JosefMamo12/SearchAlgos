import java.io.*;
import java.util.Scanner;
import java.util.Timer;

public class Ex1 {
    public static void main(String[] args) {
//        System.out.println(args.length);
//        System.out.println(args[0]);
        GameBoard gb = new GameBoard("C:\\Users\\yosim\\IdeaProjects\\Ex1\\src\\input2.txt");
        Algorithms a = new Algorithms();
        long start = System.currentTimeMillis();
        a.bfs(gb);
        long end = System.currentTimeMillis();
        double elapsedTime = (end - start);
        elapsedTime /= 1000;

            try {
                FileWriter fw = new FileWriter("output.txt");
                fw.write(a.outputPath + "\nNum: " + a.num + "\nCost: " + a.cost + "\n" + elapsedTime + " seconds");
                fw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}

