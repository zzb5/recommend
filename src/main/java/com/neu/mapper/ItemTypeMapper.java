package com.neu.mapper;

import com.neu.entity.ItemType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 功能描述:
 *
 * @author zzb
 * @email 1135462964@qq.com
 * @date 2021-10-18 16:43
 */
@Mapper
public interface ItemTypeMapper {
    int insert(ItemType itemType);
    List<ItemType> selectAll();
}
