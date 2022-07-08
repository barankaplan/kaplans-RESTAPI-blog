package com.kaplans.kaplansrestapiblog.service;


import com.kaplans.kaplansrestapiblog.data.entity.Post;
import com.kaplans.kaplansrestapiblog.dto.PostDTO;
import com.kaplans.kaplansrestapiblog.dto.PostDTOV2;
import com.kaplans.kaplansrestapiblog.info.PostResponse;

public interface IPostService {
    PostDTO createPostDTO(Post post);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortBy2, String sortDir);

    PostDTO getPostById(long id);

    PostDTO updatePost(Post post, long id);

    void deletePostById(long id);

    PostDTOV2 createPostDTOV2(Post post);


}
