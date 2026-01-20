package org.example.sb3.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.sb3.mybatis.domain.User;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    List<User> getAllUsers();

    User findByUserName(String userName);

    void insertOne(@Param("userName") String userName, @Param("age") int age);

    void insertOneWithDate(User user);

    int insertBatch(@Param("userlist") List<User> userList);

    int insertAndGetId(User user);

    User findByIndex(String userName, int age);

    User findByMap(Map map);

    User findByParam(@Param("userName") String userName, @Param("age") int age);

    List<User> namein(@Param("names") List<String> names, @Param("name") String name);

    int updateBatch(List<User> userList);

    /**
     * 获取总记录数
     */
    long count();

    /**
     * 分页查询
     * @param offset 偏移量
     * @param limit 每页记录数
     */
    List<User> selectByPage(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 获取最小ID
     */
    Long selectMinId();

    /**
     * 获取最大ID
     */
    Long selectMaxId();

    /**
     * 按ID范围查询（性能优化）
     * @param minId 最小ID（包含）
     * @param maxId 最大ID（包含）
     */
    List<User> selectByIdRange(@Param("minId") Long minId, @Param("maxId") Long maxId);

}
