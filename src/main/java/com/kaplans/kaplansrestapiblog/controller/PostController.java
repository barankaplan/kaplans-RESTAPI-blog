package com.kaplans.kaplansrestapiblog.controller;



import com.kaplans.kaplansrestapiblog.data.entity.Post;
import com.kaplans.kaplansrestapiblog.dto.PostDTO;
import com.kaplans.kaplansrestapiblog.info.PostResponse;
import com.kaplans.kaplansrestapiblog.service.IPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.kaplans.kaplansrestapiblog.utils.AppConstants.*;


@Api(value="CRUD Rest APIs for post resources")
@RestController
@RequestMapping("api/posts")
public class PostController {

    private final IPostService iPostService;

    public PostController(IPostService iPostService) {
        this.iPostService = iPostService;
    }


    @ApiOperation(value = "create post rest api")
//    @ApiResponse()
    @PreAuthorize("hasRole('USER')")
    @PostMapping("create")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody Post post) {
        return new ResponseEntity<>(iPostService.createPostDTO(post), HttpStatus.CREATED);
    }

    @ApiOperation(value = "update posts rest api")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update-by-id/{id}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody Post post, @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(iPostService.updatePost(post, id), HttpStatus.OK);
    }


    @ApiOperation(value = "delete posts rest api")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete-by-id/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
        iPostService.deletePostById(id);
        return new ResponseEntity<>("Post entity deleted!", HttpStatus.OK);
    }


    @ApiOperation(value = "get all posts rest api")
    @GetMapping("get-all")
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortBy2", defaultValue = DEFAULT_SORT_BY_2, required = false) String sortBy2,
            @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return iPostService.getAllPosts(pageNo, pageSize, sortBy, sortBy2, sortDir);
    }

    @GetMapping("get-by-id/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(iPostService.getPostById(id));
    }

    @GetMapping(value = "get-by-id/{id}",params = "version=3")
    public ResponseEntity<PostDTO> getPostByIdV3(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(iPostService.getPostById(id));
    }

    @GetMapping(value = "get-by-id/{id}",headers = "X-API-VERSION=2")
    public ResponseEntity<PostDTO> getPostByIdV2(@PathVariable(name = "id") long id) {
        System.out.println("version 2!");
        return ResponseEntity.ok(iPostService.getPostById(id));
    }

    @GetMapping(value = "get-by-id/{id}",produces = "application/vnd.kaplan.v4+json")
    public ResponseEntity<PostDTO> getPostByIdV4(@PathVariable(name = "id") long id) {
        System.out.println("version 4!");
        return ResponseEntity.ok(iPostService.getPostById(id));
    }


}
