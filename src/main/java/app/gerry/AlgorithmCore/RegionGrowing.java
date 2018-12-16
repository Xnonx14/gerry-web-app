package app.gerry.AlgorithmCore;

import app.gerry.Geography.Chunk;
import app.gerry.Geography.District;
import app.gerry.Geography.State;
import app.gerry.Sse.SseResultData;
import app.gerry.Util.AlgorithmUtil;

import java.text.DecimalFormat;
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
        System.out.println(context.getSeedCount());
        state = algorithmUtil.initializeStateWithRandomSeedDistricts(context.getStateName(), context.getSeedCount());
        chunkMoveStack = new Stack<>();
        moveStack = new Stack<>();
        seen = new HashSet<>();
        unassignedChunks = new ArrayList<>(state.getChunks());
        for(District district : state.getSeedDistricts()) {
            seen.addAll(district.getChunks());
        }
    }

    @Override
    public void step() {
        District worstDistrict = getWorstDistrict();
        Move bestMove = getBestMove(worstDistrict);
        if(bestMove == null) {
            state.getSeedDistricts().remove(worstDistrict);
            return;
        }
        bestMove.execute();
        updateState(bestMove);
    }

    @Override
    public boolean isFinished() {
        return state.getSeedDistricts().isEmpty();
    }

    @Override
    public SseResultData getSseResultData() {
        Move move = moveStack.peek();
        return new SseResultData(move, isFinished());
    }

    private Move getBestMove(District district) {
        List<Chunk> adjacentChunks = new ArrayList<>(district.getAdjacentChunks());
        double initVal = ObjectiveFunction.getObjectiveValue(district, context);
        double maxGain = - Double.MAX_VALUE;
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
                DecimalFormat df = new DecimalFormat("#.00000");
                String objValue = "District "+district.getId() + ": " + df.format(val )+ " ("+df.format(gain)+")";
                bestMove.setObjectiveValue(objValue);
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
        int chunkId = move.getChunk().getId();
        Chunk chunk = state.getIdChunkMap().get(chunkId);
        seen.add(chunk);
        moveStack.push(move);
    }
}
