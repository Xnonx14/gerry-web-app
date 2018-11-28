package app.model;

/*
    This is a test class for SSE emitters.
 */
public class Algorithm {

    private int count;

    public Algorithm() {
        count = 0;
    }

    public void step() {
        count++;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
