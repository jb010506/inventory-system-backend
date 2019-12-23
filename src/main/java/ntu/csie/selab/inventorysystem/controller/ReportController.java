package ntu.csie.selab.inventorysystem.controller;


import ntu.csie.selab.inventorysystem.model.Category;
import ntu.csie.selab.inventorysystem.model.Department;
import ntu.csie.selab.inventorysystem.model.Item;
import ntu.csie.selab.inventorysystem.repository.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "/*")
@RestController
public class ReportController {


    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    HierarchyC2ScRepository hierarchyC2ScRepository;
    @Autowired
    HierarchyD2CRepository hierarchyD2CRepository;
    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    ItemRepository itemRepository;

    @GetMapping(value = "/report", produces = "application/json")
    public String getReport() throws JSONException {

        JSONObject object_2D = new JSONObject();
        JSONArray jsonA = new JSONArray();
        for (Department department : departmentRepository.findAll()) {
            JSONObject jsonO = new JSONObject();
            jsonO.put("id", department.getId());
            jsonO.put("name", department.getName());
            jsonO.put("description", department.getDescription());
            jsonO.put("POSCode", department.getCode());
            jsonO.put("tag", department.getTag());
            jsonA.put(jsonO);
        }

        //System.out.println(jsonA);
        object_2D.put("DEPARTMENT",jsonA);

        jsonA = new JSONArray();
        for (Category category : categoryRepository.findAll()) {
            JSONObject jsonO = new JSONObject();
            jsonO.put("id", category.getId());
            jsonO.put("name", category.getName());
            jsonO.put("description", category.getDescription());
            jsonO.put("tag", category.getTag());
            jsonO.put("sub", category.getSub());
            jsonA.put(jsonO);
        }
        //System.out.println(jsonA);
        object_2D.put("CATEGORY",jsonA);

        jsonA = new JSONArray();
        for (Item item: itemRepository.findAll()) {
            JSONObject jsonO = new JSONObject();
            jsonO.put("id", item.getId());
            jsonO.put("description", item.getDescription());
            jsonO.put("quantity", item.getQuantity());
            jsonO.put("condition", item.getCondition());
            jsonO.put("price", item.getPrice());

            //jsonO.put("aid", item.getAcquisition().getId());
            jsonA.put(jsonO);
        }
        object_2D.put("ITEM",jsonA);
        return object_2D.toString();
        //return object;
    }

}
