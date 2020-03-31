package com.lrm.service;

import com.lrm.NotFoundException;
import com.lrm.dao.ResumeRepository;
import com.lrm.po.Resume;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jianengxi on 2020/2/21
 */
@Service
public class ResumeImpl implements ResumeService {
    @Autowired
    ResumeRepository resumeRepository;

    @Transactional
    @Override
    public Resume saveResume(Resume resume) {
        return resumeRepository.save(resume);
    }

    @Transactional
    @Override
    public Resume getResume(Long id) {
        return resumeRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Resume getResumeByUser(Long userId) {
        return resumeRepository.findByUserId(userId);
    }

    @Transactional
    @Override
    public Resume updateResume(Long id, Resume resume) {
        Resume r = resumeRepository.findById(id).orElse(null);
        if (r == null) {
            throw new NotFoundException("不存在该用户简历");
        }
        BeanUtils.copyProperties(resume, r);
        return resumeRepository.save(r);
    }

    @Transactional
    @Override
    public void deleteResume(Long id) {
        resumeRepository.deleteById(id);
    }
}
