package com.kaplans.kaplansrestapiblog.mapper;



import com.kaplans.kaplansrestapiblog.data.entity.Comment;
import com.kaplans.kaplansrestapiblog.dto.CommentDTO;
import org.mapstruct.Mapper;

@Mapper(implementationName = "CommentMapper", componentModel = "spring")
public interface ICommentMapper {
    Comment toComment(CommentDTO commentDTO);
    CommentDTO toCommentDTO(Comment comment);
}
