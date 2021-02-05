package com.twkj.controllers;

import com.twkj.helper.DownloadAct;
import com.twkj.service.ProjectManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: xianlehuang
 * @Description:项目管理
 * @date: 2020/6/5 - 13:42
 */
@Controller
@RequestMapping("projectManage")
public class ProjectManageAction {

    @Autowired
    ProjectManageService projectManageService;

    private DownloadAct downloadAct = new DownloadAct();

    //目录查询
    @RequestMapping("/findCatalog")
    @ResponseBody
    public String findCatalog(HttpServletRequest request){
        String msg = projectManageService.findCatalog(request);
        return msg;
    }

    //目录添加
    @RequestMapping("/addCatalog")
    @ResponseBody
    public String addCatalog(HttpServletRequest request){
        String msg = projectManageService.addCatalog(request);
        return msg;
    }

    //目录修改
    @RequestMapping("/updateCatalog")
    @ResponseBody
    public String updateCatalog(HttpServletRequest request){
        String msg = projectManageService.updateCatalog(request);
        return msg;
    }

    //目录删除
    @RequestMapping("/deleteCatalog")
    @ResponseBody
    public String deleteCatalog(HttpServletRequest request){
        String msg = projectManageService.deleteCatalog(request);
        return msg;
    }

    //项目查询
    @RequestMapping("/findProject")
    @ResponseBody
    public String findProject(HttpServletRequest request){
        String msg = projectManageService.findProject(request);
        return msg;
    }

    //项目导出
    @RequestMapping("/findProjectExcel")
    @ResponseBody
    public String findProjectExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String a[] = {"项目","权限"};//导出列明
        String b[] = {"POST_NAME","POWER_NAME"};//导出map中的key
        String gzb = "项目";//导出sheet名和导出的文件名
        String msg = projectManageService.findProject(request);
        List<Map<String, Object>> list = DownloadAct.parseJSON2List2(msg);
        downloadAct.download(request,response,a,b,gzb,list);
        return null;
    }

    //项目添加
    @RequestMapping("/addProject")
    @ResponseBody
    public String addProject(HttpServletRequest request){
        String msg = projectManageService.addProject(request);
        return msg;
    }

    //项目修改
    @RequestMapping("/updateProject")
    @ResponseBody
    public String updateProject(HttpServletRequest request){
        String msg = projectManageService.updateProject(request);
        return msg;
    }

    //项目删除
    @RequestMapping("/deleteProject")
    @ResponseBody
    public String deleteProject(HttpServletRequest request){
        String msg = projectManageService.deleteProject(request);
        return msg;
    }

    //项目处置内容查询
    @RequestMapping("/findDealContent")
    @ResponseBody
    public String findDealContent(HttpServletRequest request){
        String msg = projectManageService.findDealContent(request);
        return msg;
    }

    //项目处置内容添加
    @RequestMapping("/addDealContent")
    @ResponseBody
    public String addDealContent(HttpServletRequest request){
        String msg = projectManageService.addDealContent(request);
        return msg;
    }

    //项目处置内容修改
    @RequestMapping("/updateDealContent")
    @ResponseBody
    public String updateDealContent(HttpServletRequest request){
        String msg = projectManageService.updateDealContent(request);
        return msg;
    }

    //项目处置内容删除
    @RequestMapping("/deleteDealContent")
    @ResponseBody
    public String deleteDealContent(HttpServletRequest request){
        String msg = projectManageService.deleteDealContent(request);
        return msg;
    }

    //项目管理该用户未读信息
    @RequestMapping("/userReadProjectResult")
    @ResponseBody
    public String userReadProjectResult(HttpServletRequest request){
        String msg = projectManageService.userReadProjectResult(request);
        return msg;
    }
    public void aaa(){
        HashMap<String, Object> hsmap = new HashMap<>();
        hsmap.put("1",1);
        hsmap.get("1");

//        int sshift = 0;
//        int ssize = 1;
//        while (ssize < concurrencyLevel) {
//            ++sshift;
//            ssize <<= 1;
//        }
//        this.segmentShift = 32 - sshift;
//        this.segmentMask = ssize - 1;

//        int j = (hash >>> segmentShift) & segmentMask;

        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        map.put("1",1);
        map.get("1");
        map.size();
    }
}


