package app.model;

import javax.persistence.*;

@Entity
@Table(name = "PRECINCT")
public class Precinct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "STATE_ID")
    private Integer stateId;

    @Column(name = "COUNTY_ID")
    private Integer countyId;

    @Column(name = "NAME")
    private Integer name;

    @Column(name = "BOUNDARY_DATA")
    private String boundaryData;

    @Column(name = "CENTER_POINT")
    private String centerPoint;
}
