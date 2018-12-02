package app.gerry.AlgorithmCore;

import app.gerry.Geography.Chunk;
import app.gerry.Geography.District;
import app.gerry.Geography.State;
import app.gerry.Sse.SseResultData;
import java.util.Set;

public class SimulatedAnnealing implements Algorithm{
    State startingState;
    public SimulatedAnnealing(State state){
        startingState = state;
    }
    
    @Override
    public void step() {
        
        //1) Choose a random sourceDistrict: srcDistrict
        District srcDistrict = startingState.getRandomDistrict();
        //2) Get a chunk inside the srcDistrict along the border (to be moved): Chunk 
        Chunk movingChunk = srcDistrict.getRandomBorderChunk();
        //3) Get the Districts adjacent to the srcDistrict from step 1: destDistricts
        Set<Chunk> chunks = movingChunk.getAdjacentChunks();
        
        for(Chunk ck: chunks){
            ck.getDistrict();
        }
        
        //4) createAllMoves(chunk,srcDistrict,destDistricts): array[moves]
        //5) Iterate through all moves until you find a move that increases objective
        //6) finalize that move. 
        
        
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public SseResultData getSseResultData() {
        return null;
    }
}
