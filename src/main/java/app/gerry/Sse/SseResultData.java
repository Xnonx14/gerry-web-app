package app.gerry.Sse;

import app.gerry.AlgorithmCore.Move;

import java.util.Date;

public class SseResultData {
    private int srcDistrictId;
    private int destDistrictId;
    private int precinctId;
    private int population;
    private String data;
    private Date dateSent;
    private boolean isLastOne;

    public SseResultData(Move move, boolean isLastOne) {
        srcDistrictId = move.getSrcDistrictId();
        destDistrictId = move.getDestDistrictId();
        precinctId = move.getChunk().getId();
        population = move.getChunkPopulation();
        dateSent = new Date();
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

    public int getPrecinctId() {
        return precinctId;
    }

    public void setPrecinctId(int precinctId) {
        this.precinctId = precinctId;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    public boolean isLastOne() {
        return isLastOne;
    }

    public void setLastOne(boolean lastOne) {
        isLastOne = lastOne;
    }
}
