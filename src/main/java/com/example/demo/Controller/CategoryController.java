package com.example.demo.Controller;

import com.example.demo.Service.CategoryService;
import com.example.demo.Service.DepartmentService;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "/*")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/department/{category}", method = RequestMethod.GET)
    public @ResponseBody JSONArray getAllCategory(@PathVariable String category){

        JSONArray jsonArray = categoryService.getAll();
        System.out.println(jsonArray);
        System.out.println("SuccessGetAll categories");
        return jsonArray;
    }

}
