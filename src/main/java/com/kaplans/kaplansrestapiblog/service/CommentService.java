package com.kaplans.kaplansrestapiblog.service;


import com.kaplans.kaplansrestapiblog.data.entity.Comment;
import com.kaplans.kaplansrestapiblog.data.entity.Post;
import com.kaplans.kaplansrestapiblog.data.exception.BlogAPIException;
import com.kaplans.kaplansrestapiblog.data.exception.ResourceNotFoundException;
import com.kaplans.kaplansrestapiblog.data.repository.ICommentRepository;
import com.kaplans.kaplansrestapiblog.data.repository.IPostRepository;
import com.kaplans.kaplansrestapiblog.dto.CommentDTO;
import com.kaplans.kaplansrestapiblog.mapper.ICommentMapper;
import com.kaplans.kaplansrestapiblog.mapper.IPostMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService implements ICommentService {

    private final ICommentMapper iCommentMapper;
    private final ICommentRepository iCommentRepository;
    private final IPostMapper iPostMapper;
    private final IPostRepository iPostRepository;

    public CommentService(ICommentMapper iCommentMapper, ICommentRepository iCommentRepository, IPostMapper iPostMapper, IPostRepository iPostRepository) {
        this.iCommentMapper = iCommentMapper;
        this.iCommentRepository = iCommentRepository;
        this.iPostMapper = iPostMapper;
        this.iPostRepository = iPostRepository;
    }

    @Override
    public CommentDTO createCommentDTO(Comment comment, long postId) {
        Post post = iPostRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);
        return iCommentMapper.toCommentDTO(iCommentRepository.save(comment));
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(long postId) {
        List<Comment> comments = iCommentRepository.findByPostId(postId);

        return comments.stream()
                .map(iCommentMapper::toCommentDTO).collect(Collectors.toList());
    }
    private Comment getComment(long postId, Long commentId) {
        Post post = iPostRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = iCommentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment doesn't belong to the associated post");
        }
        return comment;
    }

    @Override
    public Comment getCommentByID(long postId, Long commentId) {
        Comment comment = getComment(postId, commentId);

        return comment;
    }



    @Override
    public CommentDTO updateCommentByID(long postId, Long commentId, Comment commentRequest) {
        Comment comment = getComment(postId, commentId);

        comment.setName(commentRequest.getName());
        comment.setEMail(commentRequest.getEMail());
        comment.setBody(commentRequest.getBody());
        return iCommentMapper.toCommentDTO(iCommentRepository.save(comment));

    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
        Comment comment = getComment(postId, commentId);
        iCommentRepository.delete(comment);

    }


}
