package com.example.BaseProject.domain;

import java.util.Date;
import java.util.Objects;

public class NoticeDto {
    private Integer id;
    private String title;
    private String content;
    private int hit;
    private int display_flag;
    private Date create_dt;
    private Date update_dt;

    public NoticeDto() {}

    public NoticeDto(Integer id, String title, String content, int hit, int display_flag, Date create_dt, Date update_dt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.hit = hit;
        this.display_flag = display_flag;
        this.create_dt = create_dt;
        this.update_dt = update_dt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
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

    public int getDisplay_flag() {
        return display_flag;
    }

    public void setDisplay_flag(int display_flag) {
        this.display_flag = display_flag;
    }

    @Override
    public String toString() {
        return "NoticeDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", hit=" + hit +
                ", display_flag=" + display_flag +
                ", create_dt=" + create_dt +
                ", update_dt=" + update_dt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoticeDto noticeDto = (NoticeDto) o;
        return Objects.equals(id, noticeDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
