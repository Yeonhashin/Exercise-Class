package com.example.BaseProject.domain;

import java.util.Objects;

public class ClassTimeDto {
    private Integer id;
    private String class_start_time;
    private String class_end_time;

    public ClassTimeDto() {}

    public ClassTimeDto(Integer id, String class_start_time, String class_end_time) {
        this.id = id;
        this.class_start_time = class_start_time;
        this.class_end_time = class_end_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClass_start_time() {
        return class_start_time;
    }

    public void setClass_start_time(String class_start_time) {
        this.class_start_time = class_start_time;
    }

    public String getClass_end_time() {
        return class_end_time;
    }

    public void setClass_end_time(String class_end_time) {
        this.class_end_time = class_end_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassTimeDto that = (ClassTimeDto) o;
        return Objects.equals(id, that.id) && Objects.equals(class_start_time, that.class_start_time) && Objects.equals(class_end_time, that.class_end_time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, class_start_time, class_end_time);
    }

    @Override
    public String toString() {
        return "ClassTimeDto{" +
                "id=" + id +
                ", class_start_time='" + class_start_time + '\'' +
                ", class_end_time='" + class_end_time + '\'' +
                '}';
    }
}
