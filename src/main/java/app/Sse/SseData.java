package app.Sse;

import java.util.Date;

public class SseData {
    protected String eventId;
    protected Date started;

    public String getEventId() {
        return eventId;
    }

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;

    }
}
