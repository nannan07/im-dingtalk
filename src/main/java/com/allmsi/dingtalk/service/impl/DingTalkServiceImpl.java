package com.allmsi.dingtalk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.allmsi.dingtalk.dao.DingTalkDeptMapper;
import com.allmsi.dingtalk.dao.DingTalkUserDeptMapper;
import com.allmsi.dingtalk.dao.DingTalkUserMapper;
import com.allmsi.dingtalk.helper.DepartmentHelper;
import com.allmsi.dingtalk.model.po.DingTalkDept;
import com.allmsi.dingtalk.model.po.DingTalkUser;
import com.allmsi.dingtalk.model.po.DingTalkUserDept;
import com.allmsi.dingtalk.service.DingTalkService;
import com.allmsi.sys.util.UUIDUtil;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import com.dingtalk.open.client.api.model.corp.DepartmentDetail;

@Service
public class DingTalkServiceImpl implements DingTalkService {

	private List<CorpUserDetail> getUserList = new ArrayList<CorpUserDetail>();

	@Resource
	private DingTalkDeptMapper deptDao;

	@Resource
	private DingTalkUserMapper userDao;

	@Resource
	private DingTalkUserDeptMapper udDao;

	public int insertDept() {
		List<DingTalkDept> deptList = new ArrayList<DingTalkDept>();
		List<DepartmentDetail> departmentDetail = DepartmentHelper.listDepartmentDetail();
		for (DepartmentDetail departmentDetail2 : departmentDetail) {
			deptList.add(new DingTalkDept(departmentDetail2));
		}
		if (deptList != null && deptList.size() > 0) {
			return deptDao.insertBatch(deptList);
		}
		return 0;
	}

	public int insertUser() {
		// 获取到所有部门ID
		List<Long> deptIds = new ArrayList<Long>();
		deptIds.addAll(DepartmentHelper.listDepartmentsId());
		if (deptIds != null && deptIds.size() > 0) {
			ExecutorService executor = Executors.newCachedThreadPool();
			CountDownLatch latch = new CountDownLatch(deptIds.size());
			for (Long long1 : deptIds) {
				executor.execute(new getUserList(latch, long1));
			}
			executor.execute(new insertUser(latch));
		}
		return deptIds.size();
	}

	public class getUserList implements Runnable {
		private CountDownLatch downLatch;
		private Long deptId;

		public getUserList(CountDownLatch downLatch, Long deptId) {
			this.downLatch = downLatch;
			this.deptId = deptId;
		}

		public void run() {
			this.doWork();
			this.downLatch.countDown();
		}

		private void doWork() {
			getUserList.addAll(DepartmentHelper.getUserList(Long.toString(deptId)));
		}
	}

	public class insertUser implements Runnable {
		private CountDownLatch downLatch;

		public insertUser(CountDownLatch downLatch) {
			this.downLatch = downLatch;
		}

		public void run() {
			try {
				this.downLatch.await();
			} catch (InterruptedException e) {
			}
			List<DingTalkUser> userList = new ArrayList<DingTalkUser>();
			Map<String, List<Long>> userDeptMap = new HashMap<String, List<Long>>();
			if (getUserList != null && getUserList.size() > 0) {
				for (CorpUserDetail corpUserDetail : getUserList) {
					DingTalkUser user = new DingTalkUser(corpUserDetail);
					userList.add(user);
					if (!userDeptMap.containsKey(corpUserDetail.getUserid())) {
						userDeptMap.put(user.getId(), corpUserDetail.getDepartment());
					}
				}
				List<DingTalkUserDept> udList = new ArrayList<DingTalkUserDept>();
				for (String key : userDeptMap.keySet()) {
					List<Long> deptList = userDeptMap.get(key);
					if (deptList != null && deptList.size() > 0) {
						for (Long long1 : deptList) {
							udList.add(new DingTalkUserDept(UUIDUtil.getUUID(), key, long1, "01"));
						}
					}
				}
				userDao.insertBatch(userList);
				if (udList != null && udList.size() > 0) {
					udDao.insertBatch(udList);
				}
			}
		}
	}

	@Override
	public List<DingTalkDept> selectDeptList() {
		return deptDao.selectDeptList();
	}
}
