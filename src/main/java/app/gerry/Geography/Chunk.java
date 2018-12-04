package app.gerry.Geography;

import app.gerry.Data.ElectionData;
import app.gerry.Data.GeometricData;
import app.gerry.Enum.PoliticalSubdivision;

import java.util.List;
import java.util.Set;

public class Chunk {
    public int id;
    public String name;
    public Set<Precinct> precincts;
    public Set<Chunk> adjacentChunks;
    public ElectionData cummElectionData;
    public GeometricData cummGeometricData;
    public int cummPopulation;
    public PoliticalSubdivision subdivision;
    public boolean isFinalized;
    public District parentDistrict;
    public boolean isBorderChunk;

    //I don't think this would work logically
    /*public Set<District> getAdjacentDistricts(){
        return null;
    }*/


    public District getDistrict(){
        return parentDistrict;
    }
    
    public Set<Chunk> getAdjacentChunks(){
        return adjacentChunks;
    }
    public void populateChunk(Precinct p){
        return;
    }
}
