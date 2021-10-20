package com.neu.service;

import com.neu.entity.Item;
import com.neu.entity.Relation;
import com.neu.entity.User;

import java.util.List;

/**
 * 功能描述:
 *
 * @author zzb
 * @email 1135462964@qq.com
 * @date 2021-10-13 10:44
 */
public interface UserService {

    User login(User user);
    int register(User user);
    int evaluate(Relation relation);
    List<Item> getRecommend(Integer id);
    List<User> getNearestUsers(Integer id);
    List<Item> getEvaluation(Integer id);

}
