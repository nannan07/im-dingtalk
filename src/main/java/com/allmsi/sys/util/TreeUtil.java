package com.allmsi.sys.util;

import java.util.ArrayList;
import java.util.List;

import com.allmsi.sys.util.BaseTree;

public class TreeUtil {

	/**
	 * @Title getfatherNode
	 * @Description 方法描述: 父节点
	 * @param treeDataList
	 * @return 返回类型：List<JsonTreeData>
	 */
	public final static <T extends BaseTree<T>> List<T> getTreeWithOutRootNood(String id, List<T> treeDataList) {
		List<T> newTreeDataList = new ArrayList<T>();
		for (T jsonTreeData : treeDataList) {
			if (jsonTreeData != null && StrUtil.notEmpty(jsonTreeData.getpId()) && id.equals(jsonTreeData.getpId())) {
				// 获取父节点下的子节点
				jsonTreeData.setChildren(getChildrenNode(jsonTreeData.getId(), treeDataList));
				newTreeDataList.add(jsonTreeData);
			}
		}
		return newTreeDataList;
	}

	/**
	 * @Title getfatherNode
	 * @Description 方法描述: 父节点
	 * @param treeDataList
	 * @return 返回类型：List<JsonTreeData>
	 */
	public final static <T extends BaseTree<T>> List<T> getFatherNode(String id, List<T> treeDataList) {
		List<T> newTreeDataList = new ArrayList<T>();
		for (T jsonTreeData : treeDataList) {
			if (jsonTreeData != null) {
				if (StrUtil.isEmpty(jsonTreeData.getpId()) || id.equals(jsonTreeData.getId())) {
					// 获取父节点下的子节点
					jsonTreeData.setChildren(getChildrenNode(jsonTreeData.getId(), treeDataList));
					newTreeDataList.add(jsonTreeData);
				}
			}
		}
		return newTreeDataList;
	}

	/**
	 * @Title getChildrenNode
	 * @Description 方法描述: 子节点
	 * @param pid
	 * @param treeDataList
	 * @return 返回类型：List<T>
	 */
	private final static <T extends BaseTree<T>> List<T> getChildrenNode(String pid, List<T> treeDataList) {
		List<T> newTreeDataList = new ArrayList<T>();
		for (T jsonTreeData : treeDataList) {
			if (jsonTreeData == null || jsonTreeData.getpId() == null) {
				continue;
			}
			// 这是一个子节点
			if (jsonTreeData.getpId().equals(pid)) {
				// 递归获取子节点下的子节点
				if (getChildrenNode(jsonTreeData.getId(), treeDataList) != null) {
					jsonTreeData.setChildren(getChildrenNode(jsonTreeData.getId(), treeDataList));
				}
				newTreeDataList.add(jsonTreeData);
			}
		}
		if (newTreeDataList != null && newTreeDataList.size() > 0) {
			return newTreeDataList;
		}
		return null;
	}
}
