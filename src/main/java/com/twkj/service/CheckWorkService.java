package com.twkj.service;

import com.twkj.dao.CheckWorkDao;
import com.twkj.dao.UserDao;
import com.twkj.helper.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: xianlehuang
 * @Description:
 * @date: 2019/12/19 - 14:34
 */
@Service
public class CheckWorkService {
    @Autowired
    private CheckWorkDao checkWorkDao;

    private JacksonUtil jacksonUtil = JacksonUtil.buildNormalBinder();
}
