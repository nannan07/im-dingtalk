package com.allmsi.dingtalk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.allmsi.dingtalk.dao.DingTalkDeptMapper;
import com.allmsi.dingtalk.dao.DingTalkUserDeptMapper;
import com.allmsi.dingtalk.dao.DingTalkUserMapper;
import com.allmsi.dingtalk.dao.UserRoleMapper;
import com.allmsi.dingtalk.helper.DepartmentHelper;
import com.allmsi.dingtalk.model.po.DingTalkDept;
import com.allmsi.dingtalk.model.po.DingTalkUser;
import com.allmsi.dingtalk.model.po.DingTalkUserDept;
import com.allmsi.dingtalk.model.po.UserRole;
import com.allmsi.dingtalk.service.DingTalkService;
import com.allmsi.dingtalk.service.ScheduledService;
import com.allmsi.sys.util.UUIDUtil;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import com.dingtalk.open.client.api.model.corp.DepartmentDetail;

@Service
public class ScheduledServicImpl implements ScheduledService {

	@Resource
	private DingTalkService dingTalkService;

	@Resource
	private DingTalkDeptMapper deptDao;

	@Resource
	private DingTalkUserMapper userDao;

	@Resource
	private DingTalkUserDeptMapper udDao;

	@Resource
	private UserRoleMapper userRoleDao;

	@Override
	public String synDept() {
		List<DepartmentDetail> deptList = DepartmentHelper.listDepartmentDetail();// 获取钉钉中部门信息
		List<DingTalkDept> newList = new ArrayList<DingTalkDept>();
		for (DepartmentDetail departmentDetail : deptList) {
			if (departmentDetail != null) {
				newList.add(new DingTalkDept(departmentDetail));
			}
		}

		Map<String, DingTalkDept> dingMap = new HashMap<String, DingTalkDept>();
		Map<String, DingTalkDept> dingMapNew = new HashMap<String, DingTalkDept>();
		for (DingTalkDept dingTalkDept : newList) {
			dingMap.put(dingTalkDept.getDingDeptId(), dingTalkDept);
			dingMapNew.put(dingTalkDept.getDingDeptId(), dingTalkDept);
		}

		List<DingTalkDept> dList = dingTalkService.selectDeptList();// 获取系统中部门信息
		Map<String, DingTalkDept> authMap = new HashMap<String, DingTalkDept>();
		Map<String, DingTalkDept> authMapNew = new HashMap<String, DingTalkDept>();
		for (DingTalkDept dingTalkDept : dList) {
			authMap.put(dingTalkDept.getDingDeptId(), dingTalkDept);
			authMapNew.put(dingTalkDept.getDingDeptId(), dingTalkDept);
		}

		List<DingTalkDept> updateList = new ArrayList<DingTalkDept>();// 需要更新的部门信息
		List<DingTalkDept> insertList = new ArrayList<DingTalkDept>();// 需要添加的部门信息
		List<DingTalkDept> delList = new ArrayList<DingTalkDept>();// 需要删除的部门信息
		String updateDeptStr = "部门原始数据";
		for (String key : dingMap.keySet()) {
			if (authMap.containsKey(key)) { // 如果系统中有该部门
				if (dingMap.get(key).equals(authMap.get(key))) {// 部门信息相同，不做处理
					dingMapNew.remove(key);
					authMapNew.remove(key);
				} else {// 部门信息有改动
					updateDeptStr = updateDeptStr + authMap.get(key).toString();
					updateList.add(dingMap.get(key));// 需要更新的部门信息
					dingMapNew.remove(key);
					authMapNew.remove(key);
				}
			} else {// 系统中没有该部门
				insertList.add(dingMap.get(key));// 需要新增的部门信息
				dingMapNew.remove(key);
			}
		}
		if (dingMapNew.size() > 0) {
			for (String key : dingMapNew.keySet()) {
				insertList.add(dingMapNew.get(key));// 需要新增的部门信息
			}
		}
		if (authMapNew.size() > 0) {
			for (String key : authMapNew.keySet()) {
				delList.add(authMapNew.get(key));// 需要删除的部门信息
			}
		}

		if (insertList != null && insertList.size() > 0) {
			deptDao.insertBatch(insertList);
		}
		if (updateList != null && updateList.size() > 0) {
			deptDao.updateBatch(updateList);
		}
		if (delList != null && delList.size() > 0) {
			deptDao.delBatch(delList);
		}
		return synDeptMail(newList, dList, insertList, updateList, delList, updateDeptStr);
	}

