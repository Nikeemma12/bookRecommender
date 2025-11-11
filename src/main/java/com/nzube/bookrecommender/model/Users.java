package com.nzube.bookrecommender.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private String password;
    private String username;
    private String role;
    private String mainGenre;

    @ManyToMany
    @JoinTable(
            name= "users_book_clubs",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_club_id")
    )
    private Set<BookClub> bookClub = new HashSet<>();


    @ManyToMany
    @JoinTable(name = "Read_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> readBooks = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "Liked_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> likedBooks = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "Book_ to_ read",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> watchListBooks = new HashSet<>();

    @ElementCollection
    @CollectionTable(name="Users Genres", joinColumns = @JoinColumn(name="user_id"))
    @Column(name="genre")
    private Set<String> otherGenres = new HashSet<>();


    public Users(String name, String email, String password, String username, String role, String mainGenre) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = role;
        this.mainGenre = mainGenre;
    }

    public Users() {

    }




    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Users user = (Users) obj;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
