package app.gerry.AlgorithmCore;

import app.gerry.Geography.Chunk;
import app.gerry.Geography.District;
import app.gerry.Geography.Precinct;
import app.gerry.Geography.State;
import app.gerry.Sse.SseResultData;
import app.gerry.Util.AlgorithmUtil;
import org.springframework.security.core.parameters.P;

import java.util.*;
import java.util.stream.Collectors;

public class SimulatedAnnealing extends Algorithm{
    private AlgorithmUtil algorithmUtil;
    private Context context;
    private State state;
    private Stack<Chunk> chunkMoveStack;
    private int iterations;
    private int index;
    private Set<Chunk> seen;
    private List<Chunk> unassignedChunks;
    private Stack<Move> moveStack;


    //Temperature
    private double T = 0.001;
    private double T_min = 0.00001;
    private double alpha = 0.9;

    public SimulatedAnnealing(Map<String, Object> params, AlgorithmUtil algorithmUtil, State state) {
        this.algorithmUtil = algorithmUtil;
        context = algorithmUtil.initializeAlgorithmParameters(params);
        moveStack = new Stack<>();
        iterations = 20;
        this.state = state;
    }

    @Override
    public void step() {
        //1) Choose a random sourceDistrict: srcDistrict
        System.out.println("Temperature" + T);
        District destDistrict = state.getRandomDistrict();
        Set<Chunk> moveableChunks = destDistrict.getAdjacentChunks();
        ArrayList<Chunk> chunks = new ArrayList(moveableChunks);
        while(true) {
            iterations--;
            int rand = new Random().nextInt(chunks.size());
            Chunk chunk = chunks.get(rand);
            District srcDistrict = chunk.getDistrict();
            while(articulation_check(chunk) == true){
                rand = new Random().nextInt(chunks.size());
                chunk = chunks.get(rand);
            }
            double sum = ObjectiveFunction.getObjectiveValue(destDistrict, context) + ObjectiveFunction.getObjectiveValue(srcDistrict, context);
            Move m = new Move(chunk, destDistrict);
            m.execute();
            double sumEnd = ObjectiveFunction.getObjectiveValue(destDistrict, context) + ObjectiveFunction.getObjectiveValue(srcDistrict, context);
            double ap = a_probability(sum, sumEnd, T);
            if (a_probability(sum,sumEnd,T) > Math.random()) {
                System.out.println("Approved");
                moveStack.push(m);
                break;
            }
//            else if(Math.random() < T){
//                moveStack.push(m);
//                break;
//            }
            else {
                System.out.println("Rejected");
                if(iterations == 0){
                    break;
                }
                m.undo();
            }
        }
    }

    public boolean articulation_check(Chunk chunk){
        Set<Chunk> copy = new HashSet<>();
        for(Chunk c: chunk.getAdjacentChunks()){
            if(c == null || c.getParentDistrict() == null || chunk == null || chunk.getParentDistrict() == null){
                continue;
            }
            if(c.getParentDistrict().getId() == chunk.getParentDistrict().getId()){
                copy.add(c);
            }
        }
        Set<Chunk> visited = new HashSet<>();
        visited.add(chunk);
        for(Chunk c: copy){
            if(c.getParentDistrict().getId() == chunk.getParentDistrict().getId()){
                copy.remove(c);
                dfs(c, copy, visited);
                break;
            }
        }
        if(copy.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
    public void dfs(Chunk chunk, Set<Chunk> copy, Set<Chunk> visited){
            for (Chunk c : chunk.getAdjacentChunks()) {
                if(c == null || c.getParentDistrict() == null){
                    continue;
                }
                if (c.getParentDistrict().getId() == chunk.getParentDistrict().getId() && !visited.contains(c)) {
                    if (copy.contains(c)) {
                        copy.remove(c);
                    }
                    visited.add(c);
                    dfs(c, copy, visited);
                }
            }
    }



    public double a_probability (double old_sum, double new_sum, double temperature){
        return Math.pow(Math.E, (new_sum - old_sum) / temperature);
    }


    @Override
    public boolean isFinished() {
        if(iterations <= 0){
            iterations = 20;
            T = T * alpha;
        }
        if(T > T_min){
            return false;
        }
        return true;
    }

    @Override
    public SseResultData getSseResultData() {
        Move move = moveStack.peek();
        return new SseResultData(move, isFinished());
    }
}
