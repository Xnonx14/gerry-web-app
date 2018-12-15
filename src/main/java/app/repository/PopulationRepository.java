package app.repository;

import app.model.PopulationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PopulationRepository extends CrudRepository<PopulationEntity, Long> {
    List<PopulationEntity> findByPrecinctId(Integer precinctId);
}
