package ntu.csie.selab.inventorysystem.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntu.csie.selab.inventorysystem.exception.ConflictException;
import ntu.csie.selab.inventorysystem.exception.NotFoundException;
import ntu.csie.selab.inventorysystem.exception.UnprocessableEntityException;
import ntu.csie.selab.inventorysystem.model.Category;
import ntu.csie.selab.inventorysystem.model.Department;
import ntu.csie.selab.inventorysystem.model.HierarchyC2Sc;
import ntu.csie.selab.inventorysystem.model.HierarchyD2C;
import ntu.csie.selab.inventorysystem.repository.CategoryRepository;
import ntu.csie.selab.inventorysystem.repository.DepartmentRepository;
import ntu.csie.selab.inventorysystem.repository.HierarchyC2ScRepository;
import ntu.csie.selab.inventorysystem.repository.HierarchyD2CRepository;
import ntu.csie.selab.inventorysystem.repository.InventoryRepository;

@Service
public class HierarchyService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    HierarchyC2ScRepository hierarchyC2ScRepository;
    @Autowired
    HierarchyD2CRepository hierarchyD2CRepository;
    @Autowired
    InventoryRepository inventoryRepository;

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
        if (!result.isPresent())
            throw new NotFoundException(String.format("No department found with id: %d.", id));
        return result.get();
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

    public void departmentDelete(Integer id) {
        if ((hierarchyD2CRepository.countByDepartment(new Department(id)) > 0) ||
                (inventoryRepository.countByDepartment(new Department(id)) > 0))
            throw new ConflictException("Cannot delete a department which is not empty.");
        departmentRepository.deleteById(id);
    }

    public List<Map<String, Object>> categoryList(Integer did) {
        List<Map<String, Object>> list = new ArrayList<>();
        Department department = new Department(did);
        for (Object object : hierarchyD2CRepository.findByDepartment(department)) {
            Category category = (Category) object;
            Map<String, Object> map = new HashMap<>();
            map.put("id", category.getId());
            map.put("name", category.getName());
            list.add(map);
        }
        return list;
    }

    public Category categoryById(Integer id) {
        Optional<Category> result = categoryRepository.findById(id);
        if (!result.isPresent())
            throw new NotFoundException(String.format("No category found with id: %d.", id));
        return result.get();
    }

    public Category categoryInsert(Integer did, Category category) {
        if (category.getId() != null)
            throw new UnprocessableEntityException("Should not contain database-generated field: 'id'.");
        if (categoryRepository.CountByName(category.getName()) > 0)
            throw new UnprocessableEntityException("Duplicated unique field: 'name'.");
        if (categoryRepository.CountByTag(category.getTag()) > 0)
            throw new UnprocessableEntityException("Duplicated unique field: 'tag'.");
        category.setSub(false);
        HierarchyD2C hierarchyD2C = new HierarchyD2C();
        hierarchyD2C.setDepartment(new Department(did));
        hierarchyD2C.setCategory(categoryRepository.save(category));
        return hierarchyD2CRepository.save(hierarchyD2C).getCategory();
    }

    public Category categoryUpdate(Category category) {
        if (category.getId() == null)
            throw new UnprocessableEntityException("Should contained primary key field: 'id'.");
        if (categoryRepository.CountByName(category.getName(), category.getId()) > 0)
            throw new UnprocessableEntityException("Duplicated unique field: 'name'.");
        if (categoryRepository.CountByTag(category.getTag(), category.getId()) > 0)
            throw new UnprocessableEntityException("Duplicated unique field: 'tag'.");
        return categoryRepository.save(category);
    }

    public void categoryDelete(Integer id) {
        if ((hierarchyC2ScRepository.countByCategory(new Category(id)) > 0) ||
                (inventoryRepository.countByCategory(new Category(id)) > 0))
            throw new ConflictException("Cannot delete a category which is not empty.");
        int rid = hierarchyD2CRepository.findIDByCID(new Category(id));
        hierarchyD2CRepository.deleteById(rid);
        categoryRepository.deleteById(id);
    }

    public List<Map<String, Object>> subcategoryList(Integer cid) {
        List<Map<String, Object>> list = new ArrayList<>();
        Category category = new Category(cid);
        for (Object object : hierarchyC2ScRepository.findByCategory(category)) {
            Category subcategory = (Category) object;
            Map<String, Object> map = new HashMap<>();
            map.put("id", subcategory.getId());
            map.put("name", subcategory.getName());
            list.add(map);
        }
        return list;
    }

    public Category subcategoryInsert(Integer cid, Category subcategory) {
        if (subcategory.getId() != null)
            throw new UnprocessableEntityException("Should not contain database-generated field: 'id'.");
        if (categoryRepository.CountByName(subcategory.getName()) > 0)
            throw new UnprocessableEntityException("Duplicated unique field: 'name'.");
        if (categoryRepository.CountByTag(subcategory.getTag()) > 0)
            throw new UnprocessableEntityException("Duplicated unique field: 'tag'.");
        subcategory.setSub(true);
        HierarchyC2Sc hierarchyC2Sc = new HierarchyC2Sc();
        hierarchyC2Sc.setCategory(new Category(cid));
        hierarchyC2Sc.setSubcategory(categoryRepository.save(subcategory));
        return hierarchyC2ScRepository.save(hierarchyC2Sc).getSubcategory();
    }
}
