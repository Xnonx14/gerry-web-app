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
    private double averageDistrictPopulation;

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

        //set district state to this state
        for(District district : districts) {
            district.setState(this);
        }

        //calculate average population
        averageDistrictPopulation = (double) population / districts.size();
    }

    public District getRandomDistrict(){
        int index = (int)(districts.size() * Math.random());
        return districts.get(index);
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

    public List<District> getDistricts(){
        return districts;
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

    public int getPopulation() {
        return population;
    }

    public double getAverageDistrictPopulation() {
        return averageDistrictPopulation;
    }


    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public Set<Representative> getRepresentatives() {
        return representatives;
    }

    public void setRepresentatives(Set<Representative> representatives) {
        this.representatives = representatives;
    }

    public PoliticalSubdivision getRestriction() {
        return restriction;
    }

    public void setRestriction(PoliticalSubdivision restriction) {
        this.restriction = restriction;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setAverageDistrictPopulation(double averageDistrictPopulation) {
        this.averageDistrictPopulation = averageDistrictPopulation;
    }
}
