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

    String posCode;

    String tag;

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

    public String getPosCode() {
        return posCode;
    }

    public void setPosCode(String posCode) {
        this.posCode = posCode;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
