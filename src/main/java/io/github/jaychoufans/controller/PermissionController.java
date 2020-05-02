package io.github.jaychoufans.controller;

import io.github.jaychoufans.annotation.LoginRequired;
import io.github.jaychoufans.common.DataGridDataSource;
import io.github.jaychoufans.model.Permission;
import io.github.jaychoufans.service.PermissionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/permission")
public class PermissionController {

	@Resource
	private PermissionService permissionService;

	@PostMapping("/loadRolePermissionData")
	@LoginRequired
	public Object loadRolePermissionData(Integer roleId) {
		List<Permission> permissions = new ArrayList<>();
		List<Permission> ps = permissionService.queryAll();

		// 获取当前角色已经分配的权限信息
		List<Integer> permissionids = permissionService.queryPermissionIdsByRoleId(roleId);

		Map<Integer, Permission> permissionMap = new HashMap<>();
		for (Permission p : ps) {
			if (permissionids.contains(p.getPermissionId())) {
				p.setChecked(true);
			}
			else {
				p.setChecked(false);
			}
			permissionMap.put(p.getPermissionId(), p);
		}
		for (Permission p : ps) {
			Permission child = p;
			if (child.getPermissionParentId() == null) {
				permissions.add(p);
			}
			else {
				Permission parent = permissionMap.get(child.getPermissionParentId());
				parent.getChildren().add(child);
			}
		}
		return permissions;
	}

	@PostMapping("/list")
	@LoginRequired
	public DataGridDataSource<Permission> list() {

		List<Permission> permissionTreeGridList = new ArrayList<>();
		List<Permission> permissionList = permissionService.queryAll();
		for (Permission permission : permissionList) {
			Permission permissionTreeGrid = new Permission();
			permissionTreeGrid.setPermissionId(permission.getPermissionId());
			permissionTreeGrid.setPermissionName(permission.getPermissionName());
			permissionTreeGrid.setPermissionUrl(permission.getPermissionUrl());
			permissionTreeGrid.setPermissionIcon(permission.getPermissionIcon());
			permissionTreeGrid.setPermissionCreateTime(permission.getPermissionCreateTime());
			permissionTreeGrid.setPermissionLastModifyTime(permission.getPermissionLastModifyTime());
			permissionTreeGrid.set_parentId(permission.getPermissionParentId());
			permissionTreeGridList.add(permissionTreeGrid);
		}
		DataGridDataSource<Permission> permissionDataGridDataSource = new DataGridDataSource<>();
		permissionDataGridDataSource.setTotal(permissionTreeGridList.size());
		permissionDataGridDataSource.setRows(permissionTreeGridList);
		return permissionDataGridDataSource;
	}

}
