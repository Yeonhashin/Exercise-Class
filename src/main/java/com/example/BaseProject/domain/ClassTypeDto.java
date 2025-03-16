package com.example.BaseProject.domain;

import java.util.Objects;

public class ClassTypeDto {
    private Integer id;
    private String class_type_name;
    private String class_name;
    private int class_level;

    public ClassTypeDto() {}

    public ClassTypeDto(Integer id, String class_type_name, String class_name, int class_level) {
        this.id = id;
        this.class_type_name = class_type_name;
        this.class_name = class_name;
        this.class_level = class_level;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClass_type_name() {
        return class_type_name;
    }

    public void setClass_type_name(String class_type_name) {
        this.class_type_name = class_type_name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public int getClass_level() {
        return class_level;
    }

    public void setClass_level(int class_level) {
        this.class_level = class_level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassTypeDto that = (ClassTypeDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClassTypeDto{" +
                "id=" + id +
                ", class_type_name='" + class_type_name + '\'' +
                ", class_name='" + class_name + '\'' +
                ", class_level=" + class_level +
                '}';
    }
}
