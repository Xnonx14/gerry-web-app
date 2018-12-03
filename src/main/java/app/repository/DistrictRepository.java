package app.repository;

import app.model.District;
import org.springframework.data.repository.CrudRepository;

public interface DistrictRepository extends CrudRepository<District, Long>{
    District findByStateId(Integer stateId);
}
