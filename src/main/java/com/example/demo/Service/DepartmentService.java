package com.example.demo.Service;

import com.example.demo.Dao.DepartmentDao;
import com.example.demo.Entity.Department;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    /*
    public JSONArray create(){
        return this.departmentDao.createDepartment();
    }
     */
    public JSONArray getAllDepartments(){
        return departmentDao.getAll();
    }

    public JSONObject getDepartment(int id){
        return departmentDao.get(id);
    }

    public void addDepartment(Department department){
        departmentDao.add(department);
    }

    public void deleteDepartment(int id){
        departmentDao.delete(id);
    }

    public void editDepartment(int id, Department department){
        departmentDao.edit(id, department);
    }
}
