package app.gerry.AlgorithmCore;

import app.gerry.Geography.District;
import app.gerry.Geography.State;
import app.gerry.Sse.SseResultData;
import app.gerry.Util.AlgorithmUtil;

import java.util.List;
import java.util.Map;

public class RegionGrowing extends Algorithm{

    private AlgorithmUtil algorithmUtil;
    private Context context;
    private State state;

    public RegionGrowing(Map<String, Object> params, AlgorithmUtil algorithmUtil) {
        this.algorithmUtil = algorithmUtil;
        context = algorithmUtil.initializeAlgorithmParameters(params);
        state = algorithmUtil.initializeStateWithRandomSeedDistricts(context.getStateName(), 2);
    }

    /**
     * For each seed district:
     *  get adjacent chunks
     *  pick best chunk(random for now)
     *  finalize the move (update movestack)
     */
    @Override
    public void step() {
        List<District> seedDistricts = state.getSeedDistricts();

    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public SseResultData getSseResultData() {
        return null;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
