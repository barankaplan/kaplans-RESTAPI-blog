package com.kaplans.kaplansrestapiblog.mapper;


import com.kaplans.kaplansrestapiblog.data.entity.Post;
import com.kaplans.kaplansrestapiblog.dto.PostDTO;
import org.mapstruct.Mapper;

@Mapper(implementationName = "PostMapper", componentModel = "spring")
public interface IPostMapper {
    Post toPost(PostDTO postDTO);
    PostDTO toPostDTO(Post post);
}
