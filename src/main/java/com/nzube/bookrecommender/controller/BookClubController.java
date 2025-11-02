package com.nzube.bookrecommender.controller;


import com.nzube.bookrecommender.model.BookClub;
import com.nzube.bookrecommender.model.Users;
import com.nzube.bookrecommender.services.BookClubService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/bookClub")
public class BookClubController {

    private final BookClubService bookClubService;

    BookClubController(BookClubService bookClubService){
        this.bookClubService = bookClubService;
    }

    //CREATE BOOK CLUB BY ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addBookClub")
    public void addBookClub(@RequestBody BookClub bookClub) {
        bookClubService.addBookClub(bookClub);
    }

    //GET ALL BOOK CLUBS CREATED
    @GetMapping("/bookClubs")
    public List<BookClub> getBookClubs(){
        return bookClubService.getBookClubs();
    }

    //FIND A BOOK CLUB BY ID
    @GetMapping("/bookClub/{id}")
    public BookClub getBookClub(@PathVariable int id){
        return bookClubService.getBookClub(id);
    }

    //FIND BOOK CLUBS BASED ON GENRE
    @GetMapping("/booClubs/{genre}")
    public Set<BookClub> getBookClubByGenre(@PathVariable String genre){
        return  bookClubService.getBookClubByGenre(genre);
    }

    //GET ALL USERS IN BOOK CLUB
    @GetMapping("/bookClub/{id}/users")
    public Set<Users> getUsers(@PathVariable int id){
        return bookClubService.getUsers(id);

    }

    //UPDATE A BOOK CLUB
    @PutMapping("/bookClub/{id}")
    public void updateBookClub(@PathVariable int id, @RequestBody BookClub bookClub){
        bookClubService.updateBookClub(id, bookClub);
    }

    //DELETE A BOOK CLUB
    @DeleteMapping("/bookClub/{id}")
    public void deleteBookClub(@PathVariable int id) {
        bookClubService.deleteBookClub(id);
    }
}
