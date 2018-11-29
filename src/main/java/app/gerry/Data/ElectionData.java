package app.gerry.Data;

import app.gerry.Enum.Party;
import app.gerry.Enum.electionType;

import java.util.Map;

public class ElectionData {
    public electionType type;
    public Map<Party, Integer> partyVotes;
}
