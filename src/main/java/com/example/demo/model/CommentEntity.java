package com.example.demo.model;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "comment", schema = "java_db", catalog = "")
public class CommentEntity {
    private int id;
    private Integer concertId;
    private Integer userId;
    private byte[] image;
    private String text;
    private ConcertEntity concertByConcertId;
    private UsersEntity usersByUserId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "concertId", insertable = false, updatable = false)
    public Integer getConcertId() {
        return concertId;
    }

    public void setConcertId(Integer concertId) {
        this.concertId = concertId;
    }

    @Basic
    @Column(name = "userId", insertable = false, updatable = false)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "image")
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Basic
    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentEntity that = (CommentEntity) o;

        if (id != that.id) return false;
        if (concertId != null ? !concertId.equals(that.concertId) : that.concertId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (!Arrays.equals(image, that.image)) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (concertId != null ? concertId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(image);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }




}
