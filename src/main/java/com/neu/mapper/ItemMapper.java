package com.neu.mapper;

import com.neu.entity.Item;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 功能描述:
 *
 * @author zzb
 * @email 1135462964@qq.com
 * @date 2021-10-17 22:42
 */
@Mapper
public interface ItemMapper {
    List<Item> selectAll();
    Item selectById(Integer id);
    int insert(Item item);
    int delete(Integer id);
    int update(Item item);
    void insertBatch(List<Item> items);
    void updateBatch(List<Item> items);
    List<Item> selectByNameLike(String name);
    List<Item> selectRandom();
    int updateScore(Item item);
    List<Item> selectRecent(List<String> dates);
}
