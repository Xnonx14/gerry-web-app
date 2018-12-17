package app.gerry.Json;

public class CountyChunkJson {
    private int id;
    private int[] precincts;
    private int[] adjCounties;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getPrecincts() {
        return precincts;
    }

    public void setPrecincts(int[] precincts) {
        this.precincts = precincts;
    }

    public int[] getAdjCounties() {
        return adjCounties;
    }

    public void setAdjCounties(int[] adjCounties) {
        this.adjCounties = adjCounties;
    }
}
