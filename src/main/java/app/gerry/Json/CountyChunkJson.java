package app.gerry.Json;

public class CountyChunkJson {
    private int id;
    private int[] precincts;

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
}
