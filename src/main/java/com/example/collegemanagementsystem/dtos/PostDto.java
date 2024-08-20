package com.example.collegemanagementsystem.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostDto {
    private Long id;
    private String title;
    private String description;
}
