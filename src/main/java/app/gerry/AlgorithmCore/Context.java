package app.gerry.AlgorithmCore;

import app.gerry.Constants.Position;
import app.gerry.Geography.State;

import java.sql.Date;

public class Context {
    private String stateName;
    private Date date;
    private Position position;
    private double polsbyPopperWeight;
    private double convexHullWeight;
    private double reockWeight;
    private double populationEqualityWeight;
    private double politicalFairnessWeight;
    private int seedCount;
    private State state;
    
    public void setSeedCount(int count){this.seedCount = count;}
    
    public int getSeedCount(){
        return seedCount;
    }

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    public Position getPosition() {return position;}

    public void setPosition(Position position) {this.position = position;}

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

    public double getReockWeight() {
        return reockWeight;
    }

    public void setReockWeight(double reockWeight) {
        this.reockWeight = reockWeight;
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
