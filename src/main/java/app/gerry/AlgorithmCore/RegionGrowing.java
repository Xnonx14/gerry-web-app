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
    private Set<Chunk> seen;
    private List<Chunk> unassignedChunks;

    public RegionGrowing(Map<String, Object> params, AlgorithmUtil algorithmUtil) {
        this.algorithmUtil = algorithmUtil;
        context = algorithmUtil.initializeAlgorithmParameters(params);
        state = algorithmUtil.initializeStateWithRandomSeedDistricts(context.getStateName(), context.getSeedCount());
        chunkMoveStack = new Stack<>();
        seen = new HashSet<>();
        unassignedChunks = new ArrayList<>(state.getChunks());
        for(District district : state.getSeedDistricts()) {
            unassignedChunks.removeAll(district.getChunks());
        }
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
        if(seedDistricts.isEmpty())
            return;
        District seedDistrict = seedDistricts.get(index);
        Set<Chunk> adjacentChunks = new HashSet<>(seedDistrict.getAdjacentChunks());
        if(adjacentChunks.isEmpty()) {
            seedDistricts.remove(seedDistrict);
            index--;
            return;
        }
        int selectedIndex = new Random().nextInt(adjacentChunks.size());
        Iterator chunkIt = adjacentChunks.iterator();
        Chunk selected = (Chunk)chunkIt.next();
        seedDistrict.addChunk(selected);
        seen.add(selected);
        unassignedChunks.remove(selected);
        for(int i = 0; i < seedDistricts.size(); i++) {
            if(i == selectedIndex)
                continue;
            seedDistrict.removeChunkFromAdjacencies(seen);
        }
        chunkMoveStack.push(selected);
        index++;
        index = index % seedDistricts.size();
        iterations++;
    }

    @Override
    public boolean isFinished() {
        return unassignedChunks.isEmpty();
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
