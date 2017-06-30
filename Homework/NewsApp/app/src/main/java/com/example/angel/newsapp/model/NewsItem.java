package com.example.angel.newsapp.model;



public class NewsItem {
    private String author;
    private String title;
    private String description;
    private String date;
    private String url;

    public NewsItem(String author, String title, String description, String date, String url) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.date = date;
        this.url = url;
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
