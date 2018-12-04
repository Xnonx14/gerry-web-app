package app.gerry.Data;

import app.gerry.Constants.Party;
import app.gerry.Constants.electionType;

import java.util.Map;

public class ElectionData {
    public electionType type;
    public Map<Party, Integer> partyVotes;
}
