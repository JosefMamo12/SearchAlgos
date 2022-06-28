public class GameManager {

    private final char[][] gameMat;
    private final char[][] initState;
    private final char[][] goalState;

    private final String info;
    private int score;
    private boolean run;
    private int velocity;
    private int threshold;
    static int currentPosition = 5;
    final Object lock;


    public GameManager(char[][] mat, String info, char[][] goal) {
        this.initState = new char[mat.length][mat.length];
        this.goalState = new char[mat.length][mat.length];
        for (int i = 0; i < mat.length; i++) {
            System.arraycopy(goal[i], 0, goalState[i], 0, mat.length);
        }
        for (int i = 0; i < mat.length; i++) {
            System.arraycopy(mat[i], 0, initState[i], 0, mat.length);
        }
        gameMat = new char[mat.length][mat.length];
        this.info = info;
        lock = new Object();
        run = false;
        velocity = 10;

    }
    public String getInfo(){
        return this.info;
    }


    public char[][] getGoalState() {
        return goalState;
    }

    public char[][] getGameMat() {
        return gameMat;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getThreshold() {
        return this.threshold;
    }

    public int getScore() {
        return score;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }


    public void setVelocityDown() {
        synchronized (lock) {
            System.out.println(currentPosition);
            if (currentPosition > 0 && currentPosition <= 10) {
                switch (currentPosition) {
                    case 1:
                        setVelocity(100);
                        currentPosition = 2;
                        break;
                    case 2:
                        setVelocity(200);
                        currentPosition = 3;
                        break;
                    case 3:
                        setVelocity(300);
                        currentPosition = 4;
                        break;
                    case 4:
                        setVelocity(400);
                        currentPosition = 5;
                        break;
                    case 5:
                        setVelocity(500);
                        currentPosition = 6;
                        break;
                    case 6:
                        setVelocity(600);
                        currentPosition = 7;
                        break;
                    case 7:
                        setVelocity(700);
                        currentPosition = 8;
                        break;
                    case 8:
                        setVelocity(800);
                        currentPosition = 9;
                        break;
                    case 9:
                        setVelocity(900);
                        currentPosition = 10;
                        break;
                    case 10:
                        setVelocity(1000);
                        break;
                    default:
                        setVelocity(50);
                }
            }
        }
    }

    public void setVelocityUp() {
        synchronized (lock) {
            if (currentPosition > 0 && currentPosition <= 10) {
                switch (currentPosition) {
                    case 1:
                        setVelocity(100);
                        break;
                    case 2:
                        setVelocity(200);
                        currentPosition = 1;
                        break;
                    case 3:
                        setVelocity(300);
                        currentPosition = 2;
                        break;
                    case 4:
                        setVelocity(400);
                        currentPosition = 3;
                        break;
                    case 5:
                        setVelocity(500);
                        currentPosition = 4;
                        break;
                    case 6:
                        setVelocity(600);
                        currentPosition = 5;
                        break;
                    case 7:
                        setVelocity(700);
                        currentPosition = 6;
                        break;
                    case 8:
                        setVelocity(800);
                        currentPosition = 7;
                        break;
                    case 9:
                        setVelocity(900);
                        currentPosition = 8;
                        break;
                    case 10:
                        setVelocity(1000);
                        currentPosition = 9;
                        break;
                    default:
                        setVelocity(50);
                }
            }
        }
    }


    public void run() {
        run = true;
    }

    public void stop() {
        run = false;
    }

    public boolean running() {
        return run;
    }


    public char[][] getInitState() {
        return initState;
    }

    public void update(char[][] mat, int stateValue) {
        score = stateValue;
        for (int i = 0; i < gameMat.length; i++) {
            System.arraycopy(mat[i], 0, gameMat[i], 0, gameMat.length);
        }
    }
}
