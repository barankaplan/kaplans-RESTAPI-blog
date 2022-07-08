package com.kaplans.kaplansrestapiblog.dto;


import lombok.Data;
import org.springframework.boot.convert.DataSizeUnit;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDTO {
    @NotEmpty(message = "Name should not be null or empty !")
    private String name;

    @NotEmpty
    @Email
    private String eMail;

    @NotEmpty
    @Size(min = 10,message = "comment body must be min 10 characters")
    private String body;


}
