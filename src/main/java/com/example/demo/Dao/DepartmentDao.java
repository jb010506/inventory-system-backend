package com.example.demo.Dao;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class DepartmentDao implements  HierarchyDao {

    public JSONArray createDepartment(){
        JSONArray res = new JSONArray();
        System.out.println("createDepartment");

        JSONObject dep1 = new JSONObject();
        JSONObject dep2 = new JSONObject();
        dep1.put("ID","1");
        dep1.put("name","Vehicle");
        dep2.put("ID", "2");
        dep2.put("name", "Book");
        res.add(dep1);
        res.add(dep2);
        return res;
    }

    @Override
    public void add() {

    }

    @Override
    public void edit() {

    }

    @Override
    public void delete() {

    }

    @Override
    public JSONObject get() {
        return null;
    }

    @Override
    public JSONArray getAll() {
        return null;
    }
}
