package com.example.BaseProject.domain;

import java.util.Date;
import java.util.Objects;

public class UserDto {
    private Integer id;
    private String email;
    private String name;
    private int password;
    private String delete_fg;
    private Date last_login_dt;
    private Date create_dt;
    private Date update_dt;

    public UserDto() {}

    public UserDto(Integer id, String email, String name, int password, String delete_fg, Date last_login_dt, Date create_dt, Date update_dt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.delete_fg = delete_fg;
        this.last_login_dt = last_login_dt;
        this.create_dt = create_dt;
        this.update_dt = update_dt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public String getDelete_fg() {
        return delete_fg;
    }

    public void setDelete_fg(String delete_fg) {
        this.delete_fg = delete_fg;
    }

    public Date getLast_login_dt() {
        return last_login_dt;
    }

    public void setLast_login_dt(Date last_login_dt) {
        this.last_login_dt = last_login_dt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password=" + password +
                ", delete_fg='" + delete_fg + '\'' +
                ", last_login_dt=" + last_login_dt +
                ", create_dt=" + create_dt +
                ", update_dt=" + update_dt +
                '}';
    }
}
