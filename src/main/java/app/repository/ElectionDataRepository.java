package app.repository;

import app.gerry.Constants.Position;
import app.model.ElectionData;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;

public interface ElectionDataRepository extends CrudRepository<ElectionData, Long> {
    ElectionData findbyDyDatePositionAndPrecinctId(Date date, int precinctId, Position position);
}
