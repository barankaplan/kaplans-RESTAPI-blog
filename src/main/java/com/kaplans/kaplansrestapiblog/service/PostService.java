package com.kaplans.kaplansrestapiblog.service;


import com.kaplans.kaplansrestapiblog.data.entity.Post;
import com.kaplans.kaplansrestapiblog.data.exception.ResourceNotFoundException;
import com.kaplans.kaplansrestapiblog.data.repository.IPostRepository;
import com.kaplans.kaplansrestapiblog.dto.PostDTO;
import com.kaplans.kaplansrestapiblog.dto.PostDTOV2;
import com.kaplans.kaplansrestapiblog.info.PostResponse;
import com.kaplans.kaplansrestapiblog.mapper.IPostMapper;
import com.kaplans.kaplansrestapiblog.mapper.IPostV2Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService implements IPostService {

    private final IPostMapper iPostMapper;
    private final IPostV2Mapper iPostV2Mapper;
    private final IPostRepository iPostRepository;

    public PostService(IPostMapper iPostMapper, IPostV2Mapper iPostV2Mapper, IPostRepository iPostRepository) {
        this.iPostMapper = iPostMapper;
        this.iPostV2Mapper = iPostV2Mapper;
        this.iPostRepository = iPostRepository;
    }

    public PostDTO createPostDTO(Post post) {
        return iPostMapper.toPostDTO(iPostRepository.save(post));
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortBy2, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy, sortBy2).ascending()
                : Sort.by(sortBy, sortBy2).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = iPostRepository.findAll(pageable);
        List<Post> listOfPosts = posts.getContent();
        List<PostDTO> postDTOS = listOfPosts.stream()
                .map(iPostMapper::toPostDTO).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDTOS);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

//    @Override
//    public List<PostDTO> getAllPosts() {
//        return iPostRepository.findAll().stream()
//                .map(iPostMapper::toPostDTO).collect(Collectors.toList());
//    }

    private PostDTO getPostDTO(long id, Optional<Post> postDTOOptional) {
        return postDTOOptional.map(iPostMapper::toPostDTO).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    }

    @Override
    public PostDTO getPostById(long id) {
        Optional<Post> postDTOOptional = iPostRepository.findById(id);
        return getPostDTO(id, postDTOOptional);

    }

    private Post getPostDTOOptional(long id) {
        return iPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    }

    @Override
    public PostDTO updatePost(Post post, long id) {
        Post postDTOOptional = getPostDTOOptional(id);
        postDTOOptional.setTitle(post.getTitle());
        postDTOOptional.setContent(post.getContent());
        postDTOOptional.setDescription(post.getDescription());
        iPostRepository.save(postDTOOptional);
        return iPostMapper.toPostDTO(postDTOOptional);
    }


    @Override
    public void deletePostById(long id) {
        Post postDTOOptional = getPostDTOOptional(id);
        iPostRepository.delete(postDTOOptional);
    }

    @Override
    public PostDTOV2 createPostDTOV2(Post post) {
        return iPostV2Mapper.toPostDTOV2(iPostRepository.save(post));
    }



}
