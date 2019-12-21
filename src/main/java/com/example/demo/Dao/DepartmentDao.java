package com.example.demo.Dao;

import com.example.demo.Entity.Department;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.RowSet;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.data.repository.init.ResourceReader.Type.JSON;


@Repository
public class DepartmentDao implements  HierarchyDao<Department> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void add(Department entity) {
        String sql = "INSERT INTO Department VALUES (?,?,?,?,?)";
        jdbcTemplate.update(sql, entity.getId(), entity.getName(), entity.getDescription(), entity.getPosCode(), entity.getTag());
    }

    @Override
    public void edit(int id, Department entity) {
        String sql = "UPDATE Department SET name = ?, description = ?, posCode = ?, tag = ? WHERE id = ?";
        jdbcTemplate.update(sql, entity.getName(), entity.getDescription(), entity.getPosCode(), entity.getTag(), id);
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Department WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public JSONObject get(int id) {
        String sql = "SELECT * FROM Department WHERE id = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, id);
        JSONObject jsonObject = null;
        if(rs.next()){
            jsonObject = new JSONObject();
            jsonObject.put("id", rs.getInt("id"));
            jsonObject.put("name", rs.getString("name"));
            jsonObject.put("description", rs.getString("description"));
            jsonObject.put("posCode", rs.getString("posCode"));
            jsonObject.put("tag", rs.getString("tag"));
        }

        return jsonObject;
    }

    @Override
    public JSONArray getAll() {
        String sql = "SELECT * FROM Department";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        JSONArray jsonArray = new JSONArray();
        for (Map<String, Object> row : rows)
        {
            jsonArray.add(row);
        }
        System.out.println("SuccessGetAll");
        return jsonArray;
    }
}
