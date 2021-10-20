package com.neu.filter;

import com.neu.entity.Item;
import com.neu.entity.ItemType;
import com.neu.mapper.ItemTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述:
 *
 * @author zzb
 * @email 1135462964@qq.com
 * @date 2021-10-18 17:20
 */
@Service
public class ItemFilter {

    @Autowired
    private ItemTypeMapper itemTypeMapper;

    public Item getItem(Item item) {
        List<ItemType> itemTypes = itemTypeMapper.selectAll();
        List<String> typeNames = new ArrayList<>();
        int type = item.getType();
        for (int i = itemTypes.size() - 1; i >= 0; i--) {
            int current = itemTypes.get(i).getCode();
            if (type >= current) {
                type -= current;
                typeNames.add(itemTypes.get(i).getName());
            }
        }
        item.setTypeName(typeNames);
        return item;
    }
}
