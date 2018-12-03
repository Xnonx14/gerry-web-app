package app.model;

import javax.persistence.*;

@Entity
@Table(name = "DISTRICT")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "STATE_ID")
    private Integer stateId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "BOUNDARY_DATA")
    private String boundaryData;
}
