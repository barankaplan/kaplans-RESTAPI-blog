package com.kaplans.kaplansrestapiblog.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDTO {
    @NotEmpty
    @Size(min = 2,message = "at least 2 characters !")
    private String title;

    @NotEmpty
    @Size(min = 10,message = "at least 10 characters !")
    private String description;

    @NotEmpty
    private String content;

    private Set<CommentDTO> comments;


}
