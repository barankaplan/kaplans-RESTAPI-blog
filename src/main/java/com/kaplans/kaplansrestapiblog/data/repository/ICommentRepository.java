package com.kaplans.kaplansrestapiblog.data.repository;

import com.kaplans.kaplansrestapiblog.data.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.post.id = ?1")
    List<Comment> findByPostId(long postId);

    @Query("select c from Comment c where c.post.id = ?1 and c.id = ?2")
    List<Comment> findByPostIdAndId(long postId,long commentId);
}