package app.gerry.AlgorithmCore;

public class Context {
    private String stateName;
    private double polsbyPopperWeight;
    private double convexHullWeight;
    private double boyceClarkWeight;
    private double populationEqualityWeight;
    private double politicalFairnessWeight;

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public double getPolsbyPopperWeight() {
        return polsbyPopperWeight;
    }

    public void setPolsbyPopperWeight(double polsbyPopperWeight) {
        this.polsbyPopperWeight = polsbyPopperWeight;
    }

    public double getConvexHullWeight() {
        return convexHullWeight;
    }

    public void setConvexHullWeight(double convexHullWeight) {
        this.convexHullWeight = convexHullWeight;
    }

    public double getBoyceClarkWeight() {
        return boyceClarkWeight;
    }

    public void setBoyceClarkWeight(double boyceClarkWeight) {
        this.boyceClarkWeight = boyceClarkWeight;
    }

    public double getPopulationEqualityWeight() {
        return populationEqualityWeight;
    }

    public void setPopulationEqualityWeight(double populationEqualityWeight) {
        this.populationEqualityWeight = populationEqualityWeight;
    }

    public double getPoliticalFairnessWeight() {
        return politicalFairnessWeight;
    }

    public void setPoliticalFairnessWeight(double politicalFairnessWeight) {
        this.politicalFairnessWeight = politicalFairnessWeight;
    }
}
