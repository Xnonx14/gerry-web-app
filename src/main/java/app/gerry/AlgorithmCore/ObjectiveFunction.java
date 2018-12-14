package app.gerry.AlgorithmCore;

import app.gerry.Data.GeometricData;
import app.gerry.Geography.District;
import app.gerry.Geography.State;

import java.util.HashMap;

public class ObjectiveFunction {

    public static double getObjectiveValue(District d, HashMap<String, Double> weights){
        GeometricData data = d.getGeometricData();
        State s = d.getState();
//      double Reock_value = calculate_Reock(data.area, data.minBoundingCircleArea);
//      double Polsby_value = calculate_Polsby(data.area, data.perimeter);
//      double Convex_value = calculate_Convex(data.area, data.convexHullArea);
//      double Political_value = calculate_Political();
        double Population_value = calculate_Population(data.population, s.getAveragePopulation(), s.getPopulation());
        return normalize(Population_value,0 , (s.getPopulation()- s.getAveragePopulation())/s.getPopulation());
    }

    private static double calculate_Reock(double area, double minBoundingCircleArea) {
        return area/minBoundingCircleArea;
    }

    private static double calculate_Polsby(double area, double perimeter) {
        return ((4 * Math.PI * area)/perimeter);
    }

    private static double calculate_Convex(double area, double convexHullArea) {
        return area/convexHullArea;
    }

    private static double calculate_Political() {
        return 0;
    }
    private static double calculate_Population(double population, double averagePopulation, double totalPopulation) {
        return 1 - (Math.abs(population - averagePopulation)/totalPopulation);
    }
    public static double normalize(double value,double min, double max){
        return (value - min) / (max - min);
    }
}
