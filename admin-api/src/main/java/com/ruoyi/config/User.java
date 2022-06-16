package com.ruoyi.config;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public User() {
    }

    public User(String name, String img) {
        this.name = name;
        this.img = img;
    }
}
