package com.example.board.vel01.service;

import com.example.board.vel01.domain.Comment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.StringJoiner;

@Service
public interface CommentService {

     String save(Comment.Request request);

    List<Comment> findAll(String nickName);

    void update(Comment.Request request);

    void delete(String nickName);

}
