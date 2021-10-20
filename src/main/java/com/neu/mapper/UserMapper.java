package com.neu.mapper;

import com.neu.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 功能描述:
 *
 * @author zzb
 * @email 1135462964@qq.com
 * @date 2021-10-13 10:46
 */
@Mapper
public interface UserMapper {
    List<User> selectAll();
    User selectById(Integer id);
    User selectByUsernameAndPassword(User user);
    int insert(User user);
    int delete(Integer id);
    int update(User user);
    void insertBatch(List<User> users);
}
