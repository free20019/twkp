package com.twkj.service;

import com.twkj.helper.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CommonService {

	protected JdbcTemplate jdbcTemplate = null;
	protected JdbcTemplate jdbcTemplate2 = null;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate2() {
		return jdbcTemplate2;
	}

	@Autowired
	public void setJdbcTemplate2(JdbcTemplate jdbcTemplate2) {
		this.jdbcTemplate2 = jdbcTemplate2;
	}

	private JacksonUtil jacksonUtil = JacksonUtil.buildNormalBinder();

	public String test() {
		System.out.println(jdbcTemplate);
		System.out.println(jdbcTemplate2);

		return "ok";
	}

	public String part(String gw, String currentpage, String totalnum) {
//		String sql = "select * from (select t.*,rownum r from tb_gw t where gw like '%"+gw+"%') tt where tt.r<="+ (Integer.parseInt(currentpage) * Integer.parseInt(totalnum)) + " and tt.r>="+(Integer.parseInt(currentpage) - 1) * Integer.parseInt(totalnum);
//		String sql2= "select count(*) count from tb_gw t where gw like '%"+gw+"%' order by t.gw";
		String sql = "select * from (select t.*,rownum r from tb_gw t where gw like ?) tt where tt.r<=? and tt.r>=?";
        String sql2= "select count(*) count from tb_gw t where gw like ? order by t.gw";
		
		//		System.out.println(sql);
		//		System.out.println(sql2);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,"%"+gw+"%",(Integer.parseInt(currentpage) * Integer.parseInt(totalnum)),(Integer.parseInt(currentpage) - 1) * Integer.parseInt(totalnum));
		List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql2,"%"+gw+"%");
		List<Object> newList = new ArrayList<Object>();
		for(int i=0;i<list.size();i++){
			Map<String, Object> map1 = list.get(i);
			map1.put("QXID", list.get(i).get("QX").toString());
			String[] qxid = list.get(i).get("QX").toString().split(",");
			String arr="";			
			for(int j=0;j<qxid.length;j++){
				String sql1 = "select * from tb_qx where id = '"+qxid[j]+"'";
				List<Map<String, Object>> list1 = jdbcTemplate.queryForList(sql1);
				arr +=list1.get(0).get("NAME").toString()+",";
			}
			map1.put("QX", arr.substring(0,arr.length()-1));
			newList.add(map1);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("datas", newList);
		m.put("count", list2.get(0).get("count"));
		//		System.out.println(m);
		return jacksonUtil.toJson(m);
	}

	public String tree() {
		String sql= "select * from tb_bm where parent is NULL";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		List<Object> newList = new ArrayList<Object>();
		for(int i=0;i<list.size();i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", list.get(i).get("CODE"));
			map.put("pId","0");
			map.put("name", list.get(i).get("BM"));
			map.put("open", "true");
			newList.add(map);
//			String sql1= "select * from tb_bm where parent ="+list.get(i).get("CODE");
			String sql1= "select * from tb_bm where parent =?";
			List<Map<String, Object>> list1 = jdbcTemplate.queryForList(sql1,list.get(i).get("CODE"));
			for(int j=0;j<list1.size();j++){
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("id", list1.get(j).get("CODE"));
				map1.put("pId",list1.get(j).get("PARENT"));
				map1.put("name", list1.get(j).get("BM"));
				newList.add(map1);
			}
		}
		return jacksonUtil.toJson(newList);
	}

	public String qxtree() {
		String sql= "select * from tb_qx";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		List<Object> newList = new ArrayList<Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id","0");
		map.put("pId","0");
		map.put("name","权限");
		map.put("open", "true");
		newList.add(map);
		for(int i=0;i<list.size();i++){
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("id", list.get(i).get("ID"));
			map1.put("pId","0");
			map1.put("name", list.get(i).get("NAME"));
			map1.put("open", "false");
			newList.add(map1);
		}
		return jacksonUtil.toJson(newList);
	}

	public int addsave(String mc, String id) {
		String sql = "insert into TB_GW (gw,qx) values (?,?)";
		//		System.out.println(sql);
		int count=jdbcTemplate.update(sql,mc,id);
		return count;
	}

	public int del(String sj) {
		String[] id=sj.split(",");
		int count=0;
		for(int i=0;i<id.length;i++){
			String sql = "delete from TB_GW where id=?";
			//			System.out.println(sql);
			count+=jdbcTemplate.update(sql,id[i]);
		}
		return count;
	}

	public int editsave(String mc, String nr, String id) {
		String sql = "update TB_GW set gw=?, qx=? where id =?";
		//		System.out.println(sql);
		int count=jdbcTemplate.update(sql,mc,nr,id);
		return count;
	}

	public String getsz(String bm) {
		String sqlcz = "select * from tb_sz t where t.bm=?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlcz,bm);
		return jacksonUtil.toJson(list);
	}
	public int addsz(String bm, String longi, String lati, String sb,String xb, String fw) {
		String sqlcz = "select * from tb_sz t where t.bm=?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlcz,bm);
		String sbtime = sb.replaceAll(":", "")+"00";
		String xbtime = xb.replaceAll(":", "")+"00";
		String sql ="";
		int count=0;
		if(list.size()==0){
//			sql = "insert into tb_sz (sbtime,xbtime,fw,longi,lati,bm) values ('"+sbtime+"','"+xbtime+"','"+fw+"','"+longi+"','"+lati+"','"+bm+"')";
			sql = "insert into tb_sz (sbtime,xbtime,fw,longi,lati,bm) values (?,?,?,?,?,?)";
			count=jdbcTemplate.update(sql,sbtime,xbtime,fw,longi,lati,bm);
		}else{
//			sql = "update tb_sz set sbtime='"+sbtime+"',xbtime='"+xbtime+"',fw='"+fw+"',longi='"+longi+"',lati='"+lati+"'where bm='"+bm+"'";
			sql = "update tb_sz set sbtime=?,xbtime=?,fw=?,longi=?,lati=? where bm=?";
			count=jdbcTemplate.update(sql,sbtime,xbtime,fw,longi,lati,bm);
		}
		//System.out.println(sql);
		
		return count;
	}

	public String user(String name, String bm, String currentpage,
			String totalnum) {
		String sql = "select * from (select c.id,c.username,c.loginname,c.password,v.bm,v.parent,v.code,t.gw,rownum r from tb_gw t, tuser c, tb_bm v where v.bm like ? and c.username like ? and c.bm = v.code and t.id = c.gw) tt where tt.r<= ? and tt.r>= ?";
		String sql2= "select count(*) count from tb_gw t, tuser c, tb_bm v where  v.bm like ? and c.username like ? and c.bm = v.code and t.id = c.gw";
		System.out.println(sql);
		System.out.println(sql2);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,"%"+bm+"%","%"+name+"%",(Integer.parseInt(currentpage) * Integer.parseInt(totalnum)),(Integer.parseInt(currentpage) - 1) * Integer.parseInt(totalnum));
		List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql2,"%"+bm+"%","%"+name+"%");
		List<Object> newList = new ArrayList<Object>();
		for(int i=0;i<list.size();i++){
			Map<String, Object> map1 = list.get(i);
			if(list.get(i).get("parent")==null){
				map1.put("PARENT", list.get(i).get("code").toString());
			}
			newList.add(map1);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("datas", newList);
		m.put("count", list2.get(0).get("count"));
		System.out.println(m);
		return jacksonUtil.toJson(m);
	}

	public int useraddsave(String xm, String zh, String mm, String gw, String bm) {
		String sql = "insert into tuser (username,loginname,password,permission,bm,gw) values (?,?,?,1,?,?)";
//		System.out.println(sql);
		int count=jdbcTemplate.update(sql,xm,zh,mm,bm,gw);
		return count;
	}


	public String findqdsz() {
		String sql= "select t.*,t1.bm bmmc from tb_sz t,tb_bm t1 where t.bm=t1.code";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> a = list.get(i);
			String sb = a.get("SBTIME").toString();
			String xb = a.get("XBTIME").toString();
			a.put("SBTIME", sb.substring(0, 2)+":"+sb.substring(2, 4))	;
			a.put("XBTIME", xb.substring(0, 2)+":"+xb.substring(2, 4))	;
		}
		return jacksonUtil.toJson(list);
	}

	public int userdel(String sj) {
		String[] id=sj.split(",");
		int count=0;
		for(int i=0;i<id.length;i++){
			String sql = "delete from tuser where id=?";
			//			System.out.println(sql);
			count+=jdbcTemplate.update(sql,id[i]);
		}
		return count;
	}

	public int usereditsave(String xm, String zh, String mm, String bm,
			String gw, String id) {
		String sql = "update tuser set username=?, loginname=?,password=?,bm=? ,gw=? where id =?";
		//		System.out.println(sql);
		int count=jdbcTemplate.update(sql,xm,zh,mm,bm,gw,id);
		return count;
	}

	public String bmgl(String bm, String currentpage, String totalnum) {
//		String sql = "select * from (select t.id,t.bm,t.code,t.parent,rownum r from tb_bm t where t.bm like '%"+bm+"%' and t.parent is not null) tt where tt.r<="+ (Integer.parseInt(currentpage) * Integer.parseInt(totalnum)) + " and tt.r>="+(Integer.parseInt(currentpage) - 1) * Integer.parseInt(totalnum);
//		String sql2= "select count(*) count from tb_bm t where  t.bm like '%"+bm+"%' and t.parent is not null";
		
		String sql = "select * from (select t.id,t.bm,t.code,t.parent,rownum r from tb_bm t where t.bm like ? and t.parent is not null) tt where tt.r<=? and tt.r>=?";
        String sql2= "select count(*) count from tb_bm t where  t.bm like ? and t.parent is not null";
//		System.out.println(sql);
//		System.out.println(sql2);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,"%"+bm+"%",(Integer.parseInt(currentpage) * Integer.parseInt(totalnum)),(Integer.parseInt(currentpage) - 1) * Integer.parseInt(totalnum));
		List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql2,"%"+bm+"%");
		List<Object> newList = new ArrayList<Object>();
		for(int i=0;i<list.size();i++){
			Map<String, Object> map = list.get(i);
			String sql1= "select * from tb_bm where code = ?";
			List<Map<String, Object>> list1 = jdbcTemplate.queryForList(sql1,list.get(i).get("PARENT"));
			for(int j=0;j<list1.size();j++){
				map.put("SJBM", list1.get(j).get("BM"));
				newList.add(map);
			}
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("datas", newList);
		m.put("count", list2.get(0).get("count"));
		System.out.println(m);
		return jacksonUtil.toJson(m);
	}

	public int bmgladdsave(String bm, String parent) {
		String sql = "insert into TB_bm (bm,parent) values (?,?)";
//		System.out.println(sql);
		int count=jdbcTemplate.update(sql,bm,parent);
		return count;
	}

	public int bmgldel(String sj) {
		String[] id=sj.split(",");
		int count=0;
		for(int i=0;i<id.length;i++){
			String sql = "delete from tb_bm where id=?";
			//			System.out.println(sql);
			count+=jdbcTemplate.update(sql,id[i]);
		}
		return count;
	}

	public int bmgleditsave(String bm, String id, String sjid) {
		String sql = "update tb_bm set bm=?, parent=? where id = ?";
//		System.out.println(sql);
		int count=jdbcTemplate.update(sql,bm,sjid,id);
		return count;
	}
