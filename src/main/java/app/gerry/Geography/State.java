package app.gerry.Geography;

import app.gerry.Data.Representative;
import app.gerry.Enum.PoliticalSubdivision;

import java.util.Set;

public class State {
    public int id;
    public String name;
    public Set<District> districts;
    public  Set<Representative> representatives;
    public PoliticalSubdivision restriction;
    public int population;

    public Set<Precinct> getAllPrecincts(){
        return null;

    }
    public Set<String> getSubdivisionNames(){
        return null;
    }
    public District getRandomDistrict(){
        return null;
    }
    public Set<District> getSeedDistricts(int count){
        return null;
    }
}
