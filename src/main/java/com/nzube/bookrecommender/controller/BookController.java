package com.nzube.bookrecommender.controller;

import com.nzube.bookrecommender.model.Book;
import com.nzube.bookrecommender.services.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookController {
    private final BookServices bookServices;

    @Autowired
    BookController(BookServices bookServices){
        this.bookServices = bookServices;
    }

    @PreAuthorize("hasAnyRole('USERS', 'ADMIN')")
    @GetMapping("/books")
        public ResponseEntity<List<Book>> getBooks() {
        return new ResponseEntity<>(bookServices.getBooks(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addBook")
    public ResponseEntity<String> addBook(@RequestBody Book book){
        if(book!=null){
            bookServices.addBook(book);
            return ResponseEntity.ok("Book added successfully");
        }
        return ResponseEntity.badRequest().body("Book title can't be null");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateBook")
    public ResponseEntity<String> updateBook(@RequestBody Book book){

        bookServices.updateBook(book);
        return ResponseEntity.ok("Book updated successfully");
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(@PathVariable int id) {
        bookServices.deleteBook(id);
    }

    @GetMapping("/{genre}")
    public List<Book> getBookByGenre(@PathVariable String genre) {
        return bookServices.getBokByGenre(genre);
    }
}
