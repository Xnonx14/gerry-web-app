package app.gerry.Util;

import app.gerry.AlgorithmCore.Context;
import app.gerry.Geography.Chunk;
import app.gerry.Geography.District;
import app.gerry.Geography.Precinct;
import app.gerry.Geography.State;
import app.gerry.Json.ChunkJson;
import app.model.DistrictEntity;
import app.model.PrecinctEntity;
import app.model.StateEntity;
import app.repository.DistrictRepository;
import app.repository.PrecinctRepository;
import app.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static app.gerry.Constants.AlgorithmConstants.*;
import static app.gerry.Json.JsonUtil.createChunkJsons;

@Service
public class AlgorithmUtil {
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private PrecinctRepository precinctRepository;

    public Context initializeAlgorithmParameters(Map<String, Object> params) {
        Context context = new Context();
        context.setPoliticalFairnessWeight(toDouble(params, POLITICAL_FAIRNESS));
        context.setPolsbyPopperWeight(toDouble(params, POLSBY_POPPER));
        context.setConvexHullWeight(toDouble(params, CONVEX_HULL));
        context.setReockWeight(toDouble(params, REOCK));
        context.setPopulationEqualityWeight(toDouble(params, POPULATION_EQUALITY));
        context.setStateName(toString(params, STATE_NAME));
        return context;
    }

    public State initializeStateWithRandomSeedDistricts(String stateName, int numDistricts) {
        List<Precinct> precincts = aggregatePrecinctsInState(stateName);
        Map<Integer, Chunk> idChunkMap = toIdChunkMap(precincts);
        List<Chunk> chunks = new ArrayList<>(idChunkMap.values());
        Map<Integer, List<Integer>> adjacentChunkIdMap = constructAdjacentChunkMap(chunks, stateName);
        setAdjacentChunks(idChunkMap, adjacentChunkIdMap);
        List<District> seeds = constructSeedDistrictsRandomly(chunks, numDistricts);

        for(int i = 0; i < seeds.size(); i++) {
            seeds.get(i).id = i;
        }
        State state = new State.Builder(stateName)
                        .withIdChunkMap(idChunkMap)
                        .withDistricts(seeds)
                        .withAdjacentChunkMap(adjacentChunkIdMap)
                        .build();
        return state;
    }

    private void setAdjacentChunks(Map<Integer, Chunk> idChunkMap, Map<Integer, List<Integer>> adjacentChunkIdMap) {
        for(Chunk chunk : idChunkMap.values()) {
            List<Integer> adjIds = adjacentChunkIdMap.get(chunk.getId());
            if(adjIds == null)
                continue;
            chunk.setAdjacentChunks(
                    adjIds.stream()
                    .map(i -> idChunkMap.get(i))
                    .collect(Collectors.toSet())
            );
        }
    }

    private Map<Integer, List<Integer>> constructAdjacentChunkMap(List<Chunk> chunks, String stateName) {
        Map<Integer, List<Integer>> idAdjacencyMap = new HashMap<>();
        String politicalSubdivision = chunks.get(0).getSubdivision().toString();
        String filepath = "./preprocessing/nh/idPrecinctAdj.json";
        List<ChunkJson> chunkJsons = createChunkJsons(filepath);
        for(ChunkJson chunkJson : chunkJsons) {
            List<Integer> adjacency = Arrays.stream(chunkJson.getPrecincts()).boxed().collect(Collectors.toList());
            idAdjacencyMap.put(chunkJson.getId(), adjacency);
        }
        return idAdjacencyMap;
    }

    private Map<Integer, Chunk> toIdChunkMap(List<Precinct> precincts) {
        return precincts.stream()
                    .collect(Collectors.toMap(Precinct::getId, Chunk::new));
    }

    private List<District> constructSeedDistrictsRandomly(List<Chunk> chunks, int numDistricts) {
        List<Integer> seedIndices = getRandomIndices(chunks.size(), numDistricts);
        return constructSeedDistricts(chunks, seedIndices);
    }

    private List<District> constructSeedDistricts(List<Chunk> chunks, List<Integer> indices) {
        return indices.stream()
                .map(i -> new District(chunks.get(i)))
                .collect(Collectors.toList());
    }

    private List<Integer> getRandomIndices(int collectionSize, int numDistricts) {
        return new Random()
                .ints(0, collectionSize)
                .distinct()
                .limit(numDistricts)
                .boxed()
                .collect(Collectors.toList());
    }

    private List<Precinct> aggregatePrecinctsInState(String stateName) {
        List<PrecinctEntity> precinctEntities = aggregatePrecinctEntitiesInState(stateName);
        return convertPrecinctEntitiesToPrecincts(precinctEntities);
    }

    private List<PrecinctEntity> aggregatePrecinctEntitiesInState(String stateName) {
        StateEntity stateEntity = stateRepository.findByName(stateName);
        List<DistrictEntity> districtEntities =  districtRepository.findByStateId(stateEntity.getId());
        List<PrecinctEntity> result = new ArrayList<>();
        for(DistrictEntity districtEntity : districtEntities) {
            result.addAll(precinctRepository.findByDistrictId(districtEntity.getId()));
        }
        return result;
    }

    private List<Precinct> convertPrecinctEntitiesToPrecincts(List<PrecinctEntity> precinctEntities) {
        return precinctEntities.stream()
                .map(p -> new Precinct.Builder()
                            .withId(p.getId())
                            .withAdjacentPrecincts(null)
                            .build()
                )
                .collect(Collectors.toList());
    }

    private Double toDouble(Map<String, Object> params, String key) {
        if(!params.containsKey(key)) {
            System.out.println("Key: " + key + " not found.");
            return 0.0;
        }
        return Double.parseDouble((String)params.get(key));
    }

    private String toString(Map<String, Object> params, String key) {
        if(!params.containsKey(key)) {
            System.out.println("Key: " + key + " not found.");
            return "null";
        }
        return params.get(key).toString();
    }
}
