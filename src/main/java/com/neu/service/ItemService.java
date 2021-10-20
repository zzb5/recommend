package com.neu.service;

import com.neu.entity.Item;

import java.util.List;

/**
 * 功能描述:
 *
 * @author zzb
 * @email 1135462964@qq.com
 * @date 2021-10-19 8:22
 */
public interface ItemService {
    List<Item> selectAll();
    List<Item> selectByNameLike(String name);
    void updateScore(Integer itemId);
    List<Item> selectByRecent();
}
