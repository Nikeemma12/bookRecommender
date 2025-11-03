package com.nzube.bookrecommender.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Book> getLikedBooks() {
        return likedBooks;
    }

    public void setLikedBooks(Set<Book> likedBooks) {
        this.likedBooks = likedBooks;
    }

    public Set<Book> getReadBooks() {
        return readBooks;
    }

    public void setReadBooks(Set<Book> readBooks) {
        this.readBooks = readBooks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMainGenre() {
        return mainGenre;
    }
    public void setMainGenre(String mainGenre){
        this.mainGenre = mainGenre;
    }

    public Set<String> getOtherGenre() {
        return otherGenres;
    }
    public void setOtherGenre(Set<String> otherGenre){
        this.otherGenres = otherGenre;
    }


    public Set<BookClub> getBookClub() {
        return bookClub;
    }

    public void setBookClub(Set<BookClub> bookClub) {
        this.bookClub = bookClub;
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

    public Set<Book> getWatchListBooks() {
        return watchListBooks;
    }

    public void setWatchListBooks(Set<Book> watchListBooks) {
        this.watchListBooks = watchListBooks;
    }
}
