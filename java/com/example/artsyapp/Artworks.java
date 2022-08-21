package com.example.artsyapp;

public class Artworks {
    private String name;
    private String imageUrl;
    private String year;
    private String artworkid;

    public Artworks(String name, String imageUrl,String year,String artworkid) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.year = year;
        this.artworkid = artworkid;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getArtworkid() {
        return artworkid;
    }

    public void setArtworkid(String artworkid) {
        this.artworkid = artworkid;
    }

    @Override
    public String toString() {
        return "Artworks{" +
                "name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
