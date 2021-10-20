package com.neu.service.impl;

import com.neu.entity.Item;
import com.neu.entity.Relation;
import com.neu.filter.ItemFilter;
import com.neu.mapper.ItemMapper;
import com.neu.mapper.RelationMapper;
import com.neu.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 功能描述:
 *
 * @author zzb
 * @email 1135462964@qq.com
 * @date 2021-10-19 8:23
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemFilter itemFilter;
    @Autowired
    private RelationMapper relationMapper;

    @Override
    public List<Item> selectAll() {
        List<Item> items = itemMapper.selectAll();

        items.forEach(item -> {
            item.setTypeName(itemFilter.getItem(item).getTypeName());
        });
        return items;
    }

    @Override
    public List<Item> selectByNameLike(String name) {
        return itemMapper.selectByNameLike(name);
    }

    @Override
    public void updateScore(Integer itemId) {
        List<Relation> relationList = relationMapper.selectByItemId(itemId);
        double sum = relationList.stream().mapToDouble(Relation::getScore).sum();
        Item item = new Item();
        item.setId(itemId);
        item.setScore(sum / relationList.size());
        itemMapper.updateScore(item);
    }

    @Override
    public List<Item> selectByRecent() {
        List<String> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = (calendar.get(Calendar.DATE));
        for (int i = 0; i < 30; i++) {
            String time = day + "-" + month + "-" + year;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date date = null;
            try {
                date = sdf.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            sdf = new SimpleDateFormat("dd-MMMMM-yyyy", Locale.US);
            time = sdf.format(date);
            time = time.substring(0,6) + time.substring(time.lastIndexOf("-"));
            dates.add(time);
            day--;
        }
        System.out.println(dates);
        List<Item> items = itemMapper.selectRecent(dates);
        items.forEach(item -> {
            item.setTypeName(itemFilter.getItem(item).getTypeName());
        });
        return items;
    }
}
