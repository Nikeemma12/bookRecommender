package com.nzube.bookrecommender.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.Objects;



@Setter
@Getter
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String author;
    private String genre;
    private int likes;
    private String description;
    private String content_url;


    public Book(String title, String author, String genre, int likes, String description, String contentUrl) {
        this.genre = genre;
        this.author = author;
        this.title = title;
        this.likes = likes;
        this.description = description;
        content_url = contentUrl;
    }

    public Book(){
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }


}
