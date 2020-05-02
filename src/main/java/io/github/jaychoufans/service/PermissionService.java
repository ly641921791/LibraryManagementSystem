package io.github.jaychoufans.service;

import io.github.jaychoufans.model.Permission;
import io.github.jaychoufans.model.User;

import java.util.List;

public interface PermissionService {

	List<Permission> queryPermissionsByUser(User user);

	List<Permission> queryAll();

	void deleteRolePermissionRsByRoleId(Integer roleId);

	List<Integer> queryPermissionIdsByRoleId(Integer roleId);

}
