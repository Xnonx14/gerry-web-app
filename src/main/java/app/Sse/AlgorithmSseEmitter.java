package app.Sse;

import app.model.Algorithm;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class AlgorithmSseEmitter extends SseEmitter{

    Algorithm algoInstance;

    public AlgorithmSseEmitter(long timeout) {
        super(timeout);
        algoInstance = new Algorithm();
    }

    public Algorithm getAlgoInstance() {
        return algoInstance;
    }

    public void setAlgoInstance(Algorithm algoInstance) {
        this.algoInstance = algoInstance;
    }
}
