package com.allmsi.dingtalk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.allmsi.dingtalk.model.po.DingTalkUserDept;

@Mapper
public interface DingTalkUserDeptMapper {

	int insertSelective(DingTalkUserDept record);

	int insertBatch(List<DingTalkUserDept> userDeptList);

	int deleteByUserId(String id);

	List<DingTalkUserDept> selectDeptByDingUserId(String dingUserId);

}