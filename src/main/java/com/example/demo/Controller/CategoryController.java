package com.example.demo.Controller;

import com.example.demo.Service.CategoryService;
import com.example.demo.Service.DepartmentService;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/department/{category}", method = RequestMethod.GET)
    public
    JSONArray getAllCategory(){
        JSONArray jsonArray = categoryService.getAllCategories();
        System.out.println(jsonArray);
        System.out.println("SuccessGetAll categories");
        return jsonArray;
    }

}