/*
 * 	签到查询
 */
	public String qdtj(String name, String bm, String stime, String etime,
			String currentpage, String totalnum) {

//		String sql = "select * from (select ts.*,rownum r,m.bm from (select tin.username,tin.qddz,tin.qdtime,tin.cdyy,tout.qtdz,tout.qttime,tout.ztyy from "
//				+ "(select * from tb_signin t where  t.username in (select u.username from tuser u where u.username like '%"+name+"%' and u.bm in (select t.code from tb_bm t where t.bm like '%"+bm+"%'))"
//				+ " and t.qdtime>=to_date('"+stime+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and t	.qdtime<=to_date('"+etime+" 23:59:59','yyyy-mm-dd hh24:mi:ss') "
//				+ ") tin left join tb_signout tout on tin.yeartime=tout.yeartime and tin.username=tout.username "
//				+ "order by tin.username,tin.qdtime asc)ts ,tb_bm m,tuser y where ts.username=y.username and y.bm=m.code)tt where tt.r<="+ (Integer.parseInt(currentpage) * Integer.parseInt(totalnum)) + " and tt.r>="+(Integer.parseInt(currentpage) - 1) * Integer.parseInt(totalnum);
//		String sql2="select count(*) c from "
//				+ "(select * from tb_signin t where  t.username in (select u.username from tuser u where u.username like '%"+name+"%' and u.bm in (select t.code from tb_bm t where t.bm like '%"+bm+"%'))"
//				+ "and t.qdtime>=to_date('"+stime+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and t.qdtime<=to_date('"+etime+" 23:59:59','yyyy-mm-dd hh24:mi:ss') "
//				+ ") tin left join tb_signout tout on tin.yeartime=tout.yeartime and tin.username=tout.username "
//				+ "order by tin.username,tin.qdtime";
	    
	    String sql = "select * from (select ts.*,rownum r,m.bm from (select tin.username,tin.qddz,tin.qdtime,tin.cdyy,tout.qtdz,tout.qttime,tout.ztyy from "
                + "(select * from tb_signin t where  t.username in (select u.username from tuser u where u.username like ? and u.bm in (select t.code from tb_bm t where t.bm like ?))"
                + " and t.qdtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss') and t   .qdtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') "
                + ") tin left join tb_signout tout on tin.yeartime=tout.yeartime and tin.username=tout.username "
                + "order by tin.username,tin.qdtime asc)ts ,tb_bm m,tuser y where ts.username=y.username and y.bm=m.code)tt where tt.r<=? and tt.r>=?";
        String sql2="select count(*) c from "
                + "(select * from tb_signin t where  t.username in (select u.username from tuser u where u.username like ? and u.bm in (select t.code from tb_bm t where t.bm like ?))"
                + "and t.qdtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss') and t.qdtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') "
                + ") tin left join tb_signout tout on tin.yeartime=tout.yeartime and tin.username=tout.username "
                + "order by tin.username,tin.qdtime";
	    
	    
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,"%"+name+"%","%"+bm+"%",stime+" 00:00:00",etime+" 23:59:59",(Integer.parseInt(currentpage) * Integer.parseInt(totalnum)),(Integer.parseInt(currentpage) - 1) * Integer.parseInt(totalnum));
		List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql2,"%"+name+"%","%"+bm+"%",stime+" 00:00:00",etime+" 23:59:59");

		String sqluser = "select u.username from tuser u,tb_bm t where u.bm=t.code and u.username like ? and t.bm like ?";
		List<Map<String, Object>> userlist = jdbcTemplate.queryForList(sqluser,"%"+name+"%","%"+bm+"%");
		for (int i = 0; i < userlist.size(); i++) {
			String username = userlist.get(i).get("USERNAME").toString();
			userlist.get(i).put("qdcs", qdcxcount(username,stime,etime));
			userlist.get(i).put("qjcs", qjcxcount(username,stime,etime));
			userlist.get(i).put("wqcs", wqcxcount(username,stime,etime));
		}

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("datas", list);
		m.put("count", list2.get(0).get("c"));
		m.put("tsj", userlist);
		return jacksonUtil.toJson(m);
	}
	

	
	public String zqdb(String name, String bm, String stime, String dyts) {
		
//		String sql = "select * from (select ts.*,rownum r,m.bm from (select tin.username,tin.qdtime,tin.cdyy,tout.qttime,tout.ztyy from "
//				+ "(select * from tb_signin t where  t.username in (select u.username from tuser u where u.username like '%"+name+"%' and u.bm in (select t.code from tb_bm t where t.bm like '%"+bm+"%'))"
//				+ " and t.qdtime>=to_date('"+stime+"-01 00:00:00','yyyy-mm-dd hh24:mi:ss') and t.qdtime<=to_date('"+stime+"-"+dyts+" 23:59:59','yyyy-mm-dd hh24:mi:ss') "
//				+ ") tin left join tb_signout tout on tin.yeartime=tout.yeartime and tin.username=tout.username "
//				+ "order by tin.username,tin.qdtime asc)ts ,tb_bm m,tuser y where ts.username=y.username and y.bm=m.code)tt";
//		String sql = "select * from (select ts.*,rownum r,m.bm from (select tin.username,tin.qdtime,tin.cdyy,tout.qttime,tout.ztyy from "
//                + "(select * from tb_signin t where  t.username in (select u.username from tuser u where u.username like ? and u.bm in (select t.code from tb_bm t where t.bm like ?))"
//                + " and t.qdtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss') and t.qdtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') "
//                + ") tin left join tb_signout tout on tin.yeartime=tout.yeartime and tin.username=tout.username "
//                + "order by tin.username,tin.qdtime asc)ts ,tb_bm m,tuser y where ts.username=y.username and y.bm=m.code)tt";

		String sql = "select ts.*,rownum r,m.bm from (select tin.username,tin.qdtime,tin.cdyy,tout.qttime,tout.ztyy from "
				+ "(select * from tb_signin t where  t.username in (select u.username from tuser u where u.username like ? and u.bm in (select t.code from tb_bm t where t.bm like ?))"
				+ " and t.qdtime>=to_date(?,'yyyy-mm-dd hh24:mi:ss') and t.qdtime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') "
				+ ") tin left join tb_signout tout on tin.yeartime=tout.yeartime and tin.username=tout.username "
				+ "order by tin.username,tin.qdtime asc)ts ,tb_bm m,tuser y where ts.username=y.username and y.bm=m.code";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,"%"+name+"%","%"+bm+"%",stime+"-01 00:00:00",stime+"-"+dyts+" 23:59:59");

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("datas", list);
		return jacksonUtil.toJson(m);
	}
	
	
