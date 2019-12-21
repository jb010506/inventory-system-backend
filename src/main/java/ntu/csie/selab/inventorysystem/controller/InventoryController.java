package ntu.csie.selab.inventorysystem.controller;

import ntu.csie.selab.inventorysystem.exception.UnprocessableEntityException;
import ntu.csie.selab.inventorysystem.model.Department;
import ntu.csie.selab.inventorysystem.service.InventoryService;
import ntu.csie.selab.inventorysystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    InventoryService inventoryService;
    @Autowired
    UserService userService;

    @GetMapping(value = "/departments", produces = "application/json")
    public List<Map<String, Object>> viewDepartmentList(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token
    ) {
        userService.isLogin(new Integer(uid), token);
        return inventoryService.departmentList();
    }

    @GetMapping(value = "/departments/{id}", produces = "application/json")
    public Department viewDepartment(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "id") Integer did
    ) {
        userService.isLogin(new Integer(uid), token);
        return inventoryService.departmentById(did);
    }

    @PostMapping(value = "/departments", produces = "application/json")
    public Department addDepartment(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @Valid @RequestBody Department department,
            BindingResult validation
    ) {
        userService.isLogin(new Integer(uid), token);
        if (validation.getFieldErrorCount() > 0)
            throw new UnprocessableEntityException(String.format("Invalid field value: %s",
                    validation.getFieldErrors().get(0).getField()));
        return inventoryService.departmentInsert(department);
    }

    @PatchMapping(value = "/departments/{id}", produces = "application/json")
    public Department editDepartment(
            @CookieValue(name = "uid") String uid,
            @CookieValue(name = "token") String token,
            @PathVariable(name = "id") Integer did,
            @Valid @RequestBody Department department,
            BindingResult validation
    ) {
        userService.isLogin(new Integer(uid), token);
        if (validation.getFieldErrorCount() > 0)
            throw new UnprocessableEntityException(String.format("Invalid field value: %s",
                    validation.getFieldErrors().get(0).getField()));
        department.setId(did);
        return inventoryService.departmentUpdate(department);
    }
}
