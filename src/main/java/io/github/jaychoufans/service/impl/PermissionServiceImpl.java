package io.github.jaychoufans.service.impl;

import io.github.jaychoufans.dao.PermissionMapper;
import io.github.jaychoufans.model.Permission;
import io.github.jaychoufans.model.User;
import io.github.jaychoufans.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

	@Resource
	private PermissionMapper permissionMapper;

	@Override
	public List<Permission> queryPermissionsByUser(User user) {
		return permissionMapper.queryPermissionsByUser(user);
	}

	@Override
	public List<Permission> queryAll() {
		return permissionMapper.queryAll();
	}

	@Override
	public void deleteRolePermissionRsByRoleId(Integer roleId) {
		permissionMapper.deleteRolePermissionRsByRoleId(roleId);
	}

	@Override
	public List<Integer> queryPermissionIdsByRoleId(Integer roleId) {
		return permissionMapper.queryPermissionIdsByRoleId(roleId);
	}

}