/*
 * 请假查询
 */
	public String qjcx(HttpServletRequest request, String name, String bm, String stime, String etime,
					   String currentpage, String totalnum) {

//		String sql = "select * from (select ts.*,rownum r from (select t.id,t.qjkstime,t.qjjstime,t.spuser,t.spusertwo,ceil((qjjstime - qjkstime) * 24) as qjsc,ceil(qjjstime - qjkstime) as qjts,t.username,m.bm,t.qjnr,t.qjtype,t.sqjg,t.sqjgtwo,t.tpone "
//				+ "from tb_qjsc t,tuser u,tb_bm m "
//				+ "where t.username=u.username and u.bm=m.code and t.username like '%"+name+"%' "
//				+ " and t.qjkstime>=to_date('"+stime+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and t.qjkstime<=to_date('"+etime+" 23:59:59','yyyy-mm-dd hh24:mi:ss') "
//				+ " and m.bm like '%"+bm+"%' order by t.username,t.qjkstime asc)ts )tt where tt.r<="+ (Integer.parseInt(currentpage) * Integer.parseInt(totalnum)) + " and tt.r>="+(Integer.parseInt(currentpage) - 1) * Integer.parseInt(totalnum);
//		String sql2="select count(*) c "
//				+ "from tb_qjsc t,tuser u,tb_bm m "
//				+ "where t.username=u.username and u.bm=m.code and t.username like '%"+name+"%' "
//				+ " and t.qjkstime>=to_date('"+stime+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and t.qjkstime<=to_date('"+etime+" 23:59:59','yyyy-mm-dd hh24:mi:ss') "
//				+ " and m.bm like '%"+bm+"%' order by t.username,t.qjkstime asc";
//	    List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
//        List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql2);
	    String sql = "select * from (select ts.*,rownum r from (select t.id,t.qjkstime,t.qjjstime,t.spuser,t.spusertwo,t.spuserthree,ceil((qjjstime - qjkstime) * 24) as qjsc,ceil(qjjstime - qjkstime) as qjts,t.username,m.bm,t.qjnr,t.qjtype,t.sqjg,t.sqjgtwo,t.sqjgthree,t.tpone "
                + "from tb_qjsc t,tuser u,tb_bm m "
                + "where t.username=u.username and u.bm=m.code and t.username like ? "
                + " and t.qjkstime>=to_date(?,'yyyy-mm-dd hh24:mi:ss') and t.qjkstime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') "
                + " and m.bm like ? order by t.username,t.qjkstime asc)ts )tt where tt.r<=? and tt.r>=?";
        String sql2="select count(*) c "
                + "from tb_qjsc t,tuser u,tb_bm m "
                + "where t.username=u.username and u.bm=m.code and t.username like ? "
                + " and t.qjkstime>=to_date(?,'yyyy-mm-dd hh24:mi:ss') and t.qjkstime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') "
                + " and m.bm like ? order by t.username,t.qjkstime asc";
		//主页展示
		String status = request.getSession().getAttribute("status") == null?"":String.valueOf(request.getSession().getAttribute("status"));
		String username_qx = request.getSession().getAttribute("username") == null?"":String.valueOf(request.getSession().getAttribute("username"));
		if(status!=null&&!status.isEmpty()&&!status.equals("null")&&status.length()>0&&!username_qx.isEmpty()){
			sql +=" and ((tt.spuser='"+username_qx+"' and tt.sqjg=3) or (tt.spusertwo='"+username_qx+"' and tt.sqjgtwo=3) or (tt.spuserthree='"+username_qx+"' and tt.sqjgthree=3))";
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,"%"+name+"%",stime+" 00:00:00",etime+" 23:59:59","%"+bm+"%",(Integer.parseInt(currentpage) * Integer.parseInt(totalnum)),(Integer.parseInt(currentpage) - 1) * Integer.parseInt(totalnum));
		//主页展示
		if(status!=null&&!status.isEmpty()&&!status.equals("null")&&status.length()>0&&!username_qx.isEmpty()){
			return jacksonUtil.toJson(list);
		}
		List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql2,"%"+name+"%",stime+" 00:00:00",etime+" 23:59:59","%"+bm+"%");
		String sqluser = "select u.username from tuser u,tb_bm t where u.bm=t.code and u.username like ? and t.bm like ?";
		List<Map<String, Object>> userlist = jdbcTemplate.queryForList(sqluser,"%"+name+"%","%"+bm+"%");
		for (int i = 0; i < userlist.size(); i++) {
			String username = userlist.get(i).get("USERNAME").toString();
			userlist.get(i).put("qdcs", qdcxcount(username,stime,etime));
			userlist.get(i).put("qjcs", qjcxcount(username,stime,etime));
			userlist.get(i).put("wqcs", wqcxcount(username,stime,etime));
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("datas", list);
		m.put("count", list2.get(0).get("c"));
		return jacksonUtil.toJson(m);
	}
	/*
	 * 添加
	 */
	public int qjcxadd(
			String name,
			String spuser,
			String userid,
			String kstime,
			String jstime,
			String qjly,
			String qjlx,
			String qjjg,
			String qjsc
			) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String qjid=sdf.format(new Date());
		String sql = "insert into TB_qjsc (qjid,username,spuser,userid,qjkstime,qjjstime,qjnr,qjtype,sqjg,qjsc) values "
        + "(?,?,?,?,to_date(?,'YYYY-MM-dd hh24:mi:ss'),to_date(?,'YYYY-MM-dd hh24:mi:ss'),?,?,?,?)";
		int count=jdbcTemplate.update(sql,qjid,name,spuser,userid,kstime,jstime,qjly,qjlx,Integer.parseInt(qjjg),Integer.parseInt(qjsc));
		return count;
	}
	/*
	 * 删除
	 */
	public int qjcxdel(String sj) {
		String[] id=sj.split(",");
		int count=0;
		for(int i=0;i<id.length;i++){
			String sql = "delete from tb_qjsc where id=?";
				//System.out.println(sql);
			count+=jdbcTemplate.update(sql,id[i]);
		}
		return count;

	}
	/*
	 * 修改
	 */

	public int qjcxeditsave(
			String name,
			String spuser,
			String userid,
			String kstime,
			String jstime,
			String qjly,
			String qjlx,
			String qjjg,
			String qjsc,
			String id
			) {
		
//		SimpleDateFormat asd = new SimpleDateFormat("yyyMMddHHmmss");
//		asd.format(new Date());
		String sql = "update tb_qjsc set username=?,spuser=?,userid=?,qjkstime=to_date(?,'YYYY-MM-dd hh24:mi:ss'),qjjstime=to_date(?,'YYYY-MM-dd hh24:mi:ss'),"
		        + "qjnr=?,qjtype=?,sqjg=?,qjsc=? where id =?";
//		System.out.println(sql);
		int count=jdbcTemplate.update(sql,name,spuser,userid,kstime,jstime,qjly,qjlx,Integer.parseInt(qjjg),Integer.parseInt(qjsc),id);
		return count;
	}

