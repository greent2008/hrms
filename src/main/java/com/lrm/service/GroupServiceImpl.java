package com.lrm.service;

import com.lrm.NotFoundException;
import com.lrm.dao.GroupRepository;
import com.lrm.po.Group;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jianengxi on 2020/2/20
 */
@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    GroupRepository groupRepository;

    @Transactional
    @Override
    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    @Transactional
    @Override
    public Group getGroup(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Group getGroupByName(String name) {
        return groupRepository.findByGroupName(name);
    }

    @Transactional
    @Override
    public List<Group> listGroup() {
        return groupRepository.findAll();
    }

    @Transactional
    @Override
    public Group updateGroup(Long id, Group group) {
        Group g = groupRepository.findById(id).orElse(null);
        if (g == null) {
            throw new NotFoundException("不存在该组");
        }
        BeanUtils.copyProperties(group, g);
        return groupRepository.save(g);
    }

    @Override
    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }
}
