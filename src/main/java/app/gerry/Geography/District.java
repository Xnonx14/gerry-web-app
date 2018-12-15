package app.gerry.Geography;

import app.gerry.AlgorithmCore.ObjectiveFunction;
import app.gerry.Data.GeometricData;
import app.gerry.Data.Representative;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.operation.union.UnaryUnionOp;

import java.util.*;
import java.util.stream.Collectors;

public class District {
    private int id;
    private String name;
    private Representative representative;
    private Set<Precinct> precincts;
    private Set<Chunk> chunks;
    private Set<Chunk> adjacentChunks;
    private Set<Chunk> borderChunks;
    private Geometry geometricData;
    private int population;
    private double ObjectiveValue;
    private State state;

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

    /**
     * Everytime we add a chunk update the district's:
     *  - adjacent chunks
     *  - boundary data
     *  - election data
     *  - population data
     * @param chunk
     */
    public void addChunk(Chunk chunk){
        updateAdjacentChunks(chunk);
        updateBoundaryData(chunk);
        updateElectionData(chunk);
        updatePopulationData(chunk);
    }

    private void updateAdjacentChunks(Chunk chunk) {
        chunk.setParentDistrict(this);
        chunks.add(chunk);
        List newAdjacentChunks = chunk.getAdjacentChunks().stream().filter(c -> c.getParentDistrict() == null).collect(Collectors.toList());
        adjacentChunks.addAll(newAdjacentChunks);
        adjacentChunks.remove(chunk);
    }

    private void updateBoundaryData(Chunk chunk) {
        //If this is the first time we're setting the boundary data
        if(geometricData == null) {
            geometricData = chunk.getCummGeometricData();
            return;
        }
        //Otherwise we have to incrementally adjust our boundary data with each chunk
        Geometry districtPolygon = this.geometricData;
        Geometry chunkPolygon = chunk.getCummGeometricData();
        Collection<Geometry> polygons = new ArrayList<>();
        polygons.add(districtPolygon);
        polygons.add(chunkPolygon);
        this.geometricData = new UnaryUnionOp(polygons).union();
    }

    private void updateElectionData(Chunk chunk) {
        //TODO
    }

    private void updatePopulationData(Chunk chunk) {
        this.population += chunk.getCummPopulation();
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setAdjacentChunks(Set<Chunk> adjacentChunks) {
        this.adjacentChunks = adjacentChunks;
    }

    public Set<Chunk> getBorderChunks() {
        return borderChunks;
    }

    public void setBorderChunks(Set<Chunk> borderChunks) {
        this.borderChunks = borderChunks;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
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

    public Geometry getGeometricData() {
        return geometricData;
    }

    public void setGeometricData(Geometry geometricData) {
        this.geometricData = geometricData;
    }

    public double getObjectiveValue() {
        return ObjectiveValue;
    }

    public void setObjectiveValue(double objectiveValue) {
        ObjectiveValue = objectiveValue;
    }
}
