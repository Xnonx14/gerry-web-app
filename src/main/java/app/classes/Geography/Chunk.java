package app.classes.Geography;

import app.classes.Data.ElectionData;
import app.classes.Data.GeometricData;
import app.classes.Enum.PoliticalSubdivision;

import java.util.List;
import java.util.Set;

public class Chunk {
    public int id;
    public String name;
    public List<Precinct> precients;
    public List<Chunk> adjacentChunks;
    public ElectionData cummElectionData;
    public GeometricData cummGeometricData;
    public int cummPopulation;
    public PoliticalSubdivision subdivision;
    public boolean isFinalized;
    public District parentDistrict;
    public boolean isBorderChunk;

    public Set<District> getAdjacentDistricts(){
        return null;
    }
    public Set<Chunk> getAdjacentChunks(){
        return null;
    }
    public void populateChunk(Precinct p){
        return;
    }
}