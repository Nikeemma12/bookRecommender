package com.nzube.bookrecommender.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@Component
@Entity
public class BookClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String genre;

    @ManyToMany(mappedBy = "bookClub")
    @JsonIgnore
    private Set<Users> users = new HashSet<>();

    public BookClub(String name, String genre) {
        this.name = name;
        this.genre = genre;
    }

    public BookClub () {

    }

}
