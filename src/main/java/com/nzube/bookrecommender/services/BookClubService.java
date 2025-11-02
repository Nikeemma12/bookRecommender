package com.nzube.bookrecommender.services;

import com.nzube.bookrecommender.model.BookClub;
import com.nzube.bookrecommender.model.Users;
import com.nzube.bookrecommender.repository.BookClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookClubService {

    private final BookClubRepository bookClubRepository;
    @Autowired
    BookClubService (BookClubRepository bookClubRepository){
        this.bookClubRepository = bookClubRepository;
    }

    //CREATE BOOK CLUB BY ADMIN
    public void addBookClub(BookClub bookClub){
        bookClubRepository.save(bookClub);
    }

    //GET ALL BOOK CLUBS
    public List<BookClub> getBookClubs() {
        return bookClubRepository.findAll();
    }

    //DELETE A BOOK CLUB
    public void deleteBookClub(int id) {
        bookClubRepository.deleteById(id);
    }

    //UPDATE BOOK CLUB
    public void updateBookClub(int id, BookClub bookClub) {
        Optional<BookClub> bookClub1  = bookClubRepository.findById(id);
        if(bookClub1.isPresent()){
            BookClub existing = bookClub1.get();
            existing.setGenre(bookClub.getGenre());
            existing.setName(bookClub.getName());
            bookClubRepository.save(existing);
        }
    }

    //FIND BOOK CLUB BY ID
    public BookClub getBookClub(int id) {
         return bookClubRepository.findById(id).orElse(null);
    }

    //GET ALL USERS IN BOOK CLUB
    public Set<Users> getUsers(int id) {
        BookClub bookClub = bookClubRepository.findById(id).orElse(null);
        if(bookClub!=null){
            return bookClub.getUsers();
        }
        return null;
    }

    //GET ALL BOOK CLUB IN THAT PARTICULAR GENRE
    public Set<BookClub> getBookClubByGenre(String genre) {
        return bookClubRepository.findBookClubsByGenre(genre);
    }
}
