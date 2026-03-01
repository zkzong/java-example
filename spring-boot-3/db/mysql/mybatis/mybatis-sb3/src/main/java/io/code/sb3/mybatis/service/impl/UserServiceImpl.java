package io.code.sb3.mybatis.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.code.sb3.mybatis.domain.User;
import io.code.sb3.mybatis.mapper.UserMapper;
import io.code.sb3.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Override
    public PageInfo<User> getUserPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> allUsers = userMapper.getAllUsers();
        PageInfo<User> userPage = new PageInfo<>(allUsers);
        return userPage;
    }

    @Override
    public User findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }

    @Override
    public void insertOne(String userName, int age) {
        userMapper.insertOne(userName, age);
    }

    @Override
    public void insertOneWithDate(User user) {
        userMapper.insertOneWithDate(user);
    }

    @Override
    public int insertBatch(List<User> userList) {
        return userMapper.insertBatch(userList);
    }

    @Override
    @CachePut(cacheNames = "user", key = "#user.id")
    public User insertAndGetId(User user) {
        userMapper.insertAndGetId(user);
        return user;
    }

    @Override
    public User findByIndex(String userName, int age) {
        return userMapper.findByIndex(userName, age);
    }

    @Override
    public User findByMap(Map map) {
        return userMapper.findByMap(map);
    }

    @Override
    public User findByParam(String userName, int age) {
        return userMapper.findByParam(userName, age);
    }

    @Override
    public List<User> namein(List<String> names, String name) {
        return userMapper.namein(names, name);
    }

    @Override
    public int updateBatch(List<User> userList) {
        return userMapper.updateBatch(userList);
    }

}
