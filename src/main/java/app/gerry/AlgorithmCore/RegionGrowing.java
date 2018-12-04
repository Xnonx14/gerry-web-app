package app.gerry.AlgorithmCore;

import app.gerry.Geography.Chunk;
import app.gerry.Geography.District;
import app.gerry.Geography.State;
import app.gerry.Sse.SseResultData;
import app.gerry.Util.AlgorithmUtil;

import java.util.*;

public class RegionGrowing extends Algorithm{

    private AlgorithmUtil algorithmUtil;
    private Context context;
    private State state;
    private Stack<Chunk> chunkMoveStack;
    private int iterations;
    private int index;

    public RegionGrowing(Map<String, Object> params, AlgorithmUtil algorithmUtil) {
        this.algorithmUtil = algorithmUtil;
        context = algorithmUtil.initializeAlgorithmParameters(params);
        state = algorithmUtil.initializeStateWithRandomSeedDistricts(context.getStateName(), 2);
        chunkMoveStack = new Stack<>();
        init();
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
        District seedDistrict = seedDistricts.get(index);
        List<Chunk> adjacentChunks = new ArrayList<>(seedDistrict.getAdjacentChunks());
        Chunk selected = adjacentChunks.get(0);
        seedDistrict.addChunk(selected);
        chunkMoveStack.push(selected);
        index++;
        index = index % seedDistricts.size();
        iterations++;
    }

    @Override
    public boolean isFinished() {
        return iterations >= 200;
    }

    @Override
    public SseResultData getSseResultData() {
        int precinctId = chunkMoveStack.peek().getId();
        int districtId = chunkMoveStack.peek().getParentDistrict().getId();

        return new SseResultData(districtId, precinctId, isFinished());
    }

    private void init() {
        //
        List<District> seedDistricts = state.getSeedDistricts();

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
