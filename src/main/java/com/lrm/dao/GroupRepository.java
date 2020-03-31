package com.lrm.dao;

import com.lrm.po.Group;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jianengxi on 2020/2/20
 */
public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByGroupName(String groupName);
}
