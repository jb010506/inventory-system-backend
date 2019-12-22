package com.example.demo.Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    int id;

    String name;

    String description;

    String posDepartmentCode;

    String uniqueTag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosDepartmentCode() {
        return posDepartmentCode;
    }

    public void setPosDepartmentCode(String posDepartmentCode) {
        this.posDepartmentCode = posDepartmentCode;
    }

    public String getUniqueTag() {
        return uniqueTag;
    }

    public void setUniqueTag(String uniqueTag) {
        this.uniqueTag = uniqueTag;
    }
}
