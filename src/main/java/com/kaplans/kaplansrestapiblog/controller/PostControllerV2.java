package com.kaplans.kaplansrestapiblog.controller;


import com.kaplans.kaplansrestapiblog.data.entity.Post;
import com.kaplans.kaplansrestapiblog.dto.PostDTO;
import com.kaplans.kaplansrestapiblog.dto.PostDTOV2;
import com.kaplans.kaplansrestapiblog.info.PostResponse;
import com.kaplans.kaplansrestapiblog.service.IPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.kaplans.kaplansrestapiblog.utils.AppConstants.*;

@RestController
@RequestMapping("api/v2/posts")
public class PostControllerV2 {

    private final IPostService iPostService;

    public PostControllerV2(IPostService iPostService) {
        this.iPostService = iPostService;
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("create")
    public ResponseEntity<PostDTOV2> createPost(@Valid @RequestBody Post post) {
        return new ResponseEntity<>(iPostService.createPostDTOV2(post), HttpStatus.CREATED);
    }

//     @PostMapping("create")
//    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody Post post) {
////        return new ResponseEntity<>(iPostService.createPostDTOV2(post), HttpStatus.CREATED);
//        return new ResponseEntity<>(iPostService.createPostDTO(post), HttpStatus.CREATED);
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update-by-id/{id}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody Post post, @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(iPostService.updatePost(post, id), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete-by-id/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
        iPostService.deletePostById(id);
        return new ResponseEntity<>("Post entity deleted!", HttpStatus.OK);
    }

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


}
