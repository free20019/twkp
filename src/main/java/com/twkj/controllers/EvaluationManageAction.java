package com.twkj.controllers;

import com.twkj.helper.DownloadAct;
import com.twkj.service.EvaluationManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    private DownloadAct downloadAct = new DownloadAct();

    //每月考核
    @RequestMapping("/findMonthlyCheck")
    @ResponseBody
    public String findMonthlyCheck(HttpServletRequest request){
        String msg = evaluationManageService.findMonthlyCheck(request);
        return msg;
    }

    //每月考核导出
    @RequestMapping("/findMonthlyCheckExcel")
    @ResponseBody
    public String findUserExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String a[] = {"姓名","日期","部门","自评分","考评总分"};//导出列明
        String b[] = {"NAME","TIME","DEPARTMENT","GRADE","AUDIT_GRADE"};//导出map中的key
        String time = request.getParameter("time")==null?"":request.getParameter("time");
        String gzb = "每月考核("+time+")";//导出sheet名和导出的文件名
        String msg = evaluationManageService.findMonthlyCheck(request);
        List<Map<String, Object>> list = DownloadAct.parseJSON2List2(msg);
        downloadAct.download(request,response,a,b,gzb,list);
        return null;
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

    //評分
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

    //mac
    @RequestMapping("/findMac")
    @ResponseBody
    public String findMac(HttpServletRequest request){
        String msg = evaluationManageService.findMac(request);
        return msg;
    }

    //mac导出
    @RequestMapping("/findMacExcel")
    @ResponseBody
    public String findMacExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String a[] = {"mac","wifi","详细地址"};//导出列明
        String b[] = {"MAC","WIFI","ADDRESS"};//导出map中的key
        String gzb = "mac";//导出sheet名和导出的文件名
        String msg = evaluationManageService.findMac(request);
        List<Map<String, Object>> list = DownloadAct.parseJSON2List2(msg);
        downloadAct.download(request,response,a,b,gzb,list);
        return null;
    }

    //mac
    @RequestMapping("/addMac")
    @ResponseBody
    public String addMac(HttpServletRequest request){
        String msg = evaluationManageService.addMac(request);
        return msg;
    }

    //mac
    @RequestMapping("/updateMac")
    @ResponseBody
    public String updateMac(HttpServletRequest request){
        String msg = evaluationManageService.updateMac(request);
        return msg;
    }

    //mac
    @RequestMapping("/deleteMac")
    @ResponseBody
    public String deleteMac(HttpServletRequest request){
        String msg = evaluationManageService.deleteMac(request);
        return msg;
    }
}
