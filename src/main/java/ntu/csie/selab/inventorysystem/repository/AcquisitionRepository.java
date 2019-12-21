package ntu.csie.selab.inventorysystem.repository;

import ntu.csie.selab.inventorysystem.model.Acquisition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcquisitionRepository extends CrudRepository<Acquisition, Integer> {}
