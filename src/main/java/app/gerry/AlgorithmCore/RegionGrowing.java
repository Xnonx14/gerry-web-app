package app.gerry.AlgorithmCore;

import app.gerry.Sse.SseResultData;
import app.gerry.Util.AlgorithmUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

public class RegionGrowing extends Algorithm{

    private AlgorithmUtil algorithmUtil;
    private String stateName;
    private Context context;

    public RegionGrowing(Map<String, Object> params, AlgorithmUtil algorithmUtil) {
        this.algorithmUtil = algorithmUtil;
        context = algorithmUtil.initializeAlgorithmParameters(params);
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
