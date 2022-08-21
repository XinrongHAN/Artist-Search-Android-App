package com.example.artsyapp;

public class Category {
    private String name;
    private String img;
    private String des;

    public Category(String name, String img, String des) {
        this.name = name;
        this.img = img;
        this.des = des;
    }

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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", des='" + des + '\'' +
                '}';
    }
}


