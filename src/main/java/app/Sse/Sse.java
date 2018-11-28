package app.Sse;

import app.event.AbstractApplicationEvent;
import org.springframework.context.event.EventListener;

import java.util.concurrent.Future;

public interface Sse {

    /**
     * Push data to SSE channel associated to an event ID
     *  @param eventId
     * @param data
     */
    public Future<String> push(String eventId, Object data);

    /**
     * This method does the necessary actions to publish the event with the proper data
     *
     * @param eventData
     */
    public void handle(SseData eventData);

    /**
     * This method is listening for new events being published. To restrict the events listened to only a specific group, the event classes in
     * charge of handle the events published must be specified within the annotation @EventListener
     *
     * @param event
     */
    @EventListener
    public void onPublish(AbstractApplicationEvent event);
}