import com.neu.entity.Item;
import com.neu.filter.ItemFilter;
import com.neu.mapper.ItemMapper;
import com.neu.mapper.ItemTypeMapper;
import com.neu.mapper.RelationMapper;
import com.neu.mapper.UserMapper;
import com.neu.service.ItemService;
import com.neu.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * 功能描述:
 *
 * @author zzb
 * @email 1135462964@qq.com
 * @date 2021-10-17 22:24
 */
public class TestUser {
    private static ApplicationContext ac = null;
    private static UserMapper userMapper = null;
    private static ItemMapper itemMapper = null;
    private static RelationMapper relationMapper = null;
    private static ItemTypeMapper itemTypeMapper = null;
    private static ItemFilter itemFilter = null;
    private static UserService userService = null;
    private static ItemService itemService = null;
    static {
        ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        userMapper = ac.getBean("userMapper", UserMapper.class);
        itemMapper = ac.getBean("itemMapper", ItemMapper.class);
        relationMapper = ac.getBean("relationMapper", RelationMapper.class);
        itemTypeMapper = ac.getBean("itemTypeMapper", ItemTypeMapper.class);
        itemFilter = ac.getBean("itemFilter", ItemFilter.class);
        userService = ac.getBean("userServiceImpl", UserService.class);
        itemService = ac.getBean("itemServiceImpl", ItemService.class);
    }

//    @Test
//    public void test() {
//        List<RelateDTO> data = InputFileUtils.getData();
//        List<Item> itemData = InputFileUtils.getItemData();
//
//        Map<Integer, List<RelateDTO>> itemMap = data.stream().collect(Collectors.groupingBy(RelateDTO::getModuleId));
//        Map<Integer, Double> scoreMap = new HashMap<>();
//        itemMap.forEach((k, v) -> {
//            double scoreSum = 0;
//            for (int i = 0; i < v.size(); i++) {
//                scoreSum += v.get(i).getIndex();
//            }
//            scoreMap.put(k, scoreSum / v.size());
//        });
////        System.out.println(scoreMap);
//
//        itemData.forEach(item -> {
//            item.setScore(scoreMap.get(item.getId()));
//        });
////        System.out.println(itemData);
//
//        itemMapper.insertBatch(itemData);
//    }

//    @Test
//    public void test1() {
//        List<Relation> relations = InputFileUtils.getData();
//        relationMapper.insertBatch(relations);
//    }

//    @Test
//    public void test2() {
//        List<Item> items = InputFileUtils.getItemData();
//        items.forEach(item -> {
//            itemMapper.update(item);
//        });
//    }
//    @Test
//    public void test3() {
////        System.out.println(InputFileUtils.getType());
//        InputFileUtils.getType().forEach(itemType -> {
//            itemTypeMapper.insert(itemType);
//        });
//    }

    @Test
    public void test4() {
        List<Item> items = itemMapper.selectAll();
        items.forEach(item -> {
            System.out.println(itemFilter.getItem(item));
        });
    }

    @Test
    public void test5() {
        userService.getNearestUsers(5);
    }

    @Test
    public void test6() {
        userService.getNearestUsers(5).forEach(System.out::println);
        System.out.println("------------------------------------------");
        userService.getEvaluation(5).forEach(System.out::println);
        System.out.println("------------------------------------------");
        userService.getRecommend(5).forEach(System.out::println);
    }

    @Test
    public void test7() {
        System.out.println(itemService.selectByRecent());
    }
}