	public String synUser() {
		// 获取到所有部门ID
		List<Long> deptIds = new ArrayList<Long>();
		deptIds.addAll(DepartmentHelper.listDepartmentsId());
		if (deptIds != null && deptIds.size() > 0) {
			List<CorpUserDetail> getUserList1 = new ArrayList<CorpUserDetail>();
			for (Long long1 : deptIds) {
				getUserList1.addAll(DepartmentHelper.getUserList(Long.toString(long1)));
			}
			List<DingTalkUser> userList = new ArrayList<DingTalkUser>();// 钉钉现有用户
			Map<String, List<Long>> userDeptMap = new HashMap<String, List<Long>>();// 钉钉用户--部门
			if (getUserList1 != null && getUserList1.size() > 0) {
				for (CorpUserDetail corpUserDetail : getUserList1) {
					if (corpUserDetail != null) {
						DingTalkUser user = new DingTalkUser(corpUserDetail);
						userList.add(user);
						String dingUserId = corpUserDetail.getUserid();
						if (!userDeptMap.containsKey(dingUserId)) {
							userDeptMap.put(dingUserId, corpUserDetail.getDepartment());
						}
					}
				}
				Map<String, DingTalkUser> dingMap = new HashMap<String, DingTalkUser>();
				Map<String, DingTalkUser> dingMapNew = new HashMap<String, DingTalkUser>();
				for (DingTalkUser dingTalkUser : userList) {
					String dingUserId = dingTalkUser.getDingUserId();
					if (!dingMap.containsKey(dingUserId)) {
						dingMap.put(dingUserId, dingTalkUser);
						dingMapNew.put(dingUserId, dingTalkUser);
					}
				}

				List<DingTalkUser> authUserList = userDao.selectUserList();
				Map<String, DingTalkUser> authMap = new HashMap<String, DingTalkUser>();
				Map<String, DingTalkUser> authMapNew = new HashMap<String, DingTalkUser>();
				for (DingTalkUser dingTalkUser : authUserList) {
					String dingUserId = dingTalkUser.getDingUserId();
					if (!authMap.containsKey(dingUserId)) {
						authMap.put(dingUserId, dingTalkUser);
						authMapNew.put(dingUserId, dingTalkUser);
					}
				}

				List<DingTalkUser> updateList = new ArrayList<DingTalkUser>();
				List<DingTalkUser> insertList = new ArrayList<DingTalkUser>();
				List<DingTalkUser> delList = new ArrayList<DingTalkUser>();
				String updateUserStr = "用户原始信息";
				for (String key : dingMap.keySet()) {
					if (authMap.containsKey(key)) { // 如果系统中有该用户
						if (0 == authMap.get(key).getDel() && dingMap.get(key).equals(authMap.get(key))) {// 用户信息相同，不做处理
							dingMapNew.remove(key);
							authMapNew.remove(key);
						} else {// 用户信息有改动
							updateUserStr = updateUserStr + "\n" + authMap.get(key).toString()+"/"+dingMap.get(key);
							updateList.add(dingMap.get(key));// 需要更新的用户信息
							dingMapNew.remove(key);
							authMapNew.remove(key);
						}
					} else {// 系统中没有该用户
						insertList.add(dingMap.get(key));// 需要新增的用户信息
						dingMapNew.remove(key);
					}
				}
				if (dingMapNew.size() > 0) {
					for (String key : dingMapNew.keySet()) {
						insertList.add(dingMapNew.get(key));// 需要新增的用户信息
					}
				}
				if (authMapNew.size() > 0) {
					for (String key : authMapNew.keySet()) {
						delList.add(authMapNew.get(key));// 需要删除的用户信息
					}
				}
				if (insertList != null && insertList.size() > 0) {
					userDao.insertBatch(insertList);
				}
				if (updateList != null && updateList.size() > 0) {
					userDao.updateBatch(updateList);
				}
				if (delList != null && delList.size() > 0) {
					userDao.delBatch(delList);
				}

				List<DingTalkUserDept> udInsertList = new ArrayList<DingTalkUserDept>();
				// 用户---部门
				for (String key : userDeptMap.keySet()) {
					List<DingTalkUserDept> udList = udDao.selectDeptByDingUserId(key);// 系统中用户的部门
					List<String> deptIds1 = new ArrayList<String>();
					for (DingTalkUserDept dingTalkUserDept : udList) {
						deptIds1.add(dingTalkUserDept.getDeptId());
					}
					List<Long> deptLongs = userDeptMap.get(key);// 钉钉中用户--部门
					if (deptLongs != null && deptLongs.size() > 0) {// 系统存在该用户的部门信息
						// 判断部门信息是否相同
						if (!equelsUD(deptIds1, deptLongs)) {// 不相同
							udDao.deleteByUserId(key);
							for (Long long1 : deptLongs) {
								// 重新添加
								udInsertList.add(new DingTalkUserDept(UUIDUtil.getUUID(), key, long1, "01"));
							}
						}

					} else {// 不存在
						for (Long long1 : deptLongs) {
							udInsertList.add(
									new DingTalkUserDept(UUIDUtil.getUUID(), dingMap.get(key).getId(), long1, "01"));
						}
					}
				}
				if (udInsertList != null && udInsertList.size() > 0) {
					udDao.insertBatch(udInsertList);
				}
				if (insertList != null && insertList.size() > 0) {// 添加普通角色
					List<UserRole> userRoleList = new ArrayList<UserRole>();
					for (DingTalkUser dingTalkUser : insertList) {
						userRoleList.add(new UserRole(UUIDUtil.getUUID(), dingTalkUser.getDingUserId(),
								"eb9f266f74e94231a9b367dc6d7ac96c"));
					}
					userRoleDao.insertBatch(userRoleList);
				}
				return synUserMail(userList, authUserList, insertList, updateList, delList, updateUserStr);
			}
		}
		return null;
	}