/*
 * 外勤查询
 */
	public String wqcx(HttpServletRequest request, String name, String bm, String stime, String etime,
					   String currentpage, String totalnum) {

//		String sql = "select * from (select ts.*,rownum r from(select t.id,t.bgdz,t.bgtime,t.bgnr,t.sqjg,t.sqjgtwo,t.spuser,t.spusertwo,t.username,m.bm,t.tpone "
//				+ "from tb_wqbgsc t,tuser u,tb_bm m where "
//				+ "t.username=u.username and u.bm=m.code and t.username like '%"+name+"%' "
//				+ " and t.bgtime>=to_date('"+stime+" 00:00:00','yyyy-MM-dd hh24:mi:ss') and t.bgtime<=to_date('"+etime+" 23:59:59','yyyy-MM-dd  hh24:mi:ss') "
//				+ " and m.bm like '%"+bm+"%' order by t.username,t.bgtime asc)ts )tt where tt.r<="+ (Integer.parseInt(currentpage) * Integer.parseInt(totalnum)) + " and tt.r>="+(Integer.parseInt(currentpage) - 1) * Integer.parseInt(totalnum);
//		String sql2="select count(*) c "
//				+ "from tb_wqbgsc t,tuser u,tb_bm m where "
//				+ "t.username=u.username and u.bm=m.code and t.username like '%%' "
//				+ " and t.bgtime>=to_date('"+stime+" 00:00:00','yyyy-MM-dd hh24:mi:ss') and t.bgtime<=to_date('"+etime+" 23:59:59','yyyy-MM-dd  hh24:mi:ss') "
//				+ " and m.bm like '%"+bm+"%' order by t.username,t.bgtime asc";
//		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
//		List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql2);
//		String sqluser = "select u.username from tuser u,tb_bm t where u.bm=t.code and u.username like '%"+name+"%' and t.bm like '%"+bm+"%'";
//		List<Map<String, Object>> userlist = jdbcTemplate.queryForList(sqluser);
	    
	    String sql = "select * from (select ts.*,rownum r from(select t.id,t.bgdz,t.bgtime,t.bgnr,t.sqjg,t.sqjgtwo,t.sqjgthree,t.spuser,t.spusertwo,t.spuserthree,t.username,m.bm,t.tpone "
                + "from tb_wqbgsc t,tuser u,tb_bm m where "
                + "t.username=u.username and u.bm=m.code and t.username like ? "
                + " and t.bgtime>=to_date(?,'yyyy-MM-dd hh24:mi:ss') and t.bgtime<=to_date(?,'yyyy-MM-dd hh24:mi:ss') "
                + " and m.bm like ? order by t.username,t.bgtime asc)ts )tt where tt.r<=? and tt.r>=?";
        String sql2="select count(*) c "
                + "from tb_wqbgsc t,tuser u,tb_bm m where "
                + "t.username=u.username and u.bm=m.code and t.username like ? "
                + " and t.bgtime>=to_date(?,'yyyy-MM-dd hh24:mi:ss') and t.bgtime<=to_date(?,'yyyy-MM-dd hh24:mi:ss') "
                + " and m.bm like ? order by t.username,t.bgtime asc";
		//主页展示
		String status = request.getSession().getAttribute("status") == null?"":String.valueOf(request.getSession().getAttribute("status"));
		String username_qx = request.getSession().getAttribute("username") == null?"":String.valueOf(request.getSession().getAttribute("username"));
		if(status!=null&&!status.isEmpty()&&!status.equals("null")&&status.length()>0&&!username_qx.isEmpty()){
			sql +=" and ((tt.spuser='"+username_qx+"' and tt.sqjg=3) or (tt.spusertwo='"+username_qx+"' and tt.sqjgtwo=3) or (tt.spuserthree='"+username_qx+"' and tt.sqjgthree=3))";
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,"%"+name+"%",stime+" 00:00:00",etime+" 23:59:59","%"+bm+"%",(Integer.parseInt(currentpage) * Integer.parseInt(totalnum)),(Integer.parseInt(currentpage) - 1) * Integer.parseInt(totalnum));
		//主页展示
		if(status!=null&&!status.isEmpty()&&!status.equals("null")&&status.length()>0&&!username_qx.isEmpty()){
			return jacksonUtil.toJson(list);
		}

        List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql2,"%"+name+"%",stime+" 00:00:00",etime+" 23:59:59","%"+bm+"%");
        String sqluser = "select u.username from tuser u,tb_bm t where u.bm=t.code and u.username like ? and t.bm like ?";
        List<Map<String, Object>> userlist = jdbcTemplate.queryForList(sqluser,"%"+name+"%","%"+bm+"%");
	    
	    
	    
		for (int i = 0; i < userlist.size(); i++) {
			String username = userlist.get(i).get("USERNAME").toString();
			userlist.get(i).put("qdcs", qdcxcount(username,stime,etime));
			userlist.get(i).put("qjcs", qjcxcount(username,stime,etime));
			userlist.get(i).put("wqcs", wqcxcount(username,stime,etime));
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("datas", list);
		m.put("count", list2.get(0).get("c"));
		return jacksonUtil.toJson(m);
	}
	/*
	 * 月外勤表
	 */
		public String ywqb(String name, String bm, String stime,String dyts) {

			String sql = "select * from (select ts.*,rownum r from(select t.bgtime,t.bgnr,t.username,m.bm "
					+ "from tb_wqbgsc t,tuser u,tb_bm m where "
					+ "t.username=u.username and u.bm=m.code and t.username like ? "
					+ " and t.bgtime>=to_date(?,'yyyy-MM-dd hh24:mi:ss') and t.bgtime<=to_date(?,'yyyy-MM-dd  hh24:mi:ss') "
					+ " and m.bm like ? order by t.username,t.bgtime asc)ts )tt";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,"%"+name+"%",stime+"-01 00:00:00",stime+"-"+dyts+" 23:59:59","%"+bm+"%");
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("datas", list);
			return jacksonUtil.toJson(m);
		}
	/*
	 * 添加
	 */

	public int wqcxadd(
			String username,
			String spuser,
			String userid,
			String time,
			String address,
			String content,
			String spjg
			) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String bgid=sdf.format(new Date());
		String sql = "insert into TB_WQBGSC (bgid,username,spuser,userid,bgtime,bgdz,bgnr,sqjg"
				+ ") values (?,?,?,?,to_date(?,'YYYY-MM-dd hh24:mi:ss'),?,?,?)";
