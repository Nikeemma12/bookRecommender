package com.nzube.bookrecommender.services;

import java.util.*;

import com.nzube.bookrecommender.model.Book;
import com.nzube.bookrecommender.model.BookClub;
import com.nzube.bookrecommender.model.UserPrincipal;
import com.nzube.bookrecommender.model.Users;
import com.nzube.bookrecommender.repository.BookClubRepository;
import com.nzube.bookrecommender.repository.BookRepository;
import com.nzube.bookrecommender.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final BookRepository bookRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final BookClubRepository bookClubRepository;

    @Autowired
    UserService(UsersRepository usersRepository, BookRepository bookRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService, BookClubRepository bookClubRepository) {
        this.usersRepository = usersRepository;
        this.bookRepository = bookRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.bookClubRepository = bookClubRepository;
    }

    //GET LOGGED-IN USER
    public Users getCurrentUser() {
         Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         if (principal instanceof UserPrincipal userPrincipal){
             return usersRepository.findByUsername(userPrincipal.getUsername());
         } else return null;
    }

    //REGISTER / SIGN UP
    public Users registerUsers(Users user){
        Random random = new Random();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        String genre = user.getMainGenre();
        System.out.println(genre);
        Set<BookClub> bookClubs = bookClubRepository.findBookClubsByGenre(genre);
        bookClubs.stream().skip(random.nextInt(bookClubs.size())).findFirst().ifPresent(userBookClub -> user.getBookClub().add(userBookClub));
        return usersRepository.save(user);
    }

    //LOGIN
    public String verify(Users user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user);
        }
        else {
            return "Failed to Login";
        }
    }

    //GET ALL USERS
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    //ADD BOOKS USER READ TO HIS COLLECTION
    public boolean addReadBook(int book_id) {
        Users user = getCurrentUser();
        Book readBook = bookRepository.findById(book_id).orElse(null);
        if(readBook!=null){
            user.getReadBooks().add(readBook);
            usersRepository.save(user);
            return true;
        }
        return false;
    }

    //ADD BOOKS USER READ AND LIKED TO HIS COLLECTION
    public boolean addLikeBook(int bookId) {
        Users user = getCurrentUser();
        Book likedBook = bookRepository.findById(bookId).orElseThrow(()->new RuntimeException("Book not found"));
        if(user.getReadBooks().contains(likedBook)){
            likedBook.setLikes(likedBook.getLikes()+1);
            user.getLikedBooks().add(likedBook);
            bookRepository.save(likedBook);
            usersRepository.save(user);
            return true;
        }
        return false;
    }

    //ADD BOOK TO WATCHLIST
    public boolean addToWatchList(int bookId) {
        Users user = getCurrentUser();
        Book bookToRead = bookRepository.findById(bookId).orElse(null);
        if(bookToRead!=null){
            user.getWatchListBooks().add(bookToRead);
            usersRepository.save(user);
            return true;
        }
        return false;
    }

    //FIND USER BY ID
    public Optional <Users> getUser(int id) {
        return usersRepository.findById(id);
    }

    //ADD USER TO A BOOK CLUB
    public void addUserToBookClub(int userId, int bookClubId) {

        BookClub bookClub= bookClubRepository.findById(bookClubId).orElse(null);
        Users user = usersRepository.findById(userId).orElse(null);

       if(user!=null && bookClub!=null && (user.getMainGenre().equals(bookClub.getGenre()) || user.getOtherGenre().contains(bookClub.getGenre()))){
           user.getBookClub().add(bookClub);
           usersRepository.save(user);
       }
    }
    //UPDATE USER MAIN GENRE
    public boolean updateMainGenre(Map<String, String> request) {
        Users user = getCurrentUser();
        if(request.containsKey("mainGenre")){
            String newGenre = request.get("mainGenre");
            user.setMainGenre(newGenre);
            usersRepository.save(user);
            return true;
        }
        return false;
    }

    //UPDATE USER OTHER GENRES
    public boolean updateOtherGenres(Map<String, String> request) {
        Users user = getCurrentUser();
        if(request.containsKey("otherGenre")){
            String otherGenre = request.get("otherGenre");
            user.getOtherGenre().add(otherGenre);
            usersRepository.save(user);
            return true;
        }
        return false;
    }

    //GET USERS LIKED BOOKS
    public Set<Book> getLikedBooks() {
        Users user = getCurrentUser();
        return user.getLikedBooks();
    }

    //GET USERS READ BOOKS
    public Set<Book> getReadBooks() {
        Users user = getCurrentUser();
        return user.getReadBooks();
    }

    //GET BOOKMARK STATUS
    public boolean getBookMarkStatus(int bookId) {
        Users user = getCurrentUser();
        Book book = bookRepository.findById(bookId).orElse(null);
        return user.getWatchListBooks().contains(book);
    }

    //GET BOOKS IN WATCH LIST
    public Set<Book> getWatchList() {
        Users user = getCurrentUser();
        return user.getWatchListBooks();
    }

    public boolean removeFromWatchList(int bookId) {
        Users user = getCurrentUser();
        Book book = bookRepository.findById(bookId).orElse(null);
        if(user.getWatchListBooks().contains(book)){
            user.getWatchListBooks().remove(book);
            usersRepository.save(user);
            return true;
        }
        return false;
    }
}
