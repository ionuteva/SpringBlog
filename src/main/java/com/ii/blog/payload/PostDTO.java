package com.ii.blog.payload;

import lombok.*;

@Data
public class PostDTO {
    private Long id;
    private String title;
    private String description;
    private String content;

}
