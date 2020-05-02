package io.github.jaychoufans.service.impl;

import com.google.common.base.Preconditions;
import io.github.jaychoufans.dao.RoleMapper;
import io.github.jaychoufans.exception.ParamException;
import io.github.jaychoufans.model.Role;
import io.github.jaychoufans.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Resource
	private RoleMapper roleMapper;

	@Override
	public int saveRole(Role role) {
		if (checkRoleNameExist(role.getRoleName(), role.getRoleId())) {
			throw new ParamException("角色名已被占用");
		}
		Role roles = Role.builder().roleName(role.getRoleName()).build();
		return roleMapper.insertSelective(roles);
	}

	@Override
	public int updateRole(Role role) {
		if (checkRoleNameExist(role.getRoleName(), role.getRoleId())) {
			throw new ParamException("角色名已被占用");
		}
		Role before = roleMapper.selectByPrimaryKey(role.getRoleId());
		Preconditions.checkNotNull(before, "需更新的角色不存在");
		Role roles = Role.builder().roleId(role.getRoleId()).roleName(role.getRoleName()).build();
		return roleMapper.updateByPrimaryKeySelective(roles);
	}

	@Override
	public int deleteRole(Integer roleId) {
		Role before = roleMapper.selectByPrimaryKey(roleId);
		Preconditions.checkNotNull(before, "需删除的角色不存在");
		return roleMapper.deleteByPrimaryKey(roleId);
	}

	@Override
	public void deleteRoleUserRsByUserId(Long userId) {

		roleMapper.deleteRoleUserRsByUserId(userId);
	}

	@Override
	public void deleteRoleUserRsByRoleId(Integer roleId) {

		roleMapper.deleteRoleUserRsByRoleId(roleId);
	}

	@Override
	public List<Role> selectRoleList(Map<String, Object> map) {
		return roleMapper.selectRoleList(map);
	}

	@Override
	public int getTotalRole(Map<String, Object> map) {
		return roleMapper.getTotalRole(map);
	}

	@Override
	public List<Role> findByUserId(Long userId) {
		return roleMapper.findByUserId(userId);
	}

	@Override
	public int insertRolePermissions(Map<String, Object> map) {
		return roleMapper.insertRolePermissions(map);
	}

	public boolean checkRoleNameExist(String roleName, Integer roleId) {
		return roleMapper.countByRoleName(roleName, roleId) > 0;
	}

}
