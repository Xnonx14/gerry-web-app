package app.gerry.Util;

import app.gerry.AlgorithmCore.Algorithm;
import app.gerry.AlgorithmCore.Context;
import app.gerry.Geography.State;

import java.util.Map;

import static app.gerry.Constants.AlgorithmConstants.*;

public class AlgorithmUtil {

    public static Context initializeAlgorithmParameters(Map<String, Object> params) {
        Context context = new Context();
        context.setPoliticalFairnessWeight(toDouble(params, POLITICAL_FAIRNESS));
        context.setPolsbyPopperWeight(toDouble(params, POLSBY_POPPER));
        context.setConvexHullWeight(toDouble(params, POLSBY_POPPER));
        context.setBoyceClarkWeight(toDouble(params, BOYCE_CLARK));
        context.setPopulationEqualityWeight(toDouble(params, POPULATION_EQUALITY));
        context.setStateName(toString(params, STATE_NAME));
        return context;
    }

    public static State initializeStateWithSeedDistricts() {
        return null;
    }

    private static Double toDouble(Map<String, Object> params, String key) {
        return Double.parseDouble((String)params.get(key));
    }

    private static String toString(Map<String, Object> params, String key) {
        return params.get(key).toString();
    }
}
