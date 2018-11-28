package app.classes.Data;

import app.classes.Enum.Party;
import app.classes.Enum.electionType;

import java.util.Map;

public class ElectionData {
    public electionType type;
    public Map<Party, Integer> partyVotes;
}
