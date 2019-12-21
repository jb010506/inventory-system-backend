package com.example.demo.Dao;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HierarchyDao<T> {

    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public void add(T entity);
    public void edit(int id, T entity);
    public void delete(int id);
    public JSONObject get(int id);
    public JSONArray getAll();
}
