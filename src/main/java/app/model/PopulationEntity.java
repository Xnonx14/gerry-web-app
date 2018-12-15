package app.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "POPULATION")
public class PopulationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "PRECINCT_ID")
    private Integer precinctId;

    @Column(name = "DATE")
    private String date;

    @Column(name = "POPULATION_COUNT")
    private Integer population;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrecinctId() {
        return precinctId;
    }

    public void setPrecinctId(Integer precinctId) {
        this.precinctId = precinctId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }
}
