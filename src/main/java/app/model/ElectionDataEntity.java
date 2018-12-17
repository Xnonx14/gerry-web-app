package app.model;

import javax.persistence.*;
import java.sql.Date;

import app.gerry.Constants.Party;
import app.gerry.Constants.Position;

@Entity
@Table(name = "ELECTION_DATA")
public class ElectionDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "DATE")
    private Date date;

    @Column(name = "REPRESENTATIVE_ID")
    private Integer representativeId;

    @Column(name = "PRECINCT_ID")
    private Integer precinctId;

    @Column(name = "PARTY")
    @Enumerated(EnumType.STRING)
    private Party party;

    @Column(name = "VOTE_COUNT")
    private Integer voteCount;

    @Column(name = "POSITION")
    @Enumerated(EnumType.STRING)
    private Position position;

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Integer getRepresentativeId() {
        return representativeId;
    }

    public Integer getPrecinctId() {
        return precinctId;
    }

    public Party getParty() {
        return party;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public Position getPosition() {
        return position;
    }
}
