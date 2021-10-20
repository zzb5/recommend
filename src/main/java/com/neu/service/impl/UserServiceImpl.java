package com.neu.service.impl;

import com.neu.entity.Item;
import com.neu.entity.Relation;
import com.neu.entity.User;
import com.neu.filter.ItemFilter;
import com.neu.mapper.ItemMapper;
import com.neu.mapper.RelationMapper;
import com.neu.mapper.UserMapper;
import com.neu.service.ItemService;
import com.neu.service.UserService;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 功能描述:
 *
 * @author zzb
 * @email 1135462964@qq.com
 * @date 2021-10-13 10:45
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private ItemFilter itemFilter;
    @Autowired
    private ItemService itemService;

    @Override
    public User login(User user) {
        return userMapper.selectByUsernameAndPassword(user);
    }

    @Override
    public int register(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int evaluate(Relation relation) {
        int result = relationMapper.insert(relation);
        itemService.updateScore(relation.getItemId());
        return result;
    }

    @Override
    public List<Item> getRecommend(Integer id) {
        if (relationMapper.selectByUserId(id).size() >= 5) {
            return recommend(id, relationMapper.selectAll());
        } else {
            System.out.println("新用户------------------------");
            return itemMapper.selectRandom();
        }
    }

    @Override
    public List<User> getNearestUsers(Integer id) {
        return getNearestUsers(getNearestUsers(id, relationMapper.selectAll()));
    }

    @Override
    public List<Item> getEvaluation(Integer id) {
        List<Relation> relationList = relationMapper.selectByUserId(id);
        List<Item> items = new ArrayList<>();
        relationList.forEach(relation -> {
            Item item = itemMapper.selectById(relation.getItemId());
            item.setScore(relation.getScore());
            item.setTypeName(itemFilter.getItem(item).getTypeName());
            items.add(item);
        });
        return items;
    }

    private List<User> getNearestUsers(Map<Integer, List<Relation>> userMap) {
        List<User> users = new ArrayList<>();
        userMap.forEach((k, v) -> {
            users.add(userMapper.selectById(k));
        });
        return users;
    }

    private List<Item> recommend(Integer userId, List<Relation> list) {
        Map<Integer, List<Relation>> userMap = getNearestUsers(userId, list);
        List<Relation> relations = new ArrayList<>();
        userMap.forEach((k, v) -> {
            relations.addAll(v);
        });
        Map<Integer, List<Relation>> itemMap = relations.stream().collect(Collectors.groupingBy(Relation::getItemId));
        List<Item> items = new ArrayList<>();
        itemMap.forEach((k, v) -> {
            Item item = itemMapper.selectById(v.get(0).getItemId());
            double score = 0;
            for (int i = 0; i < v.size(); i++) {
                score += v.get(i).getScore() - 2.5;
            }
            item.setScore(score);
            item = itemFilter.getItem(item);
            items.add(item);
        });
        items.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                int result = 0;
                if (o1.getScore() > o2.getScore()) {
                    result = -1;
                } else if (o1.getScore() < o2.getScore()) {
                    result = 1;
                }
                return result;
            }
        });
        List<Item> itemList = getEvaluation(userId);
        List<Item> contain = new ArrayList<>();
        items.forEach(item -> {
            itemList.forEach(item1 -> {
                if (item.getId() == item1.getId()) {
                    contain.add(item);
                }
            });
        });
        items.retainAll(contain);
        return items;
//        //最近邻用户看过电影列表
//        List<Integer> neighborItemList = userMap.get(nearest).stream().map(e -> e.getItemId()).collect(Collectors.toList());
//        //指定用户看过电影列表
//        List<Integer> userItemList = userMap.get(userId).stream().map(e -> e.getItemId()).collect(Collectors.toList());
//
//
//        //找到最近邻看过，但是该用户没看过的电影，计算推荐，放入推荐列表
//        List<Integer> recommendList = new ArrayList<>();
//        for (Integer item : neighborItemList) {
//            if (!userItemList.contains(item)) {
//                recommendList.add(item);
//            }
//        }
//        Collections.sort(recommendList);
//        return recommendList;
//        return null;
    }

    private Map<Integer, List<Relation>> getNearestUsers(Integer userId, List<Relation> list) {
        //找到最近邻用户id
        Map<Double, Integer> distances = computeNearestNeighbor(userId, list);
        List<Integer> nearest = new ArrayList<>();
        Iterator<Integer> iterator = distances.values().iterator();
        for (int i = 0; i < 5; i++) {
            if (iterator.hasNext()) {
                nearest.add(iterator.next());
            }
        }

        return list.stream().filter(relation -> nearest.contains(relation.getUserId())).collect(Collectors.groupingBy(Relation::getUserId));
    }

    /**
     * 在给定userId的情况下，计算其他用户和它的相关系数并排序
     *
     * @param userId
     * @param list
     * @return
     */
    private Map<Double, Integer> computeNearestNeighbor(Integer userId, List<Relation> list) {
        Map<Integer, List<Relation>> userMap = list.stream().collect(Collectors.groupingBy(Relation::getUserId));
        Map<Double, Integer> distances = new TreeMap<>();
        List<Relation> currentUserRelation = userMap.get(userId);
        userMap.forEach((k, v) -> {
            if (k != userId) {
                double distance = pearson_dis(v, currentUserRelation);
                distances.put(distance, k);
            }
        });
        return distances;
    }


    /**
     * 计算两个序列间的相关系数
     *
     * @param xList
     * @param yList
     * @return
     */
    private double pearson_dis(List<Relation> xList, List<Relation> yList) {
        List<Double> xs = Lists.newArrayList();
        List<Double> ys = Lists.newArrayList();
        xList.forEach(x -> {
            yList.forEach(y -> {
                if (x.getItemId() == y.getItemId()) {
                    xs.add(x.getScore());
                    ys.add(y.getScore());
                }
            });
        });
        return getRelate(xs, ys);
    }

    /**
     * 方法描述: 皮尔森（pearson）相关系数计算
     *
     * @param xs
     * @param ys
     * @throws
     * @Return {@link Double}
     * @author tarzan
     * @date 2020年07月31日 17:03:20
     */
    private Double getRelate(List<Double> xs, List<Double> ys) {
        int n = xs.size();
        // x的和
        double Ex = xs.stream().mapToDouble(x -> x).sum();
        // y的和
        double Ey = ys.stream().mapToDouble(y -> y).sum();
        // x的平方和
        double Ex2 = xs.stream().mapToDouble(x -> Math.pow(x, 2)).sum();
        // y的平方和
        double Ey2 = ys.stream().mapToDouble(y -> Math.pow(y, 2)).sum();
        // x*y的和
        double Exy = IntStream.range(0, n).mapToDouble(i -> xs.get(i) * ys.get(i)).sum();
        double numerator = Exy - Ex * Ey / n;
        double denominator = Math.sqrt((Ex2 - Math.pow(Ex / n, 2)) * (Ey2 - Math.pow(Ey / n, 2)));
        if (denominator == 0) return 0.0;
        double result = numerator / denominator;
        return -Math.abs(result);
    }
}
