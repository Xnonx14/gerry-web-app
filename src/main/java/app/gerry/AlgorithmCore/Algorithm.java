package app.gerry.AlgorithmCore;

import app.gerry.Sse.SseResultData;

public interface Algorithm {

    public void step();

    public boolean isFinished();

    public SseResultData getSseResultData();
}
