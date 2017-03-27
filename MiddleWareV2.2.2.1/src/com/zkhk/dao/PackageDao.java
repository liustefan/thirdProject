package com.zkhk.dao;

import java.util.List;

import com.zkhk.entity.Mem5;
import com.zkhk.entity.Opsp;

public interface PackageDao {
     /**
      * 查询套餐信息
      * @return
      * @throws Exception
      */
	List<Opsp> findPackagebyParam(int memberId) throws Exception;
    /**
     * 查询会员订购套餐信息
     * @param memberId
     * @return
     */
	List<Mem5> findMemPackagebyParam(int memberId)throws Exception;

}
