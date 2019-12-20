package com.example.demo.Dao;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public interface HierarchyDao {
    public void add();
    public void edit();
    public void delete();
    public JSONObject get();
    public JSONArray getAll();
}
