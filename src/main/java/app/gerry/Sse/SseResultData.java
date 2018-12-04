package app.gerry.Sse;

import java.util.Date;

public class SseResultData {
    private int districtId;
    private int precinctId;
    private String data;
    private Date dateSent;
    private boolean isLastOne;

    public SseResultData(int districtId, int precinctId, boolean isLastOne) {
        this.districtId = districtId;
        this.precinctId = precinctId;
        this.isLastOne = isLastOne;
    }

    public SseResultData(String data, boolean isLastOne) {
        dateSent = new Date();
        this.data = data;
        this.isLastOne = isLastOne;
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

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getPrecinctId() {
        return precinctId;
    }

    public void setPrecinctId(int precinctId) {
        this.precinctId = precinctId;
    }
}
