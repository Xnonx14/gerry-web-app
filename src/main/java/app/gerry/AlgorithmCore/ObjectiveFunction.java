package app.gerry.AlgorithmCore;

import app.gerry.Constants.Party;
import app.gerry.Geography.District;
import app.gerry.Geography.State;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.algorithm.MinimumBoundingCircle;

import java.util.HashMap;
import java.util.Map;

public class ObjectiveFunction {

    public static double getObjectiveValue(District district, Context context){
        if(district == null){
            return 0;
        }
        Geometry geometricData = district.getGeometricData();
        Map<Party, Integer> wastedVotes = district.getCummWastedVotes();
        int totalVotes = district.getTotalVotes();
        double reockWeight = context.getReockWeight();
        double polsbyWeight = context.getPolsbyPopperWeight();
        double convexWeight = context.getConvexHullWeight();
        double fairnessWeight = context.getPoliticalFairnessWeight();
        double populationWeight = context.getPopulationEqualityWeight();
        double reockValue = calculateReock(geometricData);
        double polsbyValue = calculatePolsby(geometricData);
        double convexValue = calculateConvex(geometricData);
        double fairnessValue = calculate_Political(wastedVotes, totalVotes);
        double populationValue = calculatePopulation(district);
/*        double result = (populationValue * populationWeight) + (convexValue * convexWeight) + (polsbyValue * polsbyWeight)
                + (fairnessValue * fairnessWeight);
        if(true) {
            return result;
        }*/

        return (reockWeight * reockValue)
                + (polsbyWeight * polsbyValue)
                + (convexWeight * convexValue)
                + (populationWeight * populationValue)
                + (fairnessWeight * fairnessValue);
    }

    private static double calculateReock(Geometry geometricData) {
        double area = geometricData.getArea();
        double minBoundingCircleArea = getMinBoundingCircleArea(geometricData);
        return area / minBoundingCircleArea;
    }

    private static double calculatePolsby(Geometry geometricData) {
        double area = geometricData.getArea();
        double perimeter = geometricData.getLength();
        return (4 * Math.PI * area) / perimeter;
    }

    private static double calculateConvex(Geometry geometricData) {
        double area = geometricData.getArea();
        double convexHullArea = geometricData.convexHull().getArea();
        return area / convexHullArea;
    }

    private static double calculate_Convex(double area, double convexHullArea) {
        return area/convexHullArea;
    }

    private static double calculate_Political(Map<Party, Integer> wastedVotes, int totalVotes) {
        int maxWastedVotes = 0;
        int otherVotes = 0;
        for (Party p : wastedVotes.keySet()){
            if (wastedVotes.get(p) > maxWastedVotes){
                maxWastedVotes = wastedVotes.get(p);
            }
            else{
                otherVotes += wastedVotes.get(p);
            }
        }
        if (totalVotes == 0)
            return 0;
        return ((double)(maxWastedVotes-otherVotes))/totalVotes;
    }

    private static double calculatePopulation(District district) {
        State state = district.getState();
        int lowestPop = Integer.MAX_VALUE;
        for(District d : state.getSeedDistricts()) {
            lowestPop = Math.min(lowestPop, d.getPopulation());
        }
        int population = district.getPopulation();
        double averagePopulation = state.getAverageDistrictPopulation();
        int totalPopulation = state.getPopulation();
        double ratio = (Math.abs((double)population - averagePopulation)/totalPopulation);
        double populationValue = 1 - ratio;
        if(population == lowestPop)
            return -0.2;
        return populationValue;
    }

    //this is broken
    public static double normalize(double value,double min, double max){
        return (value - min) / (max - min);
    }

    private static double getMinBoundingCircleArea(Geometry geometricData) {
        MinimumBoundingCircle mbc = new MinimumBoundingCircle(geometricData);
        return mbc.getCircle().getArea();
    }
}
