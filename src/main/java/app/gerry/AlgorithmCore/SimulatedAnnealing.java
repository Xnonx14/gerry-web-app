package app.gerry.AlgorithmCore;

import app.gerry.Geography.Chunk;
import app.gerry.Geography.District;
import app.gerry.Geography.Precinct;
import app.gerry.Geography.State;
import app.gerry.Sse.SseResultData;
import app.gerry.Util.AlgorithmUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class SimulatedAnnealing extends Algorithm{
    private AlgorithmUtil algorithmUtil;
    private Context context;
    private State state;
    private Stack<Chunk> chunkMoveStack;
    private int iterations;
    private int index;
    private Set<Chunk> seen;
    private List<Chunk> unassignedChunks;
    
    public SimulatedAnnealing(Map<String, Object> params, AlgorithmUtil algorithmUtil) {
        this.algorithmUtil = algorithmUtil;
        context = algorithmUtil.initializeAlgorithmParameters(params);
        state = algorithmUtil.initializeStateWithAllDistricts(context.getStateName());
//        chunkMoveStack = new Stack<>();
//        seen = new HashSet<>();
//        unassignedChunks = new ArrayList<>(state.getChunks());
//        for(District district : state.getSeedDistricts()) {
//            unassignedChunks.removeAll(district.getChunks());
//        }
        iterations = 10;
    }
    
    @Override
    public void step() {
        iterations--;
        //1) Choose a random sourceDistrict: srcDistrict
        /*
        District srcDistrict = state.getWorstDistrict();
        //2) Get a chunk inside the srcDistrict along the border (to be moved): Chunk 
        //3) Get the Districts adjacent to the srcDistrict from step 1: destDistricts
        Set<Chunk> movingChunk = srcDistrict.getAdjacentChunks();
        HashSet<District> destDistricts = new HashSet<>();
        for(Chunk ck: chunks){
            District temp = ck.getDistrict();
            if(temp != srcDistrict && !destDistricts.contains(temp)){
                destDistricts.add(ck.getDistrict());
            }   
        }
        //4) createAllMoves(chunk,srcDistrict,destDistricts): array[moves]
        //mm.createAllMoves(movingChunk, srcDistrict, destDistricts);
        
        //5) Iterate through all moves until you find a move that increases objective
        //6) finalize that move. 
        
        */
    }

    
    @Override
    public boolean isFinished() {
        if(iterations >= 0){
            return false;
        }
        return true;
    }

    @Override
    public SseResultData getSseResultData() {
        return null;
    }
}
