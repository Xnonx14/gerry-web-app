package app.repository;

import app.model.PartyRepresentative;
import org.springframework.data.repository.CrudRepository;

public interface RepresentativeRepository extends CrudRepository<PartyRepresentative, Long> {

    PartyRepresentative findById(Integer id);
}
