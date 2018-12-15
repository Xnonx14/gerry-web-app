package app.repository;

import app.model.StateEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface StateRepository extends CrudRepository<StateEntity, Long> {
    StateEntity findByName(String name);
}
