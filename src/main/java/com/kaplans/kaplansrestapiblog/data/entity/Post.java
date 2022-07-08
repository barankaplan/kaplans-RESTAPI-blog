package com.kaplans.kaplansrestapiblog.data.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@ApiModel(description = "post model information")
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
public class Post {
    @ApiModelProperty("Blog post id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ApiModelProperty("Blog post title")
    @NotEmpty
    @Size(min = 2,message = "post should have at least 2 characters")
    private String title;


    @ApiModelProperty("Blog post description")
    @NotEmpty
    @Size(min=10,message = "post description should have at least 10 characters")
    private String description;

    @NotEmpty
    @ApiModelProperty("Blog post content")
    private String content;

//    @OneToMany(mappedBy = "post",orphanRemoval = true)
//    @Transient
//    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

}
