package app.gerry.AlgorithmCore;

import app.gerry.Sse.SseResultData;

import java.util.Map;

import static app.gerry.Util.AlgorithmUtil.initializeAlgorithmParameters;

public class RegionGrowing extends Algorithm{

    private String stateName;
    private Context context;

    public RegionGrowing(Map<String, Object> params) {
        context = initializeAlgorithmParameters(params);
        //initializeSeedDistricts();
    }

    @Override
    public void step() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public SseResultData getSseResultData() {
        return null;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
