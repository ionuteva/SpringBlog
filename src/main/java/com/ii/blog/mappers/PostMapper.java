package com.ii.blog.mappers;

import com.ii.blog.entities.Post;
import com.ii.blog.payload.PostDTO;
import org.mapstruct.Mapper;

@Mapper
public interface PostMapper {

    PostDTO mapToDTO(Post post);
    Post mapToEntity(PostDTO postDTO);
}
