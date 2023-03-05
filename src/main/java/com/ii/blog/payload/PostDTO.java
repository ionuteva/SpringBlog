package com.ii.blog.payload;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String description;
    private String content;

}
