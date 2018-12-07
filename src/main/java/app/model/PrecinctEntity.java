package app.model;

import javax.persistence.*;

@Entity
@Table(name = "PRECINCT")
public class PrecinctEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DISTRICT_ID")
    private Integer districtId;

    @Column(name = "COUNTY_ID")
    private Integer countyId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "BOUNDARY_DATA")
    private String boundaryData;

    @Column(name = "CENTER_POINT")
    private String centerPoint;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBoundaryData() {return boundaryData;
    }

    public void setBoundaryData(String boundaryData) {
        this.boundaryData = boundaryData;
    }

    public String getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(String centerPoint) {
        this.centerPoint = centerPoint;
    }
}
