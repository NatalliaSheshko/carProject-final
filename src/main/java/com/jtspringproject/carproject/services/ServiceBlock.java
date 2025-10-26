package com.jtspringproject.carproject.services;

public class ServiceBlock {
    private String title;
    private String description;
    private String details;
    private String imageUrl;

    public ServiceBlock(String title, String description, String details, String imageUrl) {
        this.title = title;
        this.description = description;
        this.details = details;
        this.imageUrl = imageUrl;
    }

    // геттеры
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDetails() { return details; }
    public String getImageUrl() { return imageUrl; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
