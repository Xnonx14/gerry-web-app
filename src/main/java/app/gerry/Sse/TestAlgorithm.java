package app.gerry.Sse;

import app.gerry.AlgorithmCore.Algorithm;
import app.gerry.Sse.SseResultData;

public class TestAlgorithm extends Algorithm {

    private static final int MAX = 1000;

    private int curr;
    private int prev;
    private String lastMove;

    public TestAlgorithm() {
        prev = 0;
        curr = 0;
        lastMove = "None";
    }

    @Override
    public void step() {
        int jump = 5 + (int) (Math.random() * 10);
        prev = curr;
        curr += jump;
        lastMove = "Jumped by: " + Integer.toString(jump);
    }

    @Override
    public boolean isFinished() {
        return curr >= MAX;
    }

    @Override
    public SseResultData getSseResultData() {
        return new SseResultData(null, isFinished());
    }
}
