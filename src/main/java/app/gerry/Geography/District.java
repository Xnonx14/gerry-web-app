package app.gerry.Geography;

import app.gerry.Data.GeometricData;
import app.gerry.Data.Representative;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class District {
    public int id;
    public String name;
    public Representative representative;
    public Set<Precinct> precincts;
    public List<Chunk> chunks;
    public GeometricData geometricData;
    public double ObjectiveValue;

    /**
     * Construct a district that contains one chunk (Seed district)
     * @param chunk
     */
    public District(Chunk chunk) {
        chunks = new ArrayList<>();
        chunks.add(chunk);
        chunk.setParentDistrict(this);

        //TODO: Add Chunk to chunks and update geometric data, etc...
    }

    public Chunk getRandomBorderChunk(){
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

    public List<Chunk> getChunks() {
        return chunks;
    }

    public void setChunks(List<Chunk> chunks) {
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
