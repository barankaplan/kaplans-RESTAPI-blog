package com.kaplans.kaplansrestapiblog.data.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments", uniqueConstraints = {@UniqueConstraint(columnNames = {"e_mail"})})
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long id;

    @NotEmpty(message = "name should not be null or empty!")
    private String name;

    @Column(name = "e_mail", nullable = false)
    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String eMail;

    @NotEmpty
    @Size(min = 10,message = "comment body should be at least 10 characters")
    private String body;


    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;

}
