package com.example.board.vel01.api;
import com.example.board.vel01.domain.Comment;
import com.example.board.vel01.domain.User;
import com.example.board.vel01.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST API Controller
 */
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentController {

    private final CommentService commentService;

    /* CREATE */
    @PostMapping("/posts/comments")
    public ResponseEntity<?> saveComment(@RequestBody Comment.Request request) {
        Comment comment = Comment.Request.toEntity(request);
        commentService.save(request);
        return  ResponseEntity.ok().body(comment);
    }

    /* READ */
    @GetMapping("/posts/comments/{nickName}")
    public List<Comment> readComments(@PathVariable String nickName) {
        return commentService.findAll(nickName);
    }

    /* UPDATE */
    @PutMapping({"/posts/comments/update"})
    public ResponseEntity<?> update(@RequestBody Comment.Request request) {
        commentService.update(request);
        return ResponseEntity.ok().body(request);
    }

    /* DELETE */
    @DeleteMapping("/posts/comments/{nickName}")
    public ResponseEntity<?> delete(@PathVariable String nickName) {
        commentService.delete(nickName);
        return ResponseEntity.ok(nickName);
    }
}
