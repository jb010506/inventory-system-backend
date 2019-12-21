package ntu.csie.selab.inventorysystem.service;

import ntu.csie.selab.inventorysystem.exception.UnprocessableEntityException;
import ntu.csie.selab.inventorysystem.model.Department;
import ntu.csie.selab.inventorysystem.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InventoryService {
    @Autowired
    DepartmentRepository departmentRepository;

    public List<Map<String, Object>> departmentList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Department department : departmentRepository.findAll()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", department.getId());
            map.put("name", department.getName());
            list.add(map);
        }
        return list;
    }

    public Department departmentById(Integer id) {
        Optional<Department> result = departmentRepository.findById(id);
        return result.orElse(null);
    }

    public Department departmentInsert(Department department) {
        if (department.getId() != null)
            throw new UnprocessableEntityException("Should not contain database-generated field: 'id'.");
        if (departmentRepository.CountByName(department.getName()) > 0)
            throw new UnprocessableEntityException("Duplicated unique field: 'name'.");
        if (departmentRepository.CountByTag(department.getTag()) > 0)
            throw new UnprocessableEntityException("Duplicated unique field: 'tag'.");
        return departmentRepository.save(department);
    }

    public Department departmentUpdate(Department department) {
        if (department.getId() == null)
            throw new UnprocessableEntityException("Should contained primary key field: 'id'.");
        if (departmentRepository.CountByName(department.getName(), department.getId()) > 0)
            throw new UnprocessableEntityException("Duplicated unique field: 'name'.");
        if (departmentRepository.CountByTag(department.getTag(), department.getId()) > 0)
            throw new UnprocessableEntityException("Duplicated unique field: 'tag'.");
        return departmentRepository.save(department);
    }
}
