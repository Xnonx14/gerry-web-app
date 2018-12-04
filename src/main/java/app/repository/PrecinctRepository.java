package app.repository;

import app.model.PrecinctEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PrecinctRepository extends CrudRepository<PrecinctEntity, Long> {
    List<PrecinctEntity> findByDistrictId(Integer districtId);
}
