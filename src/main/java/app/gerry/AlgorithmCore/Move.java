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
    private String objectiveValue;

    public void setObjectiveValue(String val){
        objectiveValue = val;
    }

    public String getObjectiveValue(){
        return objectiveValue;
    }

    public Move(Chunk chunk, District destDistrict) {
        this.chunk = chunk;
        this.destDistrict = destDistrict;
        this.srcDistrict = chunk.getDistrict();
    }

    public void execute() {
        if(srcDistrict != null) {
            //srcDistrict.testRemoveChunk(chunk);
            srcDistrict.removeChunk(chunk);
        }
        destDistrict.addChunk(chunk);
        chunk.setFinalized(true);
    }

    public void undo() {
        if(srcDistrict != null) {
            srcDistrict.addChunk(chunk);
        }
        destDistrict.removeChunk(chunk);
        chunk.setFinalized(false);
    }

    public District getDestDistrict() {
        return destDistrict;
    }

    public void setDestDistrict(District destDistrict) {
        this.destDistrict = destDistrict;
    }

    public District getSrcDistrict() {
        return srcDistrict;
    }

    public void setSrcDistrict(District srcDistrict) {
        this.srcDistrict = srcDistrict;
    }

    public int getChunkId() {
        return chunkId;
    }

    public void setChunkId(int chunkId) {
        this.chunkId = chunkId;
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
