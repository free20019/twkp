package com.twkj.service;

import com.sun.xml.internal.bind.v2.model.core.ID;
import com.twkj.helper.JacksonUtil;
import oracle.sql.DATE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author: xianlehuang
 * @Description:人事
 * @date: 2019/11/13 - 9:33
 */
@Service
public class PersonnelMattersService {
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    private JacksonUtil jacksonUtil = JacksonUtil.buildNormalBinder();


    public String getUser(HttpServletRequest request) {
        String sql = "select u.*, b.bm department from tuser u, tb_bm b" +
                " where u.bm = b.code" +
                " order by u.username";
        List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).put("COMMIT_DATE",list.get(i).get("COMMIT_DATE")==null?"":String.valueOf(list.get(i).get("COMMIT_DATE")).substring(0,10));
        }
        return jacksonUtil.toJson(list);
    }

    public String findPersonalMorningReport(HttpServletRequest request) {
        String person_name = request.getParameter("person_name")==null?"":request.getParameter("person_name");
        String department = request.getParameter("department")==null?"":request.getParameter("department");
        String stime = request.getParameter("stime")==null?"":request.getParameter("stime");
        String etime = request.getParameter("etime")==null?"":request.getParameter("etime");
        String tj = "";
        if(person_name!=null&&!person_name.isEmpty()&&!person_name.equals("null")&&person_name.length()>0){
            tj += " and a.person_name like '%"+person_name+"%'";
        }
        if(department!=null&&!department.isEmpty()&&!department.equals("null")&&department.length()>0){
            tj += " and a.department like '%"+department+"%'";
        }
        if(stime!=null&&!stime.isEmpty()&&!stime.equals("null")&&stime.length()>0){
            tj += " and a.COMMIT_DATE >= to_date('"+stime+"','yyyy-mm-dd')";
        }
        if(etime!=null&&!etime.isEmpty()&&!etime.equals("null")&&etime.length()>0){
            tj += " and a.COMMIT_DATE <= to_date('"+etime+"','yyyy-mm-dd')";
        }
        String sql = "select a.* from TB_PERSONAL_MORNING_REPORT a" +
                " where 1=1 ";
        sql += tj;
        sql +=" order by a.COMMIT_DATE desc, a.person_name";
        List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).put("COMMIT_DATE",list.get(i).get("COMMIT_DATE")==null?"":String.valueOf(list.get(i).get("COMMIT_DATE")).substring(0,10));
        }
        //管理权限
        list=auditList(request, list);
        return jacksonUtil.toJson(list);
    }

    public String addPersonalMorningReport(HttpServletRequest request) {
        String person_name = request.getParameter("person_name")==null?"":request.getParameter("person_name");
        String commit_date = request.getParameter("commit_date")==null?"":request.getParameter("commit_date");
        String yesterday_progress = request.getParameter("yesterday_progress")==null?"":request.getParameter("yesterday_progress");
        String await_solution = request.getParameter("await_solution")==null?"":request.getParameter("await_solution");
        String today_plan = request.getParameter("today_plan")==null?"":request.getParameter("today_plan");
        String department = request.getParameter("department")==null?"":request.getParameter("department");
        String userid = findUserID(person_name);
        int count = 0;
        String insert = "insert into TB_PERSONAL_MORNING_REPORT (person_name,commit_date,yesterday_progress,await_solution,today_plan,userid,department) values" +
                "('"+person_name+"',to_date('"+commit_date+"','yyyy-mm-dd'),'"+yesterday_progress+"','"+await_solution+"','"+today_plan+"','"+userid+"','"+department+"')";
        try {
            count = jdbcTemplate.update(insert);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }

    public String updatePersonalMorningReport(HttpServletRequest request) {
        String person_name = request.getParameter("person_name")==null?"":request.getParameter("person_name");
        String commit_date = request.getParameter("commit_date")==null?"":request.getParameter("commit_date");
        String yesterday_progress = request.getParameter("yesterday_progress")==null?"":request.getParameter("yesterday_progress");
        String await_solution = request.getParameter("await_solution")==null?"":request.getParameter("await_solution");
        String today_plan = request.getParameter("today_plan")==null?"":request.getParameter("today_plan");
        String department = request.getParameter("department")==null?"":request.getParameter("department");
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        String userid = findUserID(person_name);
        int count = 0;
        String update = "update TB_PERSONAL_MORNING_REPORT set " +
                " person_name='"+person_name+"'" +
                ",commit_date=to_date('"+commit_date+"','yyyy-mm-dd')" +
                ",yesterday_progress='"+yesterday_progress+"'" +
                ",await_solution='"+await_solution+"'" +
                ",today_plan='"+today_plan+"'" +
                ",department='"+department+"'" +
                ",userid='"+userid+"'" +
                " where id='"+id+"'";
        try {
            count = jdbcTemplate.update(update);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }

    public String deletePersonalMorningReport(HttpServletRequest request) {
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        int count = 0;
        String delete = "delete from TB_PERSONAL_MORNING_REPORT where id='"+id+"'";
        try {
            count = jdbcTemplate.update(delete);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }


    public String findWorkSummary(HttpServletRequest request) {
        String person_name = request.getParameter("person_name")==null?"":request.getParameter("person_name");
        String department = request.getParameter("department")==null?"":request.getParameter("department");
        String tj = "";
        if(person_name!=null&&!person_name.isEmpty()&&!person_name.equals("null")&&person_name.length()>0){
            tj += " and a.person_name like '%"+person_name+"%'";
        }
        if(department!=null&&!department.isEmpty()&&!department.equals("null")&&department.length()>0){
            tj += " and a.department like '%"+department+"%'";
        }
        String sql = "select a.* from tb_work_summary a" +
                " where 1=1";
        sql += tj;
        sql +=" order by a.CREATE_TIME desc";
        List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).put("JOIN_TIME",list.get(i).get("JOIN_TIME")==null?"":String.valueOf(list.get(i).get("JOIN_TIME")).substring(0,10));
            list.get(i).put("EVALUATION_DATE",list.get(i).get("EVALUATION_DATE")==null?"":String.valueOf(list.get(i).get("EVALUATION_DATE")).substring(0,10));
            list.get(i).put("CREATE_TIME",list.get(i).get("CREATE_TIME")==null?"":String.valueOf(list.get(i).get("CREATE_TIME")).substring(0,10));
        }
        //管理权限
        list=auditList(request, list);
        return jacksonUtil.toJson(list);
    }

    public String addWorkSummary(HttpServletRequest request) {
        String userid = request.getParameter("userid")==null?"":request.getParameter("userid");
        String age = request.getParameter("age")==null?"":request.getParameter("age");
        String sex = request.getParameter("sex")==null?"":request.getParameter("sex");
        String education = request.getParameter("education")==null?"":request.getParameter("education");
        String politic_countenance = request.getParameter("politic_countenance")==null?"":request.getParameter("politic_countenance");
        String join_time = request.getParameter("join_time")==null?"":request.getParameter("join_time");
        String work_summary = request.getParameter("work_summary")==null?"":request.getParameter("work_summary");
        String post = request.getParameter("post")==null?"":request.getParameter("post");
        String department = request.getParameter("department")==null?"":request.getParameter("department");
        int count = 0;
        String cx = "select * from tuser where userid='"+userid+"'";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(cx);
        if(list.size()>0){
            String insert = "insert into tb_work_summary (userid, person_name,age,sex,education,politic_countenance,join_time,work_summary,post,department,CREATE_TIME) values" +
                    "('"+userid+"','"+list.get(0).get("USERNAME")+"','"+age+"','"+sex+"','"+education+"','"+politic_countenance+"',to_date('"+join_time+"','yyyy-mm-dd'),'"+work_summary+"','"+post+"','"+department+"',sysdate)";
            try {
                count = jdbcTemplate.update(insert);
            }catch (Exception e){
                count = 0;
            }
        }
        return jacksonUtil.toJson(count);
    }

    public String evaluateWorkSummary(HttpServletRequest request) {
        String department_leader = request.getParameter("department_leader")==null?"":request.getParameter("department_leader");
        String evaluation_date = request.getParameter("evaluation_date")==null?"":request.getParameter("evaluation_date");
        String department_evaluation = request.getParameter("department_evaluation")==null?"":request.getParameter("department_evaluation");
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        int count = 0;
        String insert = "update tb_work_summary set " +
                " department_leader='"+department_leader+"'" +
                ",evaluation_date= to_date('"+evaluation_date+"','yyyy-mm-dd')" +
                ",department_evaluation='"+department_evaluation+"'" +
                " where id='"+id+"'";
        try {
            count = jdbcTemplate.update(insert);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }

    public String updateWorkSummary(HttpServletRequest request) {
        String userid = request.getParameter("userid")==null?"":request.getParameter("userid");
        String age = request.getParameter("age")==null?"":request.getParameter("age");
        String sex = request.getParameter("sex")==null?"":request.getParameter("sex");
        String education = request.getParameter("education")==null?"":request.getParameter("education");
        String politic_countenance = request.getParameter("politic_countenance")==null?"":request.getParameter("politic_countenance");
        String join_time = request.getParameter("join_time")==null?"":request.getParameter("join_time");
        String work_summary = request.getParameter("work_summary")==null?"":request.getParameter("work_summary");
        String post = request.getParameter("post")==null?"":request.getParameter("post");
        String department = request.getParameter("department")==null?"":request.getParameter("department");
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        int count = 0;
        String cx = "select * from tuser where userid='"+userid+"'";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(cx);
        try {
            String update = "update tb_work_summary set " +
                    " person_name='"+list.get(0).get("USERNAME")+"'" +
                    ",userid='"+userid+"'" +
                    ",age='"+age+"'" +
                    ",sex='"+sex+"'" +
                    ",education='"+education+"'" +
                    ",politic_countenance='"+politic_countenance+"'" +
                    ",join_time=to_date('"+join_time+"','yyyy-mm-dd')" +
                    ",work_summary='"+work_summary+"'" +
                    ",post='"+post+"'" +
                    ",department='"+department+"'" +
                    " where id='"+id+"'";
            count = jdbcTemplate.update(update);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }

    public String deleteWorkSummary(HttpServletRequest request) {
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        int count = 0;
        String delete = "delete from tb_work_summary where id='"+id+"'";
        try {
            count = jdbcTemplate.update(delete);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }

    public String findMeetingMinutes(HttpServletRequest request) {
        String stime = request.getParameter("stime")==null?"":request.getParameter("stime");
        String etime = request.getParameter("etime")==null?"":request.getParameter("etime");
        List<Map<String, Object>> list= new ArrayList<Map<String, Object>>();
        String tj = "";
        if(stime!=null&&!stime.isEmpty()&&!stime.equals("null")&&stime.length()>0){
            tj += " and a.MEETING_TIME >= to_date('"+stime+"','yyyy-mm-dd')";
        }
        if(etime!=null&&!etime.isEmpty()&&!etime.equals("null")&&etime.length()>0){
            tj += " and a.MEETING_TIME <= to_date('"+etime+"','yyyy-mm-dd')";
        }
        String sql = "select a.* from TB_MEETING_MINUTES a" +
                " where 1=1";
        sql += tj;
        sql +=" order by a.MEETING_TIME desc, a.id desc";
        try {
            list=jdbcTemplate.queryForList(sql);
        }catch (Exception e){
            return jacksonUtil.toJson(list);
        }

        for (int i = 0; i < list.size(); i++) {
            list.get(i).put("MEETING_TIME",list.get(i).get("MEETING_TIME")==null?"":String.valueOf(list.get(i).get("MEETING_TIME")).substring(0,10));
        }
        return jacksonUtil.toJson(list);
    }

    public String addMeetingMinutes(HttpServletRequest request) {
        String meeting_time = request.getParameter("meeting_time")==null?"":request.getParameter("meeting_time");
        String meeting_place = request.getParameter("meeting_place")==null?"":request.getParameter("meeting_place");
        String participants = request.getParameter("participants")==null?"":request.getParameter("participants");
        String host_meeting = request.getParameter("host_meeting")==null?"":request.getParameter("host_meeting");
        String meeting_record = request.getParameter("meeting_record")==null?"":request.getParameter("meeting_record");
        String job_summary = request.getParameter("job_summary")==null?"":request.getParameter("job_summary");
        String next_week_work = request.getParameter("next_week_work")==null?"":request.getParameter("next_week_work");
        String decision_matter = request.getParameter("decision_matter")==null?"":request.getParameter("decision_matter");
        int count = 0;
        String insert = "insert into TB_MEETING_MINUTES (meeting_time, meeting_place,participants,host_meeting,meeting_record,job_summary,next_week_work,decision_matter) values" +
                "(to_date('"+meeting_time+"','yyyy-mm-dd'),'"+meeting_place+"','"+participants+"','"+host_meeting+"','"+meeting_record+"','"+job_summary+"','"+next_week_work+"','"+decision_matter+"')";
        try {
            count = jdbcTemplate.update(insert);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }

    public String updateMeetingMinutes(HttpServletRequest request) {
        String meeting_time = request.getParameter("meeting_time")==null?"":request.getParameter("meeting_time");
        String meeting_place = request.getParameter("meeting_place")==null?"":request.getParameter("meeting_place");
        String participants = request.getParameter("participants")==null?"":request.getParameter("participants");
        String host_meeting = request.getParameter("host_meeting")==null?"":request.getParameter("host_meeting");
        String meeting_record = request.getParameter("meeting_record")==null?"":request.getParameter("meeting_record");
        String job_summary = request.getParameter("job_summary")==null?"":request.getParameter("job_summary");
        String next_week_work = request.getParameter("next_week_work")==null?"":request.getParameter("next_week_work");
        String decision_matter = request.getParameter("decision_matter")==null?"":request.getParameter("decision_matter");
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        int count = 0;
        try {
            String update = "update TB_MEETING_MINUTES set " +
                    "meeting_time=to_date('"+meeting_time+"','yyyy-mm-dd')" +
                    ",meeting_place='"+meeting_place+"'" +
                    ",participants='"+participants+"'" +
                    ",host_meeting='"+host_meeting+"'" +
                    ",meeting_record='"+meeting_record+"'" +
                    ",job_summary='"+job_summary+"'" +
                    ",next_week_work='"+next_week_work+"'" +
                    ",decision_matter='"+decision_matter+"'" +
                    " where id='"+id+"'";
            count = jdbcTemplate.update(update);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);

    }

    public String deleteMeetingMinutes(HttpServletRequest request) {
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        int count = 0;
        String delete = "delete from TB_MEETING_MINUTES where id='"+id+"'";
        try {
            count = jdbcTemplate.update(delete);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }

    public String findTurnoverHandover(HttpServletRequest request) {
        String person_name = request.getParameter("person_name")==null?"":request.getParameter("person_name");
        String post = request.getParameter("post")==null?"":request.getParameter("post");
        String tj = "";
        List<Map<String, Object>> list= new ArrayList<Map<String, Object>>();
        if(person_name!=null&&!person_name.isEmpty()&&!person_name.equals("null")&&person_name.length()>0){
            tj += " and a.person_name like '%"+person_name+"%'";
        }
        if(post!=null&&!post.isEmpty()&&!post.equals("null")&&post.length()>0){
            tj += " and a.post like '%"+post+"%'";
        }
        String status = request.getSession().getAttribute("status") == null?"":String.valueOf(request.getSession().getAttribute("status"));
        String qx_userid = request.getSession().getAttribute("userid") == null?"":String.valueOf(request.getSession().getAttribute("userid"));
        if(status!=null&&!status.isEmpty()&&!status.equals("null")&&status.length()>0){
            if("1001412".equals(qx_userid)){
                tj += " and a.NET_NAME is null";
            }else{
                if("1".equals(status)){
                    tj += " and a.DEPT_NAME is null";
                }else if("2".equals(status)){
                    tj += " and a.GENERAL_NAME is null";
                }else{
                    tj +=" and 1=0";
                }
            }

        }
        String sql = "select a.* from TB_TURNOVER_HANDOVER a" +
                " where 1=1 ";
        sql += tj;
        sql +=" order by a.ID desc";
        try {
            list=jdbcTemplate.queryForList(sql);
        }catch (Exception e){
            return jacksonUtil.toJson(list);
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).put("SEPARATION_TIME",list.get(i).get("SEPARATION_TIME")==null?"":String.valueOf(list.get(i).get("SEPARATION_TIME")).substring(0,10));
            list.get(i).put("MANAGER_TIME",list.get(i).get("MANAGER_TIME")==null?"":String.valueOf(list.get(i).get("MANAGER_TIME")).substring(0,10));
            list.get(i).put("DEPT_TIME",list.get(i).get("DEPT_TIME")==null?"":String.valueOf(list.get(i).get("DEPT_TIME")).substring(0,10));
            list.get(i).put("NET_TIME",list.get(i).get("NET_TIME")==null?"":String.valueOf(list.get(i).get("NET_TIME")).substring(0,10));
            list.get(i).put("GENERAL_TIME",list.get(i).get("GENERAL_TIME")==null?"":String.valueOf(list.get(i).get("GENERAL_TIME")).substring(0,10));

            list.get(i).put("DEPT_STATUS",list.get(i).get("DEPT_NAME")==null&&"".equals(list.get(i).get("DEPT_TIME"))&&list.get(i).get("DEPT_REMARKS")==null?"未审核":(list.get(i).get("DEPT_NAME")!=null?"审核通过（已签字）":"其他"));
            list.get(i).put("NET_STATUS",list.get(i).get("NET_NAME")==null&&"".equals(list.get(i).get("NET_TIME"))&&list.get(i).get("NET_REMARKS")==null?"未审核":(list.get(i).get("NET_NAME")!=null?"审核通过（已签字）":"其他"));
            list.get(i).put("GENERAL_STATUS",list.get(i).get("GENERAL_NAME")==null&&"".equals(list.get(i).get("GENERAL_TIME"))&&list.get(i).get("GENERAL_REMARKS")==null?"未审核":(list.get(i).get("GENERAL_NAME")!=null?"审核通过（已签字）":"其他"));
        }
        //管理权限(除去冯哥)
        if(!"1001412".equals(qx_userid)){
            list=auditList(request, list);
        }
        return jacksonUtil.toJson(list);
    }

    public String addTurnoverHandover(HttpServletRequest request) {
        String person_name = request.getParameter("person_name")==null?"":request.getParameter("person_name");
        String post = request.getParameter("post")==null?"":request.getParameter("post");
        String separation_time = request.getParameter("separation_time")==null?"":request.getParameter("separation_time");
        String work_handover = request.getParameter("work_handover")==null?"":request.getParameter("work_handover");
        String net_handover = request.getParameter("net_handover")==null?"":request.getParameter("net_handover");
        String general_handover = request.getParameter("general_handover")==null?"":request.getParameter("general_handover");
        String manager = request.getParameter("manager")==null?"":request.getParameter("manager");
        String manager_time = request.getParameter("manager_time")==null?"":request.getParameter("manager_time");
        String userid = findUserID(person_name);
        int count = 0;
        String insert = "insert into TB_TURNOVER_HANDOVER (person_name, post,separation_time,work_handover,net_handover,general_handover,manager,manager_time,userid) values" +
                "('"+person_name+"','"+post+"',to_date('"+separation_time+"','yyyy-mm-dd'),'"+work_handover+"','"+net_handover+"','"+general_handover+"','"+manager+"',to_date('"+manager_time+"','yyyy-mm-dd'),'"+userid+"')";
        try {
            count = jdbcTemplate.update(insert);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }

    public String auditTurnoverHandover(HttpServletRequest request) {
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        String type = request.getParameter("type")==null?"":request.getParameter("type");
        int count = 0;
        String insert = "";
        if("1".equals(type)){
            String dept_name = request.getParameter("dept_name")==null?"":request.getParameter("dept_name");
            String dept_time = request.getParameter("dept_time")==null?"":request.getParameter("dept_time");
            String dept_remarks = request.getParameter("dept_remarks")==null?"":request.getParameter("dept_remarks");
            insert += "update TB_TURNOVER_HANDOVER set " +
                    " dept_name='"+dept_name+"'" +
                    ",dept_time= to_date('"+dept_time+"','yyyy-mm-dd')" +
                    ",dept_remarks='"+dept_remarks+"'" +
                    " where id='"+id+"'";
        }else if("2".equals(type)){
            String net_name = request.getParameter("net_name")==null?"":request.getParameter("net_name");
            String net_time = request.getParameter("net_time")==null?"":request.getParameter("net_time");
            String net_remarks = request.getParameter("net_remarks")==null?"":request.getParameter("net_remarks");
            String general_name = request.getParameter("general_name")==null?"":request.getParameter("general_name");
            String general_time = request.getParameter("general_time")==null?"":request.getParameter("general_time");
            String general_remarks = request.getParameter("general_remarks")==null?"":request.getParameter("general_remarks");
            insert += "update TB_TURNOVER_HANDOVER set " +
                    " net_name='"+net_name+"'" +
                    ",net_time= to_date('"+net_time+"','yyyy-mm-dd')" +
                    ",net_remarks='"+net_remarks+"'" +
                    ",general_name='"+general_name+"'" +
                    ",general_time= to_date('"+general_time+"','yyyy-mm-dd')" +
                    ",general_remarks='"+general_remarks+"'" +
                    " where id='"+id+"'";

        }else if("3".equals(type)){
            String manager = request.getParameter("manager")==null?"":request.getParameter("manager");
            String manager_time = request.getParameter("manager_time")==null?"":request.getParameter("manager_time");
            insert += "update TB_TURNOVER_HANDOVER set " +
                    " manager='"+manager+"'" +
                    ",manager_time= to_date('"+manager_time+"','yyyy-mm-dd')" +
                    " where id='"+id+"'";
        }
        try {
            count = jdbcTemplate.update(insert);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }


    public String updateTurnoverHandover(HttpServletRequest request) {
        String person_name = request.getParameter("person_name")==null?"":request.getParameter("person_name");
        String post = request.getParameter("post")==null?"":request.getParameter("post");
        String separation_time = request.getParameter("separation_time")==null?"":request.getParameter("separation_time");
        String work_handover = request.getParameter("work_handover")==null?"":request.getParameter("work_handover");
        String net_handover = request.getParameter("net_handover")==null?"":request.getParameter("net_handover");
        String general_handover = request.getParameter("general_handover")==null?"":request.getParameter("general_handover");
        String manager = request.getParameter("manager")==null?"":request.getParameter("manager");
        String manager_time = request.getParameter("manager_time")==null?"":request.getParameter("manager_time");
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        String userid = findUserID(person_name);
        int count = 0;
        try {
            String update = "update TB_TURNOVER_HANDOVER set " +
                    "person_name='"+person_name+"'" +
                    ",post='"+post+"'" +
                    ",separation_time=to_date('"+separation_time+"','yyyy-mm-dd')" +
                    ",work_handover='"+work_handover+"'" +
                    ",net_handover='"+net_handover+"'" +
                    ",general_handover='"+general_handover+"'" +
                    ",manager='"+manager+"'" +
                    ",manager_time=to_date('"+manager_time+"','yyyy-mm-dd')" +
                    ",userid='"+userid+"'" +
                    " where id='"+id+"'";
            count = jdbcTemplate.update(update);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }

    public String deleteTurnoverHandover(HttpServletRequest request) {
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        int count = 0;
        String delete = "delete from TB_TURNOVER_HANDOVER where id='"+id+"'";
        try {
            count = jdbcTemplate.update(delete);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }

    public String findEmployeeRegistration(HttpServletRequest request) {
        String name = request.getParameter("name")==null?"":request.getParameter("name");
        String phone = request.getParameter("phone")==null?"":request.getParameter("phone");
        String title = request.getParameter("title")==null?"":request.getParameter("title");
        String tj = "";
        List<Map<String, Object>> list= new ArrayList<Map<String, Object>>();
        if(name!=null&&!name.isEmpty()&&!name.equals("null")&&name.length()>0){
            tj += " and a.name like '%"+name+"%'";
        }
        if(phone!=null&&!phone.isEmpty()&&!phone.equals("null")&&phone.length()>0){
            tj += " and a.phone like '%"+phone+"%'";
        }
        if(title!=null&&!title.isEmpty()&&!title.equals("null")&&title.length()>0){
            tj += " and a.title like '%"+title+"%'";
        }
        String sql = "select a.* from TB_EMPLOYEE_REGISTRATION a" +
                " where 1=1 ";
        sql += tj;
        sql +=" order by a.ID desc";
        try {
            list=jdbcTemplate.queryForList(sql);
        }catch (Exception e){
            return jacksonUtil.toJson(list);
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).put("BIRTH_DATE",list.get(i).get("BIRTH_DATE")==null?"":String.valueOf(list.get(i).get("BIRTH_DATE")).substring(0,10));
            list.get(i).put("MY_TIME",list.get(i).get("MY_TIME")==null?"":String.valueOf(list.get(i).get("MY_TIME")).substring(0,10));
            list.get(i).put("DEPARTMENT_TIME",list.get(i).get("DEPARTMENT_TIME")==null?"":String.valueOf(list.get(i).get("DEPARTMENT_TIME")).substring(0,10));
            list.get(i).put("HIRING_TIME",list.get(i).get("HIRING_TIME")==null?"":String.valueOf(list.get(i).get("HIRING_TIME")).substring(0,10));
            list.get(i).put("GENERAL_MANAGER_TIME",list.get(i).get("GENERAL_MANAGER_TIME")==null?"":String.valueOf(list.get(i).get("GENERAL_MANAGER_TIME")).substring(0,10));
            list.get(i).put("JOIN_TIME",list.get(i).get("JOIN_TIME")==null?"":String.valueOf(list.get(i).get("JOIN_TIME")).substring(0,10));
        }
        return jacksonUtil.toJson(list);
    }

    public String addEmployeeRegistration(HttpServletRequest request) {
        String name = request.getParameter("name")==null?"":request.getParameter("name");
        String sex = request.getParameter("sex")==null?"":request.getParameter("sex");
        String nation = request.getParameter("nation")==null?"":request.getParameter("nation");
        String birth_date = request.getParameter("birth_date")==null?"":request.getParameter("birth_date");
        String political_outlook = request.getParameter("political_outlook")==null?"":request.getParameter("political_outlook");
        String academic_degree = request.getParameter("academic_degree")==null?"":request.getParameter("academic_degree");
        String title = request.getParameter("title")==null?"":request.getParameter("title");
        String id_number = request.getParameter("id_number")==null?"":request.getParameter("id_number");
        String telphone = request.getParameter("telphone")==null?"":request.getParameter("telphone");
        String phone = request.getParameter("phone")==null?"":request.getParameter("phone");
        String postal_code = request.getParameter("postal_code")==null?"":request.getParameter("postal_code");
        String hukou_location = request.getParameter("hukou_location")==null?"":request.getParameter("hukou_location");
        String hukou_nature = request.getParameter("hukou_nature")==null?"":request.getParameter("hukou_nature");
        String marital_status = request.getParameter("marital_status")==null?"":request.getParameter("marital_status");
        String school_profession = request.getParameter("school_profession")==null?"":request.getParameter("school_profession");
        String current_address = request.getParameter("current_address")==null?"":request.getParameter("current_address");
        String hukou_address = request.getParameter("hukou_address")==null?"":request.getParameter("hukou_address");
        String experience_time = request.getParameter("experience_time")==null?"":request.getParameter("experience_time");
        String experience_unit = request.getParameter("experience_unit")==null?"":request.getParameter("experience_unit");
        String experience_job = request.getParameter("experience_job")==null?"":request.getParameter("experience_job");
        String experience_remarks = request.getParameter("experience_remarks")==null?"":request.getParameter("experience_remarks");
        String family_name = request.getParameter("family_name")==null?"":request.getParameter("family_name");
        String family_relationship = request.getParameter("family_relationship")==null?"":request.getParameter("family_relationship");
        String family_phone = request.getParameter("family_phone")==null?"":request.getParameter("family_phone");
        String family_unit = request.getParameter("family_unit")==null?"":request.getParameter("family_unit");
        String my_signature = request.getParameter("my_signature")==null?"":request.getParameter("my_signature");
        String my_time = request.getParameter("my_time")==null?"":request.getParameter("my_time");
        String my_want = request.getParameter("my_want")==null?"":request.getParameter("my_want");
        String department_signature = request.getParameter("department_signature")==null?"":request.getParameter("department_signature");
        String department_time = request.getParameter("department_time")==null?"":request.getParameter("department_time");
        String department_want = request.getParameter("department_want")==null?"":request.getParameter("department_want");
        String hiring_signature = request.getParameter("hiring_signature")==null?"":request.getParameter("hiring_signature");
        String hiring_time = request.getParameter("hiring_time")==null?"":request.getParameter("hiring_time");
        String hiring_want = request.getParameter("hiring_want")==null?"":request.getParameter("hiring_want");
        String general_manager_signature = request.getParameter("general_manager_signature")==null?"":request.getParameter("general_manager_signature");
        String general_manager_time = request.getParameter("general_manager_time")==null?"":request.getParameter("general_manager_time");
        String general_manager_want = request.getParameter("general_manager_want")==null?"":request.getParameter("general_manager_want");
        String join_time = request.getParameter("join_time")==null?"":request.getParameter("join_time");
        String userid = findUserID(name);
        int count = 0;
        String insert = "insert into TB_EMPLOYEE_REGISTRATION (name,sex,nation,birth_date,political_outlook,academic_degree,title" +
                ",id_number,telphone,phone,postal_code,hukou_location,hukou_nature,marital_status,school_profession,current_address" +
                ",hukou_address,experience_time,experience_unit,experience_job,experience_remarks,family_name,family_relationship" +
                ",family_phone,family_unit,my_signature,my_time,my_want,department_signature,department_time,department_want,hiring_signature" +
                ",hiring_time,hiring_want,general_manager_signature,general_manager_time,general_manager_want,join_time,userid) values" +
                "('"+name+"','"+sex+"','"+nation+"',to_date('"+birth_date+"','yyyy-mm-dd'),'"+political_outlook+"','"+academic_degree+"','"+title+"'" +
                ",'"+id_number+"','"+telphone+"','"+phone+"','"+postal_code+"','"+hukou_location+"','"+hukou_nature+"','"+marital_status+"'" +
                ",'"+school_profession+"','"+current_address+"','"+hukou_address+"','"+experience_time+"','"+experience_unit+"','"+experience_job+"'" +
                ",'"+experience_remarks+"','"+family_name+"','"+family_relationship+"','"+family_phone+"','"+family_unit+"','"+my_signature+"'" +
                ",to_date('"+my_time+"','yyyy-mm-dd'),'"+my_want+"','"+department_signature+"',to_date('"+department_time+"','yyyy-mm-dd'),'"+department_want+"','"+hiring_signature+"'" +
                ",to_date('"+hiring_time+"','yyyy-mm-dd'),'"+hiring_want+"','"+general_manager_signature+"',to_date('"+general_manager_time+"','yyyy-mm-dd'),'"+general_manager_want+"',to_date('"+join_time+"','yyyy-mm-dd'),'"+userid+"')";
        try {
            count = jdbcTemplate.update(insert);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }

    public String updateEmployeeRegistration(HttpServletRequest request) {
        String name = request.getParameter("name")==null?"":request.getParameter("name");
        String sex = request.getParameter("sex")==null?"":request.getParameter("sex");
        String nation = request.getParameter("nation")==null?"":request.getParameter("nation");
        String birth_date = request.getParameter("birth_date")==null?"":request.getParameter("birth_date");
        String political_outlook = request.getParameter("political_outlook")==null?"":request.getParameter("political_outlook");
        String academic_degree = request.getParameter("academic_degree")==null?"":request.getParameter("academic_degree");
        String title = request.getParameter("title")==null?"":request.getParameter("title");
        String id_number = request.getParameter("id_number")==null?"":request.getParameter("id_number");
        String telphone = request.getParameter("telphone")==null?"":request.getParameter("telphone");
        String phone = request.getParameter("phone")==null?"":request.getParameter("phone");
        String postal_code = request.getParameter("postal_code")==null?"":request.getParameter("postal_code");
        String hukou_location = request.getParameter("hukou_location")==null?"":request.getParameter("hukou_location");
        String hukou_nature = request.getParameter("hukou_nature")==null?"":request.getParameter("hukou_nature");
        String marital_status = request.getParameter("marital_status")==null?"":request.getParameter("marital_status");
        String school_profession = request.getParameter("school_profession")==null?"":request.getParameter("school_profession");
        String current_address = request.getParameter("current_address")==null?"":request.getParameter("current_address");
        String hukou_address = request.getParameter("hukou_address")==null?"":request.getParameter("hukou_address");
        String experience_time = request.getParameter("experience_time")==null?"":request.getParameter("experience_time");
        String experience_unit = request.getParameter("experience_unit")==null?"":request.getParameter("experience_unit");
        String experience_job = request.getParameter("experience_job")==null?"":request.getParameter("experience_job");
        String experience_remarks = request.getParameter("experience_remarks")==null?"":request.getParameter("experience_remarks");
        String family_name = request.getParameter("family_name")==null?"":request.getParameter("family_name");
        String family_relationship = request.getParameter("family_relationship")==null?"":request.getParameter("family_relationship");
        String family_phone = request.getParameter("family_phone")==null?"":request.getParameter("family_phone");
        String family_unit = request.getParameter("family_unit")==null?"":request.getParameter("family_unit");
        String my_signature = request.getParameter("my_signature")==null?"":request.getParameter("my_signature");
        String my_time = request.getParameter("my_time")==null?"":request.getParameter("my_time");
        String my_want = request.getParameter("my_want")==null?"":request.getParameter("my_want");
        String department_signature = request.getParameter("department_signature")==null?"":request.getParameter("department_signature");
        String department_time = request.getParameter("department_time")==null?"":request.getParameter("department_time");
        String department_want = request.getParameter("department_want")==null?"":request.getParameter("department_want");
        String hiring_signature = request.getParameter("hiring_signature")==null?"":request.getParameter("hiring_signature");
        String hiring_time = request.getParameter("hiring_time")==null?"":request.getParameter("hiring_time");
        String hiring_want = request.getParameter("hiring_want")==null?"":request.getParameter("hiring_want");
        String general_manager_signature = request.getParameter("general_manager_signature")==null?"":request.getParameter("general_manager_signature");
        String general_manager_time = request.getParameter("general_manager_time")==null?"":request.getParameter("general_manager_time");
        String general_manager_want = request.getParameter("general_manager_want")==null?"":request.getParameter("general_manager_want");
        String join_time = request.getParameter("join_time")==null?"":request.getParameter("join_time");
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        String userid = findUserID(name);
        int count = 0;
        String update = "update TB_EMPLOYEE_REGISTRATION set " +
                " name='"+name+"'" +
                ",sex='"+sex+"'" +
                ",nation='"+nation+"'" +
                ",birth_date=to_date('"+birth_date+"','yyyy-mm-dd')" +
                ",political_outlook='"+political_outlook+"'" +
                ",academic_degree='"+academic_degree+"'" +
                ",title='"+title+"'" +
                ",id_number='"+id_number+"'" +
                ",telphone='"+telphone+"'" +
                ",phone='"+phone+"'" +
                ",postal_code='"+postal_code+"'" +
                ",hukou_location='"+hukou_location+"'" +
                ",hukou_nature='"+hukou_nature+"'" +
                ",marital_status='"+marital_status+"'" +
                ",school_profession='"+school_profession+"'" +
                ",current_address='"+current_address+"'" +
                ",hukou_address='"+hukou_address+"'" +
                ",experience_time='"+experience_time+"'" +
                ",experience_unit='"+experience_unit+"'" +
                ",experience_job='"+experience_job+"'" +
                ",experience_remarks='"+experience_remarks+"'" +
                ",family_name='"+family_name+"'" +
                ",family_relationship='"+family_relationship+"'" +
                ",family_phone='"+family_phone+"'" +
                ",family_unit='"+family_unit+"'" +
                ",my_signature='"+my_signature+"'" +
                ",my_time=to_date('"+my_time+"','yyyy-mm-dd')" +
                ",my_want='"+my_want+"'" +
                ",department_signature='"+department_signature+"'" +
                ",department_time=to_date('"+department_time+"','yyyy-mm-dd')" +
                ",department_want='"+department_want+"'" +
                ",hiring_signature='"+hiring_signature+"'" +
                ",hiring_time=to_date('"+hiring_time+"','yyyy-mm-dd')" +
                ",hiring_want='"+hiring_want+"'" +
                ",general_manager_signature='"+general_manager_signature+"'" +
                ",general_manager_time=to_date('"+general_manager_time+"','yyyy-mm-dd')" +
                ",general_manager_want='"+general_manager_want+"'" +
                ",join_time=to_date('"+join_time+"','yyyy-mm-dd')" +
                ",userid='"+userid+"'" +
                " where id='"+id+"'";
        try {
            count = jdbcTemplate.update(update);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }

    public String deleteEmployeeRegistration(HttpServletRequest request) {
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        int count = 0;
        String delete = "delete from TB_EMPLOYEE_REGISTRATION where id='"+id+"'";
        try {
            count = jdbcTemplate.update(delete);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }

    public String findEmployeeLeaveApplication(HttpServletRequest request) {
        String name = request.getParameter("name")==null?"":request.getParameter("name");
        String department = request.getParameter("department")==null?"":request.getParameter("department");
        String title = request.getParameter("title")==null?"":request.getParameter("title");
        String tj = "";
        List<Map<String, Object>> list= new ArrayList<Map<String, Object>>();
        if(name!=null&&!name.isEmpty()&&!name.equals("null")&&name.length()>0){
            tj += " and a.name like '%"+name+"%'";
        }
        if(department!=null&&!department.isEmpty()&&!department.equals("null")&&department.length()>0){
            tj += " and a.department like '%"+department+"%'";
        }
        if(title!=null&&!title.isEmpty()&&!title.equals("null")&&title.length()>0){
            tj += " and a.title like '%"+title+"%'";
        }
        String status = request.getSession().getAttribute("status") == null?"":String.valueOf(request.getSession().getAttribute("status"));
        if(status!=null&&!status.isEmpty()&&!status.equals("null")&&status.length()>0){
            if("1".equals(status)){
                tj += " and a.DEPARTMENT_SIGNATURE is null";
            }else if("2".equals(status)){
                tj += " and a.HIRING_SIGNATURE is null";
            }else if("3".equals(status)){
                tj += " and a.GENERAL_MANAGER_SIGNATURE is null";
            }else{
                tj +=" and 1=0";
            }
        }
        String sql = "select a.* from TB_EMPLOYEE_LEAVE_APPLICATION a" +
                " where 1=1 ";
        sql += tj;
        sql +=" order by a.ID desc";
        try {
            list=jdbcTemplate.queryForList(sql);
        }catch (Exception e){
            return jacksonUtil.toJson(list);
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).put("JOIN_TIME",list.get(i).get("JOIN_TIME")==null?"":String.valueOf(list.get(i).get("JOIN_TIME")).substring(0,10));
            list.get(i).put("LEAVE_TIME",list.get(i).get("LEAVE_TIME")==null?"":String.valueOf(list.get(i).get("LEAVE_TIME")).substring(0,10));
            list.get(i).put("DEPARTMENT_TIME",list.get(i).get("DEPARTMENT_TIME")==null?"":String.valueOf(list.get(i).get("DEPARTMENT_TIME")).substring(0,10));
            list.get(i).put("HIRING_TIME",list.get(i).get("HIRING_TIME")==null?"":String.valueOf(list.get(i).get("HIRING_TIME")).substring(0,10));
            list.get(i).put("GENERAL_MANAGER_TIME",list.get(i).get("GENERAL_MANAGER_TIME")==null?"":String.valueOf(list.get(i).get("GENERAL_MANAGER_TIME")).substring(0,10));
            list.get(i).put("ADD_TIME",list.get(i).get("ADD_TIME")==null?"":String.valueOf(list.get(i).get("ADD_TIME")).substring(0,10));
        }
        //管理权限
        list=auditList(request, list);
        return jacksonUtil.toJson(list);
    }

    public String addEmployeeLeaveApplication(HttpServletRequest request) {
        String name = request.getParameter("name")==null?"":request.getParameter("name");
        String department = request.getParameter("department")==null?"":request.getParameter("department");
        String title = request.getParameter("title")==null?"":request.getParameter("title");
        String join_time = request.getParameter("join_time")==null?"":request.getParameter("join_time");
        String leave_time = request.getParameter("leave_time")==null?"":request.getParameter("leave_time");
        String add_time = request.getParameter("add_time")==null?"":request.getParameter("add_time");
        String leave_reason = request.getParameter("leave_reason")==null?"":request.getParameter("leave_reason");
        String leave_signature = request.getParameter("leave_signature")==null?"":request.getParameter("leave_signature");
        String userid = findUserID(name);
        int count = 0;
        String insert = "insert into TB_EMPLOYEE_LEAVE_APPLICATION (name,department,title,join_time,leave_time,add_time,leave_reason,leave_signature,userid) values" +
                "('"+name+"','"+department+"','"+title+"',to_date('"+join_time+"','yyyy-mm-dd'),to_date('"+leave_time+"','yyyy-mm-dd'),to_date('"+add_time+"','yyyy-mm-dd'),'"+leave_reason+"','"+leave_signature+"','"+userid+"')";
        try {
            count = jdbcTemplate.update(insert);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }


    public String updateEmployeeLeaveApplication(HttpServletRequest request) {
        String name = request.getParameter("name")==null?"":request.getParameter("name");
        String department = request.getParameter("department")==null?"":request.getParameter("department");
        String title = request.getParameter("title")==null?"":request.getParameter("title");
        String join_time = request.getParameter("join_time")==null?"":request.getParameter("join_time");
        String leave_time = request.getParameter("leave_time")==null?"":request.getParameter("leave_time");
        String add_time = request.getParameter("add_time")==null?"":request.getParameter("add_time");
        String leave_reason = request.getParameter("leave_reason")==null?"":request.getParameter("leave_reason");
        String leave_signature = request.getParameter("leave_signature")==null?"":request.getParameter("leave_signature");
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        String userid = findUserID(name);
        int count = 0;
        try {
            String update = "update TB_EMPLOYEE_LEAVE_APPLICATION set " +
                    "name='"+name+"'" +
                    ",department='"+department+"'" +
                    ",title='"+title+"'" +
                    ",join_time=to_date('"+join_time+"','yyyy-mm-dd')" +
                    ",leave_time=to_date('"+leave_time+"','yyyy-mm-dd')" +
                    ",add_time=to_date('"+add_time+"','yyyy-mm-dd')" +
                    ",leave_reason='"+leave_reason+"'" +
                    ",leave_signature='"+leave_signature+"'" +
                    ",userid='"+userid+"'" +
                    " where id='"+id+"'";
            count = jdbcTemplate.update(update);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }

    public String deleteEmployeeLeaveApplication(HttpServletRequest request) {
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        int count = 0;
        String delete = "delete from TB_EMPLOYEE_LEAVE_APPLICATION where id='"+id+"'";
        try {
            count = jdbcTemplate.update(delete);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }

    public String auditEmployeeLeaveApplication(HttpServletRequest request) {
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        String type = request.getParameter("type")==null?"":request.getParameter("type");
        int count = 0;
        String insert = "";
        if("1".equals(type)){
            String department_signature = request.getParameter("department_signature")==null?"":request.getParameter("department_signature");
            String department_time = request.getParameter("department_time")==null?"":request.getParameter("department_time");
            String department_approval = request.getParameter("department_approval")==null?"":request.getParameter("department_approval");
            insert += "update TB_EMPLOYEE_LEAVE_APPLICATION set " +
                    " department_signature='"+department_signature+"'" +
                    ",department_time= to_date('"+department_time+"','yyyy-mm-dd')" +
                    ",department_approval='"+department_approval+"'" +
                    " where id='"+id+"'";
        }else if("2".equals(type)){
            String hiring_signature = request.getParameter("hiring_signature")==null?"":request.getParameter("hiring_signature");
            String hiring_time = request.getParameter("hiring_time")==null?"":request.getParameter("hiring_time");
            String hiring_approval = request.getParameter("hiring_approval")==null?"":request.getParameter("hiring_approval");
            insert += "update TB_EMPLOYEE_LEAVE_APPLICATION set " +
                    " hiring_signature='"+hiring_signature+"'" +
                    ",hiring_time= to_date('"+hiring_time+"','yyyy-mm-dd')" +
                    ",hiring_approval='"+hiring_approval+"'" +
                    " where id='"+id+"'";

        }else if("3".equals(type)){
            String general_manager_signature = request.getParameter("general_manager_signature")==null?"":request.getParameter("general_manager_signature");
            String general_manager_time = request.getParameter("general_manager_time")==null?"":request.getParameter("general_manager_time");
            String general_manager_approval = request.getParameter("general_manager_approval")==null?"":request.getParameter("general_manager_approval");
            insert += "update TB_EMPLOYEE_LEAVE_APPLICATION set " +
                    " general_manager_signature='"+general_manager_signature+"'" +
                    ",general_manager_time= to_date('"+general_manager_time+"','yyyy-mm-dd')" +
                    ",general_manager_approval='"+general_manager_approval+"'" +
                    " where id='"+id+"'";
        }
        try {
            count = jdbcTemplate.update(insert);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }

    public String findCorrectionApplication(HttpServletRequest request) {
        String name = request.getParameter("name")==null?"":request.getParameter("name");
        String department = request.getParameter("department")==null?"":request.getParameter("department");
        String title = request.getParameter("title")==null?"":request.getParameter("title");
        String tj = "";
        List<Map<String, Object>> list= new ArrayList<Map<String, Object>>();
        if(name!=null&&!name.isEmpty()&&!name.equals("null")&&name.length()>0){
            tj += " and a.name like '%"+name+"%'";
        }
        if(department!=null&&!department.isEmpty()&&!department.equals("null")&&department.length()>0){
            tj += " and a.department like '%"+department+"%'";
        }
        if(title!=null&&!title.isEmpty()&&!title.equals("null")&&title.length()>0){
            tj += " and a.title like '%"+title+"%'";
        }
        String status = request.getSession().getAttribute("status") == null?"":String.valueOf(request.getSession().getAttribute("status"));
        if(status!=null&&!status.isEmpty()&&!status.equals("null")&&status.length()>0){
            if("1".equals(status)){
                tj += " and a.DEPARTMENT_IDEA is null";
            }else if("2".equals(status)){
                tj += " and a.HIRING_IDEA is null";
            }else if("3".equals(status)){
                tj += " and a.GENERAL_MANAGER_SIGNATURE is null";
            }else{
                tj +=" and 1=0";
            }
        }
        String sql = "select a.* from TB_CORRECTION_APPLICATION a" +
                " where 1=1 ";
        sql += tj;
        sql +=" order by a.ID desc";
        try {
            list=jdbcTemplate.queryForList(sql);
        }catch (Exception e){
            return jacksonUtil.toJson(list);
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).put("BIRTH_TIME",list.get(i).get("BIRTH_TIME")==null?"":String.valueOf(list.get(i).get("BIRTH_TIME")).substring(0,10));
            list.get(i).put("JOIN_TIME",list.get(i).get("JOIN_TIME")==null?"":String.valueOf(list.get(i).get("JOIN_TIME")).substring(0,10));
            list.get(i).put("GENERAL_MANAGER_TIME",list.get(i).get("GENERAL_MANAGER_TIME")==null?"":String.valueOf(list.get(i).get("GENERAL_MANAGER_TIME")).substring(0,10));
        }
        //管理权限
        list=auditList(request, list);
        return jacksonUtil.toJson(list);
    }

    public String addCorrectionApplication(HttpServletRequest request) {
        String name = request.getParameter("name")==null?"":request.getParameter("name");
        String sex = request.getParameter("sex")==null?"":request.getParameter("sex");
        String birth_time = request.getParameter("birth_time")==null?"":request.getParameter("birth_time");
        String id_number = request.getParameter("id_number")==null?"":request.getParameter("id_number");
        String school = request.getParameter("school")==null?"":request.getParameter("school");
        String academic_degree = request.getParameter("academic_degree")==null?"":request.getParameter("academic_degree");
        String profession = request.getParameter("profession")==null?"":request.getParameter("profession");
        String title = request.getParameter("title")==null?"":request.getParameter("title");
        String join_time = request.getParameter("join_time")==null?"":request.getParameter("join_time");
        String contract_period = request.getParameter("contract_period")==null?"":request.getParameter("contract_period");
        String trial_summary = request.getParameter("trial_summary")==null?"":request.getParameter("trial_summary");
        String userid = findUserID(name);
        int count = 0;
        String insert = "insert into TB_CORRECTION_APPLICATION (name,sex,birth_time,id_number,school,academic_degree,profession,title,join_time,contract_period,trial_summary,userid) values" +
                "('"+name+"','"+sex+"',to_date('"+birth_time+"','yyyy-mm-dd'),'"+id_number+"','"+school+"','"+academic_degree+"','"+profession+"','"+title+"',to_date('"+join_time+"','yyyy-mm-dd'),'"+contract_period+"','"+trial_summary+"','"+userid+"')";
        try {
            count = jdbcTemplate.update(insert);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }

    public String updateCorrectionApplication(HttpServletRequest request) {
        String name = request.getParameter("name")==null?"":request.getParameter("name");
        String sex = request.getParameter("sex")==null?"":request.getParameter("sex");
        String birth_time = request.getParameter("birth_time")==null?"":request.getParameter("birth_time");
        String id_number = request.getParameter("id_number")==null?"":request.getParameter("id_number");
        String school = request.getParameter("school")==null?"":request.getParameter("school");
        String academic_degree = request.getParameter("academic_degree")==null?"":request.getParameter("academic_degree");
        String profession = request.getParameter("profession")==null?"":request.getParameter("profession");
        String title = request.getParameter("title")==null?"":request.getParameter("title");
        String join_time = request.getParameter("join_time")==null?"":request.getParameter("join_time");
        String contract_period = request.getParameter("contract_period")==null?"":request.getParameter("contract_period");
        String trial_summary = request.getParameter("trial_summary")==null?"":request.getParameter("trial_summary");
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        String userid = findUserID(name);
        int count = 0;
        try {
            String update = "update TB_CORRECTION_APPLICATION set " +
                    "name='"+name+"'" +
                    ",sex='"+sex+"'" +
                    ",birth_time=to_date('"+birth_time+"','yyyy-mm-dd')" +
                    ",id_number='"+id_number+"'" +
                    ",school='"+school+"'" +
                    ",academic_degree='"+academic_degree+"'" +
                    ",profession='"+profession+"'" +
                    ",title='"+title+"'" +
                    ",join_time=to_date('"+join_time+"','yyyy-mm-dd')" +
                    ",contract_period='"+contract_period+"'" +
                    ",trial_summary='"+trial_summary+"'" +
                    ",userid='"+userid+"'" +
                    " where id='"+id+"'";
            count = jdbcTemplate.update(update);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }

    public String deleteCorrectionApplication(HttpServletRequest request) {
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        int count = 0;
        String delete = "delete from TB_CORRECTION_APPLICATION where id='"+id+"'";
        try {
            count = jdbcTemplate.update(delete);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }

    public String auditCorrectionApplication(HttpServletRequest request) {
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        String type = request.getParameter("type")==null?"":request.getParameter("type");
        int count = 0;
        String insert = "";
        if("1".equals(type)){
            String department_idea= request.getParameter("department_idea")==null?"":request.getParameter("department_idea");
            insert += "update TB_CORRECTION_APPLICATION set " +
                    " department_idea='"+department_idea+"'" +
                    " where id='"+id+"'";
        }else if("2".equals(type)){
            String hiring_idea = request.getParameter("hiring_idea")==null?"":request.getParameter("hiring_idea");
            insert += "update TB_CORRECTION_APPLICATION set " +
                    " hiring_idea='"+hiring_idea+"'" +
                    " where id='"+id+"'";

        }else if("3".equals(type)){
            String general_manager_signature = request.getParameter("general_manager_signature")==null?"":request.getParameter("general_manager_signature");
            String general_manager_time = request.getParameter("general_manager_time")==null?"":request.getParameter("general_manager_time");
            String general_manager_audit = request.getParameter("general_manager_audit")==null?"":request.getParameter("general_manager_audit");
            insert += "update TB_CORRECTION_APPLICATION set " +
                    " general_manager_signature='"+general_manager_signature+"'" +
                    ",general_manager_time= to_date('"+general_manager_time+"','yyyy-mm-dd')" +
                    ",general_manager_audit='"+general_manager_audit+"'" +
                    " where id='"+id+"'";
        }
        try {
            count = jdbcTemplate.update(insert);
        }catch (Exception e){
            count = 0;
        }
        return jacksonUtil.toJson(count);
    }

    public String findUserID(String name) {
        String userid = "";
        String sql = "select userid from tb_kp_user where username='"+name.trim()+"'";
        try {
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
            if(list.size()==1&&list.get(0).get("USERID")!=null){
                userid=list.get(0).get("USERID").toString();
            }
        }catch (Exception e){
            userid = "";
        }
        return userid;
    }

    private List<Map<String, Object>> auditList(HttpServletRequest request, List<Map<String, Object>> list) {
        String qx_power = request.getSession().getAttribute("power") == null?"":String.valueOf(request.getSession().getAttribute("power"));
        String qx_userid = request.getSession().getAttribute("userid") == null?"":String.valueOf(request.getSession().getAttribute("userid"));
        String qx_username = request.getSession().getAttribute("username") == null?"":String.valueOf(request.getSession().getAttribute("username"));
        String qx_department = request.getSession().getAttribute("department") == null?"":String.valueOf(request.getSession().getAttribute("department"));
        String qx_department_id = request.getSession().getAttribute("department_id") == null?"":String.valueOf(request.getSession().getAttribute("department_id"));
        //查看部分权限能查看的userid
        List<Map<String, Object>> listUser = new ArrayList<Map<String, Object>>();
        if("1".equals(qx_power)){
            String sql="select wm_concat(userid) userids from tb_kp_user where department_id='"+qx_department_id+"'";
            try {
                listUser = jdbcTemplate.queryForList(sql);
            }catch (Exception e){
                return new ArrayList<Map<String, Object>>();
            }
        }
        Iterator<Map<String, Object>> iterator = list.iterator();
        while (iterator.hasNext()){
            Map<String, Object> next = iterator.next();
            //判断是否为查看全部权限
            if(!"0".equals(qx_power)){
                //判断是否为查看部分权限
                if("1".equals(qx_power)){
                    if(listUser.size()==1&&listUser.get(0).get("USERIDS")!=null&&next.get("USERID")!=null){
                        if(listUser.get(0).get("USERIDS").toString().indexOf(next.get("USERID").toString())==-1){
                            iterator.remove();
                        }
                    }else{
                        iterator.remove();
                    }
                    //判断是否为查看個人权限
                }else if("2".equals(qx_power)){
                    if(!qx_userid.equals(String.valueOf(next.get("USERID")))){
                        iterator.remove();
                    }
                }else{
                    iterator.remove();
                }
            }
        }
        return list;
    }


}
