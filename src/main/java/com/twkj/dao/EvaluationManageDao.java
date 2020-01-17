package com.twkj.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author: xianlehuang
 * @Description:
 * @date: 2019/12/5 - 14:58
 */
public interface EvaluationManageDao {

    @SelectProvider(type = EvaluationManage.class, method = "findMonthlyCheck")
    List<Map<String, Object>> findMonthlyCheck(Map<String, Object> map);

    @Select("select * from employee e LEFT JOIN thismonthwork t on e.wid=t.id where e.wid=#{id} order by t.thismonthwork, t.thismonthtarget")
    List<Map<String, Object>> findMonthlyCheck_thismonth(Map<String, Object> map);

    @Select("select * from employee e LEFT JOIN lastmonthwork l on e.wid=l.id where e.wid=#{id} order by l.lastmonthwork,l.lastmonthtarget")
    List<Map<String, Object>> findMonthlyCheck_lastmonth(Map<String, Object> map);

    @Select("select * from employee e LEFT JOIN workquality w on e.wid=w.id where e.wid=#{id} order by w.id desc")
    List<Map<String, Object>> findMonthlyCheck_workquality(Map<String, Object> map);

    @Insert("insert into employee (time, name, department, cycle, riqi, grade, wid) values (#{time}, #{name}, #{department}, #{cycle}, #{riqi}, #{grade}, #{id})")
    int addMonthlyCheck_employee(Map<String, Object> map);

    @Insert("insert into thismonthwork (thismonthwork, thismonthtarget, thismonthactualperformance, thismonthcompletionrate, thismonthtaskscore, thismonthgrade, id) values (#{thismonthwork}, #{thismonthtarget}, #{thismonthactualperformance}, #{thismonthcompletionrate}, #{thismonthtaskscore}, #{thismonthgrade}, #{id})")
    int addMonthlyCheck_thismonth(Map<String, Object> map);

    @Insert("insert into lastmonthwork (lastmonthwork, lastmonthtarget, lastmonthcomments, id) values (#{lastmonthwork}, #{lastmonthtarget}, #{lastmonthcomments}, #{id})")
    int addMonthlyCheck_lastmonth(Map<String, Object> map);

    @Insert("insert into workquality (selfworthdescribe, selfworthselfevaluation, selfworthgrade, teamworkdescribe, teamworkselfevaluation, teamworkgrade, customerservicedescribe, customerserviceselfevaluation, customerservicegrade, commentsdepartmentheads, companyleadersapproval, id) values (#{selfworthdescribe}, #{selfworthselfevaluation}, #{selfworthgrade}, #{teamworkdescribe}, #{teamworkselfevaluation}, #{teamworkgrade}, #{customerservicedescribe}, #{customerserviceselfevaluation}, #{customerservicegrade}, #{commentsdepartmentheads}, #{companyleadersapproval}, #{id})")
    int addMonthlyCheck_workquality(Map<String, Object> map);

    @Update("update employee set time=#{time}, name=#{name}, department=#{department}, cycle=#{cycle}, riqi=#{riqi}, grade=#{grade} where wid=#{id}")
    int updateMonthlyCheck_employee(Map<String, Object> map);

//    @Update("update thismonthwork set thismonthwork=#{thismonthwork}, thismonthtarget=#{thismonthtarget}, thismonthactualperformance=#{thismonthactualperformance}, thismonthcompletionrate=#{thismonthcompletionrate}, thismonthtaskscore=#{thismonthtaskscore}, thismonthgrade=#{thismonthgrade} where id=#{id}")
//    int updateMonthlyCheck_thismonth(Map<String, Object> map);
//
//    @Update("update lastmonthwork set lastmonthwork=#{lastmonthwork}, lastmonthtarget=#{lastmonthtarget}, lastmonthcomments=#{lastmonthcomments} where id=#{id}")
//    int updateMonthlyCheck_lastmonth(Map<String, Object> map);

    @Update("update workquality set selfworthdescribe=#{selfworthdescribe}, selfworthselfevaluation=#{selfworthselfevaluation}, selfworthgrade=#{selfworthgrade}, teamworkdescribe=#{teamworkdescribe}, teamworkselfevaluation=#{teamworkselfevaluation}, teamworkgrade=#{teamworkgrade}, customerservicedescribe=#{customerservicedescribe}, customerserviceselfevaluation=#{customerserviceselfevaluation}, customerservicegrade=#{customerservicegrade}, commentsdepartmentheads=#{commentsdepartmentheads}, companyleadersapproval=#{companyleadersapproval} where id=#{id}")
    int updateMonthlyCheck_workquality(Map<String, Object> map);

    @Delete("delete from employee where wid=#{id}")
    int deleteMonthlyCheck_employee(Map<String, Object> map);

    @Delete("delete from thismonthwork where id=#{id}")
    int deleteMonthlyCheck_thismonth(Map<String, Object> map);

    @Delete("delete from lastmonthwork where id=#{id}")
    int deleteMonthlyCheck_lastmonth(Map<String, Object> map);

    @Delete("delete from workquality where id=#{id}")
    int deleteMonthlyCheck_workquality(Map<String, Object> map);

    @Update("update employee set grade=#{grade}, audit_grade=#{audit_grade} where wid=#{id}")
    int updateMonthlyCheck_employee_grade(@Param("id") String id,@Param("grade") String grade,@Param("audit_grade") Double audit_grade);

    @Update("update workquality set selfworthgrade=#{selfworthgrade}, teamworkgrade=#{teamworkgrade}, customerservicegrade=#{customerservicegrade}, commentsdepartmentheads=#{commentsdepartmentheads}, companyleadersapproval=#{companyleadersapproval} where id=#{id}")
    int updateMonthlyCheck_workquality_two(Map<String, Object> map);

    class EvaluationManage{
        public String findMonthlyCheck(Map<String, Object> map){
            String time = String.valueOf(map.get("time"));
            String name = String.valueOf(map.get("name"));
            String id = String.valueOf(map.get("id"));
            String tj="";
            if(name!=null&&!name.isEmpty()&&!name.equals("null")&&name.length()>0){
                tj += " and a.name = '"+name+"'";
            }
            if(time!=null&&!time.isEmpty()&&!time.equals("null")&&time.length()>0){
                tj += " and a.time ='"+time+"'";
            }
            if(id!=null&&!id.isEmpty()&&!id.equals("null")&&id.length()>0){
                tj += " and a.wid ='"+id+"'";
            }
            String sql = "select * from employee a where 1=1";
            sql += tj;
            sql +=" order by a.time desc, a.name";
            return sql;
        }
    }
}
