package com.twkj.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author: xianlehuang
 * @Description:项目管理
 * @date: 2020/6/5 - 13:44
 */
public interface ProjectManageDao {

    @SelectProvider(type = ProjectSql.class,method = "findCatalog")
    List<Map<String, Object>> findCatalog(Map<String, Object> map);

    @Insert("insert into tb_flow_catalog (catalog_name, catalog_description, user_id, user_name) values(#{catalog_name}, #{catalog_description}, #{user_id}, #{user_name})")
    int addCatalog(Map<String, Object> map);

    @Update("update tb_flow_catalog set catalog_name=#{catalog_name}, catalog_description=#{catalog_description}, user_id=#{user_id}, user_name=#{user_name} where id=#{id}")
    int updateCatalog(Map<String, Object> map);

    @Update("update tb_flow_catalog set status=#{status} where id=#{id}" +
            " and (select count(*) count from tb_flow_project where catalog_id=#{id} and status='0')=0")
    int deleteCatalog(Map<String, Object> map);

    @SelectProvider(type = ProjectSql.class,method = "findProject")
    List<Map<String, Object>> findProject(Map<String, Object> map);

    @Insert("insert into tb_flow_project (project_name, project_description, user_id, user_name, catalog_id) values(#{project_name}, #{project_description}, #{user_id}, #{user_name}, #{catalog_id})")
    int addProject(Map<String, Object> map);

    @Update("update tb_flow_project set project_name=#{project_name}, project_description=#{project_description}, user_id=#{user_id}, user_name=#{user_name} where id=#{id}")
    int updateProject(Map<String, Object> map);

    @Update("update tb_flow_project set status=#{status} where id=#{id}")
    int deleteProject(Map<String, Object> map);

    @Update("update tb_flow_project_record set status=#{status} where project_id=#{id}")
    void deleteDealContent_main(Map<String, Object> map);

    @Select("select * from tb_flow_project_record where project_id=#{project_id} and STATUS='0' order by dbtime desc")
    List<Map<String, Object>> findDealContent(Map<String, Object> map);

    @SelectKey(statement="select SYS_GUID() as id from DUAL", keyProperty="id", before=true, resultType=String.class)
    @Insert("insert into tb_flow_project_record (id,project_id,user_id,user_name,deal_content,title, receiver) values(#{id}, #{project_id}, #{user_id}, #{user_name}, #{deal_content}, #{title}, #{receiver})")
    int addDealContent(Map<String, Object> map);

    @Update("update tb_flow_project_record set project_id=#{project_id}, user_id=#{user_id}, user_name=#{user_name}, deal_content=#{deal_content}, title=#{title}, receiver=#{receiver} where id=#{id}")
    int updateDealContent(Map<String, Object> map);

    @Update("update tb_flow_project_record set status=#{status} where id=#{id}")
    int deleteDealContent(Map<String, Object> map);

    @Insert("insert into tb_flow_project_record_read (project_id,record_id,user_name) values(#{project_id},#{id},#{user})")
    void addReadRecord(Map<String, Object> map);

    @Delete("delete from tb_flow_project_record_read where record_id=#{id}")
    void deleteReadRecord(Map<String, Object> map);

    @Update("update tb_flow_project_record_read set IS_READ = 1" +
            " where user_name = #{user_name} and project_id = #{project_id}" +
            " and record_id in (select id from tb_flow_project_record where project_id = #{project_id} and status='0')")
    void updateReadRecord(Map<String, Object> map);

    @Select("select distinct a.id from tb_flow_catalog a ,tb_flow_project b,tb_flow_project_record c,tb_flow_project_record_read d" +
            " where a.id = b.catalog_id and b.id = c.project_id and c.id = d.record_id" +
            " and a.status='0' and b.status='0' and c.status='0'" +
            " and d.is_read = 0 and d.user_name = #{user_name}")
    List<Map<String, Object>> userReadProjectResult_catalog(String user_name);

