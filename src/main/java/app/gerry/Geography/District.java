package app.gerry.Geography;

import app.gerry.Data.GeometricData;
import app.gerry.Data.Representative;
import org.locationtech.jts.geom.Geometry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class District {
    public int id;
    public String name;
    public State state;
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
        geometricData = chunk.getCummGeometricData();
        //TODO: Add Chunk to chunks and update geometric data, etc...
    }

    public Chunk getRandomBorderChunk(){
        return null;
    }

    public Set<Chunk> getAdjacentChunks(){
        return adjacentChunks;
    }

    public void removeChunk(Chunk c){
        chunks.remove(c);
    }

    public void addChunk(Chunk chunk){
        chunk.setParentDistrict(this);
        updateGeometricData(chunk);
        chunks.add(chunk);
        List newAdjacentChunks = chunk.getAdjacentChunks().stream().filter(c -> c.getParentDistrict() == null).collect(Collectors.toList());
        adjacentChunks.addAll(newAdjacentChunks);
        adjacentChunks.remove(chunk);
    }

    private void updateGeometricData(Chunk chunk) {
        Geometry other = chunk.getCummGeometricData().getShape();
        Geometry updatedShape = getGeometricData().getShape().union(other);
        double updatedArea = updatedShape.getArea();
        double updatedPerimeter = updatedShape.getLength();
        Geometry convexHull = updatedShape.convexHull();
        this.geometricData.setArea(updatedArea);
        this.geometricData.setPerimeter(updatedPerimeter);
        this.geometricData.setConvexHull(convexHull);
        this.geometricData.setShape(updatedShape);
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

    public void setName(String name) {
        this.name = name;
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
