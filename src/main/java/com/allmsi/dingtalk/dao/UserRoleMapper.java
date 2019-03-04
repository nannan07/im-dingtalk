package com.allmsi.dingtalk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.allmsi.dingtalk.model.po.UserRole;

@Mapper
public interface UserRoleMapper {
	
	int insertBatch(List<UserRole> userRoleList);

}