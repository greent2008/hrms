package com.lrm.service;

import com.lrm.po.Resume;

/**
 * Created by jianengxi on 2020/2/21
 */
public interface ResumeService {
    Resume saveResume(Resume resume);

    Resume getResume(Long id);

    Resume getResumeByUser(Long userId);

    Resume updateResume(Long id, Resume resume);

    void deleteResume(Long id);
}
