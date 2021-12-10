package com.hramyko.finalapp.web.controllers;


import com.hramyko.finalapp.service.PostService;
import com.hramyko.finalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping
    public String index() {
        return postService.findAll().toString();
    }

    @GetMapping("my")
    @PreAuthorize("hasAuthority('user.write')")
    public String showMyPosts() {
        return postService.findUserPosts().toString();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('user.read', 'user.write', 'user.delete')")
    public String showUserPosts(@PathVariable("id") int id) {
        return postService.findUserPosts(id).toString();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user.write')")
    public String createPost(@RequestBody String jsonString) {
        postService.createPost(jsonString);
        return "Post has been created successfully";
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasAnyAuthority('user.write', 'user.delete')")
    public String updatePost(@PathVariable("id") int id, @RequestBody String jsonString) {
        postService.updatePost(id, jsonString);
        return "Post has been updated successfully";
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('user.write', 'user.delete')")
    public String deletePost(@PathVariable("id") int id) {
        postService.deletePost(id, userService.currentUser().getId());
        return "Post has been deleted successfully";
    }
}