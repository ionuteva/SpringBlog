package com.ii.blog.services.impl;

import com.ii.blog.entities.Post;
import com.ii.blog.exceptions.ResourceNotFound;
import com.ii.blog.mappers.PostMapper;
import com.ii.blog.payload.PostDTO;
import com.ii.blog.payload.PostResponse;
import com.ii.blog.repositories.PostRepository;
import com.ii.blog.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;


    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post newPost = postRepository.save(postMapper.mapToEntity(postDTO));
        return postMapper.mapToDTO(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> lisOfPosts = posts.getContent();
        List<PostDTO> content =  lisOfPosts.stream().map(postMapper::mapToDTO).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDTO getPostById(Long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new
                ResourceNotFound("Post", "id",id));
        return postMapper.mapToDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new
                ResourceNotFound("Post", "id",id));
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        Post updatedPost = postRepository.save(post);
        return postMapper.mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    //CONVERT ENTITY TO DTO

}
