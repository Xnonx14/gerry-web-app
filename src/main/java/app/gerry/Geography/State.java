package app.gerry.Geography;

import app.gerry.Data.Representative;
import app.gerry.Constants.PoliticalSubdivision;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class State {
    private int id;
    private String name;
    private List<District> districts;
    private List<Chunk> chunks;
    private Map<Integer, Chunk> idChunkMap;
    private Map<Integer, List<Integer>> adjacentChunkIdMap;
    private Set<Representative> representatives;
    private PoliticalSubdivision restriction;
    private int population;

    private State(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.districts = builder.districts;
        this.representatives = builder.representatives;
        this.restriction = builder.restriction;
        this.population = builder.population;
        this.adjacentChunkIdMap = builder.adjacentChunkIdMap;
        this.idChunkMap = builder.idChunkMap;
        this.chunks = builder.chunks;
    }

    public Set<Precinct> getAllPrecincts(){
        // TODO
        return null;

    }
    public Set<String> getSubdivisionNames(){
        // TODO
        return null;
    }
    public District getRandomDistrict(){
        // TODO
        return null;
    }
    public List<District> getSeedDistricts(){
        return districts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Integer, Chunk> getIdChunkMap() {
        return idChunkMap;
    }

    public void setIdChunkMap(Map<Integer, Chunk> idChunkMap) {
        this.idChunkMap = idChunkMap;
    }

    public Map<Integer, List<Integer>> getAdjacentChunkIdMap() {
        return adjacentChunkIdMap;
    }

    public void setAdjacentChunkIdMap(Map<Integer, List<Integer>> adjacentChunkIdMap) {
        this.adjacentChunkIdMap = adjacentChunkIdMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Chunk> getChunks() {
        return chunks;
    }

    public void setChunks(List<Chunk> chunks) {
        this.chunks = chunks;
    }

    public static class Builder {
        private int id;
        private String name;
        private List<District> districts;
        private List<Chunk> chunks;
        private Map<Integer, Chunk> idChunkMap;
        private Map<Integer, List<Integer>> adjacentChunkIdMap;
        private Set<Representative> representatives;
        private PoliticalSubdivision restriction;
        private int population;

        public Builder(String name) {
            this.name = name;
        }

        public Builder withChunks(List<Chunk> chunks) {
            this.chunks = chunks;
            return this;
        }

        public Builder withDistricts(List<District> districts) {
            this.districts = districts;
            return this;
        }

        public Builder withRepresentatives(Set<Representative> representatives) {
            this.representatives = representatives;
            return this;
        }

        public Builder withRestriction(PoliticalSubdivision restriction) {
            this.restriction = restriction;
            return this;
        }

        public Builder withPopulation(int population) {
            this.population = population;
            return this;
        }

        public Builder withAdjacentChunkMap(Map<Integer, List<Integer>> adjacentChunkIdMap) {
            this.adjacentChunkIdMap = adjacentChunkIdMap;
            return this;
        }

        public Builder withIdChunkMap(Map<Integer, Chunk> idChunkMap) {
            this.idChunkMap = idChunkMap;
            return this;
        }

        public State build() {
            return new State(this);
        }
    }
}
