package com.neu.controller;

import com.neu.entity.Item;
import com.neu.entity.Relation;
import com.neu.entity.User;
import com.neu.service.ItemService;
import com.neu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 功能描述:
 *
 * @author zzb
 * @email 1135462964@qq.com
 * @date 2021-10-13 10:46
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;

    @RequestMapping("/login")
    public User login(@RequestBody User user) {
        return userService.login(user);
    }

    @RequestMapping("/register")
    public int register(@RequestBody User user) {
        return userService.register(user);
    }

    @RequestMapping("/getRecommend")
    public List<Item> getRecommend(@RequestBody User user) {
        return userService.getRecommend(user.getId());
    }

    @RequestMapping("/evaluate")
    public int evaluate(@RequestBody Relation relation) {
        return userService.evaluate(relation);
    }

    @RequestMapping("/getNearestUsers")
    public List<User> getNearestUsers(@RequestBody User user) {
        return userService.getNearestUsers(user.getId());
    }

    @RequestMapping("/getEvaluation")
    public List<Item> getEvaluation(@RequestBody User user) {
        return userService.getEvaluation(user.getId());
    }
}
