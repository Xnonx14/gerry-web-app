package app.model;

import javax.persistence.*;

@Entity
@Table(name = "PARTY_REPRESENTATIVE")
public class PartyRepresentative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
