package com.nzube.bookrecommender.repository;

import com.nzube.bookrecommender.model.BookClub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface BookClubRepository extends JpaRepository<BookClub, Integer> {
    Set<BookClub> findBookClubsByGenre(String genre);
}
