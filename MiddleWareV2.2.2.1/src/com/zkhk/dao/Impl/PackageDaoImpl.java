package com.zkhk.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.zkhk.dao.PackageDao;
import com.zkhk.entity.Mem5;
import com.zkhk.entity.Opsp;
import com.zkhk.jdbc.JdbcService;
import com.zkhk.util.TimeUtil;
@Repository(value="packageDao")
public class PackageDaoImpl implements PackageDao {
	 @Resource
	 private JdbcService jdbcService;

	public List<Opsp> findPackagebyParam(int memberId) throws Exception {
		String sql="select PackageCode,OrgId,PackageName,PackageDesc,PackageType,Price,PackageLevel,CreateTime,Createid,CreateName,UseTag from opsp where orgId in(select orgId from  omem  where memberId=?)";
		List<Opsp> list=new  ArrayList<Opsp>();
		SqlRowSet rowSet=jdbcService.query(sql,new Object[]{memberId});
		while(rowSet.next()){
			Opsp opsp=new Opsp();
			opsp.setCreateId(rowSet.getInt("Createid"));
			opsp.setCreateName(rowSet.getString("CreateName"));
			opsp.setCreateTime(TimeUtil.paserDatetime2(rowSet.getString("CreateTime")));
			opsp.setOrgId(rowSet.getInt("OrgId"));
			opsp.setPackageCode(rowSet.getInt("PackageCode"));
			opsp.setPackageDesc(rowSet.getString("PackageDesc"));
			opsp.setPackageLevel(rowSet.getInt("PackageLevel"));
			opsp.setPackageName(rowSet.getString("PackageName"));
			opsp.setPackageType(rowSet.getInt("PackageType"));
			opsp.setPrice(rowSet.getFloat("Price"));
			opsp.setUserTag(rowSet.getString("UseTag"));
			list.add(opsp);
		}
		return list;
	}

	public List<Mem5> findMemPackagebyParam(int memberId) throws Exception {
		String sql="select a.LineNum,a.PackageCode,a.Tag, a.CreateTime atime,a.Createid aid,a.CreateName aname, b.OrgId,b.PackageName,b.PackageDesc,b.PackageType,b.Price,b.PackageLevel,b.CreateTime ,b.Createid,b.CreateName  ,b.UseTag from mem5 a left join opsp b on  a.PackageCode=b.PackageCode where a.memberId=?";
		List<Mem5> list=new  ArrayList<Mem5>();
		SqlRowSet rowSet=jdbcService.query(sql,new Object[]{memberId});
		while(rowSet.next()){
			Mem5 mem5=new Mem5();
			Opsp opsp=new Opsp();
			opsp.setCreateId(rowSet.getInt("Createid"));
			opsp.setCreateName(rowSet.getString("CreateName"));
			opsp.setCreateTime(TimeUtil.paserDatetime2(rowSet.getString("CreateTime")));
			opsp.setOrgId(rowSet.getInt("OrgId"));
			opsp.setPackageCode(rowSet.getInt("PackageCode"));
			opsp.setPackageDesc(rowSet.getString("PackageDesc"));
			opsp.setPackageLevel(rowSet.getInt("PackageLevel"));
			opsp.setPackageName(rowSet.getString("PackageName"));
			opsp.setPackageType(rowSet.getInt("PackageType"));
			opsp.setPrice(rowSet.getFloat("Price"));
			opsp.setUserTag(rowSet.getString("UseTag"));
			mem5.setPackAge(opsp);
			mem5.setCreateId(rowSet.getInt("aid"));
			mem5.setCreateName(rowSet.getString("aname"));
			mem5.setCreateTime(TimeUtil.paserDatetime2(rowSet.getString("atime")));
			mem5.setLineNum(rowSet.getInt("LineNum"));
			mem5.setMemberId(memberId);
			mem5.setTag(rowSet.getString("Tag"));
			list.add(mem5);
		}
		return list;
	}
}
