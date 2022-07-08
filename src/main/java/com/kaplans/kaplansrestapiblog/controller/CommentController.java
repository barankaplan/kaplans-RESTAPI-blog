package com.kaplans.kaplansrestapiblog.controller;



import com.kaplans.kaplansrestapiblog.data.entity.Comment;
import com.kaplans.kaplansrestapiblog.dto.CommentDTO;
import com.kaplans.kaplansrestapiblog.service.ICommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "CRUD rest api for comment resource")
@RestController
@RequestMapping("api/")
public class CommentController {
    private final ICommentService iCommentService;


    public CommentController(ICommentService iCommentService) {
        this.iCommentService = iCommentService;
    }

    @ApiOperation(value = "create comment rest api ")
    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody Comment comment, @PathVariable(value = "postId") long postId) {
        return new ResponseEntity<>(iCommentService.createCommentDTO(comment, postId), HttpStatus.CREATED);
    }

    @ApiOperation(value = "get comment rest api ")
    @GetMapping("posts/{postId}/comments")
    public List<CommentDTO> getCommentsByPostId(@PathVariable(value = "postId") long postId){
        return iCommentService.getCommentsByPostId(postId);
    }

    @GetMapping("posts/{postId}/comments/{commentId}")
    public Comment getCommentById(@PathVariable(value = "postId") long postId, @PathVariable(value = "commentId") long commentId){
        return iCommentService.getCommentByID(postId,commentId);
    }

    @PutMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO>  updateCommentById(@Valid @PathVariable(value = "postId") long postId, @PathVariable(value = "commentId") long commentId,@RequestBody Comment comment){
        return new ResponseEntity<>(iCommentService.updateCommentByID( postId,commentId,comment), HttpStatus.OK);
    }

    @ApiOperation(value = "delete comment rest api ")
    @DeleteMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<String>  deleteCommentById(@PathVariable(value = "postId") long postId, @PathVariable(value = "commentId") long commentId){
         iCommentService.deleteCommentById(postId,commentId);
         return new ResponseEntity<>("comment deleted successfully", HttpStatus.OK);
    }
}
