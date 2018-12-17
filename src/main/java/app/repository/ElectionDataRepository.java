package app.repository;

import app.gerry.Constants.Position;
import app.gerry.Data.ElectionData;
import app.model.ElectionDataEntity;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ElectionDataRepository extends CrudRepository<ElectionDataEntity, Long>{

    List<ElectionDataEntity> findByPrecinctId(int precinctId);

    List<ElectionDataEntity> findByPrecinctIdAndDateAndPosition(int precinctId, Date date, Position position);

    default Map<Integer, ElectionDataEntity> precinctYearElectionDataMap(int precinctId, Date date, Position position){
        return findByPrecinctIdAndDateAndPosition(precinctId, date, position)
                .stream().collect(Collectors.toMap(ElectionDataEntity::getRepresentativeId, v -> v));
    }

}
