package app.gerry.Geography;

import app.gerry.Data.Boundary;
import app.gerry.Data.ElectionData;
import app.gerry.Data.GeometricData;
import app.gerry.Data.YearData;
import app.gerry.Constants.PoliticalSubdivision;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.util.Map;
import java.util.Set;

public class Precinct {
    private int id;
    private Set<Precinct> adjacentPrecincts;
    private Map<String, YearData> yearData;
    private boolean isBorderPrecinct;
    private Map<PoliticalSubdivision, String> restrictionData;
    private Set<Boundary> borderingLandmark;
    private Map<Integer, ElectionData> electionData;
    private String boundaryData;
    private GeometricData geometricData;

    public Precinct(Builder builder){
        this.id = builder.id;
        this.adjacentPrecincts = builder.adjacentPrecincts;
        setGeometricData(builder.boundaryData);
    }

    public District getRandomAdjacentDistrict(){
        return null;
    }

    public String getSubdivisionName(PoliticalSubdivision ps){
        return null;
    }

    public static class Builder {
        private int id;
        private Set<Precinct> adjacentPrecincts;
        private Map<String, YearData> yearData;
        private boolean isBorderPrecinct;
        private Map<PoliticalSubdivision, String> restrictionData;
        private Set<Boundary> borderingLandmark;
        private Map<Integer, ElectionData> electionData;
        private String boundaryData;

        public Builder() {

        }

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withBoundaryData(String boundaryData){
            this.boundaryData = boundaryData;
            return this;
        }

        public Builder withAdjacentPrecincts(Set<Precinct> adjacentPrecincts) {
            this.adjacentPrecincts = adjacentPrecincts;
            return this;
        }

        public Precinct build() {
            return new Precinct(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Precinct> getAdjacentPrecincts() {
        return adjacentPrecincts;
    }

    public void setAdjacentPrecincts(Set<Precinct> adjacentPrecincts) {
        this.adjacentPrecincts = adjacentPrecincts;
    }

    public Map<String, YearData> getYearData() {
        return yearData;
    }

    public void setYearData(Map<String, YearData> yearData) {
        this.yearData = yearData;
    }

    public boolean isBorderPrecinct() {
        return isBorderPrecinct;
    }

    public void setBorderPrecinct(boolean borderPrecinct) {
        isBorderPrecinct = borderPrecinct;
    }

    public Map<PoliticalSubdivision, String> getRestrictionData() {
        return restrictionData;
    }

    public void setRestrictionData(Map<PoliticalSubdivision, String> restrictionData) {
        this.restrictionData = restrictionData;
    }

    public Set<Boundary> getBorderingLandmark() {
        return borderingLandmark;
    }

    public GeometricData getGeometricData() {
        return geometricData;
    }

    public void setGeometricData(String boundaryData){
        WKTReader wktReader = new WKTReader();
        try {
            if (boundaryData != null) {
                Geometry geom = wktReader.read(boundaryData);
                double area = geom.getArea();
                double perimeter = geom.getLength();
                Geometry convexHull = geom.convexHull();
                this.geometricData = new GeometricData(area, perimeter, convexHull, geom);
            }
        }
        catch (ParseException p){
        }
    }

    public void setBorderingLandmark(Set<Boundary> borderingLandmark) {
        this.borderingLandmark = borderingLandmark;
    }

    public Map<Integer, ElectionData> getElectionData() {
        return electionData;
    }

    public void setElectionData(Map<Integer, ElectionData> electionData) {
        this.electionData = electionData;
    }
}
