package com.example.demo;

import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    public JSONArray create(){
        return this.departmentDao.createDepartment();
    }
}
