package io.github.jaychoufans.controller;

import io.github.jaychoufans.annotation.LoginRequired;
import io.github.jaychoufans.common.PageBean;
import io.github.jaychoufans.common.DataGridDataSource;
import io.github.jaychoufans.common.JsonData;
import io.github.jaychoufans.model.Role;
import io.github.jaychoufans.service.PermissionService;
import io.github.jaychoufans.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Resource
	private RoleService roleService;

	@Resource
	private PermissionService permissionService;

	@PostMapping("/save")
	@LoginRequired
	public JsonData saveRole(Role role) {
		int count = roleService.saveRole(role);
		if (count > 0) {
			return JsonData.success(count, "添加成功");
		}
		else {
			return JsonData.fail("添加失败");
		}

	}

	@PutMapping("/update")
	@LoginRequired
	public JsonData updateRole(Role role) {
		int count = roleService.updateRole(role);
		if (count > 0) {
			return JsonData.success(count, "更新成功");
		}
		else {
			return JsonData.fail("更新失败");
		}
	}

	@DeleteMapping("/delete")
	@LoginRequired
	public JsonData deleteRole(@RequestParam(value = "roleId") Integer roleId) {

		// TODO 根据角色id删除角色权限关联信息，再根据角色id删除用户角色关联信息
		permissionService.deleteRolePermissionRsByRoleId(roleId);
		roleService.deleteRoleUserRsByRoleId(roleId);
		int count = roleService.deleteRole(roleId);
		if (count > 0) {
			return JsonData.success(count, "删除成功");
		}
		else {
			return JsonData.fail("删除失败");
		}
	}

	@PostMapping("/list")
	@LoginRequired
	public DataGridDataSource<Role> getRoleList(
			@RequestParam(value = "roleName", required = false, defaultValue = "") String roleName,
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(value = "rows", required = false, defaultValue = "5") Integer rows) {
		PageBean pageBean = new PageBean(page, rows);
		Map<String, Object> map = new HashMap<>();
		map.put("roleName", "%" + roleName + "%");
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<Role> roleList = roleService.selectRoleList(map);
		int totalRole = roleService.getTotalRole(map);
		DataGridDataSource<Role> dataGridDataSource = new DataGridDataSource<>();
		dataGridDataSource.setRows(roleList);
		dataGridDataSource.setTotal(totalRole);
		return dataGridDataSource;
	}

	@PostMapping("/savePermissionSet")
	@LoginRequired
	public JsonData savePermissionSet(Integer roleId, Integer[] permissionIds) {
		// 先删除当前角色拥有的权限关系
		permissionService.deleteRolePermissionRsByRoleId(roleId);
		Map<String, Object> map = new HashMap<>();
		map.put("roleId", roleId);
		map.put("permissionIds", permissionIds);
		int count = roleService.insertRolePermissions(map);
		if (count > 0) {
			return JsonData.success(count, "设置成功");
		}
		else {
			return JsonData.fail("设置失败");
		}

	}

}
