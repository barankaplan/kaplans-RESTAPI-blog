package com.kaplans.kaplansrestapiblog.mapper;


import com.kaplans.kaplansrestapiblog.data.entity.Post;
import com.kaplans.kaplansrestapiblog.dto.PostDTOV2;
import org.mapstruct.Mapper;

@Mapper(implementationName = "PostMapperV2", componentModel = "spring")
public interface IPostV2Mapper {
    Post toPost(PostDTOV2 postDTO);
    PostDTOV2 toPostDTOV2(Post post);
}
