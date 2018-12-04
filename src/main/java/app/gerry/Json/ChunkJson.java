package app.gerry.Json;

public class ChunkJson {

    private int id;
    private int[] adjacents;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getAdjacents() {
        return adjacents;
    }

    public void setAdjacents(int[] adjacents) {
        this.adjacents = adjacents;
    }
}
