package com.twkj.controllers;

import com.twkj.service.CheckWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: xianlehuang
 * @Description:
 * @date: 2019/12/19 - 14:33
 */
@Controller
@RequestMapping("/checkWork")
public class CheckWorkAction {

    @Autowired
    CheckWorkService checkWorkService;


}
