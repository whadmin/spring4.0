package com.mybatis.mapper;
import java.util.List;

import com.mybatis.entity.User;
public interface UserMapper {

	List<User> getAll();

	User getOne(Long id);

	void insert(User user);

	void update(User user);

	void delete(Long id);

}