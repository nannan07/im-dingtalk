package com.allmsi.dingtalk.service;

import java.util.List;

import com.allmsi.dingtalk.model.po.DingTalkDept;

public interface DingTalkService {
	
	int insertDept();

	int insertUser();
	
	List<DingTalkDept> selectDeptList();

}
