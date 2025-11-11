package com.nzube.bookrecommender.repository;

import com.nzube.bookrecommender.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findBookByGenre(String genre);

    Book getBookById(int id);
}