//		System.out.println(sql);
		int count=jdbcTemplate.update(sql,bgid,username,spuser,userid,time,address,content,Integer.parseInt(spjg));
		return count;
	}
	/*
	 * 删除
	 */
	public int wqcxdel(String sj) {
		String[] id=sj.split(",");
		int count=0;
		for(int i=0;i<id.length;i++){
			String sql = "delete from tb_wqbgsc where id=?";
//			System.out.println(sql);
			count+=jdbcTemplate.update(sql,id[i]);
		}
		return count;

	}
	/*
	 * 修改
	 */
	public int wqcxeditsave(
			String username,
			String spuser,
			String userid,
			String time,
			String address,
			String content,
			String spjg,
			String id
			) {
		String sql = "update tb_wqbgsc set username=?,spuser=?,userid=?,bgdz=?,bgtime=to_date(?,'yyyy-MM-dd hh24:mi:ss'),bgnr=?,sqjg=? where id =?";
		System.out.println(sql);
		int count=jdbcTemplate.update(sql,username,spuser,userid,address,time,content,Integer.parseInt(spjg),id);
		return count;
	}

	public String qdcxcount(String name, String stime, String etime) {
		String sql2="select count(*) c from (select * from tb_signin t where t.username  like ?) tin  left join tb_signout tout "
				+ "on tin.yeartime=tout.yeartime and tin.username=tout.username "
				+ "and tin.qdtime>=to_date(?,'yyyy-mm-dd') and tin.qdtime<=to_date(?,'yyyy-mm-dd') ";
		List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql2,"%"+name+"%",stime,etime);
		return list2.get(0).get("c").toString();
	}

	public String qjcxcount(String name, String stime, String etime) {
		String sql2="select count(*) c "
				+ "from tb_qjsc t "
				+ "where t.username like ? "
				+ " and t.qjkstime>=to_date(?,'yyyy-mm-dd') and t.qjkstime<=to_date(?,'yyyy-mm-dd') ";
		List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql2,"%"+name+"%",stime,etime);
		return list2.get(0).get("c").toString();
	}

	public String wqcxcount(String name, String stime, String etime) {
		String sql2="select count(*) c "
				+ "from tb_wqbgsc t where "
				+ "t.username like ? "
				+ " and t.bgtime>=to_date(?,'yyyy-mm-dd') and t.bgtime<=to_date(?,'yyyy-mm-dd') ";
		List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql2,"%"+name+"%",stime,etime);
		return list2.get(0).get("c").toString();
	}
	//加班查询
	public String jbcx(HttpServletRequest request, String name, String bm, String kstime, String jstime,
					   String currentpage, String totalnum) {
		String sql = "select * from (select ts.*,rownum r from(select t.id,t.jbkstime,t.jbjstime,t.sqjg,t.sqjgtwo,t.sqjgthree,t.spuser,t.spusertwo,t.spuserthree,t.jbnr,t.username,t.jbsc,m.bm,t.tpone "
				+ "from tb_jb t,tuser u,tb_bm m where "
				+ "t.username=u.username and u.bm=m.code and t.username like ? "
				+ " and t.jbkstime>=to_date(?,'yyyy-mm-dd hh24:mi:ss') and t.jbkstime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') "
				+ " and m.bm like ? order by t.username,t.jbkstime asc)ts )tt where tt.r<=? and tt.r>=?";
		String sql2="select count(*) c "
				+ "from tb_jb t,tuser u,tb_bm m where "
				+ "t.username=u.username and u.bm=m.code and t.username like ? "
				+ " and t.jbkstime>=to_date(?,'yyyy-mm-dd hh24:mi:ss') and t.jbkstime<=to_date(?,'yyyy-mm-dd hh24:mi:ss') "
				+ " and m.bm like ? order by t.username,t.jbkstime asc";
		//主页展示
		String status = request.getSession().getAttribute("status") == null?"":String.valueOf(request.getSession().getAttribute("status"));
		String username_qx = request.getSession().getAttribute("username") == null?"":String.valueOf(request.getSession().getAttribute("username"));
		if(status!=null&&!status.isEmpty()&&!status.equals("null")&&status.length()>0&&!username_qx.isEmpty()){
			sql +=" and ((tt.spuser='"+username_qx+"' and tt.sqjg=3) or (tt.spusertwo='"+username_qx+"' and tt.sqjgtwo=3) or (tt.spuserthree='"+username_qx+"' and tt.sqjgthree=3))";
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,"%"+name+"%",kstime+" 00:00:00",jstime+" 23:59:59","%"+bm+"%",(Integer.parseInt(currentpage) * Integer.parseInt(totalnum)),(Integer.parseInt(currentpage) - 1) * Integer.parseInt(totalnum));
		//主页展示
		if(status!=null&&!status.isEmpty()&&!status.equals("null")&&status.length()>0&&!username_qx.isEmpty()){
			return jacksonUtil.toJson(list);
		}
		List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql2,"%"+name+"%",kstime+" 00:00:00",jstime+" 23:59:59","%"+bm+"%");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("datas", list);
		m.put("count", list2.get(0).get("c"));
		return jacksonUtil.toJson(m);
	}
	public int jbcxadd(
			String username,
			String spuser,
			String userid,
//			String bm,
			String stime,
			String etime,
			String content,
			String sqjg,
			String jbsc
			) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String jbid=sdf.format(new Date());
		String sqlbm = "select * from tuser t where t.userid=?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlbm,userid);
		String bm = list.get(0).get("BM").toString();
		String sql = "insert into TB_JB (jbid,username,spuser,userid,bm,jbkstime,jbjstime,jbnr,sqjg,jbsc"
				+ ") values (?,?,?,?,?,"
				        + "to_date(?,'YYYY-MM-dd hh24:mi:ss'),to_date(?,'YYYY-MM-dd hh24:mi:ss'),"
				                + "?,?,?')";
