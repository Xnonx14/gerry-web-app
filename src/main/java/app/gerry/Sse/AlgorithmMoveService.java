package app.gerry.Sse;

import app.gerry.AlgorithmCore.Algorithm;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AlgorithmMoveService {

    public final ApplicationEventPublisher eventPublisher;

    public AlgorithmMoveService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void runAlgorithm(Algorithm algorithm) {
        while(!algorithm.isFinished()) {
            algorithm.step();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            eventPublisher.publishEvent(algorithm.getSseResultData());
        }
    }
}
