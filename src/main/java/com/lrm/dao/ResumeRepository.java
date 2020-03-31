package com.lrm.dao;

import com.lrm.po.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by jianengxi on 2020/2/21
 */
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    @Query("select r from Resume r join r.user u where u.id = ?1")
    Resume findByUserId(Long userId);
}
