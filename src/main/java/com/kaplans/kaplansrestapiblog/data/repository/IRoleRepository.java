package com.kaplans.kaplansrestapiblog.data.repository;

import com.kaplans.kaplansrestapiblog.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {
//    @Override
//    <S extends Role> Optional<S> findOne(Example<S> example);

    @Query("select r from Role r where r.name = ?1")
    Optional<Role> findByName(String name);


}