	private boolean equelsUD(List<String> deptIds, List<Long> deptLongs) {
		if (deptIds != null && deptLongs != null) {
			if (deptIds.size() != deptLongs.size()) {
				return false;
			}
			List<String> dept2s = new ArrayList<String>();
			for (Long long1 : deptLongs) {
				dept2s.add(Long.toString(long1));
			}

			if (deptIds.containsAll(dept2s) && dept2s.containsAll(deptIds)) {
				return true;
			}
		}
		return false;
	}

	private String synUserMail(List<DingTalkUser> dingList, List<DingTalkUser> dataBaseList,
			List<DingTalkUser> insertList, List<DingTalkUser> updateList, List<DingTalkUser> delList,
			String updateUserStr) {
		int dingCount = 0;
		int dataBaseCount = 0;
		int insertCount = 0;
		int updateCount = 0;
		int delCount = 0;
		if (dingList != null && dingList.size() > 0) {
			dingCount = dingList.size();
		}
		if (dataBaseList != null && dataBaseList.size() > 0) {
			dataBaseCount = dataBaseList.size();
		}
		String insertString = "";
		if (insertList != null && insertList.size() > 0) {
			insertCount = insertList.size();
			for (DingTalkUser dingTalkUser : insertList) {
				insertString = insertString + dingTalkUser.getId() + "---" + dingTalkUser.getUserName() + "\n";
			}
		}
		String updateString = "";
		if (updateList != null && updateList.size() > 0) {
			updateCount = updateList.size();
			for (DingTalkUser dingTalkUser : updateList) {
				updateString = updateString + dingTalkUser.getId() + "---" + dingTalkUser.getUserName() + "\n";
			}
			updateString = updateUserStr + "\n\n" + updateString;
		}
		String delString = "";
		if (delList != null && delList.size() > 0) {
			delCount = delList.size();
			for (DingTalkUser dingTalkUser : delList) {
				delString = delString + dingTalkUser.getId() + "---" + dingTalkUser.getUserName() + "\n";
			}
		}
		String synUser = "6,钉钉现有用户数 " + dingCount + " 个;\n";
		synUser = synUser + "7,数据库现有用户数 " + dataBaseCount + " 个;\n";
		synUser = synUser + "8,数据库新增用户数 " + insertCount + " 个: " + insertString + "\n";
		synUser = synUser + "9,数据库修改用户数 " + updateCount + " 个: " + updateString + "\n";
		synUser = synUser + "10,数据库删除用户数 " + delCount + " 个:" + delString + "\n";
		return synUser;
	}

	private String synDeptMail(List<DingTalkDept> dingList, List<DingTalkDept> dataBaseList,
			List<DingTalkDept> insertList, List<DingTalkDept> updateList, List<DingTalkDept> delList,
			String updateDeptStr) {
		int dingCount = 0;
		int dataBaseCount = 0;
		int insertCount = 0;
		int updateCount = 0;
		int delCount = 0;
		if (dingList != null && dingList.size() > 0) {
			dingCount = dingList.size();
		}
		if (dataBaseList != null && dataBaseList.size() > 0) {
			dataBaseCount = dataBaseList.size();
		}
		String insertString = "";
		if (insertList != null && insertList.size() > 0) {
			insertCount = insertList.size();
			for (DingTalkDept dingTalkDept : insertList) {
				insertString = insertString + dingTalkDept.getId() + "---" + dingTalkDept.getDeptName() + "\n";
			}
		}
		String updateString = "";
		if (updateList != null && updateList.size() > 0) {
			updateCount = updateList.size();
			for (DingTalkDept dingTalkDept : updateList) {
				updateString = updateString + dingTalkDept.getId() + "---" + dingTalkDept.getDeptName() + "\n";
			}
			updateString = updateDeptStr + "\n\n" + updateString;
		}
		String delString = "";
		if (delList != null && delList.size() > 0) {
			delCount = delList.size();
			for (DingTalkDept dingTalkDept : delList) {
				delString = delString + dingTalkDept.getId() + "---" + dingTalkDept.getDeptName() + "\n";
			}
		}
		String synDept = "1,钉钉现有部门数 " + dingCount + " 个;\n";
		synDept = synDept + "2,数据库现有部门数 " + dataBaseCount + " 个;\n";
		synDept = synDept + "3,数据库新增部门数 " + insertCount + " 个: " + insertString + "\n";
		synDept = synDept + "4,数据库修改部门数 " + updateCount + " 个: " + updateString + "\n";
		synDept = synDept + "5,数据库删除部门数 " + delCount + " 个:" + delString + "\n";
		return synDept;
	}
}
