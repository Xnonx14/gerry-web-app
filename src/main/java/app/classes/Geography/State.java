package app.classes.Geography;

import app.classes.Data.Representative;
import app.classes.Enum.PoliticalSubdivision;

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
    public District getRandomDistrct(){
        return null;
    }
    public Set<District> getSeedDistricts(int count){
        return null;
    }
}
