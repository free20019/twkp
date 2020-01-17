package com.twkj.controllers;

import com.twkj.service.EvaluationManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: xianlehuang
 * @Description:考评管理
 * @date: 2019/12/5 - 14:51
 */
@Controller
@RequestMapping("/evaluation")
public class EvaluationManageAction {

    @Autowired
    private EvaluationManageService evaluationManageService;

    //每月考核
    @RequestMapping("/findMonthlyCheck")
    @ResponseBody
    public String findMonthlyCheck(HttpServletRequest request){
        String msg = evaluationManageService.findMonthlyCheck(request);
        return msg;
    }

    //每月考核详情
    @RequestMapping("/findMonthlyCheckDetails")
    @ResponseBody
    public String findMonthlyCheckDetails(HttpServletRequest request){
        String msg = evaluationManageService.findMonthlyCheckDetails(request);
        return msg;
    }

    //每月考核添加
    @RequestMapping("/addMonthlyCheck")
    @ResponseBody
    public String addMonthlyCheck(HttpServletRequest request){
        String msg = "";
        try {
            msg = evaluationManageService.addMonthlyCheck(request);
        }catch (Exception e){
            msg="0";
        }
        return msg;
    }

    //每月考核修改
    @RequestMapping("/updateMonthlyCheck")
    @ResponseBody
    public String updateMonthlyCheck(HttpServletRequest request){
        String msg = "";
        try {
            msg = evaluationManageService.updateMonthlyCheck(request);
        }catch (Exception e){
            msg="0";
        }
        return msg;
    }

    //每月考核删除
    @RequestMapping("/deleteMonthlyCheck")
    @ResponseBody
    public String deleteMonthlyCheck(HttpServletRequest request){
        String msg = "";
        try {
            msg = evaluationManageService.deleteMonthlyCheck(request);
        }catch (Exception e){
            msg="0";
        }
        return msg;
    }


    //月报管理
    @RequestMapping("/findMonthlyManage")
    @ResponseBody
    public String findMonthlyManage(HttpServletRequest request){
        String msg = evaluationManageService.findMonthlyManage(request);
        return msg;
    }


    //月报管理详情
    @RequestMapping("/findMonthlyManageDetails")
    @ResponseBody
    public String findMonthlyManageDetails(HttpServletRequest request){
        String msg = evaluationManageService.findMonthlyManageDetails(request);
        return msg;
    }

    //月报管理評分
    @RequestMapping("/auditMonthlyManage")
    @ResponseBody
    public String auditMonthlyManage(HttpServletRequest request){
        String msg = "";
        try {
            msg = evaluationManageService.auditMonthlyManage(request);
        }catch (Exception e){
            msg="0";
        }
        return msg;
    }
}
