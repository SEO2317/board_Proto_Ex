package com.example.board.vel01.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "comments")
@Entity
public class Comment {


    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @Column(name = "created_date")
    @CreatedDate
    private String createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private String modifiedDate;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post posts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {

        @NotBlank(message = "댓글을 추가하셈")
        private String comment;

        private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));

        private User user;

        private Post posts;


        public Request(final Comment commentEntity) {
            this.comment = commentEntity.getComment();
            this.createdDate = commentEntity.getCreatedDate();
            this.modifiedDate = commentEntity.getModifiedDate();
            this.user = commentEntity.getUser();
            this.posts = commentEntity.getPosts();
        }

        public static Comment toEntity(Request request) {
            return Comment.builder()
                    .comment(request.getComment())
                    .createdDate(request.getCreatedDate())
                    .modifiedDate(request.getModifiedDate())
                    .user(request.getUser())
                    .posts(request.getPosts())
                    .build();

        }
    }


    @Getter
    @RequiredArgsConstructor
    public static class Response {

        private String id;
        private String comment;
        private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        private String nickName;


        public Response(Comment comment) {
            this.id = comment.getUser().getId();
            this.comment = comment.getComment();
            this.createdDate = comment.getCreatedDate();
            this.modifiedDate = comment.getModifiedDate();
            this.nickName = comment.getUser().getNickName();
        }



        public static List<Comment.Response> toResponseList(final List<Comment> comments){
            List<Comment.Response> list = new ArrayList<>();
            for(Comment comment : comments){
                list.add(new Response(comment));
            }
            return list;
        }
    }
}