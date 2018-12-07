package app.model;

import javax.persistence.*;
import java.sql.Date;

import app.gerry.Constants.Party;
import app.gerry.Constants.Position;

@Entity
@Table(name = "ELECTION_DATA")
public class ElectionData {

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
    private Party party;

    @Column(name = "VOTE_COUNT")
    private Integer voteCount;

    @Column(name = "POSITION")
    private Position position;
}
