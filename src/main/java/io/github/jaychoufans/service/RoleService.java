package io.github.jaychoufans.service;

import io.github.jaychoufans.model.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {

	int saveRole(Role role);

	int updateRole(Role role);

	int deleteRole(Integer roleId);

	void deleteRoleUserRsByUserId(Long userId);

	void deleteRoleUserRsByRoleId(Integer roleId);

	List<Role> selectRoleList(Map<String, Object> map);

	int getTotalRole(Map<String, Object> map);

	List<Role> findByUserId(Long userId);

	int insertRolePermissions(Map<String, Object> map);

}
