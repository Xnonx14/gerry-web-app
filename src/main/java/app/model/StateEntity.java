package app.model;

import javax.persistence.*;

@Entity
@Table(name = "STATE")
public class StateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "SHORT_NAME")
    private String shortName;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CONSTITUTION_TEXT")
    private String constitutionText;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConstitutionText() {
        return constitutionText;
    }

    public void setConstitutionText(String constitutionText) {
        this.constitutionText = constitutionText;
    }
}
