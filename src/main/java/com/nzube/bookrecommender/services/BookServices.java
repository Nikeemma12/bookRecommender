package com.nzube.bookrecommender.services;

import com.nzube.bookrecommender.model.Book;
import com.nzube.bookrecommender.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookServices {

    private final BookRepository bookRepository;
    @Autowired
    BookServices(BookRepository bookRepository) {
        this.bookRepository =bookRepository;
    };

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public void addBook(Book book){
        bookRepository.save(book);
    }
    public void updateBook(Book book) {

//        bookRepository.save(book);
    }

    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }
}

