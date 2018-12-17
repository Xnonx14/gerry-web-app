package app.gerry.Geography;

import app.gerry.AlgorithmCore.ObjectiveFunction;
import app.gerry.Constants.Party;
import app.gerry.Data.ElectionData;
import app.gerry.Data.GeometricData;
import app.gerry.Data.Representative;
import app.model.PartyRepresentative;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.operation.union.UnaryUnionOp;
import org.springframework.security.core.parameters.P;

import java.util.*;
import java.util.stream.Collectors;

public class District {
    private int id;
    private String name;
    private Representative representative;
    private Set<Precinct> precincts;
    private Set<Chunk> chunks;
    private Map<Chunk, Integer> adjacentChunks;
    private Set<Chunk> borderChunks;
    private Geometry geometricData;
    private Map<Party, Integer> cummWastedVotes;
    private Integer totalVotes;
    private int population;
    private double ObjectiveValue;
    private State state;

    public static class Builder {
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
        private Integer totalVotes = 0;
        private Map<Party, Integer> cummWastedVotes = new HashMap<>();

        public Builder() {

        }

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public District build() {
            return new District(this);
        }
    }
    
    public District(Builder builder) {
        this.id = builder.id;
        this.cummWastedVotes = builder.cummWastedVotes;
        this.totalVotes = builder.totalVotes;
    }
    
    public District(Chunk chunk) {
        chunks = new HashSet<>();
        adjacentChunks = new HashMap<>();
        cummWastedVotes = new HashMap<>();
        totalVotes = 0;
        addChunk(chunk);


        //TODO: Add Chunk to chunks and update geometric data, etc...
    }

    public Chunk getRandomBorderChunk(){
        int index = (int)(Math.random()*borderChunks.size());
        return (Chunk)(borderChunks.toArray())[index];
    }

    public Set<Chunk> getAdjacentChunks(){
        return adjacentChunks.keySet();
    }

    /**
     * Everytime we add a chunk update the district's:
     *  - adjacent chunks
     *  - boundary data
     *  - election data
     *  - population data
     * @param chunk
     */
    public Map<Party, Integer> getCummWastedVotes(){
        return cummWastedVotes;
    }

    public int getTotalVotes(){
        return totalVotes;
    }

    public void testRemoveChunk(Chunk chunk){
        //update adjacencies
        District parentDist = chunk.getParentDistrict();
        Set<Chunk> firstBorder = chunk.getAdjacentChunks();
        chunk.setParentDistrict(null);
        chunks.remove(chunk);
        //firstBorder are the only ones that may need to be removed from the adjacency list
        for(Chunk ch: firstBorder){
            Set<Chunk> secondBorder = ch.getAdjacentChunks();
            boolean removeCH = true;
            for(Chunk temp: secondBorder){
                if(temp.getParentDistrict() == parentDist){
                    removeCH = false;
                }
            }
            if(ch.getParentDistrict() == parentDist){
                removeCH = false;
            }
            //if secondBorder and ch are ALL not part of original srcDistrict, remove ch from adjacency list
            if(removeCH && this.adjacentChunks.containsKey(ch)){
                adjacentChunks.remove(ch);
            }
        }
        updateBoundaryData(chunk, true);
        updateWastedVotes(chunk, true);
        updatePopulationData(chunk, true);
    }

    public void addChunk(Chunk chunk){
        updateAdjacentChunks(chunk, false);
        updateBoundaryData(chunk, false);
        updateWastedVotes(chunk, false);
        updateTotalVotes(chunk, false);
        updatePopulationData(chunk, false);
        chunks.add(chunk);
        chunk.setParentDistrict(this);
    }


    public void removeChunk(Chunk chunk) {
        updateAdjacentChunks(chunk, true);
        updateBoundaryData(chunk, true);
        updateWastedVotes(chunk, true);
        updatePopulationData(chunk, true);
        chunk.setParentDistrict(null);
        chunks.remove(chunk);
    }

    private void updateTotalVotes(Chunk chunk, boolean isRemove){
        if (isRemove){
            totalVotes -= chunk.getTotalVotes();
        }
        else{
            totalVotes += chunk.getTotalVotes();
        }
    }

    private void updateAdjacentChunks(Chunk chunk, boolean isRemove) {
        if(chunk.getAdjacentChunks() == null){
            return;
        }
        List<Chunk> newAdjacentChunks = chunk.
                getAdjacentChunks().
                stream()
                .filter(c -> c.getParentDistrict() == null ? true : c.getParentDistrict().getId() != this.getId())
                .collect(Collectors.toList());
        if(isRemove){
            for(Chunk temp: newAdjacentChunks){
                System.out.print(temp.getId() + ",");
            }
            System.out.println();
            for(Chunk c : newAdjacentChunks) {
                if(adjacentChunks.get(c) == null){
                    continue;
                }
                int oldCount = adjacentChunks.get(c);
                adjacentChunks.put(c, oldCount - 1);
                if(oldCount == 1) {
                    adjacentChunks.remove(c);
                }
            }
            List<Chunk> oldAdjacentChunks = chunk.getAdjacentChunks().stream()
                    .filter(c -> c.getParentDistrict() != null && c.getParentDistrict().getId() == this.getId())
                    .collect(Collectors.toList());
            adjacentChunks.put(chunk, oldAdjacentChunks.size());
        }
        else{
            for(Chunk c : newAdjacentChunks) {
                int oldCount = adjacentChunks.getOrDefault(c, 0);
                adjacentChunks.put(c, oldCount + 1);
            }
            adjacentChunks.remove(chunk);
        }

    }

    private void updateBoundaryData(Chunk chunk, boolean isRemove) {
        //If this is the first time we're setting the boundary data
        if(geometricData == null) {
            if(!isRemove)
                geometricData = chunk.getCummGeometricData();
            else
                System.out.println("Can't remove this district");
            return;
        }
        //Otherwise we have to incrementally adjust our boundary data with each chunk
        Geometry districtPolygon = this.geometricData;
        Geometry chunkPolygon = chunk.getCummGeometricData();
        if(chunkPolygon == null){
            System.out.println("this polygon is null " + chunk.getId());
        }
        Collection<Geometry> polygons = new ArrayList<>();
        polygons.add(districtPolygon);
        polygons.add(chunkPolygon);

        if(isRemove) {
            this.geometricData = districtPolygon.difference(chunkPolygon);
            return;
        }
        try{
            this.geometricData = new UnaryUnionOp(polygons).union();
        }
        catch(Exception e){
        }
    }

    private void updateWastedVotes(Chunk chunk, boolean isRemove) {
        Map<Party, Integer> chunkCummWastedVotes = chunk.getCummWastedVotes();

        for (Party p : chunkCummWastedVotes.keySet()) {
            int chunkWastedVotes = chunkCummWastedVotes.get(p);
            int total = 0;
            if (cummWastedVotes.containsKey(p)) {
                if (isRemove) {
                    total = cummWastedVotes.get(p) - chunkWastedVotes;
                } else {
                    total = cummWastedVotes.get(p) + chunkWastedVotes;
                }
            } else {
                if (!isRemove) {
                    total = chunkWastedVotes;
                }
            }
            cummWastedVotes.put(p, total);
        }
    }

    private void updatePopulationData(Chunk chunk, boolean isRemove) {
        if(isRemove) {
            this.population -= chunk.getCummPopulation();
            return;
        }
        this.population += chunk.getCummPopulation();
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

    public void setAdjacentChunks(Map<Chunk, Integer> adjacentChunks) {
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
