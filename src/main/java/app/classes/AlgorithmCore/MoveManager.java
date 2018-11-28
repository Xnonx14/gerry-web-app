package app.classes.AlgorithmCore;

import app.classes.Geography.Chunk;
import app.classes.Geography.District;

import java.util.Set;

public class MoveManager {
    public Set<Move> moves;
    public void createMove(Chunk c, District srcD, District destD){
        return;
    }

    public void createAllMoves(Chunk c, District srcD, Set<District> destD){
        return;
    }

    public void evaluateAllMoves(Set<Chunk> chunks, District destD){
        return;
    }
    public void executeMove(Move m){
        return;
    }

    public Move finalizeBestMove(){
        return null;
    }

    public void finalizeMove(){
        return;
    }

    public Move getRandomMove(){
        return null;
    }

    public void clearAll(){
        return;
    }

    public void removeMove(Move m){
        return;
    }

    public void undoMove(Move m){
        return;
    }
}
