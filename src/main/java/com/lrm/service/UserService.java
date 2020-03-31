package com.lrm.service;

import com.lrm.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    User getUser(Long id);

    User getUserByName(String name);

    Page<User> listUser(Pageable pageable);

    Page<User> getUserById(Long id, Pageable pageable);

    Page<User> getUserByGroup(Long groupId, Pageable pageable);

    Page<User> getUserByType(List<Integer> type, Pageable pageable);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    User checkUser(String username, String password);

}
