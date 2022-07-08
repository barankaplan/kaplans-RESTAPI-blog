package com.kaplans.kaplansrestapiblog.data.repository;


import com.kaplans.kaplansrestapiblog.data.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository//not necessary , SimpleJpaRepository solves
public interface IPostRepository extends JpaRepository<Post, Long> {



}
