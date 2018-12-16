package app.gerry.AlgorithmCore;

import app.gerry.Geography.Chunk;
import app.gerry.Geography.District;

public class Move {

    private Chunk chunk;
    private District destDistrict;
    private District srcDistrict;
    private int chunkId;
    private int srcDistrictId;
    private int destDistrictId;
    private int chunkPopulation;

    public Move(Chunk chunk, District destDistrict) {
        this.chunk = chunk;
        this.destDistrict = destDistrict;
        this.srcDistrict = chunk.getDistrict();
    }

    public void execute() {
        if(srcDistrict != null) {
            srcDistrict.removeChunk(chunk);
        }
        destDistrict.addChunk(chunk);
    }

    public void undo() {
        if(srcDistrict != null) {
            srcDistrict.addChunk(chunk);
        }
        destDistrict.removeChunk(chunk);
    }

    public Chunk getChunk() {
        return chunk;
    }

    public void setChunk(Chunk chunk) {
        this.chunk = chunk;
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
