package app.gerry.Geography;

import app.gerry.Data.Boundary;
import app.gerry.Data.ElectionData;
import app.gerry.Data.YearData;
import app.gerry.Constants.PoliticalSubdivision;

import java.util.Map;
import java.util.Set;

public class Precinct {
    public int id;
    public Set<Precinct> adjacentPrecincts;
    public Map<String, YearData> yearData;
    public boolean isBorderPrecinct;
    public Map<PoliticalSubdivision, String> restrictionData;
    public Set<Boundary> borderingLandmark;
    public Map<Integer, ElectionData> electionData;

    public District getRandomAdjacentDistrict(){
        return null;
    }

    public String getSubdivisionName(PoliticalSubdivision ps){
        return null;
    }
}
