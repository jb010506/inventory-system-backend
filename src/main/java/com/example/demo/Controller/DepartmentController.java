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
        return jsonArray;
    }

    @RequestMapping(value = "/department/{id}", method = RequestMethod.GET)
    public @ResponseBody
    JSONObject getDepartment(@PathVariable("id") Integer id){
        JSONObject jsonObject = departmentService.getDepartment(id);
        System.out.println(jsonObject);
        return jsonObject;
    }

    @RequestMapping(value = "/add/department", method = RequestMethod.POST)
    public @ResponseBody
    Department addDepartment (@RequestBody Department department) {
        departmentService.addDepartment(department);
        System.out.println("SuccessAdd");
        return department;
    }

    @RequestMapping(value = {"/delete/department"}, method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteDepartment(@RequestParam("id") int id){
        departmentService.deleteDepartment(id);
        System.out.println("SuccessDelete");
    }

    @RequestMapping(value = "/edit/department", method = RequestMethod.PUT)
    public @ResponseBody
    void editDepartment(@RequestParam("id") int id, @RequestBody Department department){
        departmentService.editDepartment(id, department);
        System.out.println("SuccessEdit");
    }

}
