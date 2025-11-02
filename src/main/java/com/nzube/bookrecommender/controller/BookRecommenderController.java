package com.nzube.bookrecommender.controller;

import com.nzube.bookrecommender.model.Book;
import com.nzube.bookrecommender.model.BookClub;
import com.nzube.bookrecommender.model.Users;
import com.nzube.bookrecommender.services.BookRecommenderService;
import com.nzube.bookrecommender.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/recommend")
public class BookRecommenderController {

    private final BookRecommenderService bookRecommenderService;
    private final UserService userService;


    @Autowired
    BookRecommenderController(BookRecommenderService bookRecommenderService, UserService userService){
        this.bookRecommenderService = bookRecommenderService;
        this.userService = userService;
    }

    @GetMapping("/user")
    public String getCurrentUser(){
        return userService.getCurrentUser().getUsername();
    }

    @GetMapping("/user/recommendedBook")
    public Set<Book> getRecommendation(){
        return bookRecommenderService.getRecommendation();
    }

}
