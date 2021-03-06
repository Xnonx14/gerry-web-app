package app.gerry.Sse;

import app.gerry.AlgorithmCore.Move;
import app.gerry.Constants.Party;
import app.gerry.Geography.District;

import java.util.Date;
import java.util.Map;

public class SseResultData {
    private int srcDistrictId;
    private int destDistrictId;
    private int precinctId;
    private int[] precinctIds;
    private int srcDistrictPopulation;
    private int destDistrictPopulation;
    private String data;
    private Date dateSent;
    private boolean isLastOne;
    private String objectiveValue;
    private String objectiveGain;
    private Map<Party, Integer> precinctWastedVotes;
    private Map<Party, Integer> destDistrictWastedVotes;

    public SseResultData(Move move, boolean isLastOne) {
        District srcDistrict = move.getSrcDistrict();
        District destDistrict = move.getDestDistrict();
        srcDistrictId = srcDistrict == null ? -1 : srcDistrict.getId();
        srcDistrictPopulation = srcDistrict == null ? -1 : srcDistrict.getPopulation();
        destDistrictId = destDistrict == null ? -1 : destDistrict.getId();
        destDistrictPopulation = destDistrict == null ? -1 :  destDistrict.getPopulation();
        precinctId = move.getChunk().getId();
        precinctIds = move.getChunk().getPrecincts().stream().mapToInt(p -> p.getId()).toArray();
        dateSent = new Date();
        objectiveValue = move.getObjectiveValue();
        objectiveGain = move.getObjectiveGain();
        precinctWastedVotes = move.getChunk().getCummWastedVotes();
        destDistrictWastedVotes = destDistrict.getCummWastedVotes();
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

    public int getSrcDistrictPopulation() {
        return srcDistrictPopulation;
    }

    public void setSrcDistrictPopulation(int srcDistrictPopulation) {
        this.srcDistrictPopulation = srcDistrictPopulation;
    }

    public int getDestDistrictPopulation() {
        return destDistrictPopulation;
    }

    public void setDestDistrictPopulation(int destDistrictPopulation) {
        this.destDistrictPopulation = destDistrictPopulation;
    }

    public String getObjectiveValue() {
        return objectiveValue;
    }

    public void setObjectiveValue(String objectiveValue) {
        this.objectiveValue = objectiveValue;
    }

    public String getObjectiveGain() {
        return objectiveGain;
    }

    public void setObjectiveGain(String objectiveGain) {
        this.objectiveGain = objectiveGain;
    }

    public int[] getPrecinctIds() {
        return precinctIds;
    }

    public void setPrecinctIds(int[] precinctIds) {
        this.precinctIds = precinctIds;
    }

    public Map<Party, Integer> getPrecinctWastedVotes() {
        return precinctWastedVotes;
    }

    public void setPrecinctWastedVotes(Map<Party, Integer> precinctWastedVotes) {
        this.precinctWastedVotes = precinctWastedVotes;
    }

    public Map<Party, Integer> getDestDistrictWastedVotes() {
        return destDistrictWastedVotes;
    }

    public void setDestDistrictWastedVotes(Map<Party, Integer> destDistrictWastedVotes) {
        this.destDistrictWastedVotes = destDistrictWastedVotes;
    }
}
