package app.gerry.AlgorithmCore;

import app.gerry.Sse.SseResultData;

public abstract class Algorithm {

    private Context context;

    public abstract void step();

    public abstract boolean isFinished();

    public abstract SseResultData getSseResultData();

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
