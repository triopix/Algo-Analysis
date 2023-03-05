package main.util;

public class SinglePoint implements Comparable<SinglePoint> {

    private long x;

    public SinglePoint(long x) {
        this.x = x;
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "SinglePoint{" +
                "x=" + x +
                '}';
    }

    @Override
    public int compareTo(SinglePoint o) {
        return (int) (this.x - o.x);
    }
}
