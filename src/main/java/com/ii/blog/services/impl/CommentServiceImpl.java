package com.ii.blog.services.impl;
import com.ii.blog.entities.Comment;
import com.ii.blog.entities.Post;
import com.ii.blog.exceptions.BlogApiException;
import com.ii.blog.exceptions.ResourceNotFound;
import com.ii.blog.payload.CommentDTO;
import com.ii.blog.repositories.CommentRepository;
import com.ii.blog.repositories.PostRepository;
import com.ii.blog.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    @Override
    public CommentDTO createComment(Long postId, CommentDTO commentDTO) {
        Comment comment = mapToEntity(commentDTO);
        Post post = postRepository.findById(postId).orElseThrow(()-> new
                ResourceNotFound("Post", "id",postId));
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDTO> getAllCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(this::mapToDTO).toList();
    }

    @Override
    public CommentDTO getCommentById(Long postId, Long commentId) {
        Comment comment = findById(postId, commentId);
        return mapToDTO(comment);
    }

    @Override
    public CommentDTO updateCommentById(Long postId, Long commentId, CommentDTO commentDTO) {
        Comment comment = findById(postId, commentId);
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());
        Comment updatedComment = commentRepository.save(comment);
        return mapToDTO(updatedComment);
    }

    private Comment mapToEntity(CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setBody(commentDTO.getBody());
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        return comment;
    }

    private CommentDTO mapToDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setName(comment.getName());
        commentDTO.setBody(comment.getBody());
        commentDTO.setEmail(comment.getEmail());
        return commentDTO;
    }

    private Comment findById(Long postId, Long commentId){
        Post post = postRepository.findById(postId).orElseThrow(()-> new
                ResourceNotFound("Post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new
                ResourceNotFound("Comment","id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        return comment;
    }
}

