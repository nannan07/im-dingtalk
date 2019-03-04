package com.allmsi.dingtalk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.allmsi.dingtalk.model.po.DingTalkUser;

@Mapper
public interface DingTalkUserMapper {
	int deleteByPrimaryKey(String id);

	int updateByPrimaryKeySelective(DingTalkUser user);
	
	int insertBatch(List<DingTalkUser> userList);

	List<DingTalkUser> selectUserList();

	int updateBatch(List<DingTalkUser> updateList);

	int delBatch(List<DingTalkUser> delList);

//	DingTalkUser selectByDingUserId(String userid);
}