    @Select("select distinct b.id from tb_flow_project b,tb_flow_project_record c,tb_flow_project_record_read d" +
            " where  b.id = c.project_id and c.id = d.record_id" +
            " and b.status='0' and c.status='0'" +
            " and d.is_read = 0 and d.user_name = #{user_name}")
    List<Map<String, Object>> userReadProjectResult_project(String user_name);

    @Select("select distinct c.id from tb_flow_project_record c,tb_flow_project_record_read d" +
            " where c.id = d.record_id" +
            " and c.status='0'" +
            " and d.is_read = 0 and d.user_name = #{user_name}")
    List<Map<String, Object>> userReadProjectResult_record(String user_name);

    class ProjectSql{
        public String findCatalog(Map<String, Object> map){
            String catalog_name = String.valueOf(map.get("catalog_name")).replaceAll("'","''");
            String stime = String.valueOf(map.get("stime")).replaceAll("'","''");
            String etime = String.valueOf(map.get("etime")).replaceAll("'","''");
            String user_name = String.valueOf(map.get("user_name")).replaceAll("'","''");
            String catalog_description = String.valueOf(map.get("catalog_description")).replaceAll("'","''");
            String tj="";
            if(catalog_name!=null&&!catalog_name.isEmpty()&&!"null".equals(catalog_name)){
                tj += " and a.catalog_name like '%"+catalog_name+"%'";
            }
            if(stime!=null&&!stime.isEmpty()&&!stime.equals("null")){
                tj += " and a.dbtime >= to_date('"+stime+" 00:00:00','yyyy-mm-dd hh24:mi:ss')";
            }
            if(etime!=null&&!etime.isEmpty()&&!etime.equals("null")){
                tj += " and a.dbtime <= to_date('"+etime+" 23:59:59','yyyy-mm-dd hh24:mi:ss')";
            }
            if(user_name!=null&&!user_name.isEmpty()&&!"null".equals(user_name)){
                tj += " and a.user_name = '"+user_name+"'";
            }
            if(catalog_description!=null&&!catalog_description.isEmpty()&&!"null".equals(catalog_description)){
                tj += " and a.catalog_description like '%"+catalog_description+"%'";
            }
            String sql = "select * from tb_flow_catalog a where 1=1 and a.STATUS='0'";
            sql += tj;
            sql +=" order by a.dbtime desc, a.catalog_name";
            return sql;
        }
        public String findProject(Map<String, Object> map){
            String project_name = String.valueOf(map.get("project_name")).replaceAll("'","''");
            String stime = String.valueOf(map.get("stime")).replaceAll("'","''");
            String etime = String.valueOf(map.get("etime")).replaceAll("'","''");
            String user_name = String.valueOf(map.get("user_name")).replaceAll("'","''");
            String project_description = String.valueOf(map.get("project_description")).replaceAll("'","''");
            String catalog_id = String.valueOf(map.get("catalog_id")).replaceAll("'","''");
            String tj="";
            if(project_name!=null&&!project_name.isEmpty()&&!"null".equals(project_name)){
                tj += " and a.project_name like '%"+project_name+"%'";
            }
            if(stime!=null&&!stime.isEmpty()&&!stime.equals("null")){
                tj += " and a.dbtime >= to_date('"+stime+" 00:00:00','yyyy-mm-dd hh24:mi:ss')";
            }
            if(etime!=null&&!etime.isEmpty()&&!etime.equals("null")){
                tj += " and a.dbtime <= to_date('"+etime+" 23:59:59','yyyy-mm-dd hh24:mi:ss')";
            }
            if(user_name!=null&&!user_name.isEmpty()&&!"null".equals(user_name)){
                tj += " and a.user_name = '"+user_name+"'";
            }
            if(project_description!=null&&!project_description.isEmpty()&&!"null".equals(project_description)){
                tj += " and a.project_description like '%"+project_description+"%'";
            }
            if(catalog_id!=null&&!catalog_id.isEmpty()&&!"null".equals(catalog_id)){
                tj += " and a.catalog_id = '"+catalog_id+"'";
            }
            String sql = "select * from tb_flow_project a where 1=1 and a.STATUS='0'";
            sql += tj;
            sql +=" order by a.dbtime desc, a.project_name";
            return sql;
        }
    }
}
