package com.example.demo.Dao;

import com.example.demo.Entity.Category;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CategoryDao implements HierarchyDao<Category>{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private Category category;

    @Override
    public void add(Category entity) {

    }

    @Override
    public void edit(int id, Category entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public JSONObject get(int id) {
        return null;
    }

    @Override
    public JSONArray getAll() {
        System.out.println("start to get categories");
        String sql = "SELECT * FROM Category";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        JSONArray jsonArray = new JSONArray();
        for (Map<String, Object> row : rows)
        {
            jsonArray.add(row);
        }

        return jsonArray;
    }
}
