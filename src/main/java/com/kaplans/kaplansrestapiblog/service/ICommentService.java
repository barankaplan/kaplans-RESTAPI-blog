package com.kaplans.kaplansrestapiblog.service;


import com.kaplans.kaplansrestapiblog.data.entity.Comment;
import com.kaplans.kaplansrestapiblog.dto.CommentDTO;

import java.util.List;


public interface ICommentService {
    CommentDTO createCommentDTO(Comment comment, long postId);

    List<CommentDTO> getCommentsByPostId(long postId);

    Comment getCommentByID(long postId,Long commentId);

    CommentDTO updateCommentByID(long postId,Long commentId,Comment comment);

    void deleteCommentById(long postId, long commentId);
}
