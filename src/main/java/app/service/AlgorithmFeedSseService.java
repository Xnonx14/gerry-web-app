package app.service;

import app.Sse.*;
import app.event.AbstractApplicationEvent;
import app.event.ResultsEvent;
import app.model.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.Future;

@Service
public class AlgorithmFeedSseService implements Sse {

    private static Logger logger = LoggerFactory.getLogger(AlgorithmFeedSseService.class);

    @Autowired
    SseEngine sseEngine;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Future<String> push(String eventId, Object data) {
        return sseEngine.runAlgorithmComplete(this, new ResultsSseData(eventId));
    }

    @Override
    public void handle(SseData eventData) {
        ResultsSseData moveData = (ResultsSseData) eventData;
        AlgorithmSseEmitter algoEmitter = (AlgorithmSseEmitter) sseEngine.getEmitters().get(eventData.getEventId());
        Algorithm algorithm = algoEmitter.getAlgoInstance();

        moveData.setCount(algorithm.getCount());    //Store last move in moveData?

        //publish event
        applicationEventPublisher.publishEvent(moveData);
    }

    @Override
    @EventListener(classes = ResultsEvent.class)
    public void onPublish(AbstractApplicationEvent event) {
        ResultsEvent resultsEvent = (ResultsEvent) event;
        SseEmitter emitter = sseEngine.getEmitters().get(event.getEventId());
        try {

            if (resultsEvent.getMove() >= 0) {
                logger.debug("Sending message through emmitter " + emitter.toString());
                emitter.send(resultsEvent.getMove());
            }

        } catch (IOException e) {
            logger.error("Error in emitter " + emitter + " while sending message");
            sseEngine.getEmitters().remove(event.getEventId());
        }
    }
}
