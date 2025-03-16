package com.example.BaseProject.domain;

import java.util.Date;
import java.util.Objects;

public class UserReservationDto {
    private Integer id;
    private Integer user_id;
    private Integer class_id;
    private Date create_dt;
    private Date update_dt;

    public UserReservationDto() {}
    public UserReservationDto(Integer user_id, Integer class_id, Date create_dt, Date update_dt) {}

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

    public UserReservationDto(Integer id, Integer user_id, Integer class_id, Date create_dt, Date update_dt) {
        this.id = id;
        this.user_id = user_id;
        this.class_id = class_id;
        this.create_dt = create_dt;
        this.update_dt = update_dt;
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
}
