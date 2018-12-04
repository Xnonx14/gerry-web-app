package app.model;

import javax.persistence.*;

@Entity
@Table(name = "DISTRICT")
public class DistrictEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "STATE_ID")
    private Integer stateId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "BOUNDARY_DATA")
    private String boundaryData;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBoundaryData() {
        return boundaryData;
    }

    public void setBoundaryData(String boundaryData) {
        this.boundaryData = boundaryData;
    }
}
