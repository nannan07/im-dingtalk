package com.allmsi.dingtalk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.allmsi.dingtalk.model.po.DingTalkDept;

@Mapper
public interface DingTalkDeptMapper {
	int insertSelective(DingTalkDept dept);
	
	int insertBatch(List<DingTalkDept> deptList);

	int updateByPrimaryKeySelective(DingTalkDept dept);

	int deleteByPrimaryKey(String id);

	List<DingTalkDept> selectDeptList();

	int updateBatch(List<DingTalkDept> updateList);

	int delBatch(List<DingTalkDept> updateList);

	
}