package com.lrm.service;

import com.lrm.po.Info;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by jianengxi on 2020/2/21
 */
public interface InfoService {
    Info saveInfo(Info info);

    Info getInfo(Long id);

    Info getInfoByUser(Long userId);

    Info updateInfo(Long id, Info info);

    void deleteInfo(Long id);
}
