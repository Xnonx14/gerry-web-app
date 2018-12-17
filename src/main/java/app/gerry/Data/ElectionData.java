package app.gerry.Data;

import app.gerry.Constants.Party;
import app.model.PartyRepresentative;

import java.util.HashMap;
import java.util.Map;

public class ElectionData {

    public Map<PartyRepresentative, Integer> representativeVotes;
    public Map<PartyRepresentative, Party> representativePartyMap;

    public ElectionData(){
        representativeVotes = new HashMap<>();
        representativePartyMap = new HashMap<>();
    }

    public Map<PartyRepresentative, Integer> getRepresentativeVotes() {
        return representativeVotes;
    }

    public void addRepresentativeVotes(PartyRepresentative rep, Integer voteCount){
        representativeVotes.put(rep, voteCount);
    }

    public Map<PartyRepresentative, Party> getRepresentativePartyMap() {
        return representativePartyMap;
    }

    public void addRepresentativeParty(PartyRepresentative rep, Party party){
        representativePartyMap.put(rep, party);
    }

}
