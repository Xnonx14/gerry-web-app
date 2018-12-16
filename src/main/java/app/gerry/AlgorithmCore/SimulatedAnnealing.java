package app.gerry.AlgorithmCore;

import app.gerry.Geography.Chunk;
import app.gerry.Geography.District;
import app.gerry.Geography.Precinct;
import app.gerry.Geography.State;
import app.gerry.Sse.SseResultData;
import app.gerry.Util.AlgorithmUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
    
    public SimulatedAnnealing(Map<String, Object> params, AlgorithmUtil algorithmUtil, State state) {
        this.algorithmUtil = algorithmUtil;
        context = algorithmUtil.initializeAlgorithmParameters(params);
        iterations = 10;
        this.state = state;
    }
    
    @Override
    public void step() {
        iterations--;
        //1) Choose a random sourceDistrict: srcDistrict
        District destDistrict = state.getRandomDistrict();
        Set<Chunk> moveableChunks = destDistrict.getAdjacentChunks();
        Iterator<Chunk> iter = moveableChunks.iterator();
        while(iter.hasNext()){
            Chunk chunk = iter.next();
            District srcDistrict = chunk.getDistrict();
            double sum = ObjectiveFunction.getObjectiveValue(destDistrict, context) + ObjectiveFunction.getObjectiveValue(srcDistrict, context);
            Move m = new Move(chunk, destDistrict);
            m.execute();
            double sumEnd = ObjectiveFunction.getObjectiveValue(destDistrict, context) + ObjectiveFunction.getObjectiveValue(srcDistrict, context);
        }
        
        //5) Iterate through all moves until you find a move that increases objective
        //6) finalize that move. 
        
        
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
