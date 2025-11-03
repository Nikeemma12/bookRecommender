
package com.nzube.bookrecommender.controller;



import com.nzube.bookrecommender.model.Book;
import com.nzube.bookrecommender.services.UserService;
import com.nzube.bookrecommender.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    //REGISTER A USER
    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return userService.registerUsers(user);
    }

    //LOGIN A USER
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user){
        String token = userService.verify(user);
        if(!token.equals("Failed to Login")) {
            return ResponseEntity.ok().body(token);
        }
        return ResponseEntity.status(401).body("Invalid");
    }

    //GET ALL USERS
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<Users> getAllUsers(){
        return userService.getAllUsers();
    }

    //GET USER BY ID
    @GetMapping("/{id}")
    public Optional<Users> Users(@PathVariable int id){
        return userService.getUser(id);
    }

    //GET LOGGED-IN USER
    @GetMapping("/logged/user")
    public String getLoggedIn(){
        return userService.getCurrentUser().getUsername();
    }

    //ADD A BOOK A USER READ TO READ BOOKS
    @PostMapping("/read/{book_id}")
    public ResponseEntity<String> addReadBook(@PathVariable int book_id){
        boolean response = userService.addReadBook(book_id);
        if(response) {
            return ResponseEntity.ok("Added to read books successfully");
        }
        return ResponseEntity.badRequest().body("Book not found");

    }

    //ADD A BOOK A USER READ TO USER LIKED BOOKS
    @PostMapping("/like/{book_id}")
    public ResponseEntity<String> addLikedBook(@PathVariable int book_id){
        boolean response = userService.addLikeBook(book_id);
        if(response){
            return ResponseEntity.ok("Added to liked books successfully");
        }
        return ResponseEntity.badRequest().body("Book can't be added either because you haven't read it or book wasn't found");


    }

    //ADD A BOOK TO WATCHLIST
    @PostMapping("/watchList/{book_id}")
    public ResponseEntity<String> addToWatchList(@PathVariable int book_id){
        boolean response = userService.addToWatchList(book_id);
        if(response){
            return ResponseEntity.ok("Bookmarked successfully");
        }
        return ResponseEntity.badRequest().body("Book wasn't found");


    }

    //DELETE A BOOK FROM WATCHLIST
    @DeleteMapping("/watchlist/{book_id}")
    public ResponseEntity<String> removeFromWatchList(@PathVariable int book_id){
        if(userService.removeFromWatchList(book_id)){
            return ResponseEntity.ok("Book removed from Bookmarked List");
        }
        return ResponseEntity.ok("Book isn't in your Bookmarked List");
    }

    //CHECK BOOKMARK STATUS OF A BOOK
    @GetMapping("/watchList/status/{book_id}")
    public ResponseEntity<Boolean> getBookmarkStaus(@PathVariable  int book_id){
        boolean found = userService.getBookMarkStatus(book_id);
        if(found){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);

    }
    //GET USERS LIKED BOOKS
    @GetMapping("/likedBooks")
    public Set<Book> getLikedBooks() {
        return userService.getLikedBooks();
    }
    //GET USERS READ BOOKS
    @GetMapping("/readBooks")
    public Set<Book> getReadBooks() {
        return userService.getReadBooks();
    }

    //GET WATCH LIST
    @GetMapping("/watchlist")
    public Set<Book> getWatchList() {
        return userService.getWatchList();
    }
    //UPDATE USER MAIN GENRES
    @PatchMapping("/updateGenre")
    public ResponseEntity<String> updateGenre(@RequestBody Map<String, String> genre){
        boolean response = userService.updateMainGenre(genre);
        System.out.println(response);
        if(response){
            return ResponseEntity.ok("Genre updated successfully");
        }
        return ResponseEntity.badRequest().body("Main genre not found");

    }

    //UPDATE USER OTHER GENRES
    @PatchMapping("/updateOtherGenres")
    public ResponseEntity<String> updateOtherGenre(@RequestBody Map<String, String> otherGenre){
        boolean response = userService.updateOtherGenres(otherGenre);
        if(response){
            return ResponseEntity.ok("Genre successfully added");
        }
        return ResponseEntity.badRequest().body("Invalid");
    }

    // ADD USER TO BOOK CLUB
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{userId}/addBookClub/{bookClubId}")
    public void addUserToBookClub(@PathVariable int userId, @PathVariable int bookClubId){
        userService.addUserToBookClub(userId,bookClubId);

    }

}
