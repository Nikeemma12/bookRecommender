package com.nzube.bookrecommender.services;


import com.nzube.bookrecommender.model.Book;
import com.nzube.bookrecommender.model.BookClub;
import com.nzube.bookrecommender.model.Users;
import com.nzube.bookrecommender.repository.BookClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class BookRecommenderService {



    private final BookClubRepository bookClubRepository;
    private final UserService userService;

    @Autowired
    public BookRecommenderService(BookClubRepository bookClubRepository, UserService userService) {
        this.bookClubRepository = bookClubRepository;
        this.userService = userService;
    }

    public Users getCurrentUser(){
        return userService.getCurrentUser();
    }


     public Set<Book> getRecommendation() {
        Users user = getCurrentUser();
         Set<Book> readBooks = user.getReadBooks();
         Set<Book> recommendedBooks = new HashSet<>();
         for(BookClub usersBookClub:user.getBookClub()) {
             for(Users usersMembers: usersBookClub.getUsers()){
                 if(!usersMembers.equals(user)){
                     for(Book book: usersMembers.getLikedBooks()){
                         if(!readBooks.contains(book) && book.getGenre().equals(usersBookClub.getGenre())){
                             recommendedBooks.add(book);
                         }
                     }
                 }
             }
         }
        return  recommendedBooks;
     }
}
