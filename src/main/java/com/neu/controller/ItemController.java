package com.neu.controller;

import com.neu.entity.Item;
import com.neu.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 功能描述:
 *  version git稳定版
 * @author zzb
 * @email 1135462964@qq.com
 * @date 2021-10-19 8:21
 */
@RestController
@CrossOrigin
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/selectAll")
    public List<Item> selectAll() {
        return itemService.selectAll();
    }

    @RequestMapping("/selectByNameLike")
    public List<Item> selectBYNameLike(@RequestBody Item item) {
        return itemService.selectByNameLike(item.getName());
    }

    @RequestMapping("/selectRecent")
    public List<Item> selectRecent() {
        return itemService.selectByRecent();
    }
}
