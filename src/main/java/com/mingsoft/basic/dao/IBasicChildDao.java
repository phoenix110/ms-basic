package com.mingsoft.basic.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.basic.entity.BasicChildEntity;


/**

 * 基础表之间的子父关联关系持久化接口

 * @author 史爱华 QQ:924690193

 * @version

 * 版本号：100-000-000<br/>

 * 创建日期：2015-08-21<br/>

 * 历史修订：<br/>

 */
public interface IBasicChildDao extends IBaseDao{
	
	/**
	 * 根据基础表id删除数据
	 * @param basicId 基础表id
	 */
	void delete(int basicId);
	
	/**
	 * 根据基础表id查询基础表关联数据集合
	 * @param basicId  基础表id
	 * @return  基础表关联数据集合
	 */
	List<BasicChildEntity> queryByBasicId(int basicId);
	
	/**
	 * 根据basicId集合实现批量的删除
	 * @param basicIds basicId集合
	 */
	void deleteBatch(@Param("basicIds")int[] basicIds);
	
	/**
	 * 根据基础id和子id集合删除
	 * @param basicId 基础id
	 * @param basicChildIds 子集合id
	 */
	void deleteByChildIds(@Param("basicId")int basicId,@Param("basicChildIds")int[] basicChildIds);
	
	/**
	 * 根据基础id和子id删除
	 * @param basicId  基础id
	 * @param basicChildId 子id
	 */
	void deleteByBasicIdAndChildId(@Param("basicId")int basicId,@Param("basicChildId")int basicChildId);
}
