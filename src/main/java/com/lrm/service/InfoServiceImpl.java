package com.lrm.service;

import com.lrm.NotFoundException;
import com.lrm.dao.InfoRepository;
import com.lrm.po.Info;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jianengxi on 2020/2/21
 */
@Service
public class InfoServiceImpl implements InfoService{
    @Autowired
    InfoRepository infoRepository;

    @Transactional
    @Override
    public Info saveInfo(Info info) {
        return infoRepository.save(info);
    }

    @Transactional
    @Override
    public Info getInfo(Long id) {
        return infoRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Info getInfoByUser(Long userId) {
        return infoRepository.findByUserId(userId);
    }

    @Transactional
    @Override
    public Info updateInfo(Long id, Info info) {
        Info i = infoRepository.findById(id).orElse(null);
        if (i == null) {
            throw new NotFoundException("不存在该用户信息");
        }
        BeanUtils.copyProperties(info, i);
        return infoRepository.save(i);
    }

    @Transactional
    @Override
    public void deleteInfo(Long id) {
        infoRepository.deleteById(id);
    }
}
