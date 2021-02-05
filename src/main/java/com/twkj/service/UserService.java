package com.twkj.service;

import com.sun.xml.internal.bind.v2.model.core.ID;
import com.twkj.dao.UserDao;
import com.twkj.helper.DownloadAct;
import com.twkj.helper.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: xianlehuang
 * @Description:
 * @date: 2019/12/4 - 10:18
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PersonnelMattersService personnelMattersService;

    @Autowired
    private FinanceService financeService;

    @Autowired
    private CommonService commonService;

    private JacksonUtil jacksonUtil = JacksonUtil.buildNormalBinder();

    public String power(HttpServletRequest request) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = userDao.power();
        }catch (Exception e){
            return jacksonUtil.toJson(list);
        }
        return jacksonUtil.toJson(list);
    }

    public String login(HttpServletRequest request) {
        String loginname = request.getParameter("loginname")==null?"":request.getParameter("loginname");
        String password = request.getParameter("password")==null?"":request.getParameter("password");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("loginname",loginname);
        map.put("password",password);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = userDao.login(map);
        }catch (Exception e){
            return jacksonUtil.toJson(list);
        }
        if(list.size()==1){
            request.getSession().setAttribute("userid", list.get(0).get("USERID"));
            request.getSession().setAttribute("username", list.get(0).get("USERNAME"));
            request.getSession().setAttribute("post_name", list.get(0).get("POST_NAME"));
            request.getSession().setAttribute("power_name", list.get(0).get("POWER_NAME"));
            request.getSession().setAttribute("power_id", list.get(0).get("POWER_ID"));
            request.getSession().setAttribute("department", list.get(0).get("DEPARTMENT"));
            request.getSession().setAttribute("department_id", list.get(0).get("DEPARTMENT_ID"));
            request.getSession().setAttribute("power", list.get(0).get("POWER"));
            request.getSession().setAttribute("list", list);
            request.getSession().setMaxInactiveInterval(-1);
        }else{
            request.getSession().setAttribute("list", new ArrayList<Map<String, Object>>());
        }
        return jacksonUtil.toJson(list);
    }


    public String index(HttpServletRequest request) {
        List<Map<String, Object>> list = (List<Map<String, Object>>) request.getSession().getAttribute("list");
        return jacksonUtil.toJson(list);
    }

    public String findPost(HttpServletRequest request) {
        String post_name = request.getParameter("post_name")==null?"":request.getParameter("post_name");
        String power_id = request.getParameter("power_id")==null?"":request.getParameter("power_id");
        String power_name = request.getParameter("power_name")==null?"":request.getParameter("power_name");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("post_name",post_name);
        map.put("power_id",power_id);
        map.put("power_name",power_name);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = userDao.findPost(map);
        }catch (Exception e){
            return jacksonUtil.toJson(list);
        }
        return jacksonUtil.toJson(list);
    }

    public synchronized String addPost(HttpServletRequest request) {
        String post_name = request.getParameter("post_name")==null?"":request.getParameter("post_name");
        String power_id = request.getParameter("power_id")==null?"":request.getParameter("power_id");
        String power_name = request.getParameter("power_name")==null?"":request.getParameter("power_name");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("post_name",post_name);
        map.put("power_id",power_id);
        map.put("power_name",power_name);
        int count =0;
        try {
            count = userDao.addPost(map);
        }catch (Exception e){
            count =0;
        }
        return jacksonUtil.toJson(count);
    }

    public String updatePost(HttpServletRequest request) {
        String post_name = request.getParameter("post_name")==null?"":request.getParameter("post_name");
        String power_id = request.getParameter("power_id")==null?"":request.getParameter("power_id");
        String power_name = request.getParameter("power_name")==null?"":request.getParameter("power_name");
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("post_name",post_name);
        map.put("power_id",power_id);
        map.put("power_name",power_name);
        map.put("id",id);
        int count =0;
        try {
            count = userDao.updatePost(map);
        }catch (Exception e){
            count =0;
        }
        return jacksonUtil.toJson(count);
    }

    public String deletePost(HttpServletRequest request) {
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        int count =0;
        try {
            count = userDao.deletePost(map);
        }catch (Exception e){
            count =0;
        }
        return jacksonUtil.toJson(count);
    }

    public String department(HttpServletRequest request) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = userDao.department();
        }catch (Exception e){
            return jacksonUtil.toJson(list);
        }
        return jacksonUtil.toJson(list);
    }

    public String findUser(HttpServletRequest request) {
        String department = request.getParameter("department")==null?"":request.getParameter("department");
        String username = request.getParameter("username")==null?"":request.getParameter("username");
        String lzzt = request.getParameter("lzzt")==null?"":request.getParameter("lzzt");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("department",department);
        map.put("username",username);
        map.put("lzzt",lzzt);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = userDao.findUser(map);
        }catch (Exception e){
            return jacksonUtil.toJson(list);
        }
        return jacksonUtil.toJson(list);
    }

    public synchronized String addUser(HttpServletRequest request) {
        String department_id = request.getParameter("department_id")==null?"":request.getParameter("department_id");
        String username = request.getParameter("username")==null?"":request.getParameter("username");
        String password = request.getParameter("password")==null?"":request.getParameter("password");
        String post_id = request.getParameter("post_id")==null?"":request.getParameter("post_id");
        String loginname = request.getParameter("loginname")==null?"":request.getParameter("loginname");
        String power = request.getParameter("power")==null?"":request.getParameter("power");
        String userid = request.getParameter("userid")==null?"":request.getParameter("userid");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("department_id",department_id);
        map.put("username",username.trim());
        map.put("password",password);
        map.put("post_id",post_id);
        map.put("loginname",loginname);
        map.put("power",power);
        map.put("userid",userid);
        int count =0;
        try {
            count = userDao.addUser(map);
        }catch (Exception e){
            count =0;
        }
        return jacksonUtil.toJson(count);
    }

    public String updateUser(HttpServletRequest request) {
        String department_id = request.getParameter("department_id")==null?"":request.getParameter("department_id");
        String username = request.getParameter("username")==null?"":request.getParameter("username");
        String password = request.getParameter("password")==null?"":request.getParameter("password");
        String post_id = request.getParameter("post_id")==null?"":request.getParameter("post_id");
        String loginname = request.getParameter("loginname")==null?"":request.getParameter("loginname");
        String power = request.getParameter("power")==null?"":request.getParameter("power");
        String userid = request.getParameter("userid")==null?"":request.getParameter("userid");
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("department_id",department_id);
        map.put("username",username.trim());
        map.put("password",password);
        map.put("post_id",post_id);
        map.put("loginname",loginname);
        map.put("power",power);
        map.put("userid",userid);
        map.put("id",id);
        int count =0;
        try {
            count = userDao.updateUser(map);
        }catch (Exception e){
            count =0;
        }
        return jacksonUtil.toJson(count);
    }

    public String deleteUser(HttpServletRequest request) {
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        int count =0;
        try {
            count = userDao.deleteUser(map);
        }catch (Exception e){
            count =0;
        }
        return jacksonUtil.toJson(count);
    }

    public String auditUserStatus(HttpServletRequest request) {
        String lzzt = request.getParameter("lzzt")==null?"":request.getParameter("lzzt");
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("lzzt",lzzt);
        map.put("id",id);
        int count =0;
        try {
            count = userDao.auditUserStatus(map);
        }catch (Exception e){
            count =0;
        }
        return jacksonUtil.toJson(count);
    }

    public String findDaily(String username, String department, String time, String type) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("username",username);
        map.put("department",department);
        map.put("time",time);
        int maxDate=0;
        Map<String, Object> resultMap= new HashMap<String, Object>();
        if (time != null && !time.isEmpty() && !time.equals("null") && time.length() > 0) {
            String[] year_month = time.split("-");
            Calendar cal = Calendar.getInstance();
            int year = Integer.parseInt(year_month[0]);
            int month = Integer.parseInt(year_month[1]) - 1;
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DATE, 1);
            cal.roll(Calendar.DATE, -1);
            maxDate = cal.get(Calendar.DATE);
        }
        List<LinkedHashMap<String, Object>> list1 = new ArrayList<LinkedHashMap<String, Object>>();
        List<LinkedHashMap<String, Object>> list2 = new ArrayList<LinkedHashMap<String, Object>>();
        try {
            list1 = userDao.findDaily1(map);
            list2 = userDao.findDaily2(map);
            List<String> dateList=findHoliday(time.replace("-",""),maxDate);
            if("1".equals(type)){
                resultMap.put("list2",handle(list2, list1, maxDate, time, dateList));
                resultMap.put("list1", handle(list1, list2, maxDate, time, dateList));
            }else if("2".equals(type)){
                resultMap.put("list1", handle(list1, list2, maxDate, time, dateList));
                resultMap.put("list2",handle(list2, list1, maxDate, time, dateList));
            }
            return jacksonUtil.toJson(resultMap);

        }catch (Exception e){
            return jacksonUtil.toJson(resultMap);
        }
    }

    private List<LinkedHashMap<String, Object>> handle(List<LinkedHashMap<String, Object>> list, List<LinkedHashMap<String, Object>> other, Integer maxDate, String time, List<String> dateList) {
        String[] a={"事假","病假","婚假","年休假","调休假","妊娠假","陪产假","探亲假","其他"};
        List<String> leave = Arrays.asList(a);
        for(int i=0;i<list.size();i++){
            float cq = 0,sj = 0,bj=0,hj=0,nj=0,txj=0,rsj=0,pcj=0,tqj=0,qj=0,jjr=0,qt=0,kg=0;
            String day="";
            for(int m=1;m<=maxDate;m++){
                String item=String.valueOf(list.get(i).get("QJ"+m));
                String item2=String.valueOf(other.get(i).get("QJ"+m));
                if(m<10){
                    day = time.replace("-","")+"0"+m;
                }else{
                    day = time.replace("-","")+m;
                }

                if(leave.indexOf(item)==0||leave.indexOf(item2)==0){
                    if(leave.indexOf(item)==0){
                        sj += 0.5;
                        list.get(i).put("KQTB"+m,"⚪");
                    }
                    if(leave.indexOf(item2)==0){
                        sj += 0.5;
                    }
                }else if(leave.indexOf(item)==1||leave.indexOf(item2)==1){
                    if(leave.indexOf(item)==1){
                        bj += 0.5;
                        list.get(i).put("KQTB"+m,"▲");
                    }
                    if(leave.indexOf(item2)==1){
                        bj += 0.5;
                    }
                }else if(leave.indexOf(item)==2||leave.indexOf(item2)==2){
                    if(leave.indexOf(item)==2){
                        hj += 0.5;
                        list.get(i).put("KQTB"+m,"婚");
                    }
                    if(leave.indexOf(item2)==2){
                        hj += 0.5;
                    }
                }else if(leave.indexOf(item)==3||leave.indexOf(item2)==3){
                    if(leave.indexOf(item)==3){
                        nj += 0.5;
                        list.get(i).put("KQTB"+m,"年");
                    }
                    if(leave.indexOf(item2)==3){
                        nj += 0.5;
                    }
                }else if(leave.indexOf(item)==4||leave.indexOf(item2)==4){
                    if(leave.indexOf(item)==4){
                        txj += 0.5;
                        list.get(i).put("KQTB"+m,"L");
                    }
                    if(leave.indexOf(item2)==4){
                        txj += 0.5;
                    }
                }else if(leave.indexOf(item)==5||leave.indexOf(item2)==5){
                    if(leave.indexOf(item)==5){
                        rsj += 0.5;
                        list.get(i).put("KQTB"+m,"什");
                    }
                    if(leave.indexOf(item2)==5){
                        rsj += 0.5;
                    }
                }else if(leave.indexOf(item)==6||leave.indexOf(item2)==6){
                    if(leave.indexOf(item)==6){
                        pcj += 0.5;
                        qt += 0.5;
                        list.get(i).put("KQTB"+m,"什");
                    }
                    if(leave.indexOf(item2)==6){
                        pcj += pcj+0.5;
                        qt += qt+0.5;
                    }
                }else if(leave.indexOf(item)==7||leave.indexOf(item2)==7){
                    if(leave.indexOf(item)==7){
                        tqj += 0.5;
                        qt += 0.5;
                        list.get(i).put("KQTB"+m,"什");
                    }
                    if(leave.indexOf(item2)==7){
                        tqj += 0.5;
                        qt += 0.5;
                    }
                }else if(leave.indexOf(item)==8||leave.indexOf(item2)==8){
                    if(leave.indexOf(item)==8){
                        qj += 0.5;
                        qt += 0.5;
                        list.get(i).put("KQTB"+m,"什");
                    }
                    if(leave.indexOf(item2)==8){
                        qj += 0.5;
                        qt += 0.5;
                    }
                }

                if(!item.equals("null")){
                    list.get(i).put("KQ"+m,item);
                }else if(!String.valueOf(list.get(i).get("JB"+m)).equals("null")){
                    cq +=0.5;
                    list.get(i).put("KQ"+m,"加班");
                    list.get(i).put("KQTB"+m,"●");
                }else if(!String.valueOf(list.get(i).get("BG"+m)).equals("null")){
                    cq +=0.5;
//                    list.get(i).put("KQ"+m,"外勤");
                    list.get(i).put("KQ"+m,"出勤");
                    list.get(i).put("KQTB"+m,"/");
                }else if(!String.valueOf(list.get(i).get("QD"+m)).equals("null")){
                    cq +=0.5;
//                    cq ++;
                    if(String.valueOf(list.get(i).get("QD"+m)).equals("正常")){
                        list.get(i).put("KQ"+m,"出勤");
                        list.get(i).put("KQTB"+m,"/");
                    }else if(String.valueOf(list.get(i).get("QD"+m)).equals("不正常")){
                        list.get(i).put("KQ"+m,"迟到/早退");
                        list.get(i).put("KQTB"+m,"*");
                    }
                }else if(compare_date(day) == 1){
                    list.get(i).put("KQ"+m,"");
                    list.get(i).put("KQTB"+m,"");
                }else{
                    if(dateList.indexOf(day)>-1){
                        list.get(i).put("KQ"+m,"节假日");
                        list.get(i).put("KQTB"+m,"节");
                        jjr ++;
                    }else{
                        list.get(i).put("KQ"+m,"旷工");
                        list.get(i).put("KQTB"+m,"×");
                        kg += 0.5;
                    }
                }
            }
            if(String.valueOf(list.get(i).get("TYPE")).equals("1")){
                list.get(i).put("TYPE","上午");
            }else if(String.valueOf(list.get(i).get("TYPE")).equals("2")){
                list.get(i).put("TYPE","下午");
            }
            list.get(i).put("CQ",cq+Float.parseFloat(String.valueOf(other.get(i).get("CQ")).equals("null")?"0":String.valueOf(other.get(i).get("CQ"))));
            list.get(i).put("SJ",sj);
            list.get(i).put("BJ",bj);
            list.get(i).put("HJ",hj);
            list.get(i).put("NJ",nj);
            list.get(i).put("TXJ",txj);
            list.get(i).put("JJR",jjr);
            list.get(i).put("KG",kg+Float.parseFloat(String.valueOf(other.get(i).get("KG")).equals("null")?"0":String.valueOf(other.get(i).get("KG"))));
            list.get(i).put("QT",qt);
            //加班
            list.get(i).put("JB","");
//            list.get(i).put("RSJ",rsj);
//            list.get(i).put("PCJ",pcj);
//            list.get(i).put("TQJ",tqj);
            list.get(i).put("QJ",qj+rsj+pcj+tqj);
        }
        return list;
    }
    private List<String> findHoliday(String time, Integer maxDate) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("time",time);
        map.put("maxDate",maxDate);
        List<Map<String, Object>> listQuery = new ArrayList<Map<String, Object>>();
        List<String> list =new ArrayList<String>();
        try {
            listQuery = userDao.findHoliday(map);
            if(listQuery.size()>0){
                for(int i=0;i<listQuery.size();i++){
                    list.add(String.valueOf(listQuery.get(i).get("DAY")));
                }
            }
        }catch (Exception e){
            return list;
        }
        return list;
    }

    private int compare_date(String s) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            Date dt = df.parse(s);
            if (dt.getTime() >= System.currentTimeMillis()) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public String getDepartment() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = userDao.getDepartment();
        }catch (Exception e){
            return jacksonUtil.toJson(list);
        }
        return jacksonUtil.toJson(list);
    }


    public String homePage(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String department_id = request.getSession().getAttribute("department_id") == null?"":String.valueOf(request.getSession().getAttribute("department_id"));
        String power = request.getSession().getAttribute("power") == null?"":String.valueOf(request.getSession().getAttribute("power"));
        if("1".equals(department_id)){
            request.getSession().setAttribute("status", "3");
        }else if("9".equals(department_id)&&!"2".equals(power)){
            request.getSession().setAttribute("status", "2");
        }else if("1".equals(power)){
            request.getSession().setAttribute("status", "1");
        }else{
            request.getSession().setAttribute("status", "11111");
        }
        if("".equals(power)){
            return jacksonUtil.toJson(map);
        }
        try {
            //离职交接
            List<Map<String, Object>> list1 = DownloadAct.parseJSON2List2(personnelMattersService.findTurnoverHandover(request));
            map.put("离职交接",list1);
            //员工离职申请
            List<Map<String, Object>> list2 = DownloadAct.parseJSON2List2(personnelMattersService.findEmployeeLeaveApplication(request));
            map.put("员工离职申请",list2);
            //转正申请
            List<Map<String, Object>> list3 = DownloadAct.parseJSON2List2(personnelMattersService.findCorrectionApplication(request));
            map.put("转正申请",list3);
            //出差申请
            List<Map<String, Object>> list4 = DownloadAct.parseJSON2List2(financeService.findBusinessTravelApplication(request));
            map.put("出差申请",list4);
            //合同签订
            List<Map<String, Object>> list5 = DownloadAct.parseJSON2List2(financeService.findContractSign(request));
            map.put("合同签订",list5);
            //请假申请
            List<Map<String, Object>> list6 = DownloadAct.parseJSON2List2(commonService.qjcx(request,"","","1997-01-01","2200-01-01", "", "1","100000"));
            map.put("请假申请",list6);
            //外勤申请
            List<Map<String, Object>> list7 = DownloadAct.parseJSON2List2(commonService.wqcx(request,"","","1997-01-01","2200-01-01", "", "1","100000"));
            map.put("外勤申请",list7);
            //加班申请
            List<Map<String, Object>> list8 = DownloadAct.parseJSON2List2(commonService.jbcx(request,"","","1997-01-01","2200-01-01", "", "1","100000"));
            map.put("加班申请",list8);

        }catch (Exception e){
        }
        request.getSession().setAttribute("status", "");
        return jacksonUtil.toJson(map);
    }

    public String monthlyCheck(HttpServletRequest request) {
        String username = request.getParameter("username")==null?"":request.getParameter("username");
        String department = request.getParameter("department")==null?"":request.getParameter("department");
        String time = request.getParameter("time")==null?"":request.getParameter("time");

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("username",username);
        map.put("department",department);
        map.put("time",time);
        int maxDate=0;
        if (time != null && !time.isEmpty() && !time.equals("null") && time.length() > 0) {
            String[] year_month = time.split("-");
            Calendar cal = Calendar.getInstance();
            int year = Integer.parseInt(year_month[0]);
            int month = Integer.parseInt(year_month[1]) - 1;
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DATE, 1);
            cal.roll(Calendar.DATE, -1);
            maxDate = cal.get(Calendar.DATE);
        }
        List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String, Object>>();
        try {
            list =userDao.monthlyCheck(map);
        }catch (Exception e){
            return jacksonUtil.toJson(list);
        }
        return jacksonUtil.toJson(list);
    }

    public String monthlyFieldWork(HttpServletRequest request) {
        String username = request.getParameter("username")==null?"":request.getParameter("username");
        String department = request.getParameter("department")==null?"":request.getParameter("department");
        String time = request.getParameter("time")==null?"":request.getParameter("time");

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("username",username);
        map.put("department",department);
        map.put("time",time);
        int maxDate=0;
        if (time != null && !time.isEmpty() && !time.equals("null") && time.length() > 0) {
            String[] year_month = time.split("-");
            Calendar cal = Calendar.getInstance();
            int year = Integer.parseInt(year_month[0]);
            int month = Integer.parseInt(year_month[1]) - 1;
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DATE, 1);
            cal.roll(Calendar.DATE, -1);
            maxDate = cal.get(Calendar.DATE);
        }
        List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String, Object>>();
        try {
            list = userDao.monthlyFieldWork(map);
        }catch (Exception e){
            return jacksonUtil.toJson(list);
        }
        return jacksonUtil.toJson(list);
    }

    public String findAppUser(HttpServletRequest request) {
        String xxbm = request.getParameter("xxbm")==null?"":request.getParameter("xxbm");
        String username = request.getParameter("username")==null?"":request.getParameter("username");
        String lzzt = request.getParameter("lzzt")==null?"":request.getParameter("lzzt");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("xxbm",xxbm);
        map.put("username",username);
        map.put("lzzt",lzzt);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = userDao.findAppUser(map);
        }catch (Exception e){
            return jacksonUtil.toJson(list);
        }
        return jacksonUtil.toJson(list);
    }

    public synchronized String addAppUser(HttpServletRequest request) {
        String username = request.getParameter("username")==null?"":request.getParameter("username");
        String loginname = request.getParameter("loginname")==null?"":request.getParameter("loginname");
        String password = request.getParameter("password")==null?"":request.getParameter("password");
        String bm = request.getParameter("bm")==null?"":request.getParameter("bm");//100108
        String xxbm = request.getParameter("xxbm")==null?"":request.getParameter("xxbm");
        String qx = request.getParameter("qx")==null?"":request.getParameter("qx");

        String permission = request.getParameter("permission")==null?"":request.getParameter("permission");//qx
        String gw = request.getParameter("gw")==null?"":request.getParameter("gw");//qx
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("username",username);
        map.put("loginname",loginname);
        map.put("password",password);
        map.put("bm","100108");
        map.put("xxbm",xxbm);
        map.put("qx",qx);
        map.put("permission",qx);
        map.put("gw",qx);
        int count =0;
        try {
            count = userDao.addAppUser(map);
        }catch (Exception e){
            count =0;
        }
        return jacksonUtil.toJson(count);
    }

    public String updateAppUser(HttpServletRequest request) {
        String username = request.getParameter("username")==null?"":request.getParameter("username");
        String loginname = request.getParameter("loginname")==null?"":request.getParameter("loginname");
        String password = request.getParameter("password")==null?"":request.getParameter("password");
        String bm = request.getParameter("bm")==null?"":request.getParameter("bm");//100108
        String xxbm = request.getParameter("xxbm")==null?"":request.getParameter("xxbm");
        String qx = request.getParameter("qx")==null?"":request.getParameter("qx");

        String permission = request.getParameter("permission")==null?"":request.getParameter("permission");//qx
        String gw = request.getParameter("gw")==null?"":request.getParameter("gw");//qx
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("username",username);
        map.put("loginname",loginname);
        map.put("password",password);
        map.put("bm","100108");
        map.put("xxbm",xxbm);
        map.put("qx",qx);
        map.put("permission",qx);
        map.put("gw",qx);
        map.put("id",id);
        int count =0;
        try {
            count = userDao.updateAppUser(map);
        }catch (Exception e){
            count =0;
        }
        return jacksonUtil.toJson(count);
    }

    public String deleteAppUser(HttpServletRequest request) {
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        int count =0;
        try {
            count = userDao.deleteAppUser(map);
        }catch (Exception e){
            count =0;
        }
        return jacksonUtil.toJson(count);
    }

    public String auditAppUserStatus(HttpServletRequest request) {
        String lzzt = request.getParameter("lzzt")==null?"":request.getParameter("lzzt");
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("lzzt",lzzt);
        map.put("id",id);
        int count =0;
        try {
            count = userDao.auditAppUserStatus(map);
        }catch (Exception e){
            count =0;
        }
        return jacksonUtil.toJson(count);
    }

    public String findAppDepartment(HttpServletRequest request) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = userDao.findAppDepartment();
        }catch (Exception e){
            return jacksonUtil.toJson(list);
        }
        return jacksonUtil.toJson(list);
    }

    public synchronized String signin(HttpServletRequest request) {
        String username = request.getParameter("username")==null?"":request.getParameter("username").trim();
        String msg = "";
        if(username.equals("")){
            msg="请输入名字";
            return msg;
        }
        SimpleDateFormat dateFormater = new SimpleDateFormat("HHmm");
        String date= dateFormater.format(new Date());
        System.out.println(date);
        int time = Integer.parseInt(date);
        if(time<800){
            msg="该接口只支持在8点之后打卡";
            return msg;
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("username",username);
        int count =0;
        try {
            List<Map<String, Object>> list = userDao.findAppUser(map);
            if(list.size()==1){
                map.put("table","tb_signin");
                map.put("userid",list.get(0).get("USERID"));
                map.put("time","qdtime");
                List<Map<String, Object>> today = userDao.findSign(map);
                if(today.size()>=1){
                    msg="今日已签到";
                    return msg;
                }else{
                    String[] qddz={"浙江省杭州市下城区朝晖路嘉汇大厦"
                            ,"浙江省杭州市下城区上塘高架嘉汇大厦"
                            ,"浙江省杭州市下城区上塘路元丰钛合国际大厦"
                            ,"浙江省杭州市下城区上塘高架施家花园(朝晖路)"
                            ,"浙江省杭州市下城区上塘高架元丰钛合国际大厦"
                            ,"浙江省杭州市下城区朝晖路施家花园(朝晖路)"
                            ,"浙江省杭州市下城区上塘路杭州市交通运输局"
                            ,"浙江省杭州市下城区上塘高架国都发展大厦"
                            ,"嘉汇大厦"
                            ,"浙江省杭州市下城区朝晖路绿城"};
                    Random random = new Random();
                    map.put("qddz",qddz[random.nextInt(qddz.length)]);
                    map.put("jd","120.170"+(random.nextInt(900)+100));
                    map.put("wd","30.277"+(random.nextInt(900)+100));
                    map.put("xs",random.nextInt(30)+10);
                    count = userDao.signin(map);
                }
            }else if(list.size()>1){
                msg="请输入完整名字";
                return msg;
            }else{
                msg="请输入正确名字";
                return msg;
            }

        }catch (Exception e){
            count =0;
        }
        if (count==0){
            msg="签到失败";
        }else{
            msg="签到成功";
        }
        return msg;
    }

    public synchronized String signout(HttpServletRequest request) {
        String username = request.getParameter("username")==null?"":request.getParameter("username").trim();
        String msg = "";
        if(username.equals("")){
            msg="请输入名字";
            return msg;
        }
        SimpleDateFormat dateFormater = new SimpleDateFormat("HHmm");
        String date= dateFormater.format(new Date());
        System.out.println(date);
        int time = Integer.parseInt(date);
        if(time<1730){
            msg="该接口只支持在5点半之后打卡";
            return msg;
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("username",username);
        int count =0;
        try {
            List<Map<String, Object>> list = userDao.findAppUser(map);
            if(list.size()==1){
                map.put("table","tb_signin");
                map.put("userid",list.get(0).get("USERID"));
                map.put("time","qdtime");
                List<Map<String, Object>> sign = userDao.findSign(map);
                if(sign.size()==0){
                    msg="你还没签到，想啥呢";
                    return msg;
                }
                map.put("table","tb_signout");
                map.put("time","qttime");
                List<Map<String, Object>> today = userDao.findSign(map);
                if(today.size()>=1){
                    msg="今日已签退";
                    return msg;
                }else{
                    String[] qddz={"浙江省杭州市下城区朝晖路嘉汇大厦"
                            ,"浙江省杭州市下城区上塘高架嘉汇大厦"
                            ,"浙江省杭州市下城区上塘路元丰钛合国际大厦"
                            ,"浙江省杭州市下城区上塘高架施家花园(朝晖路)"
                            ,"浙江省杭州市下城区上塘高架元丰钛合国际大厦"
                            ,"浙江省杭州市下城区朝晖路施家花园(朝晖路)"
                            ,"浙江省杭州市下城区上塘路杭州市交通运输局"
                            ,"浙江省杭州市下城区上塘高架国都发展大厦"
                            ,"嘉汇大厦"
                            ,"浙江省杭州市下城区朝晖路绿城"};
                    Random random = new Random();
                    map.put("qtdz",qddz[random.nextInt(qddz.length)]);
                    map.put("jd","120.170"+(random.nextInt(900)+100));
                    map.put("wd","30.277"+(random.nextInt(900)+100));
                    map.put("xs",random.nextInt(30)+10);
                    count = userDao.signout(map);
                }
            }else if(list.size()>1){
                msg="请输入完整名字";
                return msg;
            }else{
                msg="请输入正确名字";
                return msg;
            }

        }catch (Exception e){
            count =0;
        }
        if (count==0){
            msg="签退失败";
        }else{
            msg="签退成功";
        }
        return msg;
    }

//    @Transactional
//    public String addCheckAttendance(HttpServletRequest request) {
//        String department = request.getParameter("department")==null?"":request.getParameter("department");
//        String stime = request.getParameter("stime")==null?"":request.getParameter("stime");
//        String etime = request.getParameter("etime")==null?"":request.getParameter("etime");
//        String longi = request.getParameter("longi")==null?"":request.getParameter("longi");
//        String lati = request.getParameter("lati")==null?"":request.getParameter("lati");
//        String range = request.getParameter("range")==null?"":request.getParameter("range");
//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("stime",stime.replace(":","")+"00");
//        map.put("etime",etime.replace(":","")+"00");
//        map.put("range",range);
//        int count =0;
//        try {
//            if(department.equals("")){
//                throw new Exception("部门不能为空");
//            }
//            map.put("department","'"+department.replace(",","','")+"'");
//            userDao.deleteCheckAttendance(map);
//            String type[] = department.split(",");
//            String px[] = longi.split(",");
//            String py[] = lati.split(",");
//            for(int i=0;i<type.length;i++){
//                for(int j=0;j<px.length;j++){
//                    map.put("department",type[i]);
//                    map.put("longi",px[j]);
//                    map.put("lati",py[j]);
//                    count = userDao.addCheckAttendance(map);
//                }
//            }
//        }catch (Exception e){
//            count =0;
//            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//        }
//        return jacksonUtil.toJson(count);
//    }

    public synchronized String addCheckAttendance(HttpServletRequest request) {
        String department = request.getParameter("department")==null?"":request.getParameter("department");
        String stime = request.getParameter("stime")==null?"":request.getParameter("stime");
        String etime = request.getParameter("etime")==null?"":request.getParameter("etime");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("stime",stime.replace(":","")+"00");
        map.put("etime",etime.replace(":","")+"00");
        map.put("department",department);
        map.put("range",1500);
        map.put("longi",120.170737);
        map.put("lati",30.277141);
        int count =0;
        try {
            if(department.equals("")){
                throw new Exception("部门不能为空");
            }
            count = userDao.addCheckAttendance(map);
        }catch (Exception e){
            count =0;
            e.printStackTrace();
        }
        return jacksonUtil.toJson(count);
    }
    public String updateCheckAttendance(HttpServletRequest request) {
        String department = request.getParameter("department")==null?"":request.getParameter("department");
        String stime = request.getParameter("stime")==null?"":request.getParameter("stime");
        String etime = request.getParameter("etime")==null?"":request.getParameter("etime");
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("stime",stime.replace(":","")+"00");
        map.put("etime",etime.replace(":","")+"00");
        map.put("department",department);
        map.put("range",1500);
        map.put("longi",120.170737);
        map.put("lati",30.277141);
        map.put("id",id);
        int count =0;
        try {
            if(department.equals("")){
                throw new Exception("部门不能为空");
            }
            count = userDao.updateCheckAttendance(map);
        }catch (Exception e){
            count =0;
            e.printStackTrace();
        }
        return jacksonUtil.toJson(count);
    }

    public String deleteCheckAttendance(HttpServletRequest request) {
        String id = request.getParameter("id")==null?"":request.getParameter("id");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        int count =0;
        try {
            count = userDao.deleteCheckAttendance(map);
        }catch (Exception e){
            count =0;
            e.printStackTrace();
        }
        return jacksonUtil.toJson(count);
    }

    public String findCheckAttendance(HttpServletRequest request) {
        String department = request.getParameter("department")==null?"":request.getParameter("department");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("department",department);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = userDao.findCheckAttendance(map);
        }catch (Exception e){
            return jacksonUtil.toJson(list);
        }
        return jacksonUtil.toJson(list);
    }

    public String findAttendanceDepartment(HttpServletRequest request) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = userDao.findAttendanceDepartment();
        }catch (Exception e){
            return jacksonUtil.toJson(list);
        }
        return jacksonUtil.toJson(list);
    }



}
