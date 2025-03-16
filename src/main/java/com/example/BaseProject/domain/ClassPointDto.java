package com.example.BaseProject.domain;

import java.util.Objects;

public class ClassPointDto {
    private Integer id;
    private String class_point_name;
    private int class_point_maximum;

    public ClassPointDto() {}

    public ClassPointDto(Integer id, String class_point_name, int class_point_maximum) {
        this.id = id;
        this.class_point_name = class_point_name;
        this.class_point_maximum = class_point_maximum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClass_point_name() {
        return class_point_name;
    }

    public void setClass_point_name(String class_point_name) {
        this.class_point_name = class_point_name;
    }

    public int getClass_point_maximum() {
        return class_point_maximum;
    }

    public void setClass_point_maximum(int class_point_maximum) {
        this.class_point_maximum = class_point_maximum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassPointDto that = (ClassPointDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClassPointDto{" +
                "id=" + id +
                ", class_point_name='" + class_point_name + '\'' +
                ", class_point_maximum=" + class_point_maximum +
                '}';
    }
}
