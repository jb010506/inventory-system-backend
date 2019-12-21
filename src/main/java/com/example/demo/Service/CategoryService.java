package com.example.demo.Service;

import com.example.demo.Dao.CategoryDao;
import com.example.demo.Entity.Category;
import com.example.demo.Entity.Department;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;


    public JSONArray getAll(){
        return categoryDao.getAll();
    }

    public void addCategory(Category category){
        //categoryDao.add(category);
    }

    public void deleteCategory(int id){
        //categoryDao.delete(id);
    }

    public void editCategory(int id, Category category){
        //categoryDao.edit(id, category);
    }
}
