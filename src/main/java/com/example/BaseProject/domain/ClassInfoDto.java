package com.example.BaseProject.domain;

import java.util.Date;
import java.util.Objects;

public class ClassInfoDto {
    private Integer id;
    private int class_type_id;
    private String class_type_name;
    private String class_name;
    private int class_point_id;
    private String class_point_name;
    private int class_point_maximum;
    private int instructor_id;
    private String instructor_name;
    private int class_time_id;
    private String class_date;
    private String class_start_time;
    private String class_end_time;
    private String class_time;
    private String class_memo;
    private int reservation_cnt;
    private Date create_dt;
    private Date update_dt;
    private String updated;

    private boolean past;
    private Integer isReserved;
    private int reservation_id;

    public ClassInfoDto() {
    }

    public ClassInfoDto(Integer id, int class_type_id, String class_type_name, String class_name, int class_point_id, String class_point_name, int class_point_maximum, int instructor_id, String instructor_name, int class_time_id, String class_date, String class_start_time, String class_end_time, String class_time, String class_memo, int reservation_cnt, Date create_dt, Date update_dt, String updated) {
        this.id = id;
        this.class_type_id = class_type_id;
        this.class_type_name = class_type_name;
        this.class_name = class_name;
        this.class_point_id = class_point_id;
        this.class_point_name = class_point_name;
        this.class_point_maximum = class_point_maximum;
        this.instructor_id = instructor_id;
        this.instructor_name = instructor_name;
        this.class_time_id = class_time_id;
        this.class_date = class_date;
        this.class_start_time = class_start_time;
        this.class_end_time = class_end_time;
        this.class_time = class_time;
        this.class_memo = class_memo;
        this.reservation_cnt = reservation_cnt;
        this.create_dt = create_dt;
        this.update_dt = update_dt;
        this.updated = updated;
    }

    public String getClass_time() {
        return class_time;
    }

    public void setClass_time(String class_time) {
        this.class_time = class_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getClass_type_id() {
        return class_type_id;
    }

    public void setClass_type_id(int class_type_id) {
        this.class_type_id = class_type_id;
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

    public int getClass_point_id() {
        return class_point_id;
    }

    public void setClass_point_id(int class_point_id) {
        this.class_point_id = class_point_id;
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

    public int getInstructor_id() {
        return instructor_id;
    }

    public void setInstructor_id(int instructor_id) {
        this.instructor_id = instructor_id;
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

    public String getClass_memo() {
        return class_memo;
    }

    public void setClass_memo(String class_memo) {
        this.class_memo = class_memo;
    }

    public int getReservation_cnt() {
        return reservation_cnt;
    }

    public void setReservation_cnt(int reservation_cnt) {
        this.reservation_cnt = reservation_cnt;
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

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public boolean isPast() {
        return past;
    }

    public void setPast(boolean past) {
        this.past = past;
    }

    public Integer getIsReserved() {
        return isReserved;
    }

    public void setIsReserved(Integer isReserved) {
        this.isReserved = isReserved;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    @Override
    public String toString() {
        return "ClassInfoDto{" +
                "id=" + id +
                ", class_type_id=" + class_type_id +
                ", class_type_name='" + class_type_name + '\'' +
                ", class_name='" + class_name + '\'' +
                ", class_point_id=" + class_point_id +
                ", class_point_name='" + class_point_name + '\'' +
                ", class_point_maximum=" + class_point_maximum +
                ", instructor_id=" + instructor_id +
                ", instructor_name='" + instructor_name + '\'' +
                ", class_time_id=" + class_time_id +
                ", class_date='" + class_date + '\'' +
                ", class_start_time='" + class_start_time + '\'' +
                ", class_end_time='" + class_end_time + '\'' +
                ", class_time='" + class_time + '\'' +
                ", class_memo='" + class_memo + '\'' +
                ", reservation_cnt=" + reservation_cnt +
                ", create_dt=" + create_dt +
                ", update_dt=" + update_dt +
                ", updated='" + updated + '\'' +
                ", past=" + past +
                ", isReserved=" + isReserved +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassInfoDto classInfoDto = (ClassInfoDto) o;
        return Objects.equals(id, classInfoDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
