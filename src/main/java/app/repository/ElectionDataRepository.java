package app.repository;

import app.model.ElectionData;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;

public interface ElectionDataRepository extends CrudRepository<ElectionData, Long> {
}
