package com.gene.base.api.controller;

import com.gene.base.application.command.*;
import com.gene.base.application.query.GetBlogCommentsRequest;
import com.gene.base.application.query.GetBlogRequest;
import com.gene.base.application.query.viewModel.BlogViewModel;
import com.gene.base.application.query.GetBlogsRequest;
import com.gene.base.application.query.GetBlogByUserRequest;
import com.gene.base.application.query.viewModel.CommentViewModel;
import io.jkratz.mediator.core.Mediator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {
    private final Mediator mediator;

    @Autowired
    public BlogController(Mediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping("/blogs")
    public ResponseEntity<String> createBlog(@Valid @RequestBody CreateBlogCommand command) {
        this.mediator.dispatch(command);
        return new ResponseEntity<>("Successfully created!", HttpStatus.CREATED);
    }

    @PutMapping("/blogs/{blogId}")
    public ResponseEntity<String> updateBlog(@Valid @RequestBody UpdateBlogCommand command, @RequestHeader(value = "user_id") String userId, @PathVariable String blogId) {
        command.setBlogId(blogId);
        command.setUserId(userId);
        this.mediator.dispatch(command);
        return new ResponseEntity<>("Successfully updated!", HttpStatus.OK);
    }

    @DeleteMapping("/blogs/{blogId}")
    public ResponseEntity<String> deleteBlog(@RequestHeader(value = "user_id") String userId, @PathVariable String blogId) {
        DeleteBlogCommand command = new DeleteBlogCommand(blogId, userId);
        this.mediator.dispatch(command);
        return new ResponseEntity<>("Successfully deleted!", HttpStatus.OK);
    }

    @PostMapping("/blogs/{blogId}/likes")
    public ResponseEntity<String> likeBlog(@RequestHeader(value = "user_id") String userId, @PathVariable String blogId) {
        LikeBlogCommand command = new LikeBlogCommand(blogId, userId);
        this.mediator.dispatch(command);
        return new ResponseEntity<>("Liked the blog successfully!", HttpStatus.CREATED);
    }

    @DeleteMapping("/blogs/{blogId}/likes/{likeId}")
    public ResponseEntity<String> unlikeBlog(@RequestHeader(value = "user_id") String userId, @PathVariable String blogId,
                                             @PathVariable String likeId) {
        UnlikeBlogCommand command = new UnlikeBlogCommand(blogId, userId, likeId);
        this.mediator.dispatch(command);
        return new ResponseEntity<>("Unliked the blog complete!", HttpStatus.OK);
    }

    @PostMapping("/blogs/{blogId}/comments")
    public ResponseEntity<String> commentBlog(@RequestHeader(value = "user_id") String userId, @PathVariable String blogId,
                                              @Valid @RequestBody CreateCommentCommand command) {
        command.setBlogId(blogId);
        command.setUserId(userId);
        this.mediator.dispatch(command);
        return new ResponseEntity<>("Comment is added successfully!", HttpStatus.CREATED);
    }

    @PutMapping("/blogs/{blogId}/comments/{commentId}")
    public ResponseEntity<String> updateCommentBlog(@RequestHeader(value = "user_id") String userId, @PathVariable String blogId,
                                                    @Valid @RequestBody UpdateCommentCommand command, @PathVariable String commentId) {
        command.setBlogId(blogId);
        command.setUserId(userId);
        command.setCommentId(commentId);
        this.mediator.dispatch(command);
        return new ResponseEntity<>("Comment is updated successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/blogs/{blogId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentBlog(@RequestHeader(value = "user_id") String userId, @PathVariable String blogId,
                                                    @PathVariable String commentId) {
        DeleteCommentCommand command = new DeleteCommentCommand(blogId, userId, commentId);
        this.mediator.dispatch(command);
        return new ResponseEntity<>("Comment has been deleted!", HttpStatus.OK);
    }

    @GetMapping(path = "/blogs")
    public Page<BlogViewModel> getBlogs(@RequestParam Integer page, @RequestParam Integer size) {
        GetBlogsRequest request = new GetBlogsRequest(page, size);
        return this.mediator.dispatch(request);
    }

    @GetMapping(path = "/blogs/user/{userId}")
    public Page<BlogViewModel> getBlogsByUser(@RequestParam Integer page, @RequestParam Integer size, @PathVariable String userId) {
        GetBlogByUserRequest request = new GetBlogByUserRequest(page, size, userId);
        return this.mediator.dispatch(request);
    }

    @GetMapping(path = "/blogs/{blogId}")
    public BlogViewModel getBlog(@RequestHeader(value = "user_id") String userId, @PathVariable String blogId) {
        GetBlogRequest request = new GetBlogRequest(blogId);
        return this.mediator.dispatch(request);
    }

    @GetMapping(path = "/blogs/{blogId}/comments")
    public List<CommentViewModel> getBlogComments(@RequestHeader(value = "user_id") String userId, @PathVariable String blogId) {
        GetBlogCommentsRequest request = new GetBlogCommentsRequest(blogId);
        return this.mediator.dispatch(request);
    }
}
