package app.event;

import org.springframework.context.ApplicationEvent;

public class AbstractApplicationEvent extends ApplicationEvent{
    private static final long serialVersionUID = 1L;

    protected String eventId;

    public AbstractApplicationEvent(Object source, String eventId) {
        super(source);
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
