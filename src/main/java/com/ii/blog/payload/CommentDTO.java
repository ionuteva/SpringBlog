package com.ii.blog.payload;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class CommentDTO {
    private Long id;
    private String name;
    private String email;
    private String body;
}
