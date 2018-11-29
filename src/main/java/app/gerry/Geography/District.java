package app.gerry.Geography;

import app.gerry.Data.GeometricData;
import app.gerry.Data.Representative;

import java.util.Set;

public class District {
    public int id;
    public String name;
    public Representative representative;
    public Set<Precinct> precients;
    public Set<Chunk> chunks;
    public GeometricData geometricData;
    public double ObjectiveValue;

    public Chunk getRandomBorderChunks(){
        return null;
    }

    public Set<Chunk> getAdjacentChunks(){
        return null;
    }

    public void removeChunk(Chunk c){
        return;
    }

    public void addChunk(Chunk c){
        return;
    }

    public Precinct getRandomBordering(){
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
