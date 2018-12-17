package app.gerry.Geography;

import app.gerry.Constants.Party;
import app.gerry.Data.ElectionData;
import app.gerry.Data.GeometricData;
import app.gerry.Constants.PoliticalSubdivision;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;

import java.util.*;

public class Chunk {
    private int id;
    private String name;
    private List<Precinct> precincts;
    private Set<Chunk> adjacentChunks;
    private ElectionData cummElectionData;
    private Map<Party, Integer> cummWastedVotes;
    private int totalVotes;
    private Geometry cummGeometricData;
    private int cummPopulation;
    private PoliticalSubdivision subdivision;
    private boolean isFinalized;
    private District parentDistrict;
    private int parentDistrictID;

    public int getParentDistrictID() {
        return getPrecincts().get(0).getParentDistrictID();
    }

    public void setParentDistrictID(int parentID) {
        this.parentDistrictID = parentID;
    }
    
    private boolean isBorderChunk;

    /**
     * Chunk of one precinct
     * @param precinct
     */
    public Chunk(Precinct precinct) {
        id = precinct.getId();
        precincts = new ArrayList<>();
        precincts.add(precinct);
        subdivision = PoliticalSubdivision.PRECINCT;
        id = precinct.getId();
        cummPopulation = precinct.getPopulation();
        cummGeometricData = precinct.getBoundary();
        cummElectionData = precinct.getElectionData();
        cummWastedVotes = precinct.getWastedVotesMap();
        totalVotes = precinct.getTotalVotes();
    }

    public Map<Party, Integer> getCummWastedVotes() {
        return cummWastedVotes;
    }

    /**
     * Chunk of many precincts eg. county
     * @param precincts
     */
    public Chunk(List<Precinct> precincts) {
        precincts.addAll(precincts);
        subdivision = PoliticalSubdivision.COUNTY;
        //TODO: assign county id to chunk id
        //TODO: fill in rest of fields
    }

    //I don't think this would work logically
    /*public Set<DistrictEntity> getAdjacentDistricts(){
        return null;
    }*/
    public District getDistrict(){
        return parentDistrict;
    }

    public Set<Chunk> getAdjacentChunks(){
        return adjacentChunks;
    }

    public void printChunk(){
        System.out.println(this.getId());
    }
    
    public void populateChunk(Precinct p){
        precincts.add(p);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Precinct> getPrecincts() {
        return precincts;
    }

    public void setPrecincts(List<Precinct> precincts) {
        this.precincts = precincts;
    }

    public void setAdjacentChunks(Set<Chunk> adjacentChunks) {
        this.adjacentChunks = adjacentChunks;
    }

    public ElectionData getCummElectionData() {
        return cummElectionData;
    }

    public void setCummElectionData(ElectionData cummElectionData) {
        this.cummElectionData = cummElectionData;
    }

    public Geometry getCummGeometricData() {
        return cummGeometricData;
    }

    public void setCummGeometricData(Geometry cummGeometricData) {
        this.cummGeometricData = cummGeometricData;
    }

    public int getCummPopulation() {
        return cummPopulation;
    }

    public void setCummPopulation(int cummPopulation) {
        this.cummPopulation = cummPopulation;
    }

    public PoliticalSubdivision getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(PoliticalSubdivision subdivision) {
        this.subdivision = subdivision;
    }

    public boolean isFinalized() {
        return isFinalized;
    }

    public void setFinalized(boolean finalized) {
        isFinalized = finalized;
    }

    public District getParentDistrict() {
        return parentDistrict;
    }

    public void setParentDistrict(District parentDistrict) {
        this.parentDistrict = parentDistrict;
    }

    public int getTotalVotes(){
        return totalVotes;
    }

    public boolean isBorderChunk() {
        return isBorderChunk;
    }

    public void setBorderChunk(boolean borderChunk) {
        isBorderChunk = borderChunk;
    }
}