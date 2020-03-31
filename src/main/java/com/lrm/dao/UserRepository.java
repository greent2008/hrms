package com.lrm.dao;

import com.lrm.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);

    Page<User> findById(Long id, Pageable pageable);

    @Query("select u from User u join u.group g where g.id = ?1")
    Page<User> findByGroupId(Long groupId, Pageable pageable);


    Page<User> findByTypeIn(List<Integer> types, Pageable pageable);
}
