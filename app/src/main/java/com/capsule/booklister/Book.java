package com.capsule.booklister;

public class Book {

    private String title;
    private String imageUrl;
    private String author;

    public Book(String title, String author, String imageUrl) {
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAuthor() {
        return author;
    }
}
