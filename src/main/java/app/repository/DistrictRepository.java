package app.repository;

import app.model.DistrictEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DistrictRepository extends CrudRepository<DistrictEntity, Long>{
    List<DistrictEntity> findByStateId(Integer stateId);
}
