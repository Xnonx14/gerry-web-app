package app.gerry.Util;

import app.gerry.AlgorithmCore.Context;
import app.gerry.Geography.Precinct;
import app.gerry.Geography.State;
import app.model.DistrictEntity;
import app.model.PrecinctEntity;
import app.model.StateEntity;
import app.repository.DistrictRepository;
import app.repository.PrecinctRepository;
import app.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static app.gerry.Constants.AlgorithmConstants.*;

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
        //context.setPolsbyPopperWeight(toDouble(params, POLSBY_POPPER));
        //context.setConvexHullWeight(toDouble(params, CONVEX_HULL));
        //context.setBoyceClarkWeight(toDouble(params, BOYCE_CLARK));
        context.setPopulationEqualityWeight(toDouble(params, POPULATION_EQUALITY));
        context.setStateName(toString(params, STATE_NAME));
        return context;
    }

    public State initializeStateWithRandomSeedDistricts(String stateName, int numDistricts) {
        List<Precinct> precincts = aggregatePrecinctsInState(stateName);

        State state = new State.Builder(stateName).build();
        return null;
    }

    private List<Precinct> aggregatePrecinctsInState(String stateName) {
        List<PrecinctEntity> precinctEntities = aggregatePrecinctEntitiesInState(stateName);
        return convertPrecinctEntitiesToPrecincts(precinctEntities);
    }

    private List<PrecinctEntity> aggregatePrecinctEntitiesInState(String stateName) {
        StateEntity stateEntityEntity = stateRepository.findByName(stateName);
        List<DistrictEntity> districtEntities =  districtRepository.findByStateId(stateEntityEntity.getId());
        List<PrecinctEntity> result = new ArrayList<>();
        for(DistrictEntity districtEntity : districtEntities) {
            result.addAll(precinctRepository.findByDistrictId(districtEntity.getId()));
        }
        return result;
    }

    private List<Precinct> convertPrecinctEntitiesToPrecincts(List<PrecinctEntity> precinctEntities) {
        return precinctEntities.stream().map(
                pe -> new Precinct.Builder()
                            .withId(pe.getId())
                            .withAdjacentPrecincts(null)
                            .build()
        ).collect(Collectors.toList());
    }

    private Double toDouble(Map<String, Object> params, String key) {
        System.out.println(params.values());
        return Double.parseDouble((String)params.get(key));
    }

    private String toString(Map<String, Object> params, String key) {
        return params.get(key).toString();
    }
}
