package com.example.board.vel01.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String nickName;
    private String title;
    private String content;

	@OneToMany(mappedBy = "posts", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Comment> comments;;
    private Long viewCount;
    private String error;
    private boolean done;


    @Getter@Setter
	@ToString
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
    public static class Request{
    	
    	private String id;
        private String nickName;
        private String title;
        private String content;

        private List<Comment> comments;;
        private Long viewCount; 
        
        public Request(final Post postEntity) {
        	this.id = postEntity.getId();
        	this.nickName = postEntity.getNickName();
        	this.title = postEntity.getTitle();
        	this.content = postEntity.getContent();
        	this.comments = postEntity.getComments();
        	this.viewCount = postEntity.getViewCount();
        }
        
    	
    	
    	public static Post toEntity(Post.Request req) {
    		return Post.builder()
    				.id(req.getId())
    				.nickName(req.getNickName())
    				.title(req.getTitle())
    				.content(req.getContent())
    				.comments(req.getComments())
    				.viewCount(req.getViewCount())
    				.build();
    	}
    }
    
    
    @Getter@Setter
   	@ToString
   	@Builder
   	@NoArgsConstructor
   	@AllArgsConstructor
    public static class Response{

		@Column(name = "posts_id")
    	private String id;
        private String nickName;
        private String title;
        private String content;

		private List<Comment> comments;
        private Long viewCount;
        private boolean done;
        private String error;
        
        public Response(final Post postEntity) {
        	this.id = postEntity.getId();
        	this.nickName = postEntity.getNickName();
        	this.title = postEntity.getTitle();
        	this.content = postEntity.getContent();
        	this.comments = postEntity.getComments();
        	this.viewCount = postEntity.getViewCount();
        }
    	
    	public static Post.Response toResponse(Post postEntity){
    		
    		return Post.Response.builder()
    				.id(postEntity.getId())
    				.nickName(postEntity.getNickName())
    				.title(postEntity.getTitle())
    				.content(postEntity.getContent())
    				.comments(postEntity.getComments())
    				.viewCount(postEntity.getViewCount())
    				.error(postEntity.getError())
    				.build();
    	}
    	
    	public static List<Post.Response> toResponseList(List<Post> posts){
    		List<Post.Response> postList = new ArrayList<Post.Response>();
    		for (Post post : posts) {
				postList.add(toResponse(post));
			}
    		return postList;
    	}
    }
    
    
}