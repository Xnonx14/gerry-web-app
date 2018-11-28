package app.Sse;

import app.dao.MoveDao;

import java.util.Date;
import java.util.List;

public class ResultsSseData extends SseData{
    private int count;

    public ResultsSseData(String eventId) {
        super();
        setEventId(eventId);
        setStarted(new Date());
    }

    public int getCount() {
        return count;
    }

    public void setCount(int processedMove) {
        this.count = processedMove;
    }
}

