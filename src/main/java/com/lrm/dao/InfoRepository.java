package com.lrm.dao;

import com.lrm.po.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;

/**
 * Created by jianengxi on 2020/2/20
 */
public interface InfoRepository extends JpaRepository<Info, Long> {
    @Query("select i from Info i join i.user u where u.id = ?1")
    Info findByUserId(Long userId);
}
