package com.example.filmapi.models;

public class Video {
    public String url;
    public String name;
    public String site;

    public Video(String url, String name, String site) {
        this.url = url;
        this.name = name;
        this.site = site;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
