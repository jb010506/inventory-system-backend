package com.example.demo.Controller;

import com.example.demo.Entity.Department;
import com.example.demo.Service.DepartmentService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /*
    @RequestMapping(value = "/department", method = RequestMethod.GET, produces = "application/json")
    public JSONArray firstPage() {

        return departmentService.create();
    }
     */
    @RequestMapping(value = "/department", method = RequestMethod.GET)
    public @ResponseBody
    JSONArray getAllDepartment(){
        JSONArray jsonArray = departmentService.getAllDepartments();
        System.out.println(jsonArray);
        System.out.println("SuccessGetAll");
        return jsonArray;
    }

    @RequestMapping(value = "/department/{id}", method = RequestMethod.GET)
    public @ResponseBody
    JSONObject getDepartment(@PathVariable("id") Integer id){
        JSONObject jsonObject = departmentService.getDepartment(id);
        System.out.println(jsonObject);
        System.out.println("SuccessGetById");
        return jsonObject;
    }

    @RequestMapping(value = "/add/department", method = RequestMethod.POST)
    public @ResponseBody
    Department addDepartment (@RequestBody Department department) {
        departmentService.addDepartment(department);
        System.out.println("SuccessAdd");
        return department;
    }

    @RequestMapping(value = {"/delete/department/{id}"}, method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteDepartment(@PathVariable("id") int id){
        departmentService.deleteDepartment(id);
        System.out.println("SuccessDelete");
    }

    @RequestMapping(value = "/edit/department/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    void editDepartment(@PathVariable("id") int id, @RequestBody Department department){
        departmentService.editDepartment(id, department);
        System.out.println("SuccessEdit");
    }

}
