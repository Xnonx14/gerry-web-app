package app.gerry.Sse;

import java.util.Date;

public class SseResultData {
    private String data;
    private Date dateSent;
    private boolean isLastOne;

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
}
