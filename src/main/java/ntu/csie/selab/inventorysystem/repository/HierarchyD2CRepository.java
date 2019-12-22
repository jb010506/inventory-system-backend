package ntu.csie.selab.inventorysystem.repository;

import ntu.csie.selab.inventorysystem.model.Department;
import ntu.csie.selab.inventorysystem.model.HierarchyD2C;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HierarchyD2CRepository extends CrudRepository<HierarchyD2C, Integer> {
    @Query(value = "SELECT h.category FROM HierarchyD2C h WHERE h.department = :department")
    List<Object> findByDepartment(@Param("department") Department department);
    @Query(value = "SELECT COUNT(h) FROM HierarchyD2C h WHERE h.department = :department")
    Integer countByDepartment(@Param("department") Department department);
}
