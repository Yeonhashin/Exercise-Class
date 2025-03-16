package com.example.BaseProject.domain;

import java.util.Objects;

public class InstructorDto {
    private Integer id;
    private int class_point_id;
    private String instructor_name;

    public InstructorDto(){
    }

    public InstructorDto(Integer id, int class_point_id, String instructor_name) {
        this.id = id;
        this.class_point_id = class_point_id;
        this.instructor_name = instructor_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getClass_point_id() {
        return class_point_id;
    }

    public void setClass_point_id(int class_point_id) {
        this.class_point_id = class_point_id;
    }

    public String getInstructor_name() {
        return instructor_name;
    }

    public void setInstructor_name(String instructor_name) {
        this.instructor_name = instructor_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstructorDto that = (InstructorDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstructorDto{" +
                "id=" + id +
                ", class_point_id=" + class_point_id +
                ", instructor_name='" + instructor_name + '\'' +
                '}';
    }
}

