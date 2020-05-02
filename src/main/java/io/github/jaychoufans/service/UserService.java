package io.github.jaychoufans.service;

import io.github.jaychoufans.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

	User findUserByUserId(Long userId);

	User findUserByUserName(String userName);

	int saveUser(User user);

	int updateUser(User user);

	int deleteUser(Long userId);

	List<User> selectUserList(Map<String, Object> map);

	int getTotalUser(Map<String, Object> map);

	int insertUserRoles(Map<String, Object> map);

}
