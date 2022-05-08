import java.util.Objects;

public class Pair {
    private final int i;
    private final int j;

    public int getI() {
        return i;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return i == pair.i && j == pair.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }

    public int getJ() {
        return j;
    }


    public Pair(int i, int j) {
        this.i = i;
        this.j = j;
    }
}
