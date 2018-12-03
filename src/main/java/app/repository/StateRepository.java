package app.repository;

import app.model.StateEntity;
import org.springframework.data.repository.CrudRepository;

public interface StateRepository extends CrudRepository<StateEntity, Long> {
    StateEntity findByName(String name);
}
