package app.gerry.Geography;

import app.gerry.AlgorithmCore.ObjectiveFunction;
import app.gerry.Data.GeometricData;
import app.gerry.Data.Representative;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class District {
    public int id;
    public String name;
    public State state;
    public Representative representative;
    public Set<Precinct> precincts;
    public Set<Chunk> chunks;
    public Set<Chunk> adjacentChunks;
    public Set<Chunk> borderChunks;
    public GeometricData geometricData;
    public double ObjectiveValue;

    /**
     * Construct a district that contains one chunk (Seed district)
     * @param chunk
     */
    public District(Chunk chunk) {
        chunks = new HashSet<>();
        adjacentChunks = new HashSet<>();
        addChunk(chunk);

        //TODO: Add Chunk to chunks and update geometric data, etc...
    }

    public Chunk getRandomBorderChunk(){
        return null;
    }

    public Set<Chunk> getAdjacentChunks(){
        return adjacentChunks;
    }

    public void removeChunk(Chunk c){
        return;
    }

    public void addChunk(Chunk chunk){
        chunk.setParentDistrict(this);
        chunks.add(chunk);
        List newAdjacentChunks = chunk.getAdjacentChunks().stream().filter(c -> c.getParentDistrict() == null).collect(Collectors.toList());
        adjacentChunks.addAll(newAdjacentChunks);
        adjacentChunks.remove(chunk);
    }

    public void removeChunkFromAdjacencies(Set<Chunk> chunk) {
        adjacentChunks.removeAll(chunk);
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

    public State getState(){return state;}
    public void setName(String name) {
        this.name = name;
    }


    public Representative getRepresentative() {
        return representative;
    }

    public void setRepresentative(Representative representative) {
        this.representative = representative;
    }

    public Set<Precinct> getPrecincts() {
        return precincts;
    }

    public void setPrecincts(Set<Precinct> precincts) {
        this.precincts = precincts;
    }

    public Set<Chunk> getChunks() {
        return chunks;
    }

    public void setChunks(Set<Chunk> chunks) {
        this.chunks = chunks;
    }

    public GeometricData getGeometricData() {
        return geometricData;
    }

    public void setGeometricData(GeometricData geometricData) {
        this.geometricData = geometricData;
    }

    public double getObjectiveValue() {
        return ObjectiveValue;
    }

    public void setObjectiveValue(double objectiveValue) {
        ObjectiveValue = objectiveValue;
    }
}
