package com.lrm.service;

import com.lrm.po.Group;
import com.lrm.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by jianengxi on 2020/2/20
 */
public interface GroupService {
    Group saveGroup(Group group);

    Group getGroup(Long id);

    Group getGroupByName(String name);

    List<Group> listGroup();

    Group updateGroup(Long id, Group group);

    void deleteGroup(Long id);
}
