package com.example.BaseProject.domain;

import java.util.Date;
import java.util.Objects;

public class UserReservationDto {
    private Integer id;
    private Integer user_id;
    private Integer class_id;
    private int cancel_flag;

    private Date create_dt;
    private Date update_dt;

    private String class_type_name;
    private String class_name;
    private String class_point_name;
    private String instructor_name;
    private int class_time_id;
    private String class_date;
    private String class_start_time;
    private String class_end_time;

    private boolean canCancel;

    public UserReservationDto() {}

    public UserReservationDto(Integer id, Integer user_id, Integer class_id, int cancel_flag, Date create_dt, Date update_dt, String class_type_name, String class_name, String class_point_name, String instructor_name, int class_time_id, String class_date, String class_start_time, String class_end_time, boolean canCancel) {
        this.id = id;
        this.user_id = user_id;
        this.class_id = class_id;
        this.cancel_flag = cancel_flag;
        this.create_dt = create_dt;
        this.update_dt = update_dt;
        this.class_type_name = class_type_name;
        this.class_name = class_name;
        this.class_point_name = class_point_name;
        this.instructor_name = instructor_name;
        this.class_time_id = class_time_id;
        this.class_date = class_date;
        this.class_start_time = class_start_time;
        this.class_end_time = class_end_time;
        this.canCancel = canCancel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getClass_id() {
        return class_id;
    }

    public void setClass_id(Integer class_id) {
        this.class_id = class_id;
    }

    public Integer getCancel_flag() {
        return cancel_flag;
    }

    public void setCancel_flag(int cancel_flag) {
        this.cancel_flag = cancel_flag;
    }

    public Date getCreate_dt() {
        return create_dt;
    }

    public void setCreate_dt(Date create_dt) {
        this.create_dt = create_dt;
    }

    public Date getUpdate_dt() {
        return update_dt;
    }

    public void setUpdate_dt(Date update_dt) {
        this.update_dt = update_dt;
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

    public String getClass_point_name() {
        return class_point_name;
    }

    public void setClass_point_name(String class_point_name) {
        this.class_point_name = class_point_name;
    }

    public String getInstructor_name() {
        return instructor_name;
    }

    public void setInstructor_name(String instructor_name) {
        this.instructor_name = instructor_name;
    }

    public int getClass_time_id() {
        return class_time_id;
    }

    public void setClass_time_id(int class_time_id) {
        this.class_time_id = class_time_id;
    }

    public String getClass_date() {
        return class_date;
    }

    public void setClass_date(String class_date) {
        this.class_date = class_date;
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

    public boolean isCanCancel() {
        return canCancel;
    }

    public void setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserReservationDto that = (UserReservationDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserReservationDto{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", class_id=" + class_id +
                ", cancel_flag=" + cancel_flag +
                ", create_dt=" + create_dt +
                ", update_dt=" + update_dt +
                ", class_type_name='" + class_type_name + '\'' +
                ", class_name='" + class_name + '\'' +
                ", class_point_name='" + class_point_name + '\'' +
                ", instructor_name='" + instructor_name + '\'' +
                ", class_time_id=" + class_time_id +
                ", class_date='" + class_date + '\'' +
                ", class_start_time='" + class_start_time + '\'' +
                ", class_end_time='" + class_end_time + '\'' +
                ", canCancel=" + canCancel +
                '}';
    }
}
