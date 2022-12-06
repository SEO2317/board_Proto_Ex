package com.example.board.vel01.service;

import com.example.board.vel01.domain.Comment;
import com.example.board.vel01.domain.Post;
import com.example.board.vel01.domain.User;
import com.example.board.vel01.repository.CommentRepository;
import com.example.board.vel01.repository.PostRepository;
import com.example.board.vel01.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpI implements CommentService{

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postsRepository;


    @Override
    public String save(Comment.Request request) {
        User user = userRepository.findByNickName(request.getUser().getNickName());
        Post posts = postsRepository.findById(user.getId()).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다. " + user.getId()));

        request.setUser(user);
        request.setPosts(posts);

        Comment comment = Comment.Request.toEntity(request);
        commentRepository.save(comment);

        return comment.getId();
    }

    @Override
    public List<Comment> findAll(String id) {
        Post posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));
        List<Comment> comment = posts.getComments();
        commentRepository.findAll((Pageable) comment);
        return comment;
    }

    @Override
    public void update(Comment.Request request) {
        User user = userRepository.findByNickName(request.getUser().getNickName()); //찾는 다
        Comment comment = commentRepository.findById(user.getId()).orElseThrow(() -> //저장 되어 잇는 닉네임
                new IllegalArgumentException("해당 닉네임이 작성한 댓글이 없습니다. " + request.getUser().getNickName()));
    }

    @Override
    public void delete(String nickName) {
        User user = userRepository.findByNickName(nickName);
        Comment comment = commentRepository.findById(user.getId()).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 지울 수 있는 권한이 없습니다. nickName=" + nickName));

        commentRepository.delete(comment);
    }
}
