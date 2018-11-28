package app.Sse;

import app.model.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

@Component
public class SseEngine {

    private static Logger logger = LoggerFactory.getLogger(SseEngine.class);
    private static long TIMEOUT = 30000L;
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public Map<String, SseEmitter> getEmitters() {
        return emitters;
    }

    @Async
    public Future<String> runAlgorithmComplete(Sse sseService, SseData data) {
        //Thread.interrupted() if its interrupted
        SseEmitter emitter = emitters.get(data.getEventId());
        String eventId = data.getEventId();

        config(emitter, eventId);
        if(emitters.get(data.getEventId()) != null) {
            //replace this with while algorithm is not complete
            while ((Calendar.getInstance().getTimeInMillis() - data.getStarted().getTime()) < TIMEOUT) {
                Algorithm algo = ((AlgorithmSseEmitter) emitter).getAlgoInstance();
                algo.step();    //State of the algorithm changes after a step
                sseService.handle(data);    //Handle the data so the user can view a change in the algorithm
                if(Thread.interrupted()) {
                    /**
                     * If thread is interrupted, we want to stop stepping in the algorithm.
                     * When the user pauses the algorithm this should happen somehow.
                    **/
                    return new AsyncResult<>("Algorithm paused");
                }
            }
        }
        return new AsyncResult<>("Algorithm completed");
    }

    @Async
    public void runAlgorithmStep(Sse sseService, SseData data) {

    }

    private void config(SseEmitter emitter, String eventId) {
        if (emitter != null) {
            emitter.onCompletion(() -> {
                logger.debug("Emitter " + emitter.toString() + " COMPLETED!");
                emitters.remove(eventId);
            });
            emitter.onTimeout(() -> {
                logger.debug("Emitter " + emitter.toString() + " TIMEOUT!");
                emitters.remove(eventId);
            });
        }
    }

    public static long getTIMEOUT() {
        return TIMEOUT;
    }
}
