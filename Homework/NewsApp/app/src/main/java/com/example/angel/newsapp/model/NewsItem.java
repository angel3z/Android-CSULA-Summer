package com.example.angel.newsapp.model;



public class NewsItem {
    private String author;
    private String title;
    private String description;
    private String date;
    private String url;
    private String image;

    public NewsItem(String author, String title, String description, String date, String url, String image) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.date = date;
        this.url = url;
        this.image = image;
    }
// Added Getters and Setters for the Image URL
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
