package com.allmsi.dingtalk.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.allmsi.dingtalk.dao.DingTalkUserDeptMapper;
import com.allmsi.dingtalk.dao.DingTalkUserMapper;
import com.allmsi.dingtalk.helper.DepartmentHelper;
import com.allmsi.dingtalk.model.po.DingTalkUser;
import com.allmsi.dingtalk.model.po.DingTalkUserDept;
import com.allmsi.dingtalk.service.DingUserService;
import com.allmsi.sys.util.UUIDUtil;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;

@Service
public class DingUserServiceImpl implements DingUserService {

	@Resource
	private DingTalkUserMapper userDao;

	@Resource
	private DingTalkUserDeptMapper udDao;

	public Object userInfo(String userid) {
		CorpUserDetail corpUserDetail = DepartmentHelper.getUserInfo(userid);
		if (corpUserDetail != null) {
			// 添加用户信息
			DingTalkUser userinfo = new DingTalkUser(corpUserDetail);
			List<DingTalkUser> list = new ArrayList<DingTalkUser>();
			list.add(userinfo);
			userDao.insertBatch(list);
			List<Long> deptIds = corpUserDetail.getDepartment();
			List<DingTalkUserDept> udList = new ArrayList<DingTalkUserDept>();
			for (Long long1 : deptIds) {
				udList.add(new DingTalkUserDept(UUIDUtil.getUUID(), userinfo.getId(), long1, null));
			}
			if (udList != null && udList.size() > 0) {
				int msg = udDao.insertBatch(udList);
				return msg == 0 ? false : true;
			}
		}
		return false;
	}
}
