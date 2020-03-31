package com.lrm.service;

import com.lrm.NotFoundException;
import com.lrm.dao.UserRepository;
import com.lrm.po.User;
import com.lrm.util.MD5Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Transactional
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public User getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    @Override
    public Page<User> listUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Page<User> getUserById(Long id, Pageable pageable) {
        return userRepository.findById(id, pageable);
    }

    @Transactional
    @Override
    public Page<User> getUserByGroup(Long groupId, Pageable pageable) {
        return userRepository.findByGroupId(groupId, pageable);
    }

    @Transactional
    @Override
    public Page<User> getUserByType(List<Integer> type, Pageable pageable) {
        return userRepository.findByTypeIn(type, pageable);
    }

    @Transactional
    @Override
    public User updateUser(Long id, User user) {
        User u = userRepository.findById(id).orElse(null);
        if (u == null) {
            throw new NotFoundException("不存在该用户");
        }
        BeanUtils.copyProperties(user, u);
        return userRepository.save(u);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
