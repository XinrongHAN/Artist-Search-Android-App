package com.example.artsyapp.SectionedRecView;

public class Favorite {

    private String name;
    private String nation;
    private String year;
    private String id;

    public Favorite(String name, String nation, String year,String id) {
        this.name = name;
        this.nation = nation;
        this.year = year;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "name='" + name + '\'' +
                ", nation='" + nation + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
