package com.scme.order.model;

/**
 * Created by lzxyy on 2016/5/8.
 */
public class Photoimage implements java.io.Serializable{

    private int id;
    private String subject;
    private String path;



    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
