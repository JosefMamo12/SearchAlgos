/**
 * Main project to run all the project by using that we get from the GameInfo class.
 */
public class Ex1 implements Runnable {
    long start = 0, end = 0;
    GameInfo info;
    GameBoard gb;
    Algorithms a;
    MyFrame frame;


    private void init() {
        info = new GameInfo("src/input2.txt");
        gb = new GameBoard(info);
        GameManager gm = new GameManager(gb.getBoard(), info.getAlgo(), GameBoard.goal);
        frame = new MyFrame(gm);
        a = new Algorithms(frame, gb, gm);
    }


    public static void main(String[] args) {
        Thread ex1 = new Thread(new Ex1());
        ex1.start();
    }


    @Override
    public void run() {
            new LaunchWindow();
//        init();
//        switch (info.getAlgo()) {
//            case "BFS":
//                start = System.currentTimeMillis();
//                a.bfs(gb);
//                end = System.currentTimeMillis();
//                break;
//            case "DFID":
//                start = System.currentTimeMillis();
//                a.dfid(gb);
//                end = System.currentTimeMillis();
//                break;
//            case ("A*"):
//                start = System.currentTimeMillis();
//                a.aStar(gb);
//                end = System.currentTimeMillis();
//                break;
//            case ("IDA*"):
//                start = System.currentTimeMillis();
//                a.idaStar(gb);
//                end = System.currentTimeMillis();
//                break;
//            case ("DFBnB"):
//                start = System.currentTimeMillis();
//                a.dfbnb(gb);
//                end = System.currentTimeMillis();
//                break;
//        }
//        double elapsedTime = (end - start);
//        elapsedTime /= 1000;
//
//        try {
//            FileWriter fw = new FileWriter("output.txt");
//            fw.write(a.getOutputPath() + "\nNum: " + a.getNum() + "\nCost: " + a.getCost() + "\n" + elapsedTime + " seconds");
//            fw.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        }
    }




