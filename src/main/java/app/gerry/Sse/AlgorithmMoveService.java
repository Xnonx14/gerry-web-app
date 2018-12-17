package app.gerry.Sse;

import app.gerry.AlgorithmCore.Algorithm;
import app.gerry.AlgorithmCore.Move;
import app.gerry.AlgorithmCore.RegionGrowing;
import app.gerry.Geography.Chunk;
import app.gerry.Geography.District;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlgorithmMoveService {

    public final ApplicationEventPublisher eventPublisher;
    private boolean endAlgorithm = false;
    public AlgorithmMoveService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void runAlgorithm(Algorithm algorithm) {
        while(!algorithm.isFinished() && !endAlgorithm) {
            algorithm.step();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            eventPublisher.publishEvent(algorithm.getSseResultData());
        }
        endAlgorithm = false;
    }

    public void setEndAlgorithm(boolean endAlgorithm){
        this.endAlgorithm = endAlgorithm;
    }

    public void publishInitialSeedDistrictMoves(Algorithm algorithm) {
        RegionGrowing algo = (RegionGrowing) algorithm;
        List<District> districts = algo.getState().getSeedDistricts();
        for(District seed : districts) {
            Chunk seedChunk = new ArrayList<>(seed.getChunks()).get(0);
            Move move = new Move(seedChunk, seed);
            eventPublisher.publishEvent(new SseResultData(move, false));
        }
    }
}
