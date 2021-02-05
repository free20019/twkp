package com.twkj.service;

import com.sun.xml.internal.bind.v2.model.core.ID;
import com.twkj.dao.ProjectManageDao;
import com.twkj.helper.JacksonUtil;
import oracle.sql.DATE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: xianlehuang
 * @Description:项目管理
 * @date: 2020/6/5 - 13:43
 */
@Service
public class ProjectManageService {

    @Autowired
    ProjectManageDao projectManageDao;

    private JacksonUtil jacksonUtil = JacksonUtil.buildNormalBinder();

    public String findCatalog(HttpServletRequest request) {
        String catalog_name = request.getParameter("catalog_name")==null?"":request.getParameter("catalog_name");
        String stime = request.getParameter("stime")==null?"":request.getParameter("stime");
        String etime = request.getParameter("etime")==null?"":request.getParameter("etime");
        String user_name = request.getParameter("user_name")==null?"":request.getParameter("user_name");
        String catalog_description = request.getParameter("catalog_description")==null?"":request.getParameter("catalog_description");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("catalog_name",catalog_name);
        map.put("stime",stime);
        map.put("etime",etime);
        map.put("user_name",user_name);
        map.put("catalog_description",catalog_description);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = projectManageDao.findCatalog(map);
        }catch (Exception e){
            return jacksonUtil.toJson(list);
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).put("DBTIME", list.get(i).get("DBTIME")==null?"":String.valueOf(list.get(i).get("DBTIME")).substring(0,19));
        }
        return jacksonUtil.toJson(list);
    }

    public synchronized String addCatalog(HttpServletRequest request) {
        String catalog_name = request.getParameter("catalog_name")==null?"":request.getParameter("catalog_name");
        String user_id = request.getSession().getAttribute("userid") == null?"":String.valueOf(request.getSession().getAttribute("userid"));
        String user_name = request.getSession().getAttribute("username") == null?"":String.valueOf(request.getSession().getAttribute("username"));
        String catalog_description = request.getParameter("catalog_description")==null?"":request.getParameter("catalog_description");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("catalog_name",catalog_name);
        map.put("user_id",user_id);
        map.put("user_name",user_name);
        map.put("catalog_description",catalog_description);
        int count =0;
        try {
            count = projectManageDao.addCatalog(map);
        }catch (Exception e){
            count =0;
        }
        return jacksonUtil.toJson(count);
    }

    public String updateCatalog(HttpServletRequest request) {
        String catalog_name = request.getParameter("catalog_name")==null?"":request.getParameter("catalog_name");
        String user_id = request.getSession().getAttribute("userid") == null?"":String.valueOf(request.getSession().getAttribute("userid"));
        String user_name = request.getSession().getAttribute("username") == null?"":String.valueOf(request.getSession().getAttribute("username"));
        String catalog_description = request.getParameter("catalog_description")==null?"":request.getParameter("catalog_description");
        String id = request.getParameter("id")==null?"":request.getParameter("id");

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("catalog_name",catalog_name);
        map.put("user_id",user_id);
        map.put("user_name",user_name);
        map.put("catalog_description",catalog_description);
        map.put("id",id);
        int count =0;
        try {
            count = projectManageDao.updateCatalog(map);
        }catch (Exception e){
            count =0;
        }
        return jacksonUtil.toJson(count);
    }

    public String deleteCatalog(HttpServletRequest request) {
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        map.put("status","1");
        int count =0;
        try {
            map.put("catalog_id",id);
            List<Map<String, Object>> list = projectManageDao.findProject(map);
            if(list.size()>0){
                count =-1;
            }else{
                count = projectManageDao.deleteCatalog(map);
            }
        }catch (Exception e){
            count =0;
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return jacksonUtil.toJson(count);
    }

    public String findProject(HttpServletRequest request) {
        String project_name = request.getParameter("project_name")==null?"":request.getParameter("project_name");
        String stime = request.getParameter("stime")==null?"":request.getParameter("stime");
        String etime = request.getParameter("etime")==null?"":request.getParameter("etime");
        String user_name = request.getParameter("user_name")==null?"":request.getParameter("user_name");
        String project_description = request.getParameter("project_description")==null?"":request.getParameter("project_description");
        String catalog_id = request.getParameter("catalog_id")==null?"":request.getParameter("catalog_id");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("project_name",project_name);
        map.put("stime",stime);
        map.put("etime",etime);
        map.put("user_name",user_name);
        map.put("project_description",project_description);
        map.put("catalog_id",catalog_id);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = projectManageDao.findProject(map);
        }catch (Exception e){
            return jacksonUtil.toJson(list);
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).put("DBTIME", list.get(i).get("DBTIME")==null?"":String.valueOf(list.get(i).get("DBTIME")).substring(0,19));
        }
        return jacksonUtil.toJson(list);
    }

    public synchronized String addProject(HttpServletRequest request) {
        String project_name = request.getParameter("project_name")==null?"":request.getParameter("project_name");
        String user_id = request.getSession().getAttribute("userid") == null?"":String.valueOf(request.getSession().getAttribute("userid"));
        String user_name = request.getSession().getAttribute("username") == null?"":String.valueOf(request.getSession().getAttribute("username"));
        String project_description = request.getParameter("project_description")==null?"":request.getParameter("project_description");
        String catalog_id = request.getParameter("catalog_id")==null?"":request.getParameter("catalog_id");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("project_name",project_name);
        map.put("user_id",user_id);
        map.put("user_name",user_name);
        map.put("project_description",project_description);
        map.put("catalog_id",catalog_id);
        int count =0;
        try {
            count = projectManageDao.addProject(map);
        }catch (Exception e){
            count =0;
        }
        return jacksonUtil.toJson(count);
    }

    public String updateProject(HttpServletRequest request) {
        String project_name = request.getParameter("project_name")==null?"":request.getParameter("project_name");
        String user_id = request.getSession().getAttribute("userid") == null?"":String.valueOf(request.getSession().getAttribute("userid"));
        String user_name = request.getSession().getAttribute("username") == null?"":String.valueOf(request.getSession().getAttribute("username"));
        String project_description = request.getParameter("project_description")==null?"":request.getParameter("project_description");
        String id = request.getParameter("id")==null?"":request.getParameter("id");

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("project_name",project_name);
        map.put("user_id",user_id);
        map.put("user_name",user_name);
        map.put("project_description",project_description);
        map.put("id",id);
        int count =0;
        try {
            count = projectManageDao.updateProject(map);
        }catch (Exception e){
            count =0;
        }
        return jacksonUtil.toJson(count);
    }

    @Transactional
    public String deleteProject(HttpServletRequest request) {
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        map.put("status","1");
        int count =0;
        try {
            count = projectManageDao.deleteProject(map);
//            if(count>0){
//                projectManageDao.deleteDealContent_main(map);
//            }
        }catch (Exception e){
            count =0;
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return jacksonUtil.toJson(count);
    }

    @Transactional(rollbackFor = Exception.class)
    public String findDealContent(HttpServletRequest request) {
        String project_id = request.getParameter("project_id")==null?"":request.getParameter("project_id");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("project_id",project_id);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = projectManageDao.findDealContent(map);
            String user_name = request.getSession().getAttribute("username")==null?"":request.getSession().getAttribute("username").toString();
            map.put("user_name",user_name);
            projectManageDao.updateReadRecord(map);
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return jacksonUtil.toJson(list);
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).put("DBTIME", list.get(i).get("DBTIME")==null?"":String.valueOf(list.get(i).get("DBTIME")).substring(0,19));
        }
        return jacksonUtil.toJson(list);
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized String addDealContent(HttpServletRequest request) {
        String project_id = request.getParameter("project_id")==null?"":request.getParameter("project_id");
        String user_id = request.getSession().getAttribute("userid") == null?"":String.valueOf(request.getSession().getAttribute("userid"));
        String user_name = request.getSession().getAttribute("username") == null?"":String.valueOf(request.getSession().getAttribute("username"));
        String deal_content = request.getParameter("deal_content")==null?"":request.getParameter("deal_content");
        String title = request.getParameter("title")==null?"":request.getParameter("title");
        String receiver = request.getParameter("receiver")==null?"":request.getParameter("receiver");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("project_id",project_id);
        map.put("user_id",user_id);
        map.put("user_name",user_name);
        map.put("deal_content",deal_content);
        map.put("title",title);
        map.put("receiver",receiver);
        map.put("id","");
        int count =0;
        try {
            count = projectManageDao.addDealContent(map);
            if(count>0&&receiver.length()>0){
                String [] users = receiver.split(",");
                for (int i = 0; i < users.length; i++) {
                    map.put("user",users[i]);
                    projectManageDao.addReadRecord(map);
                }
            }
        }catch (Exception e){
            count =0;
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return jacksonUtil.toJson(count);
    }

    @Transactional(rollbackFor = Exception.class)
    public String updateDealContent(HttpServletRequest request) {
        String project_id = request.getParameter("project_id")==null?"":request.getParameter("project_id");
        String user_id = request.getSession().getAttribute("userid") == null?"":String.valueOf(request.getSession().getAttribute("userid"));
        String user_name = request.getSession().getAttribute("username") == null?"":String.valueOf(request.getSession().getAttribute("username"));
        String deal_content = request.getParameter("deal_content")==null?"":request.getParameter("deal_content");
        String title = request.getParameter("title")==null?"":request.getParameter("title");
        String receiver = request.getParameter("receiver")==null?"":request.getParameter("receiver");
        String id = request.getParameter("id")==null?"":request.getParameter("id");

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("project_id",project_id);
        map.put("user_id",user_id);
        map.put("user_name",user_name);
        map.put("deal_content",deal_content);
        map.put("title",title);
        map.put("receiver",receiver);
        map.put("id",id);
        int count =0;
        try {
            count = projectManageDao.updateDealContent(map);
            if(count>0){
                projectManageDao.deleteReadRecord(map);
                if(receiver.length()>0){
                    String [] users = receiver.split(",");
                    for (int i = 0; i < users.length; i++) {
                        map.put("user",users[i]);
                        projectManageDao.addReadRecord(map);
                    }
                }
            }
        }catch (Exception e){
            count =0;
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return jacksonUtil.toJson(count);
    }

    public String deleteDealContent(HttpServletRequest request) {
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        map.put("status","1");
        int count =0;
        try {
            count = projectManageDao.deleteDealContent(map);
        }catch (Exception e){
            count =0;
        }
        return jacksonUtil.toJson(count);
    }


    public String userReadProjectResult(HttpServletRequest request) {
        String user_name = request.getSession().getAttribute("username")==null?"":request.getSession().getAttribute("username").toString();
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            List<Map<String, Object>> list_catalog = projectManageDao.userReadProjectResult_catalog(user_name);
            List<Map<String, Object>> list_project = projectManageDao.userReadProjectResult_project(user_name);
            List<Map<String, Object>> list_record = projectManageDao.userReadProjectResult_record(user_name);
            map.put("catalog",list_catalog);
            map.put("project",list_project);
            map.put("record",list_record);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jacksonUtil.toJson(map);
    }
}