//		System.out.println(sql);
		int count=jdbcTemplate.update(sql,jbid,username,spuser,userid,bm,stime,etime,content,Integer.parseInt(sqjg));
		return count;
	}
	public int jbcxdel(String sj) {
		String[] id=sj.split(",");
		int count=0;
		for(int i=0;i<id.length;i++){
			String sql = "delete from tb_jb where id=?";
			System.out.println(sql);
			count+=jdbcTemplate.update(sql,id[i]);
		}
		return count;

	}
	public int jbcxeditsave(
			String username,
			String spuser,
			String userid,
			String stime,
			String etime,
			String content,
			String sqjg,
			String jbsc,
			String id
			) {
		String sqlbm = "select * from tuser t where t.userid=?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlbm,userid);
		String bm = list.get(0).get("BM").toString();
		String sql = "update tb_jb set username=?,bm=?,spuser=?,userid=?,jbkstime=to_date(?,'yyyy-MM-dd hh24:mi:ss'),jbjstime=to_date(?,'yyyy-MM-dd hh24:mi:ss'),jbnr=?,sqjg=?,jbsc=? where id =?";
//		System.out.println(sql);
		int count=jdbcTemplate.update(sql,username,bm,spuser,userid,stime,etime,content,Integer.parseInt(sqjg),Integer.parseInt(jbsc),id);
		return count;
	}


	public String queryqj(String id) {
		String  sql="select t.qjkstime,t.qjjstime,t.spuser,t.qjnr,t.qjtype,t.sqjg,t.qjsc,u.username,u.userid from tb_qjsc t,tuser u where t.username=u.username AND t.id=?";
//		System.out.println(sql);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,id);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("datas", list);
//		System.out.println(m);
		return jacksonUtil.toJson(m);
	}
	public String querywq(String id) {
		String  sql="select * from tb_wqbgsc where id=?";
//		System.out.println(sql);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,id);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("datas", list);
		System.out.println(m);
		return jacksonUtil.toJson(m);
	}
	public String queryjb(String id) {
		String  sql="select * from tb_jb where id=?";
//		System.out.println(sql);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,id);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("datas", list);
		System.out.println(m);
		return jacksonUtil.toJson(m);
	}
	public String selectname() {
		String sql = "select * from tuser";
		System.out.println(sql);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("datas", list);
		return jacksonUtil.toJson(m);
	}
	public void dcqd(HttpServletRequest request, HttpServletResponse response, String name, String stime, String cdyy, String etime, String ztyy){
		//导出的数据
		String sql = "select * from "
					+"(select ts.*,rownum r from "
					+"(select tin.username,tin.qdtime,tin.cdyy,tout.qttime,tout.ztyy from "
					+"(select * from tb_signin t where  t.username in "
					+"(select u.username from tuser u where u.username like '%%' and u.bm in "
					+"(select t.code from tb_bm t where t.bm like '%%')) "
					+"and t.qdtime>=to_date('2016-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss') "
					+"and t.qdtime<=to_date('2018-12-31 23:59:59','yyyy-mm-dd hh24:mi:ss')) tin left join tb_signout tout "
					+"on tin.yeartime=tout.yeartime and tin.username=tout.username "
					+"order by tin.username,tin.qdtime desc)ts ,tb_bm m,tuser y where ts.username=y.username and y.bm=m.code)";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		//表名
		String fileName = "签到表";
		//表的列名
		String header = "<caption>"+fileName+"</caption><thead><tr><th width='200'>序号</th>"
				+ "<th width='200'>姓名</th><th width='200'>签到时间</th><th width='200'>迟到原因</th>"
				+ "<th width='200'>签退时间</th><th width='200'>早退原因</th></tr></thead><tbody>";
		
	}

//	public String selectwqname() {
//		String sql = "select u.username from TB_WQBGSC t,tuser u where t.username=u.username ";
//		System.out.println(sql);
//		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
//		Map<String, Object> m = new HashMap<String, Object>();
//		m.put("datas", list);
//		System.out.println(list);
//		return jacksonUtil.toJson(m);
//
//	}

}