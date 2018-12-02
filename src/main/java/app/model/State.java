package app.model;

import javax.persistence.*;

@Entity
@Table(name = "STATE")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "SHORT_NAME")
    private String shortName;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CONSTITUTION_TEXT")
    private String constitutionText;



}
