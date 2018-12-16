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
    private Stack<Move> moveStack;
    private int index;
    private Set<Chunk> seen;
    private List<Chunk> unassignedChunks;

    public RegionGrowing(Map<String, Object> params, AlgorithmUtil algorithmUtil) {
        this.algorithmUtil = algorithmUtil;
        context = algorithmUtil.initializeAlgorithmParameters(params);
        state = algorithmUtil.initializeStateWithRandomSeedDistricts(context.getStateName(), context.getSeedCount());
        chunkMoveStack = new Stack<>();
        moveStack = new Stack<>();
        seen = new HashSet<>();
        unassignedChunks = new ArrayList<>(state.getChunks());
        for(District district : state.getSeedDistricts()) {
            seen.addAll(district.getChunks());
        }
    }

//    /**
//     * For each seed district:
//     *  get adjacent chunks
//     *  pick best chunk(random for now)
//     *  finalize the move (update movestack)
//     */
//    @Override
//    public void step() {
//        List<District> seedDistricts = state.getSeedDistricts();
//        if(seedDistricts.isEmpty())
//            return;
//        District seedDistrict = seedDistricts.get(index);
//        Set<Chunk> adjacentChunks = new HashSet<>(seedDistrict.getAdjacentChunks());
//        if(adjacentChunks.isEmpty()) {
//            seedDistricts.remove(seedDistrict);
//            index--;
//            return;
//        }
//        int selectedIndex = getBestChunkIndex(adjacentChunks);
//        Iterator chunkIt = adjacentChunks.iterator();
//        Chunk selected = (Chunk)chunkIt.next();
//        seedDistrict.addChunk(selected);
//        updateState(selected, seedDistricts, selectedIndex);
//    }

    @Override
    public void step() {
        District worstDistrict = getWorstDistrict();
        Move bestMove = getBestMove(worstDistrict);
        //bestMove.execute();
        //updateState(bestMove);
    }

    @Override
    public boolean isFinished() {
        return unassignedChunks.isEmpty();
    }

    @Override
    public SseResultData getSseResultData() {
        Move move = moveStack.peek();
        return new SseResultData(move, isFinished());
    }

    private Move getBestMove(District district) {
        List<Chunk> adjacentChunks = new ArrayList<>(district.getAdjacentChunks());
        double initVal = ObjectiveFunction.getObjectiveValue(district, context);
        double maxGain = Double.MIN_VALUE;
        Move bestMove = null;
        for(Chunk chunk : adjacentChunks) {
            if(seen.contains(chunk)) {
                continue;
            }
            Move move = new Move(chunk, district);
            move.execute();
            double val = ObjectiveFunction.getObjectiveValue(district, context);
            double gain = val - initVal;
            if(gain > maxGain) {
                maxGain =  gain;
                bestMove = move;
            }
            move.undo();
        }
        return bestMove;
    }

    private District getWorstDistrict() {
        List<District> districts = state.getSeedDistricts();
        District worstDistrict = districts.get(0);
        for(District district : districts) {
            double worstDistrictValue = ObjectiveFunction.getObjectiveValue(worstDistrict, context);
            double currDistrictValue = ObjectiveFunction.getObjectiveValue(district, context);
            if(currDistrictValue < worstDistrictValue) {
                worstDistrict = district;
            }
        }
        return worstDistrict;
    }

    private Move createMove(Chunk chunk, District district) {
        return null;
    }

    private void executeMove(Move move) {

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private int getBestChunkIndex(Set<Chunk> adjacentChunks) {
        return new Random().nextInt(adjacentChunks.size());
    }

//    private void updateState(Chunk selected, List<District> seedDistricts, int selectedIndex) {
//        seen.add(selected);
//        unassignedChunks.remove(selected);
//        for(int i = 0; i < seedDistricts.size(); i++) {
//            if(i == selectedIndex)
//                continue;
//            seedDistricts.get(i).removeChunkFromAdjacencies(seen);
//        }
//        chunkMoveStack.push(selected);
//        index++;
//        index = index % seedDistricts.size();
//    }

    private void updateState(Move move) {
        int chunkId = move.getChunkId();
        Chunk chunk = state.getIdChunkMap().get(chunkId);
        seen.add(chunk);
        moveStack.push(move);
    }
}
