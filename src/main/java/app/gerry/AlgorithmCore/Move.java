package app.gerry.AlgorithmCore;

import app.gerry.Geography.Chunk;
import app.gerry.Geography.District;

public class Move {

    private Chunk chunk;
    private District district;
    private int chunkId;
    private int srcDistrictId;
    private int destDistrictId;
    private int chunkPopulation;

    public Move(Chunk chunk, District district) {
        this.chunk = chunk;
        this.district = district;
    }

    public void execute() {

    }

    public void undo() {

    }

    public int getChunkId() {
        return chunkId;
    }

    public void setChunkId(int chunkId) {
        this.chunkId = chunkId;
    }

    public int getSrcDistrictId() {
        return srcDistrictId;
    }

    public void setSrcDistrictId(int srcDistrictId) {
        this.srcDistrictId = srcDistrictId;
    }

    public int getDestDistrictId() {
        return destDistrictId;
    }

    public void setDestDistrictId(int destDistrictId) {
        this.destDistrictId = destDistrictId;
    }

    public int getChunkPopulation() {
        return chunkPopulation;
    }

    public void setChunkPopulation(int chunkPopulation) {
        this.chunkPopulation = chunkPopulation;
    }
}
