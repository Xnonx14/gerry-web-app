package app.gerry.Geography;

import app.gerry.Constants.Party;
import app.gerry.Constants.Position;
import app.gerry.Data.Boundary;
import app.gerry.Data.ElectionData;
import app.gerry.Data.YearData;
import app.gerry.Constants.PoliticalSubdivision;
import app.model.PartyRepresentative;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.WKTReader;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Precinct {
    private int id;
    private int parentDistrictID;

    public int getParentDistrictID() {
        return parentDistrictID;
    }
    
    public void setParentDistrictID(int id) {
        parentDistrictID = id;
    }
    
    private Set<Precinct> adjacentPrecincts;
    private Map<String, YearData> yearData;
    private int population;
    private Geometry boundary;
    private boolean isBorderPrecinct;
    private Map<PoliticalSubdivision, String> restrictionData;
    private Set<Boundary> borderingLandmark;
    private ElectionData electionData;

    public Precinct(Builder builder) {
        this.id = builder.id;
        this.boundary = builder.boundary;
        this.adjacentPrecincts = builder.adjacentPrecincts;
        this.parentDistrictID = builder.parentDistrictID;
        this.electionData = new ElectionData();
    }

    public District getRandomAdjacentDistrict(){
        return null;
    }

    public Map<Party, Integer> getWastedVotesMap(){
        int max = 0;
        Party winner = null;
        int total = 0;
        Map<Party, Integer> wastedVotesMap = new HashMap<>();
        for (PartyRepresentative r: electionData.getRepresentativeVotes().keySet()){
            Party party = electionData.getRepresentativePartyMap().get(r);
            int votes = electionData.getRepresentativeVotes().get(r);
            if (votes > max) {
                max = votes;
                winner = party;
            }
            else{
                if (wastedVotesMap.containsKey(party))
                    wastedVotesMap.put(party, wastedVotesMap.get(party)+ votes);
                else
                    wastedVotesMap.put(party, votes);
            }
            total += votes;
        }
        if (winner != null) {
            if (wastedVotesMap.containsKey(winner))
                wastedVotesMap.put(winner, wastedVotesMap.get(winner) + (total / 2 + 1));
            else
                wastedVotesMap.put(winner, total / 2 + 1);
        }
        return wastedVotesMap;
    }

    public int getTotalVotes(){
        int totalVotes = 0;
        for (PartyRepresentative r: electionData.getRepresentativeVotes().keySet()) {
            Party party = electionData.getRepresentativePartyMap().get(r);
            totalVotes += electionData.getRepresentativeVotes().get(r);
        }
        return totalVotes;
    }

    public String getSubdivisionName(PoliticalSubdivision ps){
        return null;
    }

    public static class Builder {
        private int id;
        private int parentDistrictID;
        private Set<Precinct> adjacentPrecincts;
        private Map<String, YearData> yearData;
        private int population;
        private Geometry boundary;
        private boolean isBorderPrecinct;
        private Map<PoliticalSubdivision, String> restrictionData;
        private Set<Boundary> borderingLandmark;
        private Map<Integer, ElectionData> electionData;

        public Builder() {

        }

        public Builder withId(int id) {
            this.id = id;
            return this;
        }
        
        public Builder withDistrictID(int id) {
            this.parentDistrictID = id;
            return this;
        }

        public Builder withBoundary(String boundaryData) {
            WKTReader reader = new WKTReader();
            try {
                Geometry polygon = reader.read(boundaryData);
                this.boundary = polygon;
            }
            catch(Exception e) {
                System.out.println("This precinct is missing boundary data: " + this.id);
            }
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

    public Geometry getBoundary() {
        return boundary;
    }

    public void setBoundary(Geometry boundary) {
        this.boundary = boundary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
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

    public void setBorderingLandmark(Set<Boundary> borderingLandmark) {
        this.borderingLandmark = borderingLandmark;
    }

    public ElectionData getElectionData() {
        return electionData;
    }

    public void setElectionData(ElectionData electionData) {
        this.electionData = electionData;
    }
}
